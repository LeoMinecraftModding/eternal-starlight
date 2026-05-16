package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
	@Shadow
	public abstract boolean is(TagKey<Block> key);

	@WrapMethod(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
	private VoxelShape getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
		VoxelShape result = original.call(level, pos, context);
		if (!is(ESTags.Blocks.UNAFFECTED_BY_OBLIVION)
			&& context instanceof EntityCollisionContext entityContext
			&& entityContext.getEntity() instanceof LivingEntity living
			&& living.hasEffect(ESMobEffects.OBLIVION.asHolder())) {
			if (context.isAbove(result, pos, true) && !context.isDescending()) {
				return result;
			} else {
				return Shapes.empty();
			}
		}
		return result;
	}
}
