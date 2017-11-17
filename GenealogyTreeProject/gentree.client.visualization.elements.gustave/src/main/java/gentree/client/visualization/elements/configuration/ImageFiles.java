package gentree.client.visualization.elements.configuration;

/**
 * Created by vanilka on 27/12/2016.
 */
public enum ImageFiles {
    NO_NAME_MALE("backgrounds/NoNameMale.png"),
    NO_NAME_FEMALE("backgrounds/NoNameFemale.png"),
    GENERIC_MALE("backgrounds/GenericMale.png"),
    GENERIC_FEMALE("backgrounds/GenericFemale.png"),
    EQUAL_TRIANGLE("backgrounds/equalTrangle.png"),
    RELATION_NEUTRAL("backgrounds/neutral.png"),
    RELATION_MARRIED("backgrounds/maried.png"),
    RELATION_FIANCE("backgrounds/fiance.png"),
    NEW_ADDITION("backgrounds/new.png"),
    RELATION_LOVE("backgrounds/love.png"),
    HUMAIN("races/sim.png"),
    VAMPIRE("races/Vampire.png"),
    VEGE("races/plant.png"),
    ALIEN("races/Alien.png"),
    GHOST("races/fairy.png"),
    ZOMBI("races/zombi.png"),
    SERVO("races/Servo.png"),
    WEREWOLF("races/Werewolf.png"),
    GENIE("races/Genie.png"),
    WHITCH("races/which.png"),
    FAIRY("races/fairy.png"),
    IM_FRIEND("races/imfriend.png"),
    MERMAID("races/Mermaid.png"),
    HYBRID("races/hybrid.png");

    private final String path = "/layout/images/";
    private String file;

    private ImageFiles(String filePath) {

        file = path.concat(filePath);
    }

    @Override
    public String toString() {
        return file;
    }
}

