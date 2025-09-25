package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class HammerItem extends TieredItem {
	private final Supplier<ParticleOptions> smashParticle;
	private final Holder<SoundEvent> smashSound;

	public HammerItem(Tier tier, Supplier<ParticleOptions> smashParticle, Holder<SoundEvent> smashSound, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, tier.createToolProperties(BlockTags.MINEABLE_WITH_PICKAXE)));
		this.smashParticle = smashParticle;
		this.smashSound = smashSound;
	}

	protected void spawnBlockParticle(ServerLevel serverLevel, BlockPos pos, Vec3 particlePos) {
		BlockState state = serverLevel.getBlockState(pos.below());
		if (state.getRenderShape() != RenderShape.INVISIBLE) {
			serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), particlePos.x, particlePos.y, particlePos.z, 3, 0.15, 0.15, 0.15, 0.2);
		}
	}

	public void performCriticalAttack(Player player, Entity target) {
		if (player instanceof ServerPlayer serverPlayer) {
			ESCriteriaTriggers.HAMMER_CRITICAL_HIT.get().trigger(serverPlayer);
		}
		Level level = player.level();
		for (LivingEntity entity : level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, new AABB(target.blockPosition()).inflate(2))) {
			if (entity.hurt(level.damageSources().playerAttack(player), (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75))) {
				entity.addDeltaMovement(new Vec3(0.0, 0.4 * Math.max(0.0, 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), 0.0));
			}
		}
		level.playSound(null, target.blockPosition(), smashSound.value(), player.getSoundSource());
		if (target.onGround() && level instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 360; i += 10) {
				Vec3 vec3 = ESMathUtil.rotationToPosition(target.blockPosition().below().getCenter().add(0, -0.1, 0), 1.75f, 0, i);
				BlockPos particlePos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
				for (int j = 0; j < 5; j++) {
					spawnBlockParticle(serverLevel, particlePos, vec3.add(0, 0.6, 0));
				}
				if (smashParticle.get() != null) {
					Vec3 particleVec = vec3.add(0, 0.6, 0);
					serverLevel.sendParticles(smashParticle.get(), particleVec.x, particleVec.y, particleVec.z, 3, 0.15, 0.15, 0.15, 0.1);
				}
			}
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}
