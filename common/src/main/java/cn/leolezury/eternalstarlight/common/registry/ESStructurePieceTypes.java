package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.StranghoulDenMainPiece;
import cn.leolezury.eternalstarlight.common.world.gen.structure.garden.CursedGardenMazePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class ESStructurePieceTypes {
	public static final RegistrationProvider<StructurePieceType> STRUCTURE_PIECE_TYPES = RegistrationProvider.get(Registries.STRUCTURE_PIECE, EternalStarlight.ID);
	public static final RegistryObject<StructurePieceType, StructurePieceType> CURSED_GARDEN_MAZE = STRUCTURE_PIECE_TYPES.register("cursed_garden_maze", () -> CursedGardenMazePiece::new);
	public static final RegistryObject<StructurePieceType, StructurePieceType> STRANGHOUL_DEN_MAIN = STRUCTURE_PIECE_TYPES.register("stranghoul_den_main", () -> StranghoulDenMainPiece::new);

	public static void loadClass() {
	}
}
