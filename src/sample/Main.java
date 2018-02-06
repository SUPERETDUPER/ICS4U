package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Salutation et Avatar");

        BorderPane layout = new BorderPane();


        final Text bienvenue = new Text("Bonjour! Mon nom est Martin!");
        bienvenue.setFont(Font.font(null, 20));
        layout.setTop(bienvenue);
        BorderPane.setAlignment(bienvenue, Pos.CENTER);

        final Text popup = new Text("Surprise!");
        popup.setFont(Font.font(null, 20));
        popup.setVisible(false);
        layout.setBottom(popup);
        BorderPane.setAlignment(popup, Pos.CENTER);

        final ImageView avatar = new ImageView();

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
                popup.setVisible(!popup.isVisible());
            }
        });

        layout.setCenter(avatar);

        Scene scene = new Scene(layout, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
