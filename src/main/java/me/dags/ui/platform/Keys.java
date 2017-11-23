package me.dags.ui.platform;

/**
 * @author dags <dags@dags.me>
 */
public class Keys {

    private static final int CTRL = 0;
    private static final int SHIFT = 1;
    private static final int ALT = 2;

    public static int modifiers(boolean ctrl, boolean shift, boolean alt) {
        int modifiers = 0;
        modifiers |= bit(ctrl);
        modifiers |= bit(shift) << SHIFT;
        modifiers |= bit(alt) << ALT;
        return modifiers;
    }

    public static boolean ctrl(int mod) {
        return val(mod, CTRL) == 1;
    }

    public static boolean shift(int mod) {
        return val(mod, SHIFT) == 1;
    }

    public static boolean alt(int mod) {
        return val(mod, ALT) == 1;
    }

    private static byte bit(boolean val) {
        return val ? (byte) 1 : (byte) 0;
    }

    private static int val(int mod, int pos) {
        return (mod >> pos) & 1;
    }
}
