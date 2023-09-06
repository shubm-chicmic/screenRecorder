module com.example.screenrecorder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.datatransfer;
    requires java.desktop;
    requires org.bytedeco.opencv;
    requires org.bytedeco.javacv;

    requires org.bytedeco.ffmpeg;
//    requires opencv;
//    requires org.bytedeco.javacv;
//    requires org.bytedeco.opencv;


    opens com.example.screenrecorder to javafx.fxml;
    exports com.example.screenrecorder;
}