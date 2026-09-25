package salted.packedup.common.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum PillarShape implements StringRepresentable {
    TOP("top"),
    BOTTOM("bottom"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right"),
    SINGLE("single");

    private final String name;

    PillarShape(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
