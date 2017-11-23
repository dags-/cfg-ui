package me.dags.ui.element;

import java.util.function.Function;

/**
 * @author dags <dags@dags.me>
 */
public interface InputMapper<I, T> {

    T map(I input);

    I convert(T value);

    static InputMapper<StringBuilder, String> text() {
        return new InputMapper<StringBuilder, String>() {
            @Override
            public String map(StringBuilder input) {
                return input.toString();
            }

            @Override
            public StringBuilder convert(String value) {
                return new StringBuilder(value);
            }
        };
    }

    static InputMapper<Float, Integer> range(int min, int max) {
        int m0 = Math.min(min, max);
        int m1 = Math.max(min, max);
        float range = m1 - m0;
        return new InputMapper<Float, Integer>() {
            @Override
            public Integer map(Float value) {
                return m0 + Math.round(range * value);
            }

            @Override
            public Float convert(Integer value) {
                return Math.min(1.0F, Math.max(0.0F, (value - m0) / range));
            }
        };
    }

    static InputMapper<Float, Float> range(float min, float max) {
        float m0 = Math.min(min, max);
        float m1 = Math.max(min, max);
        float range = m1 - m0;
        return new InputMapper<Float, Float>() {
            @Override
            public Float map(Float value) {
                return m0 + range * value;
            }

            @Override
            public Float convert(Float value) {
                return Math.min(1.0F, Math.max(0.0F, (value - m0) / range));
            }
        };
    }

    static <T> InputMapper<Integer, T> option(T[] values) {
        return new InputMapper<Integer, T>() {
            @Override
            public T map(Integer input) {
                return values[input];
            }

            @Override
            public Integer convert(T value) {
                for (int i = 0; i < values.length; i++) {
                    T t = values[i];
                    if (t.equals(value)) {
                        return i;
                    }
                }
                return 0;
            }
        };
    }

    static InputMapper<Integer, Integer> integer() {
        return new InputMapper<Integer, Integer>() {
            @Override
            public Integer map(Integer input) {
                return input;
            }

            @Override
            public Integer convert(Integer value) {
                return value;
            }
        };
    }

    static <T extends Number> InputMapper<StringBuilder, T> numberParser(Number def, Function<Number, T> mapper) {
        return new InputMapper<StringBuilder, T>() {
            @Override
            public T map(StringBuilder input) {
                try {
                    double d = Double.parseDouble(input.toString());
                    return mapper.apply(d);
                } catch (NumberFormatException e) {
                    return mapper.apply(def);
                }
            }

            @Override
            public StringBuilder convert(T value) {
                return new StringBuilder().append(value);
            }
        };
    }
}
