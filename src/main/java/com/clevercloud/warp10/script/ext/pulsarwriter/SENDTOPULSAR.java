package com.clevercloud.warp10.script.ext.pulsarwriter;

import java.util.Map;

import com.clevercloud.biscuitpulsar.AuthenticationBiscuit;

import java.util.HashMap;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import io.vavr.collection.List;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SENDTOPULSAR extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  protected final Map<String, String> validKeys = new HashMap<>();
  protected final List<PulsarWriterParam> mandatoryParams = List.of(
    PulsarWriterParam.BISCUIT,
    PulsarWriterParam.ENDPOINT,
    PulsarWriterParam.TOPIC
  );

  public SENDTOPULSAR(final String name) {
    super(name);
    for(PulsarWriterParam param : PulsarWriterParam.values()) {
      this.validKeys.put(param.getParam(), param.getType());
    }
  }

  /*
    This is powered by Exception Driven Development which saddens me greatly.
    However, Java 8 makes it a pain to not do that. So, there.
  */
  public final Map<String, Object> validateParamsMap(final Object input) throws WarpScriptException {
    if (!(input instanceof Map)) {
      throw new WarpScriptException(getName() + "expects a Map as parameters");
    }

    final Map<String, Object> paramsMap = (Map<String, Object>) input;

    for (Object key : paramsMap.keySet()) {
      validate(key, paramsMap.get(key));
    }

    for (PulsarWriterParam param : mandatoryParams) {
      if(!paramsMap.containsKey(param.getParam())) {
        throw new WarpScriptException(getName() + "expects the key " + param.getParam() + " in the parameters map");
      }
    }
    
    return paramsMap;
  }

  public final void validate(final Object key, final Object value) throws WarpScriptException {
    if (!(key instanceof String)) {
      throw new WarpScriptException(getName() + " Expect each keys of the parameter map to be a string");
    }

    final String currentKey = key.toString();

    if (!(this.validKeys.containsKey(currentKey))) {
      throw new WarpScriptException(getName() + " Expect each key of the parameters map to be a valid key: "
              + this.validKeys.keySet().toString());
    }

    if (!(value.getClass().toString().equals(this.validKeys.get(currentKey)))) {
      throw new WarpScriptException(getName() + " Expect key " + currentKey + " to be of type: "
              + this.validKeys.get(currentKey).replace("class java.lang.", ""));
    }
  }

  @Override
  public Object apply(final WarpScriptStack stack) throws WarpScriptException {
    final Map<String, Object> params = this.validateParamsMap(stack.pop());
    final String data = (String) stack.pop();
    try {
      final PulsarClient client = PulsarClient.builder()
        .serviceUrl(params.get(PulsarWriterParam.ENDPOINT.getParam()).toString())
        .authentication(AuthenticationBiscuit.class.getName(), params.get(PulsarWriterParam.BISCUIT.getParam()).toString())
        .build();

      final Producer<String> producer = client.newProducer(Schema.STRING)
        .topic(params.get(PulsarWriterParam.TOPIC.getParam()).toString())
        .enableBatching(false)
        .create();
      
      producer.send(data);
    } catch (PulsarClientException e) {
      throw new WarpScriptException("Pulsar client exception: " + e.getMessage());
    }
      
    return stack;
  }
}