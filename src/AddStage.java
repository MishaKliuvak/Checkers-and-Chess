import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class AddStage {

    private static AddStage addStage;
    private static Label linkLbl, nameLbl, descrLbl;
    private static TextField linkTF, nameTF, desctTF;
    private static Button addBtn;
    private static List<String> data = new ArrayList<String>();
    private AddStage(){

    }

    public static AddStage getInstance(){
        if(addStage == null){
            AddStage addStage = new AddStage();
        }
        return AddStage.addStage;
    }

    public static Stage newStage(Scene scene, String table, int player, String[] backname, String[] backcon, String backpart,
                                 int id_game, String action){
        data.clear();
        Stage st = new Stage();
        st.setHeight(200);
        st.setWidth(300);
        st.getIcons().add(new Image("resources/ico.png"));

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        st.setX((primScreenBounds.getWidth() - 300) / 2);
        st.setY((primScreenBounds.getHeight() - 200) / 2);

        st.initOwner(scene.getWindow());
        st.initModality(Modality.WINDOW_MODAL);
        Pane pane = new Pane();

        nameLbl = new Label("Назва партії:");
        nameLbl.relocate(27, 25);

        nameTF = new TextField();
        nameTF.relocate(110, 20);
        nameTF.setPrefSize(150, 20);

        descrLbl = new Label("Опис партії:");
        descrLbl.relocate(27, 55);

        desctTF = new TextField();
        desctTF.relocate(110, 50);
        desctTF.setPrefSize(150, 20);

        linkLbl = new Label("Посилання:");
        linkLbl.relocate(27, 85);

        linkTF = new TextField();
        linkTF.relocate(110, 80);
        linkTF.setPrefSize(150, 20);

        addBtn = new Button("");
        addBtn.setId("selectBtn");

        if(action.equals("add")){
            addBtn.setText("Додати");
            st.setTitle("Додати");
        }
        else {
            addBtn.setText("Змінити");
            st.setTitle("Змінити");
            data = DataBase.getInstance().getGamesData(id_game, table);

            nameTF.setText(data.get(0));
            desctTF.setText(data.get(1));
            linkTF.setText(data.get(2));
        }
        addBtn.setOnMouseClicked(event -> {
            if(!nameTF.getText().equals("") && !desctTF.getText().equals("") && !linkTF.getText().equals("")){
                if(action.equals("add")){
                    DataBase.getInstance().addGame(table, nameTF.getText(), desctTF.getText(), linkTF.getText(), player);
                    st.close();
                    scene.setRoot(GamesView.getInstance().changePane(scene, player, backname, backcon, backpart));
                }
                else {
                    DataBase.getInstance().update(table, nameTF.getText(), desctTF.getText(), linkTF.getText(), id_game);
                    st.close();
                    scene.setRoot(GamesView.getInstance().changePane(scene, player, backname, backcon, backpart));
                }
            }
            else {
                AlertShowing.showAlert(pane,"Помилка", "Заповніть поля");
            }
        });
        addBtn.relocate(75, 120);

        pane.getChildren().addAll(nameLbl, nameTF, desctTF, descrLbl, linkLbl, linkTF, addBtn);

        st.setScene(new Scene(pane, 300, 200));
        st.getScene().getStylesheets().add("styles/main-styles.css");
        return st;
    }
}
