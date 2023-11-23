package com.tidal.wave.data;


public enum MaxTime {

    _10_SECONDS(10),
    _20_SECONDS(20),
    _30_SECONDS(30),
    _40_SECONDS(40),
    _50_SECONDS(50),
    _60_SECONDS(60),
    _70_SECONDS(70),
    _80_SECONDS(80),
    _90_SECONDS(90),
    _100_SECONDS(100),
    _120_SECONDS(120),
    _150_SECONDS(150),
    _180_SECONDS(180),
    _200_SECONDS(200),
    _250_SECONDS(250),
    _300_SECONDS(300);

    private final int maxTime;

    MaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

}
