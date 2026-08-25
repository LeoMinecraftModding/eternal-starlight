package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class OrbOfProphecyItem extends Item {
	public OrbOfProphecyItem(Properties properties) {
		super(properties);
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
