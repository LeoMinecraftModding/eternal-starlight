package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ESPaintingItem extends HangingEntityItem {
	public ESPaintingItem(Properties properties) {
		super(EntityType.PAINTING, properties);
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
			Optional<Painting> optional = createPainting(level, relativePos, direction);
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

	private static Optional<Painting> createPainting(Level level, BlockPos blockPos, Direction direction) {
		Painting painting = new Painting(level, blockPos);
		List<Holder<PaintingVariant>> list = new ArrayList<>();
		Iterable<Holder<PaintingVariant>> placeable = level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(ESTags.PaintingVariants.PLACEABLE);
		placeable.forEach(list::add);
		if (list.isEmpty()) {
			return Optional.empty();
		} else {
			painting.setDirection(direction);
			list.removeIf((holder) -> {
				painting.setVariant(holder);
				return !painting.survives();
			});
			if (list.isEmpty()) {
				return Optional.empty();
			} else {
				int i = list.stream().mapToInt(holder -> holder.value().area()).max().orElse(0);
				list.removeIf(holder -> holder.value().area() < i);
				Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, painting.getRandom());
				if (optional.isEmpty()) {
					return Optional.empty();
				} else {
					painting.setVariant(optional.get());
					painting.setDirection(direction);
					return Optional.of(painting);
				}
			}
		}
	}
}
