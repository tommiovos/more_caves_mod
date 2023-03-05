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

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes
{
    protected static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registry.BIOME_REGISTRY, TestMod.MOD_ID);

    public static void registerBiomes()
    {
        register(TestBiomes.HOT_RED, ModBiomes::hotRed);
    }

    public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier)
    {
        return BIOME_REGISTER.register(key.location().getPath(), biomeSupplier);
    }

    @SuppressWarnings("SameParameterValue")
    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3;
        $$1 = Mth.clamp($$1, -1, 1);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1);
    }

    public static Biome hotRed() {
        double energyBudget = 1D;
        double charge = 0.8D;

        MobSpawnSettings spawnSettings = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 3, 5, 5))
                .addMobCharge(EntityType.SKELETON, energyBudget, charge)
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder()
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                //.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND)
                //.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE)
                //.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_VEGETATION)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.getHolder(ModPlacedFeatures.CAVE_FUNGI.get()))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.getHolder(ModPlacedFeatures.SMALL_BLUESHROOM.get()));

        BiomeDefaultFeatures.addDefaultOres(generationSettings);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .temperature(2)
                .downfall(0)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(0x593a21)
                                .skyColor(calculateSkyColor(2))
                                .build()
                )
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings.build())
                .build();
    };
}
