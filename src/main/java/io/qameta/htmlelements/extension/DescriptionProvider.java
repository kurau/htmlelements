package io.qameta.htmlelements.extension;

import io.qameta.htmlelements.context.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HandleWith(DescriptionProvider.Extension.class)
@ExtendWith(DescriptionProvider.Extension.class)
public @interface DescriptionProvider {

    class Extension implements ContextEnricher, MethodHandler<String> {

        private static final String DESCRIPTION_KEY = "description";

        @Override
        public void enrich(Context context, Method method, Object[] args) {
            context.getStore().put(DESCRIPTION_KEY, method.getName());
        }

        @Override
        public String handle(Context context, Object proxy, Object[] args) {
            return context.getStore().get(DESCRIPTION_KEY, String.class)
                    .orElseThrow(() -> new NoSuchElementException("Missing description"));
        }
    }

}
