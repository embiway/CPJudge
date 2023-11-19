package com.embi.server;

import java.util.List;

public interface CommandPreparationManager {
    List<String> getCheckProcessCommand(String executable);

    List<String> getTaskKillCommand(String executable);

    List<String> getFinalBashCommand(String command);

    String getExecutableExtension();
}
