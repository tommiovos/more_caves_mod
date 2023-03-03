package net.tom.test.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class FeaturesPlacements {
    public static final Holder<PlacedFeature> HOT_CAVE_VEGETATION = PlacementUtils.register("hot_cave_vegetation", CaveFeatures.MONSTER_ROOM, CountPlacement.of(10), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()), BiomeFilter.biome());
}
