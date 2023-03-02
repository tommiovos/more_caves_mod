/**
 * Copyright (C) Glitchfiend
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package terrablender.example;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class TestSurfaceRuleData
{
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource RED_TERRACOTTA = makeStateRule(Blocks.RED_TERRACOTTA);
    private static final SurfaceRules.RuleSource BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA);

    private static final SurfaceRules.RuleSource DRIPSTONE = makeStateRule(Blocks.DRIPSTONE_BLOCK);

    private static final SurfaceRules.RuleSource SLIME_BLOCK = makeStateRule(Blocks.SLIME_BLOCK);
    private static final SurfaceRules.RuleSource LIMESTONE_BLOCK = makeStateRule(ModBlocks.LIMESTONE_BLOCK.get());
    protected static SurfaceRules.RuleSource makeRules()
    {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource below35  = SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(35), 0));
        // Second param : Lower = more common
        SurfaceRules.ConditionSource caveCheeseNoise = SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, 1.0D);
        SurfaceRules.ConditionSource noise = SurfaceRules.noiseCondition(Noises.PATCH, -1.0D);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);


        return SurfaceRules.sequence(
                // If we are in the Hot red biome
            SurfaceRules.ifTrue(SurfaceRules.isBiome(TestBiomes.HOT_RED),
                    // Sequence of multiple rules
                    SurfaceRules.sequence(
                            // If ConditionSource is true, place said block
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SurfaceRules.ifTrue(noise, DRIPSTONE)),
                            SurfaceRules.ifTrue(below35, LIMESTONE_BLOCK),
                            // default to Grass block otherwise
                            GRASS_BLOCK
                    )
            ),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(TestBiomes.COLD_BLUE), BLUE_TERRACOTTA),

            // Default to a grass and dirt block if on floor
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
