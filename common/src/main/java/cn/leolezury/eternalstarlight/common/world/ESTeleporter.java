package cn.leolezury.eternalstarlight.common.world;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ESTeleporter {
    protected static Optional<BlockPos> getOrMakePortal(ServerLevel level, Entity entity, BlockPos entrancePos, BlockPos pos) {
        Optional<BlockPos> existingPortal = getExistingPortal(level, pos);
        if (existingPortal.isPresent()) {
            return existingPortal;
        } else {
            Direction.Axis portalAxis = entity.level().getBlockState(entrancePos).getOptionalValue(ESPortalBlock.AXIS).orElse(Direction.Axis.X);
            return makePortal(level, pos, portalAxis);
        }
    }

    public static Optional<BlockPos> getExistingPortal(ServerLevel level, BlockPos pos) {
        int maxHeight = level.getMaxBuildHeight();
        int minHeight = level.getMinBuildHeight();
        WorldBorder border = level.getWorldBorder();
        for (int x = pos.getX() - 16; x <= pos.getX() + 16; x++) {
            for (int z = pos.getZ() - 16; z <= pos.getZ() + 16; z++) {
                for (int y = minHeight; y <= maxHeight; y++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (border.isWithinBounds(blockPos) && level.getBlockState(blockPos).is(ESBlocks.STARLIGHT_PORTAL.get())) {
                        return Optional.of(blockPos);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<BlockPos> makePortal(ServerLevel level, BlockPos blockPos, Direction.Axis axis) {
        int maxHeight = level.getMaxBuildHeight();
        int minHeight = level.getMinBuildHeight();
        WorldBorder border = level.getWorldBorder();
        for (BlockPos.MutableBlockPos pos : BlockPos.spiralAround(blockPos, 32, Direction.EAST, Direction.SOUTH)) {
            // search near world surface
            int worldSurface = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
            int from = Math.max(minHeight, worldSurface - 10);
            int to = Math.min(maxHeight, worldSurface + 10);
            if (from < to) {
                for (int height = from; height < to; height++) {
                    pos.setY(height);
                    if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
                        return Optional.of(pos.relative(Direction.UP));
                    }
                }
            }
            // search upwards
            pos.setY(blockPos.getY());
            for (int height = blockPos.getY(); height < maxHeight; height++) {
                pos.setY(height);
                if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
                    return Optional.of(pos.relative(Direction.UP));
                }
            }
            // search downwards
            pos.setY(blockPos.getY());
            for (int height = blockPos.getY(); height > minHeight; height--) {
                pos.setY(height);
                if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
                    return Optional.of(pos.relative(Direction.UP));
                }
            }
        }
        return Optional.empty();
    }

    @Nullable
    public static DimensionTransition getPortalInfo(Entity entity, BlockPos entrancePos, ServerLevel dest) {
        if (entity.level().dimension() != ESDimensions.STARLIGHT_KEY && dest.dimension() != ESDimensions.STARLIGHT_KEY) {
            return null;
        } else {
            WorldBorder border = dest.getWorldBorder();
            double minX = Math.max(-2.9999872E7D, border.getMinX() + 16.0D);
            double minZ = Math.max(-2.9999872E7D, border.getMinZ() + 16.0D);
            double maxX = Math.min(2.9999872E7D, border.getMaxX() - 16.0D);
            double maxZ = Math.min(2.9999872E7D, border.getMaxZ() - 16.0D);
            double coordinateDifference = DimensionType.getTeleportationScale(entity.level().dimensionType(), dest.dimensionType());
            BlockPos blockpos = new BlockPos((int) Mth.clamp(entity.getX() * coordinateDifference, minX, maxX), (int) entity.getY(), (int) Mth.clamp(entity.getZ() * coordinateDifference, minZ, maxZ));
            return getOrMakePortal(dest, entity, entrancePos, blockpos).map((result) -> new DimensionTransition(dest, Vec3.atCenterOf(result), Vec3.ZERO, entity.getYRot(), entity.getXRot(), DimensionTransition.PLACE_PORTAL_TICKET)).orElse(null);
        }
    }
}
