package com.tidal.wave.webelement;

import com.tidal.wave.data.DataEnum;
import com.tidal.wave.retry.RetryCondition;
import com.tidal.wave.verification.conditions.Condition;
import com.tidal.wave.verification.criteria.Criteria;
import com.tidal.wave.verification.expectations.Expectation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import java.util.Map;
import java.util.function.Supplier;

public interface UIElement {

    UIElement invisibleElement();

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

    UIElement unCheck();

    String select(String selectValue);

    String selectByValue(String selectValue);

    String select(int index);

    UIElement moveToElement();

    void doubleClick();

    void rightClick();

    void contextClick();

    void actionClick();

    void forceClick();

    UIElement pressTab();

    UIElement pressTab(int times);

    void pressEnter();

    void clickByJS();

    UIElement hover(int secondsToHover);

    UIElement scrollToView();

    UIElement scrollPage(int xDirection, int yDirection);

    UIElement moveByOffset(int xDirection, int yDirection);

    void dragAndDrop();

    void dragAndDropByOffset(int xDirection, int yDirection);

    UIElement thenFind(String locatorMatcher);

    UIElements thenFindAll(String byNewLocatorMatcher);

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

    void uploadFile(String fileName);

    void uploadFileWRC(String fileName);

    UIElement pause(int seconds);


}
