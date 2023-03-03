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
import java.util.List;
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
        int stemLength = ThreadLocalRandom.current().nextInt(1, 4 + 1);

        String dir = shroomDirection(origin, level);
        switch (dir) {
            case "" -> {
                for (int i = 0; i < stemLength; i++) {
                    level.setBlock(target_block, stem, 2);
                    target_block = target_block.below(1);
                }
            }
            case "N" -> {
                for (int i = 0; i < stemLength; i++) {
                    level.setBlock(target_block, stem, 2);
                    target_block = target_block.below().north();
                }
            }
            case "E" -> {
                for (int i = 0; i < stemLength; i++) {
                    level.setBlock(target_block, stem, 2);
                    target_block = target_block.below().east();
                }
            }
            case "W" -> {
                for (int i = 0; i < stemLength; i++) {
                    level.setBlock(target_block, stem, 2);
                    target_block = target_block.below().west();
                }
            }
            case "S" -> {
                for (int i = 0; i < stemLength; i++) {
                    level.setBlock(target_block, stem, 2);
                    target_block = target_block.below().south();
                }
            }
        }

        // Create mushroom cap
        for (int x=0; x<3; x++) {
            for (int z=0; z<3; z++) {
                level.setBlock(target_block.offset(x-1, 0, z-1), cap, 2);
            }
        }

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
        String[] dirs = {"E", "S", "W", "N"};
        int[] voidBlocksCount = {0, 0, 0, 0};
        int[] posNegArray = {1, 1, -1, -1};

       /* X - Determines your position East/West in the map. A positive value increases your position to the East. ...
          Y - Determines your position up/down in the map. A positive value increases your position upward. ...*/
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int z = 0;
            int p = posNegArray[i];
            if (i % 2 == 0) {
                x = p;
            } else {
                z = p;
            }
            for (int j = 1; j < 6; j++) {
                if (level.getBlockState(pos.offset(j * x, 0, j * z)) != Blocks.AIR.defaultBlockState()) {
                    voidBlocksCount[i]++;
                }
            }
        }
        int idxMax = 0;
        int max = voidBlocksCount[0];
        int cfIdxSum = -1;
        for(int i = 1; i < 4; i++) {
            if(voidBlocksCount[i] > max) {
                cfIdxSum = -1;
                max = voidBlocksCount[i];
                idxMax = i;
            } else if(voidBlocksCount[i] == max) {
                cfIdxSum = idxMax + i;
            }
        }
        if(max < 3 || cfIdxSum % 2 == 0) {
            return "";
        } else {
            return dirs[idxMax];
        }
    }
    public boolean isOnEdge(int radius, int val) {
        return (val == 0 || val == radius-1);
    }
}