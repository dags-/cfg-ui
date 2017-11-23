package me.dags.ui.element;

/**
 * @author dags <dags@dags.me>
 */
public class Animator {

    private final float inc = 0.05F;
    private final long interval = 4L;

    private float value = 0.0F;
    private long time = 0L;

    public void tick() {
        long time = System.currentTimeMillis();
        if (time - this.time > interval) {
            this.time = time;
            this.value = Math.min(1F, value + inc);
        }
    }

    public void reset() {
        this.value = 0F;
    }

    public int getPad(int size, int max) {
        return (max - size) / 2;
    }

    public int getValue(int max) {
        return Math.round(max * value);
    }
}
