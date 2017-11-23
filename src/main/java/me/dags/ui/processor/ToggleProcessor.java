package me.dags.ui.processor;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Toggle;
import me.dags.ui.element.Element;
import me.dags.ui.element.ToggleElement;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public class ToggleProcessor implements ElementProcessor<Toggle> {

    @Override
    public Class<Toggle> getType() {
        return Toggle.class;
    }

    @Override
    public boolean accepts(Field field) {
        return field.getType() == boolean.class || field.getType() == Boolean.class;
    }

    @Override
    public Layout getPos(Toggle data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Toggle data, Theme theme) {
        Alignment style = new Alignment(data.hPad(), data.vPad(), data.hAlign(), data.vAlign());
        return new ToggleElement(style, theme, data.format(), data.col(), data.row());
    }
}
