package cn.leolezury.eternalstarlight.entity.boss.bossevent;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class SLServerBossEvent extends ServerBossEvent {
    private final Mob boss;
    private final Set<ServerPlayer> unseenPlayers = new HashSet<>();
    private UUID id;

    public SLServerBossEvent(Mob boss, UUID id, BossEvent.BossBarColor color, boolean darkenScreen) {
        super(boss.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
        setVisible(true);
        setId(id);
        setDarkenScreen(darkenScreen);
        this.boss = boss;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public UUID getId() {
        return this.id;
    }

    public void update() {
        setProgress(this.boss.getHealth() / this.boss.getMaxHealth());
        Iterator<ServerPlayer> it = this.unseenPlayers.iterator();
        while (it.hasNext()) {
            ServerPlayer player = it.next();
            if (this.boss.getSensing().hasLineOfSight(player)) {
                super.addPlayer(player);
                it.remove();
            }
        }
    }

    public void addPlayer(ServerPlayer player) {
        if (this.boss.getSensing().hasLineOfSight(player)) {
            super.addPlayer(player);
        } else {
            this.unseenPlayers.add(player);
        }
    }

    public void removePlayer(ServerPlayer player) {
        super.removePlayer(player);
        this.unseenPlayers.remove(player);
    }
}
