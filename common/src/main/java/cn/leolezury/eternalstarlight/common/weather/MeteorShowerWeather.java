package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TinyCreteor;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class MeteorShowerWeather extends AbstractWeather {
	private static final Object2IntMap<ResourceKey<Level>> LAST_SUCCESSFUL_SPAWN = new Object2IntArrayMap<>();

	public MeteorShowerWeather(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canStart(ServerLevel level) {
		return true;
	}

	@Override
	public boolean canContinue(ServerLevel level, int ticks) {
		return true;
	}

	@Override
	public void serverTick(ServerLevel level, int ticks) {
		if (!LAST_SUCCESSFUL_SPAWN.containsKey(level.dimension()) || ticks - LAST_SUCCESSFUL_SPAWN.getInt(level.dimension()) > 600) {
			for (ServerPlayer player : level.players()) {
				if (!player.isSpectator()) {
					BlockPos pos = player.blockPosition();
					DifficultyInstance difficulty = level.getCurrentDifficultyAt(pos);
					RandomSource random = level.getRandom();
					BlockPos randomPos = pos.above(20 + random.nextInt(15))
						.east(-10 + random.nextInt(21))
						.south(-10 + random.nextInt(21));
					BlockState blockState = level.getBlockState(randomPos);
					FluidState fluidState = level.getFluidState(randomPos);
					if (NaturalSpawner.isValidEmptySpawnBlock(level, randomPos, blockState, fluidState, ESEntities.TINY_CRETEOR.get()) && randomPos.getY() > level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, randomPos.getX(), randomPos.getZ())) {
						SpawnGroupData groupData = null;
						for (int i = 0; i < 2; i++) {
							TinyCreteor tinyCreteor = ESEntities.TINY_CRETEOR.get().create(level);
							if (tinyCreteor != null) {
								tinyCreteor.moveTo(randomPos, 0.0F, 0.0F);
								groupData = tinyCreteor.finalizeSpawn(level, difficulty, MobSpawnType.NATURAL, groupData);
								level.addFreshEntityWithPassengers(tinyCreteor);
								LAST_SUCCESSFUL_SPAWN.put(level.dimension(), ticks);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void tickBlock(ServerLevel level, int ticks, BlockPos pos) {
		if (level.getRandom().nextFloat() < ESConfig.INSTANCE.aethersentMeteorDropRate) {
			int targetX = pos.getX();
			int targetY = pos.getY();
			int targetZ = pos.getZ();
			RandomSource random = level.getRandom();
			AethersentMeteor meteor = new AethersentMeteor(level, null, targetX + (random.nextFloat() - 0.5) * 3, targetY + 200 + (random.nextFloat() - 0.5) * 5, targetZ + (random.nextFloat() - 0.5) * 3);
			meteor.setSize(10);
			meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
			level.addFreshEntity(meteor);
			level.sendParticles(ParticleTypes.EXPLOSION, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
		}
	}

	@Override
	public void onStart(ServerLevel level) {
		LAST_SUCCESSFUL_SPAWN.removeInt(level.dimension());
	}

	@Override
	public void onStop(ServerLevel level, int ticks) {

	}

	@Override
	public void clientTick() {
		EternalStarlight.getClientHelper().handleMeteorShowerClientTick();
	}

	@Override
	public float modifyRainLevel(float original) {
		return EternalStarlight.getClientHelper().handleMeteorShowerRainLevel();
	}
}
