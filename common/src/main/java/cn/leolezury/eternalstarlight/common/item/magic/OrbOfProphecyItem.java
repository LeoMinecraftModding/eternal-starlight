package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class OrbOfProphecyItem extends Item {
	public OrbOfProphecyItem(Properties properties) {
		super(properties);
	}

	public static void recordCrests(RegistryAccess access, ItemStack stack, CompoundTag tag) {
		List<Crest.Instance> crests = ESCrestUtil.getCrests(access, tag);
		List<Crest.Instance> itemCrests = ESCrestUtil.getCrests(access, stack.getComponents().getOrDefault(ESDataComponents.CRESTS.get(), CustomData.EMPTY).copyTag());
		CompoundTag crestsTag = new CompoundTag();
		ESCrestUtil.setCrests(access, crestsTag, ESCrestUtil.mergeCrests(crests, itemCrests));
		stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.CRESTS.get(), CustomData.of(crestsTag)).build());
	}

	public static boolean hasCrests(RegistryAccess access, ItemStack stack) {
		return !getCrests(access, stack).isEmpty();
	}

	public static List<Crest.Instance> getCrests(RegistryAccess access, ItemStack stack) {
		return ESCrestUtil.getCrests(access, stack.getComponents().getOrDefault(ESDataComponents.CRESTS.get(), CustomData.EMPTY).copyTag());
	}

	public static void setTemporary(ItemStack stack) {
		stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.ORB_OF_PROPHECY_TEMPORARY.get(), true).build());
	}

	public static boolean isTemporary(ItemStack stack) {
		return stack.getOrDefault(ESDataComponents.ORB_OF_PROPHECY_TEMPORARY.get(), false);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
		if (livingEntity.getPose() != Pose.STANDING) {
			livingEntity.stopUsingItem();
		}
		if (livingEntity.getTicksUsingItem() >= 140 && livingEntity instanceof ServerPlayer player) {
			Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
			List<OpenCrestGuiPacket.CrestInstance> ownedCrests = ESCrestUtil.getCrests(player, "OwnedCrests").stream().map(c -> new OpenCrestGuiPacket.CrestInstance(Objects.requireNonNull(registry.getKey(c.crest())).toString(), c.level())).toList();
			List<OpenCrestGuiPacket.CrestInstance> crests = ESCrestUtil.getCrests(player).stream().map(c -> new OpenCrestGuiPacket.CrestInstance(Objects.requireNonNull(registry.getKey(c.crest())).toString(), c.level())).toList();
			ESPlatform.INSTANCE.sendToClient(player, new OpenCrestGuiPacket(ownedCrests, crests));
			player.stopUsingItem();
			player.getCooldowns().addCooldown(this, 20);
		}
	}

	public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (player.getPose() == Pose.STANDING) {
			if (hasCrests(level.registryAccess(), itemStack)) {
				int xpCost = isTemporary(itemStack) ? 2 : 1;
				if (player.experienceLevel >= xpCost) {
					player.experienceLevel -= xpCost;
					getCrests(level.registryAccess(), itemStack).forEach(crest -> ESCrestUtil.giveCrest(player, crest));
					itemStack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.CRESTS.get(), CustomData.EMPTY).build());
					if (isTemporary(itemStack) && !player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					return InteractionResultHolder.success(itemStack);
				}
			} else {
				player.startUsingItem(interactionHand);
				return InteractionResultHolder.consume(itemStack);
			}
		}
		return InteractionResultHolder.pass(itemStack);
	}

	@Override
	public InteractionResult useOn(UseOnContext useOnContext) {
		Level level = useOnContext.getLevel();
		Player player = useOnContext.getPlayer();
		BlockPos pos = useOnContext.getClickedPos();
		if (player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
			AdvancementHolder challenge = serverPlayer.getServer().getAdvancements().get(EternalStarlight.id("challenge_gatekeeper"));
			boolean challenged = challenge != null && serverPlayer.getAdvancements().getOrStartProgress(challenge).isDone();
			if ((challenged || serverPlayer.getAbilities().instabuild) && level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)) {
				if (level.dimension() == ESDimensions.STARLIGHT_KEY || level.dimension() == Level.OVERWORLD) {
					if (ESPortalBlock.validateAndPlacePortal(level, pos)) {
						level.playSound(player, pos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
						return InteractionResult.SUCCESS;
					}
				}
			}
		}
		return InteractionResult.PASS;
	}
}
