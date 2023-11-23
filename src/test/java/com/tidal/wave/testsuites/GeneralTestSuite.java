package com.tidal.wave.testsuites;

import com.tidal.wave.commands.IsVisibleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IsVisibleTest.class,
//        CSVReaderCollectionTest.class,
//        CSVReadTests.class,
})
public class GeneralTestSuite {
}
