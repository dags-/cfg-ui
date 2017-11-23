package me.dags.ui.processor;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.annotation.Slider;
import me.dags.ui.element.Element;
import me.dags.ui.element.InputMapper;
import me.dags.ui.element.SliderElement;
import me.dags.ui.layout.Layout;

import java.lang.reflect.Field;

/**
 * @author dags <dags@dags.me>
 */
public class SliderProcessor implements ElementProcessor<Slider> {

    @Override
    public Class<Slider> getType() {
        return Slider.class;
    }

    @Override
    public boolean accepts(Field field) {
        if (field.getType().isPrimitive()) {
            return field.getType() == int.class || field.getType() == float.class;
        }
        return field.getType() == Integer.class || field.getType() == Float.class;
    }

    @Override
    public Layout getPos(Slider data) {
        return data.pos();
    }

    @Override
    public Element<?> getElement(Field field, Slider data, Theme theme) {
        Alignment style = new Alignment(data.hPad(), data.vPad(), data.hAlign(), data.vAlign());
        return newSlider(field.getType(), data, style, theme);
    }

    private SliderElement<?> newSlider(Class<?> type, Slider slider, Alignment style, me.dags.ui.Theme theme) {
        int col = slider.col();
        int row = slider.row();
        boolean vertical = slider.vertical();
        boolean invert = slider.invert();
        boolean showTick = slider.def() != Float.MIN_VALUE;
        float def = showTick ? slider.def() : slider.min();
        if (type == int.class || type == Integer.class) {
            int min = (int) slider.min();
            int max = (int) slider.max();
            return new SliderElement<>(style, theme, col, row, showTick, vertical, invert, (int) def, InputMapper.range(min, max));
        }
        return new SliderElement<>(style, theme, col, row, showTick, vertical, invert, def, InputMapper.range(slider.min(), slider.max()));
    }
}
