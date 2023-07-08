package cn.leolezury.eternalstarlight.item.weapon;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ScytheItem extends TieredItem implements Vanishable {
    private final TagKey<Block> blocks;
    protected final float speed;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ScytheItem(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        super(tier, properties);
        this.blocks = BlockTags.MINEABLE_WITH_HOE;
        this.speed = tier.getSpeed();
        this.attackDamage = (float)damage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("A9867629-19D6-F529-862E-21979863B5CF"), "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public InteractionResult useOn(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(p_41341_, ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(p_41341_)) {
                Player player = p_41341_.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(p_41341_);
                    if (player != null) {
                        p_41341_.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(p_41341_.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static Consumer<UseOnContext> changeIntoState(BlockState p_150859_) {
        return (p_238241_) -> {
            p_238241_.getLevel().setBlock(p_238241_.getClickedPos(), p_150859_, 11);
            p_238241_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_238241_.getClickedPos(), GameEvent.Context.of(p_238241_.getPlayer(), p_150859_));
        };
    }

    public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState p_150850_, ItemLike p_150851_) {
        return (p_238246_) -> {
            p_238246_.getLevel().setBlock(p_238246_.getClickedPos(), p_150850_, 11);
            p_238246_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_238246_.getClickedPos(), GameEvent.Context.of(p_238246_.getPlayer(), p_150850_));
            Block.popResourceFromFace(p_238246_.getLevel(), p_238246_.getClickedPos(), p_238246_.getClickedFace(), new ItemStack(p_150851_));
        };
    }

    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(this.blocks) ? this.speed : 1.0F;
    }

    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(2, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_40990_) {
        return p_40990_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_40990_);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(blocks) && TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }
}
