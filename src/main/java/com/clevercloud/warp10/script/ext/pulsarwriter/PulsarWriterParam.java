package com.clevercloud.warp10.script.ext.pulsarwriter;

public enum PulsarWriterParam {
  BISCUIT ("biscuit", "class java.lang.String"),
  ENDPOINT ("endpoint", "class java.lang.String"),
  TOPIC ("topic", "class java.lang.String");

  private final String param;
  private final String typeAsString;

  PulsarWriterParam(final String param, final String typeAsString) {
    this.param = param;
    this.typeAsString = typeAsString;
  }

  final String getParam() {
    return this.param;
  }

  final String getType() {
    return this.typeAsString;
  }
}