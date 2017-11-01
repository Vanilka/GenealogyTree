package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.domain.Member;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 03/07/2017.
 */

@Log4j2
public class DialogAddMemberController implements Initializable, FXMLController, FXMLPane, FXMLDialogController {


    private static final String PREFIX_FILE_LOAD = "file://";

    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private AnchorPane DEATH_CAUSE_PANE;

    @FXML
    private JFXTextField SIM_NAME_FIELD;

    @FXML
    private JFXTextField SIM_SURNAME_FIELD;

    @FXML
    private JFXTextField SIM_BORNNAME_FIELD;

    @FXML
    private ImageView PHOTO_IMV;

    @FXML
    private JFXRadioButton SIM_SEX_MALE_CHOICE;

    @FXML
    private JFXRadioButton SIM_SEX_FEMALE_CHOICE;

    @FXML
    private ComboBox<Age> AGE_COMBO_BOX;

    @FXML
    private ComboBox<Race> RACE_COMBO_BOX;

    @FXML
    private ComboBox<DeathCauses> DEATH_CAUSE_COMBO_BOX;

    @FXML
    private JFXToggleButton ALIVE_TOGGLE_BUTTON;

    @FXML
    private JFXButton BUTTON_CANCEL;

    @FXML
    private JFXButton CONFIRM_BUTTON;

    private Stage stage;
    private ToggleGroup toggleGroupSex;
    private String path;

    {
        toggleGroupSex = new ToggleGroup();
        path = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
        initListeners();
        createSexToogleGroupe();
        populateAgeComboBox();
        populateRaceComboBox();
        populateDeathCauseComboBox();
        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }


    public void cancel() {
        this.stage.close();
    }

    public void confirm() {

        Member member = new Member(this.SIM_NAME_FIELD.getText(), this.SIM_SURNAME_FIELD.getText(), this.SIM_BORNNAME_FIELD.getText(), path,
                AGE_COMBO_BOX.getSelectionModel().getSelectedItem(),
                (Gender) toggleGroupSex.getSelectedToggle().getUserData(),
                RACE_COMBO_BOX.getSelectionModel().getSelectedItem(),
                ALIVE_TOGGLE_BUTTON.isSelected(),
                ALIVE_TOGGLE_BUTTON.isSelected() ? null : DEATH_CAUSE_COMBO_BOX.getSelectionModel().getSelectedItem());

        ServiceResponse response = context.getService().addMember(member);
        System.out.println(response);

        if (response.getStatus().equals(ServiceResponse.ResponseStatus.OK)) {
            this.stage.close();
        }
    }


    public void choosePhoto(MouseEvent event) {
        if (event.getClickCount() == 2) {
            File file = sm.openImageFileChooser();
            if (file != null) {
                try {
                    path = PREFIX_FILE_LOAD.concat(file.getCanonicalPath());
                    this.PHOTO_IMV.setImage(new Image(path));
                } catch (Exception e) {
                    log.error(LogMessages.MSG_ERROR_LOAD_IMAGE);
                    e.printStackTrace();
                }
            }
        }
    }


    private void createSexToogleGroupe() {
        this.SIM_SEX_MALE_CHOICE.setToggleGroup(this.toggleGroupSex);
        this.SIM_SEX_MALE_CHOICE.setUserData(Gender.M);
        this.SIM_SEX_FEMALE_CHOICE.setToggleGroup(this.toggleGroupSex);
        this.SIM_SEX_FEMALE_CHOICE.setUserData(Gender.F);
        this.toggleGroupSex.selectToggle(SIM_SEX_MALE_CHOICE);
    }

    private void populateAgeComboBox() {
        AGE_COMBO_BOX.getItems().addAll(Age.values());
        AGE_COMBO_BOX.getSelectionModel().select(Age.YOUNG_ADULT);
    }

    private void populateRaceComboBox() {
        RACE_COMBO_BOX.getItems().addAll(Race.values());
        RACE_COMBO_BOX.getSelectionModel().select(Race.HUMAIN);
    }

    private void populateDeathCauseComboBox() {
        DEATH_CAUSE_COMBO_BOX.getItems().addAll(DeathCauses.values());
        DEATH_CAUSE_COMBO_BOX.getSelectionModel().select(DeathCauses.NATURAL);
    }


    /*
        INIT LISTENERS
     */

    private void initListeners() {
        initLanguageListener();
        initAliveListener();
        initSexListener();
    }

    private void initAliveListener() {
        ALIVE_TOGGLE_BUTTON.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ALIVE_TOGGLE_BUTTON.setText("Alive");
                DEATH_CAUSE_PANE.setVisible(false);

            } else {
                ALIVE_TOGGLE_BUTTON.setText("Mort");
                DEATH_CAUSE_PANE.setVisible(true);
            }
        });

        ALIVE_TOGGLE_BUTTON.setSelected(true);
    }

    private void initSexListener() {
        toggleGroupSex.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (path == null) {
                if (newValue.getUserData().equals(Gender.M)) {
                    PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
                } else {
                    PHOTO_IMV.setImage(new Image(ImageFiles.GENERIC_FEMALE.toString()));
                }
            }
        });
    }

    /*
    *  LISTEN LANGUAGE CHANGES
    */
    private void initLanguageListener() {
        this.languageBundle.addListener((observable, oldValue, newValue) -> reloadElements());
    }

    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // Nothing to do
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
