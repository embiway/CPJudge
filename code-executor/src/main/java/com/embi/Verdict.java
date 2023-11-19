package com.embi;

public class Verdict {
    public enum ResultCode {
        CF("Compilation Failed"),
        AC("Accepted"),
        WA("Wrong Answer"),
        TLE("Time Limit Exceeded"),
        MLE("Memory Limit Exceeded"),

        // Umbrella term for all exceptions.
        UE("Unknown Error");

        private final String name;
        ResultCode(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    ResultCode resultCode;

    // To describe the compilation result. Can also be used to populate some other useful information for others.
    String message;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
