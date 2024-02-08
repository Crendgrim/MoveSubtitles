package mod.crend.movesubtitles;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MoveSubtitlesConfig {
	public static MoveSubtitlesConfig INSTANCE;

	public ScreenEdge edge = ScreenEdge.BOTTOM_RIGHT;
	public float deltaX = 0.0F;
	public float deltaY = 0.0F;

	public static void load(Path configFile) {
		try {
			INSTANCE = new GsonBuilder().create().fromJson(Files.readString(configFile), MoveSubtitlesConfig.class);
		} catch (IOException e) {
			INSTANCE = new MoveSubtitlesConfig();
			try {
				Files.writeString(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(INSTANCE));
			} catch (IOException ignored) {
			}
		}
	}
}
