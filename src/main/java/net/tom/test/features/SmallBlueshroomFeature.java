package net.tom.test.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.tom.test.block.ModBlocks;
import net.tom.test.features.configuration.SmallBlueshroomConfig;

import java.util.concurrent.ThreadLocalRandom;

public class SmallBlueshroomFeature extends Feature<SmallBlueshroomConfig> {

    public SmallBlueshroomFeature(Codec<SmallBlueshroomConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SmallBlueshroomConfig> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos target_block = ctx.origin();

        if (level.getBlockState(target_block.above()) != ModBlocks.ENRICHED_DIRT_BLOCK.get().defaultBlockState()) {
            return false;
        }

        BlockState stem = ModBlocks.BLUESHROOM_STEM_BLOCK.get().defaultBlockState();
        BlockState cap = ModBlocks.BLUESHROOM_CAP_BLOCK.get().defaultBlockState();
        BlockState enriched_mycelium = ModBlocks.ENRICHED_MYCELIUM_BLOCK.get().defaultBlockState();

        int stemLength = 0;

        int rand100 = ThreadLocalRandom.current().nextInt(0, 100);
        if (rand100 < 20) { stemLength = 1; }
        else if (rand100 < 70) { stemLength = 2; }
        else { stemLength = 3; }

        // Set mycelium above shroom
        level.setBlock(target_block.above(), enriched_mycelium, 2);

        // Create stem
        level.setBlock(target_block, stem, 2);
        for(int i = 1; i < stemLength; i++) {
            target_block = target_block.below();
            level.setBlock(target_block, stem, 2);
        }

        // Create mushroom cap
        level.setBlock(target_block.below(), cap, 2);
        return true;
    }
}
