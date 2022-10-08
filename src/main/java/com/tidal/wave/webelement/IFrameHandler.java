package com.tidal.wave.webelement;

import com.tidal.wave.loggers.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

class IFrameHandler extends LoggerUtil {

    private static final LoggerUtil logger = LoggerUtil.getLogger(IFrameHandler.class);
    private static final String ELEMENT_FOUND = "Element found using ";
    int counter = 0;

    boolean switchToIframeOfElement(By locator, WebDriver driver, boolean checkVisibility) {

        counter++;
        logger.iframeLog("Counter: " + counter);

        driver.switchTo().defaultContent();

        List<WebElement> elementToFind;
        elementToFind = driver.findElements(locator);

        logger.iframeLog("Total number of elements(visible or invisible) from outside all frames " + elementToFind.size());
        boolean elementFound;

        if (checkVisibility) {
            if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                logger.iframeLog(ELEMENT_FOUND + locator + " from outside of the frames");
                counter = 0;
                return true;
            } else {
                elementFound = false;
            }
        } else if (!elementToFind.isEmpty()) {
            logger.iframeLog(ELEMENT_FOUND + locator + " from outside of the frames : No displayed element check performed");
            counter = 0;
            return true;
        } else {
            logger.iframeLog("No element found from default content or outside of the frames");
            elementFound = false;
        }

        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        List<WebElement> innerFrames;

        logger.iframeLog("Total iframes found from default content: " + iframes.size());


        for (int i = 0; i < iframes.size(); i++) {

            logger.iframeLog(String.format("Entering iframe number : %d", i));

            try {
                if (iframes.get(i).isDisplayed()) {
                    driver.switchTo().frame(iframes.get(i));
                } else {
                    logger.iframeLog(String.format("The iframe %s is not visible", iframes.get(i).toString()));
                    continue;
                }
            } catch (WebDriverException e) {
                logger.iframeLog(e.getMessage());
            }

            elementToFind = driver.findElements(locator);

            logger.iframeLog("Total number of elements(visible or invisible) from baseIframe layer " + elementToFind.size());

            if (checkVisibility) {
                if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                    logger.iframeLog(ELEMENT_FOUND + locator + " from frame baseIframe layer");
                    counter = 0;
                    return true;
                } else {
                    elementFound = false;
                }
            } else if (!elementToFind.isEmpty()) {
                logger.iframeLog(ELEMENT_FOUND + locator + " from frame baseIframe layer");
                counter = 0;
                return true;
            } else {
                logger.iframeLog("Element not found using " + locator + " from frame baseIframe layer");
                elementFound = false;
            }

            innerFrames = driver.findElements(By.xpath("//iframe"));
            logger.iframeLog("Total iframes found from baseIframe: " + innerFrames.size());

            for (WebElement firstLayerFrame : innerFrames) {

                if (firstLayerFrame.isEnabled()) {
                    driver.switchTo().frame(firstLayerFrame);

                    logger.iframeLog("Entering firstlayer iframe");

                    elementToFind = driver.findElements(locator);

                    logger.iframeLog("Total number of elements(visible or invisible) from firstlayer frame " + elementToFind.size());

                    if (checkVisibility) {
                        if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                            logger.iframeLog(ELEMENT_FOUND + locator + " from frame first layer");
                            counter = 0;
                            return true;
                        } else {
                            elementFound = false;
                        }
                    } else if (!elementToFind.isEmpty()) {
                        logger.iframeLog(ELEMENT_FOUND + locator + " from frame first layer");
                        counter = 0;
                        return true;
                    } else {
                        logger.iframeLog("Element not found using " + locator + " from frame first layer");
                        elementFound = false;
                    }

                    innerFrames = driver.findElements(By.xpath("//iframe"));
                    logger.iframeLog("Total iframes found from second layer: " + innerFrames.size());

                    for (WebElement secondLayerFrame : innerFrames) {

                        if (secondLayerFrame.isEnabled()) {
                            driver.switchTo().frame(secondLayerFrame);
                            logger.iframeLog("Entering secondlayer iframe");


                            elementToFind = driver.findElements(locator);

                            logger.iframeLog("Total number of elements(visible or invisible) from secondlayer frame " + elementToFind.size());

                            if (checkVisibility) {
                                if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                    logger.iframeLog(ELEMENT_FOUND + locator + " from frame second layer");
                                    counter = 0;
                                    return true;
                                } else {
                                    elementFound = false;
                                }
                            } else if (!elementToFind.isEmpty()) {
                                logger.iframeLog(ELEMENT_FOUND + locator + " from frame second layer");
                                counter = 0;
                                return true;
                            } else {
                                logger.iframeLog("Element not found using " + locator + " from frame second layer");
                                elementFound = false;
                            }

                            innerFrames = driver.findElements(By.xpath("//iframe"));
                            logger.iframeLog("Total iframes found from third layer: " + innerFrames.size());

                            for (WebElement thirdLayerFrame : innerFrames) {

                                if (thirdLayerFrame.isEnabled()) {
                                    driver.switchTo().frame(thirdLayerFrame);
                                    logger.iframeLog("Entering thirdlayer iframe");

                                    elementToFind = driver.findElements(locator);

                                    logger.iframeLog("Total number of elements(visible or invisible) from thirdlayer layer " + elementToFind.size());

                                    if (checkVisibility) {
                                        if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                            logger.iframeLog(ELEMENT_FOUND + locator + " from frame third layer");
                                            counter = 0;
                                            return true;
                                        } else {
                                            elementFound = false;
                                        }
                                    } else if (!elementToFind.isEmpty()) {
                                        logger.iframeLog(ELEMENT_FOUND + locator + " from frame third layer");
                                        counter = 0;
                                        return true;
                                    } else {
                                        logger.iframeLog("Element not found using " + locator + " from frame third layer");
                                        elementFound = false;
                                    }

                                    innerFrames = driver.findElements(By.xpath("//iframe"));
                                    logger.iframeLog("Total iframes found fourth layer: " + innerFrames.size());

                                    for (WebElement fourthLayerFrame : innerFrames) {

                                        if (fourthLayerFrame.isEnabled()) {
                                            driver.switchTo().frame(fourthLayerFrame);
                                            logger.iframeLog("Entering fourthlayer iframe");

                                            elementToFind = driver.findElements(locator);

                                            logger.iframeLog("Total number of elements(visible or invisible) from fourthlayer frame " + elementToFind.size());

                                            if (checkVisibility) {
                                                if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                                    logger.iframeLog(ELEMENT_FOUND + locator + " from frame fourth layer");
                                                    counter = 0;
                                                    return true;
                                                } else {
                                                    elementFound = false;
                                                }
                                            } else if (!elementToFind.isEmpty()) {
                                                logger.iframeLog(ELEMENT_FOUND + locator + " from frame fourth layer");
                                                counter = 0;
                                                return true;
                                            } else {
                                                logger.iframeLog("Element not found using " + locator + " from frame fourth layer");
                                                elementFound = false;
                                            }

                                            innerFrames = driver.findElements(By.xpath("//iframe"));
                                            logger.iframeLog("Total iframes found from fifth layer: " + innerFrames.size());

                                            for (WebElement fifthLayerFrame : innerFrames) {

                                                if (fifthLayerFrame.isEnabled()) {
                                                    driver.switchTo().frame(fifthLayerFrame);
                                                    logger.iframeLog("Entering fifthlayer iframe");


                                                    elementToFind = driver.findElements(locator);

                                                    logger.iframeLog("Total number of elements(visible or invisible) from fifthlayer frame " + elementToFind.size());

                                                    if (checkVisibility) {
                                                        if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                                            logger.iframeLog(ELEMENT_FOUND + locator + " from frame fifth layer");
                                                            counter = 0;
                                                            return true;
                                                        } else {
                                                            elementFound = false;
                                                        }
                                                    } else if (!elementToFind.isEmpty()) {
                                                        logger.iframeLog(ELEMENT_FOUND + locator + " from frame fifth layer");
                                                        counter = 0;
                                                        return true;
                                                    } else {
                                                        logger.iframeLog("Element not found using " + locator + " from frame fifth layer");
                                                        elementFound = false;
                                                    }

                                                    innerFrames = driver.findElements(By.xpath("//iframe"));
                                                    logger.iframeLog("Total iframes found from sixth layer: " + innerFrames.size());

                                                    for (WebElement sixthLayerFrame : innerFrames) {

                                                        if (sixthLayerFrame.isEnabled()) {
                                                            driver.switchTo().frame(sixthLayerFrame);
                                                            logger.iframeLog("Entering sixthlayer iframe");


                                                            elementToFind = driver.findElements(locator);

                                                            logger.iframeLog("Total number of elements(visible or invisible) from sixthlayer frame " + elementToFind.size());

                                                            if (checkVisibility) {
                                                                if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                                                    logger.iframeLog(ELEMENT_FOUND + locator + " from frame sixth layer");
                                                                    counter = 0;
                                                                    return true;
                                                                } else {
                                                                    elementFound = false;
                                                                }
                                                            } else if (!elementToFind.isEmpty()) {
                                                                logger.iframeLog(ELEMENT_FOUND + locator + " from frame sixth layer");
                                                                counter = 0;
                                                                return true;
                                                            } else {
                                                                logger.iframeLog("Element not found using " + locator + " from frame sixth layer");
                                                                elementFound = false;
                                                            }
                                                            innerFrames = driver.findElements(By.xpath("//iframe"));
                                                            logger.iframeLog("Total iframes found from sixth layer: " + innerFrames.size());

                                                            for (WebElement seventhLayerFrame : innerFrames) {

                                                                if (seventhLayerFrame.isEnabled()) {
                                                                    driver.switchTo().frame(seventhLayerFrame);
                                                                    logger.iframeLog("Entering seventhlayer iframe");


                                                                    elementToFind = driver.findElements(locator);

                                                                    if (checkVisibility) {
                                                                        if (!elementToFind.isEmpty() && findDisplayedElement(elementToFind)) {
                                                                            logger.iframeLog(ELEMENT_FOUND + locator + " from frame seventh layer");
                                                                            counter = 0;
                                                                            return true;
                                                                        } else {
                                                                            elementFound = false;
                                                                        }
                                                                    } else if (!elementToFind.isEmpty()) {
                                                                        logger.iframeLog(ELEMENT_FOUND + locator + " from frame seventh layer");
                                                                        return true;
                                                                    } else {
                                                                        logger.iframeLog("Element not found using " + locator + " from frame seventh layer");
                                                                        elementFound = false;
                                                                    }

                                                                    //Exiting seventh layer
                                                                    driver.switchTo().parentFrame();
                                                                }
                                                            }
                                                            //Exiting sixth layer
                                                            driver.switchTo().parentFrame();
                                                        }
                                                    }
                                                    //Exiting fifth layer
                                                    driver.switchTo().parentFrame();
                                                }
                                            }

                                            //Exiting fourth layer
                                            driver.switchTo().parentFrame();
                                        }
                                    }

                                    //Exiting third layer
                                    driver.switchTo().parentFrame();
                                }
                            }

                            //Exiting second layer
                            driver.switchTo().parentFrame();
                        }
                    }
                    //Exiting first layer
                    driver.switchTo().parentFrame();
                }
            }

            logger.iframeLog(String.format("Exiting iframe number %d", i));

            //Exiting the parent layer
            driver.switchTo().parentFrame();
        }  //forloop end

        driver.switchTo().parentFrame();

        logger.iframeLog("Element not found from the frames");
        return elementFound;
    }

    private boolean findDisplayedElement(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

}
