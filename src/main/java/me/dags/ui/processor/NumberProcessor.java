package me.dags.ui.processor;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Num;
import me.dags.ui.element.Element;
import me.dags.ui.element.InputMapper;
import me.dags.ui.element.NumberInputElement;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * @author dags <dags@dags.me>
 */
public class NumberProcessor implements ElementProcessor<Num> {

    @Override
    public Class<Num> getType() {
        return Num.class;
    }

    @Override
    public boolean accepts(Field field) {
        return Number.class.isAssignableFrom(field.getType());
    }

    @Override
    public Layout getPos(Num data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Num number, Theme theme) {
        Style style = new Style(number.hPad(), number.vPad(), number.hAlign(), number.vAlign());
        InputMapper<StringBuilder, Number> mapper = InputMapper.numberParser(number.def(), num(field.getType()));
        return new NumberInputElement<>(style, theme, number.col(), number.row(), number.def(), mapper);
    }

    private Function<Number, Number> num(Class<?> type) {
        if (type == byte.class || type == Byte.class) {
            return Number::byteValue;
        }
        if (type == double.class || type == Double.class) {
            return Number::doubleValue;
        }
        if (type == float.class || type == Float.class) {
            return Number::floatValue;
        }
        if (type == int.class || type == Integer.class) {
            return Number::intValue;
        }
        if (type == long.class || type == Long.class) {
            return Number::longValue;
        }
        return Number::shortValue;
    }
}
