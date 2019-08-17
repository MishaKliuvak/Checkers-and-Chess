import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static Main main;
    public static Main getInstance() {
        if (main == null) {
            Main.main = new Main();
        }
        return Main.main;
    }
    public Scene scene;
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

        pStage.getScene().getStylesheets().add(this.getClass().getResource("styles/main-styles.css").toExternalForm());

        pStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
