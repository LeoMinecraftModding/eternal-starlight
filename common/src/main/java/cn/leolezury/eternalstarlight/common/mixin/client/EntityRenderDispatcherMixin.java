package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	@Unique
	private static final Material ABYSSAL_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, EternalStarlight.id("block/abyssal_fire_0"));
	@Unique
	private static final Material ABYSSAL_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, EternalStarlight.id("block/abyssal_fire_1"));

	@Shadow
	public abstract <T extends Entity> EntityRenderer<? super T> getRenderer(T entity);

	@Shadow
	private static void fireVertex(PoseStack.Pose arg, VertexConsumer arg2, float f, float g, float h, float i, float j) {
	}

	@Shadow
	private Quaternionf cameraOrientation;

	@Inject(method = "render", at = @At("RETURN"))
	private <E extends Entity> void render(E entity, double xOffset, double yOffset, double zOffset, float delta, float yRot, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
		if (entity instanceof LivingEntity living && !living.isDeadOrDying() && living.hasEffect(ESMobEffects.CRYSTALLINE_INFECTION.asHolder())) {
			EntityRenderer<? super E> entityRenderer = getRenderer(living);

			Vec3 renderOffset = entityRenderer.getRenderOffset(entity, yRot);
			double x = xOffset + renderOffset.x();
			double y = yOffset + renderOffset.y();
			double z = zOffset + renderOffset.z();
			poseStack.pushPose();
			poseStack.translate(x, y, z);

			long seed = (long) (Math.pow(living.getId(), 3) * 54321L);
			RandomSource random = RandomSource.create();
			random.setSeed(seed);
			int crystalCount = (int) (living.getBbHeight() / 0.4F) + 2;

			for (int i = 0; i < crystalCount; i++) {
				poseStack.pushPose();
				float blockX = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2f;
				float blockY = random.nextFloat() * living.getBbHeight();
				float blockZ = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2f;
				Vec3 center = new Vec3(0, living.getBbHeight() / 2, 0);
				Vec3 block = ESMathUtil.lerpVec(0.2f, new Vec3(blockX, blockY, blockZ), center);
				blockX = (float) block.x;
				blockY = (float) block.y;
				blockZ = (float) block.z;
				poseStack.translate(blockX, blockY, blockZ);
				float pitch = ESMathUtil.positionToPitch(center, block);
				float yaw = ESMathUtil.positionToYaw(center, block);
				poseStack.mulPose(new Quaternionf().rotationX(90.0F * Mth.DEG_TO_RAD));
				poseStack.mulPose(new Quaternionf().rotationZ((yaw - 90.0F) * Mth.DEG_TO_RAD));
				poseStack.mulPose(new Quaternionf().rotationX(-pitch * Mth.DEG_TO_RAD));
				poseStack.scale(living.getBbWidth() / 2f, living.getBbWidth() / 2f, living.getBbWidth() / 2f);
				poseStack.translate(-0.5F, -0.5F, -0.5F);
				ESPlatform.INSTANCE.renderBlock(Minecraft.getInstance().getBlockRenderer(), poseStack, multiBufferSource, living.level(), random.nextBoolean() ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState(), living.blockPosition(), seed);
				poseStack.popPose();
			}
			poseStack.popPose();
		}
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;displayFireAnimation()Z", shift = At.Shift.AFTER))
	private <E extends Entity> void renderFlame(E entity, double d, double e, double f, float g, float h, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		CompoundTag persistentData = ESEntityUtil.getPersistentData(entity);
		int inAbyssalFireTicks = persistentData.getInt(CommonHandlers.TAG_IN_ABYSSAL_FIRE_TICKS);
		if (inAbyssalFireTicks > 0) {
			renderAbyssalFlame(poseStack, multiBufferSource, entity, Mth.rotationAroundAxis(Mth.Y_AXIS, cameraOrientation, new Quaternionf()));
		}
	}

	@Unique
	private void renderAbyssalFlame(PoseStack poseStack, MultiBufferSource buffer, Entity entity, Quaternionf quaternion) {
		TextureAtlasSprite textureatlassprite = ABYSSAL_FIRE_0.sprite();
		TextureAtlasSprite textureatlassprite1 = ABYSSAL_FIRE_1.sprite();
		poseStack.pushPose();
		float f = entity.getBbWidth() * 1.4F;
		poseStack.scale(f, f, f);
		float f1 = 0.5F;
		float f3 = entity.getBbHeight() / f;
		float f4 = 0.0F;
		poseStack.mulPose(quaternion);
		poseStack.translate(0.0F, 0.0F, 0.3F - (float) ((int) f3) * 0.02F);
		float f5 = 0.0F;
		int i = 0;
		VertexConsumer vertexconsumer = buffer.getBuffer(Sheets.cutoutBlockSheet());

		for (PoseStack.Pose posestack$pose = poseStack.last(); f3 > 0.0F; ++i) {
			TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
			float f6 = textureatlassprite2.getU0();
			float f7 = textureatlassprite2.getV0();
			float f8 = textureatlassprite2.getU1();
			float f9 = textureatlassprite2.getV1();
			if (i / 2 % 2 == 0) {
				float f10 = f8;
				f8 = f6;
				f6 = f10;
			}

			fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f8, f9);
			fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f6, f9);
			fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f6, f7);
			fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f8, f7);
			f3 -= 0.45F;
			f4 -= 0.45F;
			f1 *= 0.9F;
			f5 -= 0.03F;
		}

		poseStack.popPose();
	}
}
