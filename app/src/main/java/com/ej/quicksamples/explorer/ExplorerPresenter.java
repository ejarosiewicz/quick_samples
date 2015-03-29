package com.ej.quicksamples.explorer;


/**
 * Interface of Explorer's presenter
 *
 * @author Emil Jarosiewicz
 */
public interface ExplorerPresenter {

    public void onInit(FileListAdapter fileListAdapter);

    public void onItemClick(int position, FileListAdapter fileListAdapter);


}
