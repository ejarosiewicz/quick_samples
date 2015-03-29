package com.ej.quicksamples.explorer;

/**
 * Interface of FileOpsListener.
 *
 * @author Emil Jarosiewicz
 */
public interface FilesOpsListener {

    public void onListLoaded();

    public void onItemGet(String string);

}
