package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.CrestUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class OrbOfProphecyItem extends Item implements Vanishable {
    public OrbOfProphecyItem(Properties properties) {
        super(properties);
    }

    public static void recordCrests(RegistryAccess access, ItemStack stack, CompoundTag tag) {
        List<Crest> crests = CrestUtil.getCrests(access, tag);
        List<Crest> itemCrests = CrestUtil.getCrests(access, stack.getOrCreateTag().getCompound("Crests"));
        crests.addAll(itemCrests);
        CompoundTag crestsTag = new CompoundTag();
        CrestUtil.setCrests(access, crestsTag, crests);
        stack.getOrCreateTag().put("Crests", crestsTag);
    }

    public static boolean hasCrests(RegistryAccess access, ItemStack stack) {
        if (access == null) return false;
        CompoundTag crests = stack.getOrCreateTag().getCompound("Crests");
        return !CrestUtil.getCrests(access, crests).isEmpty();
    }

    public static List<Crest> getCrests(RegistryAccess access, ItemStack stack) {
        CompoundTag crests = stack.getOrCreateTag().getCompound("Crests");
        return CrestUtil.getCrests(access, crests);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (livingEntity.getPose() != Pose.STANDING) {
            livingEntity.stopUsingItem();
        }
        if (livingEntity.getTicksUsingItem() >= 140 && livingEntity instanceof ServerPlayer player) {
            Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
            List<String> ownedCrests = CrestUtil.getCrests(player, "OwnedCrests").stream().map(c -> Objects.requireNonNull(registry.getKey(c)).toString()).toList();
            List<String> crests = CrestUtil.getCrests(player).stream().map(c -> Objects.requireNonNull(registry.getKey(c)).toString()).toList();
            ESPlatform.INSTANCE.sendToClient(player, new OpenCrestGuiPacket(ownedCrests, crests));
            player.stopUsingItem();
            player.getCooldowns().addCooldown(this, 20);
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (player.getPose() == Pose.STANDING) {
            if (hasCrests(level.registryAccess(), itemStack)) {
                if (player.experienceLevel >= 1) {
                    player.experienceLevel -= 1;
                    getCrests(level.registryAccess(), itemStack).forEach(crest -> CrestUtil.giveCrest(player, crest));
                    itemStack.getOrCreateTag().put("Crests", new CompoundTag());
                    return InteractionResultHolder.success(itemStack);
                }
            } else {
                player.startUsingItem(interactionHand);
                return InteractionResultHolder.consume(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
