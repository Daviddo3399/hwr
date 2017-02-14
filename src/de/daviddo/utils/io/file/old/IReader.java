package de.daviddo.utils.io.file.old;

import java.io.*;

/**
 *
 * @author      Daviddo3399
 * @since	    4. Januar 2015
 * @deprecated
 */
public class IReader {

    private File            file;

    private FileInputStream fileInputStream;
    private BufferedReader  bufferedReader;

    public IReader(File file) {
        this.file = file;
    }


    public String readFileContent() {
        openReader();

        StringBuffer fileContents = new StringBuffer();
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.append(line);
            }
            return fileContents.toString();
        } catch (IOException e) { e.printStackTrace(); }

        closeReader();
        return "No File content found!";
    }

    public String readLine() throws IOException {
        closeReader();
        openReader();
        return bufferedReader.readLine();
    }

    public void updateFile(File file) {
        if (file == null) throw new NullPointerException("The file can't be null!");
        this.file = file;
        if (isOpen()) {
            closeReader();
            openReader();
        }
    }

    private boolean openReader() {
        if (isOpen()) {
            try {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                return true;
            } catch (IOException e) { e.printStackTrace(); }
        }
        return false;
    }

    private boolean closeReader() {
        if (isOpen()) {
            try {
                fileInputStream.close();
                bufferedReader.close();
                return true;
            } catch (IOException e) { e.printStackTrace(); }
        }
        return false;
    }

    private boolean isOpen() {
        return bufferedReader != null && fileInputStream != null;
    }
}
