package cope.nova.client.ui.clickgui;

import cope.nova.client.Nova;
import cope.nova.client.features.module.Module;
import cope.nova.client.ui.clickgui.components.Frame;
import cope.nova.client.ui.clickgui.components.button.ModuleButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClickGuiScreen extends GuiScreen {
    private static ClickGuiScreen INSTANCE;

    private final ArrayList<Frame> frames = new ArrayList<>();

    private ClickGuiScreen() {
        double x = 4.0;

        for (Module.Category category : Module.Category.values()) {
            List<Module> modules = Nova.moduleManager.getElements()
                    .stream().filter((mod) -> mod.getCategory().equals(category))
                    .collect(Collectors.toList());

            if (modules.isEmpty()) {
                continue;
            }

            this.frames.add(new Frame(category.name(), x) {
                @Override
                protected void init() {
                    modules.forEach((module) -> this.children.add(new ModuleButton(module)));
                }
            });

            x += 88.0;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        this.frames.forEach((frame) -> frame.render(mouseX, mouseY));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.frames.forEach((frame) -> frame.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.frames.forEach((frame) -> frame.mouseRelease(mouseX, mouseY, state));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.frames.forEach((frame) -> frame.keyTyped(typedChar, keyCode));
    }

    public static ClickGuiScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGuiScreen();
        }

        return INSTANCE;
    }
}
