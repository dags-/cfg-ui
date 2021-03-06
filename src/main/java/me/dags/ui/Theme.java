package me.dags.ui;

import java.awt.*;

/**
 * @author dags <dags@dags.me>
 */
public class Theme {

    public static final Theme DEFAULT = new Theme(me.dags.ui.annotation.Theme.DEFAULT);

    public final int text;
    public final int label;
    public final int primary;
    public final int secondary;
    public final int accent;
    public final int highlight;
    public final Animation animation;

    public Theme(me.dags.ui.annotation.Theme theme) {
        this(theme.text(), theme.label(), theme.primary(), theme.secondary(), theme.accent(), theme.highlight(), theme.animation().inc(), theme.animation().time());
    }

    public Theme(Theme parent, me.dags.ui.annotation.Theme theme) {
        this(parent, theme.text(), theme.label(), theme.primary(), theme.secondary(), theme.accent(), theme.highlight(), theme.animation().inc(), theme.animation().time());
    }

    public Theme(int text, int label, int primary, int secondary, int accent, int highlight, float inc, long time) {
        this.text = text;
        this.label = label;
        this.primary = primary;
        this.secondary = secondary;
        this.accent = accent;
        this.highlight = highlight;
        this.animation = new Animation(inc, time);
    }

    public Theme(Theme parent, int text, int label, int primary, int secondary, int accent, int highlight, float inc, long time) {
        this.text = getValue(text, parent.text);
        this.label = getValue(label, parent.label);
        this.primary = getValue(primary, parent.primary);
        this.secondary = getValue(secondary, parent.secondary);
        this.accent = getValue(accent, parent.accent);
        this.highlight = getValue(highlight, parent.highlight);
        this.animation = getAnimation(inc, time, parent.animation);
    }

    private static int getValue(int in, int parent) {
        return in == me.dags.ui.annotation.Theme.UNDEFINED ? parent : in;
    }

    private static Animation getAnimation(float inc, long time, Animation parent) {
        inc = inc == me.dags.ui.annotation.Animation.DEFAULT.inc() ? parent.increment : inc;
        time = time == me.dags.ui.annotation.Animation.DEFAULT.time() ? parent.interval : time;
        return new Animation(inc, time);
    }

    public static Color color(int in) {
        int a = in >> 24 & 0xFF;
        int r = in >> 16 & 0xFF;
        int g = in >> 8 & 0xFF;
        int b = in & 0xFF;
        return new Color(r, g, b, a);
    }
}
