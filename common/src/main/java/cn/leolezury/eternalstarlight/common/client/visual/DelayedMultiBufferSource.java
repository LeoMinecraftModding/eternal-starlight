package cn.leolezury.eternalstarlight.common.client.visual;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class DelayedMultiBufferSource extends MultiBufferSource.BufferSource {
	public DelayedMultiBufferSource(ByteBufferBuilder builder) {
		super(builder, new Object2ObjectLinkedOpenHashMap<>());
	}

	@Override
	public VertexConsumer getBuffer(RenderType renderType) {
		if (!fixedBuffers.containsKey(renderType)) {
			fixedBuffers.put(renderType, new ByteBufferBuilder(renderType.bufferSize()));
		}
		return super.getBuffer(renderType);
	}

	@Override
	public void endBatch() {
		for (RenderType renderType : fixedBuffers.keySet()) {
			endBatch(renderType);
			fixedBuffers.get(renderType).close();
		}
		fixedBuffers.clear();
	}
}
