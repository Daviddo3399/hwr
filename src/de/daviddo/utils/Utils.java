package de.daviddo.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author  Daviddo3399
 */
public class Utils {

    private static final    Long        MEGABYTE = 1024L * 1024L;
    public static final     String[]    ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static long bytesToMegaBytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static int getObjectSize(Object o) {
        return 0;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        return (double) Math.round(value * factor) / factor;
    }

    public static boolean isNumeric(Object o) {
        return o instanceof Number;
    }

    public static int getIndex(String character) {
        int index = -1;
        for (int i = 0; i < Utils.ALPHABET.length; i++) {
            if (Utils.ALPHABET[i].equalsIgnoreCase(character)) {
                index = i;
            }
        }
        return index;
    }

    public static void editTooltipTiming(Tooltip tooltip, int time) {
        try {
            Field       fieldBehavior   = tooltip.getClass().getDeclaredField("BEHAVIOR");

            fieldBehavior.setAccessible(true);

            Object      objBehavior     = fieldBehavior.get(tooltip);
            Field       fieldTimer      = objBehavior.getClass().getDeclaredField("activationTimer");

            fieldTimer.setAccessible(true);

            Timeline    objTimer        = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(time)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDate() {
        return new SimpleDateFormat("yyyy_MM_dd").format(new Date());
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
