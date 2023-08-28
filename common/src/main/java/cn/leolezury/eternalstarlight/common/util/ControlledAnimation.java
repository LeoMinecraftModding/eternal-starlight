package cn.leolezury.eternalstarlight.common.util;

public class ControlledAnimation {
    private int timer;
    private int duration;

    public ControlledAnimation(int d) {
        this.timer = 0;
        this.duration = d;
    }

    public int getTimer() {
        return this.timer;
    }

    public void increaseTimer() {
        if (this.timer < this.duration) {
            this.timer++;
        }
    }

    public void decreaseTimer() {
        if (this.timer > 0.0D) {
            this.timer--;
        }
    }
}