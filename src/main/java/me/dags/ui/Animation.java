package me.dags.ui;

/**
 * @author dags <dags@dags.me>
 */
public class Animation {

    public final float increment;
    public final long interval;

    public Animation(float increment, long interval) {
        this.increment = increment;
        this.interval = interval;
    }
}
