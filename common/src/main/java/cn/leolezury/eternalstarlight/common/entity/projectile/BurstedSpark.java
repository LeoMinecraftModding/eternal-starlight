package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class BurstedSpark extends AbstractArrow implements TrailOwner {
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/concentrated_trail.png");

	public BurstedSpark(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}

	public BurstedSpark(Level level, LivingEntity livingEntity) {
		super(ESEntities.BURSTED_SPARK.get(), livingEntity, level, ItemStack.EMPTY, ItemStack.EMPTY);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		//TODO: do hurt
		super.onHit(hitResult);
	}

	@Override
	public void tick() {
		if (this.onGround()) {
			//TODO: burst
		}
		super.tick();
	}

	@Override
	public TrailEffect newTrail() {
		return new TrailEffect(0.15f, 5);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0), position().subtract(oldPos));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.5f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(224 / 255f, 124 / 255f, 195 / 255f, 1f);
	}

	@Override
	public RenderType getTrailRenderType() {
		return ESRenderType.entityTranslucentNoDepth(TRAIL_TEXTURE);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean shouldRender(double d, double e, double f) {
		return true;
	}
}
