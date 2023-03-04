package net.tom.test.features;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tom.test.features.configuration.HotCaveVegetationConfig;
import net.tom.test.TestMod;
import net.tom.test.features.configuration.SmallBlueshroomConfig;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<HotCaveVegetationFeature> HOT_CAVE_VEGETATION = FEATURES.register("hot_cave_vegetation", () -> new HotCaveVegetationFeature(HotCaveVegetationConfig.CODEC));
    public static final RegistryObject<SmallBlueshroomFeature> SMALL_BLUESHROOM = FEATURES.register("small_blueshroom", () -> new SmallBlueshroomFeature(SmallBlueshroomConfig.CODEC));
}
