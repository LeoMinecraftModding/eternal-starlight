package cn.leolezury.eternalstarlight.forge.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ESPayload<T> implements CustomPacketPayload {
    private final ResourceLocation id;
    private final BiConsumer<T, FriendlyByteBuf> write;
    private final Consumer<T> handle;
    private final T packet;

    public ESPayload(ResourceLocation id, Function<FriendlyByteBuf, T> read, BiConsumer<T, FriendlyByteBuf> write, Consumer<T> handle, FriendlyByteBuf byteBuf) {
        this.id = id;
        this.write = (BiConsumer<T, FriendlyByteBuf>) write;
        this.handle = (Consumer<T>) handle;
        this.packet = (T) read.apply(byteBuf);
    }

    public ESPayload(ResourceLocation id, BiConsumer<T, FriendlyByteBuf> write, Consumer<T> handle, T packet) {
        this.id = id;
        this.write = write;
        this.handle = handle;
        this.packet = packet;
    }
    
    public Consumer<T> handler() {
        return handle;
    }
    
    public T packet() {
        return packet;
    }

    @Override
    public void write(FriendlyByteBuf byteBuf) {
        write.accept(packet, byteBuf);
    }

    @Override
    public ResourceLocation id() {
        return id;
    }
}
