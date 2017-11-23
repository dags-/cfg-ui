package me.dags.ui.annotation;

import me.dags.ui.Align;
import me.dags.ui.Defaults;
import me.dags.ui.layout.BaseLayout;
import me.dags.ui.layout.Layout;

import java.lang.annotation.*;

/**
 * @author dags <dags@dags.me>
 */
@Repeatable(Sections.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Section {

    Layout pos();

    boolean vert() default false;

    float[] grid() default 1.0F;

    int size() default BaseLayout.DEFAULT_CELL_SIZE;

    float hPad() default Defaults.HPAD;

    float vPad() default Defaults.VPAD;

    Align hAlign() default Align.CENTER;

    Align vAlign() default Align.CENTER;

    Theme theme() default @Theme(secondary = 0xCC000000);
}
