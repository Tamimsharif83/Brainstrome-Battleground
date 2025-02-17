package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import javafx.scene.control.ChoiceBox;
import java.io.*;

public class GamePageController {

    @FXML
    private TextArea codeEditor;

    @FXML
    private TextArea outputArea;

    @FXML
    private Button runButton;

    @FXML
    private ChoiceBox<String> languageSelector; // ভাষা সিলেক্ট করার জন্য

    @FXML
    public void initialize() {
        // ডিফল্ট ভাষা লিস্টে যোগ করছি
        languageSelector.getItems().addAll("Python", "C", "C++", "Java");
        languageSelector.setValue("Python"); // ডিফল্ট Python সেট
    }

    @FXML
    private void runCode() {
        String code = codeEditor.getText();
        String selectedLanguage = languageSelector.getValue(); // ইউজার যে ভাষা সিলেক্ট করেছে

        if (code.isEmpty()) {
            outputArea.setText("Error: No code to run!");
            return;
        }

        try {
            File tempFile;
            ProcessBuilder pb;

            switch (selectedLanguage) {
                case "Python":
                    tempFile = new File("temp_script.py");
                    writeToFile(tempFile, code);
                    pb = new ProcessBuilder("python", tempFile.getAbsolutePath());
                    break;

                case "C":
                    tempFile = new File("temp_program.c");
                    writeToFile(tempFile, code);
                    Process compileC = new ProcessBuilder("gcc", tempFile.getAbsolutePath(), "-o", "temp_program").start();
                    compileC.waitFor();
                    pb = new ProcessBuilder("./temp_program");
                    break;

                case "C++":
                    tempFile = new File("temp_program.cpp");
                    writeToFile(tempFile, code);
                    Process compileCpp = new ProcessBuilder("g++", tempFile.getAbsolutePath(), "-o", "temp_program").start();
                    compileCpp.waitFor();
                    pb = new ProcessBuilder("./temp_program");
                    break;

                case "Java":
                    tempFile = new File("TempProgram.java");
                    writeToFile(tempFile, code);
                    Process compileJava = new ProcessBuilder("javac", tempFile.getAbsolutePath()).start();
                    compileJava.waitFor();
                    pb = new ProcessBuilder("java", "TempProgram");
                    break;

                default:
                    outputArea.setText("Error: Unsupported language!");
                    return;
            }

            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            outputArea.setText(output.toString());

            // Cleanup
            tempFile.delete();
            new File("temp_program").delete();
            new File("TempProgram.class").delete();

        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void writeToFile(File file, String content) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(content);
        writer.close();
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        codeEditor.clear(); // Reset the code editor
    }
}
