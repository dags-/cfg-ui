package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;

/**
 * @author dags <dags@dags.me>
 */
public abstract class ValueElement<I, T> extends BaseElement<T> {

    private final T defaultVal;
    private final InputMapper<I, T> mapper;

    private I value;

    public ValueElement(Style style, Theme theme, int column, int row, T defaultVal, InputMapper<I, T> mapper) {
        super(style, theme, column, row);
        this.value = mapper.convert(defaultVal);
        this.defaultVal = mapper.map(mapper.convert(defaultVal));
        this.mapper = mapper;
    }

    @Override
    public T getValue() {
        return mapper.map(getRawValue());
    }

    @Override
    public T getDefaultValue() {
        return defaultVal;
    }

    public I getRawValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        try {
            this.value = mapper.convert(value);
        } catch (Exception ignored) {
        }
    }

    protected InputMapper<I, T> getMapper() {
        return mapper;
    }
}
