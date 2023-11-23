package dev.tidalcode.wave.command;

import com.tidal.flow.assertions.Soft;
import com.tidal.flow.assertions.stackbuilder.ErrorStack;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;


public class CommandContextTest {

    @Test
    public void commandContextSetterTest() {
        List<String> locators = new LinkedList<>();
        locators.add("locator1");
        locators.add("locator2");

        Executor executor = new Executor();

        executor
                .usingLocator(locators)
                .debugMode(true)
                .withXYCords(100, 200)
                .withText("with text")
                .withMultipleElements(true)
                .isVisible(false)
                .withTimeToWait(2)
                .withAttribute("with attribute")
                .withCharSequence("c h a r s e q u e n c e")
                .presenceOfShadowDom()
                .withElementIndex(1)
                .withSelectIndex(2)
                .withZoomLevel(5)
                .withTabIndex(3)
                .withTimeToWait(10);

        CommandContext context = executor.getContext();

        Soft.verify("Command Context X Coordinate", context.getXYCords()[0]).isEqualTo(100);
        Soft.verify("Command Context Y Coordinate", context.getXYCords()[1]).isEqualTo(200);
        Soft.verify("Command Context WITH TEXT", context.getTextInput()).isEqualTo("with text");
        Soft.verify("Command Context multiple elements true", context.isMultiple()).isEqualTo(true);
        Soft.verify("Command Context visibility condition false", context.getVisibility()).isEqualTo(false);
        Soft.verify("Command Context locator 1", context.getLocators()).contains("locator1");
        Soft.verify("Command Context locator 2", context.getLocators()).contains("locator2");
        Soft.verify("Command Context attribute name", context.getAttributeName()).isEqualTo("with attribute");
        Soft.verify("Command Context debug mode true", context.getDebugMode()).isEqualTo(true);
        Soft.verify("Command Context element index", context.getElementIndex()).isEqualTo(1);
        Soft.verify("Command Context tab index", context.getTabIndex()).isEqualTo(3);
        Soft.verify("Command Context hover wait time", context.getHoverWaitTime()).isEqualTo(10);
        Soft.verify("Command Context char sequence", context.getSequence()).contains("c h a r s e q u e n c e");
        Soft.verify("Command Context shadow dom presence", context.getShadowDomPresence()).isEqualTo(true);

        new ErrorStack().execute();

    }
}
