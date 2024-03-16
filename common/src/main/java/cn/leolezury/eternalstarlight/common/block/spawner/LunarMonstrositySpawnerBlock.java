package cn.leolezury.eternalstarlight.common.block.spawner;

import cn.leolezury.eternalstarlight.common.block.entity.spawner.LunarMonstrositySpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LunarMonstrositySpawnerBlock extends BaseEntityBlock {
    public static final MapCodec<LunarMonstrositySpawnerBlock> CODEC = simpleCodec(LunarMonstrositySpawnerBlock::new);

    public LunarMonstrositySpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LunarMonstrositySpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ESBlockEntities.LUNAR_MONSTROSITY_SPAWNER.get(), LunarMonstrositySpawnerBlockEntity::tick);
    }
}
