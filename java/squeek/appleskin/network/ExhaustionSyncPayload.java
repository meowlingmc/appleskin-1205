package squeek.appleskin.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ExhaustionSyncPayload(Float value) implements CustomPayload {
    public static final Id<ExhaustionSyncPayload> ID = CustomPayload.id(new Identifier("appleskin", "exhaustion_sync").toString());
    public static final PacketCodec<PacketByteBuf, ExhaustionSyncPayload> CODEC = PacketCodec.tuple(SyncHandler.HOLDER_FLOAT_CODEC, ExhaustionSyncPayload::value, ExhaustionSyncPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
