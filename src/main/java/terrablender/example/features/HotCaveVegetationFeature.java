package terrablender.example.features;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import terrablender.example.features.configuration.HotCaveVegetationConfig;

public class HotCaveVegetationFeature extends Feature<HotCaveVegetationConfig> {

    public HotCaveVegetationFeature(Codec<HotCaveVegetationConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<HotCaveVegetationConfig> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        BlockState state = level.getBlockState(origin.below());
        HotCaveVegetationConfig config = ctx.config();
        RandomSource randomSource = ctx.random();
        BlockState blockstate = Blocks.ACACIA_LOG.defaultBlockState();
        level.setBlock(origin, blockstate, 2);
        return true;
    }
}
