package net.tom.test.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

public class ModBlockProperties {

    //public static final Material STEM_MAT = new Material.Builder(MaterialColor.NONE).noCollider().notSolidBlocking().nonSolid().destroyOnPush().build();;

    public static BlockBehaviour.Properties LIMESTONE_BLOCK = BlockBehaviour.Properties.of(Material.STONE)
            .strength(4)
            .sound(SoundType.STONE);

    public static BlockBehaviour.Properties ENRICHED_DIRT_BLOCK = BlockBehaviour.Properties.of(Material.DIRT)
            .strength(2)
            .sound(SoundType.ROOTED_DIRT);

    public static BlockBehaviour.Properties ENRICHED_MYCELIUM_BLOCK = BlockBehaviour.Properties.of(Material.DIRT)
            .strength(1)
            .sound(SoundType.STEM);

    public static BlockBehaviour.Properties BLUESHROOM_STEM_BLOCK = BlockBehaviour.Properties.of(Material.PLANT)
            .randomTicks()
            .strength(1)
            .sound(SoundType.STEM)
            .lightLevel(litBlockEmission(6))
            .noOcclusion()
            .dynamicShape();

    public static BlockBehaviour.Properties BLUESHROOM_CAP_BLOCK = BlockBehaviour.Properties.of(Material.PLANT)
            .randomTicks()
            .strength(1)
            .sound(SoundType.STEM)
            .lightLevel(litBlockEmission(6))
            .noOcclusion()
            .dynamicShape();

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (state) -> {
            return lightLevel;
        };
    }
}
