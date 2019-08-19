import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class PlayersView {
    public Scene scene;
    private Pane pane;

    private static PlayersView playersView = null;

    private Button selectBtn, backBtn;

    private int select = 0;

    private List<Image> listOfImages = new ArrayList<>();

    private PlayersView() {
    }

    public static PlayersView getInstance() {
        if (playersView == null) {
            PlayersView.playersView = new PlayersView();
        }
        return PlayersView.playersView;
    }

    public Pane changePane(Scene scene, String[] names, String[] countries, String part){
        listOfImages.clear();

        for(int i=0; i<5; i++){
            listOfImages.add(new Image(this.getClass().getResource("resources/" + part + "/" + (i+1) + ".png").toExternalForm()));
        }
        select = 0;

        pane = new Pane();
        pane.setPrefSize(500, 500);
        pane.setId("pane");

        ObservableList<CustomThing> data = FXCollections.observableArrayList();
        data.addAll(new CustomThing(names[0], countries[0]), new CustomThing(names[1], countries[1]),
                new CustomThing(names[2], countries[2]), new CustomThing(names[3], countries[3]), new CustomThing(names[4],countries[4]));

        final ListView<CustomThing> listView = new ListView<CustomThing>(data);
        listView.setCellFactory(new Callback<ListView<CustomThing>, ListCell<CustomThing>>() {
            @Override
            public ListCell<CustomThing> call(ListView<CustomThing> listView) {
                return new CustomListCell();
            }
        });
        listView.relocate(15, 30);
        listView.setPrefSize(480, 400);


        selectBtn = new Button("Перейти");
        selectBtn.setId("selectBtn");
        selectBtn.setOnMouseClicked(event -> {
            if(listView.getSelectionModel().getSelectedItem() == null){
                AlertShowing.showAlert(pane,"Помилка", "Виберіть гравця");
            }
            else {
                System.out.println(listView.getSelectionModel().getSelectedItem().name);
            }
        });
        selectBtn.relocate(350, 460);

        backBtn = new Button("Назад");
        backBtn.setId("selectBtn");
        backBtn.setOnMouseClicked(event -> {
            scene.setRoot(MenuView.getInstance().createPane());
        });
        backBtn.relocate(30, 460);

        pane.getChildren().addAll(listView, selectBtn, backBtn);
        return pane;
    }

    private static class CustomThing {
        private String name;
        private String nation;
        public String getName() {
            return name;
        }
        public String getPrice() {
            return nation;
        }
        public CustomThing(String name, String nation) {
            super();
            this.name = name;
            this.nation = nation;
        }
    }

    private class CustomListCell extends ListCell<CustomThing> {
        private HBox content;
        private Text name;
        private Text price;

        public CustomListCell() {
            super();
            name = new Text();
            name.setId("name");

            price = new Text();
            price.setId("price");

            VBox vBox = new VBox(name, price);
            name.relocate(10,10);
            price.relocate(10,50);
            content = new HBox(new ImageView(listOfImages.get(select)), vBox);
            select = (select == 4) ? 0 : ++select;
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(CustomThing item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                name.setText(item.getName());
                price.setText(item.getPrice());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

}
