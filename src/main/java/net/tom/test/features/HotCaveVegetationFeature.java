package net.tom.test.features;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.fml.common.Mod;
import net.tom.test.block.ModBlocks;
import net.tom.test.features.configuration.HotCaveVegetationConfig;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class HotCaveVegetationFeature extends Feature<HotCaveVegetationConfig> {

    public HotCaveVegetationFeature(Codec<HotCaveVegetationConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<HotCaveVegetationConfig> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        BlockPos target_block = origin;



        //BlockState state = level.getBlockState(target_block.above());
        HotCaveVegetationConfig config = ctx.config();
        RandomSource randomSource = ctx.random();
        BlockState stem = Blocks.MUSHROOM_STEM.defaultBlockState();
        BlockState cap = Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState();
        BlockState enriched_mycelium = ModBlocks.ENRICHED_MYCELIUM_BLOCK.get().defaultBlockState();
        BlockState enriched_dirt = ModBlocks.ENRICHED_DIRT_BLOCK.get().defaultBlockState();
        // between 0 and 3 (inclusive)
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        for (int i=0; i<randomNum; i++) {
            level.setBlock(target_block, stem, 2);
            target_block = target_block.below(1);
        }

        // Create mushroom cap
        level.setBlock(target_block.north().west(), cap, 2);
        level.setBlock(target_block.north(), cap, 2);
        level.setBlock(target_block.north().east(), cap, 2);
        level.setBlock(target_block.west(), cap, 2);
        level.setBlock(target_block, cap, 2);
        level.setBlock(target_block.east(), cap, 2);
        level.setBlock(target_block.south().west(), cap, 2);
        level.setBlock(target_block.south(), cap, 2);
        level.setBlock(target_block.south().east(), cap, 2);

        // change enriched dirt around to enriched mycelium
        int radius = 5;
        for (int x=0; x<radius; x++) {
            for (int z=0; z<radius; z++) {
                for (int y=0; y<2; y++) {
                    // Either isn't on edge or one chance out of 2 to be placed
                    boolean shouldPlace =
                            (!isOnEdge(radius, x) && !isOnEdge(radius, y))
                                    || ThreadLocalRandom.current().nextInt(0, 100) > 50;

                    if (shouldPlace) {
                        // Offset by 2 blocks so mycelium is placed around the base of the stem
                        target_block = origin.offset(x-2,y,z-2);
                        if (level.getBlockState(target_block) == enriched_dirt &&
                                level.getBlockState(target_block.below()) == Blocks.AIR.defaultBlockState()) {
                            level.setBlock(target_block, enriched_mycelium, 2);
                        }
                    }
                }
            }
        }
        return true;
    }

    public String shroomDirection(BlockPos pos, WorldGenLevel level) {
        pos = pos.below(2);
        int[] voidBlocksCount = {0, 0, 0, 0};
        for (int i=0; i<4; i++) {
            for(int j=1; j<6; j++) {
                switch (i) {
                    case 0: {
                        if (level.getBlockState(pos.offset(j, 0, 0)) == Blocks.AIR.defaultBlockState()) {
                            voidBlocksCount[0] += 1;
                        }
                    }
                    case 1: {
                        if (level.getBlockState(pos.offset(-j, 0, 0)) == Blocks.AIR.defaultBlockState()) {
                            voidBlocksCount[1] += 1;
                        }
                    }
                    case 2: {
                        if (level.getBlockState(pos.offset(0, 0, j)) == Blocks.AIR.defaultBlockState()) {
                            voidBlocksCount[1] += 1;
                        }
                    }
                    case 3: {
                        if (level.getBlockState(pos.offset(0, 0, -j)) == Blocks.AIR.defaultBlockState()) {
                            voidBlocksCount[1] += 1;
                        }
                    }
                }
            }

        }

        //if (Collections.max(voidBlocksCount))
    }

    public boolean isOnEdge(int radius, int val) {
        return (val == 0 || val == radius-1);
    }
}
