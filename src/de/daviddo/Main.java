package de.daviddo;

import java.io.IOException;

public class Main  {

    public static void main(String[] args) {
        try {
            new de.daviddo.program.manager.ProgramManager().boot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
