package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class YetiFurBlock extends HalfTransparentBlock {
	public static final MapCodec<YetiFurBlock> CODEC = simpleCodec(YetiFurBlock::new);

	public YetiFurBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<YetiFurBlock> codec() {
		return CODEC;
	}

	@Override
	public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
		if (entity.isSuppressingBounce()) {
			super.fallOn(level, blockState, blockPos, entity, f);
		} else {
			entity.causeFallDamage(f, 0.0F, level.damageSources().fall());
		}
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
		if (entity.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(blockGetter, entity);
		} else {
			this.bounceUp(entity);
		}
	}

	private void bounceUp(Entity entity) {
		Vec3 vec3 = entity.getDeltaMovement();
		if (vec3.y < 0.0) {
			double d = entity instanceof LivingEntity ? 0.8 : 0.6;
			entity.setDeltaMovement(vec3.x, -vec3.y * d, vec3.z);
		}
	}
}
