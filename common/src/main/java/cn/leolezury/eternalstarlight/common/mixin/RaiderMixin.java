package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// ok wtf
@Mixin(Raider.class)
public abstract class RaiderMixin {
	@Inject(method = "finalizeSpawn", at = @At(value = "TAIL"))
	private void finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
		if ((LivingEntity) (Object) this instanceof Witch witch) {
			Holder<Biome> biome = serverLevelAccessor.getBiome(witch.blockPosition());
			if (biome.is(ESBiomes.DARK_SWAMP)) {
				ESDataAttachments.WITCH_TYPE.setData(witch, "dark_swamp");
			}
		}
	}
}
