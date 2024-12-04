package salted.packedup.common.block.state;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import salted.packedup.common.block.state.properties.PileShape;

public class PUProperties {

    public static IntegerProperty QUARTER_LAYERS = IntegerProperty.create("layers", 1, 4);
    public static IntegerProperty ROPE_DISTANCE = IntegerProperty.create("distance", 0, 4);

    public static EnumProperty<PileShape> PILE_SHAPE = EnumProperty.create("shape", PileShape.class);
}