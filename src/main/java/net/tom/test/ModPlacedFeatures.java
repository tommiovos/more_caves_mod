package net.tom.test;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tom.test.block.ModBlocks;
import net.tom.test.placementmodifier.CountOnEveryCeilingPlacement;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> CAVE_FUNGI = register("hot_cave_vegetation", ModConfiguredFeatures.HOT_CAVE_VEGETATION,
            CountOnEveryCeilingPlacement.of(4),
            BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.LAVA))),
            BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.GRASS, Blocks.TALL_GRASS, Blocks.AZALEA, Blocks.MOSS_CARPET, Blocks.FLOWERING_AZALEA, Blocks.OAK_WOOD, Blocks.RAIL))),
            BiomeFilter.biome()
    );

    public static final RegistryObject<PlacedFeature> SMALL_BLUESHROOM = register("small_blueshroom", ModConfiguredFeatures.SMALL_BLUESHROOM,
            CountOnEveryCeilingPlacement.of(3),
            BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.LAVA))),
            BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.GRASS, Blocks.TALL_GRASS, Blocks.AZALEA, Blocks.MOSS_CARPET, Blocks.FLOWERING_AZALEA, Blocks.OAK_WOOD, Blocks.RAIL))),
            BiomeFilter.biome()
    );

    private static <T extends FeatureConfiguration> RegistryObject<PlacedFeature> register(String name, Supplier<ConfiguredFeature<T, ?>> feature, PlacementModifier... modifiers) {
        return register(name, feature, () -> List.of(modifiers));
    }

    private static <T extends FeatureConfiguration> RegistryObject<PlacedFeature> register(String name, Supplier<ConfiguredFeature<T, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACED_FEATURES.register(name, () -> new PlacedFeature(getHolder(feature.get()), modifiers.get()));
    }

    private static Holder<ConfiguredFeature<?, ?>> getHolder(ConfiguredFeature<?, ?> feature) {
        return getHolder(BuiltinRegistries.CONFIGURED_FEATURE, feature);
    }

    public static Holder<PlacedFeature> getHolder(PlacedFeature feature) {
        return getHolder(BuiltinRegistries.PLACED_FEATURE, feature);
    }

    private static <T> Holder<T> getHolder(Registry<T> registry, T object) {
        return registry.getHolderOrThrow(registry.getResourceKey(object).orElseThrow());
    }
}
