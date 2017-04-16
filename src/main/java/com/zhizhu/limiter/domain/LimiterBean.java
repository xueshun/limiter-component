package com.zhizhu.limiter.domain;

/**
 * Created by zhizhu on 16/11/8.
 */
public class LimiterBean<T> {

    private long maxQps;
    private boolean warmupSwitch;
    private long warmupTime;
    private Class<T> interfaze;

    public long getMaxQps() {
        return maxQps;
    }

    public void setMaxQps(long maxQps) {
        this.maxQps = maxQps;
    }

    public boolean isWarmupSwitch() {
        return warmupSwitch;
    }

    public void setWarmupSwitch(boolean warmupSwitch) {
        this.warmupSwitch = warmupSwitch;
    }

    public long getWarmupTime() {
        return warmupTime;
    }

    public void setWarmupTime(long warmupTime) {
        this.warmupTime = warmupTime;
    }

    public Class<T> getInterfaze() {
        return interfaze;
    }

    public void setInterfaze(Class<T> interfaze) {
        this.interfaze = interfaze;
    }

    @Override
    public String toString() {
        return "LimiterBean{" +
                "maxQps=" + maxQps +
                ", warmupSwitch=" + warmupSwitch +
                ", warmupTime=" + warmupTime +
                ", interfaze=" + interfaze +
                '}';
    }
}
