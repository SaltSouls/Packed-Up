package salted.packedup.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue STACK_FILLED_DRUMS = BUILDER
            .comment("Whether filled drum barrels stack up to 64.")
            .define("stackFilledDrums", false);

    private static final ForgeConfigSpec.IntValue MAX_DRUM_STACK = BUILDER
            .comment("Max stack size for filled drums.")
            .defineInRange("maxDrumStack", 8, 1, 64);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean stackFilledDrums() {
        return SPEC.isLoaded() && STACK_FILLED_DRUMS.get();
    }
    public static int maxDrumStack() {
        return SPEC.isLoaded() ? MAX_DRUM_STACK.get() : 1;
    };
}