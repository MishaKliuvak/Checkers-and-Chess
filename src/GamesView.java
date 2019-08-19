import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class GamesView {
    public Scene scene;
    private Pane pane;

    private static GamesView gamesView = null;

    private Button selectBtn, backBtn, editBtn, deleteBtn, addBtn;

    private GamesView() {
    }

    public static GamesView getInstance() {
        if (gamesView == null) {
            GamesView.gamesView = new GamesView();
        }
        return GamesView.gamesView;
    }

    private List<String> names = new ArrayList<String>();
    private List<String> descriptions = new ArrayList<String>();
    private List<String> ids = new ArrayList<String>();

    private String table = "";

    public Pane changePane(Scene scene, int name_id, String[] backname, String[] backcon, String backpart){
        pane = new Pane();
        pane.setPrefSize(500, 500);
        pane.setId("pane");

        table = (backpart.equals("ChessPlayers")) ? "chess_games" : "checkers_game";

        names = DataBase.getInstance().getGames(String.valueOf(name_id), table, "name");
        descriptions = DataBase.getInstance().getGames(String.valueOf(name_id), table, "description");
        ids = DataBase.getInstance().getGames(String.valueOf(name_id), table, "id");

        ObservableList<CustomThing> data = FXCollections.observableArrayList();
        for(int i = 0; i < names.size(); i++){
            data.add(new CustomThing(names.get(i), descriptions.get(i), Integer.valueOf(ids.get(i))));
        }

        final ListView<CustomThing> listView = new ListView<CustomThing>(data);
        listView.setCellFactory(new Callback<ListView<CustomThing>, ListCell<CustomThing>>() {
            @Override
            public ListCell<CustomThing> call(ListView<CustomThing> listView) {
                return new CustomListCell();
            }
        });
        listView.relocate(15, 30);
        listView.setPrefSize(480, 400);


        selectBtn = new Button("Переглянути");
        selectBtn.setId("selectBtn");
        selectBtn.setOnMouseClicked(event -> {
            if(listView.getSelectionModel().getSelectedItem() == null){
                AlertShowing.showAlert(pane,"Помилка", "Виберіть партію");
            }
            else {
                scene.setRoot(GameOpenView.getInstance().changePane(scene, listView.getSelectionModel().getSelectedItem().id, name_id, backname, backcon, backpart,
                        listView.getSelectionModel().getSelectedItem().name, listView.getSelectionModel().getSelectedItem().description,
                            DataBase.getInstance().getLink(table, listView.getSelectionModel().getSelectedItem().id)));
               // System.out.println(DataBase.getInstance().getGames("1", "chess_games"));
                //System.out.println(listView.getSelectionModel().getSelectedItem().name);
            }
        });
        selectBtn.relocate(350, 460);

        backBtn = new Button("Назад");
        backBtn.setId("selectBtn");
        backBtn.setOnMouseClicked(event -> {
            scene.setRoot(PlayersView.getInstance().changePane(scene, backname, backcon, backpart));
        });
        backBtn.relocate(30, 460);

        deleteBtn = new Button("-");
        deleteBtn.setId("deleteBtn");
        deleteBtn.setOnMouseClicked(event -> {
            if(listView.getSelectionModel().getSelectedItem() == null){
                AlertShowing.showAlert(pane,"Помилка", "Виберіть партію");
            }
            else {
                DataBase.getInstance().deleteFromDB(table, listView.getSelectionModel().getSelectedItem().id);
                scene.setRoot(changePane(scene, name_id, backname,backcon,backpart));
            }
        });
        deleteBtn.relocate(243, 460);

        addBtn = new Button("+");
        addBtn.setId("addBtn");
        addBtn.setOnMouseClicked(event -> {
                //System.out.println(listView.getSelectionModel().getSelectedItem().id);
                AddStage.getInstance().newStage(scene, table, name_id, backname, backcon, backpart, 0, "add").show();
        });
        addBtn.relocate(200, 460);

        editBtn = new Button("/");
        editBtn.setId("editBtn");

        editBtn.setOnMouseClicked(event -> {
            if(listView.getSelectionModel().getSelectedItem() == null){
                AlertShowing.showAlert(pane,"Помилка", "Виберіть партію");
            }
            else {
                AddStage.getInstance().newStage(scene, table, name_id, backname, backcon, backpart, listView.getSelectionModel().getSelectedItem().id, "change").show();
            }
        });
        editBtn.relocate(286, 460);

        pane.getChildren().addAll(listView, selectBtn, backBtn, deleteBtn, addBtn, editBtn);
        return pane;
    }

    private static class CustomThing {
        private String name;
        private String description;
        private int id;

        public String getName() {
            return name;
        }
        public String getPrice() {
            return description;
        }
        public Integer getId() {
            return id;
        }
        public CustomThing(String name, String description, int id) {
            super();
            this.name = name;
            this.description = description;
            this.id = id;
        }
    }

    private class CustomListCell extends ListCell<CustomThing> {
        private HBox content;
        private Text name;
        private Text price;

        public CustomListCell() {
            super();
            name = new Text();
            name.setId("gname");

            price = new Text();
            price.setId("gdescr");

            VBox vBox = new VBox(name, price);
            name.relocate(30,20);
            price.relocate(30,60);
            content = new HBox(vBox);
        }

        @Override
        protected void updateItem(CustomThing item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                name.setText(item.getName());
                price.setText(item.getPrice());
                setGraphic(content);
                setPrefHeight(50);
            } else {
                setGraphic(null);
            }
        }
    }
}
