package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ESBoat extends Boat {
	private static final String TAG_TYPE = "type";

	private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(ESBoat.class, EntityDataSerializers.INT);

	public ESBoat(EntityType<? extends ESBoat> type, Level level) {
		super(type, level);
		this.blocksBuilding = true;
	}

	public ESBoat(Level level, double x, double y, double z) {
		this(ESEntities.BOAT.get(), level);
		this.setPos(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	public ESBoat.Type getESBoatType() {
		return ESBoat.Type.byId(this.getEntityData().get(BOAT_TYPE));
	}

	@Override
	public Item getDropItem() {
		return switch (this.getESBoatType()) {
			case LUNAR -> ESItems.LUNAR_BOAT.get();
			case NORTHLAND -> ESItems.NORTHLAND_BOAT.get();
			case BANYIN -> ESItems.BANYIN_BOAT.get();
			case SCARLET -> ESItems.SCARLET_BOAT.get();
			case TORREYA -> ESItems.TORREYA_BOAT.get();
			case JINGLESTEM -> ESItems.JINGLESTEM_RAFT.get();
			case CRADLEWOOD -> ESItems.CRADLEWOOD_BOAT.get();
		};
	}

	@Override
	protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		Vec3 point = super.getPassengerAttachmentPoint(entity, entityDimensions, f);
		if (getESBoatType() == Type.JINGLESTEM) {
			return new Vec3(point.x, point.y / 3.0 * 8.0, point.z);
		}
		return point;
	}

	public void setStarlightBoatType(ESBoat.Type boatType) {
		this.getEntityData().set(BOAT_TYPE, boatType.ordinal());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BOAT_TYPE, Type.LUNAR.ordinal());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putString(TAG_TYPE, this.getESBoatType().getName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		if (compound.contains(TAG_TYPE, CompoundTag.TAG_STRING)) {
			this.setStarlightBoatType(ESBoat.Type.getTypeFromString(compound.getString(TAG_TYPE)));
		}
	}

	public enum Type {
		LUNAR(ESBlocks.LUNAR_PLANKS.get(), "lunar"),
		NORTHLAND(ESBlocks.NORTHLAND_PLANKS.get(), "northland"),
		BANYIN(ESBlocks.BANYIN_PLANKS.get(), "banyin"),
		SCARLET(ESBlocks.SCARLET_PLANKS.get(), "scarlet"),
		TORREYA(ESBlocks.TORREYA_PLANKS.get(), "torreya"),
		JINGLESTEM(ESBlocks.JINGLESTEM_PLANKS.get(), "jinglestem"),
		CRADLEWOOD(ESBlocks.CRADLEWOOD_PLANKS.get(), "cradlewood");

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
