package cn.leolezury.eternalstarlight.forge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.neoforged.neoforge.common.PlantType;

public class ForgeWaterlilyBlock extends WaterlilyBlock {
    public ForgeWaterlilyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.WATER;
    }
}
