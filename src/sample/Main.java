package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application implements EventHandler<MouseEvent> {

    ImageView avatar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Salutation et Avatar");

        avatar = new ImageView();

        avatar.setImage(new Image(new FileInputStream("res/avatar.png")));

        avatar.setPreserveRatio(true);

        avatar.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                avatar.setFitHeight(300);
            }
        });

        avatar.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                avatar.setFitHeight(250);
            }
        });

        avatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(avatar);

        Scene scene = new Scene(layout, 600, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(MouseEvent event) {
        switch (event.getEventType()){
            case MouseEvent.MOUSE_ENTERED:
                avatar.setFitHeight(300);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
