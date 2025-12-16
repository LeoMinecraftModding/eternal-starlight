package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.EnergyTransmitterBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
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

public class EnergyTransmitterBlockEntity extends BlockEntity {
	private static final String TAG_INPUT_OFFSET = "input_offset";
	private static final String TAG_OUTPUT_OFFSET = "output_offset";

	private Vec3i inputOffset = Vec3i.ZERO;
	private Vec3i outputOffset = Vec3i.ZERO;

	public void setInputOffset(Vec3i inputOffset) {
		this.inputOffset = inputOffset;
		setChanged();
	}

	public Vec3i getInputOffset() {
		return inputOffset;
	}

	public BlockState getInputState() {
		return getLevel() != null ? getLevel().getBlockState(getBlockPos().offset(inputOffset)) : Blocks.AIR.defaultBlockState();
	}

	public void setOutputOffset(Vec3i outputOffset) {
		this.outputOffset = outputOffset;
		setChanged();
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
			if (entity.inputOffset.distManhattan(Vec3i.ZERO) > EnergyTransmitterBlock.MAX_CONNECTION_DISTANCE) {
				entity.inputOffset = Vec3i.ZERO;
				entity.setChanged();
			}
			if (entity.outputOffset.distManhattan(Vec3i.ZERO) > EnergyTransmitterBlock.MAX_CONNECTION_DISTANCE) {
				entity.outputOffset = Vec3i.ZERO;
			}
			if (!entity.inputOffset.equals(Vec3i.ZERO) && !entity.outputOffset.equals(Vec3i.ZERO)) {
				entity.outputOffset = Vec3i.ZERO;
			}
			int directOutputPower = 0;
			boolean receiver = !entity.inputOffset.equals(Vec3i.ZERO);
			if (receiver) {
				BlockPos inputPos = pos.offset(entity.inputOffset);
				if (level.getBlockEntity(inputPos) instanceof EnergyTransmitterBlockEntity inputEntity && inputEntity.outputOffset.equals(entity.inputOffset.multiply(-1))) {
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
