package me.dags.ui.annotation;

import java.lang.annotation.*;

/**
 * @author dags <dags@dags.me>
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Theme {

    int UNDEFINED = Integer.MAX_VALUE;

    int text() default UNDEFINED;

    int label() default UNDEFINED;

    int primary() default UNDEFINED;

    int secondary() default UNDEFINED;

    int accent() default UNDEFINED;

    int highlight() default UNDEFINED;

    Animation animation() default @Animation;

    Theme DEFAULT = new Theme() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return Theme.class;
        }

        @Override
        public int text() {
            return 0xFF525252;
        }

        @Override
        public int label() {
            return 0xFF212121;
        }

        @Override
        public int primary() {
            return 0xFF848484;
        }

        @Override
        public int secondary() {
            return 0xFFD5D5D5;
        }

        @Override
        public int accent() {
            return 0xFFC2185B;
        }

        @Override
        public int highlight() {
            return 0x66C2185B;
        }

        @Override
        public Animation animation() {
            return Animation.DEFAULT;
        }
    };
}
