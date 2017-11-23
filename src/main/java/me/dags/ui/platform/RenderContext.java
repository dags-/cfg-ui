package me.dags.ui.platform;

/**
 * @author dags <dags@dags.me>
 */
public interface RenderContext {

    default void outline(int left, int top, int width, int height, int color) {
        fill(left, top, width, 1, color);
        fill(left, top + height, width, 1, color);
        fill(left, top, 1, height, color);
        fill(left + width, top, 1, height, color);
    }

    void fill(int left, int top, int width, int height, int color);

    void drawString(String s, int left, int top, int color);

    int stringHeight();

    int stringWidth(String s);
}
