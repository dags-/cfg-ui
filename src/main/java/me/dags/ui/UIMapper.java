package me.dags.ui;

import me.dags.config.Mapper;
import me.dags.ui.element.BaseElement;
import me.dags.ui.element.Element;
import me.dags.ui.platform.RenderContext;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author dags <dags@dags.me>
 */
public class UIMapper<T> extends BaseElement<Object> {

    private final Element<?> root;
    private final Mapper<T> mapper;
    private final Map<String[], Element<?>> mappings;

    UIMapper(Mapper<T> mapper, Element<?> root, Map<String[], Element<?>> mappings) {
        super(new Style(), Theme.DEFAULT, 0, 0);
        this.mapper = mapper;
        this.root = root;
        this.mappings = Collections.unmodifiableMap(new HashMap<>(mappings));
    }

    public T load(File file, Supplier<T> supplier) {
        T value = mapper.must(file, supplier);
        accept(value);
        return value;
    }

    public void write(T value, File file) {
        try {
            apply(value);
            mapper.write(value, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accept(T value) {
        for (Map.Entry<String[], Element<?>> e : mappings.entrySet()) {
            Optional<?> optional = mapper.getValue(value, e.getKey());
            if (optional.isPresent()) {
                Element element = e.getValue();
                element.setValue(optional.get());
            }
        }
    }

    public void apply(T value) {
        for (Map.Entry<String[], Element<?>> e : mappings.entrySet()) {
            mapper.setValue(value, e.getValue().getValue(), e.getKey());
        }
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Object getDefaultValue() {
        return this;
    }

    @Override
    public void setValue(Object o) {

    }

    @Override
    public boolean mousePress(int mx, int my, int button, int modifiers) {
        return root.mousePress(mx, my, button, modifiers);
    }

    @Override
    public void mouseRelease() {
        root.mouseRelease();
    }

    @Override
    public void draw(RenderContext context, int mx, int my) {
        root.setSize(getWidth(), getHeight());
        root.setPos(getLeft(), getTop());
        root.draw(context, mx, my);
    }

    @Override
    public boolean keyPress(int code, char c, int modifiers) {
        return root.keyPress(code, c, modifiers);
    }

    public static <T> UIMapper<T> create(Class<T> type) {
        return UIFactory.create().createUI(type);
    }
}
