package salted.packedup.common.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class PUSoundsTypes {

    public static final SoundType RESOURCE_CRATE = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOD_BREAK,
            () -> SoundEvents.POLISHED_DEEPSLATE_STEP,
            () -> SoundEvents.WOOD_PLACE,
            () -> SoundEvents.WOOD_HIT,
            () -> SoundEvents.POLISHED_DEEPSLATE_FALL);
    public static final SoundType GUNPOWDER_CRATE = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOD_BREAK,
            () -> SoundEvents.SAND_STEP,
            () -> SoundEvents.WOOD_PLACE,
            () -> SoundEvents.WOOD_HIT,
            () -> SoundEvents.SAND_FALL);
    public static final SoundType CRYSTAL_CRATE = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOD_BREAK,
            () -> SoundEvents.AMETHYST_BLOCK_STEP,
            () -> SoundEvents.WOOD_PLACE,
            () -> SoundEvents.WOOD_HIT,
            () -> SoundEvents.AMETHYST_BLOCK_FALL);
    public static final SoundType SHARD_CRATE = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOD_BREAK,
            () -> SoundEvents.SCULK_CATALYST_STEP,
            () -> SoundEvents.WOOD_PLACE,
            () -> SoundEvents.WOOD_HIT,
            () -> SoundEvents.SCULK_CATALYST_FALL);
    public static final SoundType RESOURCE_BAG_DIRT = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOL_BREAK,
            () -> SoundEvents.ROOTED_DIRT_STEP,
            () -> SoundEvents.WOOL_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.ROOTED_DIRT_FALL);
    public static final SoundType RESOURCE_BAG_GRAVEL = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOL_BREAK,
            () -> SoundEvents.GRAVEL_STEP,
            () -> SoundEvents.WOOL_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.GRAVEL_FALL);
    public static final SoundType APPLE_BASKET = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.BAMBOO_WOOD_BREAK,
            () -> SoundEvents.WOOD_STEP,
            () -> SoundEvents.BAMBOO_WOOD_PLACE,
            () -> SoundEvents.BAMBOO_WOOD_HIT,
            () -> SoundEvents.WOOD_FALL);
    public static final SoundType BERRY_BASKET = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.BAMBOO_WOOD_BREAK,
            () -> SoundEvents.MUD_STEP,
            () -> SoundEvents.BAMBOO_WOOD_PLACE,
            () -> SoundEvents.BAMBOO_WOOD_HIT,
            () -> SoundEvents.MUD_FALL);
    public static final SoundType PRODUCE_BAG = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOL_BREAK,
            () -> SoundEvents.MUD_STEP,
            () -> SoundEvents.WOOL_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.MUD_STEP);
    public static final SoundType POWDER_BAG = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOL_BREAK,
            () -> SoundEvents.SAND_STEP,
            () -> SoundEvents.WOOL_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.SAND_FALL);
    public static final SoundType GLASS_BAG = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOL_BREAK,
            () -> SoundEvents.SCULK_CATALYST_STEP,
            () -> SoundEvents.WOOL_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.SCULK_CATALYST_FALL);
    public static final SoundType FISH_BARREL = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.WOOD_BREAK,
            () -> SoundEvents.MUD_STEP,
            () -> SoundEvents.WOOD_PLACE,
            () -> SoundEvents.WOOL_HIT,
            () -> SoundEvents.MUD_FALL);
    public static final SoundType BOOKS = new ForgeSoundType(
            1.0F,
            1.0F,
            () -> SoundEvents.CHISELED_BOOKSHELF_PICKUP,
            () -> SoundEvents.BOOK_PUT,
            () -> SoundEvents.CHISELED_BOOKSHELF_INSERT,
            () -> SoundEvents.BOOK_PUT,
            () -> SoundEvents.BOOK_PUT);
}
