package me.dags.ui.layout;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.element.BaseElement;
import me.dags.ui.element.Element;

import java.util.*;

/**
 * @author dags <dags@dags.me>
 */
public abstract class BaseLayout<T> extends BaseElement<T> {

    public static final int DEFAULT_CELL_SIZE = 40;

    private final List<Element<?>> elements = new LinkedList<>();
    private boolean dirty = true;
    private int columns = 0;
    private int rows = 0;

    public BaseLayout(Alignment style, Theme theme) {
        super(style, theme, 0, 0);
    }

    @Override
    public T getValue() {
        return null;
    }

    @Override
    public T getDefaultValue() {
        return null;
    }

    @Override
    public void setValue(Object o) {

    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        for (Element<?> el : getChildren()) {
            if (el.keyPress(code, c, modifiers)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        boolean result = false;
        for (Element<?> e : getChildren()) {
            result |= e.mousePress(mx, my, button, modifiers);
        }
        return result;
    }

    @Override
    public void mouseRelease() {
        for (Element<?> el : getChildren()) {
            el.mouseRelease();
        }
    }

    public int getColumns() {
        return Math.max(1, columns);
    }

    public int getRows() {
        return Math.max(1, rows);
    }

    public void add(Element<?> element) {
        elements.add(element);
        columns = Math.max(columns, element.getColumn() + 1);
        rows = Math.max(rows, element.getRow() + 1);
        dirty = true;
    }

    public Collection<Element<?>> getChildren() {
        if (dirty) {
            dirty = false;
            Collections.sort(elements, Comparator.comparing(Element::getZLevel));
        }
        return elements;
    }
}
