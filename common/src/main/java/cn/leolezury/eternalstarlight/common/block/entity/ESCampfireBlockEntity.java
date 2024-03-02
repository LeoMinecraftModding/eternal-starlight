package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ESCampfireBlockEntity extends CampfireBlockEntity {
    public ESCampfireBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity) {
        if (blockState.getValue(BlockStateProperties.LIT)) {
            cookTick(level, blockPos, blockState, campfireBlockEntity);
            AABB box = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(blockPos)).inflate(10);
            for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box)) {
                if (!(living instanceof Enemy)) {
                    living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20));
                }
            }
        } else {
            cooldownTick(level, blockPos, blockState, campfireBlockEntity);
        }
    }

    @Override
    public BlockEntityType<?> getType() {
        return ESBlockEntities.CAMPFIRE.get();
    }
}
