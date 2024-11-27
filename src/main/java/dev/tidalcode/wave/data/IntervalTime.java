package dev.tidalcode.wave.data;

public enum IntervalTime {

    _2_SECONDS(2),
    _3_SECONDS(3),
    _4_SECONDS(4),
    _5_SECONDS(5),
    _6_SECONDS(6),
    _7_SECONDS(7),
    _8_SECONDS(8),
    _9_SECONDS(9),
    _10_SECONDS(10),
    _15_SECONDS(15),
    _20_SECONDS(20),
    _25_SECONDS(25),
    _30_SECONDS(30),
    _35_SECONDS(35),
    _40_SECONDS(40),
    _45_SECONDS(45),
    _50_SECONDS(50),
    _55_SECONDS(55),
    _60_SECONDS(60);

    private final int intervalTime;

    IntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }
}
