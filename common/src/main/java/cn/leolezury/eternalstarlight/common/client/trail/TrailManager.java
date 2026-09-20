package cn.leolezury.eternalstarlight.common.client.trail;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.*;

public class TrailManager {
	public static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

	private static final Map<EntityType<?>, TrailEmitter<?>> EMITTERS = new HashMap<>();
	private static final Map<Integer, Instance<?>> INSTANCES = new HashMap<>();

	public static <T extends Entity> void register(EntityType<T> entityType, TrailEmitter<T> emitter) {
		EMITTERS.put(entityType, emitter);
	}

	public static void tick(ClientLevel level) {
		Set<Integer> renderedIds = new HashSet<>();
		for (Entity entity : level.entitiesForRendering()) {
			TrailEmitter<?> emitter = EMITTERS.get(entity.getType());
			if (emitter != null && entity.tickCount > 2) {
				renderedIds.add(entity.getId());
				Instance<?> instance = INSTANCES.get(entity.getId());
				if (instance == null) {
					instance = createInstance(entity, emitter);
					INSTANCES.put(entity.getId(), instance);
				}
				instance.tick();
			}
		}
		Iterator<Map.Entry<Integer, Instance<?>>> iterator = INSTANCES.entrySet().iterator();
		while (iterator.hasNext()) {
			Instance<?> instance = iterator.next().getValue();
			if (renderedIds.contains(instance.entity.getId())) {
				continue;
			}
			if (!instance.entity.isRemoved()) {
				iterator.remove();
				continue;
			}
			instance.tick();
			if (instance.shouldRemove()) {
				iterator.remove();
			}
		}
	}

	public static void render(MultiBufferSource source, PoseStack stack, float partialTicks) {
		for (Instance<?> instance : INSTANCES.values()) {
			instance.render(source, stack, partialTicks);
		}
	}

	public static void clear() {
		INSTANCES.clear();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Entity> Instance<T> createInstance(Entity entity, TrailEmitter<?> emitter) {
		return new Instance<>((T) entity, (TrailEmitter<T>) emitter);
	}

	private static class Instance<T extends Entity> {
		private final T entity;
		private final TrailEmitter<T> emitter;
		private final Trail trail;

		private Instance(T entity, TrailEmitter<T> emitter) {
			this.entity = entity;
			this.emitter = emitter;
			this.trail = emitter.createTrail(entity);
		}

		private void tick() {
			emitter.tick(entity, trail);
		}

		private boolean shouldRemove() {
			return emitter.shouldRemove(entity, trail);
		}

		private void render(MultiBufferSource source, PoseStack stack, float partialTicks) {
			RenderType renderType = emitter.getRenderType(entity, trail);
			if (renderType == null) {
				return;
			}
			emitter.frame(entity, trail, partialTicks);
			TrailPoint head = emitter.getHeadPoint(entity, partialTicks);
			trail.prepareRender(head, partialTicks);
			boolean solid = emitter.isSolid(entity, trail);
			VertexConsumer consumer = (solid ? source : ESClientHandler.DELAYED_BUFFER_SOURCE).getBuffer(renderType);
			TrailRenderer.render(trail, consumer, stack, false, solid, emitter.getLight(entity, partialTicks));
		}
	}
}
