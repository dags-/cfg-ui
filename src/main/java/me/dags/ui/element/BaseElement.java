package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public abstract class BaseElement<T> implements Element<T> {

    private final Style style;
    private final Theme theme;
    private final int column;
    private final int row;
    private boolean pressed;
    private boolean active;
    private int left;
    private int top;
    private int width;
    private int height;
    private int containerLeft;
    private int containerTop;
    private int containerWidth;
    private int containerHeight;

    public BaseElement(Style style, Theme theme, int column, int row) {
        this.style = style;
        this.theme = theme;
        this.column = column;
        this.row = row;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    public int getContainerLeft() {
        return containerLeft;
    }

    public int getContainerTop() {
        return containerTop;
    }

    public int getContainerWidth() {
        return containerWidth;
    }

    public int getContainerHeight() {
        return containerHeight;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void setPos(int left, int top) {
        this.containerLeft = left;
        this.containerTop = top;
        this.left = getStyle().getLeft(left, getContainerWidth(), getWidth());
        this.top = getStyle().getTop(top, getContainerHeight(), getHeight());
    }

    @Override
    public void setSize(int width, int height) {
        this.containerWidth = width;
        this.containerHeight = height;
        this.width = getStyle().getWidth(width);
        this.height = getStyle().getHeight(height);
    }

    @Override
    public void draw(RenderContext context, int mx, int my) {
//        context.outline(getContainerLeft() - 1, getContainerTop() - 1, getContainerWidth(), getContainerHeight(), getTheme().accent);
    }

    public boolean contains(int x, int y) {
        return x >= getLeft() && x <= getLeft() + getWidth() && y >= getTop() && y <= getTop() + getHeight();
    }
}
