package mod.crend.movesubtitles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class MoveSubtitles implements ClientModInitializer {
	public static final String MOD_ID = "movesubtitles";
	public static final String CONFIG_FILE = MOD_ID + ".json";

	@Override
	public void onInitializeClient() {
		MoveSubtitlesConfig.load(FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE));
	}
}