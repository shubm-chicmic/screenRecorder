package com.example.screenrecorder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

public class ScreenRecorderApp3 extends Application {
    private boolean isRecording = false;
    private Robot robot;
    private int frameNumber = 0;
    private String tempImagesDirectory = "tempImages";
    private List<BufferedImage> frameList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Screen Recorder");

        Button startButton = new Button("Start Recording");
        Button stopButton = new Button("Stop Recording");
        stopButton.setDisable(true);

        startButton.setOnAction(e -> {
            if (!isRecording) {
                try {
                    robot = new Robot();
                    startRecording();
                    startButton.setText("Stop Recording");
                    stopButton.setDisable(false);
                } catch (AWTException awtException) {
                    awtException.printStackTrace();
                }
            } else {
                stopRecording();
                startButton.setText("Start Recording");
                stopButton.setDisable(true);
                generateVideo();
            }
        });

        VBox vbox = new VBox(startButton, stopButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void startRecording() {
        isRecording = true;
        frameNumber = 0;
        frameList.clear();

        // Create a scheduled executor to capture frames
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::captureFrame, 0, 33, TimeUnit.MILLISECONDS);
    }

    private void captureFrame() {
        if (!isRecording) {
            return;
        }

        try {
            // Capture the screen as an image
            BufferedImage capture = robot.createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            frameList.add(capture);
            frameNumber++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        isRecording = false;
    }

    private void generateVideo() {
        if (!frameList.isEmpty()) {
            String outputVideoPath = "output.mp4";

            // Create a video from the captured frames
            int frameRate = 30;
            File videoFile = new File(outputVideoPath);
            BufferedImage firstFrame = frameList.get(0);

            try {
                ImageIO.write(firstFrame, "png", videoFile);
                for (int i = 1; i < frameList.size(); i++) {
                    ImageIO.write(frameList.get(i), "png", videoFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Encode the video
//                VideoEncoder.encodeVideo(outputVideoPath, frameRate, firstFrame.getWidth(), firstFrame.getHeight(), frameNumber);

            // Delete temporary image files
            deleteTempImages();
        }
    }

    private void deleteTempImages() {
        File tempDir = new File(tempImagesDirectory);
        if (tempDir.exists()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            tempDir.delete();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
