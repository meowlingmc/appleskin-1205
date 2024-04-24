package squeek.appleskin.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SyncHandler
{
    public static final PacketCodec HOLDER_FLOAT_CODEC = new PacketCodec<ByteBuf, Float>() {
        public Float decode(ByteBuf byteBuf) {
            return byteBuf.readFloat();
        }
        public void encode(ByteBuf byteBuf, Float blockPos) {
            byteBuf.writeFloat(blockPos);
        }
    };

	/*
	 * Sync saturation (vanilla MC only syncs when it hits 0)
	 * Sync exhaustion (vanilla MC does not sync it at all)
	 */
	private static final Map<UUID, Float> lastSaturationLevels = new HashMap<UUID, Float>();
	private static final Map<UUID, Float> lastExhaustionLevels = new HashMap<UUID, Float>();

	public static void onPlayerUpdate(ServerPlayerEntity player)
	{
		Float lastSaturationLevel = lastSaturationLevels.get(player.getUuid());
		Float lastExhaustionLevel = lastExhaustionLevels.get(player.getUuid());

		float saturation = player.getHungerManager().getSaturationLevel();
		if (lastSaturationLevel == null || lastSaturationLevel != saturation)
		{
			player.networkHandler.sendPacket(new CustomPayloadS2CPacket(new SaturationSyncPayload(saturation)));
			lastSaturationLevels.put(player.getUuid(), saturation);
		}

		float exhaustionLevel = player.getHungerManager().getExhaustion();
		if (lastExhaustionLevel == null || Math.abs(lastExhaustionLevel - exhaustionLevel) >= 0.01f)
		{
            player.networkHandler.sendPacket(new CustomPayloadS2CPacket(new ExhaustionSyncPayload(exhaustionLevel)));
            lastExhaustionLevels.put(player.getUuid(), exhaustionLevel);
		}
	}

	public static void onPlayerLoggedIn(ServerPlayerEntity player)
	{
		lastSaturationLevels.remove(player.getUuid());
		lastExhaustionLevels.remove(player.getUuid());
	}
}
