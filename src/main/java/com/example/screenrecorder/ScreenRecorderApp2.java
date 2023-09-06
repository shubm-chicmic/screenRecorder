package com.example.screenrecorder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ScreenRecorderApp2 extends Application {
    private boolean isRecording = false;
    private Robot robot;
    private int frameNumber = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Screen Recorder");

        Button startButton = new Button("Start Recording");
        startButton.setOnAction(e -> {
            if (!isRecording) {
                try {
                    robot = new Robot();
                    startRecording();
                    startButton.setText("Stop Recording");
                } catch (AWTException awtException) {
                    awtException.printStackTrace();
                }
            } else {
                stopRecording();
                startButton.setText("Start Recording");
            }
        });

        VBox vbox = new VBox(startButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void startRecording() {
        // Define the output directory where captured frames will be saved.
        String outputDir = "C:\\Users\\shubm\\IdeaProjects\\screenRecorder\\src\\main\\resources\\tempImages"; // Change this to your desired directory.
        isRecording = true;

        while (isRecording) {
            try {
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture = robot.createScreenCapture(screenRect);

                String outputFileName = String.format("%s/frame_%04d.png", outputDir, frameNumber);
                frameNumber++;

                File outputFile = new File(outputFileName);

                try {
                    ImageIO.write(capture, "png", outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Thread.sleep(33); // Adjust frame rate by changing the sleep duration.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording() {
        isRecording = false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
