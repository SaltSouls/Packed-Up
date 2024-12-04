package salted.packedup.common.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum PileShape implements StringRepresentable {
    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower"),
    SINGLE("single");

    private final String name;

    PileShape(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
