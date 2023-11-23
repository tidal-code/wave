package dev.tidalcode.wave.testsuites;

import dev.tidalcode.wave.commands.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        // Commands Test
        CheckTest.class,
        ClearAndTypeTest.class,
        ClearTest.class,
        ClickByJSTest.class,
        ClickTest.class,
        DoubleClickTest.class,
        FindAllTextDataTest.class,
        IsVisibleTest.class,
        SelectTests.class,
        SendKeysTest.class,
        SetInnerHtmlTest.class,
        SetTextTest.class,
        UnCheckTest.class,
})
public class CommandsTestSuite {
}
