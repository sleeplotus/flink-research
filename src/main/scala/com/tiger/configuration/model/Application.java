package com.tiger.configuration.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 王澎
 */
@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class Application {
    private Profiles profiles;

    @Getter
    @Setter
    public static class Profiles {
        private String active;
    }
}


