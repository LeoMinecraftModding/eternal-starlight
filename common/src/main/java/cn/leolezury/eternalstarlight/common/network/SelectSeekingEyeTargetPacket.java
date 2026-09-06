package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.common.item.misc.SeekingEyeTargets;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;

public record SelectSeekingEyeTargetPacket(SeekingEyeTargets.TargetType targetType, ResourceLocation targetId) implements CustomPacketPayload {
	public static final Type<SelectSeekingEyeTargetPacket> TYPE = new Type<>(EternalStarlight.id("select_seeking_eye_target"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SelectSeekingEyeTargetPacket> STREAM_CODEC = StreamCodec.composite(
		SeekingEyeTargets.TargetType.STREAM_CODEC, SelectSeekingEyeTargetPacket::targetType,
		ResourceLocation.STREAM_CODEC, SelectSeekingEyeTargetPacket::targetId,
		SelectSeekingEyeTargetPacket::new
	);

	public static void handle(SelectSeekingEyeTargetPacket packet, Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			SeekingEyeTargets.Entry entry = SeekingEyeTargets.TargetType.get(packet.targetType(), packet.targetId());
			if (entry == null) {
				return;
			}
			ServerLevel level = serverPlayer.serverLevel();
			if (!level.dimension().equals(ESDimensions.STARLIGHT_KEY)) {
				serverPlayer.displayClientMessage(Component.translatable("message." + EternalStarlight.ID + ".seeking_eye.wrong_dimension"), true);
				return;
			}
			InteractionHand hand = serverPlayer.getMainHandItem().is(ESItems.SEEKING_EYE.get()) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
			ItemStack stack = serverPlayer.getItemInHand(hand);
			if (!stack.is(ESItems.SEEKING_EYE.get())) {
				return;
			}
			BlockPos found = locate(packet.targetType(), entry.key(), level, serverPlayer);
			if (found == null) {
				serverPlayer.displayClientMessage(Component.translatable("message." + EternalStarlight.ID + ".seeking_eye.not_found"), true);
				return;
			}
			EyeOfSeeking eye = new EyeOfSeeking(level, serverPlayer.getX(), serverPlayer.getY(0.5D), serverPlayer.getZ());
			eye.setItem(stack);
			eye.setOwner(serverPlayer.getUUID());
			eye.signalTo(found);
			level.gameEvent(GameEvent.PROJECTILE_SHOOT, eye.position(), GameEvent.Context.of(serverPlayer));
			level.addFreshEntity(eye);
			level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), ESSoundEvents.SEEKING_EYE_LAUNCH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			stack.consume(1, serverPlayer);
			serverPlayer.awardStat(Stats.ITEM_USED.get(ESItems.SEEKING_EYE.get()));
			serverPlayer.swing(hand, true);
		}
	}

	private static BlockPos locate(SeekingEyeTargets.TargetType type, ResourceKey<?> key, ServerLevel level, ServerPlayer player) {
		if (type == SeekingEyeTargets.TargetType.BOSS_STRUCTURE || type == SeekingEyeTargets.TargetType.NORMAL_STRUCTURE) {
			Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
			Optional<Holder.Reference<Structure>> holder = registry.getHolder(key.location());
			if (holder.isPresent()) {
				Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator().findNearestMapStructure(level, HolderSet.direct(holder.get()), player.blockPosition(), 100, false);
				return pair != null ? pair.getFirst() : null;
			}
			return null;
		}
		if (type == SeekingEyeTargets.TargetType.BIOME) {
			Pair<BlockPos, Holder<Biome>> pair = level.findClosestBiome3d(biome -> biome.is(key.location()), player.blockPosition(), 6400, 32, 64);
			return pair != null ? pair.getFirst() : null;
		}
		return null;
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
