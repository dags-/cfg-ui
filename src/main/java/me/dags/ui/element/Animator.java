package me.dags.ui.element;

import me.dags.ui.Animation;

/**
 * @author dags <dags@dags.me>
 */
public class Animator {

    private final Animation animation;
    private float value = 0.0F;
    private long time = 0L;

    public Animator(Animation animation) {
        this.animation = animation;
    }

    public void tick() {
        long time = System.currentTimeMillis();
        if (time - this.time > animation.interval) {
            this.time = time;
            this.value = Math.min(1F, value + animation.increment);
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
