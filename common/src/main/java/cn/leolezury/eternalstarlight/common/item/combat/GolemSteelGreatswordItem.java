package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class GolemSteelGreatswordItem extends GreatswordItem {
	public GolemSteelGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);
		Level level = attacker.level();
		if (!level.isClientSide && !SpecialItemCooldown.isOnCooldown(attacker, this)) {
			List<LivingEntity> hurtEntities = new ArrayList<>();
			float radius = Math.min(target.getBbWidth() * 4f, 12);
			ESBlockUtil.getBlocksInBoundingBox(target.getBoundingBox().inflate(radius)).forEach(pos -> {
				BlockState aboveState = level.getBlockState(pos.above());
				if (!level.getBlockState(pos).isAir() && (aboveState.isAir() || aboveState.getBlock() instanceof LiquidBlock) && pos.distToCenterSqr(target.position()) <= radius) {
					ESFallingBlock fallingBlock = new ESFallingBlock(level, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, level.getBlockState(pos), 40, false);
					fallingBlock.push(0, 0.1 + 0.1 * (radius - Math.sqrt(pos.distToCenterSqr(target.position()))) / radius, 0);
					level.addFreshEntity(fallingBlock);
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ESExplosionParticleOptions.ENERGY, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 1, 0, 0, 0, 0);
					}
					for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(1))) {
						if (ESEntityUtil.shouldHarm(attacker, living) && !hurtEntities.contains(living)) {
							living.invulnerableTime = 0;
							if (living.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.GROUND_SMASH), 8)) {
								hurtEntities.add(living);
							}
						}
					}
				}
			});
			if (level instanceof ServerLevel serverLevel && !hurtEntities.isEmpty()) {
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.ENERGY, target.getX(), target.getY(), target.getZ(), 0, 0.1, 0));
				ScreenShakeVfx.createInstance(level.dimension(), target.position(), 20, 20, 0.1f, 0.2f, 3, 5.5f).send(serverLevel);
			}
			SpecialItemCooldown.setCooldown(attacker, this, 80);
		}
	}
}
