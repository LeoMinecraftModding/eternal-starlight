package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.ESTeleporter;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ESPortalBlock extends BaseEntityBlock implements Portal {
    public static final MapCodec<ESPortalBlock> CODEC = simpleCodec(ESPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty CENTER = BooleanProperty.create("center");
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 20);
    protected static final VoxelShape X_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public ESPortalBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(CENTER, false).setValue(SIZE, 2));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ESPortalBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ESBlockEntities.STARLIGHT_PORTAL.get(), ESPortalBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case Z -> Z_AABB;
            default -> X_AABB;
        };
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        Direction.Axis axis = state.getValue(AXIS);
        Direction leftDir, rightDir;
        if (axis == Direction.Axis.X) {
            leftDir = Direction.EAST;
            rightDir = Direction.WEST;
        } else {
            leftDir = Direction.NORTH;
            rightDir = Direction.SOUTH;
        }
        List<Direction> directions = List.of(leftDir, rightDir, Direction.UP, Direction.DOWN);
        for (Direction direction : directions) {
            if (!level.getBlockState(currentPos.relative(direction)).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS) && !(level.getBlockState(currentPos.relative(direction)).is(this) && level.getBlockState(currentPos.relative(direction)).getValue(AXIS) == axis)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return state;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(true) && !level.isClientSide) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return switch (rot) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, CENTER, SIZE);
    }

    public static boolean validateAndPlacePortal(LevelAccessor level, BlockPos framePos) {
        Validator validator = new Validator(level, framePos, Direction.Axis.X);
        if (!validator.isValid()) {
            validator = new Validator(level, framePos, Direction.Axis.Z);
            if (validator.isValid()) {
                validator.fillPortal();
                return true;
            }
        } else {
            validator.fillPortal();
            return true;
        }
        return false;
    }

    public static boolean placePortal(LevelAccessor level, BlockPos bottomPos, Direction.Axis axis, int size) {
        List<BlockPos> framePositions = new ArrayList<>();
        List<BlockPos> hollowPositions = new ArrayList<>();
        Direction rightDir;
        Direction leftDir;
        if (axis == Direction.Axis.X) {
            leftDir = Direction.EAST;
            rightDir = Direction.WEST;
        } else {
            leftDir = Direction.NORTH;
            rightDir = Direction.SOUTH;
        }
        for (int height = -size; height <= size; height++) {
            int hollowWidth = size - Math.abs(height);
            framePositions.add(new BlockPos(0, height + size, 0).relative(leftDir, hollowWidth));
            framePositions.add(new BlockPos(0, height + size, 0).relative(rightDir, hollowWidth));
            if (hollowWidth >= 1) {
                for (int i = 0; i < hollowWidth; i++) {
                    hollowPositions.add(new BlockPos(0, height + size, 0).relative(leftDir, i));
                    hollowPositions.add(new BlockPos(0, height + size, 0).relative(rightDir, i));
                }
            }
        }
        BlockPos center = bottomPos.above(size);
        for (BlockPos blockPos : framePositions) {
            if (!level.isEmptyBlock(blockPos.offset(bottomPos))) {
                return false;
            }
        }
        for (BlockPos blockPos : hollowPositions) {
            if (!level.isEmptyBlock(blockPos.offset(bottomPos))) {
                return false;
            }
        }
        boolean flag = true;
        for (BlockPos blockPos : framePositions) {
            flag &= level.setBlock(blockPos.offset(bottomPos), ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
        }
        for (BlockPos blockPos : hollowPositions) {
            flag &= level.setBlock(blockPos.offset(bottomPos), ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
        }
        for (BlockPos blockPos : hollowPositions) {
            flag &= level.setBlock(blockPos.offset(bottomPos), ESBlocks.STARLIGHT_PORTAL.get().defaultBlockState().setValue(AXIS, axis).setValue(CENTER, blockPos.offset(bottomPos).equals(center)).setValue(SIZE, size), 3);
        }
        return flag;
    }

    @Nullable
    @Override
    public DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        Level entityLevel = entity.level();
        MinecraftServer server = entityLevel.getServer();
        ResourceKey<Level> destination = entity.level().dimension() == ESDimensions.STARLIGHT_KEY
                ? Level.OVERWORLD : ESDimensions.STARLIGHT_KEY;
        if (server != null) {
            ServerLevel destinationLevel = server.getLevel(destination);
            if (destinationLevel != null) {
                if (ESPlatform.INSTANCE.postTravelToDimensionEvent(entity, destination)) {
                    return ESTeleporter.getPortalInfo(entity, blockPos, destinationLevel);
                }
            }
        }
        return null;
    }

    public static class Validator {
        private static final int MAX_SIZE = 10;
        private static final int MIN_SIZE = 2;
        private final LevelAccessor level;
        private final Direction.Axis axis;
        private final List<BlockPos> frames = new ArrayList<>();
        private final List<BlockPos> hollows = new ArrayList<>();
        private final BlockPos center;
        private final int portalSize;

        public Validator(LevelAccessor level, BlockPos framePos, Direction.Axis axis) {
            this.level = level;
            this.axis = axis;
            Direction rightDir;
            Direction leftDir;
            if (axis == Direction.Axis.X) {
                leftDir = Direction.EAST;
                rightDir = Direction.WEST;
            } else {
                leftDir = Direction.NORTH;
                rightDir = Direction.SOUTH;
            }

            for (int size = MIN_SIZE; size <= MAX_SIZE; size++) {
                // try build portal
                List<BlockPos> framePositions = new ArrayList<>();
                List<BlockPos> hollowPositions = new ArrayList<>();
                for (int height = -size; height <= size; height++) {
                    int hollowWidth = size - Math.abs(height);
                    framePositions.add(new BlockPos(0, height, 0).relative(leftDir, hollowWidth));
                    framePositions.add(new BlockPos(0, height, 0).relative(rightDir, hollowWidth));
                    if (hollowWidth >= 1) {
                        for (int i = 0; i < hollowWidth; i++) {
                            hollowPositions.add(new BlockPos(0, height, 0).relative(leftDir, i));
                            hollowPositions.add(new BlockPos(0, height, 0).relative(rightDir, i));
                        }
                    }
                }
                // try validate portal
                for (BlockPos blockPos : framePositions) {
                    // so assume our framePos is this frame position
                    BlockPos offset = framePos.subtract(blockPos);
                    List<BlockPos> offsetFrames = framePositions.stream().map(pos -> pos.offset(offset)).toList();
                    List<BlockPos> offsetHollows = hollowPositions.stream().map(pos -> pos.offset(offset)).toList();
                    boolean correctFrames = validateBlocks(offsetFrames, pos -> !level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS));
                    boolean correctHollows = validateBlocks(offsetHollows, pos -> !level.getBlockState(pos).is(ESBlocks.STARLIGHT_PORTAL.get()) && !level.getBlockState(pos).isAir());
                    if (correctFrames && correctHollows) {
                        frames.addAll(offsetFrames);
                        hollows.addAll(offsetHollows);
                        center = offset;
                        portalSize = size;
                        return;
                    }
                }
            }
            center = BlockPos.ZERO;
            portalSize = 0;
        }

        private boolean validateBlocks(List<BlockPos> positions, Function<BlockPos, Boolean> function) {
            for (BlockPos offsetFrame : positions) {
                if (function.apply(offsetFrame)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValid() {
            return !frames.isEmpty() && !hollows.isEmpty();
        }

        public void fillPortal() {
            for (BlockPos blockPos : frames) {
                level.setBlock(blockPos, ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
            }
            for (BlockPos blockPos : hollows) {
                level.setBlock(blockPos, ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
            }
            for (BlockPos blockPos : hollows) {
                level.setBlock(blockPos, ESBlocks.STARLIGHT_PORTAL.get().defaultBlockState().setValue(AXIS, axis).setValue(CENTER, blockPos.equals(center)).setValue(SIZE, portalSize), 3);
            }
        }
    }
}
