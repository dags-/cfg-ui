import me.dags.ui.UIFactory;
import me.dags.ui.UIMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author dags <dags@dags.me>
 */
public class Main {

    private static Config conf = new Config();

    public static void main(String[] args) throws Exception {
        UIMapper<Config> ui = UIFactory.create().createUI(Config.class);
        File file = new File("config.conf");

        ConfigPanel panel = new ConfigPanel(ui);
        panel.setPreferredSize(new Dimension(720, 400));

        JButton load = new JButton("Load");
        load.addActionListener(e -> conf = ui.load(file, Config::new));
        conf = ui.load(file, Config::new);

        JButton save = new JButton("Save");
        save.addActionListener(e -> ui.write(conf, file));

        JPanel buttons = new JPanel();
        buttons.add(load);
        buttons.add(save);

        JPanel root = new JPanel(new BorderLayout());
        root.add(panel, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.PAGE_END);

        JFrame frame = new JFrame();
        frame.add(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
