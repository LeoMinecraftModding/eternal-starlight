package cn.leolezury.eternalstarlight.common.entity.living.boss;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ESServerBossEvent extends ServerBossEvent {
	private final ESBoss boss;
	private final Set<ServerPlayer> unseenPlayers = new HashSet<>();
	private UUID id;

	public ESServerBossEvent(ESBoss boss, UUID id, BossEvent.BossBarColor color, boolean darkenScreen) {
		super(boss.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
		setVisible(true);
		setId(id);
		setDarkenScreen(darkenScreen);
		this.boss = boss;
	}

	public void setId(UUID uuid) {
		this.id = uuid;
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	public void update() {
		setProgress(this.boss.getHealth() / this.boss.getMaxHealth());
		Iterator<ServerPlayer> it = this.unseenPlayers.iterator();
		while (it.hasNext()) {
			ServerPlayer player = it.next();
			if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated()) {
				super.addPlayer(player);
				it.remove();
			}
		}
	}

	@Override
	public void addPlayer(ServerPlayer player) {
		if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated()) {
			super.addPlayer(player);
		} else {
			this.unseenPlayers.add(player);
		}
	}

	@Override
	public void removePlayer(ServerPlayer player) {
		super.removePlayer(player);
		this.unseenPlayers.remove(player);
	}

	public void allConvertToUnseen() {
		this.unseenPlayers.addAll(getPlayers());
		getPlayers().forEach(super::removePlayer);
	}
}
