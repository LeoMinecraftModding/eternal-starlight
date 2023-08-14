package cn.leolezury.eternalstarlight.entity.misc;

import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
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
import net.minecraftforge.network.NetworkHooks;

public class SLBoat extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(SLBoat.class, EntityDataSerializers.INT);

    public SLBoat(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public SLBoat(Level level, double x, double y, double z) {
        this(EntityInit.BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public SLBoat.Type getSLBoatType() {
        return SLBoat.Type.byId(this.entityData.get(BOAT_TYPE));
    }

    @Override
    public Item getDropItem() {
        return switch (this.getSLBoatType()) {
            case LUNAR -> ItemInit.LUNAR_BOAT.get();
            case NORTHLAND -> ItemInit.NORTHLAND_BOAT.get();
            case STARLIGHT_MANGROVE -> ItemInit.STARLIGHT_MANGROVE_BOAT.get();
        };
    }

    public void setSLBoatType(SLBoat.Type boatType) {
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
            this.setSLBoatType(SLBoat.Type.getTypeFromString(compound.getString("Type")));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public enum Type {
        LUNAR(BlockInit.LUNAR_PLANKS.get(), "lunar"),
        NORTHLAND(BlockInit.NORTHLAND_PLANKS.get(), "northland"),
        STARLIGHT_MANGROVE(BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), "starlight_mangrove"),
        ;

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

        public static SLBoat.Type byId(int id) {
            SLBoat.Type[] SLBoat$type = values();
            if (id < 0 || id >= SLBoat$type.length) {
                id = 0;
            }

            return SLBoat$type[id];
        }

        public static SLBoat.Type getTypeFromString(String nameIn) {
            SLBoat.Type[] boatTypeArray = values();

            for (Type type : boatTypeArray) {
                if (type.getName().equals(nameIn)) {
                    return type;
                }
            }

            return boatTypeArray[0];
        }
    }
}
