package com.genealogytree.configuration;

/**
 * Created by vanilka on 27/12/2016.
 */
public enum ImageFiles {
    NO_NAME_MALE("NoNameMale.png"),
    NO_NAME_FEMALE("NoNameFemale.png"),
    GENERIC_MALE("GenericMale.png"),
    GENERIC_FEMALE("GenericFemale.png"),
    TEST_BACKGROUND("TestBackground.png");

    private final String path = "/com/genealogytree/sources/backgrounds/";
    private String file;

    private ImageFiles(String file) {
        this.file =  path.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }
}
