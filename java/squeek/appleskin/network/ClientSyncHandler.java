package squeek.appleskin.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientSyncHandler
{
	@Environment(EnvType.CLIENT)
	public static void init()
	{
		ClientPlayNetworking.registerGlobalReceiver(ExhaustionSyncPayload.ID, (payload, ctx) -> {
			ctx.client().execute(() -> {
				ctx.player().getHungerManager().setExhaustion(payload.value());
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(SaturationSyncPayload.ID, (payload, ctx) -> {
            ctx.client().execute(() -> {
                ctx.player().getHungerManager().setSaturationLevel(payload.value());
            });
		});
	}
}
