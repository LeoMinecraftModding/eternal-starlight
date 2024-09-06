package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import java.util.Optional;

public class OrbOfProphecyItem extends Item {
	public OrbOfProphecyItem(Properties properties) {
		super(properties);
	}

	public static void recordCrests(RegistryAccess access, ItemStack stack, CompoundTag tag) {
		List<Crest.Instance> crests = ESCrestUtil.getCrests(access, tag).crests();
		List<Crest.Instance> itemCrests = ESCrestUtil.getCrests(access, stack.getComponents().getOrDefault(ESDataComponents.CRESTS.get(), CustomData.EMPTY).copyTag()).crests();
		Optional<Tag> crestsTag = ESCrestUtil.setCrests(access, ESCrestUtil.mergeCrests(crests, itemCrests));
		if (crestsTag.isPresent() && crestsTag.get() instanceof CompoundTag compoundTag) {
			stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.CRESTS.get(), CustomData.of(compoundTag)).build());
		}
	}

	public static boolean hasCrests(RegistryAccess access, ItemStack stack) {
		return !getCrests(access, stack).isEmpty();
	}

	public static List<Crest.Instance> getCrests(RegistryAccess access, ItemStack stack) {
		return ESCrestUtil.getCrests(access, stack.getComponents().getOrDefault(ESDataComponents.CRESTS.get(), CustomData.EMPTY).copyTag()).crests();
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
		if (!level.isClientSide) {
			if (!itemStack.has(ESDataComponents.CURRENT_CREST.get())) {
				if (livingEntity.getTicksUsingItem() >= 140 && livingEntity instanceof ServerPlayer player) {
					ESPlatform.INSTANCE.sendToClient(player, new OpenCrestGuiPacket(ESCrestUtil.getCrests(player), ESCrestUtil.getOwnedCrests(player)));
					player.stopUsingItem();
					player.getCooldowns().addCooldown(this, 20);
				}
			} else {
				boolean success = false;
				CurrentCrestComponent component = itemStack.get(ESDataComponents.CURRENT_CREST.get());
				if (component != null && component.crest().isBound()) {
					Crest crest = component.crest().value();
					if (crest.spell().isPresent()) {
						success = true;
						if (livingEntity instanceof SpellCaster caster && (caster.getESSpellData().spell() != crest.spell().get() || !caster.getESSpellData().hasSpell())) {
							livingEntity.stopUsingItem();
							if (livingEntity instanceof Player player) {
								player.getCooldowns().addCooldown(this, crest.spell().get().spellProperties().cooldownTicks());
							}
						}
					}
				}
				if (!success) {
					livingEntity.stopUsingItem();
					if (livingEntity instanceof Player player) {
						player.getCooldowns().addCooldown(this, 20);
					}
				}
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
		if (itemStack.has(ESDataComponents.CURRENT_CREST.get()) && !level.isClientSide) {
			CurrentCrestComponent component = itemStack.get(ESDataComponents.CURRENT_CREST.get());
			if (component != null && component.crest().isBound()) {
				Crest crest = component.crest().value();
				if (crest.spell().isPresent()) {
					crest.spell().get().stop(livingEntity, getUseDuration(itemStack, livingEntity) - i - crest.spell().get().spellProperties().preparationTicks());
					if (livingEntity instanceof Player player) {
						player.getCooldowns().addCooldown(this, crest.spell().get().spellProperties().cooldownTicks());
					}
				}
			}
		}
	}

	@Override
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
					if (isTemporary(itemStack)) {
						itemStack.consume(1, player);
					}
					return InteractionResultHolder.success(itemStack);
				}
			} else if (!itemStack.has(ESDataComponents.CURRENT_CREST.get())) {
				player.startUsingItem(interactionHand);
				return InteractionResultHolder.consume(itemStack);
			} else if (!level.isClientSide) {
				CurrentCrestComponent component = itemStack.get(ESDataComponents.CURRENT_CREST.get());
				if (component != null && component.crest().isBound()) {
					Crest crest = component.crest().value();
					if (crest.spell().isPresent() && crest.spell().get().canCast(player, true)) {
						crest.spell().get().start(player, ESCrestUtil.getCrestLevel(player, component.crest()), true);
						player.startUsingItem(interactionHand);
						if (player instanceof SpellCaster caster) {
							caster.setESSpellSource(new SpellCastData.ItemSpellSource(this, interactionHand));
						}
						return InteractionResultHolder.consume(itemStack);
					}
				} else {
					player.startUsingItem(interactionHand);
					return InteractionResultHolder.consume(itemStack);
				}
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
			if ((challenged || serverPlayer.hasInfiniteMaterials()) && level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)) {
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
