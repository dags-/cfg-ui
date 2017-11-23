package me.dags.ui.platform;

import java.awt.event.KeyEvent;

/**
 * @author dags <dags@dags.me>
 */
public class BuiltIn implements Input {

    @Override
    public boolean isEscape(int code) {
        return code == KeyEvent.VK_ESCAPE;
    }

    @Override
    public boolean isReturn(int code) {
        return code == KeyEvent.VK_ENTER;
    }

    @Override
    public boolean isBackspace(int code) {
        return code == KeyEvent.VK_BACK_SPACE;
    }

    @Override
    public boolean isDelete(int code) {
        return code == KeyEvent.VK_DELETE;
    }

    @Override
    public boolean isLeft(int code) {
        return code == KeyEvent.VK_LEFT;
    }

    @Override
    public boolean isRight(int code) {
        return code == KeyEvent.VK_RIGHT;
    }

    @Override
    public boolean isUp(int code) {
        return code == KeyEvent.VK_UP;
    }

    @Override
    public boolean isDown(int code) {
        return code == KeyEvent.VK_DOWN;
    }

    @Override
    public boolean isUndefined(char c) {
        return c == KeyEvent.CHAR_UNDEFINED;
    }

    @Override
    public String keyName(int code) {
        return KeyEvent.getKeyText(code);
    }

    @Override
    public int keyCode(char c) {
        return KeyEvent.getExtendedKeyCodeForChar(c);
    }

    @Override
    public int shiftCode() {
        return KeyEvent.VK_SHIFT;
    }
}
