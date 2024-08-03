package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ESBoat extends Boat {
	private static final String TAG_TYPE = "type";

	private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(ESBoat.class, EntityDataSerializers.INT);

	public ESBoat(EntityType<? extends Boat> type, Level level) {
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
			case STARLIGHT_MANGROVE -> ESItems.STARLIGHT_MANGROVE_BOAT.get();
			case SCARLET -> ESItems.SCARLET_BOAT.get();
			case TORREYA -> ESItems.TORREYA_BOAT.get();
		};
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
		STARLIGHT_MANGROVE(ESBlocks.STARLIGHT_MANGROVE_PLANKS.get(), "starlight_mangrove"),
		SCARLET(ESBlocks.SCARLET_PLANKS.get(), "scarlet"),
		TORREYA(ESBlocks.TORREYA_PLANKS.get(), "torreya");

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
