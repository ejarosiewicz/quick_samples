package com.ej.quicksamples.utils;

import java.io.Serializable;

/**
 * Set of the buttons. Contains filename, filepath, and player options.
 *
 * @author Emil Jarosiewicz
 */
public class ButtonSet implements Serializable {

    private String name;
    private String path;
    private PlayerOptons playerOptons;

    public ButtonSet(String name, String path, PlayerOptons playerOptons) {
        this.name = name;
        this.path = path;
        this.playerOptons = playerOptons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PlayerOptons getPlayerOptons() {
        return playerOptons;
    }

    public void setPlayerOptons(PlayerOptons playerOptons) {
        this.playerOptons = playerOptons;
    }
}
