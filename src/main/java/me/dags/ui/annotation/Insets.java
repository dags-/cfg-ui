package me.dags.ui.annotation;

import java.lang.annotation.*;

/**
 * @author dags <dags@dags.me>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Insets {

    float left() default 0.2F;
    float right() default 0.2F;
    float top() default 0.1F;
    float bottom() default 0.1F;

    Insets DEFAULT = new Insets() {
        @Override
        public Class<? extends Annotation> annotationType() {
            return Insets.class;
        }

        @Override
        public float left() {
            return 0.2F;
        }

        @Override
        public float right() {
            return 0.2F;
        }

        @Override
        public float top() {
            return 0.1F;
        }

        @Override
        public float bottom() {
            return 0.1F;
        }
    };
}
