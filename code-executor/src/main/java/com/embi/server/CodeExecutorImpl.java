package com.embi.server;

import com.embi.Code;
import com.embi.Language;
import com.embi.Problem;
import com.embi.Verdict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class CodeExecutorImpl implements CodeExecutor {

//    @Autowired
    CommandPreparationManager commandPreparationManager = new CommandPreparationManagerForLinux();

    private String prepareCommand(Code code, boolean isCompilerCommand) {
        Language language = code.getLanguage();
        String[] result = isCompilerCommand ? language.getCompilerCommand().split(",") : language.getExecutionCommand().split(",");

        StringBuilder cmd = new StringBuilder();

        if (result.length > 1) {
            cmd.append(result[0]);
            cmd.append(code.getId()).append(".").append(code.getLanguage().getExtension());
            cmd.append(result[1]);
            cmd.append(code.getId()+"x");
        }
        else {
            cmd.append(result[0]);
            cmd.append(code.getId()+"x");
        }

        return cmd.toString();
    }
    private String prepareCompileCommand(Code code) {
        return prepareCommand(code, true);
    }

    private String prepareExecutableCommand(Code code) {
        return prepareCommand(code, false);
    }

    private void terminateProcess(Process process, String executable) throws IOException, InterruptedException {
        // Should never be this case
        if (executable.equals("")) {
            process.destroy();
            return;
        }

        // Check if any process by this name is being executed currently
        Process pidFetchProcess = new ProcessBuilder(commandPreparationManager.getCheckProcessCommand(executable)).start();
        BufferedReader pidReader = new BufferedReader(new InputStreamReader(pidFetchProcess.getInputStream()));
        List<String> args = new ArrayList<>();
        String curr;
        while ((curr = pidReader.readLine()) != null) {
            System.out.println(curr + "\n");
            args.add(curr);
        }

        if (!args.isEmpty()) {
            System.out.println("Args is empty\n");
            Process killProcess = new ProcessBuilder(commandPreparationManager.getTaskKillCommand(executable)).start();
            killProcess.waitFor();
        }

        // Just destroy the original process that spun the subprocess.
        process.destroy();
    }

    private void sendInputToStream(Process process, String input) throws IOException, InterruptedException {
        Thread.sleep(4000);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write(input);
        System.out.println("Done\n");
        writer.close();
    }

    private String fetchResult(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String currLine;
        StringBuilder result = new StringBuilder();

        while ((currLine = bufferedReader.readLine()) != null) {
            result.append(currLine).append('\n');
        }

        return result.toString();
    }

    public CompletableFuture<String> compileCode(Code code) throws IOException {
        // Create file from the code that'll be compiled
        File codeFile = new File(code.getId() + "." + code.getLanguage().getExtension());
        boolean isFileCreated = codeFile.createNewFile();

        System.out.println("File created status : " + isFileCreated);

        // Populate this file with the code
        Files.writeString(Path.of(codeFile.getPath()), code.getCode());

        ProcessBuilder compileProcessBuilder = new ProcessBuilder();
        compileProcessBuilder.command(commandPreparationManager.getFinalBashCommand(prepareCompileCommand(code)));
        compileProcessBuilder.redirectErrorStream(true);
        System.out.println(prepareCompileCommand(code));

        Process compileProcess = compileProcessBuilder.start();

        // The future will run in the forkJoinPool for as long as possible.

        return CompletableFuture.supplyAsync(() -> {
            String result;
            try {
                result = fetchResult(compileProcess);
                compileProcess.waitFor();
            } catch (InterruptedException | IOException e) {
                result = "Compilation interrupted: " + e.getMessage();
            }
//            finally {
//                // Delete codeFile when executable is created.
//                codeFile.delete();
//            }

            System.out.println("Compile result: " + result);
            return result;
        });
    }

    // Executes the code
    public CompletableFuture<String> executeCode(CompletableFuture<String> compileFuture, Code code, String inputParams) throws Exception {
        return compileFuture.thenApplyAsync((res) -> {
            if (!res.isEmpty()) {
                return Verdict.ResultCode.CF.getName() + res;
            }
            StringBuilder result = new StringBuilder();
            ProcessBuilder executionProcessBuilder = new ProcessBuilder();
            executionProcessBuilder.command(commandPreparationManager.getFinalBashCommand(prepareExecutableCommand(code)));
            try {
                Process execProcess = executionProcessBuilder.start();
                sendInputToStream(execProcess, inputParams);
                CompletableFuture<Void> execFuture = CompletableFuture.runAsync(() -> {
                    try {
                        execProcess.waitFor();
                        result.append(fetchResult(execProcess));
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
                ScheduledFuture<String> timeLimitFuture = executorService.schedule(() -> {
                    // If after the time specified in the problem the future is not completed then
                    if (!execFuture.isDone()) {
                        terminateProcess(execProcess, code.getId()+commandPreparationManager.getExecutableExtension());
                        return Verdict.ResultCode.TLE.getName();
                    }
                    return "";
                }, code.getProblem().getTimeLimit(), TimeUnit.SECONDS);

                String timeLimitVerdict = timeLimitFuture.get();
                if (timeLimitVerdict.isEmpty()) return result.toString();
                return timeLimitVerdict;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        CodeExecutorImpl codeExecutor = new CodeExecutorImpl();
        int status = 0;
        try {
            Code code = new Code(1,
                    new Problem(1, "problem1", 1, 1),
                    "#include<bits/stdc++.h>\n" +
                            "using namespace std;\n" +
                            "\n" +
                            "int main() {\n" +
                            "    int n;\n" +
                            "    cin >> n;\n" +
                            "\n" +
                            "    cout << n << endl;\n" +
                            "\n" +
                            "    int cnt = 0;\n" +
                            "    while (cnt++ < 100000000000);\n" +
                            "\n" +
                            "    for (int i = 0 ; i < n ; i++) {\n" +
                            "        int x;\n" +
                            "        cin >> x;\n" +
                            "\n" +
                            "        cout << 2 * x << endl;\n" +
                            "    }\n" +
                            "}", Language.C_PLUS_PLUS);
            CompletableFuture<String> compileFuture = codeExecutor.compileCode(code);
            System.out.println(codeExecutor.executeCode(compileFuture, code, "5 1 2 3 4 5\n").get());
        } catch (Exception e) {
            status = 1;
            throw new RuntimeException(e);
        } finally {
            System.exit(status);
        }
    }
}
