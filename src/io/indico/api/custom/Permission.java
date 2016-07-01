package io.indico.api.custom;

/**
 * Created by Chris on 7/1/16.
 */
public enum Permission {
    WRITE,
    READ;

    public String toString() {
        return this.name().toLowerCase();
    }
}
