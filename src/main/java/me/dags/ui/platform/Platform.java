package me.dags.ui.platform;

/**
 * @author dags <dags@dags.me>
 */
public class Platform {

    private static Input input = new Keyboard();

    public static Input getInput() {
        return input;
    }

    public static void setInput(Input input) {
        Platform.input = input;
    }
}
