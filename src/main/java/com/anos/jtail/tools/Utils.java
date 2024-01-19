package com.anos.jtail.tools;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * @author Gani GÜRGAH <gani.gurgah@qnbsigorta.com>
 */
public class Utils {

    public enum StartParameter {
        filePath("f", "file", "File name"),
        bufferSize("bs", "BufferSize", "sets will show buffers"),
        workingDirectory("wd","workDir","sets working directory"),
        help("h", "help", "show this", false);

        private String param, longParam, decprtion;
        private boolean hasArgs;

        private StartParameter(String param, String longParam, String decprtion) {
            this.param = param;
            this.longParam = longParam;
            this.decprtion = decprtion;
            this.hasArgs = true;
        }

        private StartParameter(String param, String longParam, String decprtion, boolean hasArgs) {
            this.param = param;
            this.longParam = longParam;
            this.decprtion = decprtion;
            this.hasArgs = hasArgs;
        }

        public String getParam() {
            return param;
        }

        public String getLongParam() {
            return longParam;
        }

        public String getDecprtion() {
            return decprtion;
        }
    }

    private static Options options;

    public static Options getOptions() {
        if (options == null) {
            options = new Options();
            StartParameter[] params = StartParameter.values();
            for (StartParameter param : params) {
                options.addOption(new Option(
                        param.param,
                        param.longParam,
                        param.hasArgs,
                        param.decprtion));
            }
        }
        return options;
    }

    public static Option getHelpOption() {
        StartParameter help = StartParameter.help;
        return new Option(help.param, help.longParam, help.hasArgs, help.decprtion);
    }

    public static Option getOption(String param) {
        return getOptions().getOption(param);
    }

    public static boolean isEmtpyStr(String str) {
        return str == null || str.trim().length() <= 0 || str.equals("null");
    }

    public static boolean isNumber(Object value) {
        if (!isEmtpyStr(String.valueOf(value))) {
            int intVal;
            try {
                intVal = Integer.valueOf(String.valueOf(value));
                return intVal > 0;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
