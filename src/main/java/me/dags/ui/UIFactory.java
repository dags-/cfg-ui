package me.dags.ui;

import me.dags.config.Mapper;
import me.dags.ui.annotation.Insets;
import me.dags.ui.annotation.Label;
import me.dags.ui.annotation.Section;
import me.dags.ui.element.Element;
import me.dags.ui.element.LabelElement;
import me.dags.ui.layout.BorderLayout;
import me.dags.ui.layout.Layout;
import me.dags.ui.processor.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author dags <dags@dags.me>
 */
public class UIFactory {

    private final Map<Class<? extends Annotation>, ElementProcessor<?>> processors;

    private UIFactory(Builder builder) {
        this.processors = Collections.unmodifiableMap(new HashMap<>(builder.processors));
    }

    public <T> UIMapper<T> createUI(Class<T> type) {
        Mapper<T> mapper = Mapper.of(type);
        Map<String[], Element<?>> mappings = new HashMap<>();
        BorderLayout root = process(type, mappings);
        return new UIMapper<>(mapper, root, mappings);
    }

    private BorderLayout process(Class<?> c, Map<String[], Element<?>> mappings) {
        Insets layout = c.getAnnotation(Insets.class);
        layout = layout == null ? Insets.DEFAULT : layout;
        Section[] sections = c.getAnnotationsByType(Section.class);
        BorderLayout root = new BorderLayout(layout, sections);
        process(c, Collections.emptyList(), root, Theme.DEFAULT, mappings);
        return root;
    }

    private void process(Class<?> c, List<String> path, BorderLayout parent, Theme parentTheme, Map<String[], Element<?>> mappings) {
        me.dags.ui.annotation.Theme theme = c.getAnnotation(me.dags.ui.annotation.Theme.class);
        if (theme != null) {
            parentTheme = new Theme(parentTheme, theme);
        }

        for (Field field : c.getDeclaredFields()) {
            List<String> route = new LinkedList<>(path);
            route.add(field.getName());

            if (isPrimitive(field.getType())) {
                add(field, route, parent, parentTheme, mappings);
            } else {
                process(field.getType(), route, parent, parentTheme, mappings);
            }
        }
    }

    private void add(Field field, List<String> path, BorderLayout parent, Theme parentTheme, Map<String[], Element<?>> mappings) {
        for (Map.Entry<Class<? extends Annotation>, ElementProcessor<?>> e : processors.entrySet()) {
            Class<? extends Annotation> type = e.getKey();
            ElementProcessor<?> processor = e.getValue();
            if (processor.accepts(field) && add(type, processor, field, path, parent, parentTheme, mappings)) {
                return;
            }
        }
    }

    private <T extends Annotation> boolean add(Class<? extends Annotation> type, ElementProcessor<T> processor, Field field, List<String> path, BorderLayout parent, Theme parentTheme, Map<String[], Element<?>> mappings) {
        Annotation annotation = field.getAnnotation(type);
        if (annotation == null || !processor.getType().isInstance(annotation)) {
            return false;
        }

        me.dags.ui.annotation.Theme th = field.getAnnotation(me.dags.ui.annotation.Theme.class);
        Theme theme = th == null ? parentTheme : new Theme(parentTheme, th);

        T data = processor.getType().cast(annotation);
        Layout pos = processor.getPos(data);
        Element<?> element = processor.getElement(field, data, theme);

        // add to parent
        parent.getLayout(pos).add(element);
        // add to mappings
        mappings.put(getRoute(path), element);

        Label label = field.getAnnotation(Label.class);
        if (label != null) {
            LabelElement labelElement = processor.getLabel(label, element, parentTheme);
            // add label
            parent.getLayout(label.pos()).add(labelElement);
        }

        return true;
    }

    private static String[] getRoute(List<String> path) {
        String[] route = new String[path.size()];
        int i = 0;
        for (String s : path) {
            route[i++] = s;
        }
        return route;
    }

    private static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type.isEnum() || Number.class.isAssignableFrom(type) || type == String.class || type == Boolean.class || type == Character.class;
    }

    public static UIFactory create() {
        return builder().defaults().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<Class<? extends Annotation>, ElementProcessor<?>> processors = new HashMap<>();

        public Builder processor(ElementProcessor<?> processor) {
            processors.put(processor.getType(), processor);
            return this;
        }

        public Builder defaults() {
            processor(new BindProcessor());
            processor(new CycleProcessor());
            processor(new InputProcessor());
            processor(new NumberProcessor());
            processor(new SliderProcessor());
            processor(new ToggleProcessor());
            return this;
        }

        public UIFactory build() {
            return new UIFactory(this);
        }
    }
}
