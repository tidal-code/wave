package dev.tidalcode.wave.webelement;

import com.tidal.utils.data.DataEnum;
import dev.tidalcode.wave.actions.Interactions;
import dev.tidalcode.wave.data.IntervalTime;
import dev.tidalcode.wave.data.MaxTime;
import dev.tidalcode.wave.data.tabular.Table;
import dev.tidalcode.wave.retry.RetryCondition;
import dev.tidalcode.wave.verification.conditions.Condition;
import dev.tidalcode.wave.verification.criteria.Criteria;
import dev.tidalcode.wave.verification.expectations.Expectation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import java.util.Map;
import java.util.function.Supplier;

public interface UIElement {

    UIElement invisibleElement();

    UIActions debugMode();

    UIElement click();

    UIElement sendKeys(CharSequence... keysToSend);

    UIElement setValue(String value);

    UIElement setInnerHtml(String value);

    UIElement clearAndType(CharSequence... charSequences);

    UIElement clear();

    String getAttribute(String value);

    String getCSSAttribute(String value);

    String getAllCSSAttributes();

    Map<String, String> getAllAttributes();

    String getTagName();

    Point getLocation();

    Rectangle getRect();

    Dimension getDimension();

    boolean isDisplayed();

    boolean isEnabled();

    boolean isSelected();

    String getText();

    UIElement setText(String text);

    UIElement check();

    UIActions inShadowDom(String locator);

    UIActions inShadowDom();

    UIElement unCheck();

    String select(String selectValue);

    String selectByValue(String selectValue);

    String select(int index);

    UIElement moveToElement();

    UIElement doubleClick();

    UIElement rightClick();

    UIElement contextClick();

    UIElement actionClick();

    UIElement forceClick();

    UIElement pressTab();

    UIElement pressTab(int times);

    UIElement pressEnter();

    UIElement clickByJS();

    UIElement hover(int secondsToHover);

    UIElement scrollToView();

    UIElement scrollPage(int xDirection, int yDirection);

    UIElement moveByOffset(int xDirection, int yDirection);

    UIElement dragAndDrop();

    UIElement doPageRefresh(MaxTime maxTime, IntervalTime intervalTime);

    UIElement dragAndDropByOffset(int xDirection, int yDirection);

    UIElement inFrame(String locatorMatcher);

    UIElement thenFind(String locator);

    UIElements thenFindAll(String locator);

    UIElement waitFor(int waitTime);

    void retryIf(Supplier<RetryCondition> retryCondition);

    void retryIf(RetryCondition retryCondition);

    void retryIf(Supplier<RetryCondition> retryCondition, int numberOfTimes);

    void retryIf(RetryCondition retryCondition, int numberOfTimes);

    Expectation expecting(Expectation expectation);

    Expectation expecting(Supplier<Expectation> expectation);

    void shouldHave(Condition... condition);

    UIElement shouldBe(Criteria... criteria);

    UIElement shouldBe(Supplier<Criteria> criteria);

    UIElement shouldBe(Supplier<Criteria> criteria1, Supplier<Criteria> criteria2);

    UIElement addData(String key);

    UIElement addData(DataEnum key);

    void addData(String key, String value);

    UIElement uploadFile(String fileName);

    UIElement uploadFileWRC(String fileName);

    UIElement uploadFileByDragAndDrop(String fileName);

    UIElement pause(int seconds);

    @Deprecated(since = "Not ready for use")
    Table tableData();

    void performActions(Interactions interactions);
}
