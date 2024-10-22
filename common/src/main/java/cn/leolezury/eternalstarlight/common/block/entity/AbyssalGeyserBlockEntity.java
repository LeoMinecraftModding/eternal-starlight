package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AbyssalGeyserBlockEntity extends BlockEntity {
	private static final String TAG_TICKS_SINCE_LAST_ERUPT = "ticks_since_last_erupt";

	private int ticksSinceLastErupt = 0;
	private final RandomSource random;

	public AbyssalGeyserBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.ABYSSAL_GEYSER.get(), blockPos, blockState);
		this.random = new LegacyRandomSource(blockPos.asLong());
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AbyssalGeyserBlockEntity entity) {
		entity.ticksSinceLastErupt++;
		entity.ticksSinceLastErupt = entity.ticksSinceLastErupt % 800; // 30 sec + 10 sec
		if (entity.ticksSinceLastErupt <= 200) { // erupt for 10 sec
			if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
				Vec3 particlePos = pos.getCenter().add(0, 0.4, 0);
				ParticlePacket packet1 = new ParticlePacket(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
				ParticlePacket packet2 = new ParticlePacket(ParticleTypes.LARGE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
				ParticlePacket packet3 = new ParticlePacket(ParticleTypes.WHITE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet1);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet2);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet3);
				if (entity.ticksSinceLastErupt == 200) {
					// convert items above
					List<RecipeHolder<GeyserSmokingRecipe>> list = level.getRecipeManager().getAllRecipesFor(ESRecipes.GEYSER_SMOKING.get());
					AABB itemBox = new AABB(pos);
					itemBox = itemBox.setMaxY(itemBox.maxY + 2);
					for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, itemBox)) {
						ItemStack item = itemEntity.getItem();
						for (RecipeHolder<GeyserSmokingRecipe> recipeHolder : list) {
							GeyserSmokingRecipe recipe = recipeHolder.value();
							if (item.is(recipeHolder.value().input()) && item.getCount() >= recipe.inputCount()) {
								int count = item.getCount() - recipe.inputCount();
								Vec3 itemPos = itemEntity.position();
								if (count > 0) {
									itemEntity.setItem(item.copyWithCount(count));
								} else {
									itemEntity.discard();
								}
								DataComponentMap components = item.getComponents();
								ItemStack stack = recipe.output().copy();
								stack.applyComponents(components);
								ItemEntity outputEntity = new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, stack);
								outputEntity.setDefaultPickUpDelay();
								level.addFreshEntity(outputEntity);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.ticksSinceLastErupt = compoundTag.getInt(TAG_TICKS_SINCE_LAST_ERUPT);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_TICKS_SINCE_LAST_ERUPT, this.ticksSinceLastErupt);
	}
}
