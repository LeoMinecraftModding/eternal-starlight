package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.item.combat.SickleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// vanilla hardcodes shears in its loot conditions
// Fabric has no item ability for that so a sickle is accepted by pretending the condition is tested against shears
@Mixin(MatchTool.class)
public class MatchToolMixin {
	@Inject(method = "test*", at = @At("HEAD"), cancellable = true)
	private void test(LootContext context, CallbackInfoReturnable<Boolean> cir) {
		ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
		if (tool == null || !(tool.getItem() instanceof SickleItem)) {
			return;
		}
		if (((MatchTool) (Object) this).predicate().map(predicate -> predicate.test(new ItemStack(Items.SHEARS))).orElse(false)) {
			cir.setReturnValue(true);
		}
	}
}
