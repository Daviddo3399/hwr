package de.daviddo.utils.logging;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 *
 * @author  Daviddo3399
 * @since   20. Januar 2015
 */
public class LogWindow extends JFrame {

    private JScrollPane scrollPane;
    private JTextArea   textArea;

    public LogWindow(int w, int h) {
        setSize(w, h);

        textArea    = new JTextArea();
        scrollPane  = new JScrollPane(textArea);

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);

        getContentPane().add(scrollPane);
        setVisible(true);
    }

    public void write(String s) {
        textArea.append(s + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
        getContentPane().validate();
    }
}
