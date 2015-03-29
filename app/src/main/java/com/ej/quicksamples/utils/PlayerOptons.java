package com.ej.quicksamples.utils;

import java.io.Serializable;

/**
 * Contains the sound range (start and end position) of the sound file.
 *
 * @author Emil Jarosiewicz
 */

public class PlayerOptons implements Serializable {
    private int startPosition;
    private int endPosition;

    public PlayerOptons(){};

    public PlayerOptons(int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
