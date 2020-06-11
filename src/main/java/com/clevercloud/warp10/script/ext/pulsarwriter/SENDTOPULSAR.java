package com.clevercloud.warp10.script.ext.pulsarwriter;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SENDTOPULSAR extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  public SENDTOPULSAR(String name) {
    super(name);
  }

  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    return stack;
  }
}