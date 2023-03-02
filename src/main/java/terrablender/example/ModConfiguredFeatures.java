package terrablender.example;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import terrablender.example.features.HotCaveVegetationFeature;
import terrablender.example.features.ModFeatures;
import terrablender.example.features.configuration.HotCaveVegetationConfig;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<HotCaveVegetationConfig, ?>> HOT_CAVE_VEGETATION = CONFIGURED_FEATURES.register("hot_cave_vegetation", () -> new ConfiguredFeature<>(ModFeatures.HOT_CAVE_VEGETATION.get(), new HotCaveVegetationConfig(
            Blocks.SOUL_SOIL.defaultBlockState(),
            Blocks.SOUL_SOIL.defaultBlockState(),
            Blocks.SOUL_SOIL.defaultBlockState(),
            true
    )));
}
