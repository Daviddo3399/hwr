package de.daviddo.utils.logging;

import de.daviddo.utils.Utils;
import de.daviddo.utils.io.file.old.IWriter;

import java.io.File;
import java.io.IOException;

import static de.daviddo.program.manager.ProgramManager.PATHSEPARATOR;


/**
 *
 * @author  Daviddo3399
 * @since   8. Januar 2015
 */
public class Logger {

    private static LogWindow    logWindow;

    private static File         logFile;
    private static IWriter      writer;
    private String              path;

    public Logger(String path) {
        if (path == null) throw new NullPointerException("The path can't be null!");

        this.path = path;
        logWindow = new LogWindow(700, 800);
    }

    public void setupLogger() {

        logFile = new File(path + PATHSEPARATOR + Utils.getDate() + "_log.log");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();

                writer = new IWriter(logFile);
                writer.write("#Log has been created! (" + Utils.getDate() + ")");

            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            logFile = new File(path + PATHSEPARATOR + Utils.getDate() + "_log.log");
            writer = new IWriter(logFile);
        }
    }

    public static void log(String message) {
        write(message, null);
    }

    public static void log(String message, LoggerLevel level) {
        write(message, level);
    }

    private static void write(String message, LoggerLevel level) {
        String s = null;
        if (level == null)  s = Utils.getTime() + "> " + message;
        else                s = Utils.getTime() + "> " + "[" + level.toString() + "] " + message;

        System.out.println(s);
        writer.write(s);
        logWindow.write(s);
    }

    public void close() {
        Logger.log("Logger closed..", LoggerLevel.INFO);
    }
}
