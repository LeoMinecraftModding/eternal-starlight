package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

public class BoarwarfLookAtCustomerGoal extends LookAtPlayerGoal {

    private final Boarwarf villager;

    public BoarwarfLookAtCustomerGoal(Boarwarf villager) {
        super(villager, Player.class, 8.0F);
        this.villager = villager;
    }

    @Override
    public boolean canUse() {
        if (this.villager.hasCustomer()) {
            this.lookAt = this.villager.getTradingPlayer();
            return true;
        } else {
            return false;
        }
    }
}
