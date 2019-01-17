package br.org.pti.fpti_base.application.i18n;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public ResourceBundleMessageSource() {
        MessageSourceHolder.setMessageSource(this);
    }

    public Properties getProperties(Locale locale) {
        super.clearCacheIncludingAncestors();

        final PropertiesHolder propertiesHolder = super.getMergedProperties(locale);
        return propertiesHolder.getProperties();
    }
}
