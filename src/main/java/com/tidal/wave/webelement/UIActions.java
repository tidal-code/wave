package com.tidal.wave.webelement;


import com.tidal.utils.data.DataEnum;
import com.tidal.utils.data.GlobalData;
import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.*;
import com.tidal.wave.data.CommandStore;
import com.tidal.wave.data.IntervalTime;
import com.tidal.wave.data.MaxTime;
import com.tidal.wave.retry.Retry;
import com.tidal.wave.retry.RetryCondition;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.verification.conditions.Condition;
import com.tidal.wave.verification.conditions.TestVerification;
import com.tidal.wave.verification.criteria.Criteria;
import com.tidal.wave.verification.criteria.EnsureElementState;
import com.tidal.wave.verification.expectations.Expectation;
import com.tidal.wave.verification.expectations.SoftAssertion;
import com.tidal.wave.wait.ThreadSleep;
import com.tidal.wave.wait.Wait;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;


@SuppressWarnings("unchecked")
public class UIActions implements UIElement {
    private final Executor executor;
    private final List<String> locators = new LinkedList<>();
    private boolean visibility = true;
    private boolean isMultiple = false;
    private boolean debugMode = false;

    public UIActions(){
        ObjectSupplier.flushInstance(this.getClass().getSimpleName());
        CommandStore.clearCommands();
        executor = new Executor();
        ObjectSupplier.addInstance(executor);
    }

    protected UIActions setProperties(String byLocator) {
        locators.add(byLocator);
        return this;
    }


    public void setMultiple() {
        isMultiple = true;
        executor.withMultipleElements(true);
    }

    public void setElementIndex(int index) {
        executor.withElementIndex(index);
    }

    protected UIActions withDefaultWait() {
        Wait.setDefaultWait();
        return this;
    }

    /**
     * The find method searches for a visible element by default. This method can be used to turn that setting off.
     *
     * @return A self reference
     */
    @Override
    public UIActions invisibleElement() {
        visibility = false;
        executor.isVisible(false);
        return this;
    }

    @Override
    public UIActions debugMode() {
        this.debugMode = true;
        return this;
    }

    /**
     * The click method involves a normal selenium click. It handles stale element exception and element intercepted
     * exceptions.
     *
     * @return A self reference
     */
    @Override
    public UIActions click() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(Click.class);
        return this;
    }

    /**
     * The sendKeys method is a wrapper on top of Selenium sendKeys which can handle stale element exception and element not interactable
     * exceptions.
     *
     * @param keysToSend as a char sequence
     * @return A self reference
     */
    @Override
    public UIActions sendKeys(CharSequence... keysToSend) {
        Objects.requireNonNull(keysToSend, "The input value for 'SendKeys' should not be null");
        executor.debugMode(debugMode).usingLocator(locators).withCharSequence(keysToSend).invokeCommand(SendKeys.class);
        return this;
    }

    /**
     * Method to set text value to an input. This is an alternative to sendKeys method. It is slower than sendKeys method. <br/>
     * <b>WARNING!</b> Do not use this method to write password or to input Keyboard Keys. The setText method will not be able to read back the entered value.
     * For the first few seconds it will try to input the value to the element and if it fails do so, then it will attempt to
     * clear the text by sending backspace keys and try again until the timeout is reached or value is entered correctly; whichever happens first
     *
     * @param text to input to the element
     * @return A self reference
     */
    @Override
    public UIActions setText(String text) {
        Objects.requireNonNull(text, "The input value for 'SetText' should not be null");
        executor.debugMode(debugMode).usingLocator(locators).withText(text).invokeCommand(SetText.class);
        return this;
    }

    /**
     * The setValue method sets the input value using Javascript. The method will handle stale element exception and element not interactable
     * exceptions.
     *
     * @param value to be sent to input as a string
     * @return A self reference
     */
    @Override
    public UIActions setValue(String value) {
        executor.debugMode(debugMode).usingLocator(locators).withText(value).invokeCommand(SetValue.class);
        return this;
    }

    /**
     * The setInnerHtml method sets the inner Html using Javascript. The method will handle stale element exception and element not interactable
     * exceptions.
     *
     * @param value to be sent to input as a string
     * @return A self reference
     */
    @Override
    public UIActions setInnerHtml(String value) {
        executor.debugMode(debugMode).usingLocator(locators).withText(value).invokeCommand(SetInnerHtml.class);
        return this;
    }

    /**
     * The clearAndType method would simulate manual clear and input using backspace keys and sending characters one at a time
     *
     * @param charSequences to be sent to the input
     * @return A self reference
     */
    @Override
    public UIActions clearAndType(CharSequence... charSequences) {
        executor.debugMode(debugMode).usingLocator(locators).withCharSequence(charSequences).invokeCommand(ClearAndType.class);
        return this;
    }

    /**
     * The clear method is wrapper on top of Selenium clear method. There are no exception handling involves with this method
     *
     * @return A self reference
     */
    @Override
    public UIActions clear() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(Clear.class);
        return this;
    }

    /**
     * Method to get the attribute value of an element.
     *
     * @param value the key for the value to be returned
     * @return A self reference
     */
    @Override
    public String getAttribute(String value) {
        return executor.debugMode(debugMode).usingLocator(locators).withAttribute(value).invokeCommand(GetAttribute.class);
    }

    /**
     * Method to get the css attribute of an element.
     *
     * @param value the key for the css value to be returned
     * @return A self reference
     */
    @Override
    public String getCSSAttribute(String value) {
        return executor.debugMode(debugMode).usingLocator(locators).withAttribute(value).invokeCommand(GetCssAttribute.class);
    }

    /**
     * Method to return all the css attributes of an element.
     *
     * @return all the css values of an element as a single string
     */
    @Override
    public String getAllCSSAttributes() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetAllCssAttributes.class);
    }

    /**
     * Method to return all the inline element attributes as a map
     *
     * @return a map of all the inline attributes of an element
     */
    @Override
    public Map<String, String> getAllAttributes() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetAllAttributes.class);
    }

    /**
     * Method to return the tag name of the element
     *
     * @return the tag name of the element
     */
    @Override
    public String getTagName() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetTagName.class);
    }

    /**
     * Method to return where on the page is the top left-hand corner of the rendered element?
     *
     * @return A point, containing the location of the top left-hand corner of the element
     */
    @Override
    public Point getLocation() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetLocation.class);
    }

    /**
     * Method to return the location and size of the rendered element
     *
     * @return The location and size of the rendered element
     */
    @Override
    public Rectangle getRect() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetRect.class);
    }

    /**
     * Method to return the width and height of the element
     *
     * @return The size of the element on the page.
     */
    @Override
    public Dimension getDimension() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(GetDimension.class);
    }


    /**
     * Method to check if the element is displayed
     * Visibility option cannot be passed to this method as it is expected to find the element without visible option
     *
     * @return true or false depending on whether the element is displayed or not
     */
    @Override
    public boolean isDisplayed() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(IsVisible.class);
    }

    /**
     * Method to check if the element is enabled
     * Visibility option cannot be passed to this method as it is expected to find the element without visible option
     *
     * @return true or false depending on whether the element is enabled or not
     */
    @Override
    public boolean isEnabled() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(IsEnabled.class);
    }

    /**
     * Method to check if the element is selected
     *
     * @return true or false depending on whether the element is selected or not
     */
    @Override
    public boolean isSelected() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(IsSelected.class);
    }

    /**
     * Method to get the text value of an element.
     * It will try to get the element node text first and if it fails then value, inner html and then inner html value by javascript in order.
     *
     * @return the text value of the element or an empty string if no value existing
     */
    @Override
    public String getText() {
        return executor.debugMode(debugMode).usingLocator(locators).invokeCommand(FindTextData.class);
    }

    /**
     * Method to check the checkbox. The check method will select the checkbox only if it is not already checked.
     * It will do nothing if it is already checked.
     *
     * @return A self reference
     */
    @Override
    public UIActions check() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(Check.class);
        return this;
    }

    @Override
    public UIActions inShadowDom(String locator) {
        return null;
    }

    @Override
    public UIActions inShadowDom() {
        addData("SE", "true");
        executor.debugMode(debugMode).presenceOfShadowDom();
        return this;
    }

    /**
     * Method to uncheck the checkbox. The check method will deselect the checkbox only if it is already checked.
     * It will do nothing if it is already deselected.
     *
     * @return A self reference
     */
    @Override
    public UIActions unCheck() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(UnCheck.class);
        return this;
    }

    /**
     * Selects the dropdown value using the text value
     *
     * @param selectValue text value to be selected
     * @return the value selected
     */
    @Override
    public String select(String selectValue) {
        return executor.debugMode(debugMode).usingLocator(locators).withText(selectValue).invokeCommand(SelectByText.class);
    }

    /**
     * Selects the dropdown value using the text value
     *
     * @param selectValue text value to be selected
     * @return the value selected
     */
    @Override
    public String selectByValue(String selectValue) {
        return executor.debugMode(debugMode).usingLocator(locators).withText(selectValue).invokeCommand(SelectByValue.class);
    }

    /**
     * Selects the dropdown value using the text value
     *
     * @param index index of the value to be selected
     * @return the value selected
     */
    @Override
    public String select(int index) {
        return executor.debugMode(debugMode).usingLocator(locators).withSelectIndex(index).invokeCommand(SelectByIndex.class);
    }

    /**
     * Moves  to the element found. Can be used to scroll to the corresponding element
     *
     * @return A self reference
     */
    @Override
    public UIActions moveToElement() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(MoveToElement.class);
        return this;
    }

    /**
     * Simulates a double click by the user. Since double click normally takes the user to a different screen, the method is final
     */
    @Override
    public void doubleClick() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(DoubleClick.class);
    }

    /**
     * Simulates a right click or context click by the user. Since right click normally takes the user to a different screen the method is final
     */
    @Override
    public void rightClick() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(RightClick.class);
    }

    /**
     * Simulates a right click or context click by the user. Since right click normally takes the user to a different screen the method is final
     */
    @Override
    public void contextClick() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(RightClick.class);
    }

    /**
     * Similar to click by selenium actions. This action is final.
     */
    @Override
    public void actionClick() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(ClickByAction.class);
    }

    /**
     * Simulates a force click on an element by clicking and holding for a short time.
     */
    @Override
    public void forceClick() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(ForceClick.class);
    }

    /**
     * Similar to the keystroke of pressing the tab key.
     *
     * @return A self reference
     */
    @Override
    public UIActions pressTab() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(PressTab.class);
        return this;
    }

    /**
     * Similar to the keystroke of pressing the tab multiple times.
     *
     * @param times the desired times the tab key needs to be pressed
     * @return A self reference
     * @deprecated This is being deprecated as we cannot rely on this action
     */
    @Override
    @Deprecated
    public UIActions pressTab(int times) {
        executor.debugMode(debugMode).usingLocator(locators).withTabIndex(times).invokeCommand(PressTab.class);
        return this;
    }

    /**
     * Similar to pressing enter key. The method is final
     */
    @Override
    public void pressEnter() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(PressEnter.class);
    }

    /**
     * Clicks on the element using Javascript. This is not a reliable method to click but can be useful when other clicks fails.
     */
    @Override
    public void clickByJS() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(ClickByJS.class);
    }

    /**
     * Hovers on an element for a definite amount of time
     *
     * @param secondsToHover time to hover on the element
     * @return A self reference
     */
    @Override
    public UIActions hover(int secondsToHover) {
        executor.debugMode(debugMode).usingLocator(locators).withTimeToWait(secondsToHover).invokeCommand(Hover.class);
        return this;
    }

    /**
     * Scrolls the element to the view port so the element actions can be performed
     * The visibility option is always set to false
     *
     * @return A self reference
     */
    @Override
    public UIActions scrollToView() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(ScrollToView.class);
        return this;
    }

    /**
     * Scrolls the page for x or y directions in pixels. The x, y co-ordinates starts from the top-left corner of the page
     *
     * @param xDirection pixels
     * @param yDirection pixels
     * @return A self reference
     */
    @Override
    public UIActions scrollPage(int xDirection, int yDirection) {
        executor.debugMode(debugMode).usingLocator(locators).withXYCords(xDirection, yDirection).invokeCommand(ScrollPage.class);
        return this;
    }

    /**
     * Moves the mouse to the specified offset of the last known mouse coordinates.
     *
     * @param xDirection The horizontal offset to which to move the mouse.
     * @param yDirection The vertical offset to which to move the mouse.
     * @return A self reference
     */
    @Override
    public UIActions moveByOffset(int xDirection, int yDirection) {
        executor.debugMode(debugMode).usingLocator(locators).withXYCords(xDirection, yDirection).invokeCommand(MoveByOffSet.class);
        return this;
    }

    /**
     * Drag and drop the source element to the target element.
     * Source element is found using find(locator) method and target element is found by the following thenFind(locator) method.
     * Example: find(locator).thenFind(locator2).dragAndDrop();
     */
    @Override
    public void dragAndDrop() {
        executor.debugMode(debugMode).usingLocator(locators).invokeCommand(DragAndDrop.class);
    }

    /**
     * Method to keep refreshing the page until the element is found.
     *
     * @param maxTime      The maximum time to keep the page refreshing until the element is found
     * @param intervalTime The interval time to refresh the page
     * @return A self reference
     */
    @Override
    public UIElement doPageRefresh(MaxTime maxTime, IntervalTime intervalTime) {
        executor.pageRefreshData(maxTime, intervalTime).usingLocator(locators).invokeCommand(PageRefresh.class);
        return this;
    }


    /**
     * Drag and drop the source element to the target location.
     * Source element is found using find(locator) method and target location is found through the coordinates.
     *
     * @param xDirection The horizontal offset to which the source element to be movied.
     * @param yDirection The vertical offset to which the source element to be movied
     */
    @Override
    public void dragAndDropByOffset(int xDirection, int yDirection) {
        executor.debugMode(debugMode).usingLocator(locators).withXYCords(xDirection, yDirection).invokeCommand(DragAndDrop.class);
    }


    /**
     * Method to chain locators to get relative elements.
     *
     * @param locator the second/third/fourth locators relative to the first one
     * @return ElementChain instance
     */
    @Override
    public UIActions thenFind(String locator) {
        locators.add(locator);
        return this;
    }

    /**
     * Method to chain locators to get relative elements.
     *
     * @param newLocator the locator relative to the first locator.
     * @return list of WebElements
     */
    @Override
    public UIElements thenFindAll(String newLocator) {
        locators.add(newLocator);
        UIElements uiElements = new UIElements();
        uiElements.setElementProperties(this);
        return uiElements;
    }

    /**
     * The waitFor method would explicitly wait for the specified time for the first action to complete.
     * It will be active till the chain of actions completes with the find() method. The wait time will be defaulted to the
     * value set by setOption(int waitTime) method after that. There for there is no need to reset the wait.
     *
     * @param waitTime in seconds till the element is found or action is completed
     * @return A self reference
     */
    @Override
    public UIActions waitFor(int waitTime) {
        Wait.setExplicitWait(waitTime);
        return this;
    }


    /**
     * Method to retry the actions if a RetryCondition is not satisfied.
     * The below method would retry only once.
     *
     * @param retryCondition The retry condition to met
     */
    @Override
    public void retryIf(Supplier<RetryCondition> retryCondition) {
        Retry.retry(visibility, isMultiple, locators, retryCondition.get(), 1);
    }

    /**
     * Method to retry the actions if a RetryCondition is not satisfied.
     * The below method would retry only once.
     *
     * @param retryCondition The retry condition to met
     */
    @Override
    public void retryIf(RetryCondition retryCondition) {
        Retry.retry(visibility, isMultiple, locators, retryCondition, 1);
    }

    /**
     * Method to retry the actions if a RetryCondition is not satisfied.
     * We can specify the number of retry attempts before the timeout is reached.
     * The timeout will depend on the explicit timeout set for the tests.
     *
     * @param retryCondition The retry condition to met
     */
    @Override
    public void retryIf(Supplier<RetryCondition> retryCondition, int numberOfTimes) {
        Retry.retry(visibility, isMultiple, locators, retryCondition.get(), numberOfTimes);
    }

    /**
     * Method to retry the actions if a RetryCondition is not satisfied.
     * We can specify the number of retry attempts before the timeout is reached.
     * The timeout will depend on the explicit timeout set for the tests.
     *
     * @param retryCondition The retry condition to met
     */
    @Override
    public void retryIf(RetryCondition retryCondition, int numberOfTimes) {
        Retry.retry(visibility, isMultiple, locators, retryCondition, numberOfTimes);
    }

    /**
     * Expectations are soft assertions. They will wait for the condition to satisfy but won't fail unless specifically called by orElseFail() method
     * They are similar to Selenium expected conditions.
     *
     * @param expectation the expectation to be fulfilled
     * @return Expectation instance
     */
    @Override
    public Expectation expecting(Expectation expectation) {
        return SoftAssertion.softAssert(visibility, isMultiple, locators, expectation);
    }

    /**
     * Expectations are soft assertions. They will wait for the condition to satisfy but won't fail unless specifically called by orElseFail() method
     * They are similar to Selenium expected conditions.
     *
     * @param expectation the expectation to be fulfilled
     * @return Expectation instance
     */
    @Override
    public Expectation expecting(Supplier<Expectation> expectation) {
        return SoftAssertion.softAssert(visibility, isMultiple, locators, expectation.get());
    }

    /**
     * shouldHave methods can be used for both verification purposes and as conditional waits.
     * They cause the test to fail if the condition is not satisfied.
     *
     * @param condition to be checked to pass the test
     */
    @Override
    public void shouldHave(Condition... condition) {
        TestVerification.verification(visibility, isMultiple, locators, condition);
    }


    /**
     * shouldBe methods can be used for both verification purposes and as conditional waits.
     * They are a naming variation of shouldHave methods to reflect the syntactical correctness of the verification
     *
     * @param criteria to be checked
     * @return A self reference
     */
    @Override
    public final UIActions shouldBe(Criteria... criteria) {
        EnsureElementState.affirmation(visibility, isMultiple, locators, criteria);
        return this;
    }

    /**
     * shouldBe methods can be used for both verification purposes and as conditional waits.
     * They are a naming variation of shouldHave methods to reflect the syntactical correctness of the verification
     *
     * @param criteria to be checked
     * @return A self reference
     */
    @Override
    public final UIActions shouldBe(Supplier<Criteria> criteria) {
        Supplier<Criteria>[] criterion = new Supplier[]{criteria};
        EnsureElementState.affirmation(visibility, isMultiple, locators, criterion);
        return this;
    }

    @Override
    public final UIActions shouldBe(Supplier<Criteria> criteria1, Supplier<Criteria> criteria2) {
        Supplier<Criteria>[] criterion = new Supplier[]{criteria1, criteria2};
        EnsureElementState.affirmation(visibility, isMultiple, locators, criterion);
        return this;
    }

    /**
     * Method to capture the text value of an element and store it with a key to a global data map.<br>
     * Example: <br><code>
     * 1.find("//someXPath").addData("aStringInputKey"); <br>
     * 2.getData("aStringInputKey"); //retrieval
     * </code>
     *
     * @param key to store the value
     * @return A self reference
     */
    @Override
    public UIActions addData(String key) {
        GlobalData.addData(key, executor.debugMode(debugMode).usingLocator(locators).invokeCommand(FindTextData.class, "findTextData"));
        return this;
    }

    /**
     * Method to capture the text value of an element and store it with a key to a global data map.<br>
     * The key is an enum value which would itself be an extension of the {@link DataEnum} interface.<br>
     *
     * @param key to store the value where the key is a Data Enum value
     * @return A self reference
     */
    @Override
    public UIActions addData(DataEnum key) {
        GlobalData.addData(key, executor.debugMode(debugMode).usingLocator(locators).invokeCommand(FindTextData.class, "findTextData"));
        return this;
    }

    /**
     * Method to store the value with a key.
     * This is manual storage, and it will not automatically pick up any element data.
     *
     * @param key   to assign the value to
     * @param value to be stored
     */
    @Override
    public void addData(String key, String value) {
        GlobalData.addData(key, value);
    }

    /**
     * Method to upload a file to the application.
     * An absolute, relative path or file name can be provided.
     * If only a file name is provided, the file should exist in the 'resources' folder of the project
     *
     * @param fileName of the file to be found
     */
    public void uploadFile(@NotNull String fileName) {
        executor.debugMode(debugMode).usingLocator(locators).withText(fileName).invokeCommand(FileUpload.class);
    }


    /**
     * Method to upload a file to the application using Java Robot Class.
     * This method will not work with headless mode
     * If only a file name is provided, the file should exist in the 'resources' folder of the project
     *
     * @param fileName of the file to be found - An absolute, relative path or file name can be provided.
     */
    public void uploadFileWRC(@NotNull String fileName) {
        executor.debugMode(debugMode).usingLocator(locators).withText(fileName).invokeCommand(FileUploadWRC.class);
    }

    /**
     * Method to upload a file to the application via drag and drop
     * This method is to be used in pages where files are uploaded when file is dropped into the drag and drop control
     * component and there is no upload button
     * <p>
     * If only a file name is provided, the file should exist in the 'resources' folder of the project
     *
     * @param fileName of the file to be found - An absolute, relative path or file name can be provided.
     */
    public void uploadFileByDragAndDrop(@NotNull String fileName) {
        executor.debugMode(debugMode).usingLocator(locators).withText(fileName).invokeCommand(FileUploadByDragAndDrop.class);
    }


    /**
     * <p>Just the plain old Thread.sleep wrapped in a method with a nicer name. </p>
     * <p>Use it when absolutely necessary as this is a static wait and would slow down the tests.</p>
     * <b>Examples:</b> <br/>
     * <code>
     * find("//xPath").scrollToView().pause(2).click().scrollToView().pause(2).click(); <br>
     * find("//xpath").moveToElement().pause(2).click();
     * </code>
     *
     * @param seconds The time in seconds
     * @return A self reference
     */
    @Override
    public UIActions pause(int seconds) {
        ThreadSleep.forSeconds(seconds);
        return this;
    }

    protected List<String> getLocators() {
        return locators;
    }
}
