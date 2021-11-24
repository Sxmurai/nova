package cope.nova.client.features.module;

import cope.nova.client.features.Wrapper;
import cope.nova.client.settings.Bind;
import cope.nova.client.settings.Setting;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

public class Module implements Wrapper {
    private final String name;
    private final Category category;
    private String description;

    private final ArrayList<Setting> settings = new ArrayList<>();
    private final Bind bind = new Bind("Bind", Keyboard.KEY_NONE);
    private final Setting<Boolean> drawn = new Setting<>("Drawn", true);

    private boolean toggled = false;

    public Module() {
        Define def = this.getClass().getDeclaredAnnotation(Define.class);

        this.name = def.name();
        this.category = def.category();

        if (this.getClass().isAnnotationPresent(Info.class)) {
            Info info = this.getClass().getDeclaredAnnotation(Info.class);

            this.description = info.description();
            this.bind.setValue(info.bind());
        }

        this.settings.add(this.bind);
        this.settings.add(this.drawn);
    }

    public void registerAllSettings() {
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter((field) -> Setting.class.isAssignableFrom(field.getType()))
                .forEach((field) -> {
                    field.setAccessible(true);
                    try {
                        this.settings.add((Setting) field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    protected void onEnable() { }
    protected void onDisable() { }

    public void onUpdate() { }
    public void onTick() { }
    public void onRender3d() { }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public int getBind() {
        return this.bind.getValue();
    }

    public void setBind(int bind) {
        this.bind.setValue(bind);
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public boolean isToggled() {
        return toggled;
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if (this.toggled) {
            MinecraftForge.EVENT_BUS.register(this);
            this.onEnable();
        } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            this.onDisable();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Define {
        String name();
        Category category() default Category.Other;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String description() default "No description provided";
        int bind() default Keyboard.KEY_NONE;
    }

    public enum Category {
        Client, Combat, Movement, Other, Player, Render
    }
}
