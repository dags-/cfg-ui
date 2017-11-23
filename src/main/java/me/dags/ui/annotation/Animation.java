package me.dags.ui.annotation;

import java.lang.annotation.*;

/**
 * @author dags <dags@dags.me>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Animation {

    float inc() default 0.05F;

    long time() default 2L;

    Animation DEFAULT = new Animation() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return Animation.class;
        }

        @Override
        public float inc() {
            return 0.05F;
        }

        @Override
        public long time() {
            return 2L;
        }
    };
}
