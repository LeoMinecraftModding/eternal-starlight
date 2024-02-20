package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
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
                ESParticlePacket packet1 = new ESParticlePacket(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
                ESParticlePacket packet2 = new ESParticlePacket(ParticleTypes.LARGE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
                ESParticlePacket packet3 = new ESParticlePacket(ParticleTypes.WHITE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
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
                            if (item.is(recipeHolder.value().input())) {
                                int count = item.getCount();
                                CompoundTag tag = item.getOrCreateTag();
                                ItemStack stack = new ItemStack(recipeHolder.value().output(), count);
                                stack.setTag(tag);
                                itemEntity.setItem(stack);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.ticksSinceLastErupt = compoundTag.getInt("TicksSinceLastErupt");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("TicksSinceLastErupt", this.ticksSinceLastErupt);
    }
}
