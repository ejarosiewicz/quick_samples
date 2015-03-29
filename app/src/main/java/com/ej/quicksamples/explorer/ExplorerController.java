package com.ej.quicksamples.explorer;

/**
 * Interface of Explorer's Controller
 *
 * @author Emil Jarosiewicz
 */
public interface ExplorerController {


    public void onInit(FilesOpsListener filesOpsListener, FileListAdapter fileListAdapter);

    public void onItemClick(int position, FileListAdapter fileListAdapter, FilesOpsListener filesOpsListener);

}
