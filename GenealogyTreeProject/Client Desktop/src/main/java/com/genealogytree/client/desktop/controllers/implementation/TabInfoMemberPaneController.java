package com.genealogytree.client.desktop.controllers.implementation;


import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.configuration.enums.ImageFiles;
import com.genealogytree.client.desktop.controllers.FXMLTab;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 26/12/2016.
 */
public class TabInfoMemberPaneController implements Initializable, FXMLTab {

    private static final Logger LOG = LogManager.getLogger(TabInfoMemberPaneController.class);
    public static final ScreenManager sc = ScreenManager.getInstance();
    public static final ContextGT context = ContextGT.getInstance();

    @FXML
    private AnchorPane infoMemberTab;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simSex;

    @FXML
    private JFXTextField simAge;

    @FXML
    private ImageView simPhoto;

    @FXML
    private JFXButton buttonCancel;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private Tab tab;
    private JFXTabPane tabPane;

    private ObjectProperty<GTX_Member> member;

    {
        member = new SimpleObjectProperty<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoLog("Initialisation : " + this.toString());
        this.languageBundle.setValue(rb);

        this.simNameField.setEditable(false);
        this.simSurnameField.setEditable(false);
        this.simSex.setEditable(false);
        this.simAge.setEditable(false);
        addMemberChangeListener();
        addLanguageListener();

    }

    @FXML
    void buttonCancel(ActionEvent event) {
        this.tabPane.getTabs().remove(tab);
    }

    public void addMemberChangeListener() {
        this.member.addListener(new ChangeListener<GTX_Member>() {
            @Override
            public void changed(ObservableValue<? extends GTX_Member> observable, GTX_Member oldValue, GTX_Member newValue) {
                if (newValue != null) {
                    simNameField.setText(member.getValue().getName());
                    simSurnameField.setText(member.getValue().getSurname());
                    simSex.setText(member.getValue().getSex().toString());
                    simAge.setText(member.getValue().getAge().toString());
                    Image img = null;
                    try {
                        String path = member.getValue().getPhoto();
                        img = new Image(path);
                    } catch (Exception e) {
                        img = new Image(ImageFiles.GENERIC_MALE.toString());
                    } finally {
                        simPhoto.setImage(img);

                    }

                }
            }
        });
    }

    /*
    *LISTEN LANGUAGE CHANGES
    */
    private void addLanguageListener() {
        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {
            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }


    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        // TODO
    }

    /*
    * GETTERS AND SETTERS
    */

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }

    public GTX_Member getMember() {
        return member.get();
    }

    public void setMember(GTX_Member member) {
        this.member.set(member);
    }

    public ObjectProperty<GTX_Member> memberProperty() {
        return member;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}
