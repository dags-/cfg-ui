package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class CycleElement<T> extends ValueElement<Integer, T> {

    private final Animator highlighter = new Animator();
    private final int length;
    private final String format;

    public CycleElement(Style style, Theme theme, String format, int column, int row, T[] values) {
        super(style, theme, column, row, values[0], InputMapper.option(values));
        this.format = format;
        this.length = values.length;
    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        if (contains(mx, my)) {
            setPressed(true);
            if (Keys.shift(modifiers)) {
                reset();
            } else {
                T next = getMapper().map(nextIndex());
                setValue(next);
            }
        } else {
            setPressed(false);
        }
        return isActive();
    }

    @Override
    public void mouseRelease() {
        setPressed(false);
    }

    @Override
    public void draw(RenderContext context, int mx, int my) {
        super.draw(context, mx, my);

        if (isPressed()) {
            highlighter.tick();
            int wid = highlighter.getValue(getWidth());
            int pad = highlighter.getPad(wid, getWidth());
            context.fill(getLeft() + pad, getTop() + getHeight(), wid, 1, getTheme().accent);
        } else {
            highlighter.reset();
        }
        // fill
        context.fill(getLeft(), getTop(), getWidth(), getHeight(), getTheme().secondary);

        // text
        String text = String.format(format, getValue());
        int width = context.stringWidth(text);
        int height = context.stringHeight();
        int left = getStyle().getLeft(getLeft(), getWidth(), width);
        int top = getStyle().getTop(getTop(), getHeight(), height);
        int color = isPressed() ? getTheme().accent : getTheme().text;
        context.drawString(text, left, top, color);
    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        return false;
    }

    private int nextIndex() {
        int state = getRawValue();
        if (state + 1 < length) {
            return state + 1;
        }
        return 0;
    }
}
