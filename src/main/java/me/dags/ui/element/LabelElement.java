package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class LabelElement implements Element<String> {

    private final Style style;
    private final Theme theme;
    private final int column;
    private final int row;
    private final String format;
    private final Element<?> value;
    private int top = 0;
    private int left = 0;
    private int width = 0;
    private int height = 0;

    public LabelElement(Style style, Theme theme, String label, int col, int row, Element<?> value) {
        this.format = label;
        this.value = value;
        this.style = style;
        this.theme = theme;
        this.column = col;
        this.row = row;
    }

    @Override
    public void setPos(int left, int top) {
        this.left = left;
        this.top = top;
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getZLevel() {
        return 1;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getRow() {
        return row;
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

    @Override
    public String getValue() {
        if (value == null) {
            return format;
        }
        return String.format(format, value.getValue());
    }

    @Override
    public String getDefaultValue() {
        if (value == null) {
            return format;
        }
        return String.format(format, value.getDefaultValue());
    }

    @Override
    public void setValue(String s) {

    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        return false;
    }

    @Override
    public void mouseRelease() {

    }

    @Override
    public void draw(RenderContext renderer, int mx, int my) {
        String text = getValue();
        int height = renderer.stringHeight();
        int top = getStyle().getTop(this.top, this.height, height);

        if (text.indexOf('\n') > -1) {
            String[] split = text.split("\\n");
            for (int i = 0; i < split.length; i++) {
                renderString(renderer, split[i], top + i * height);
            }
        } else {
            renderString(renderer, text, top);
        }
    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        return false;
    }

    private void renderString(RenderContext context, String s, int top) {
        int width = context.stringWidth(s);
        int left = getStyle().getLeft(this.left, this.width, width);
        context.drawString(s, left, top, getTheme().label);
    }
}
