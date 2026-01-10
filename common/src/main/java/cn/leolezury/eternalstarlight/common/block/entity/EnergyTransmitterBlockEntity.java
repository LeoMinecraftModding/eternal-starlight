package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.EnergyTransmitterBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.math.OctahedralGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class EnergyTransmitterBlockEntity extends BlockEntity {
	private static final String TAG_INPUT_OFFSET = "input_offset";
	private static final String TAG_OUTPUT_OFFSET = "output_offset";

	private Vec3i inputOffset = Vec3i.ZERO;
	private Vec3i outputOffset = Vec3i.ZERO;

	public void setInputOffset(Vec3i inputOffset) {
		OctahedralGroup group = getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
		this.inputOffset = transformOffset(inputOffset, group.inverse());
		setChanged();
	}

	public Vec3i getInputOffset() {
		OctahedralGroup group = getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
		return transformOffset(inputOffset, group);
	}

	public BlockState getInputState() {
		return getLevel() != null ? getLevel().getBlockState(getBlockPos().offset(getInputOffset())) : Blocks.AIR.defaultBlockState();
	}

	public void setOutputOffset(Vec3i outputOffset) {
		OctahedralGroup group = getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
		this.outputOffset = transformOffset(outputOffset, group.inverse());
		setChanged();
	}

	public Vec3i getOutputOffset() {
		OctahedralGroup group = getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
		return transformOffset(outputOffset, group);
	}

	private Vec3i transformOffset(Vec3i original, OctahedralGroup group) {
		Vector3f offset = group.rotate(Direction.EAST).step().mul(original.getX())
			.add(group.rotate(Direction.UP).step().mul(original.getY()))
			.add(group.rotate(Direction.SOUTH).step().mul(original.getZ()));
		return new Vec3i(Math.round(offset.x), Math.round(offset.y), Math.round(offset.z));
	}

	public EnergyTransmitterBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.ENERGY_TRANSMITTER.get(), blockPos, blockState);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public static void tick(Level level, BlockPos pos, BlockState state, EnergyTransmitterBlockEntity entity) {
		if (!level.isClientSide) {
			if (entity.getInputOffset().distManhattan(Vec3i.ZERO) > EnergyTransmitterBlock.MAX_CONNECTION_DISTANCE) {
				entity.setInputOffset(Vec3i.ZERO);
				entity.setChanged();
			}
			if (entity.getOutputOffset().distManhattan(Vec3i.ZERO) > EnergyTransmitterBlock.MAX_CONNECTION_DISTANCE) {
				entity.setOutputOffset(Vec3i.ZERO);
			}
			if (!entity.getInputOffset().equals(Vec3i.ZERO) && !entity.getOutputOffset().equals(Vec3i.ZERO)) {
				entity.setOutputOffset(Vec3i.ZERO);
			}
			int directOutputPower = 0;
			boolean receiver = !entity.getInputOffset().equals(Vec3i.ZERO);
			if (receiver) {
				BlockPos inputPos = pos.offset(entity.getInputOffset());
				if (level.getBlockEntity(inputPos) instanceof EnergyTransmitterBlockEntity inputEntity && inputEntity.getOutputOffset().equals(entity.getInputOffset().multiply(-1))) {
					BlockState inputState = level.getBlockState(inputPos);
					if (inputState.hasProperty(EnergyTransmitterBlock.DIRECT_POWER)) {
						directOutputPower = Math.max(inputState.getValue(EnergyTransmitterBlock.DIRECT_POWER) - 1, 0);
					}
				}
			}
			boolean powered = receiver ? directOutputPower > 0 : state.hasProperty(EnergyTransmitterBlock.DIRECT_POWER) && state.getValue(EnergyTransmitterBlock.DIRECT_POWER) > 0;
			if (state.hasProperty(EnergyTransmitterBlock.POWER)
				&& state.hasProperty(EnergyTransmitterBlock.POWERED)
				&& (state.getValue(EnergyTransmitterBlock.POWER) != directOutputPower
				|| state.getValue(EnergyTransmitterBlock.POWERED) != powered)) {
				level.setBlockAndUpdate(pos, state.setValue(EnergyTransmitterBlock.POWER, directOutputPower).setValue(EnergyTransmitterBlock.POWERED, powered));
			}
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		if (compoundTag.contains(TAG_INPUT_OFFSET)) {
			Vec3i.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_INPUT_OFFSET)).resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Energy Transmitter input offset: {}", s)).ifPresent(pos -> this.inputOffset = pos);
		}
		if (compoundTag.contains(TAG_OUTPUT_OFFSET)) {
			Vec3i.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_OUTPUT_OFFSET)).resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Energy Transmitter output offset: {}", s)).ifPresent(pos -> this.outputOffset = pos);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.put(TAG_INPUT_OFFSET, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, inputOffset).getOrThrow());
		compoundTag.put(TAG_OUTPUT_OFFSET, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, outputOffset).getOrThrow());
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
}
