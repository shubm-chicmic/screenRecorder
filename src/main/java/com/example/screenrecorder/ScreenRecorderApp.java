//package com.example.screenrecorder;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import javax.imageio.ImageIO;
//import org.bytedeco.javacv.*;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.opencv.global.opencv_imgcodecs;
//import org.bytedeco.opencv.opencv_core.*;
//
//public class ScreenRecorderApp extends Application {
//    private boolean isRecording = false;
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Screen Recorder");
//
//        Button startButton = new Button("Start Recording");
//        startButton.setOnAction(e -> {
//            if (!isRecording) {
//                startRecording();
//                startButton.setText("Stop Recording");
//            } else {
//                stopRecording();
//                startButton.setText("Start Recording");
//            }
//        });
//
//        VBox vbox = new VBox(startButton);
//        Scene scene = new Scene(vbox, 300, 200);
//        primaryStage.setScene(scene);
//
//        primaryStage.show();
//    }
//
//    private void startRecording() {
//        // Define the temporary directory where captured frames will be saved.
//        String tempDir = "path_to_temporary_directory"; // Change this to your desired directory.
//
//        // Set up the screen capture using JavaCV.
//        FrameGrabber grabber = new ScreenGrabber();
//        try {
//            grabber.start();
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        int frameNumber = 0;
//        isRecording = true;
//
//        // Create an FFmpegFrameRecorder to save the frames as a video (MP4).
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("output.mp4", grabber.getImageWidth(), grabber.getImageHeight());
//        recorder.setVideoCodecName("libx264");
//        recorder.setFormat("mp4");
//        recorder.setFrameRate(30); // Set your desired frame rate here.
//        try {
//            recorder.start();
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//        }
//
//        // Continuously capture frames and add them to the video until recording is stopped.
//        while (isRecording) {
//            try {
//                Frame frame = grabber.grab();
//                if (frame != null) {
//                    // Convert the frame to an IplImage.
//                    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//                    IplImage img = converter.convert(frame);
//
//                    // Write the frame to the video.
//                    recorder.record(img);
//
//                    frameNumber++;
//                }
//            } catch (FrameGrabber.Exception e) {
//                e.printStackTrace();
//            } catch (FrameRecorder.Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            grabber.stop();
//            recorder.stop();
//        } catch (FrameGrabber.Exception | FrameRecorder.Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void stopRecording() {
//        // Stop the screen recording process.
//        isRecording = false;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
