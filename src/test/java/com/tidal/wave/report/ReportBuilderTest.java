package com.tidal.wave.report;

import com.tidal.flow.assertions.VerificationError;
import org.junit.Test;

import static com.tidal.flow.assertions.Assert.verify;


public class ReportBuilderTest {


    @Test
    public void frameworkVerificationTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        String typeContent = "nz.co.wc.utility.components.assertions.VerificationError";
        String errorMessage = "Verification Failed: 'Testing' does not end with 'hello'&#10;Description: Intentional assertion failure&#10;at nz.co.wc.utility.components.assertions.strings.StringAssertion.endsWith(StringAssertion.java:113)&#10;at nz.co.watercare.ui.framework.cukes.rules.InputValueRules.bingSearchInputVerification(InputValueRules.java:13)&#10;at nz.co.watercare.ui.framework.cukes.stepdefs.StepDefs.thenICanSeeBingHomepage(StepDefs.java:45)&#10;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'Verification Error", reportModel.functionalFailure())
                .isEqualTo("''Testing' does not end with 'hello'&#10;';");
    }

    @Test
    public void assertionErrorTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        String typeContent = "java.lang.AssertionError";
        String errorMessage = "&#10;Expected: is &quot;world&quot;&#10;     but: was &quot;hello&quot;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'AssertionError", reportModel.functionalFailure())
                .isEqualTo("&#10;Expected: is &quot;world&quot;&#10;     but: was &quot;hello&quot;");
    }

    @Test
    public void comparisonFailureTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        String typeContent = "jorg.junit.ComparisonFailure";
        String errorMessage = "expected:&lt;[hello]&gt; but was:&lt;[world]&gt;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'Comparison Failure", reportModel.functionalFailure())
                .isEqualTo("expected:&lt;[hello]&gt; but was:&lt;[world]&gt;");
    }

    @Test
    public void expectationFailureTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        String typeContent = "nz.co.watercare.ui.framework.exceptions.ExpectationFailure";
        String errorMessage = "Expected value 'test' is not equal to actual value Testing";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'ExpectationFailure", reportModel.functionalFailure())
                .isEqualTo("Expected value 'test' is not equal to actual value Testing");
    }

    @Test
    public void sqlServerExceptionShouldBeOtherTypeTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();
        final ReportMatcher thrownExceptionMatcher = new Reporter.ThrownException();
        final ReportMatcher otherTypeFailure  = new Reporter.OtherTypeFailures();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);
        scriptFailureMatcher.setNextMatcher(thrownExceptionMatcher);
        thrownExceptionMatcher.setNextMatcher(otherTypeFailure);

        String typeContent = "nz.co.watercare.api.base.exceptions.RuntimeTestException";
        String errorMessage = "exceptionMessagecom.microsoft.sqlserver.jdbc.SQLServerException: The TCP/IP connection to the host sqlimport.wsldev.internal, port 1433 has failed. Error: &quot;sqlimport.wsldev.internal. Verify the connection properties. Make sure that an instance of SQL Server is running on the host and accepting TCP/IP connections at the port. Make sure that TCP connections to the port are not blocked by a firewall.&quot;.";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'SQLServerException", reportModel.generalFailure())
                .isEqualTo("exceptionMessagecom.microsoft.sqlserver.jdbc.SQLServerException: The TCP/IP connection to the host sqlimport.wsldev.internal, port 1433 has failed. Er ....");
    }

    @Test
    public void snowflakeSQLLoggedExceptionShouldBeOtherTypeTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();
        final ReportMatcher thrownExceptionMatcher = new Reporter.ThrownException();
        final ReportMatcher otherTypeFailure  = new Reporter.OtherTypeFailures();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);
        scriptFailureMatcher.setNextMatcher(thrownExceptionMatcher);
        thrownExceptionMatcher.setNextMatcher(otherTypeFailure);

        String typeContent = "nz.co.watercare.api.base.exceptions.RuntimeTestException";
        String errorMessage = "Snow Flake Connection error OR Data base access error or Query Errornet.snowflake.client.jdbc.SnowflakeSQLLoggedException: JDBC driver internal error: Fail to retrieve row count for first arrow chunk: null.";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'SQLServerException", reportModel.generalFailure())
                .isEqualTo("Snow Flake Connection error OR Data base access error or Query Errornet.snowflake.client.jdbc.SnowflakeSQLLoggedException: JDBC driver internal error: ....");
    }

    @Test
    public void athenaQueryExceptionShouldBeOtherTypeTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();
        final ReportMatcher thrownExceptionMatcher = new Reporter.ThrownException();
        final ReportMatcher otherTypeFailure  = new Reporter.OtherTypeFailures();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);
        scriptFailureMatcher.setNextMatcher(thrownExceptionMatcher);
        thrownExceptionMatcher.setNextMatcher(otherTypeFailure);

        String typeContent = "nz.co.watercare.api.base.exceptions.RuntimeTestException";
        String errorMessage = "The Amazon Athena query failed to run with error message: SYNTAX_ERROR: line 1:69: '=' cannot be applied to varchar, integer";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'SQLServerException", reportModel.functionalFailure())
                .isEqualTo("The Amazon Athena query failed to run with error message: SYNTAX_ERROR: line 1:69: '=' cannot be applied to varchar, integer");
    }


    //This is a thrown exception done intentionally and there for
    // should be classified as a functional failure
    @Test
    public void runtimeTimeoutExceptionTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        String typeContent = "nz.co.watercare.ui.framework.exceptions.RuntimeTestException";
        String errorMessage = "A Runtime Test Exception";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'thrown exception types", reportModel.functionalFailure())
                .isEqualTo("A Runtime Test Exception");
    }

    @Test
    public void runtimeTimeoutActionFailureTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);

        String typeContent = "nz.co.watercare.ui.framework.exceptions.RuntimeTestException";
        String errorMessage = "Exception caused from action 'sendKeys' &#10;Details:&#10;Exception caused by org.openqa.selenium.TimeoutException: Element not found using By.name: qX in 6 seconds&#10;Build info: version: '4.1.1', revision: 'e8fcc2cecf'&#10;System info: host: 'Philips-MBP', ip: 'fe80:0:0:0:c94:42d6:46bc:3cb7%en0', os.name: 'Mac OS X', os.arch: 'aarch64', os.version: '12.2.1', java.version: '17.0.1'&#10;Driver info: driver.version: unknown&#10;at nz.co.watercare.ui.framework.cukes.stepdefs.StepDefs.thenICanSeeBingHomepageInvalidLocator(StepDefs.java:55)&#10;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'action timeout exceptions'", reportModel.scriptFailure())
                .isEqualTo("Element not found using By.name: qX in 6 seconds&#10;");
    }

    @Test
    public void runtimeTimeoutExceptionFailureTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);

        String typeContent = "nz.co.watercare.ui.framework.exceptions.RuntimeTestException";
        String errorMessage = "Exception caused from action 'click' &#10;Details:&#10;Click intercepted exception occurred with element By.xpath: //a[@title='Log']. &#10;Element click intercepted.&#10;Check for a spinner or other element overlapping the element.&#10;Try using expected conditions as a conditional wait or add more wait time if a spinner is present&#10;at nz.co.watercare.testing.ips.faults.ui.stepdefs.IPS.ServiceRequestLogSteps.iVerifyLogTypeAddedCorrectlyWithComment(ServiceRequestLogSteps.java:54)&#10;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'action timeout exceptions'", reportModel.scriptFailure())
                .isEqualTo("&#10;Click intercepted exception occurred with element By.xpath: //a[@title='Log']. &#10;Element click intercepted.&#10;Check for a spinner or other element overlapping the element.&#10;Try using expected conditions as a conditional wait or add more wait time if a spinner is present&#10;at nz.co.watercare.testing.ips.faults.ui.stepdefs.IPS.ServiceRequestLogSteps.iVerifyLogTypeAddedCorrectlyWithComment(ServiceRequestLogSteps.java:54)&#10;");
    }



    @Test (expected = VerificationError.class)
    public void runtimeTimeoutActionFailureFailingTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);

        String typeContent = "nz.co.watercare.ui.framework.exceptions.RuntimeTestException";
        String errorMessage = "Exception caused from action 'sendKeys' &#10;Details:&#10;Exception caused by org.openqa.selenium.TimeoutException: Element not found using By.name: qX in 6 seconds&#10;Build info: version: '4.1.1', revision: 'e8fcc2cecf'&#10;System info: host: 'Philips-MBP', ip: 'fe80:0:0:0:c94:42d6:46bc:3cb7%en0', os.name: 'Mac OS X', os.arch: 'aarch64', os.version: '12.2.1', java.version: '17.0.1'&#10;Driver info: driver.version: unknown&#10;at nz.co.watercare.ui.framework.cukes.stepdefs.StepDefs.thenICanSeeBingHomepageInvalidLocator(StepDefs.java:55)&#10;";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'Verification Error'", reportModel.functionalFailure())
                .isEqualTo(" Element not found using By.name: qX in 6 seconds&#10;");
    }

    @Test
    public void otherTypesTest(){
        final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
        final ReportMatcher scriptFailureMatcher = new Reporter.ScriptFailure();
        final ReportMatcher thrownExceptionMatcher = new Reporter.ThrownException();
        final ReportMatcher otherTypeFailure  = new Reporter.OtherTypeFailures();

        functionalMatcher.setNextMatcher(scriptFailureMatcher);
        scriptFailureMatcher.setNextMatcher(thrownExceptionMatcher);
        thrownExceptionMatcher.setNextMatcher(otherTypeFailure);

        String typeContent = "io.cucumber.java.PendingException";
        String errorMessage = "The scenario has pending or undefined step(s)";
        ReportModel reportModel = functionalMatcher.parse(typeContent, errorMessage);
        verify("Functional verification with 'other  types'", reportModel.generalFailure())
                .isEqualTo("The scenario has pending or undefined step(s)");
    }

//    public static void main(String[] args) {
//        ResultParser.parseRunnerResult();
//        ReportBuilder.createRunnerReport();
//    }
}
