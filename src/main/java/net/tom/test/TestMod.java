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
package net.tom.test;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tom.test.block.ModBlocks;
import net.tom.test.features.ModFeatures;
import net.tom.test.items.ModItems;
import terrablender.api.SurfaceRuleManager;

@Mod(TestMod.MOD_ID)
public class TestMod
{
    public static final String MOD_ID = "test";

    public TestMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModPlacementModifiers.PLACEMENT_MODIFIER_TYPES.register(bus);
        ModBiomes.BIOME_REGISTER.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
        ModPlacedFeatures.PLACED_FEATURES.register(bus);
        ModBiomes.registerBiomes();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, TestSurfaceRuleData.makeRules());
        });
    }
}
