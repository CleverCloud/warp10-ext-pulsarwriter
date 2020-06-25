package com.clevercloud.warp10.script.ext.pulsarwriter;

import java.util.HashMap;
import java.util.Map;

import io.warp10.warp.sdk.WarpScriptExtension;

public class PulsarWriterExtension extends WarpScriptExtension {

  private static final Map<String, Object> functions;

  static {
      functions = new HashMap<String, Object>();
      functions.put("SENDTOPULSAR", new SENDTOPULSAR("SENDTOPULSAR"));
  }

  @Override
  public Map<String, Object> getFunctions() {
      return functions;
  }
}
