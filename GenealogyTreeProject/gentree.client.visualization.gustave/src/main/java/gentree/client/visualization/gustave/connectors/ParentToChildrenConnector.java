package gentree.client.visualization.gustave.connectors;

import gentree.client.visualization.gustave.panels.PanelChild;
import gentree.client.visualization.gustave.panels.PanelRelationCurrent;
import gentree.client.visualization.gustave.panels.PanelRelationEx;
import gentree.client.visualization.gustave.panels.SubRelationPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;

import java.util.Comparator;

/**
 * Created by Martyna SZYMKOWIAK on 20/08/2017.
 */
public class ParentToChildrenConnector extends LineConnector {

    /*
    *  Parent pane for this Connector
    */
    private SubRelationPane subBorderPane;

    /*
    *   Child Connectors for children in Sub-Relation-Pane
    */

    private final BetweenChildrenConnector betweenChildrenConnector;


    /**
     * SubRelationPane element is the parent for this Connector
     *
     * @param subBorderPane
     */
    public ParentToChildrenConnector(SubRelationPane subBorderPane) {
        super();
        this.subBorderPane = subBorderPane;
        this.subBorderPane.getChildren().add(0, getLine());
        this.betweenChildrenConnector = new BetweenChildrenConnector(subBorderPane);
        initListeners();
    }


    private void initListeners() {
        betweenChildrenConnector.getLine().boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            connectRelationToChildren();
        });

        betweenChildrenConnector.getLine().boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            connectRelationToChildren();
        });

        subBorderPane.getConnectionNode().boundsInLocalProperty().addListener((observable -> {
            connectRelationToChildren();
        }));

        subBorderPane.getConnectionNode().boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            connectRelationToChildren();
        });

        getLine().visibleProperty().bind(betweenChildrenConnector.isListEmpty().not());


    }


    public void addPanelChild(PanelChild child) {
        betweenChildrenConnector.addPanelChild(child);
    }

    public void removePanelChild(PanelChild child) {
        betweenChildrenConnector.removePanelChild(child);
    }


    /**
     * Drawing line beetween parent/ relation and connection point
     *
     */
    private void connectRelationToChildren() {
        if ( betweenChildrenConnector.isListEmpty().not().get()) {
            if (subBorderPane instanceof PanelRelationCurrent) {
                connectToLeft();
            } else if (subBorderPane instanceof PanelRelationEx) {
                connectToRight();
            } else {
                connectToCenter();
            }
        }
    }


    private void connectToLeft() {
        drawConnector(subBorderPane.getConnectionNode(), betweenChildrenConnector.getLine().getStartX(), betweenChildrenConnector.getLine().getStartY());
    }

    private void connectToRight() {
        drawConnector(subBorderPane.getConnectionNode(), betweenChildrenConnector.getLine().getEndX(), betweenChildrenConnector.getLine().getEndY());
    }

    private void connectToCenter() {
        Double middle = (betweenChildrenConnector.getLine().getStartX() + betweenChildrenConnector.getLine().getEndX())/2;
        drawConnector(subBorderPane.getConnectionNode(), middle, betweenChildrenConnector.getLine().getStartY());
    }


    /**
     * Drawing line between Node and children point
     *
     * @param n
     * @param startX
     * @param startY
     */
    private void drawConnector(Node n, Double startX, Double startY) {

        Bounds b = getRelativeBounds(n);
        Point2D bottomPoint = getBottomPoint(b);

        setLineCoordinates(getLine(), startX, startY, bottomPoint.getX(), bottomPoint.getY());
    }

    protected Bounds getRelativeBounds(Node node) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return subBorderPane.sceneToLocal(nodeBoundsInScene);
    }

    protected Bounds getRelativeBounds(Node node, Node relativeTo) {
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        return relativeTo.sceneToLocal(nodeBoundsInScene);
    }

}