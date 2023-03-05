package net.tom.test.features;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.tom.test.block.ModBlocks;
import net.tom.test.features.configuration.HotCaveVegetationConfig;

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
        //HotCaveVegetationConfig config = ctx.config();
        //RandomSource randomSource = ctx.random();

        //int stemLength = ThreadLocalRandom.current().nextInt(2, 4 + 1);
        //int radius = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        int stemLengthHardCap = 5;
        int maxStemLength = deterministicMaxStemLength(origin, level, stemLengthHardCap);
        if(maxStemLength == 0) return false;
        int stemLength = maxStemLength == 1 ? 1 : ThreadLocalRandom.current().nextInt(Math.max(1, maxStemLength - 2), maxStemLength);
        int radius = 1;

        BlockState stem = Blocks.MUSHROOM_STEM.defaultBlockState();
        BlockState cap = Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState();
        BlockState enriched_mycelium = ModBlocks.ENRICHED_MYCELIUM_BLOCK.get().defaultBlockState();
        BlockState enriched_dirt = ModBlocks.ENRICHED_DIRT_BLOCK.get().defaultBlockState();

        //boolean isBigMushroom = canBuildBigMushroom(origin, level, stemLength);
        boolean isBigMushroom = true;
        level.setBlock(target_block, stem, 2);
        if(stemLength > 1) {
            for (int i = 1; i < stemLength; i++) {
                target_block = target_block.below();
                level.setBlock(target_block, stem, 2);
            }
        }

        target_block = target_block.below();

        // Create mushroom
        for (int x=-radius; x<=radius; x++) {
            for (int z=-radius; z<=radius; z++) {
                if(stemLength == 1) {
                    if(!isBlockAir(target_block.offset(x, 0, z), level)) continue;
                }
                level.setBlock(target_block.offset(x, 0, z), cap, 2);
            }
        }

        // change enriched dirt around to enriched mycelium
        radius = 5;
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
                                isBlockAir(target_block.below(), level)) {
                            level.setBlock(target_block, enriched_mycelium, 2);
                        }
                    }
                }
            }
        }

        return true;
    }

    public int deterministicMaxStemLength(BlockPos pos, WorldGenLevel level, int hardCap) {

        /*

            | |           KO = 0
            | |   OK = 1; KO = 0
          | | | |         KO = 1
          | | | |      2       1
          | | | |      3       2
            ...        4       3
         */

        if(!isBlockAir(pos, level)) return 0;
        pos = pos.below();
        if(!isBlockAir(pos, level)) return 0;
        int length = 1;
        pos = pos.below();
        if(!isAreaAllFree(pos, level, -1, 1, 0, 0, -1, 1)) return length;

        for(int i = 0; i < hardCap-2; i++) {
            pos = pos.below();
            if(!isAreaAllFree(pos, level, -1, 1, 0, 0, -1, 1)) return length;
            length++;
        }
        return length;
    }

    public boolean isOnEdge(int radius, int val) {
        return (val == 0 || val == radius-1);
    }

    public boolean isBlockAir(BlockPos pos, WorldGenLevel level) {
        return level.getBlockState(pos) == Blocks.AIR.defaultBlockState();
    }

    public boolean isAreaAllFree(BlockPos pos, WorldGenLevel level, int xOffsetStart, int xOffsetEnd, int yOffsetStart, int yOffsetEnd, int zOffsetStart, int zOffsetEnd) {
        for(int x = xOffsetStart; x <= xOffsetEnd; x++) {
            for(int y = yOffsetStart; y <= yOffsetEnd; y++) {
                for(int z = zOffsetStart; z <= zOffsetEnd; z++) {
                    if(!isBlockAir(pos.offset(x,y,z), level)) return false;
                }
            }
        }
        return true;
    }

    public boolean isAreaAnyFree(BlockPos pos, WorldGenLevel level, int xOffsetStart, int xOffsetEnd, int yOffsetStart, int yOffsetEnd, int zOffsetStart, int zOffsetEnd) {
        for(int x = xOffsetStart; x <= xOffsetEnd; x++) {
            for(int y = yOffsetStart; y <= yOffsetEnd; y++) {
                for(int z = zOffsetStart; z <= zOffsetEnd; z++) {
                    if(isBlockAir(pos.offset(x,y,z), level)) return true;
                }
            }
        }
        return false;
    }

    public boolean isAreaAmountFree(BlockPos pos, WorldGenLevel level, int xOffsetStart, int xOffsetEnd, int yOffsetStart, int yOffsetEnd, int zOffsetStart, int zOffsetEnd, int amount) {
        int ct = 0;
        for(int x = xOffsetStart; x <= xOffsetEnd; x++) {
            for(int y = yOffsetStart; y <= yOffsetEnd; y++) {
                for(int z = zOffsetStart; z <= zOffsetEnd; z++) {
                    if(isBlockAir(pos.offset(x,y,z), level)) ct++;
                    if(ct == amount) return true;
                }
            }
        }
        return ct >= amount;
    }

    /**
     * @apiNote Returns whether at least the portion of the specified area is free. Rounds down, ex : 0.5 of a 3 block area will return False for 1 free, block, true for >=2.
     * @param portion range ]0; 1]
     */
    public boolean isAreaPortionFree(BlockPos pos, WorldGenLevel level, int xOffsetStart, int xOffsetEnd, int yOffsetStart, int yOffsetEnd, int zOffsetStart, int zOffsetEnd, double portion) {
        int amount = (int) Math.ceil((xOffsetEnd - xOffsetStart) * (yOffsetEnd - yOffsetStart) * (zOffsetEnd - zOffsetStart) * portion);
        return isAreaAmountFree(pos, level, xOffsetStart, xOffsetEnd, yOffsetStart, yOffsetEnd, zOffsetStart,zOffsetEnd, amount);
    }




    public int spaceAvailableBelow(int radius, int height, boolean checkCircleShaped, WorldGenLevel level, BlockPos pos) {
        int emptyBlocksCount = 0;
        //int offsetXZ = -Math.round(radius/2F);
        int offsetXZ = 0;

        if (checkCircleShaped) {
            for(int y=1; y<height+1; y++) {
                /*for (int i = y-radius; i < y+radius; i++) {
                    for (int j = 0; Math.pow(j,2) + Math.pow((i-y),2) <= Math.pow(radius,2); j--) {
                        //in the circle
                    }
                    for (int j = 1; j*j + (i-y)*(i-y) <= radius*radius; j++) {
                        //in the circle
                    }
                }*/
                for (int x = -radius+offsetXZ; x <= radius+offsetXZ; x++) {
                    for (int z = -radius+offsetXZ; z <= radius+offsetXZ; z++) {
                        if (x*x + z*z <= radius*radius){
                            if (level.getBlockState(pos.offset(x, y, z)) == Blocks.AIR.defaultBlockState()) {
                                emptyBlocksCount++;
                            }
                        }
                    }
                }
            }
        }
        else {
            for(int y=0; y<height; y++) {
                for(int x= -radius+offsetXZ; x<radius+offsetXZ; x++) {
                    for(int z = -radius+offsetXZ; z<radius+offsetXZ; z++) {
                        if (level.getBlockState(pos.offset(x, y, z)) == Blocks.AIR.defaultBlockState()) {
                            emptyBlocksCount++;
                        }
                    }
                }
            }
        }
        return emptyBlocksCount;
    }
}