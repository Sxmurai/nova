package cope.nova.util.timing;

public class Timer {
    private long time = -1L;

    public boolean passed(double time, Format format) {
        return this.passedMs(this.transform(time, format));
    }

    public boolean passedS(double time) {
        return this.passedMs(this.transform(time, Format.Seconds));
    }

    public boolean passedMs(long time) {
        return System.nanoTime() - this.time >= (time * 1000000L);
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    private long transform(double value, Format format) {
        switch (format) {
            case Milliseconds: return (long) value;
            case Seconds: return (long) (value / 1000.0);
            case Ticks: return (long) (value * 50L);
        }

        return (long) value;
    }

    public enum Format {
        Milliseconds, Seconds, Ticks
    }
}
