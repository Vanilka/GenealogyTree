package gentree.client.visualization.elements.configuration;

import gentree.common.configuration.enums.Race;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Created by Martyna SZYMKOWIAK on 15/11/2017.
 */
public class ElementsConfig {
    public static final ElementsConfig INSTANCE = new ElementsConfig();

    private final HashMap<Race, Color> raceConfigurator = new HashMap<>();
    private final HashMap<Race, ImageFiles> raceImage = new HashMap<>();

    private ElementsConfig() {
        initMap();
        initRaceImageMap();
    }

    private void initMap() {
        raceConfigurator.put(Race.HUMAIN, Color.GREEN);
        raceConfigurator.put(Race.WEREWOLF, Color.BROWN);
        raceConfigurator.put(Race.VAMPIRE, Color.RED);
    }

    private void initRaceImageMap() {
        raceImage.put(Race.HUMAIN, ImageFiles.HUMAIN);
        raceImage.put(Race.ALIEN, ImageFiles.ALIEN);
        raceImage.put(Race.FAIRY, ImageFiles.FAIRY);
        raceImage.put(Race.GENIE, ImageFiles.GENIE);
        raceImage.put(Race.GHOST, ImageFiles.GHOST);
        raceImage.put(Race.HYBRID, ImageFiles.HYBRID);
        raceImage.put(Race.IM_FRIEND, ImageFiles.IM_FRIEND);
        raceImage.put(Race.MERMAID, ImageFiles.MERMAID);
        raceImage.put(Race.SERVO, ImageFiles.SERVO);
        raceImage.put(Race.WHITCH, ImageFiles.WHITCH);
        raceImage.put(Race.WEREWOLF, ImageFiles.WEREWOLF);
        raceImage.put(Race.ZOMBI, ImageFiles.ZOMBI);
        raceImage.put(Race.VEGE, ImageFiles.VEGE);
        raceImage.put(Race.VAMPIRE, ImageFiles.VAMPIRE);
    }



    public String getFilePathOfRace(Race race) {
        if(raceImage.containsKey(race)) return raceImage.get(race).toString();
        return raceImage.get(Race.HUMAIN).toString();
    }

    public Color findColor(Race race) {
        if(raceConfigurator.containsKey(race)) {
            return raceConfigurator.get(race);
        }
        return Color.GREEN;
    }



}
