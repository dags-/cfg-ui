package me.dags.ui.processor;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Label;
import me.dags.ui.element.Element;
import me.dags.ui.element.LabelElement;
import me.dags.ui.layout.Layout;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public interface ElementProcessor<T extends Annotation> {

    Class<T> getType();

    boolean accepts(Field field);

    Layout getPos(T data);

    Element<?> getElement(Field field, T data, Theme theme);

    default LabelElement getLabel(Label label, Element<?> element, Theme parentTheme) {
        int col = label.col();
        int row = label.row();
        Theme theme = new Theme(parentTheme, label.theme());
        Style style = new Style(label.hPad(), label.vPad(), label.hAlign(), label.vAlign());
        return new LabelElement(style, theme, label.format(), col, row, element);
    }
}
