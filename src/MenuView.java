import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuView {

    public Scene scene;
    private Pane mainPane, checkersPane, chessesPane;
    private Label titleLabel, chessLabel, checkersLabel, firstLine, secondLine;

    private ImageView chessIV, checkIV;
    private Image chessImage, checkImage;
    private Button chessBtn, checkBtn;
    private static MenuView menu = null;
    private MenuView() {
    }

    public static MenuView getInstance() {
        if (menu == null) {
            MenuView.menu = new MenuView();
        }
        return MenuView.menu;
    }

    /**
     *
     * головна сцена
     */
    public Scene changePane(){

        scene = new Scene(createPane(), 500, 500);
        scene.setFill(Color.BLACK);


        return scene;
    }

    /**
     *
     * панель головного меню
     */
    public  Pane createPane(){
        mainPane = new Pane();
        mainPane.setId("pane");
        mainPane.setPrefSize(500, 500);

        titleLabel = new Label("Оберіть розділ");
        titleLabel.setId("titleM");

        mainPane.getChildren().addAll(CK_Pane(), CS_Pane());
        return mainPane;
    }

    private Pane CK_Pane(){
        checkersPane = new Pane();
        checkersPane.setId("CK");

        checkImage = new Image(this.getClass().getResource("resources/checkers.png").toExternalForm());
        checkIV = new ImageView(checkImage);
        checkIV.relocate(280, 25);

        checkersLabel = new Label("Партії в шашки");
        checkersLabel.setId("ckL");
        checkersLabel.relocate(57, 35);

        secondLine = new Label("від найкращих гравців");
        secondLine.setId("sl");
        secondLine.relocate(110, 75);

        checkBtn = new Button("Відкрити");
        checkBtn.setId("ckB");

        checkersPane.getChildren().addAll(checkIV, checkersLabel, secondLine, checkBtn);
        return checkersPane;
    }

    private Pane CS_Pane(){
        chessesPane = new Pane();
        chessesPane.setId("CS");

        chessImage = new Image(this.getClass().getResource("resources/chess.png").toExternalForm());
        chessIV = new ImageView(chessImage);
        chessIV.relocate(20, 25);

        chessLabel = new Label("Партії в шахи");
        chessLabel.setId("csL");
        chessLabel.relocate(130, 35);

        firstLine = new Label("від найкращих гравців");
        firstLine.setId("fl");
        firstLine.relocate(140, 75);

        chessBtn = new Button("Відкрити");
        chessBtn.setId("csB");

        chessesPane.getChildren().addAll(chessIV, chessLabel, firstLine, chessBtn);
        return chessesPane;
    }
}