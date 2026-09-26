package salted.packedup.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.helpers.Translations;

public class PULanguages extends LanguageProvider {
    private final Translations translations;

    public PULanguages(PackOutput output, Translations translations) {
        super(output, PackedUp.MODID, translations.locale());
        this.translations = translations;
    }

    @Override
    protected void addTranslations() {
        translations.entries().forEach(this::add);
    }
}