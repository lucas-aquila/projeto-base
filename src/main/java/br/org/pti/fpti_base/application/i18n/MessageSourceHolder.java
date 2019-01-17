package br.org.pti.fpti_base.application.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageSourceHolder {

    private static MessageSource messageSource;

    public static String getMessage(String code) {
        return getMessage(code, code);
    }

    public static String getMessage(String code, String defaultMessage) {
        return getMessage(code, defaultMessage, (Object[]) null);
    }

    public static String getMessage(String code, Object... args) {
        return getMessage(code, code, args);
    }

    public static String getMessage(String code, String defaultMessage, Object... args) {
        return getMessage(LocaleContextHolder.getLocale(), code, defaultMessage, args);
    }

    /* (non-Javadoc)
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String, java.util.Locale)
     */
    public static String getMessage(Locale locale, String code, String defaultMessage, Object... args) {
        return getMessageSource().getMessage(code, args, defaultMessage, locale);
    }

    /* (non-Javadoc)
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)
     */
    public static String getMessage(Locale locale, String code, Object... args) throws NoSuchMessageException {
        return getMessageSource().getMessage(code, args, locale);
    }

    /* (non-Javadoc)
     * @see org.springframework.context.MessageSource#getMessage(org.springframework.context.MessageSourceResolvable, java.util.Locale)
     */
    public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return getMessageSource().getMessage(resolvable, locale);
    }

    public static MessageSource getMessageSource() {
        if (messageSource == null) {
            throw new IllegalArgumentException("Was not possible to get a valid messageSource instance.");
        }

        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageSourceHolder.messageSource = messageSource;
    }

    public static String translate(String code) {
        return getMessage(code);
    }

    public static String translate(String code, Object... args) {
        return getMessage(code, args);
    }

}
