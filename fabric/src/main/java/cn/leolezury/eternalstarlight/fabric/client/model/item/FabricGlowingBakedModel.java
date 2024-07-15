package cn.leolezury.eternalstarlight.fabric.client.model.item;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FabricGlowingBakedModel extends ForwardingBakedModel {
	public FabricGlowingBakedModel(BakedModel model) {
		wrapped = model;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
		context.pushTransform(quad -> {
			quad.lightmap(ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT);
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
		context.pushTransform(quad -> {
			quad.lightmap(ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT, ClientHandlers.FULL_BRIGHT);
			return true;
		});
		super.emitItemQuads(stack, randomSupplier, context);
		context.popTransform();
	}
}
