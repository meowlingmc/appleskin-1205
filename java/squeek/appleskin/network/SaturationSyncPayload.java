package squeek.appleskin.network;

import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SaturationSyncPayload(Float value) implements CustomPayload {
    public static final Id<SaturationSyncPayload> ID = CustomPayload.id(new Identifier("appleskin", "saturation_sync").toString());
    public static final PacketCodec<PacketByteBuf, SaturationSyncPayload> CODEC = PacketCodec.tuple(SyncHandler.HOLDER_FLOAT_CODEC, SaturationSyncPayload::value, SaturationSyncPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
