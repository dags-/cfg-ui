package me.dags.ui.layout;

import me.dags.ui.Style;
import me.dags.ui.Theme;
import me.dags.ui.element.Element;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class HorizontalLayout<T> extends BaseLayout<T> {

    private final int cellHeight;
    private final float[] cellWidths;

    private int[] widths;
    private int[] lefts;

    HorizontalLayout(Style style, Theme theme, float[] cellWidths, int cellHeight) {
        super(style, theme);
        this.cellHeight = cellHeight;
        this.cellWidths = cellWidths;
        this.widths = new int[cellWidths.length];
        this.lefts = new int[cellWidths.length];
    }

    @Override
    public void draw(RenderContext renderer, int mx, int my) {
        if (getChildren().isEmpty()) {
            return;
        }

        recalculate();

        renderer.fill(getContainerLeft(), getContainerTop(), getContainerWidth(), getContainerHeight(), getTheme().secondary);

        int rows = 0;
        for (Element<?> e : getChildren()) {
            if (e.getColumn() < widths.length) {
                int rowTop = getTop() + e.getRow() * cellHeight;
                int cellWidth = widths[e.getColumn()];
                int cellLeft = lefts[e.getColumn()];
                e.setSize(cellWidth, cellHeight);
                e.setPos(cellLeft, rowTop);
                e.draw(renderer, mx, my);
                rows = Math.max(rows, 1 + e.getRow());
            }
        }

        setSize(getContainerWidth(), rows * cellHeight);
    }

    private void recalculate() {
        if (cellWidths.length == 0) {
            if (widths.length != getColumns()) {
                widths = new int[getColumns()];
                lefts = new int[getColumns()];
            }

            int left = getLeft();
            int width = getWidth() / getColumns();
            for (int i = 0; i < getColumns(); i++) {
                widths[i] = width;
                lefts[i] = left;
                left += width;
            }
        } else {
            if (widths.length != cellWidths.length) {
                widths = new int[cellWidths.length];
                lefts = new int[cellWidths.length];
            }

            float total = 0F;
            for (float f : cellWidths) {
                total += f;
            }

            int left = getLeft();
            int width = getWidth();
            for (int i = 0; i < widths.length; i++) {
                float mod = cellWidths[i] / total;
                widths[i] = Math.round(mod * width);
                lefts[i] = left;
                left += widths[i];
            }
        }
    }
}
