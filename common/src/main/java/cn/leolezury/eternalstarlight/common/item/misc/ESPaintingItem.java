package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.entity.misc.ESPainting;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;
import java.util.Optional;

public class ESPaintingItem extends HangingEntityItem {
	private static final Component TOOLTIP_RANDOM_VARIANT = Component.translatable("painting.random").withStyle(ChatFormatting.GRAY);

	public ESPaintingItem(Properties properties) {
		super(ESEntities.PAINTING.get(), properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos blockPos = context.getClickedPos();
		Direction direction = context.getClickedFace();
		BlockPos relativePos = blockPos.relative(direction);
		Player player = context.getPlayer();
		ItemStack itemStack = context.getItemInHand();
		if (player != null && !this.mayPlace(player, direction, itemStack, relativePos)) {
			return InteractionResult.FAIL;
		} else {
			Level level = context.getLevel();
			Optional<ESPainting> optional = ESPainting.createPainting(level, itemStack.copyWithCount(1), relativePos, direction);
			if (optional.isEmpty()) {
				return InteractionResult.CONSUME;
			}
			HangingEntity hangingEntity = optional.get();
			CustomData customData = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
			if (!customData.isEmpty()) {
				EntityType.updateCustomEntityTag(level, player, hangingEntity, customData);
			}
			if (hangingEntity.survives()) {
				if (!level.isClientSide) {
					hangingEntity.playPlacementSound();
					level.gameEvent(player, GameEvent.ENTITY_PLACE, hangingEntity.position());
					level.addFreshEntity(hangingEntity);
				}
				itemStack.shrink(1);
				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				return InteractionResult.CONSUME;
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
		HolderLookup.Provider provider = tooltipContext.registries();
		if (provider != null) {
			CustomData customData = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
			if (!customData.isEmpty()) {
				customData.read(provider.createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC).result().ifPresentOrElse((holder) -> {
					holder.unwrapKey().ifPresent((resourceKey) -> {
						list.add(Component.translatable(resourceKey.location().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW));
						list.add(Component.translatable(resourceKey.location().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY));
					});
					list.add(Component.translatable("painting.dimensions", holder.value().width(), holder.value().height()));
				}, () -> list.add(TOOLTIP_RANDOM_VARIANT));
			} else if (tooltipFlag.isCreative()) {
				list.add(TOOLTIP_RANDOM_VARIANT);
			}
		}
	}
}
