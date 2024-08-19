package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.DuskLightBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class DuskLightBlockEntity extends BlockEntity {
	public int facing = 0;
	public int ticks = 0;
	public float distance = 0;
	public boolean lit = false;
	public DuskLightBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.DUSK_LIGHT.get(), blockPos, blockState);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		compoundTag.putInt("Facing", facing);
		compoundTag.putInt("Ticks", ticks);
		compoundTag.putFloat("Distance", distance);
		compoundTag.putBoolean("Lit", lit);

		super.saveAdditional(compoundTag, provider);
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);

		facing = compoundTag.getInt("Facing");
		ticks = compoundTag.getInt("Ticks");
		distance = compoundTag.getFloat("Distance");
		lit = compoundTag.getBoolean("Lit");
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

	@Override
	public boolean isValidBlockState(BlockState blockState) {
		return this.getType().isValid(blockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ESBlockEntities.DUSK_LIGHT.get();
	}

	public static void tick(Level level, BlockPos pos, BlockState state, DuskLightBlockEntity entity) {
		entity.ticks++;

		int rangeX = 0;
		int rangeY = 0;
		int rangeZ = 0;
		switch (entity.facing) {
			case 0: {
				rangeX = 18;
				break;
			}
			case 1: {
				rangeZ = 18;
				break;
			}
			case 2: {
				rangeX = -18;
				break;
			}
			case 3: {
				rangeZ = -18;
				break;
			}
			case 4: {
				rangeY = 18;
				break;
			}
			case 5: {
				rangeY = -18;
				break;
			}
			default:
		}
		if (level.getBlockState(pos.below()).is(ESBlocks.ATALPHAITE_LIGHT.get())) {
			entity.lit = true;
		}
		var blocks = level.getBlockStates(new AABB(entity.getBlockPos().getCenter().add((double) rangeX / 18, (double) rangeY / 18, (double) rangeZ /18), entity.getBlockPos().getCenter().add(rangeX, rangeY, rangeZ))).toList();
		for (int i = 0; i < 18; i++) {
			if (level.getBlockEntity(entity.getBlockPos().offset((i + 1) * (rangeX / 18), (i + 1) * (rangeY / 18), (i + 1) * (rangeZ / 18))) instanceof DuskLightBlockEntity next) {
				next.lit = true;
				break;
			}
			if (rangeX < 0 || rangeY < 0 || rangeZ < 0) {
				if (blocks.get(17 - i).is(ESBlocks.DUSK_GLASS.get())) {
					break;
				}
			} else {
				if (blocks.get(i).is(ESBlocks.DUSK_GLASS.get())) {
					break;
				}
			}
			entity.distance = i + 1;
		}
	}

	public void setFacing() {
		if (this.facing >= 5) {
			this.facing = 0;
		} else {
			this.facing++;
		}
		markUpdated();
	}
	private void markUpdated() {
		if (getLevel() != null) {
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}
}
