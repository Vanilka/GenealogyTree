package gentree.client.desktop.service;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.configurations.helper.BorderPaneReloadHelper;
import gentree.client.desktop.configurations.messages.AppTitles;
import gentree.client.desktop.controllers.FXMLDialogController;
import gentree.client.desktop.controllers.FXMLPane;
import gentree.client.desktop.controllers.FXMLTab;
import gentree.client.desktop.controllers.screen.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Getter
@Setter
@Log4j2
public class ScreenManager {

    public static final ScreenManager INSTANCE = new ScreenManager();
    public static final GenTreeContext context = GenTreeContext.INSTANCE;

    private static final FileChooser imgFileChooser = new FileChooser();
    private static final FileChooser xmlFileChooser = new FileChooser();
    private static String LAST_PATH;
    BorderPaneReloadHelper bpHelper;

    /*
        Commons controllers
     */
    private ScreenMainController screenMainController;
    private MainMenuController mainMenuController;
    private MainFooterController mainFooterController;
    private ScreenWelcomeController screenWelcomeController;
    /*
        Commons Panes
     */
    private ScreenMainRightController screenMainRightController;
    private BorderPane mainWindowBorderPane;
    private Stage stage;
    private Scene scene;

    public ScreenManager() {
        bpHelper = new BorderPaneReloadHelper();
        LAST_PATH = System.getProperty("user.home");
    }


    public void init() {
        initRoot();
        loadFxml(mainFooterController, this.screenMainController.getRootBorderPane(), FilesFXML.MAIN_FOOTER_FXML, Where.BOTTOM);
        loadFxml(mainMenuController, this.screenMainController.getRootBorderPane(), FilesFXML.MAIN_MENU_FXML, Where.TOP);
        loadFxml(screenWelcomeController, this.screenMainController.getRootBorderPane(), FilesFXML.SCREEN_WELCOME_FXML, Where.CENTER);


        this.scene = new Scene(mainWindowBorderPane);
        this.stage.setScene(this.scene);
        this.stage.setTitle(AppTitles.APP_TITLE);
        this.stage.setHeight(750);
        this.stage.setWidth(1200);
        this.stage.setMinHeight(750);
        this.stage.setMinWidth(1200);
        this.stage.show();
    }

    private void initRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FilesFXML.ROOT_WINDOW_FXML.toString()));
        try {
            this.mainWindowBorderPane = ((BorderPane) loader.load());
            this.screenMainController = loader.getController();
            this.screenMainController.setRootBorderPane(this.mainWindowBorderPane);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }
    }


    public void loadFxml(FXMLPane controller, BorderPane border, FilesFXML fxml, Where where) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            AnchorPane temp = (AnchorPane) loader.load();
            controller = loader.getController();
            switch (where) {
                case TOP:
                    border.setTop(temp);
                    break;
                case CENTER:
                    bpHelper.before(border);
                    border.setCenter(temp);
                    bpHelper.after(border);
                    break;
                case BOTTOM:
                    border.setBottom(temp);
                    break;
            }

        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }

    }

    public void showNewDialog(FXMLDialogController controller, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());

        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getStage());
            Scene scene = new Scene(dialogwindow);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();


        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }

    }

    public FXMLPane loadFxml(FXMLPane controller, Pane pane, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            pane.getChildren().clear();
            pane.getChildren().addAll((Pane) loader.load());
            controller = loader.getController();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }
        return controller;
    }


    public FXMLPane loadFxml(FXMLPane controller, AnchorPane anchor, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            anchor.getChildren().clear();
            anchor.getChildren().addAll((AnchorPane) loader.load());
            controller = loader.getController();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }
        return controller;
    }

    public FXMLTab loadFxml(FXMLTab controller, JFXTabPane tabPane, Tab tab, FilesFXML fxml, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            tab.setContent(loader.load());
            tab.setText(title);
            controller = loader.getController();
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            controller.setTabAndTPane(tabPane, tab);


        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(ex.getCause());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }

        return controller;
    }

    public FXMLTab loadFxml(FXMLTab controller, JFXTabPane tabPane, Tab tab, String fxml) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), context.getBundleValue());
        try {
            tab.setContent(loader.load());
            controller = loader.getController();
            controller.setTabAndTPane(tabPane, tab);

        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }

        return controller;
    }

    public void showNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(10))
                .darkStyle()
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.show();
    }

    public File openImageFileChooser() {
        configureImageFileChooser(imgFileChooser);
        File file = imgFileChooser.showOpenDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;

    }

    public File openXMLFileChooser() {
        configureXmlFileChooser(xmlFileChooser);
        File file = xmlFileChooser.showOpenDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;
    }

    public File saveXMLFileChooser() {
        configureXmlFileChooser(xmlFileChooser);
        File file = xmlFileChooser.showSaveDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;
    }

    private void configureImageFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle(context.getBundleValue().getString("select_image_dialog"));
        fileChooser.setInitialDirectory(
                new File(LAST_PATH)
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void configureXmlFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle(context.getBundleValue().getString("select_xml_dialog"));
        fileChooser.setInitialDirectory(
                new File(LAST_PATH)
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
    }

    /*
        Register Screen
     */

    public void register(ScreenMainRightController controller) {
        this.screenMainRightController = controller;
    }

    public enum Where {
        TOP,
        CENTER,
        BOTTOM
    }


}
