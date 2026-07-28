package it.girasolia.matrixgenlatex.bins;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class TranslationProvider implements I18NProvider {

    private final ResourceBundleMessageSource messageSource;

    public TranslationProvider() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(
                Locale.of("ru"),
                Locale.ENGLISH,
                Locale.ITALIAN
        );
    }

    @Override
    public String getTranslation(String key,
                                 Locale locale,
                                 Object... params) {

        return messageSource.getMessage(
                key,
                params,
                locale
        );
    }
}
