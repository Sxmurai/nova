package cope.nova.client.manager;

import cope.nova.client.features.Wrapper;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class Manager<T> implements Wrapper {
    protected final ArrayList<T> elements = new ArrayList<>();

    public Manager() {
        this.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public abstract void init();

    public ArrayList<T> getElements() {
        return elements;
    }
}
