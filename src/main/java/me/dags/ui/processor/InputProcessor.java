package me.dags.ui.processor;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Input;
import me.dags.ui.element.Element;
import me.dags.ui.element.InputElement;
import me.dags.ui.element.InputMapper;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public class InputProcessor implements ElementProcessor<Input> {

    @Override
    public Class<Input> getType() {
        return Input.class;
    }

    @Override
    public boolean accepts(Field field) {
        return field.getType() == String.class;
    }

    @Override
    public Layout getPos(Input data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Input data, Theme theme) {
        Alignment style = new Alignment(data.hPad(), data.vPad(), data.hAlign(), data.vAlign());
        return new InputElement<>(style, theme, data.col(), data.row(), "", InputMapper.text());
    }
}
