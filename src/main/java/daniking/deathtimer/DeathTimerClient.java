package daniking.deathtimer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class DeathTimerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            final var client = MinecraftClient.getInstance();
            if (client.player != null) {
                long secs = (((Timer) client.player).getTime());
                long minutes = secs / 60;
                long hours = minutes / 60;
                String output = hours + ":" + (minutes % 60) + ":" + (secs % 60);
                client.textRenderer.draw(matrixStack, output, 40, 40, 14737632);
            }
        });
    }
}
