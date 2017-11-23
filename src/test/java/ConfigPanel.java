import me.dags.ui.Theme;
import me.dags.ui.UIMapper;
import me.dags.ui.element.Element;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.RenderContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author dags <dags@dags.me>
 */
public class ConfigPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private final Element<?> element;
    private int mouseX = 0, mouseY = 0;

    public ConfigPanel(UIMapper<?> element) {
        this.element = element;
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics graphics) {
        RenderContext renderer = getRenderer(graphics);
        renderer.fill(0, 0, getWidth(), getHeight(), getBackground().getRGB());
        element.setSize(getWidth(), getHeight());
        element.setPos(0, 0);
        element.draw(renderer, mouseX, mouseY);
        graphics.dispose();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int modifiers = Keys.modifiers(mouseEvent.isControlDown(), mouseEvent.isShiftDown(), mouseEvent.isAltDown());
        element.mousePress(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton(), modifiers);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        element.mouseRelease();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int modifiers = Keys.modifiers(keyEvent.isControlDown(), keyEvent.isShiftDown(), keyEvent.isAltDown());
        element.keyPress(keyEvent.getKeyCode(), keyEvent.getKeyChar(), modifiers);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private static RenderContext getRenderer(Graphics graphics) {
        return new RenderContext() {

            @Override
            public void fill(int left, int top, int width, int height, int color) {
                graphics.setColor(Theme.color(color));
                graphics.fillRect(left, top, width, height);
            }

            @Override
            public void drawString(String s, int left, int top, int color) {
                graphics.setColor(Theme.color(color));
                graphics.drawString(s, left, top + Math.round(stringHeight() * 0.75F));
            }

            @Override
            public int stringHeight() {
                return graphics.getFontMetrics().getHeight();
            }

            @Override
            public int stringWidth(String s) {
                return graphics.getFontMetrics().stringWidth(s);
            }
        };
    }
}
