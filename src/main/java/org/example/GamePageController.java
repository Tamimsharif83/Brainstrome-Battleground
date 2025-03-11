package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class GamePageController {

    public Button backButton;
    @FXML
    private CodeArea codeEditor;

    @FXML
    private ChoiceBox<String> languageSelector;

    @FXML
    private TextArea outputArea; // Output দেখানোর জন্য TextArea

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("Python", "C", "C++", "Java");
        languageSelector.setValue("Python");
        setupAutoComplete();
    }

    private void setupAutoComplete() {
        codeEditor.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.endsWith("(")) {
                codeEditor.appendText(")");
                codeEditor.moveTo(codeEditor.getCaretPosition() - 1);
            } else if (newText.endsWith("{")) {
                codeEditor.appendText("}");
                codeEditor.moveTo(codeEditor.getCaretPosition() - 1);
            } else if (newText.endsWith("\"")) {
                codeEditor.appendText("\"");
                codeEditor.moveTo(codeEditor.getCaretPosition() - 1);
            }
        });

        codeEditor.textProperty().addListener((obs, oldText, newText) -> {
            codeEditor.setStyleSpans(0, computeHighlighting(newText));
        });
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        final String[] KEYWORDS = { "public", "private", "class", "static", "void", "if", "else", "return", "while", "for" };
        final Pattern KEYWORD_PATTERN = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        Matcher matcher = KEYWORD_PATTERN.matcher(text);
        int lastKwEnd = 0;

        while (matcher.find()) {
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton("keyword"), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    // ✅ runButton-এর জন্য Event Handler
    @FXML
    private void runCode() {
        String code = codeEditor.getText();
        String language = languageSelector.getValue();

        switch (language) {
            case "Python":
                runPythonCode(code);
                break;
            case "C":
                runCCode(code);
                break;
            case "C++":
                runCppCode(code);
                break;
            case "Java":
                runJavaCode(code);
                break;
            default:
                outputArea.setText("Unsupported Language: " + language);
        }
    }

    private void runPythonCode(String code) {
        try {
            File tempScript = File.createTempFile("script", ".py");
            FileWriter writer = new FileWriter(tempScript);
            writer.write(code);
            writer.close();

            ProcessBuilder pb = new ProcessBuilder("python", tempScript.getAbsolutePath());
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
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    // ✅ C রান করার জন্য মেথড
    private void runCCode(String code) {
        try {
            File tempC = File.createTempFile("program", ".c");
            FileWriter writer = new FileWriter(tempC);
            writer.write(code);
            writer.close();

            File exeFile = new File(tempC.getParent(), "program.exe");

            ProcessBuilder compile = new ProcessBuilder("gcc", tempC.getAbsolutePath(), "-o", exeFile.getAbsolutePath());
            compile.redirectErrorStream(true);
            Process compileProcess = compile.start();
            compileProcess.waitFor();

            ProcessBuilder run = new ProcessBuilder(exeFile.getAbsolutePath());
            run.redirectErrorStream(true);
            Process runProcess = run.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            runProcess.waitFor();

            outputArea.setText(output.toString());
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    // ✅ C++ রান করার জন্য মেথড
    private void runCppCode(String code) {
        try {
            File tempCpp = File.createTempFile("program", ".cpp");
            FileWriter writer = new FileWriter(tempCpp);
            writer.write(code);
            writer.close();

            File exeFile = new File(tempCpp.getParent(), "program.exe");

            ProcessBuilder compile = new ProcessBuilder("g++", tempCpp.getAbsolutePath(), "-o", exeFile.getAbsolutePath());
            compile.redirectErrorStream(true);
            Process compileProcess = compile.start();
            compileProcess.waitFor();

            ProcessBuilder run = new ProcessBuilder(exeFile.getAbsolutePath());
            run.redirectErrorStream(true);
            Process runProcess = run.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            runProcess.waitFor();

            outputArea.setText(output.toString());
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    // ✅ Java রান করার জন্য মেথড
    private void runJavaCode(String code) {
        try {
            File tempJava = File.createTempFile("Main", ".java");
            FileWriter writer = new FileWriter(tempJava);
            writer.write(code);
            writer.close();

            ProcessBuilder compile = new ProcessBuilder("javac", tempJava.getAbsolutePath());
            compile.redirectErrorStream(true);
            Process compileProcess = compile.start();
            compileProcess.waitFor();

            ProcessBuilder run = new ProcessBuilder("java", "-cp", tempJava.getParent(), "Main");
            run.redirectErrorStream(true);
            Process runProcess = run.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            runProcess.waitFor();

            outputArea.setText(output.toString());
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }
    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleResetButton() {
        codeEditor.clear();
        outputArea.clear();
        languageSelector.setValue("Python");
    }


}