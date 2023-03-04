package net.tom.test.features.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SmallBlueshroomConfig(
        BlockState validBaseBlockState
) implements FeatureConfiguration {
    public static final Codec<SmallBlueshroomConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
                    BlockState.CODEC.fieldOf("valid_base_block").forGetter((config) -> config.validBaseBlockState)
            ).apply(builder, SmallBlueshroomConfig::new)
    );
}
