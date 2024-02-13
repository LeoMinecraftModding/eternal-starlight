package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import cn.leolezury.eternalstarlight.common.init.BlockEntityInit;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESTags;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ESPortalBlock extends BaseEntityBlock {
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
        return createTickerHelper(blockEntityType, BlockEntityInit.STARLIGHT_PORTAL.get(), ESPortalBlockEntity::tick);
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
        if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            } else {
                if (!entity.level().isClientSide && !pos.equals(entity.portalEntrancePos)) {
                    entity.portalEntrancePos = pos.immutable();
                }
                Level entityWorld = entity.level();
                MinecraftServer minecraftserver = entityWorld.getServer();
                ResourceKey<Level> destination = entity.level().dimension() == DimensionInit.STARLIGHT_KEY
                        ? Level.OVERWORLD : DimensionInit.STARLIGHT_KEY;
                if (minecraftserver != null) {
                    ServerLevel destinationWorld = minecraftserver.getLevel(destination);
                    if (destinationWorld != null && !entity.isPassenger()) {
                        entity.level().getProfiler().push("starlight_portal");
                        entity.setPortalCooldown();
                        if (ESPlatform.INSTANCE.postTravelToDimensionEvent(entity, destination)) {
                            ESPlatform.INSTANCE.teleportEntity(destinationWorld, entity);
                        }
                        entity.level().getProfiler().pop();
                    }
                }
            }
        }
    }

    /*@Override
    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(100) == 0) {
            worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                    (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double x = (double)pos.getX() + rand.nextDouble();
            double y = (double)pos.getY() + rand.nextDouble();
            double z = (double)pos.getZ() + rand.nextDouble();
            double xSpeed = ((double)rand.nextFloat() - 0.5D) * 0.1D;
            double ySpeed = ((double)rand.nextFloat() - 0.5D) * 0.1D;
            double zSpeed = ((double)rand.nextFloat() - 0.5D) * 0.1D;
            int j = rand.nextInt(2) * 2 - 1;
            if (!worldIn.getBlockState(pos.west()).is(this) && !worldIn.getBlockState(pos.east()).is(this)) {
                x = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                xSpeed = rand.nextFloat() * 2.0F * (float)j;
            } else {
                z = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                zSpeed = rand.nextFloat() * 2.0F * (float)j;
            }

            worldIn.addParticle(ParticleInit.STARLIGHT.get(), x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }*/

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
        for (BlockPos blockPos : framePositions) {
            level.setBlock(blockPos.offset(bottomPos), BlockInit.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
        }
        for (BlockPos blockPos : hollowPositions) {
            level.setBlock(blockPos.offset(bottomPos), BlockInit.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
        }
        for (BlockPos blockPos : hollowPositions) {
            level.setBlock(blockPos.offset(bottomPos), BlockInit.STARLIGHT_PORTAL.get().defaultBlockState().setValue(AXIS, axis).setValue(CENTER, blockPos.offset(bottomPos).equals(center)).setValue(SIZE, size), 3);
        }
        return true;
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
                    boolean correctHollows = validateBlocks(offsetHollows, pos -> !level.getBlockState(pos).is(BlockInit.STARLIGHT_PORTAL.get()) && !level.getBlockState(pos).isAir());
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
                level.setBlock(blockPos, BlockInit.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
            }
            for (BlockPos blockPos : hollows) {
                level.setBlock(blockPos, BlockInit.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
            }
            for (BlockPos blockPos : hollows) {
                level.setBlock(blockPos, BlockInit.STARLIGHT_PORTAL.get().defaultBlockState().setValue(AXIS, axis).setValue(CENTER, blockPos.equals(center)).setValue(SIZE, portalSize), 3);
            }
        }
    }
}
