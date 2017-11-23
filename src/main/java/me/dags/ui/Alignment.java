package me.dags.ui;

import me.dags.ui.annotation.Section;

/**
 * @author dags <dags@dags.me>
 */
public class Alignment {

    public final float hPad;
    public final float vPad;
    public final Align hAlign;
    public final Align vAlign;

    public Alignment(float hPad, float vPad, Align hAlign, Align vAlign) {
        this.hPad = hPad;
        this.vPad = vPad;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
    }

    public Alignment() {
        this(0F, 0F, Align.CENTER, Align.CENTER);
    }

    public int getWidth(int width) {
        return width - Math.round(width * hPad * 2);
    }

    public int getHeight(int height) {
        return height - Math.round(height * vPad * 2);
    }

    public int getLeft(int left, int parentWidth, int width) {
        int padding = 0;
        if (hAlign == Align.CENTER) {
            return left + padding + (parentWidth - width) / 2;
        }
        if (hAlign == Align.RIGHT) {
            return left + padding + parentWidth - width;
        }
        return left + padding;
    }

    public int getTop(int top, int parentHeight, int height) {
        int padding = 0;
        if (vAlign == Align.CENTER) {
            return top + padding + (parentHeight - height) / 2;
        }
        if (vAlign == Align.BOTTOM) {
            return top + padding + parentHeight - height;
        }
        return top + padding;
    }

    public static Alignment of(Section section) {
        return new Alignment(section.hPad(), section.vPad(), section.hAlign(), section.vAlign());
    }
}
