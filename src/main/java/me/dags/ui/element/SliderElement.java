package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.platform.Keys;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class SliderElement<T> extends ValueElement<Float, T> {

    private final int slidePadX;
    private final int slidePadY;
    private final boolean vertical;
    private final boolean inverted;
    private final boolean showTick;

    private int slideLeft;
    private int slideTop;
    private int slideWidth;
    private int slideHeight;

    public SliderElement(Style style, Theme theme, int column, int row, boolean showTick, boolean vertical, boolean invert, T defaultVal, InputMapper<Float, T> mapper) {
        super(style, theme, column, row, defaultVal, mapper);
        slidePadX = 4;
        slidePadY = 2;
        this.vertical = vertical;
        this.inverted = (vertical && !invert) || invert;
        this.showTick = showTick;
    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        return false;
    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        if (contains(mx, my)) {
            setActive(true);
            setPressed(true);
            if (Keys.shift(modifiers)) {
                reset();
                setActive(false);
            } else if (!slideContains(mx, my)) {
                updateSlidePos(mx, my);
            }
        } else {
            setActive(false);
        }
        return isActive();
    }

    @Override
    public void mouseRelease() {
        setActive(false);
        setPressed(false);
    }

    @Override
    public void draw(RenderContext renderer, int mx, int my) {
        super.draw(renderer, mx, my);

        updateSlidePos(mx, my);
        updateSlideBounds();

        if (isPressed()) {
            renderer.outline(getLeft() - 1, getTop() - 1, getWidth() + 1, getHeight() + 1, getTheme().highlight);
        }

        // fill
        renderer.fill(getLeft(), getTop(), getWidth(), getHeight(), getTheme().secondary);
        // ticks
        drawTicks(renderer);

        // slider
        int color = isPressed() ? getTheme().accent : getTheme().primary;
        renderer.fill(slideLeft, slideTop, slideWidth, slideHeight, color);
    }

    private void updateSlidePos(int mx, int my) {
        if (isActive()) {
            int min = vertical ? getTop() + slidePadY : getLeft() + slidePadX;
            int max = vertical ? getTop() + getHeight() - slidePadY : getLeft() + getWidth() - slidePadX;
            int mouse = vertical ? my : mx;
            float length = max - min;
            float position = pos((mouse - min) / length, inverted);
            T value = getMapper().map(position);
            setValue(value);
        }
    }

    private void updateSlideBounds() {
        slideWidth = vertical ? getWidth() + (2 * slidePadY) : slidePadX * 2;
        slideHeight = vertical ? slidePadX * 2 : getHeight() + slidePadY * 2;

        int pos = getPos(getRawValue());
        slideTop = getSlideTop(pos);
        slideLeft = getSlideLeft(pos);
    }

    private int getPos(float value) {
        int length = vertical ? getHeight() - slideHeight : getWidth() - slideWidth;
        return Math.round(pos(value, inverted) * length);
    }

    private int getSlideTop(int pos) {
        return vertical ? getTop() + pos : getTop() - slidePadY;
    }

    private int getSlideLeft(int pos) {
        return vertical ? getLeft() - slidePadY : getLeft() + pos;
    }

    private void drawTicks(RenderContext renderer) {
        if (showTick && getDefaultValue() != null) {
            float value = getMapper().convert(getDefaultValue());
            int pos = getPos(value);

            int left0 = getSlideLeft(pos);
            int top0 = getSlideTop(pos);

            int width = vertical ? (slideWidth - getWidth()) / 2 : slideWidth;
            int height = vertical ? slideHeight : (slideHeight - getHeight()) / 2;

            int left1 = vertical ? left0 + slideWidth - width : left0;
            int top1 = vertical ? top0 : top0 + slideHeight - height;

            renderer.fill(left0, top0, width, height, getTheme().secondary);
            renderer.fill(left1, top1, width, height, getTheme().secondary);

            if (isActive()) {
                renderer.outline(left0 - 1, top0 - 1, slideWidth + 1, slideHeight + 1, getTheme().highlight);
            }
        }
    }

    private boolean slideContains(int x, int y) {
        return x >= slideLeft && x <= slideLeft + slideWidth && y >= slideTop && y <= slideTop + slideHeight;
    }

    private static float pos(float position, boolean invert) {
        return invert ? 1F - position : position;
    }
}
