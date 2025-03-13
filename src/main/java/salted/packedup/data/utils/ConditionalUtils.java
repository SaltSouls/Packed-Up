package salted.packedup.data.utils;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.*;

public class ConditionalUtils {

    // conditions
    public static ICondition modLoaded(String modID) {
        return new ModLoadedCondition(modID);
    }

    public static ICondition itemRegistered(String itemID) {
        return new ItemExistsCondition(itemID);
    }

    public static ICondition Not(ICondition condition) {
        return new NotCondition(condition);
    }

    public static ICondition And(ICondition... values) {
        return new AndCondition(values);
    }

    public static ICondition Or(ICondition... values) {
        return new OrCondition(values);
    }


    // one day I will find a use for these...
    public static ItemPredicate items(ItemLike... items) {
        return ItemPredicate.Builder.item().of(items).build();
    }

    public static ItemPredicate tagItem(TagKey<Item> itemTag) {
        return ItemPredicate.Builder.item().of(itemTag).build();
    }

    public static InventoryChangeTrigger.TriggerInstance multiCriteria(ItemPredicate... predicates) {
        return inventoryTrigger(predicates);
    }

    // copy of forge's function
    private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... itemPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, itemPredicates);
    }

}
