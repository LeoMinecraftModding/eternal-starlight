package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.entity.misc.ESSynchedEntityData;

public interface ESLivingEntity {
    ESSynchedEntityData.SynchedData getSynchedData();
    void setSynchedData(ESSynchedEntityData.SynchedData data);
}
