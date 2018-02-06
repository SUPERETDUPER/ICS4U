package sample;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;

public class Main extends Application {

    private static final int SCENE_HAUTEUR = 600;
    private static final int SCENE_LARGEUR = 600;

    private static final int AVATAR_HAUTEUR = 250;

    private static final int TAILLE_TEXT = 30;
    private static final int PADDING = 20;

    private static final int DUREE_FADE_IN = 1500;
    private static final int DUREE_ZOOM = 200;
    private static final double FACTEUR_ZOOM = 1.5;

    private static final String TITRE_FENETRE = "Salutation et Avatar";
    private static final String MESSAGE_SALUTATION = "Bonjour! Mon nom est Martin!";
    private static final String MESSAGE_POPUP = "Surprise!";

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        primaryStage.setTitle(TITRE_FENETRE);


        //Creation du message de bienvenue
        final Text bienvenue = new Text(MESSAGE_SALUTATION);
        bienvenue.setFont(Font.font(null, TAILLE_TEXT));


        //Creation du message qui s'affiche quand l'on click sur l'avatar
        final Text popup = new Text(MESSAGE_POPUP);
        popup.setFont(Font.font(null, TAILLE_TEXT));
        popup.setVisible(false);


        //Creation de l'avatar
        final ImageView avatar = new ImageView();
        avatar.setImage(new Image(new FileInputStream("res/avatar.png")));
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(AVATAR_HAUTEUR);

        //Creation des transitions zoom
        final ScaleTransition grandir = new ScaleTransition(Duration.millis(DUREE_ZOOM), avatar);
        grandir.setFromX(1);
        grandir.setFromY(1);
        grandir.setToX(FACTEUR_ZOOM);
        grandir.setToY(FACTEUR_ZOOM);

        final ScaleTransition rapetisser = new ScaleTransition(Duration.millis(DUREE_ZOOM), avatar);
        rapetisser.setFromX(FACTEUR_ZOOM);
        rapetisser.setFromY(FACTEUR_ZOOM);
        rapetisser.setToX(1);
        rapetisser.setToY(1);


        //Creation des events listeners
        avatar.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rapetisser.stop();
                grandir.play();
            }
        });

        avatar.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                grandir.stop();
                rapetisser.play();
                popup.setVisible(false);
            }
        });

        avatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.setVisible(!popup.isVisible());
            }
        });


        //Creation du layout
        BorderPane layout = new BorderPane(avatar, bienvenue, null, popup, null);
        BorderPane.setAlignment(bienvenue, Pos.CENTER);
        BorderPane.setAlignment(popup, Pos.CENTER);
        layout.setPadding(new Insets(PADDING));

        //Ajout du fade
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(DUREE_FADE_IN), layout);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        //Creation de la scene
        Scene scene = new Scene(layout, SCENE_LARGEUR, SCENE_HAUTEUR);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Debuter le fade
        fadeTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
