package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {
	@WrapOperation(method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;isValidPositionForMob(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;D)Z"))
	private static boolean isValidPositionForMob(ServerLevel serverLevel, Mob mob, double distance, Operation<Boolean> original) {
		boolean originalValid = original.call(serverLevel, mob, distance);
		if (originalValid
			&& !mob.getType().is(ESTags.EntityTypes.SOLARIS_ISLES_INHABITANTS)
			&& ESBiomes.anyNearbyGoldenGrassBlock(serverLevel, mob.blockPosition())) {
			return false;
		}
		if (originalValid
			&& mob.getType().getCategory() == MobCategory.MONSTER
			&& serverLevel.getLevel().dimension() == ESDimensions.STARLIGHT_KEY
			&& ESBiomes.isBlockedBySpawnPreventionBlocks(serverLevel.getLevel(), mob.blockPosition())) {
			return false;
		}
		return originalValid;
	}

	@WrapOperation(method = "spawnMobsForChunkGeneration(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/Holder;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/util/RandomSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;getTopNonCollidingPos(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/entity/EntityType;II)Lnet/minecraft/core/BlockPos;"))
	private static BlockPos getTopNonCollidingPos(LevelReader level, EntityType<?> entityType, int x, int z, Operation<BlockPos> original) {
		BlockPos originalPos = original.call(level, entityType, x, z);
		if (ESBiomes.isUndergroundCreature(entityType) && level instanceof ServerLevelAccessor serverLevelAccessor) {
			Optional<BlockPos> spawnPos = ESBiomes.findRandomUndergroundCreatureSpawnPos(serverLevelAccessor, entityType, originalPos.getY() - 1, x, z, MobSpawnType.CHUNK_GENERATION);
			if (spawnPos.isPresent()) {
				return spawnPos.get();
			}
		}
		return originalPos;
	}
}
