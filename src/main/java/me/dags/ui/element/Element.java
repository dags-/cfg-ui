package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public interface Element<T> {

    void setPos(int left, int top);

    void setSize(int width, int height);

    int getColumn();

    int getRow();

    int getLeft();

    int getTop();

    int getWidth();

    int getHeight();

    default int getZLevel() {
        return 0;
    }

    default boolean isActive() {
        return false;
    }

    default boolean contains(int x, int y) {
        return false;
    }

    default void setActive(boolean active) {

    }

    Style getStyle();

    default Theme getTheme() {
        return Theme.DEFAULT;
    }

    T getValue();

    T getDefaultValue();

    void setValue(T t);

    boolean mousePress(int mx, int my, int button, int modifiers);

    void mouseRelease();

    void draw(RenderContext context, int mx, int my);

    boolean keyPress(int code, char c, int modifiers);

    default void reset() {
        if (getDefaultValue() != null) {
            setValue(getDefaultValue());
        }
    }
}
