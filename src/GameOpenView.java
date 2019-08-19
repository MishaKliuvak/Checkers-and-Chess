import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

public class GameOpenView {

    private Pane pane;

    private static GameOpenView gameOpenView = null;

    private Button selectBtn, backBtn;

    private Label descrLbl, nameLbl;
    private GameOpenView() {
    }

    public static GameOpenView getInstance() {
        if (gameOpenView == null) {
            GameOpenView.gameOpenView = new GameOpenView();
        }
        return GameOpenView.gameOpenView;
    }

    public Pane changePane(Scene scene, int id, int name_id, String[] backname, String[] backcon, String backpart, String name, String description, String link){
        scene.getWindow().setWidth(1000);
        scene.getWindow().setHeight(700);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        scene.getWindow().setX((primScreenBounds.getWidth() - scene.getWindow().getWidth()) / 2);
        scene.getWindow().setY((primScreenBounds.getHeight() - scene.getWindow().getHeight()) / 2);

        System.out.println(id);
        pane = new Pane();
        pane.setPrefSize(500, 500);
        pane.setId("pane");

        nameLbl = new Label(name);
        nameLbl.setId("nameLbl");
        nameLbl.relocate(200, 20);

        descrLbl = new Label(description);
        descrLbl.setId("descrLbl");
        descrLbl.relocate(200, 40);

        WebView webView = new WebView();
        webView.setPrefSize(980, 580);
        webView.relocate(10, 70);
       // webView.relocate(100, 100);
        webView.getEngine().load(link);

        backBtn = new Button("Назад");
        backBtn.setId("selectBtn");
        backBtn.setOnMouseClicked(event -> {
            scene.getWindow().setWidth(516);
            scene.getWindow().setHeight(539);
            scene.getWindow().setX((primScreenBounds.getWidth() - scene.getWindow().getWidth()) / 2);
            scene.getWindow().setY((primScreenBounds.getHeight() - scene.getWindow().getHeight()) / 2);
            webView.getEngine().load(null);
            scene.setRoot(GamesView.getInstance().changePane(scene, name_id, backname, backcon, backpart));
        });
        backBtn.relocate(20, 20);

        pane.getChildren().addAll(backBtn, nameLbl, descrLbl, webView);
        return pane;
    }



}
