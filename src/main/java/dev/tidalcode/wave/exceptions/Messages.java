package dev.tidalcode.wave.exceptions;

public class Messages {

    private Messages() {
    }

    public static class Exceptions {

        private Exceptions() {
        }

        public static String staleElementError() {
            return "Stale Element exception occurred with element %s. \n" +
                    "The page may have refreshed or element may have changed. \n" +
                    "TryUntil to add more waits or retry with the find method.\n" +
                    "Else try using expected conditions as a conditional wait";
        }

        public static String clickIntercepted() {
            return "Click intercepted exception occurred with element %s. \n" +
                    "Element click intercepted.\n" +
                    "Check for a spinner or other element overlapping the element.\n" +
                    "TryUntil using expected conditions as a conditional wait or add more wait time if a spinner is present";
        }

        public static String notInteractable() {
            return "Not interactable exception occurred with element %s. \n" +
                    "Element is in not interactable state\n" +
                    "Check the element is disabled or not visible or out of the view port.\n" +
                    "TryUntil using using 'scrollToView()' method or assert that the element is visible.\n" +
                    "Calling the 'moveToElement()' function also brings the element to the view";
        }

        public static String setTextExceptionError() {
            return "SetText exception error happened %s. \n" +
                    "Make sure that you are not trying to input password or 'Keys'.\n" +
                    "setText method reads back the input and ensure that it is entered correctly.\n" +
                    "It cannot read password inputs or Keys sent to the input field.\n" +
                    "Also make sure that the element is in a proper interactable state.";
        }
    }
}
