package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractDuskLightBlockEntity extends BlockEntity implements DuskLightReceptor {
	private static final String TAG_LENGTH = "length";
	private static final String TAG_LIT = "lit";

	public static final float MAX_LENGTH = 30;

	protected int ticksLeft = 0;
	private boolean lit = false;
	private final Object2FloatMap<Direction> lengths = new Object2FloatArrayMap<>();
	private final Object2FloatMap<Direction> beamProgresses = new Object2FloatArrayMap<>();
	private final Object2FloatMap<Direction> oldBeamProgresses = new Object2FloatArrayMap<>();

	protected boolean alwaysActivated = false;

	public AbstractDuskLightBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
	}

	public boolean isLit() {
		return lit;
	}

	public Object2FloatMap<Direction> getBeamProgresses() {
		return beamProgresses;
	}

	public Object2FloatMap<Direction> getOldBeamProgresses() {
		return oldBeamProgresses;
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		for (Direction direction : Direction.values()) {
			compoundTag.putFloat(TAG_LENGTH + "_" + direction.getName(), lengths.getOrDefault(direction, 0));
		}
		compoundTag.putBoolean(TAG_LIT, lit);
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		for (Direction direction : Direction.values()) {
			lengths.put(direction, compoundTag.getFloat(TAG_LENGTH + "_" + direction.getName()));
		}
		lit = compoundTag.getBoolean(TAG_LIT);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveWithFullMetadata(provider);
	}

	protected abstract boolean isFaceActivated(BlockState state, Direction direction);

	@Override
	public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
		ticksLeft = 5;
	}

	public static boolean canPassThrough(BlockState state) {
		if (state.is(ESTags.Blocks.DUSK_LIGHT_ALWAYS_PASSABLE)) {
			return true;
		}
		if (state.is(ESTags.Blocks.DUSK_LIGHT_ALWAYS_UNPASSABLE)) {
			return false;
		}
		return state.getBlock() instanceof HalfTransparentBlock;
	}

	public static boolean canDestroy(BlockState state) {
		return state.is(ESTags.Blocks.DUSK_LIGHT_DESTROYABLES);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AbstractDuskLightBlockEntity entity) {
		if (level.isClientSide) {
			entity.oldBeamProgresses.clear();
			entity.oldBeamProgresses.putAll(entity.beamProgresses);
			for (Direction direction : Direction.values()) {
				if (!entity.beamProgresses.containsKey(direction)) {
					entity.beamProgresses.put(direction, 0);
				}
				if (entity.isFaceActivated(state, direction) && entity.lit) {
					entity.beamProgresses.put(direction, Mth.clamp(entity.beamProgresses.getFloat(direction) + 0.3f, 0, entity.lengths.getOrDefault(direction, 0) / MAX_LENGTH));
					EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(ManaType.BLAZE, pos.getCenter().add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(entity.lengths.getOrDefault(direction, 0) + 0.5)));
				} else {
					entity.beamProgresses.put(direction, Mth.clamp(entity.beamProgresses.getFloat(direction) - 0.3f, 0, 1));
				}
			}
		} else {
			Object2FloatMap<Direction> oldLengths = new Object2FloatArrayMap<>(entity.lengths);
			boolean oldLit = entity.lit;
			if (entity.lit) {
				for (Direction direction : Direction.values()) {
					if (entity.isFaceActivated(state, direction)) {
						Vec3 fromPos = pos.getCenter().add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(0.51));
						Vec3 toPos = fromPos.add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(MAX_LENGTH));
						BlockHitResult result = level.clip(new ClipContext(fromPos, toPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()) {
							@Override
							public VoxelShape getBlockShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
								return canPassThrough(blockState) ? Shapes.empty() : super.getBlockShape(blockState, blockGetter, blockPos);
							}
						});
						if (result.getType() != HitResult.Type.MISS) {
							entity.lengths.put(direction, (float) result.getLocation().subtract(fromPos).length());
							if (level.getBlockEntity(result.getBlockPos()) instanceof DuskLightReceptor receptor) {
								receptor.lightUp(level, result.getBlockPos(), direction.getOpposite());
							}
							if (canDestroy(level.getBlockState(result.getBlockPos()))) {
								level.destroyBlock(result.getBlockPos(), true);
							}
						} else {
							entity.lengths.put(direction, MAX_LENGTH);
						}
						List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(
							fromPos.subtract(direction.getAxis() == Direction.Axis.X ? 0 : 0.25, direction.getAxis() == Direction.Axis.Y ? 0 : 0.25, direction.getAxis() == Direction.Axis.Z ? 0 : 0.25),
							fromPos.relative(direction, entity.lengths.getOrDefault(direction, 0)).add(direction.getAxis() == Direction.Axis.X ? 0 : 0.25, direction.getAxis() == Direction.Axis.Y ? 0 : 0.25, direction.getAxis() == Direction.Axis.Z ? 0 : 0.25)
						));
						for (Entity e : entities) {
							if (!(e instanceof ItemEntity)) {
								e.setRemainingFireTicks(Math.max(e.getRemainingFireTicks(), 100));
							}
						}
					}
				}
			}
			if (entity.alwaysActivated) {
				entity.ticksLeft = 5;
			}
			entity.ticksLeft--;
			if (entity.ticksLeft < 0) {
				entity.ticksLeft = 0;
			}
			entity.lit = entity.ticksLeft > 0;
			boolean lengthsChanged = false;
			for (Direction direction : Direction.values()) {
				if (Math.abs(entity.lengths.getOrDefault(direction, 0) - oldLengths.getOrDefault(direction, 0)) > 0.01) {
					lengthsChanged = true;
					break;
				}
			}
			if (lengthsChanged || oldLit != entity.lit) {
				entity.markUpdated();
			}
		}
	}

	public void markUpdated() {
		if (getLevel() != null) {
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}
}
