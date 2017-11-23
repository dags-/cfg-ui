package me.dags.ui.element;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;

/**
 * @author dags <dags@dags.me>
 */
public class NumberInputElement<T extends Number> extends InputElement<T> {

    private static final StringBuilder DECIMAL_TEST = new StringBuilder("0.5");
    private final boolean decimal;

    public NumberInputElement(Alignment style, Theme theme, int column, int row, T def, InputMapper<StringBuilder, T> mapper) {
        super(style, theme, column, row, def, mapper);
        this.decimal = !mapper.map(DECIMAL_TEST).equals(0);
    }

    @Override
    public boolean allow(char c) {
        return Character.isDigit(c) || (getCursor() == 0 && c == '-') || (decimal && c == '.' && getRawValue().indexOf(".") == -1);
    }
}
