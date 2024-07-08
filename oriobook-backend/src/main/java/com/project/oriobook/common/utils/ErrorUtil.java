package com.project.oriobook.common.utils;

import java.util.Arrays;
import java.util.List;

public class ErrorUtil {
    public static String getStackTraceElement(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder stackTraceString = new StringBuilder();
        if (stackTrace.length > 0) {

            stackTraceString.append(String.format("%s: %s",
                    ErrorUtil.convertMsgToUpperCase(ex.getMessage()), ex.getMessage()));

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

    public static String convertMsgToUpperCase(String msg) {
        String _msgReplace = msg.replaceAll("[.]+", "");

        List<String> _msgSplit = Arrays.stream(_msgReplace.split(" ")).map(element ->
                element.substring(0,1).toUpperCase() + element.substring(1).toLowerCase()
        ).toList();

        _msgReplace = String.join("", _msgSplit);
        return _msgReplace;
    }
}
