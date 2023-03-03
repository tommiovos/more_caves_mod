package net.tom.test.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.tom.test.TestBiomes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {

    @Shadow @Final private Parameter FULL_RANGE;
    private final Parameter FULL_SPAN = Parameter.span(-1.0F, 1.0F);

    private void addCaveBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        consumer.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Parameter.span(0.2F, 0.9F), weirdness, offset), biome));
    }

    @Inject(method="addUndergroundBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        // Hot red
        this.addCaveBiome(consumer, Parameter.span(0.5F, 1F), FULL_SPAN, Parameter.span(-1.0F, -0.3F), FULL_SPAN, FULL_SPAN, 0.0F, TestBiomes.HOT_RED);
    }
}
