package me.dags.ui.processor;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Bind;
import me.dags.ui.element.BindElement;
import me.dags.ui.element.Element;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public class BindProcessor implements ElementProcessor<Bind> {

    @Override
    public Class<Bind> getType() {
        return Bind.class;
    }

    @Override
    public boolean accepts(Field field) {
        return field.getType() == int.class || field.getType() == Integer.class;
    }

    @Override
    public Layout getPos(Bind data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Bind data, Theme theme) {
        Style style = new Style(data.hPad(), data.vPad(), data.hAlign(), data.vAlign());
        return new BindElement(style, theme, data.mouse(), data.key(), data.col(), data.row(), data.def());
    }
}
