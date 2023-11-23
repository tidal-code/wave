package dev.tidalcode.wave.data;

public class WaitData {

    private int waitDuration;

    public int getWaitDuration() {
        return waitDuration;
    }

    public void setWaitDuration(int waitDuration) {
        this.waitDuration = waitDuration;
    }

    public void setDefaultWait() {
        this.waitDuration = 5;
    }
}
