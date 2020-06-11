package com.clevercloud.warp10.script.ext.pulsarwriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.warp10.WarpConfig;
import io.warp10.warp.sdk.WarpScriptExtension;

/**
 * Functions declared by this WarpScript extension must be present in the
 * functions field.
 */
public class PulsarWriterExtension extends WarpScriptExtension {

    private static final Map<String, Object> functions;

    static {
        functions = new HashMap<String, Object>();

        Properties properties = WarpConfig.getProperties();

        final Boolean enable = Boolean.parseBoolean(properties.getProperty("warpscript.pulsarwriter.enable", "true"));

        if (enable) {
            functions.put("SENDTOPULSAR", new SENDTOPULSAR("SENDTOPULSAR"));
        }
    }

    public Map<String, Object> getFunctions() {
        return functions;
    }
}
