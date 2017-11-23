package me.dags.ui.processor;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Cycle;
import me.dags.ui.element.CycleElement;
import me.dags.ui.element.Element;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public class CycleProcessor implements ElementProcessor<Cycle> {

    @Override
    public Class<Cycle> getType() {
        return Cycle.class;
    }

    @Override
    public boolean accepts(Field field) {
        return field.getType().isEnum();
    }

    @Override
    public Layout getPos(Cycle data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Cycle data, Theme theme) {
        Object[] values = field.getType().getEnumConstants();
        Alignment style = new Alignment(data.hPad(), data.vPad(), data.hAlign(), data.vAlign());
        return new CycleElement<>(style, theme, data.format(), data.col(), data.row(), values);
    }
}
