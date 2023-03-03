package net.tom.test.block;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tom.test.items.ModItems;
import net.tom.test.TestMod;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registry.BLOCK_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<Block> LIMESTONE_BLOCK = block("limestone_block", ModBlockProperties.LIMESTONE_BLOCK);
    public static final RegistryObject<Block> ENRICHED_DIRT_BLOCK = block("enriched_dirt_block", ModBlockProperties.ENRICHED_DIRT_BLOCK);
    public static final RegistryObject<Block> ENRICHED_MYCELIUM_BLOCK = block("enriched_mycelium_block", ModBlockProperties.ENRICHED_MYCELIUM_BLOCK);
    public static final RegistryObject<Block> BLUESHROOM_STEM_BLOCK = BLOCKS.register("blueshroom_stem_block", () -> new BlueshroomStemBlock(ModBlockProperties.BLUESHROOM_STEM_BLOCK));
    public static final RegistryObject<Block> BLUESHROOM_CAP_BLOCK = BLOCKS.register("blueshroom_cap_block", () -> new BlueshroomCapBlock(ModBlockProperties.BLUESHROOM_CAP_BLOCK));


    private static RegistryObject<Block> block(String name, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, () -> new Block(properties));
    }
}
