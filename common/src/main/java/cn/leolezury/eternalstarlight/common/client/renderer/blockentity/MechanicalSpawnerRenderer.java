package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.MechanicalSpawner;
import cn.leolezury.eternalstarlight.common.block.entity.MechanicalSpawnerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SpawnerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class MechanicalSpawnerRenderer implements BlockEntityRenderer<MechanicalSpawnerBlockEntity> {
	private final EntityRenderDispatcher entityRenderer;

	public MechanicalSpawnerRenderer(BlockEntityRendererProvider.Context context) {
		this.entityRenderer = context.getEntityRenderer();
	}

	@Override
	public void render(MechanicalSpawnerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		Level level = blockEntity.getLevel();
		if (level != null) {
			MechanicalSpawner spawner = blockEntity.getSpawner();
			Entity entity = spawner.getOrCreateDisplayEntity(level, blockEntity.getBlockPos());
			if (entity != null) {
				SpawnerRenderer.renderEntityInSpawner(partialTick, poseStack, bufferSource, packedLight, entity, this.entityRenderer, spawner.getOSpin(), spawner.getSpin());
			}
		}
	}
}
