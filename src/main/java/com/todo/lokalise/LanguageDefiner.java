package com.todo.lokalise;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LanguageDefiner {

        private static ResourceBundleMessageSource messageSource;

        LanguageDefiner (ResourceBundleMessageSource messageSource) {
            LanguageDefiner.messageSource = messageSource;
        }

        public static String toLocale(String message, String... args) {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(message, args, locale);
        }

}
