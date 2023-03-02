package terrablender.example.features;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import terrablender.example.TestMod;
import terrablender.example.features.configuration.HotCaveVegetationConfig;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<HotCaveVegetationFeature> HOT_CAVE_VEGETATION = FEATURES.register("hot_cave_vegetation", () -> new HotCaveVegetationFeature(HotCaveVegetationConfig.CODEC));
}
