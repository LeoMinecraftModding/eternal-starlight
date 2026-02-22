package cn.leolezury.eternalstarlight.common.client.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface WorldVisualEffect {
	void worldTick();

	void render(MultiBufferSource source, PoseStack stack, float partialTicks);

	boolean shouldRemove();
}
