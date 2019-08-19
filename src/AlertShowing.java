
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

    /**
     * Class for show alerts
     */
    public class AlertShowing {
        /**
         * show alert
         * @param title message title
         * @param text message text
         */
        public static void showAlert(Pane pane, String title, String text){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.setTitle(title);
            alert.setContentText(text);
            alert.initOwner(pane.getScene().getWindow());
            alert.show();
        }
    }


