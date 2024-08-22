package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class CrestPotBlock extends Block {
	public static final MapCodec<? extends CrestPotBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(propertiesCodec()).apply(instance, CrestPotBlock::new));
	private static final VoxelShape BOUNDING_BOX = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);

	public CrestPotBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends CrestPotBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
		Registry<Crest> registry = level.registryAccess().registryOrThrow(ESRegistries.CREST);
		registry.getRandomElementOf(ESTags.Crests.IS_IN_CREST_POT, player.getRandom()).ifPresent(holder -> {
			if (holder.isBound()) {
				Optional<ResourceKey<Crest>> key = registry.getResourceKey(holder.value());
				if (key.isPresent()) {
					CrestEntity crest = new CrestEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), key.get());
					level.addFreshEntity(crest);
				}
			}
		});
		return super.playerWillDestroy(level, blockPos, blockState, player);
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return BOUNDING_BOX;
	}
}
