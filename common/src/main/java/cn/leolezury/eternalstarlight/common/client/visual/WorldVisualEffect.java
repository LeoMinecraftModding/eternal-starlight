package cn.leolezury.eternalstarlight.common.client.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;

@Environment(EnvType.CLIENT)
public interface WorldVisualEffect {
	void tick();

	void render(MultiBufferSource source, PoseStack stack, float partialTicks);

	boolean shouldRemove();
}
