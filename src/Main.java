import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * запуск додатку
     * @param pStage головне вікно
     */
    public void start(final Stage pStage) throws Exception {
        pStage.getIcons().add(new Image("resources/ico.png"));
        pStage.setTitle("Бази даних шахових та шашкових партій");
        pStage.setScene(MenuView.getInstance().changePane());
        pStage.setResizable(false);
        pStage.setMaximized(false);



        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        pStage.setX((primScreenBounds.getWidth() - 516) / 2);
        pStage.setY((primScreenBounds.getHeight() - 539) / 2);

        pStage.getScene().getStylesheets().add(this.getClass().getResource("styles/main-styles.css").toExternalForm());

        pStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
