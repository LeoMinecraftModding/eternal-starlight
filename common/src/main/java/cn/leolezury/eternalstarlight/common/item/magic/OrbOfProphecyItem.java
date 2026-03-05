package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class OrbOfProphecyItem extends Item {
	public OrbOfProphecyItem(Properties properties) {
		super(properties);
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
			if (!itemStack.has(ESDataComponents.CURRENT_CREST.get())) {
				player.startUsingItem(interactionHand);
				return InteractionResultHolder.consume(itemStack);
			} else if (!level.isClientSide && player instanceof SpellCaster && !ESDataAttachments.SPELL_CAST_DATA.getData(player).hasSpell()) {
				Holder<Crest> component = itemStack.get(ESDataComponents.CURRENT_CREST.get());
				if (component != null && component.isBound()) {
					Crest crest = component.value();
					if (crest.getSpell().isPresent() && crest.getSpell().get().canCast(player, true)) {
						ESDataAttachments.SPELL_SOURCE.setData(player, new SpellCastData.ItemSpellSource(this, interactionHand));
						crest.getSpell().get().start(player, ESCrestUtil.getCrestLevel(player, component), true);
						return InteractionResultHolder.consume(itemStack);
					}
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
		if (level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)) {
			if (ESPortalBlock.validateAndPlacePortal(level, pos)) {
				level.playSound(player, pos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return InteractionResult.PASS;
	}
}
