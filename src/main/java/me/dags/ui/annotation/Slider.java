package me.dags.ui.annotation;

import me.dags.ui.Align;
import me.dags.ui.Defaults;
import me.dags.ui.layout.Layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dags <dags@dags.me>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Slider {

    float min();

    float max();

    float def() default Float.MIN_VALUE;

    int col() default 0;

    int row() default 0;

    Layout pos() default Layout.CENTER;

    boolean invert() default false;

    boolean vertical() default false;

    float hPad() default Defaults.HPAD;

    float vPad() default Defaults.VPAD;

    Align hAlign() default Align.CENTER;

    Align vAlign() default Align.CENTER;
}
