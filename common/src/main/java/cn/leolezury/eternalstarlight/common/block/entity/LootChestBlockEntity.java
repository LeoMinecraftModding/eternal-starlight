package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LootChestBlockEntity extends BlockEntity {
	private static final String TAG_LOOT_TABLE = "loot_table";
	private static final String TAG_ITEMS_TO_EJECT = "items_to_eject";
	private static final String TAG_REWARD_TARGETS = "reward_targets";
	private static final String TAG_CURRENT_REWARD_TARGET = "current_reward_target";
	private static final String TAG_EJECTION_TICKS = "ejection_ticks";
	private static final String TAG_COOLDOWN = "cooldown";

	private ResourceKey<LootTable> lootTable;
	private final List<ItemStack> itemsToEject = new ArrayList<>();
	private final List<UUID> rewardTargets = new ArrayList<>();
	private UUID currentRewardTarget;
	private int ejectionTicks, cooldown;

	public AnimationState openAnimationState = new AnimationState();
	public AnimationState closeAnimationState = new AnimationState();
	public int clientTickCount = 0;
	public int flashStartTickCount = Integer.MIN_VALUE;

	public void setLootTable(ResourceKey<LootTable> lootTable) {
		this.lootTable = lootTable;
		setChanged();
	}

	public List<UUID> getRewardTargets() {
		return Collections.unmodifiableList(rewardTargets);
	}

	public void addRewardTarget(UUID rewardTarget) {
		this.rewardTargets.add(rewardTarget);
		setChanged();
	}

	public void rewardPlayer(ServerPlayer player, BlockPos pos, Block block) {
		player.level().blockEvent(pos, block, 1, 0);
		this.currentRewardTarget = player.getUUID();
		this.rewardTargets.remove(player.getUUID());
		if (this.lootTable != null) {
			itemsToEject.clear();
			ServerLevel serverLevel = player.serverLevel();
			MinecraftServer server = serverLevel.getServer();
			LootTable table = server.reloadableRegistries().getLootTable(lootTable);
			LootParams.Builder paramBuilder = new LootParams.Builder(serverLevel);
			LootParams params = paramBuilder.create(LootContextParamSets.EMPTY);
			itemsToEject.addAll(table.getRandomItems(params));
		}
		setChanged();
	}

	public boolean isFree() {
		return this.currentRewardTarget == null && this.cooldown <= 0;
	}

	public boolean isEjecting() {
		return ejectionTicks > 0;
	}

	public LootChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.LOOT_CHEST.get(), blockPos, blockState);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, LootChestBlockEntity blockEntity) {
		if (!level.isClientSide) {
			if (blockEntity.currentRewardTarget != null) {
				blockEntity.ejectionTicks++;
				if (blockEntity.ejectionTicks == 1) {
					level.sendBlockUpdated(pos, state, state, 3);
				}
				if (blockEntity.ejectionTicks % 10 == 0) {
					blockEntity.setChanged();
				}
				if ((level.getGameTime() + 5) % 10 == 0 && !blockEntity.itemsToEject.isEmpty()) {
					level.blockEvent(pos, state.getBlock(), 1, 2);
				}
				if (level.getGameTime() % 10 == 0) {
					if (blockEntity.itemsToEject.isEmpty()) {
						if (blockEntity.ejectionTicks > 20) {
							blockEntity.currentRewardTarget = null;
							blockEntity.cooldown = 20;
							blockEntity.ejectionTicks = 0;
							blockEntity.setChanged();
							level.sendBlockUpdated(pos, state, state, 3);
							level.blockEvent(pos, state.getBlock(), 1, 1);
						}
					} else {
						ItemStack stack = blockEntity.itemsToEject.removeFirst();
						Vec3 position = Vec3.atBottomCenterOf(pos).relative(Direction.UP, 0.625);
						double x = position.x();
						double y = position.y();
						double z = position.z();
						ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack);
						itemEntity.setDeltaMovement(
							level.random.triangle(0, 0.035),
							level.random.triangle(0.2, 0.035),
							level.random.triangle(0, 0.035)
						);
						level.addFreshEntity(itemEntity);
						ESDataAttachments.IMPORTANT_ITEM.setData(itemEntity, true);
						itemEntity.setTarget(blockEntity.currentRewardTarget);
						itemEntity.setGlowingTag(true);
						itemEntity.setExtendedLifetime();
						blockEntity.setChanged();
					}
				}
			}
			if (blockEntity.cooldown > 0) {
				blockEntity.cooldown--;
				if (blockEntity.cooldown % 10 == 0) {
					blockEntity.setChanged();
				}
			}
		} else {
			blockEntity.clientTickCount++;
		}
	}

	@Override
	public boolean triggerEvent(int id, int type) {
		if (id == 1) {
			if (type == 0) {
				openAnimationState.stop();
				closeAnimationState.stop();
				openAnimationState.startIfStopped(clientTickCount);
			} else if (type == 1) {
				openAnimationState.stop();
				closeAnimationState.stop();
				closeAnimationState.startIfStopped(clientTickCount);
			} else if (type == 2) {
				flashStartTickCount = clientTickCount;
			}
			return true;
		} else {
			return super.triggerEvent(id, type);
		}
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
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		if (compoundTag.contains(TAG_LOOT_TABLE, CompoundTag.TAG_STRING)) {
			setLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString(TAG_LOOT_TABLE))));
		}
		if (compoundTag.contains(TAG_ITEMS_TO_EJECT)) {
			ItemStack.OPTIONAL_CODEC.listOf().parse(provider.createSerializationContext(NbtOps.INSTANCE), compoundTag.get(TAG_ITEMS_TO_EJECT)).resultOrPartial((string) -> EternalStarlight.LOGGER.error("Failed to parse Loot Chest items: '{}'", string)).ifPresent(this.itemsToEject::addAll);
		}
		if (compoundTag.contains(TAG_REWARD_TARGETS, CompoundTag.TAG_LIST)) {
			ListTag listTag = compoundTag.getList(TAG_REWARD_TARGETS, CompoundTag.TAG_INT_ARRAY);
			for (Tag tag : listTag) {
				if (tag != null && tag.getType() == IntArrayTag.TYPE && ((IntArrayTag) tag).getAsIntArray().length == 4) {
					this.rewardTargets.add(NbtUtils.loadUUID(tag));
				}
			}
		}
		if (compoundTag.contains(TAG_CURRENT_REWARD_TARGET)) {
			currentRewardTarget = compoundTag.getUUID(TAG_CURRENT_REWARD_TARGET);
		}
		cooldown = compoundTag.getInt(TAG_COOLDOWN);
		ejectionTicks = compoundTag.getInt(TAG_EJECTION_TICKS);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		if (lootTable != null) {
			compoundTag.putString(TAG_LOOT_TABLE, lootTable.location().toString());
		}
		compoundTag.put(TAG_ITEMS_TO_EJECT, ItemStack.OPTIONAL_CODEC.listOf().encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this.itemsToEject).getOrThrow());
		ListTag listTag = new ListTag();
		for (UUID uuid : this.rewardTargets) {
			if (uuid != null) {
				listTag.add(NbtUtils.createUUID(uuid));
			}
		}
		compoundTag.put(TAG_REWARD_TARGETS, listTag);
		if (currentRewardTarget != null) {
			compoundTag.putUUID(TAG_CURRENT_REWARD_TARGET, currentRewardTarget);
		}
		compoundTag.putInt(TAG_COOLDOWN, cooldown);
		compoundTag.putInt(TAG_EJECTION_TICKS, ejectionTicks);
	}
}
