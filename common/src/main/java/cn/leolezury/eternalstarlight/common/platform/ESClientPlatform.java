package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.common.client.resource.BookLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface ESClientPlatform {
	ESClientPlatform INSTANCE = Util.make(() -> {
		final ServiceLoader<ESClientPlatform> loader = ServiceLoader.load(ESClientPlatform.class);
		final Iterator<ESClientPlatform> iterator = loader.iterator();
		if (!iterator.hasNext()) {
			throw new RuntimeException("Client platform instance not found!");
		} else {
			final ESClientPlatform platform = iterator.next();
			if (iterator.hasNext()) {
				throw new RuntimeException("More than one client platform instance was found!");
			}
			return platform;
		}
	});

	// reload listeners

	default BookLoader createBookLoader() {
		return new BookLoader();
	}

	// client-side

	default DimensionSpecialEffects getDimEffect() {
		return new ESDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false);
	}

	BakedModel getGlowingBakedModel(BakedModel origin);

	default void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
		dispatcher.getModelRenderer().tesselateBlock(level, dispatcher.getBlockModel(state), state, pos, stack, multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY);
	}

	void sendToServer(CustomPacketPayload packet);
}
