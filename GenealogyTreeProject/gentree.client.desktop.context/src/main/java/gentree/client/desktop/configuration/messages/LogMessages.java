package gentree.client.desktop.configuration.messages;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
public class LogMessages {

    public static final String MSG_CTRL_INITIALIZATION = "Initialization controller";
    public static final String MSG_CTRL_INITIALIZED = "Controller Initialized correctly";

    public static final String MSG_FAMILY_SERVICE_CURRENT_FAMILY = "Set Current Family [{}]";

    public static final String MSG_MEMBER_ADD_NEW = "Add new Member [{}]";
    public static final String MSG_RELATION_ADD_NEW = "Add new Relation [{}]";
    public static final String MSG_MEMBER_UPDATE = "Update Member [{}]";

    public static final String MSG_RELATION_VERIF_EXIST_BORN = "Verification Existing of Born Relation";
    public static final String MSG_RELATION_VERIF_EXIST_BORN_FOR = "Verification Existing of Born Relation for [{}]";
    public static final String MSG_RELATION_BORN = "Born Relation [{}]";
    public static final String MSG_ERROR_BORN = "Member [{}] has not an unique relation";

    public static final String MSG_SERVICE_INITIALIZATION = "Service Initialization";


    public static final String MSG_NO_CONFIG_FILE = "Cannot find config file. It will be created.";
    public static final String MSG_READ_CONFIG_FILE = "Read config file";
    public static final String MSG_DIR_NOT_EXIST = "Mandatory directory {{}} not exist. It will be created";
    public static final String MSG_MISSING_PROPERTY = "The property {{}} is missing in config file. The default value will be used";

    public static final String MSG_ERROR_LOAD_IMAGE = "Error while load image";
    public static final String MSG_NO_STRONG_PERSON = "Cannot fins strong person for Relation. Should never happens";
    public static final String MSG_MERGING_RELATIONS = "Relation {{}} will be merge to {{}}";
    public static final String MSG_AFTER_MERGE = "Merging Relation result -> {{}}";

    public static final String MSG_POST_REQUEST = "POST REQUEST : [{}]";
}
