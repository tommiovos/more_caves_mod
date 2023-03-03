package net.tom.test.items;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tom.test.TestMod;
import net.tom.test.block.ModBlocks;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registry.ITEM_REGISTRY, TestMod.MOD_ID);

    public static final RegistryObject<Item> LIMESTONE_BLOCK = blockItem(ModBlocks.LIMESTONE_BLOCK);
    public static final RegistryObject<Item> ENRICHED_DIRT_BLOCK = blockItem(ModBlocks.ENRICHED_DIRT_BLOCK);
    public static final RegistryObject<Item> ENRICHED_MYCELIYUM_BLOCK = blockItem(ModBlocks.ENRICHED_MYCELIUM_BLOCK);

    private static RegistryObject<Item> blockItem(RegistryObject<? extends Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties()));
    }

    private static Item.Properties properties() {
        return new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
    }
}
