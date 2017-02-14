package de.daviddo.utils.gui;


import de.daviddo.utils.Messages;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * @author  Daviddo3399
 */
public class DrawablePane extends JPanel implements MouseMotionListener, MouseListener {

    private ArrayList<Pixel> pixels;
    private ArrayList<Pixel> activePixels;

    private int height;
    private int width;
    private int count;

    private boolean drawAble;

    public DrawablePane(int height, int width, int count) {
        this(height, width, count, true);
    }

    /**
     * Initialisiert alle nötigen Informationen für das Zeichenfeld und erstellt
     * vorab schon einmale alle wichtigen Parameter für die Pixel.
     *
     * @param height    Die Höhe des Zeichenfelds
     * @param width     Die Breite des Zeichenfelds
     * @param count     Die Anzahl der Pixel
     * @param drawAble  Der Zustand des Zeichenfelds. Ist das Zeichenfeld aktiviert
     *                  kann gezeichnet werden, ansonsten nicht
     */
    public DrawablePane(int height, int width, int count, boolean drawAble) {
        this.height     = height;
        this.width      = width;
        this.count      = count;
        this.drawAble   = drawAble;

        pixels          = new ArrayList<>();

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);

        createPixels();

        addMouseMotionListener(this);
        addMouseListener(this);
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);

        drawGrid(graphics);
        fillPixel(graphics);
    }

    /**
     * Generiert alle wichtigen Parameter für die einzelnen Pixel in abhänigkeit
     * von der Anzahl der Pixel die sich später in dem Zeichenfeld befinden sollen.
     */
    private void createPixels() {
        for (int a = 0; a < count; a++) {
            for (int b = 0; b < count; b++) {

                int x = a * (width / count);
                int y = b * (height / count);
                int h = height / count;
                int w = width / count;

                pixels.add(new Pixel(x, y, h, w));
            }
        }
        repaint();
    }

    /**
     * Zeichnet die Umrisse jedes Pixels. Diese Umrisse ergeben dann das gesamt
     * Gitter und somit auch das gesamte Zeichenfeld.
     *
     * @param graphics
     */
    private void drawGrid(Graphics graphics) {
        graphics.setColor(Color.WHITE);

        pixels.forEach(pixel -> graphics.drawLine(0, pixel.getY(), width, pixel.getY()));
        pixels.forEach(pixel -> graphics.drawLine(pixel.getX(), 0, pixel.getX(), height));
        repaint();
    }

    /**
     * Mit dieser Methode werden alle aktivierten Pixel "gefüllt".
     *
     * @param graphics
     */
    private void fillPixel(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);

        pixels.stream().filter(pixel -> pixel.getState()).forEach(pixel ->
                graphics.fillRect(pixel.getX(), pixel.getY(), pixel.getWidth(), pixel.getHeight()));
    }

    /**
     * Gibt die gesamte Zeichenfläch als ArrayList aus. Jeder Pixel wird auf seinen
     * Zustand überpfrüft. Ist beispielsweise ein Pixel deaktiviert, so wird für
     * ihn eine 0 in die ArrayList eingetragen. Ist er aktiviert, so wird für ihn
     * eine 1 in die ArrayList eingetragen.
     * Diese ArrayList wird später von dem künstlich neuronalen Netz als Inputs
     * genutz.
     *
     * @return Das gesamte Zeichenfeld als ArrayList
     */
    public ArrayList<Integer> getPixelsAsList() {
        ArrayList<Integer> pixelsAsList = new ArrayList<>();

        pixels.forEach(
                pixel -> {
                    if (pixel.getState()) {
                        pixelsAsList.add(1);
                    } else {
                        pixelsAsList.add(0);
                    }
                }
        );
        return  pixelsAsList;
    }

    /**
     * Diese Methode überprüft, ob das Zeichenfeld komplette leer ist oder mit
     * aktivierten Pixeln gefüllt.
     *
     * @return  true oder false, jenachdem ob das Zeichenfeld leer oder gefüllt
     *          ist.
     */
    public boolean isPanelClear() {
        return !getPixelsAsList().contains(1);
    }

    /**
     * Deaktiviert alle Pixel des Zeichenfelds.
     */
    public void clearPane() {
        pixels.forEach(pixel -> pixel.setState(false));
        repaint();
        Logger.log(Messages.PANE_CLEARED, LoggerLevel.INFO);
    }

    private void action(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            pixels.stream().filter(pixel -> pixel.isMouseOnPixel(e.getX(), e.getY())).forEach(pixel -> {
                if (!pixel.getState()) {
                    pixel.setState(true);
                    Logger.log(Messages.PANE_PIXEL_PAINTED.replace("%x", String.valueOf(pixel.getX())).replace("%y", String.valueOf(pixel.getY())), LoggerLevel.INFO);
                }
            });

        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (e.getClickCount() == 2) {
                clearPane();
            } else {
                pixels.stream().filter(pixel -> pixel.isMouseOnPixel(e.getX(), e.getY())).forEach(pixel -> {
                    if (pixel.getState())  {
                        pixel.setState(false);
                        Logger.log(Messages.PANE_PIXEL_REMOVED.replace("%x", String.valueOf(pixel.getX())).replace("%y", String.valueOf(pixel.getY())), LoggerLevel.INFO);

                    }
                });
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        action(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        action(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { action(e); }

    @Override
    public void mouseMoved(MouseEvent e) { }

    /**
     *
     * @author Daviddo3399
     */
    private class Pixel {

        private Integer x;
        private Integer y;
        private Integer height;
        private Integer width;

        private Boolean state;

        /**
         * Initialisiert alle nötigen Informationen, welche für den Pixel vorliegen.
         *
         * @param x         Die X-Koordinate
         * @param y         Die Y-Koordinate
         * @param height    Die Höhe des Pixels
         * @param width     Die Breite des Pixels
         */
        public Pixel(Integer x, Integer y, Integer height, Integer width) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;

            this.state = false;
        }

        /**
         * Diese Methode überprüft, ob die Maus sich innerhalb eines bestimmten
         * Pixel befindet. Dies wird anhand der Maus Koordinaten analysiert.
         *
         * @param mouseCords    Die X- und Y-Koordinate der Maus
         *
         * @return              true oder false, jenachdem ob die Maus sich innerhalb
         *                      des Pixels befindet oder nicht.
         */
        public boolean isMouseOnPixel(int[] mouseCords) {
            return isMouseOnPixel(mouseCords[0], mouseCords[1]);
        }

        /**
         * Diese Methode überprüft, ob die Maus sich innerhalb eines bestimmten
         * Pixel befindet. Dies wird anhand der Maus Koordinaten analysiert
         *
         * Zuerst wird überprüft, ob sich die Maus zwischen der X-Koordinate x1 und
         * der X-Koordinate x2 befindet. x2 stellt sich hierbei aus der Breite des
         * Pixels und der Koordinate x1 zusammen.
         *
         * Danach wird das gleiche Verfahren noch für die Y-Koordinate eingesetzt.
         * Stimmen beide Erwartungen, dann ist die Maus inerhalb eines Pixels.
         *
         * Ist die Maus jedoch auf dem Rand des Pixels oder berührt sie beispielsweise
         * die x-Koordinate x1 oder x2 exakt, so wird dies trotzdem nicht gewertet,
         * da beispielsweise an der Ursprungskoordinate x1 des Pixels ein anderer endet.
         * So können fehler wie z.B. doppelet aktivierte Pixel vermieden werden.
         *
         * @param mouseX    Die X-Koordinate der Maus
         * @param mouseY    Die Y-Koordinate der Maus
         *
         * @return          true oder false, jenachdem ob die Maus sich innerhalb des
         *                  Pixels befindet oder nicht.
         */
        public boolean isMouseOnPixel(int mouseX, int mouseY) {
            int w       = width + x;
            int h       = height + y;

            if ((mouseX < (width + x) && mouseX > x) && (mouseY < (height + y) && mouseY > y)) {
                return true;
            }
            return false;
        }


        /**
         * Setzt den Zustand des Pixels.
         *
         * @param active true oder false, jenachdem ob der Pixel aktiviert ist oder
         *               nicht.
         */
        public void setState(Boolean active) {
            this.state = active;
        }

        /**
         * Die x-Koordinate des Pixels als ein Integer.
         *
         * @return die x-Koordinate
         */
        public Integer getX() {
            return x;
        }

        /**
         * Die y-Koordinate des Pixels als ein Integer.
         *
         * @return die y-Koordinate
         */
        public Integer getY() {
            return y;
        }

        /**
         * Die Höhe des Pixels als ein Integer.
         *
         * @return die Höhe
         */
        public Integer getHeight() {
            return height;
        }

        /**
         * Die Breite des Pixels als ein Integer.
         *
         * @return die Breite
         */
        public Integer getWidth() {
            return width;
        }

        /**
         * Gibt den Zustand des Pixels zurück.
         *
         * @return  true oder false, jenachdem ob der de.daviddo.utils.gui.Pixel
         *          aktiviert ist oder nicht.
         */
        public Boolean getState() {
            return state;
        }
    }
}
