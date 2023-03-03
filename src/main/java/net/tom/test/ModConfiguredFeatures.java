package net.tom.test;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tom.test.features.ModFeatures;
import net.tom.test.features.configuration.HotCaveVegetationConfig;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<HotCaveVegetationConfig, ?>> HOT_CAVE_VEGETATION = CONFIGURED_FEATURES.register("hot_cave_vegetation", () -> new ConfiguredFeature<>(ModFeatures.HOT_CAVE_VEGETATION.get(), new HotCaveVegetationConfig(
            Blocks.SOUL_SOIL.defaultBlockState(),
            Blocks.SOUL_SOIL.defaultBlockState(),
            Blocks.SOUL_SOIL.defaultBlockState(),
            true
    )));

    public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> ALT_AMETHYST =
            FeatureUtils.register(
                    "amethyst_geode",
                    Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(Blocks.AMETHYST_BLOCK),
                                    BlockStateProvider.simple(Blocks.BUDDING_AMETHYST),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(
                                            Blocks.DIAMOND_BLOCK.defaultBlockState(),
                                            Blocks.MEDIUM_AMETHYST_BUD.defaultBlockState(),
                                            Blocks.LARGE_AMETHYST_BUD.defaultBlockState(),
                                            Blocks.RED_TERRACOTTA.defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(
                                    1.7D,
                                    2.2D,
                                    3.2D,
                                    4.2D),
                            new GeodeCrackSettings(
                                    0.95D,
                                    2.0D,
                                    2),
                            0.35D,
                            0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1
                    )
            );

    public static final Holder<PlacedFeature> NEW_AMETH = PlacementUtils.register("amethyst_geode", ALT_AMETHYST, RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome());

}
