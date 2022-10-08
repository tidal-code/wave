package com.tidal.wave.report;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reporter {

    private static final String[] notFuncAssertion = {
            "TimeoutException",
            "Exception caused from action",
            "SQLServerException",
            "SnowflakeSQLLoggedException",
            "AlreadyClosedSqlException"
    };

    private static final String[] funcAssertion = {
            "AssertionError",
            "ComparisonFailure",
            "ExpectationFailure"
    };

    private static final String[] scriptIssues = {
            "NullPointerException"
    };

    private static final String[] notThrownException = {
            "SQLServerException",
            "SnowflakeSQLLoggedException",
            "AlreadyClosedSqlException"
    };

    public static class FunctionalFailure extends ReportMatcher {
        public FunctionalFailure() {
            super();
        }

        @Override
        public ReportModel parse(String typeContent, String errorMessage) {

            if (typeContent.contains("VerificationError")) {
                ReportModel reportModel = new ReportModel();
                reportModel.setFunctionalFailure(extractVerifications(errorMessage));
                return reportModel;
            } else if (Arrays.stream(funcAssertion).anyMatch(typeContent::contains)) {
                ReportModel reportModel = new ReportModel();
                reportModel.setFunctionalFailure(errorMessage.replace("\n", "'"));
                return reportModel;
            } else if (typeContent.contains("RuntimeTestException") && Arrays.stream(notFuncAssertion).noneMatch(errorMessage::contains)) {
                ReportModel reportModel = new ReportModel();
                reportModel.setFunctionalFailure(errorMessage);
                return reportModel;
            }
            return this.nextReportTypeMatcher.parse(typeContent, errorMessage);
        }
    }

    public static class ScriptFailure extends ReportMatcher {
        public ScriptFailure() {
            super();
        }

        @Override
        public ReportModel parse(String typeContent, String errorMessage) {
            if (typeContent.contains("RuntimeTestException") && errorMessage.contains("TimeoutException")) { //Element search timeout exception
                ReportModel reportModel = new ReportModel();
                reportModel.setScriptFailure(errorMessage.substring(errorMessage.indexOf("TimeoutException:") + "TimeoutException:".length(), errorMessage.indexOf("Build info:")).trim());
                return reportModel;
            } else {
                if (typeContent.contains("RuntimeTestException") && errorMessage.contains("Details:")) {
                    String errorMessageSubString = errorMessage.substring(errorMessage.indexOf("RuntimeTestException:") + "RuntimeTestException:".length(), errorMessage.indexOf("Details:"));

                    if (typeContent.contains("Exception caused from action")) {
                        ReportModel reportModel = new ReportModel();
                        reportModel.setScriptFailure(errorMessageSubString.trim());
                        return reportModel;
                    } else if (errorMessage.contains("Exception caused from action")) {
                        ReportModel reportModel = new ReportModel();
                        String errorDetails = errorMessage.substring(errorMessage.indexOf("Details:") + "Details:".length()).trim();
                        if (errorDetails.startsWith("null")) {
                            errorDetails = errorMessageSubString;
                        }
                        reportModel.setScriptFailure(errorDetails);
                        return reportModel;
                    }
                }
            }
            return this.nextReportTypeMatcher.parse(typeContent, errorMessage);
        }

    }

    public static class ThrownException extends ReportMatcher {
        public ThrownException() {
            super();
        }

        @Override
        public ReportModel parse(String typeContent, String errorMessage) {
            if (typeContent.contains("RuntimeTestException") && Arrays.stream(notThrownException).noneMatch(errorMessage::contains)) { //This is mostly a thrown exception
                ReportModel reportModel = new ReportModel();
                reportModel.setFunctionalFailure(errorMessage);
                return reportModel;
            }
            return this.nextReportTypeMatcher.parse(typeContent, errorMessage);
        }
    }

    public static class OtherTypeFailures extends ReportMatcher {
        public OtherTypeFailures() {
            super();
        }

        @Override
        public ReportModel parse(String typeContent, String errorMessage) {
            ReportModel reportModel = new ReportModel();
            reportModel.setGeneralFailure(errorMessage.length() > 150 ? errorMessage.substring(0, 150) + " ...." : errorMessage);
            return reportModel;
        }
    }

    private static String extractVerifications(String failureMessage) {
        StringBuilder failureMessageContent = new StringBuilder();

        String matchString = "Verification Failed:(.*?)Description:";

        Pattern pattern = Pattern.compile(matchString);
        Matcher matcher = pattern.matcher(failureMessage);

        while (matcher.find()) {
            String verificationMessage = matcher.group();
            verificationMessage = verificationMessage
                    .replace("Verification Failed:", "")
                    .replace("Description:", "").trim();

            failureMessageContent.append(String.format("'%s';", verificationMessage));
        }
        return failureMessageContent.toString();
    }
}
