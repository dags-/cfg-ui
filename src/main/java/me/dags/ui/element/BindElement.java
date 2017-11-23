package me.dags.ui.element;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.Platform;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class BindElement extends ValueElement<Integer, Integer> {

    private static final String NONE = "NONE";

    private final Animator highlighter;
    private final String mouse;
    private final String key;

    private String text = NONE;

    public BindElement(Alignment style, Theme theme, String mouse, String key, int column, int row, int defaultVal) {
        super(style, theme, column, row, defaultVal, InputMapper.integer());
        this.highlighter = new Animator(theme.animation);
        this.mouse = mouse;
        this.key = key;
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);

        if (value < 0) {
            int button = -value;
            text = String.format(mouse, button);
        } else if (value > 0) {
            String name = Platform.getInput().keyName(value);
            if (!name.isEmpty()) {
                text = String.format(key, name);
            }
        } else {
            text = NONE;
        }
    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        if (contains(mx, my)) {
            if (isActive()) {
                setValue(-button);
                setActive(false);
                setPressed(false);
            } else {
                setActive(true);
                setPressed(true);
                if (Keys.shift(modifiers)) {
                    setValue(0);
                    setActive(false);
                }
            }
        } else {
            setActive(false);
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

        if (isActive()) {
            highlighter.tick();
            int wid = highlighter.getValue(getWidth());
            int pad = highlighter.getPad(wid, getWidth());
            context.fill(getLeft() + pad, getTop() + getHeight(), wid, 1, getTheme().accent);
        } else {
            highlighter.reset();
        }

        context.fill(getLeft(), getTop(), getWidth(), getHeight(), getTheme().secondary);

        int width = context.stringWidth(text);
        int height = context.stringHeight();
        int left = getStyle().getLeft(getLeft(), getWidth(), width);
        int top = getStyle().getTop(getTop(), getHeight(), height);
        int color = isPressed() ? getTheme().accent : getTheme().text;
        context.drawString(text, left, top, color);
    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        if (isActive()) {
            if (Platform.getInput().isEscape(code)) {
                super.setValue(0);
                text = NONE;
            } else {
                setValue(code);
            }
            setActive(false);
            setPressed(false);
            return true;
        }
        return false;
    }
}
