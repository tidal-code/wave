package dev.tidalcode.wave.wait;

public enum Speed {
    ONE(5000),
    TWO(3000),
    THREE(2000),
    FOUR(1000),
    FIVE(500);

    final int pollingSpeed;

    Speed(int pollingSpeed) {
        this.pollingSpeed = pollingSpeed;
    }

    public int getSpeed() {
        return pollingSpeed;
    }
}
