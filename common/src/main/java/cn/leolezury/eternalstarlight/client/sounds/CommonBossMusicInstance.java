package cn.leolezury.eternalstarlight.client.sounds;

import cn.leolezury.eternalstarlight.entity.boss.AbstractSLBoss;
import cn.leolezury.eternalstarlight.event.client.ClientEvents;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class CommonBossMusicInstance extends AbstractTickableSoundInstance {
    private final AbstractSLBoss boss;

    private int ticksExisted = 0;

    public CommonBossMusicInstance(AbstractSLBoss boss) {
        super(SoundEventInit.MUSIC_BOSS.get(), SoundSource.RECORDS, boss.getRandom());
        this.boss = boss;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.looping = true;
        this.delay = 0;
        this.x = this.boss.getX();
        this.y = this.boss.getY();
        this.z = this.boss.getZ();
    }

    public boolean shouldPlaySound() {
        return (!this.boss.isSilent() && ClientEvents.BOSS_SOUND_MAP.get(this.boss.getId()) == this);
    }

    public boolean isNearest() {
        for (Map.Entry<Integer, CommonBossMusicInstance> entry : ClientEvents.BOSS_SOUND_MAP.entrySet()) {
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
            this.volume = 1.0F;
            this.pitch = 1.0F;
            this.x = this.boss.getX();
            this.y = this.boss.getY();
            this.z = this.boss.getZ();
        } else {
            stop();
            ClientEvents.BOSS_SOUND_MAP.remove(this.boss.getId());
        }
        this.ticksExisted++;
    }
}

