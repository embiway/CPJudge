package com.embi;

public enum Language {

    C_PLUS_PLUS("cpp", "g++ , -o ,", "./,", 2),
    JAVA("java", "javac ,", "java ,", 1);

    private String extension;

    // The command to be run in the bash shell to generate the executable
    private String compilerCommand;

    // Command to run the executable
    private String executionCommand;

    // The number of compiler arguments. For example for C++ we'll pass the file name + name of executable to be created.
    // For Java, we only need the name of the file as the class name should be same as file name.
    private int noOfCompilerArgs;

    public String getExtension() {
        return extension;
    }

    public String getCompilerCommand() {
        return compilerCommand;
    }

    public String getExecutionCommand() {
        return executionCommand;
    }

    public int getNoOfCompilerArgs() {
        return noOfCompilerArgs;
    }

    private Language(String extension, String compilerCommand, String executionCommand, int noOfCompilerArgs) {
        this.compilerCommand = compilerCommand;
        this.extension = extension;
        this.executionCommand = executionCommand;
        this.noOfCompilerArgs = noOfCompilerArgs;
    }

    public static Language getLanguage(String extension) {
        for (Language language : Language.values()) {
            if (language.getExtension().equals(extension)) return language;
        }

        return null;
    }
}
