package me.dags.ui.element;

import me.dags.ui.Style;
import me.dags.ui.Theme;

/**
 * @author dags <dags@dags.me>
 */
public class ToggleElement extends CycleElement<Boolean> {

    public ToggleElement(Style style, Theme theme, String format, int column, int row) {
        super(style, theme, format, column, row, new Boolean[]{true, false});
    }
}
