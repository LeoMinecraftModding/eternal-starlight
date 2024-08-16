package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrestPotBlock extends Block {
	public static final MapCodec<? extends CrestPotBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(propertiesCodec()).apply(instance, CrestPotBlock::new));
	public CrestPotBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends CrestPotBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
		List<ResourceKey<Crest>> crests = new ArrayList<>();
		for (var crest : level.holderLookup(ESRegistries.CREST).listElementIds().toList()) {
			if (crest != ESCrests.EMPTY) {
				crests.add(crest);
			}
		}
		int i = RandomSource.create().nextIntBetweenInclusive(0, crests.size() - 1);

		CrestEntity crest = new CrestEntity(crests.get(i), level, blockPos.getX(), blockPos.getY(), blockPos.getZ());
		level.addFreshEntity(crest);

		return super.playerWillDestroy(level, blockPos, blockState, player);
	}
}
