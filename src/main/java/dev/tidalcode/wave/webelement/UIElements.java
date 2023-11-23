package dev.tidalcode.wave.webelement;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindAllTextData;
import dev.tidalcode.wave.commands.GetSize;
import dev.tidalcode.wave.commands.IsPresent;
import dev.tidalcode.wave.tabsandwindows.Frames;
import dev.tidalcode.wave.verification.conditions.collections.CollectionsCondition;
import dev.tidalcode.wave.verification.expectations.CollectionsSoftAssertion;
import dev.tidalcode.wave.verification.expectations.collections.Expectations;
import dev.tidalcode.wave.wait.ThreadSleep;
import dev.tidalcode.wave.wait.Wait;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static dev.tidalcode.wave.tabsandwindows.Frames.switchToFrame;
import static dev.tidalcode.wave.verification.conditions.TestVerification.verification;


@SuppressWarnings("parameterized")
public class UIElements extends AbstractCollection<UIElement> {

    private UIActions uiActions;
    private boolean visibility;

    private int currentIndex = 0;
    private int size = -1;
    private final LinkedList<UIElement> dimensions;

    public UIElements() {
        dimensions = new LinkedList<>();
    }

    @Override
    public Iterator<UIElement> iterator() {
        return new UIElementsIterator();
    }

    protected UIElements setProperties(String byLocator) {
        visibility = false;
        uiActions = new UIActions();
        uiActions.setProperties(byLocator);
        uiActions.setMultiple();
        return this;
    }

    //For thenFindAll in UIElement
    protected void setElementProperties(UIActions uiActions) {
        this.uiActions = uiActions;
        this.visibility = false;
        uiActions.setMultiple();
    }

    protected UIElements withDefaultWait() {
        Wait.setDefaultWait();
        return this;
    }

    /**
     * findAll by default switches to the first iframe where the elements are present regardless of them visible or not.
     * If you need to confine the switching to the frame where at least one visible element is found, use this method
     *
     * @return A self reference
     */
    public UIElements visibleElements() {
        this.visibility = true;
        return this;
    }

    /**
     * Returns an UIElement from the list of all elements.
     *
     * @param index of the element to be found
     * @return UIElement instance
     */
    public UIElement get(int index) {
        uiActions.setElementIndex(index);
        return uiActions;
    }

    public UIElement first() {
        return get(0);
    }

    public UIElement last() {
        return get(size() - 1);
    }

    public void skipFirst() {
        currentIndex = 1;
    }

    public void skipLast() {
        size = size() - 1;
    }

    /**
     * The waitFor method is applicable only for the expected conditions.
     * @return A self reference
     */
    public UIElements waitFor(int waitTime) {
        Wait.setExplicitWait(waitTime);
        expecting(Expectations.sizeGreaterThan(0));
        return this;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof UIElement) {
            UIElement element = (UIElement) o;
//            size = size - 1;
            dimensions.remove(element);
            size = dimensions.size();
            return true;
        }
        return false;
    }


    /**
     * To get the list of text from all similar elements in the page
     *
     * @return List of text from all similar elements
     */
    public List<String> getAllText() {
        return new Executor().usingLocator(uiActions.getLocators()).isVisible(visibility).invokeCommand(FindAllTextData.class);
    }

    /**
     * To get the number of elements present in the current DOM. It is iframe specific
     *
     * @return the number of WebElements present in the DOM
     */
    @Override
    public int size() {
        size = new Executor().usingLocator(uiActions.getLocators()).isVisible(visibility).invokeCommand(GetSize.class);
        if (dimensions.isEmpty()) {
            for (int i = 0; i < size; i++) {
                dimensions.add(get(i));
            }
        }
        return size;
    }

    /**
     * Though the framework automatically switches to the iframe where element belongs, sometimes it won't work as expected as there would be identical
     * elements in multiple frames. This method can be used to switch to the iframe manually. If there are nested frames, there is no need to switch to
     * them individually. The framework will switch to the right iframe if it can be uniquely identified using its properties.
     *
     * @param locatorMatcher The locator to be used to identify the iframe
     * @return A self reference
     */
    public UIElements inFrame(String locatorMatcher) {
        Frames.switchToFrame(locatorMatcher);
        return this;
    }

    /**
     * Method to check if the element is present in the DOM
     *
     * @return true or false depending on whether the element is displayed or not
     */
    public boolean isPresent() {
        return new Executor().usingLocator(uiActions.getLocators()).isVisible(visibility).invokeCommand(IsPresent.class);
    }

    /**
     * <p>Just the plain old Thread.sleep wrapped in a method with a nicer name. </p>
     * <p>Use it when absolutely necessary as this is a static wait and would slow down the tests.</p>
     *
     * @param seconds The time in seconds
     * @return A self reference
     */
    public UIElements pause(int seconds) {
        ThreadSleep.forSeconds(seconds);
        return this;
    }

    /**
     * Collections condition for verification purpose.
     *
     * @param condition Collections conditions
     */
    public void shouldHave(CollectionsCondition... condition) {
        verification(uiActions.getExecutor(), condition);
    }

    /**
     * Expected conditions to meet. They do not fail even if the conditions were not met. <br>
     * Add <code>orElseFail()</code> to make the expected condition to fail.
     *
     * @param expectations These are collection specific expectations.
     * @return Instance of the Expected condition
     */
    public Expectations expecting(Expectations expectations) {
        return CollectionsSoftAssertion.softAssert(true, uiActions.getLocators(), expectations);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    private class UIElementsIterator implements Iterator<UIElement> {
        public boolean hasNext() {
            if (size < 0) {
                size = size();
            }
            return currentIndex < size && get(currentIndex) != null;
        }

        public UIElement next() {
            System.out.println("returning index : " + currentIndex);
            return dimensions.get(currentIndex++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
