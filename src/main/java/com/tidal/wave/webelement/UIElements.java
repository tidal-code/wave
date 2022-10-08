package com.tidal.wave.webelement;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindAllTextData;
import com.tidal.wave.commands.GetSize;
import com.tidal.wave.commands.IsPresent;
import com.tidal.wave.data.ElementData;
import com.tidal.wave.data.GlobalData;
import com.tidal.wave.verification.conditions.collections.CollectionsCondition;
import com.tidal.wave.verification.expectations.CollectionsSoftAssertion;
import com.tidal.wave.verification.expectations.collections.Expectations;
import com.tidal.wave.wait.ThreadSleep;
import com.tidal.wave.wait.Wait;
import org.openqa.selenium.By;

import java.util.AbstractList;
import java.util.List;

import static com.tidal.wave.verification.conditions.TestVerification.verification;


public class UIElements extends AbstractList<UIElement> {

    private UIActions uiActions;
    private boolean visibility;

    protected UIElements setProperties(By byLocator) {
        visibility = false;
        uiActions = new UIActions();
        uiActions.setProperties(byLocator);
        uiActions.setMultiple();
        return this;
    }

    //For thenFindAll in UIElement
    protected void setElementProperties(UIActions uiActions, boolean visibility) {
        this.uiActions = uiActions;
        this.visibility = visibility;
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
        GlobalData.addData(ElementData.INDEX, String.valueOf(index));
        return uiActions;
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
    public int size() {
        return new Executor().usingLocator(uiActions.getLocators()).isVisible(visibility).invokeCommand(GetSize.class);
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
        verification(visibility, true, uiActions.getLocators(), condition);
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

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (UIElement e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }
}
