package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class OrbOfProphecyItem extends Item implements Vanishable {
    public OrbOfProphecyItem(Properties properties) {
        super(properties);
    }

    public static void recordCrests(RegistryAccess access, ItemStack stack, CompoundTag tag) {
        List<Crest> crests = ESCrestUtil.getCrests(access, tag);
        List<Crest> itemCrests = ESCrestUtil.getCrests(access, stack.getOrCreateTag().getCompound("Crests"));
        crests.addAll(itemCrests);
        CompoundTag crestsTag = new CompoundTag();
        ESCrestUtil.setCrests(access, crestsTag, crests);
        stack.getOrCreateTag().put("Crests", crestsTag);
    }

    public static boolean hasCrests(RegistryAccess access, ItemStack stack) {
        if (access == null) return false;
        CompoundTag crests = stack.getOrCreateTag().getCompound("Crests");
        return !ESCrestUtil.getCrests(access, crests).isEmpty();
    }

    public static List<Crest> getCrests(RegistryAccess access, ItemStack stack) {
        CompoundTag crests = stack.getOrCreateTag().getCompound("Crests");
        return ESCrestUtil.getCrests(access, crests);
    }

    public static void setTemporary(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("Temporary", true);
    }

    public static boolean isTemporary(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("Temporary");
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (livingEntity.getPose() != Pose.STANDING) {
            livingEntity.stopUsingItem();
        }
        if (livingEntity.getTicksUsingItem() >= 140 && livingEntity instanceof ServerPlayer player) {
            Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
            List<String> ownedCrests = ESCrestUtil.getCrests(player, "OwnedCrests").stream().map(c -> Objects.requireNonNull(registry.getKey(c)).toString()).toList();
            List<String> crests = ESCrestUtil.getCrests(player).stream().map(c -> Objects.requireNonNull(registry.getKey(c)).toString()).toList();
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
                int xpCost = isTemporary(itemStack) ? 2 : 1;
                if (player.experienceLevel >= xpCost) {
                    player.experienceLevel -= xpCost;
                    getCrests(level.registryAccess(), itemStack).forEach(crest -> ESCrestUtil.giveCrest(player, crest));
                    itemStack.getOrCreateTag().put("Crests", new CompoundTag());
                    if (isTemporary(itemStack)) {
                        itemStack.shrink(1);
                    }
                    return InteractionResultHolder.success(itemStack);
                }
            } else {
                player.startUsingItem(interactionHand);
                return InteractionResultHolder.consume(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos pos = useOnContext.getClickedPos();
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
            AdvancementHolder challenge = serverPlayer.getServer().getAdvancements().get(new ResourceLocation(EternalStarlight.MOD_ID, "challenge_gatekeeper"));
            boolean challenged = challenge != null && serverPlayer.getAdvancements().getOrStartProgress(challenge).isDone();
            if (challenged && level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)) {
                if (level.dimension() == ESDimensions.STARLIGHT_KEY || level.dimension() == Level.OVERWORLD) {
                    if (ESPortalBlock.validateAndPlacePortal(level, pos)) {
                        level.playSound(player, pos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
