package com.anos.jtail;

import com.anos.jtail.tools.Utils;
import com.anos.jtail.tools.AnosTailerListener;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.Tailer.Builder;
import org.apache.commons.io.input.TailerListener;

/**
 *
 * @author Gani GÜRGAH <gani.gurgah@qnbsigorta.com>
 */
public class JTail {

    private static String APP_NAME;

    public static void main(String[] args) {
        APP_NAME = JTail.class.getSimpleName();
        if (APP_NAME.indexOf(".") > 0) {
            APP_NAME = APP_NAME.substring(0, APP_NAME.indexOf(".") - 1);
        }

        JTail tail = new JTail();

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(Utils.getOptions(), args);
        } catch (ParseException ex) {
            System.err.println("Command Line can not parse");
            Logger.getLogger(JTail.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        if (!commandLine.hasOption(Utils.getOption("file")) || commandLine.hasOption(Utils.getHelpOption())) {
            if (!commandLine.hasOption(Utils.getHelpOption())) {
                System.err.println("file must be declared");
            }
            tail.showHelp();
            System.exit(1);
        } else {
            String filePath = commandLine.getOptionValue(Utils.getOption("file"));
            if (Utils.isEmtpyStr(filePath)) {
                System.err.println("file must be declared");
                System.exit(1);
            }
            File logFile = new File(filePath);
            String absolutePath = filePath;
            if (logFile != null) {
                absolutePath = logFile.getAbsolutePath();
                logFile = new File(absolutePath);

                if (!logFile.exists()) {
                    String workDir = commandLine.getOptionValue(Utils.getOption("workDir"));
                    if (!Utils.isEmtpyStr(workDir)) {
                        logFile = new File(workDir + File.separator + filePath);
                    }
                }
            }
            if (logFile == null || !logFile.exists()) {
                System.err.println("File \"" + absolutePath + "\" not found");
                System.exit(1);
            }
            String buffer = commandLine.getOptionValue(Utils.getOption("BufferSize"));
            TailerListener listener = new AnosTailerListener();
            Builder builder = Tailer.builder()
                    .setFile(logFile)
                    //.setReOpen(true)
                    .setStartThread(true)
                    //.setTailFromEnd(true)
                    .setTailerListener(listener);
            if (Utils.isNumber(buffer)) {
                builder.setBufferSize(Integer.valueOf(buffer));
            }
            Tailer tailer = builder.get();
            Executor executor = new Executor() {
                public void execute(Runnable command) {
                    command.run();
                }
            };
            executor.execute(tailer);

        }

    }

    private void showHelp() {
        String msg = "\nWith java => java -jar " + APP_NAME + ".jar [options]"
                + " \nWith cmd => " + APP_NAME + " [options]"
                + "\n\nOptions:";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(msg, Utils.getOptions());
    }
}
