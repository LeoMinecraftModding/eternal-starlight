package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GlowParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class GuidanceOfStarsSpell extends AbstractSpell {
	public GuidanceOfStarsSpell(Properties properties) {
		super(properties);
	}

	@Override
	public boolean checkExtraConditions(LivingEntity entity) {
		return entity.level().dimension() == ESDimensions.STARLIGHT_KEY;
	}

	@Override
	public boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks) {
		return entity.level().dimension() == ESDimensions.STARLIGHT_KEY;
	}

	@Override
	public void onPreparationTick(LivingEntity entity, int ticks) {
	}

	@Override
	public void onSpellTick(LivingEntity entity, int ticks) {
		if (entity.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 4; i++) {
				serverLevel.sendParticles(GlowParticleOptions.getSeek(entity.getRandom(), false, false), entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth() * 2, entity.getY() + entity.getBbHeight() / 2d + (entity.getRandom().nextDouble() - 0.5) * entity.getBbHeight() * 2, entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth() * 2, 3, 0.2, 0.2, 0.2, 0);
			}
		}
	}

	@Override
	public void onStart(LivingEntity entity) {
	}

	@Override
	public void onStop(LivingEntity entity, int ticks) {
		if (ticks >= spellProperties().spellTicks() && entity.level() instanceof ServerLevel serverLevel) {
			TagKey<Structure> key = ESTags.Structures.BOSS_STRUCTURES;
			BlockPos blockPos = serverLevel.findNearestMapStructure(key, entity.blockPosition(), 100, false);
			if (blockPos != null) {
				Vec3 startPos = entity.getEyePosition();
				Vec3 endPos = startPos.add(blockPos.getCenter().subtract(startPos).normalize().scale(5));
				BlockHitResult result = entity.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.of(entity)));
				if (result.getType() != HitResult.Type.MISS) {
					endPos = result.getLocation();
				}
				for (int i = 0; i < 18; i++) {
					Vec3 delta = endPos.offsetRandom(entity.getRandom(), 0.5f).subtract(startPos).normalize().scale(0.5);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(GlowParticleOptions.getSeek(entity.getRandom(), true, true), startPos.x + (entity.getRandom().nextFloat() - 0.5) * 0.8, startPos.y + (entity.getRandom().nextFloat() - 0.5) * 0.8, startPos.z + (entity.getRandom().nextFloat() - 0.5) * 0.8, delta.x, delta.y, delta.z));
				}
				serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ESSoundEvents.SEEKING_EYE_LAUNCH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));
			}
		}
	}
}
