package com.tidal.wave.report;

import com.google.common.base.Functions;
import com.tidal.wave.filehandlers.FileOutWriter;
import com.tidal.wave.filehandlers.FilePaths;
import com.tidal.wave.filehandlers.FileReader;
import com.tidal.wave.junit.ResultParser;
import com.tidal.wave.propertieshandler.Config;
import com.tidal.wave.propertieshandler.PropertiesFinder;
import com.tidal.wave.utils.Helper;
import com.tidal.wave.xml.XMLReader;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ReportBuilder {
    private static final Logger logger = Logger.getLogger(ReportBuilder.class);

    private static final String FINAL_RESULT_FILE = "TestResultFinal.xml";
    private static final String RESULT_FOLDER_NAME = "test-summary";
    private static final Path TARGET_FOLDER_PATH = Paths.get(Helper.getAbsoluteFromRelativePath(FilePaths.TARGET_FOLDER_PATH.getPath()));
    private static final Path PATH_TO_WRITE_FILE = Paths.get(TARGET_FOLDER_PATH.toString(), RESULT_FOLDER_NAME, "TestResult.csv");

    private static final List<String> testFailures = new ArrayList<>();


    private static final String REPORT_HEADERS = "" +
            "Test Case, " +
            "Result, " +
            "Functional Failure, " +
            "Script Failure, " +
            "Other, " +
            "Duration(Seconds)";

    private static final ReportMatcher functionalMatcher = new Reporter.FunctionalFailure();
    private static final ReportMatcher scriptFailure = new Reporter.ScriptFailure();
    private static final ReportMatcher thrownExceptionFailure = new Reporter.ThrownException();
    private static final ReportMatcher genericFailure = new Reporter.OtherTypeFailures();
    private static final ReportMatcher reportMatcher;


    static {
        functionalMatcher.setNextMatcher(scriptFailure);
        scriptFailure.setNextMatcher(thrownExceptionFailure);
        thrownExceptionFailure.setNextMatcher(genericFailure);
        reportMatcher = functionalMatcher;
    }

    //Prevents instantiation of class
    private ReportBuilder() {
    }


    /**
     * Method to create the report if the retry option is not enabled. <br>
     * This method needs to be run as part of the global hook (@AfterTest) of the TestNgRunner file. <br>
     * It will work only if the j-unit report is created. So make sure that - <br>
     * this method is called only after calling {@link ResultParser#parseRunnerResult()}
     */
    public static void createRunnerReport() {
        //Report generation is not needed if the retry option is on
        if (Config.RETRY_FAILED_TESTS) {
            return;
        }
        createReport();
    }

    public static void createReport() {
        StringBuilder result = new StringBuilder(REPORT_HEADERS);
        result.append(System.lineSeparator());

        String testResultFinalXML = FileReader.readFileToString(FINAL_RESULT_FILE, TARGET_FOLDER_PATH);

        List<Node> runnerResult = getTestCasesFrom(testResultFinalXML);
        for (Node node : runnerResult) {
            result.append(analyseResult(node));
        }

        result.append(addAdditionalPipelineInfo(testResultFinalXML, runnerResult));

        FileOutWriter.createTargetFolderDirectory(RESULT_FOLDER_NAME);
        FileOutWriter.writeFileTo(result.toString(), PATH_TO_WRITE_FILE.toString());
    }

    private static String addAdditionalPipelineInfo(String testResultFinalXML, List<Node> runnerResult) {
        StringBuilder result = new StringBuilder();
        IntStream.range(0, 3).forEach(e -> result.append(System.lineSeparator()));

        //Azure DevOps Predefined variables - Pipeline Name, Repository Name and Branch
        String pipelineName = PropertiesFinder.getProperty("build.definitionName");
        String projectName = PropertiesFinder.getProperty("build.repository.name");
        String branchName = PropertiesFinder.getProperty("build.sourceBranchName");

        int totalPassedTests = getTotalPassedTests(runnerResult);
        int totalFailedTests = Integer.parseInt(getTotalFailedTests(testResultFinalXML));

        float passPercentage = (float) Math.floor((float) (totalPassedTests * 100) / (totalFailedTests + totalPassedTests) * 100) / 100;

        float totalRunTime = getTotalRunTime(testResultFinalXML);
        int timeInHours = (int) ((totalRunTime) / (60 * 60));
        int timeInMinutes = (int) ((totalRunTime % (60 * 60)) / 60);
        int totalSeconds = (int) (totalRunTime % 60);

        String totalTime = String.format("%02d:%02d:%02d", timeInHours, timeInMinutes, totalSeconds);
        totalTime = totalTime + " (This is the total run time if tests run sequentially. It does not take into account the time saved by parallel runs)";


        result.append("Pipeline Name: ").append(pipelineName == null ? "" : pipelineName).append(System.lineSeparator());
        result.append("Project Name: ").append(projectName == null ? "" : projectName).append(System.lineSeparator());
        result.append("Branch Name: ").append(branchName == null ? "" : branchName).append(System.lineSeparator());
        result.append("Total tests passed: ").append(totalPassedTests).append(System.lineSeparator());
        result.append("Total tests failed: ").append(totalFailedTests).append(System.lineSeparator());
        result.append("Pass percentage: ").append(passPercentage).append(System.lineSeparator());
        result.append("Total run time: ").append(totalTime).append(System.lineSeparator());


        result.append(System.lineSeparator());

        result.append(getAMaintenanceSuggestion().isPresent() ? getAMaintenanceSuggestion().get() : "");

        return result.toString();
    }

    private static Optional<String> getAMaintenanceSuggestion() {
        String mostFailItem = testFailures.stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Functions.identity(), Collectors.counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if(null != mostFailItem) {
            long count = testFailures.stream().filter(mostFailItem::equals).count();
            return Optional.of(String.format("You can fix %d test(s) failing with issue '%s'", count, mostFailItem));
        }

        return Optional.of("Hooray! There are no test failures.");
    }

    private static String getTotalFailedTests(String testResultFinalXML) {
        return XMLReader.getValue("testsuite.@failures", testResultFinalXML);
    }

    private static float getTotalRunTime(String testResultFinalXML) {
        float runTime = 0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ROOT);
        String timeValue = XMLReader.getValue("testsuite.@time", testResultFinalXML);

        try {
            runTime = numberFormat.parse(timeValue).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return runTime;
    }

    private static int getTotalPassedTests(List<Node> runnerResul) {
        final List<Node> passedTests = runnerResul.stream()
                .filter(e -> {
                    if (e == null) return false;
                    Element element = (Element) e;
                    return element.getElementsByTagName("failure").getLength() == 0;
                })
                .collect(Collectors.toList());

        logger.info(String.format("Passed test count is %d", passedTests.size()));
        return passedTests.size();
    }

    private static List<Node> getTestCasesFrom(String testResultFinalXML) {
        return XMLReader.getNodes("testsuite.testcase", testResultFinalXML);
    }

    private static String analyseResult(Node node) {
        StringBuilder result = new StringBuilder();
        //Test case name extracted from the xml file.
        String testCase = node.getAttributes().getNamedItem("name").getTextContent().replace(",", "");
        //Test run result is evaluated to failed if the presence of a 'failure' node is detected for the test case
        boolean testPassed = ReportBuilder.hasTestPassed(node);
        //The String literal for the test case status. The boolean representation of the status 'false' or 'true' do not convey clear information.
        String status = "pass";

        ReportModel reportModel = new ReportModel();

        if (!testPassed) {
            //The type information is gathered from the 'failure' node. This node will be present only the test failed.
            Node type = node.getFirstChild().getNextSibling().getAttributes().getNamedItem("type");
            //Message attribute contains the failure message
            Node messageAttribute = node.getFirstChild().getNextSibling().getAttributes().getNamedItem("message");
            //Failure type to classify what caused the run to fail
            String typeContent = type.getTextContent();
            //Clean the verification message of new lines and commas.
            String failureMessage = messageAttribute.getTextContent().replace(",", "").replace("\n", "");
            //Parse the type content and error message to classify the failure types
            reportModel = reportMatcher.parse(typeContent, failureMessage);

            status = "fail";
        }

        if (reportModel.functionalFailure() != null) {
            testFailures.add(reportModel.functionalFailure());
        }

        if (reportModel.scriptFailure() != null) {
            testFailures.add(reportModel.scriptFailure());
        }

        if (reportModel.generalFailure() != null) {
            testFailures.add(reportModel.generalFailure());
        }


        float runtime = getRunTime(node);

        result.append(testCase).append(",")
                .append(status).append(",")
                .append(reportModel.functionalFailure() == null ? "," : reportModel.functionalFailure() + ",")
                .append(reportModel.scriptFailure() == null ? "," : reportModel.scriptFailure() + ",")
                .append(reportModel.generalFailure() == null ? "," : reportModel.generalFailure() + ",")
                .append(runtime);
        //This line separator separates each row of data
        result.append(System.lineSeparator());

        return result.toString();
    }


    private static float getRunTime(Node node) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ROOT);
        float runTime = 0;

        try {
            runTime = numberFormat.parse(node.getAttributes().getNamedItem("time").getTextContent()).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return runTime;
    }

    private static boolean hasTestPassed(Node node) {
        Element nodeElement = (Element) node;
        return nodeElement.getElementsByTagName("failure").getLength() == 0;
    }
}
