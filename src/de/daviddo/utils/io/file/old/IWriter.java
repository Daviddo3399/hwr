package de.daviddo.utils.io.file.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author 		Daviddo3399
 * @since		4. Januar 2015
 * @deprecated
 */
public class IWriter {

    private File            file;
    private BufferedWriter  bufferedWriter;

    public IWriter(File file) {
        this.file = file;
    }

    public boolean write(String str) {
        openWriter();
        try {
            if (str.equals("-null")) {
                bufferedWriter.write("");
            } else {
                bufferedWriter.write(str);
                bufferedWriter.flush();
                bufferedWriter.newLine();
            }
            closeWriter();
            return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public void updateFile(File file) {
        if (file == null) throw new NullPointerException("The file can't be null!");
        this.file = file;
        if (isOpen()) {
            closeWriter();
            openWriter();
        }
    }

    private boolean openWriter() {
        if (!isOpen()) {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                return true;
            } catch (IOException e) { e.printStackTrace(); }
        }
        return false;
    }

    private boolean closeWriter() {
        if (isOpen()) {
            try {
                bufferedWriter.close();
                bufferedWriter = null;
                return true;
            } catch (IOException e) { e.printStackTrace(); }
        }
        return false;
    }

    private boolean isOpen() {
        return bufferedWriter != null;
    }
}