package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GeyserParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AbyssalGeyserBlockEntity extends BlockEntity {
	private static final String TAG_ERUPTION_TIMER = "eruption_timer";
	private static final String TAG_ERUPTION_STRENGTH = "eruption_strength";

	private int eruptionTimer = 0;
	private int eruptionStrength = 1;

	public AbyssalGeyserBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.ABYSSAL_GEYSER.get(), blockPos, blockState);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AbyssalGeyserBlockEntity entity) {
		entity.eruptionTimer++;
		entity.eruptionTimer = entity.eruptionTimer % 1000;
		if (entity.eruptionTimer % 10 == 0) {
			entity.setChanged();
		}
		if (entity.eruptionTimer <= 200) {
			if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
				if (entity.eruptionTimer % 20 == 0) {
					Vec3 particlePos = pos.getCenter().add(0, 0.51, 0);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(GeyserParticleOptions.getAbyssalGeyser(entity.eruptionStrength), particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0, false));
				}
				if (entity.eruptionTimer == 200) {
					AABB itemBox = new AABB(pos);
					itemBox = itemBox.setMaxY(itemBox.maxY + 2);
					for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, itemBox)) {
						ItemStack item = itemEntity.getItem();
						level.getRecipeManager().getRecipeFor(ESRecipes.GEYSER_SMOKING.get(), new SingleRecipeInput(item), level).ifPresent(recipeHolder -> {
							GeyserSmokingRecipe recipe = recipeHolder.value();
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
						});
					}
					entity.eruptionTimer += level.getRandom().nextInt(200);
					entity.eruptionStrength = level.getRandom().nextInt(4) + 1;
					entity.setChanged();
				}
			}
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.eruptionTimer = compoundTag.getInt(TAG_ERUPTION_TIMER);
		this.eruptionStrength = compoundTag.getInt(TAG_ERUPTION_STRENGTH);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_ERUPTION_TIMER, this.eruptionTimer);
		compoundTag.putInt(TAG_ERUPTION_STRENGTH, this.eruptionStrength);
	}
}
