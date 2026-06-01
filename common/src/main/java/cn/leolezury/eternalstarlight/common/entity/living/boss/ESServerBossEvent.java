package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.network.UpdateBossBarPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ESServerBossEvent extends ServerBossEvent {
	public static final int THE_GATEKEEPER = 1;
	public static final int STARLIGHT_GOLEM = 2;
	public static final int LUNAR_MONSTROSITY = 3;
	public static final int LUNAR_MONSTROSITY_SOUL = 4;

	private final ESBoss boss;
	private final Set<ServerPlayer> unseenPlayers = new HashSet<>();
	private int type;

	public ESServerBossEvent(ESBoss boss, int type, BossEvent.BossBarColor color, boolean darkenScreen) {
		super(boss.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
		this.boss = boss;
		this.type = type;
		this.setDarkenScreen(darkenScreen);
	}

	public void setType(ServerLevel serverLevel, int type) {
		if (this.type != type) {
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new UpdateBossBarPacket(getId(), type));
		}
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void update() {
		setProgress(this.boss.getHealth() / this.boss.getMaxHealth());
		Iterator<ServerPlayer> iterator = this.unseenPlayers.iterator();
		while (iterator.hasNext()) {
			ServerPlayer player = iterator.next();
			if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated() && this.boss.tickCount > 20) {
				super.addPlayer(player);
				iterator.remove();
			}
		}
	}

	@Override
	public void addPlayer(ServerPlayer player) {
		if (this.boss.getSensing().hasLineOfSight(player) && this.boss.isActivated() && this.boss.tickCount > 20) {
			super.addPlayer(player);
		} else {
			this.unseenPlayers.add(player);
		}
		ESPlatform.INSTANCE.sendToClient(player, new UpdateBossBarPacket(getId(), getType()));
	}

	@Override
	public void removePlayer(ServerPlayer player) {
		super.removePlayer(player);
		this.unseenPlayers.remove(player);
		ESPlatform.INSTANCE.sendToClient(player, new UpdateBossBarPacket(getId(), 0));
	}

	public void allConvertToUnseen() {
		this.unseenPlayers.addAll(getPlayers());
		new ArrayList<>(getPlayers()).forEach(super::removePlayer);
	}
}
