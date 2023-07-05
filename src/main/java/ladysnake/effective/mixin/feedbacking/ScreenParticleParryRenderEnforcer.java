package ladysnake.effective.mixin.feedbacking;

import com.sammy.lodestone.handlers.screenparticle.ScreenParticleHandler;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import ladysnake.effective.gui.ParryScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;


@Mixin(ScreenParticleHandler.class)
public abstract class ScreenParticleParryRenderEnforcer {
	@Shadow(remap = false)
	public static boolean canSpawnParticles;


	@Shadow
	public static void renderEarliestParticles() {}
	@Shadow
	public static void renderLateParticles() {}
	@Inject(method = "renderParticles()V", at = @At("TAIL"), remap = false)
	private static void renderParticles(CallbackInfo ci) {
		final MinecraftClient client = MinecraftClient.getInstance();
		Screen screen = client.currentScreen;
		if (screen instanceof ParryScreen) {
			renderEarliestParticles();
			renderLateParticles();
		}
		// TODO: Check if this work, I'm not sure if this needs any replacement.
//		RENDERED_STACKS.clear();
		canSpawnParticles = false;
	}

}
