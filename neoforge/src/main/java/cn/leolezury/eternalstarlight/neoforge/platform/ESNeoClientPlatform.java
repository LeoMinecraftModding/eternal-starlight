package cn.leolezury.eternalstarlight.neoforge.platform;

import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.neoforge.client.ESNeoDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.neoforge.client.model.item.NeoGlowingBakedModel;
import cn.leolezury.eternalstarlight.neoforge.network.ESNeoNetworkHandler;
import com.google.auto.service.AutoService;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;

@AutoService(ESClientPlatform.class)
public class ESNeoClientPlatform implements ESClientPlatform {
	@OnlyIn(Dist.CLIENT)
	@Override
	public DimensionSpecialEffects getDimEffect() {
		return new ESNeoDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false);
	}

	@Override
	public BakedModel getGlowingBakedModel(BakedModel origin) {
		return new NeoGlowingBakedModel(origin);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderBlock(BlockRenderDispatcher dispatcher, PoseStack stack, MultiBufferSource multiBufferSource, Level level, BlockState state, BlockPos pos, long seed) {
		var model = dispatcher.getBlockModel(state);
		for (var renderType : model.getRenderTypes(state, RandomSource.create(seed), ModelData.EMPTY))
			dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, stack, multiBufferSource.getBuffer(renderType), false, RandomSource.create(), seed, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
	}

	@Override
	public void sendToServer(CustomPacketPayload packet) {
		ESNeoNetworkHandler.sendToServer(packet);
	}
}
