package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.CursedGardenMazePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class ESStructurePieceTypes {
	public static final RegistrationProvider<StructurePieceType> STRUCTURE_PIECE_TYPES = RegistrationProvider.get(Registries.STRUCTURE_PIECE, EternalStarlight.ID);
	public static final RegistryObject<StructurePieceType, StructurePieceType> CURSED_GARDEN_MAZE = STRUCTURE_PIECE_TYPES.register("cursed_garden_maze", () -> CursedGardenMazePiece::new);

	public static void loadClass() {
	}
}
