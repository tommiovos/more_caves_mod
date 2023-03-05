package net.tom.test;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

public class TestRegion extends Region {
    public TestRegion() {
        super(new ResourceLocation(TestMod.MOD_ID, "biome_provider"), RegionType.OVERWORLD, 1);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        mapper.accept(Pair.of(BiomeReagentHandler.HOT_RED_PARAMETER, BiomeReagentHandler.HOT_RED));
    }

    public void init(ParallelDispatchEvent event) {
        event.enqueueWork(() -> Regions.register(new TestRegion()));
    }
}
