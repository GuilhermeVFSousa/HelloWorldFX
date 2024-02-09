package com.example.helloworldfx;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WinOsCommandHandler {

    public static Pair<String, Integer> executeCommand(String... commands) {
        try {
            var processBuilder = new ProcessBuilder("powershell.exe", String.join("; ", commands));
            processBuilder.redirectErrorStream(true);
            var process = processBuilder.start();

            var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            var output = new ArrayList<String>();

            while (reader.readLine() != null)
                output.add(reader.readLine());

            int exitCode = process.waitFor();

            return new Pair<>(String.join("\n", output), exitCode);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void listFileSystem() {
        try {
            String comandoPowerShell = "cd C:\\; ls";

            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", comandoPowerShell);

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            var linha = new ArrayList<String>();
            while (reader.readLine() != null) {
                linha.add(reader.readLine());
            }

            int exitCode = process.waitFor();
            System.out.println("O comando terminou com o código de saída: " + exitCode);

            System.out.println((String.join("\n", linha)));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
