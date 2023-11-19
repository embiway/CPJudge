package com.embi.server;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(value = "!windows")
public class CommandPreparationManagerForLinux implements CommandPreparationManager{
    @Override
    public List<String> getCheckProcessCommand(String executable) {
        return List.of("pgrep", executable);
    }

    @Override
    public List<String> getTaskKillCommand(String executable) {
        return List.of("pkill", executable);
    }

    @Override
    public List<String> getFinalBashCommand(String command) {
        return List.of("/usr/bin/bash", "-c", command);
    }

    @Override
    public String getExecutableExtension() {
        return ".out";
    }
}
