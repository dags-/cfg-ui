import me.dags.config.style.Style;
import me.dags.ui.annotation.*;
import me.dags.ui.layout.Layout;
import me.dags.ui.platform.Platform;

/**
 * @author dags <dags@dags.me>
 */
@Insets(left = 0.5F, right = 0.5F, top = 0F, bottom = 0F)
@Section(pos = Layout.LEFT, grid = {1F, 0.3F})
@Section(pos = Layout.RIGHT)
public class Config {

    private Inputs binds = new Inputs();

    private Speeds speeds = new Speeds();

    public static class Speeds {

        @Label(format = "Fly Speed: %.2f", row = 0, pos = Layout.RIGHT)
        @Slider(def = 1, min = 0F, max = 10F, row = 0, pos = Layout.RIGHT)
        private float fly = 1.0F;

        @Label(format = "Fly Boost: %.2f", row = 1, pos = Layout.RIGHT)
        @Slider(def = 2, min = 0F, max = 10F, row = 1, pos = Layout.RIGHT)
        private float flyBoost = 2.0F;

        @Label(format = "Sprint Speed: %.2f", row = 2, pos = Layout.RIGHT)
        @Slider(def = 1, min = 0F, max = 10F, row = 2, pos = Layout.RIGHT)
        private float sprint = 1.0F;

        @Label(format = "Sprint Boost: %.2f", row = 3, pos = Layout.RIGHT)
        @Slider(def = 2, min = 0F, max = 10F, row = 3, pos = Layout.RIGHT)
        private float sprintBoost = 2.0F;
    }

    @Style(ignoreEmpty = true, override = true)
    public static class Inputs {

        @Bind(key = "Fly: %s", mouse = "Fly: Mouse %s", def = 0, row = 0, col = 0, pos = Layout.LEFT)
        private int fly = Platform.getInput().keyCode('f');

        @Bind(key = "Sprint: %s", mouse = "Sprint: Mouse %s", def = 0, row = 1, col = 0, pos = Layout.LEFT)
        private int sprint = Platform.getInput().keyCode('r');

        @Bind(key = "Boost: %s", mouse = "Boost: Mouse %s", def = 0, row = 2, col = 0, pos = Layout.LEFT)
        private int boost = Platform.getInput().keyCode('x');

        @Bind(key = "Up: %s", mouse = "Up: Mouse %s", def = 0, row = 3, col = 0, pos = Layout.LEFT)
        private int up = Platform.getInput().keyCode(' ');

        @Bind(key = "Down: %s", mouse = "Down: Mouse %s", def = 0, row = 4, col = 0, pos = Layout.LEFT)
        private int down = Platform.getInput().shiftCode();

        @Cycle(row = 0, col = 1, pos = Layout.LEFT)
        private BindMode flyMode = BindMode.toggle;

        @Cycle(row = 1, col = 1, pos = Layout.LEFT)
        private BindMode sprintMode = BindMode.toggle;

        @Cycle(row = 2, col = 1, pos = Layout.LEFT)
        private BindMode boostMode = BindMode.toggle;
    }

    public enum BindMode {
        toggle,
        hold,
        ;
    }
}
