package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VoidstoneSpikeBlock extends SpeleothemBlock {
	public static final MapCodec<VoidstoneSpikeBlock> CODEC = simpleCodec(VoidstoneSpikeBlock::new);

	@Override
	public MapCodec<VoidstoneSpikeBlock> codec() {
		return CODEC;
	}

	public VoidstoneSpikeBlock(BlockBehaviour.Properties properties) {
		super(ESBlocks.VOIDSTONE.get().defaultBlockState(), properties);
	}

	@Override
	protected int getStalactiteLandingSound() {
		return LevelEvent.SOUND_POINTED_DRIPSTONE_LAND;
	}
}
