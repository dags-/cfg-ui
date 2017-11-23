package me.dags.ui.platform;

/**
 * @author dags <dags@dags.me>
 */
public interface Input {

    boolean isEscape(int code);

    boolean isReturn(int code);

    boolean isBackspace(int code);

    boolean isDelete(int code);

    boolean isLeft(int code);

    boolean isRight(int code);

    boolean isUp(int code);

    boolean isDown(int code);

    boolean isUndefined(char c);

    String keyName(int code);

    int keyCode(char c);

    int shiftCode();

    default int mouseOffset() {
        return 0;
    }
}
