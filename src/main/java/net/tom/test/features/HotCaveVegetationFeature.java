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
        int stemLength = 2;
        int radius = 2;
        int allowedNonEmptyBlocksCount = 0;

        /*System.out.println(Integer.toString(((radius*2*2) *stemLength) - allowedNonEmptyBlocksCount));*/
        int spaceAv = spaceAvailableBelow(radius, stemLength+1, false, level, origin);
        if (spaceAv < ( (radius*2*2) *stemLength) - allowedNonEmptyBlocksCount) {
            return false;
        };
        System.out.println("**********************");
        System.out.println("x :" + Integer.toString(origin.getX()) +", y :" + Integer.toString(origin.getY()) + ", z :" + Integer.toString(origin.getZ()));
        System.out.println("space available : " + Integer.toString(spaceAv));
        System.out.println("**********************");
        BlockState stem = Blocks.MUSHROOM_STEM.defaultBlockState();
        BlockState cap = Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState();
        BlockState enriched_mycelium = ModBlocks.ENRICHED_MYCELIUM_BLOCK.get().defaultBlockState();
        BlockState enriched_dirt = ModBlocks.ENRICHED_DIRT_BLOCK.get().defaultBlockState();

        //boolean isBigMushroom = canBuildBigMushroom(origin, level, stemLength);
        boolean isBigMushroom = true;

        level.setBlock(target_block, stem, 2);
        if(isBigMushroom) {
            for(int i = 1; i < stemLength; i++) {
                target_block = target_block.below();
                level.setBlock(target_block, stem, 2);
            }
        }

        target_block = target_block.below();

        // Create mushroom cap
        for (int x=-(radius/2); x<(radius/2); x++) {
            for (int z=-(radius/2); z<(radius/2); z++) {
                if(!isBigMushroom) {
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

    public boolean canBuildBigMushroom(BlockPos pos, WorldGenLevel level, int stemLength) {
        for(int i = 1; i<=stemLength+1; i++) {
            pos = pos.below();
            if(!isBlockAir(pos, level)) return false;
        }
        for(int z = -1; z < 2; z++) {
            for (int x = -1; x < 2; x++) {
                if(!isBlockAir(pos.offset(x,0,z), level)) return false;
            }
        }
        return true;
    }

    public boolean isOnEdge(int radius, int val) {
        return (val == 0 || val == radius-1);
    }

    public boolean isBlockAir(BlockPos pos, WorldGenLevel level) {
        return level.getBlockState(pos) == Blocks.AIR.defaultBlockState();
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
                        };
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