package com.project.oriobook.common.utils;

import java.util.Arrays;

public class ErrorUtil {
    public static String getStackTraceElement(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder stackTraceString = new StringBuilder();
        if (stackTrace.length > 0) {
            String _msgReplace = ex.getMessage().replaceAll("\\s+", "");
            stackTraceString.append(String.format("%s: %s", _msgReplace, ex.getMessage()));
            Arrays.stream(stackTrace).limit(3).forEach(element -> {
                String stackTemp = String.format("   at %s.%s(%s:%d)",
                        element.getClassName(),
                        element.getMethodName(),
                        element.getFileName(),
                        element.getLineNumber());
                stackTraceString.append(stackTemp).append("\n");
            });
            return stackTraceString.toString();
        }
        return "No stack trace available";
    }
}
