package squeek.appleskin;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.client.DebugInfoHandler;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.client.TooltipOverlayHandler;
import squeek.appleskin.network.ClientSyncHandler;
import squeek.appleskin.network.ExhaustionSyncPayload;
import squeek.appleskin.network.SaturationSyncPayload;

public class AppleSkin implements ClientModInitializer, ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitializeClient()
	{
		ClientSyncHandler.init();
		ModConfig.init();
		HUDOverlayHandler.init();
		TooltipOverlayHandler.init();
		DebugInfoHandler.init();
		FabricLoader.getInstance().getEntrypointContainers("appleskin", AppleSkinApi.class).forEach(entrypoint -> {
			try
			{
				entrypoint.getEntrypoint().registerEvents();
			}
			catch (Throwable e)
			{
				LOGGER.error("Failed to load entrypoint for mod {}", entrypoint.getProvider().getMetadata().getId(), e);
			}
		});
	}

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(ExhaustionSyncPayload.ID, ExhaustionSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SaturationSyncPayload.ID, SaturationSyncPayload.CODEC);
    }
}
