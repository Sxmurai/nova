package cope.nova.client.settings;

import java.util.function.Supplier;

public class Setting<T> {
    private final String name;
    private T value;

    private final Number min, max;
    private final Supplier<Boolean> visibility;

    public Setting(String name, T value) {
        this(name, value, null, null, null);
    }

    public Setting(String name, T value, Supplier<Boolean> visibility) {
        this(name, value, null, null, visibility);
    }

    public Setting(String name, T value, Number min, Number max) {
        this(name, value, min, max, null);
    }

    public Setting(String name, T value, Number min, Number max, Supplier<Boolean> visibility) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public Number getMax() {
        return max;
    }

    public Number getMin() {
        return min;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isVisible() {
        return this.visibility == null || this.visibility.get();
    }

    public Enum next() {
        if (!(this.value instanceof Enum)) {
            return null;
        }

        int current = this.currentEnum();
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            if (current + 1 == i) {
                return ((Enum[]) this.value.getClass().getEnumConstants())[i];
            }
        }

        return null;
    }

    public int currentEnum() {
        if (!(this.value instanceof Enum)) {
            return -1;
        }

        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            Enum val = ((Enum[]) this.value.getClass().getEnumConstants())[i];
            if (val == this.value) {
                return i;
            }
        }

        return -1;
    }
}
