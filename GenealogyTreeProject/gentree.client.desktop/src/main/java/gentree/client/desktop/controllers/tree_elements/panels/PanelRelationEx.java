package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.controllers.tree_elements.connectors.RelationExConnector;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class PanelRelationEx extends SubBorderPane implements RelationPane {

    private final static double MARGIN_TOP = 0.0;
    private final static double MARGIN_LEFT = 0.0;
    private final static double MARGIN_RIGHT = 0.0;
    private final static double MARGIN_BOTTOM = 50.0;

    private final static double PADDING_TOP = 10.0;
    private final static double PADDING_LEFT = 10.0;
    private final static double PADDING_RIGHT = 10.0;
    private final static double PADDING_BOTTOM = 0.0;

    private final static double MINIMAL_RELATION_WIDTH = 550.0;
    private final static double SPACE_BEETWEN_OBJECTS = 100.0;

    private final AnchorPane relation;
    private final HBox childrenBox;

    private final FamilyMember spouseCard;
    private final RelationTypeElement relationTypeElement;

    private final ObjectProperty<RelationType> relationType;
    private final ObjectProperty<Member> spouse;
    private final ObservableList<PanelChild> children;
    private final RelationExConnector childrenConnector;


    {
        relation = new AnchorPane();
        childrenBox = new HBox();
        spouseCard = new FamilyMember();
        relationTypeElement = new RelationTypeElement();
        relationType = new SimpleObjectProperty<>();
        spouse = new SimpleObjectProperty<>();
        children = FXCollections.observableArrayList();
        childrenConnector = new RelationExConnector(this);
    }

    public PanelRelationEx() {
        this(null, RelationType.NEUTRAL, null);
    }

    public PanelRelationEx(Member spouse, RelationType type) {
        this(spouse, type, null);
    }

    public PanelRelationEx(Member spouse, RelationType type, SubBorderPane parent) {
        initPanes();
        initListeners();
        this.spouse.setValue(spouse);
        this.relationType.setValue(type);
        this.initBorder(Color.BROWN, this);
        relation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        initBorder(Color.CHOCOLATE, relation);
        initBorder(Color.GREEN, childrenBox);

    }


    private void initPanes() {
        initHbox();
        this.setCenter(childrenBox);
        this.setTop(relation);
        relation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        setMargin(relation, new Insets(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT));

    }

    private void initHbox() {
        childrenBox.setSpacing(10);
        childrenBox.setAlignment(Pos.TOP_RIGHT);
    }


    @Override
    public void addChild(PanelChild child) {
        children.add(child);
        child.setParentPane(this);
    }

    /*
        Listeners
     */


    private void initListeners() {
        initSpouseListener();
        initChildrenListener();
        initRelationTypeListener();
        initRelationElementsPositionListener();
    }


    private void initSpouseListener() {
        spouse.addListener((observable, oldValue, newValue) -> {
            relation.getChildren().removeAll();
            if (newValue != null) {
                spouseCard.setMember(newValue);
                relation.getChildren().addAll(spouseCard, relationTypeElement);
            }
        });
    }

    private void initRelationTypeListener() {
        relationType.addListener((observable, oldValue, newValue) -> {
            relationTypeElement.setType(newValue == null ? RelationType.NEUTRAL : newValue);
        });
    }

    private void initChildrenListener() {
        children.addListener((ListChangeListener<PanelChild>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    childrenBox.getChildren().addAll(c.getAddedSubList());
                    c.getAddedSubList().forEach(childrenConnector::addPanelChild);
                } else if (c.wasRemoved()) {
                    childrenBox.getChildren().removeAll(c.getRemoved());
                    c.getRemoved().forEach(childrenConnector::removePanelChild);
                }
            }
        });
    }

    private void initRelationElementsPositionListener() {

        childrenBox.prefWidthProperty().bindBidirectional(relation.prefWidthProperty());

        relation.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (relationTypeElement != null) {
                relationTypeElement.setLayoutY((newValue.doubleValue() - relationTypeElement.getHeight() - MARGIN_BOTTOM - PADDING_TOP) / 2);
                calculateRelationElementsPosition();
            }
        });

        relation.widthProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });


        childrenConnector.getLine().startXProperty().addListener(c -> {
            calculateRelationElementsPosition();
            resizeRelation();

        });

        childrenConnector.getLine().endXProperty().addListener((observable, oldValue, newValue) -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });

        relationTypeElement.boundsInLocalProperty().addListener(c -> {
            calculateRelationElementsPosition();
            resizeRelation();
        });

    }

    private void calculateRelationElementsPosition() {
        if (children.size() > 0) {
            Line line = childrenConnector.getLine();
            Double offset = line.getEndX() - relationTypeElement.getWidth()/2.0 - 10.0;
            relationTypeElement.setLayoutX(offset);
            offset = offset - spouseCard.getWidth() - SPACE_BEETWEN_OBJECTS;
            spouseCard.setLayoutX(offset);
            childrenConnector.connectRelationToChildren(relationTypeElement);

        } else {
            relationTypeElement.setLayoutX(SPACE_BEETWEN_OBJECTS);
        }
    }

    private void resizeRelation() {

        if(spouseCard.getLayoutX() < 0) {
            relation.setPrefWidth(MINIMAL_RELATION_WIDTH);
        }
    }


}

