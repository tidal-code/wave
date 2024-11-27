package dev.tidalcode.wave.testsuites;


import dev.tidalcode.wave.browser.OpenTest;
import dev.tidalcode.wave.commands.*;
import dev.tidalcode.wave.config.ConfigPropertiesTest;
import dev.tidalcode.wave.config.PropertiesFinderTest;
import dev.tidalcode.wave.elementfinder.ThenFindAndThenFindAllTests;
import dev.tidalcode.wave.expectations.*;
import dev.tidalcode.wave.retry.StillNotPresentRetryTests;
import dev.tidalcode.wave.retry.StillNotVisibleRetryTests;
import dev.tidalcode.wave.retry.StillPresentRetryTests;
import dev.tidalcode.wave.retry.StillVisibleRetryTests;
import dev.tidalcode.wave.waiter.*;
import dev.tidalcode.wave.webelement.ElementFinderTest;
import dev.tidalcode.wave.webelement.IframesTest;
import dev.tidalcode.wave.elementfinder.FindAllIterationTest;
import dev.tidalcode.wave.elementfinder.TheFindAllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({

        OpenTest.class,

        // Commands Test
        CheckTest.class,
        ClearAndTypeTest.class,
        ClearTest.class,
        ClickByJSTest.class,
        ClickTest.class,
        DoubleClickTest.class,
        FindAllTextDataTest.class,
        IsVisibleTest.class,
        SelectParallelTest.class,
        SelectTests.class,
        SendKeysTest.class,
        SetInnerHtmlTest.class,
        SetTextTest.class,
        UnCheckTest.class,
        ConfigPropertiesTest.class,
        PropertiesFinderTest.class,
        FindAllIterationTest.class,
        TheFindAllTest.class,
        ThenFindAndThenFindAllTests.class,
        ExpectationCollectionTest.class,
        ExpectationCustomMessageTest.class,
        ExpectationTest.class,
        ExpectationTestForStaticTest.class,
        ExpectationTestParallelOneTest.class,
        ExpectationTestParallelTwoTest.class,
        FindAllWaitTest.class,
        FluentWaitTest.class,
        GlobalWaitTest.class,
        TimeoutTest.class,
        WaitTest.class,
        IframesTest.class,
        ElementFinderTest.class,

        //Retry Tests
        StillPresentRetryTests.class,
        StillNotVisibleRetryTests.class,
        StillNotPresentRetryTests.class,
        StillVisibleRetryTests.class

})
public class FunctionTestSuite {
}
