package cn.leolezury.eternalstarlight.common.world.feature;

import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public abstract class ESFeature<FC extends FeatureConfiguration> extends Feature<FC> {
    public ESFeature(Codec<FC> codec) {
        super(codec);
    }

    protected boolean isValidBlock(BlockState state, List<TagKey<Block>> tags) {
        for (TagKey<Block> tag : tags) {
            if (state.is(tag)) {
                return true;
            }
        }
        return false;
    }

    protected BlockPos rotateBlockPos(BlockPos centerPos, BlockPos pos, float pitch, float roll) {
        Vec3 posVec = pos.getCenter();
        Vec3 centerVec = centerPos.getCenter();
        Vector3f posVec3f = new Vector3f((float) posVec.x, (float) posVec.y, (float) posVec.z);
        Vector3f centerVec3f = new Vector3f((float) centerVec.x, (float) centerVec.y, (float) centerVec.z);

        Quaternionf quaternion = new Quaternionf().rotateX(pitch * Mth.PI / 180f).rotateZ(roll * Mth.PI / 180f);

        Matrix4f translationToOrigin = new Matrix4f().translation(-centerVec3f.x, -centerVec3f.y, -centerVec3f.z);
        Matrix4f inverseTranslation = new Matrix4f().translation(centerVec3f.x, centerVec3f.y, centerVec3f.z);

        Matrix4f rotationMatrix = new Matrix4f().rotate(quaternion);

        Matrix4f transformMatrix = new Matrix4f();
        transformMatrix.mul(inverseTranslation).mul(rotationMatrix).mul(translationToOrigin);

        posVec3f.mulPosition(transformMatrix);

        return new BlockPos((int) posVec3f.x, (int) posVec3f.y, (int) posVec3f.z);
    }

    protected boolean setBlockWithRotationIfEmpty(WorldGenLevel level, BlockPos centerPos, BlockPos pos, float pitch, float roll, BlockState state) {
        return setBlockIfEmpty(level, rotateBlockPos(centerPos, pos, pitch, roll), state);
    }

    protected boolean setBlockWithRotationIfEmpty(WorldGenLevel level, BlockPos centerPos, BlockPos pos, float pitch, float roll, BlockState state, List<TagKey<Block>> ignored) {
        return setBlockIfEmpty(level, rotateBlockPos(centerPos, pos, pitch, roll), state, ignored);
    }

    protected void setBlockWithRotation(WorldGenLevel level, BlockPos centerPos, BlockPos pos, float pitch, float yaw, BlockState state) {
        setBlock(level, rotateBlockPos(centerPos, pos, pitch, yaw), state);
    }

    protected boolean setBlockIfEmpty(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (level.isEmptyBlock(pos)) {
            setBlock(level, pos, state);
            return true;
        }
        return false;
    }

    protected boolean setBlockIfEmpty(WorldGenLevel level, BlockPos pos, BlockState state, List<TagKey<Block>> ignored) {
        if (level.isEmptyBlock(pos) || isValidBlock(level.getBlockState(pos), ignored)) {
            setBlock(level, pos, state);
            return true;
        }
        return false;
    }

    protected void placeOnTop(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState state, List<TagKey<Block>> ignored) {
        BlockPos pos = blockPos;
        for(; (isValidBlock(worldGenLevel.getBlockState(pos), ignored) || worldGenLevel.isEmptyBlock(pos) || !worldGenLevel.getFluidState(pos).equals(Fluids.EMPTY.defaultFluidState())) && pos.getY() > worldGenLevel.getMinBuildHeight() + 2; pos = pos.below());
        pos = pos.above();
        if (worldGenLevel.getBlockState(pos).isAir()) {
            setBlock(worldGenLevel, pos, state);
        }
    }

    protected BlockPos getChunkCoordinate(BlockPos origin) {
        return new BlockPos((origin.getX() / 16) * 16, origin.getY(), (origin.getZ() / 16) * 16);
    }
}
