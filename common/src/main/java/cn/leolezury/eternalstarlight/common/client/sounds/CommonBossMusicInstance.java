package cn.leolezury.eternalstarlight.common.client.sounds;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class CommonBossMusicInstance extends AbstractTickableSoundInstance {
    private final ESBoss boss;

    private int ticksExisted = 0;

    public CommonBossMusicInstance(ESBoss boss) {
        super(ESSoundEvents.MUSIC_BOSS.get(), SoundSource.RECORDS, boss.getRandom());
        this.boss = boss;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.looping = true;
        this.delay = 0;
        this.x = this.boss.getX();
        this.y = this.boss.getY();
        this.z = this.boss.getZ();
    }

    public boolean shouldPlaySound() {
        return (!this.boss.isSilent() && ClientHandlers.BOSS_SOUND_MAP.get(this.boss.getId()) == this);
    }

    public boolean isNearest() {
        for (Map.Entry<Integer, CommonBossMusicInstance> entry : ClientHandlers.BOSS_SOUND_MAP.entrySet()) {
            CommonBossMusicInstance music = entry.getValue();
            if (music != this && new Vec3(getX(), getY(), getZ()).distanceToSqr(music.x, music.y, music.z) < (400f * 400f) && music.shouldPlaySound()) {
                return false;
            }
        }
        return true;
    }

    public void tick() {
        if (this.ticksExisted % 50 == 0) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
        if (!this.boss.isSilent() && this.boss.isAlive()) {
            this.volume = this.boss.shouldPlayBossMusic() ? 1.0F : 0.0F;
            this.pitch = 1.0F;
            this.x = this.boss.getX();
            this.y = this.boss.getY();
            this.z = this.boss.getZ();
        } else {
            stop();
            ClientHandlers.BOSS_SOUND_MAP.remove(this.boss.getId());
        }
        this.ticksExisted++;
    }
}

