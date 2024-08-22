package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class DuskLightBlockEntity extends BlockEntity {
	private static final String TAG_FACING = "facing";
	private static final String TAG_LENGTH = "length";
	private static final String TAG_LIT = "lit";

	public static final float MAX_LENGTH = 15;

	private static final List<Direction> ORDER = Arrays.stream(Direction.values()).toList();

	private Direction facing = Direction.UP;
	private float length = MAX_LENGTH;
	private int ticksLeft = 0;
	private int offTicks = 0;
	private boolean lit = false;
	private BlockPos poweringBlock;
	private final Object2FloatMap<Direction> beamProgresses = new Object2FloatArrayMap<>();
	private final Object2FloatMap<Direction> oldBeamProgresses = new Object2FloatArrayMap<>();

	public DuskLightBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.DUSK_LIGHT.get(), blockPos, blockState);
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
		compoundTag.putInt(TAG_FACING, facing.get3DDataValue());
		compoundTag.putFloat(TAG_LENGTH, length);
		compoundTag.putBoolean(TAG_LIT, lit);
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		facing = Direction.from3DDataValue(compoundTag.getInt(TAG_FACING));
		length = compoundTag.getFloat(TAG_LENGTH);
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

	public static void tick(Level level, BlockPos pos, BlockState state, DuskLightBlockEntity entity) {
		if (level.isClientSide) {
			entity.oldBeamProgresses.clear();
			entity.oldBeamProgresses.putAll(entity.beamProgresses);
			for (Direction direction : Direction.values()) {
				if (!entity.beamProgresses.containsKey(direction)) {
					entity.beamProgresses.put(direction, 0);
				}
				if (direction.equals(entity.facing) && entity.lit) {
					entity.beamProgresses.put(direction, Mth.clamp(entity.beamProgresses.getFloat(direction) + 0.05f, 0, entity.length / MAX_LENGTH));
				} else {
					entity.beamProgresses.put(direction, Mth.clamp(entity.beamProgresses.getFloat(direction) - 0.15f, 0, 1));
				}
			}
			if (entity.lit) {
				EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(ManaType.BLAZE, pos.getCenter().add(new Vec3(entity.facing.getStepX(), entity.facing.getStepY(), entity.facing.getStepZ()).scale(entity.length)));
			}
		} else {
			float oldLength = entity.length;
			boolean oldLit = entity.lit;
			BlockHitResult result = level.clip(new ClipContext(pos.getCenter().add(new Vec3(entity.facing.getStepX(), entity.facing.getStepY(), entity.facing.getStepZ()).scale(0.51)), pos.getCenter().add(new Vec3(entity.facing.getStepX(), entity.facing.getStepY(), entity.facing.getStepZ()).scale(MAX_LENGTH)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
			if (result.getType() != HitResult.Type.MISS) {
				entity.length = (float) result.getLocation().subtract(pos.getCenter()).length();
				if (entity.lit && level.getBlockEntity(result.getBlockPos()) instanceof DuskLightBlockEntity light && (light.poweringBlock == null || !light.poweringBlock.equals(pos))) {
					light.ticksLeft = 5;
					entity.poweringBlock = result.getBlockPos();
				}
				if (level.getBlockState(result.getBlockPos()).is(ESBlocks.ECLIPSE_CORE.get())) {
					level.destroyBlock(result.getBlockPos(), false);
				}
			} else {
				entity.length = MAX_LENGTH;
				entity.poweringBlock = null;
			}
			if (level.getBlockState(pos.below()).is(ESTags.Blocks.DUSK_LIGHT_ENERGY_SOURCES)) {
				entity.ticksLeft = 5;
			}
			entity.ticksLeft--;
			if (entity.ticksLeft < 0) {
				entity.ticksLeft = 0;
			}
			entity.lit = entity.ticksLeft > 0;
			if (entity.lit) {
				entity.offTicks = 0;
				List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.getCenter().subtract(0.5, 0.5, 0.5), pos.getCenter().relative(entity.facing, entity.length).add(0.5, 0.5, 0.5)));
				for (Entity e : entities) {
					e.setRemainingFireTicks(Math.max(e.getRemainingFireTicks(), 100));
				}
			} else {
				entity.offTicks++;
				if (entity.offTicks > 10) {
					entity.poweringBlock = null;
				}
			}
			if (Math.abs(entity.length - oldLength) < 0.01 || oldLit != entity.lit) {
				entity.markUpdated();
			}
		}
	}

	public void setFacing() {
		this.facing = ORDER.get((ORDER.indexOf(facing) + 1) % ORDER.size());
		markUpdated();
	}

	private void markUpdated() {
		if (getLevel() != null) {
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}
}
