package salted.packedup.common.registry.helpers;

import java.util.LinkedHashMap;
import java.util.Map;

import static salted.packedup.PackedUp.MODID;

public abstract class Translations {
    private final Map<String, String> entries = new LinkedHashMap<>();
    private boolean collected;

    public abstract String locale();

    protected abstract void addTranslations();

    public Map<String, String> entries() {
        if (!collected) {
            addTranslations();
            collected = true;
        }
        return entries;
    }

    protected void lang(String key, String value) {
        entries.put(key, value);
    }
    protected void name(String path, String display) {
        lang("block." + MODID + "." + path, display);
    }
    protected void tooltip(String path, String value) {
        lang("tooltip." + MODID + "." + path, value);
    }
    protected void subtitle(String path, String value) {
        lang("subtitles." + MODID + "." + path, value);
    }

    protected static String titleCase(String path) {
        StringBuilder name = new StringBuilder();

        for (String word : path.split("_")) {
            if (!name.isEmpty()) name.append(" ");
            name.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return name.toString();
    }
}
