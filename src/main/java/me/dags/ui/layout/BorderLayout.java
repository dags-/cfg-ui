package me.dags.ui.layout;

import me.dags.ui.Alignment;
import me.dags.ui.annotation.Insets;
import me.dags.ui.annotation.Section;
import me.dags.ui.element.Element;
import me.dags.ui.platform.RenderContext;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dags <dags@dags.me>
 */
public class BorderLayout extends BaseLayout {

    private final Insets borders;
    private final BaseLayout<?> header;
    private final BaseLayout<?> footer;
    private final BaseLayout<?> left, center, right;
    private final Map<Layout, BaseLayout<?>> elements = new LinkedHashMap<>();

    public BorderLayout(Insets borders, Section[] sections) {
        super(new Alignment(), me.dags.ui.Theme.DEFAULT);
        this.borders = borders;
        this.header = getLayout(Layout.TOP, sections);
        this.footer = getLayout(Layout.BOTTOM, sections);
        this.left = getLayout(Layout.LEFT, sections);
        this.right = getLayout(Layout.RIGHT, sections);
        this.center = getLayout(Layout.CENTER, sections);
    }

    @Override
    public void draw(RenderContext renderer, int mx, int my) {
        drawHeader(renderer, mx, my);
        drawFooter(renderer, mx, my);
        drawLeft(renderer, mx, my);
        drawRight(renderer, mx, my);
        drawCenter(renderer, mx, my);
    }

    private void drawHeader(RenderContext renderer, int mx, int my) {
        int width = getWidth();
        int height = Math.round(getHeight() * borders.top());
        header.setSize(width, height);
        draw(header, renderer, mx, my, getLeft(), getTop(), width, height);
    }

    private void drawFooter(RenderContext renderer, int mx, int my) {
        int width = getWidth();
        int height = Math.round(getHeight() * borders.bottom());
        int top = getTop() + getHeight() - footer.getContainerHeight();
        footer.setSize(width, height);
        draw(footer, renderer, mx, my, getLeft(), top, width, height);
    }

    private void drawLeft(RenderContext renderer, int mx, int my) {
        int top = getTop() + header.getContainerHeight();
        int width = Math.round(getWidth() * borders.left());
        int height = getHeight() - header.getContainerHeight() - footer.getContainerHeight();
        left.setSize(width, height);
        draw(left, renderer, mx, my, getLeft(), top, width, height);
    }

    private void drawRight(RenderContext renderer, int mx, int my) {
        int width = Math.round(getWidth() * borders.right());
        int height = getHeight() - header.getContainerHeight() - footer.getContainerHeight();
        int left = getLeft() + getWidth() - right.getContainerWidth();
        int top = getTop() + header.getContainerHeight();
        right.setSize(width, height);
        draw(right, renderer, mx, my, left, top, width, height);
    }

    private void drawCenter(RenderContext renderer, int mx, int my) {
        int lef = getLeft() + left.getContainerWidth();
        int top = getTop() + header.getContainerHeight();
        int width = getWidth() - left.getContainerWidth() - right.getContainerWidth();
        int height = getHeight() - header.getContainerHeight() - footer.getContainerHeight();
        center.setSize(width, height);
        draw(center, renderer, mx, my, lef, top);
    }

    private void draw(BaseLayout layout, RenderContext renderer, int mx, int my, int left, int top, int width, int height) {
        if (layout.getChildren().isEmpty()) {
            width = Math.round(width);
            height = Math.round(height);
            layout.setSize(width, height);
        }
        layout.setSize(width, height);
        draw(layout, renderer, mx, my, left, top);
    }

    private void draw(BaseLayout layout, RenderContext renderer, int mx, int my, int left, int top) {
        draw(layout, renderer, mx, my, left, top, getTheme().primary);
    }

    private void draw(BaseLayout layout, RenderContext renderer, int mx, int my, int left, int top, int color) {
        layout.setPos(left, top);
        layout.draw(renderer, mx, my);
    }

    public BaseLayout<?> getLayout(Layout type) {
        return elements.get(type);
    }

    @Override
    public Collection<? extends Element<?>> getChildren() {
        return elements.values();
    }

    private BaseLayout getLayout(Layout pos, Section[] sections) {
        for (Section section : sections) {
            if (section.pos() != pos) {
                continue;
            }

            Alignment style = Alignment.of(section);
            me.dags.ui.Theme theme = new me.dags.ui.Theme(section.theme());
            BaseLayout layout = newLayout(section.vert(), style, theme, section.size(), section.grid());
            elements.put(pos, layout);
            return layout;
        }

        boolean vertical = pos == Layout.LEFT || pos == Layout.RIGHT;
        BaseLayout layout = newLayout(vertical, new Alignment(), me.dags.ui.Theme.DEFAULT, BaseLayout.DEFAULT_CELL_SIZE);
        elements.put(pos, layout);
        return layout;
    }

    private BaseLayout newLayout(boolean vertical, Alignment style, me.dags.ui.Theme theme, int cellSize, float... grid) {
        if (vertical) {
            return new VerticalLayout(style, theme, grid, cellSize);
        }
        return new HorizontalLayout(style, theme, grid, cellSize);
    }
}
