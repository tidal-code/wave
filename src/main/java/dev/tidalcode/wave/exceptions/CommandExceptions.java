package dev.tidalcode.wave.exceptions;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandExceptions {

    private CommandExceptions() {
    }

    public static class Of {
        private Of() {
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> click() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(ElementClickInterceptedException.class, Messages.Exceptions::clickIntercepted);
            ignoredExceptions.put(ElementNotInteractableException.class, Messages.Exceptions::notInteractable);
            ignoredExceptions.put(StaleElementReferenceException.class, Messages.Exceptions::staleElementError);
            return ignoredExceptions;
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> sendKeys() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(ElementNotInteractableException.class, Messages.Exceptions::notInteractable);
            ignoredExceptions.put(StaleElementReferenceException.class, Messages.Exceptions::staleElementError);
            ignoredExceptions.put(SetTextException.class, Messages.Exceptions::setTextExceptionError);
            ignoredExceptions.put(KeyInputException.class, Messages.Exceptions::setTextExceptionError);
            return ignoredExceptions;
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> clear() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(ElementNotInteractableException.class, Messages.Exceptions::notInteractable);
            ignoredExceptions.put(StaleElementReferenceException.class, Messages.Exceptions::staleElementError);
            return ignoredExceptions;
        }
    }

    public static class TypeOf {
        private TypeOf() {
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> stale() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(StaleElementReferenceException.class, Messages.Exceptions::staleElementError);
            return ignoredExceptions;
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> notInteractable() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(ElementNotInteractableException.class, Messages.Exceptions::notInteractable);
            return ignoredExceptions;
        }

        public static Map<Class<? extends Throwable>, Supplier<String>> notInteractableAndStale() {
            HashMap<Class<? extends Throwable>, Supplier<String>> ignoredExceptions = new HashMap<>();
            ignoredExceptions.put(StaleElementReferenceException.class, Messages.Exceptions::staleElementError);
            ignoredExceptions.put(ElementNotInteractableException.class, Messages.Exceptions::notInteractable);
            return ignoredExceptions;
        }
    }
}
