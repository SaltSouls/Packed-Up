package salted.packedup.common.block.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import salted.packedup.common.block.state.properties.PillarShape;

public class PUProperties {

    public static IntegerProperty QUARTER_LAYERS = IntegerProperty.create("layers", 1, 4);
    public static IntegerProperty ROPE_DISTANCE = IntegerProperty.create("distance", 0, 4);

    public static EnumProperty<PillarShape> PILLAR_SHAPE = EnumProperty.create("shape", PillarShape.class);
    public static BooleanProperty FULL_SIZE = BooleanProperty.create("full_size");
}