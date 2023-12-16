package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ESBoat extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(ESBoat.class, EntityDataSerializers.INT);

    public ESBoat(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public ESBoat(Level level, double x, double y, double z) {
        this(EntityInit.BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public ESBoat.Type getSLBoatType() {
        return ESBoat.Type.byId(this.entityData.get(BOAT_TYPE));
    }

    @Override
    public Item getDropItem() {
        return switch (this.getSLBoatType()) {
            case LUNAR -> ItemInit.LUNAR_BOAT.get();
            case NORTHLAND -> ItemInit.NORTHLAND_BOAT.get();
            case STARLIGHT_MANGROVE -> ItemInit.STARLIGHT_MANGROVE_BOAT.get();
            case SCARLET -> ItemInit.SCARLET_BOAT.get();
        };
    }

    public void setSLBoatType(ESBoat.Type boatType) {
        this.entityData.set(BOAT_TYPE, boatType.ordinal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, Type.LUNAR.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("Type", this.getSLBoatType().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Type", 8)) {
            this.setSLBoatType(ESBoat.Type.getTypeFromString(compound.getString("Type")));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Packet<ClientGamePacketListener> packet = ESPlatform.INSTANCE.getAddEntityPacket(this);
        return packet == null ? super.getAddEntityPacket() : packet;
    }

    public enum Type {
        LUNAR(BlockInit.LUNAR_PLANKS.get(), "lunar"),
        NORTHLAND(BlockInit.NORTHLAND_PLANKS.get(), "northland"),
        STARLIGHT_MANGROVE(BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), "starlight_mangrove"),
        SCARLET(BlockInit.SCARLET_PLANKS.get(), "scarlet");

        private final String name;
        private final Block block;

        Type(Block block, String name) {
            this.name = name;
            this.block = block;
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }

        public static ESBoat.Type byId(int id) {
            ESBoat.Type[] SLBoat$type = values();
            if (id < 0 || id >= SLBoat$type.length) {
                id = 0;
            }

            return SLBoat$type[id];
        }

        public static ESBoat.Type getTypeFromString(String nameIn) {
            ESBoat.Type[] boatTypeArray = values();

            for (Type type : boatTypeArray) {
                if (type.getName().equals(nameIn)) {
                    return type;
                }
            }

            return boatTypeArray[0];
        }
    }
}
