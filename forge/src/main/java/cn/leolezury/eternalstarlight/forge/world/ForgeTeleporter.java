package cn.leolezury.eternalstarlight.forge.world;

import cn.leolezury.eternalstarlight.common.world.ESTeleporter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.neoforged.neoforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ForgeTeleporter implements ITeleporter {
    protected final ServerLevel level;

    public ForgeTeleporter(ServerLevel level) {
        this.level = level;
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        return ESTeleporter.getPortalInfo(entity, destWorld);
    }
}
