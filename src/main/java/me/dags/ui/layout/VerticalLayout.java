package me.dags.ui.layout;

import me.dags.ui.Alignment;
import me.dags.ui.Theme;
import me.dags.ui.element.Element;
import me.dags.ui.platform.RenderContext;

/**
 * @author dags <dags@dags.me>
 */
public class VerticalLayout<T> extends BaseLayout<T> {

    private final int cellWidth;
    private final float total;
    private final float[] cellHeights;

    private int[] heights;
    private int[] tops;

    public VerticalLayout(Alignment style, Theme theme, float[] cellHeights, int cellWidth) {
        super(style, theme);
        this.cellWidth = cellWidth;
        this.cellHeights = cellHeights;
        this.heights = new int[cellHeights.length];
        this.tops = new int[cellHeights.length];

        float total = 0F;
        for (float f : cellHeights) {
            total += f;
        }

        this.total = total;
    }

    @Override
    public void draw(RenderContext renderer, int mx, int my) {
        if (getChildren().isEmpty()) {
            return;
        }

        recalculate();
        renderer.fill(getContainerLeft(), getContainerTop(), getContainerWidth(), getContainerHeight(), getTheme().secondary);
        int left = getLeft();
        int columns = 0;

        for (Element<?> e : getChildren()) {
            if (e.getRow() < heights.length) {
                int colLeft = left + e.getColumn() * cellWidth;
                int rowHeight = heights[e.getRow()];
                int rowTop = tops[e.getRow()];
                e.setSize(cellWidth, rowHeight);
                e.setPos(colLeft, rowTop);
                e.draw(renderer, mx, my);
                columns = Math.max(columns, 1 + e.getColumn());
            }
        }

        setSize(columns * cellWidth, getContainerHeight());
    }

    private void recalculate() {
        if (cellHeights.length == 0) {
            if (heights.length < getRows()) {
                heights = new int[getRows()];
                tops = new int[getRows()];
            }

            int top = getTop();
            int height = getHeight() / getRows();
            for (int i = 0; i < getRows(); i++) {
                heights[i] = height;
                tops[i] = top;
                top += height;
            }
        } else {
            if (heights.length < cellHeights.length) {
                heights = new int[cellHeights.length];
                tops = new int[cellHeights.length];
            }

            int top = getTop();
            int height = getHeight();
            for (int i = 0; i < heights.length; i++) {
                float mod = cellHeights[i] / total;
                heights[i] = Math.round(mod * height);
                tops[i] = top;
                top += heights[i];
            }
        }
    }
}
