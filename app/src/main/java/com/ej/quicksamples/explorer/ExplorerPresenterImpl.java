package com.ej.quicksamples.explorer;

/**
 * Implementation of Explorer's Presenter.
 *
 * @author Emil Jarosiewicz
 */
public class ExplorerPresenterImpl implements ExplorerPresenter , FilesOpsListener{

    private ExplorerController explorerController;
    private ExplorerActivityView explorerActivityView;

    public ExplorerPresenterImpl(ExplorerActivityView explorerActivityView){
        this.explorerActivityView=explorerActivityView;
        explorerController=new ExplorerControllerImpl();
    }

    /**
     * Initialization ops.
     *
     * @param fileListAdapter File List Adapter
     * @see FileListAdapter
     */
    @Override
    public void onInit(FileListAdapter fileListAdapter) {
        explorerController.onInit(this, fileListAdapter);
    }


    /**
     * Item Click actions.
     *
     * @param position position on the list.
     * @param fileListAdapter File list adapter.
     *                        @see FileListAdapter
     *
     */
    @Override
    public void onItemClick(int position, FileListAdapter fileListAdapter) {
        explorerController.onItemClick(position, fileListAdapter, this);
    }

    /**
     * Operations after list load.
     */
    @Override
    public void onListLoaded() {
        explorerActivityView.loadFileList();
    }


    /**
     * Getting path from the list.
     * @param string path of file.
     */
    @Override
    public void onItemGet(String string) {
        explorerActivityView.itemClickFinish(string);
    }

}
