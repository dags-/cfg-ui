package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.Platform;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class InputElement<T> extends ValueElement<StringBuilder, T> {

    private static final long BLINK = 500L;
    private static final long TOTAL = BLINK * 2;

    private final Animator highlighter = new Animator();
    private long time = 0L;
    private int cursor = 0;

    public InputElement(Style style, Theme theme, int column, int row, T defaultVal, InputMapper<StringBuilder, T> mapper) {
        super(style, theme, column, row, defaultVal, mapper);
        cursor = getRawValue().length() - 1;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        if (contains(mx, my)) {
            setPressed(true);
            setActive(true);
            if (Keys.shift(modifiers)) {
                reset();
                setActive(false);
            } else {
                cursor = getRawValue().length();
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
    public boolean keyPress(int code, char c, int modifiers) {
        if (isActive()) {
            time = System.currentTimeMillis();
            StringBuilder builder = getRawValue();

            if (Platform.getInput().isBackspace(code)) {
                if (builder.length() == 0 || cursor > builder.length()) {
                    return false;
                }

                if (Keys.ctrl(modifiers)) {
                    while (--cursor >= 0) {
                        builder.deleteCharAt(cursor);
                        if (cursor < 1 || builder.charAt(cursor - 1) == ' ') {
                            break;
                        }
                    }
                } else {
                    cursor = Math.max(0, cursor - 1);
                    builder.deleteCharAt(cursor);
                }

                return true;
            }

            if (Platform.getInput().isDelete(code)) {
                if (cursor >= builder.length()) {
                    return false;
                }

                if (Keys.ctrl(modifiers)) {
                    while (cursor < builder.length()) {
                        builder.deleteCharAt(cursor);
                        if (cursor < builder.length() && builder.charAt(cursor) == ' ') {
                            break;
                        }
                    }
                } else {
                    builder.deleteCharAt(cursor);
                }

                return true;
            }

            if (Platform.getInput().isEscape(code) || Platform.getInput().isReturn(code)) {
                setActive(false);
                return true;
            }

            if (Platform.getInput().isLeft(code)) {
                if (Keys.ctrl(modifiers)) {
                    while (--cursor > 0) {
                        if (cursor < 1 || builder.charAt(cursor - 1) == ' ') {
                            break;
                        }
                    }
                } else {
                    cursor = Math.max(0, cursor - 1);
                }
                return true;
            }

            if (Platform.getInput().isRight(code)) {
                if (Keys.ctrl(modifiers)) {
                    while (++cursor < builder.length()) {
                        if (cursor >= builder.length() || builder.charAt(cursor) == ' ') {
                            break;
                        }
                    }
                } else {
                    cursor = Math.min(builder.length(), cursor + 1);
                }
                return true;
            }

            if (Platform.getInput().isUp(code)) {
                cursor = 0;
                return true;
            }

            if (Platform.getInput().isDown(code)) {
                cursor = builder.length();
                return true;
            }

            if (allow(c)) {
                appendChar(c);
                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public void draw(RenderContext context, int mx, int my) {
        drawBackground(context);
        drawText(context);
    }

    @Override
    public void reset() {
        super.reset();
        setCursor(getRawValue().length() - 1);
    }

    public boolean allow(char c) {
        return !Platform.getInput().isUndefined(c);
    }

    private void drawBackground(RenderContext context) {
        if (isActive() || isPressed()) {
            highlighter.tick();
            int wid = highlighter.getValue(getWidth());
            int pad = highlighter.getPad(wid, getWidth());
            context.fill(getLeft() + pad, getTop() + getHeight(), wid, 1, getTheme().accent);
        } else {
            highlighter.reset();
        }
        context.fill(getLeft(), getTop(), getWidth(), getHeight(), getTheme().secondary);
    }

    private void drawText(RenderContext context) {
        if (getRawValue().length() == 0) {
            int height = context.stringHeight();
            int left = getLeft() + getWidth() / 2;
            int top = getStyle().getTop(getTop(), getHeight(), height);
            drawCursor(context, left, top, height);
            return;
        }

        int cursor = getCursor();
        StringBuilder text = new StringBuilder(getRawValue());

        String pre = cursor > 0 ? text.substring(0, cursor) : "";
        int cursorWidth = context.stringWidth(pre);

        int width = context.stringWidth(text.toString());
        int height = context.stringHeight();
        int left = Math.max(getLeft(), getStyle().getLeft(getLeft(), getWidth(), width));
        int top = getStyle().getTop(getTop(), getHeight(), height);
        int right = getLeft() + getWidth();

        while (left + width + 2 >= right && text.length() > 0) {
            if (cursorWidth < width) {
                text.deleteCharAt(text.length() - 1);
            } else {
                text.deleteCharAt(0);
            }
            width = context.stringWidth(text.toString());
            left = getStyle().getLeft(getLeft(), getWidth(), width);
            cursor--;
        }
        // text
        context.drawString(text.toString(), left, top, getTheme().text);

        int cursorLeft = Math.min(right - 4, left + cursorWidth);
        drawCursor(context, cursorLeft, top, height);
    }

    private void drawCursor(RenderContext context, int left, int top, int height) {
        if (isActive()) {
            long time = System.currentTimeMillis();
            if (time - this.time < BLINK) {
                context.fill(left, top, 1, height, getTheme().accent);
            } else if (time - this.time > TOTAL) {
                this.time = time;
            }
        }
    }

    private void appendChar(char c) {
        int cursor = getCursor();
        getRawValue().insert(cursor, c);
        setCursor(++cursor);
    }
}
