package net.tom.test.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModBlockProperties {

    public static BlockBehaviour.Properties LIMESTONE_BLOCK = BlockBehaviour.Properties.of(Material.STONE)
            .strength(4)
            .sound(SoundType.STONE);

    public static BlockBehaviour.Properties ENRICHED_DIRT_BLOCK = BlockBehaviour.Properties.of(Material.DIRT)
            .strength(2)
            .sound(SoundType.ROOTED_DIRT);

    public static BlockBehaviour.Properties ENRICHED_MYCELIUM_BLOCK = BlockBehaviour.Properties.of(Material.DIRT)
            .strength(1)
            .sound(SoundType.STEM);
}
