package com.embi.server;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(value = "windows")
public class CommandPreparationManagerForWindows implements CommandPreparationManager{
    @Override
    public List<String> getCheckProcessCommand(String executable) {
        return List.of("powershell", "-Command", "Get-Process -Name " + executable.split("\\.")[0]);
    }

    @Override
    public List<String> getTaskKillCommand(String executable) {
        return List.of("taskkill", "/F", "/IM", executable);
    }

    @Override
    public List<String> getFinalBashCommand(String command) {
        return List.of("cmd.exe", "/c", command);
    }

    @Override
    public String getExecutableExtension() {
        return ".exe";
    }
}
