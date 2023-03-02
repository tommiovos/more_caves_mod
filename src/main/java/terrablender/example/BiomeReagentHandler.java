package terrablender.example;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class BiomeReagentHandler {

    public static ResourceKey<Biome> HOT_RED = registerResourceKey("hot_red");

    public static final Climate.ParameterPoint HOT_RED_PARAMETER = Climate.parameters(
            Climate.Parameter.span(-1.0F, 1.0F),
            Climate.Parameter.span(-1.0F, 1.0F),
            Climate.Parameter.span(0.7F, 1.0F),
            Climate.Parameter.span(Climate.Parameter.span(-1.0F, -0.78F),
                    Climate.Parameter.span(-0.78F, -0.375F)),
            Climate.Parameter.span(0.2F, 0.9F),
            Climate.Parameter.span(-1.0F, 1.0F),
            0.0F
    );

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(HOT_RED_PARAMETER, HOT_RED));
    };

    private static ResourceKey<Biome> registerResourceKey(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(TestMod.MOD_ID, name));
    }
}