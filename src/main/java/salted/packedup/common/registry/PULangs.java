package salted.packedup.common.registry;

import salted.packedup.common.registry.lang.English;
import salted.packedup.common.registry.helpers.Translations;
import salted.packedup.common.registry.lang.Japanese;
import salted.packedup.common.registry.lang.Ukrainian;

import java.util.List;
import java.util.Map;

import static salted.packedup.PackedUp.MODID;
import static salted.packedup.common.registry.helpers.RegistryHelper.REGISTRATE;

public class PULangs {
    public static final English ENGLISH = new English();

    public static final List<Translations> TRANSLATED = List.of(
            new Japanese(),
            new Ukrainian()
    );

    private static final String NAME_PREFIX = "block." + MODID + ".";

    public static void register() {
        ENGLISH.entries().forEach((key, value) -> {
            if (key.startsWith(NAME_PREFIX)) return;
            REGISTRATE.addRawLang(key, value);
        });
    }

    public static String name(String path) {
        return ENGLISH.entries().get(NAME_PREFIX + path);
    }
    public static Map<String, String> entries(Translations translations) {
        return translations.entries();
    }
}
