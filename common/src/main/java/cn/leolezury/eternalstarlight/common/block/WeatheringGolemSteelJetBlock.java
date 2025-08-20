package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;

public class WeatheringGolemSteelJetBlock extends WeatheringGolemSteelFullBlock {
	public static final MapCodec<WeatheringGolemSteelJetBlock> CODEC = simpleCodec(WeatheringGolemSteelJetBlock::new);
	public static final IntegerProperty POWER = BlockStateProperties.POWER;

	public WeatheringGolemSteelJetBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(POWER, 0));
	}

	@Override
	protected MapCodec<WeatheringGolemSteelJetBlock> codec() {
		return CODEC;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
		if (blockState.getValue(POWER) > 0) {
			if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
				Vec3 particlePos = pos.getCenter().add(0, 0.5, 0);
				RandomSource random = entity.getRandom();
				for (int i = 0; i < 15; i++) {
					ParticlePacket packet = new ParticlePacket(ParticleTypes.WHITE_SMOKE, particlePos.x, particlePos.y, particlePos.z, 0, 0.2 + random.nextFloat() / 1.5, 0);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet);
				}
			}
			entity.hurtMarked = true;
			entity.addDeltaMovement(new Vec3(0, blockState.getValue(POWER) * (isOxidized() ? 0.1 : 0.2), 0));
			if (entity instanceof Player player) {
				player.currentImpulseImpactPos = pos.getCenter().add(0, 0.5, 0);
				player.setIgnoreFallDamageFromCurrentImpulse(true);
			}
		}
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (state.getValue(POWER) != level.getBestNeighborSignal(pos)) {
				level.setBlockAndUpdate(pos, state.setValue(POWER, level.getBestNeighborSignal(pos)));
			}
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!level.isClientSide) {
			if (state.getValue(POWER) != level.getBestNeighborSignal(pos)) {
				level.setBlockAndUpdate(pos, state.setValue(POWER, level.getBestNeighborSignal(pos)));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}
}
