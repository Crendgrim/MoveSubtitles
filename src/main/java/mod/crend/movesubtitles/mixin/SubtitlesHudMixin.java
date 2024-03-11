package mod.crend.movesubtitles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mod.crend.movesubtitles.MoveSubtitlesConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(SubtitlesHud.class)
public abstract class SubtitlesHudMixin {

	@Final
	@Shadow
	private List<SubtitlesHud.SubtitleEntry> audibleEntries;

	@WrapOperation(method = "render", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
	private void moveSubtitles(MatrixStack instance, float x, float y, float z, Operation<Void> original, DrawContext context, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j) {
		int centerX = j / 2;
		int heightPerRow = 10;
		float x2 = switch (MoveSubtitlesConfig.INSTANCE.edge) {
			case TOP_LEFT, LEFT, BOTTOM_LEFT -> centerX + 2.0F;
			case TOP, BOTTOM -> context.getScaledWindowWidth() / 2.0F;
			case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> x;
		};
		float y2 = switch (MoveSubtitlesConfig.INSTANCE.edge) {
			case TOP_LEFT, TOP, TOP_RIGHT -> 15 + i * heightPerRow;
			case LEFT, RIGHT -> (context.getScaledWindowHeight() / 2.0F - heightPerRow / 2.0F - (heightPerRow * i)) + this.audibleEntries.size() * heightPerRow / 2;
			case BOTTOM_LEFT, BOTTOM_RIGHT -> y;
			case BOTTOM -> y - 10;
		};

		x2 += MoveSubtitlesConfig.INSTANCE.deltaX;
		y2 += MoveSubtitlesConfig.INSTANCE.deltaY;

		// Subtitle text can look weird with some resolutions/GUI scales
		// if between a pixel. So make sure it's at an integer position.
		x2 = (int)x2;
		y2 = (int)y2;
		
		original.call(instance, x2, y2, z);
	}
}