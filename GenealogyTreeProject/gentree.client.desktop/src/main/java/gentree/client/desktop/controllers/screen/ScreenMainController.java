package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLAnchorPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

@Log4j2
public class ScreenMainController implements Initializable, FXMLController, FXMLAnchorPane {

    @FXML
    @Setter
    @Getter
    private BorderPane rootBorderPane;

    @FXML
    private AnchorPane screenMainLeft;

    @FXML
    private AnchorPane screenMainRight;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);

        initPanes();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

    private void initPanes() {
        sm.loadFxml(new ScreenMainLeftController(), screenMainLeft, FilesFXML.SCREEN_MAIN_LEFT_FXML);
        sm.loadFxml(new ScreenMainRightController(), screenMainRight, FilesFXML.SCREEN_MAIN_RIGHT_FXML);
    }
}
