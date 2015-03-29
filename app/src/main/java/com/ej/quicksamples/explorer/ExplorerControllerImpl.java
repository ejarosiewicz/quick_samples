package com.ej.quicksamples.explorer;

import android.os.Environment;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;

import com.ej.quicksamples.utils.Tools;

/**
 * Implementation of Explorer's controller.
 *
 * @author Emil Jarosiewicz
 */
public class ExplorerControllerImpl implements ExplorerController {

    private ArrayList<Pair<String, ArrayList<String>>> fileList;


    /**
     * Initialization ops.
     *
     * @param filesOpsListener Listener for feedback
     * @param fileListAdapter  Adapter of File's list.
     * @see FileListAdapter
     */
    @Override
    public void onInit(FilesOpsListener filesOpsListener, FileListAdapter fileListAdapter) {
        fileList = new ArrayList<Pair<String, ArrayList<String>>>();
        searchFiles(Environment.getExternalStorageDirectory());
        addElementsToAdapter(fileListAdapter);
        filesOpsListener.onListLoaded();
    }


    @Override
    public void onItemClick(int position, FileListAdapter fileListAdapter, FilesOpsListener filesOpsListener) {
        Pair<String, String> itemFromList = (Pair<String, String>) fileListAdapter.getItem(position);
        filesOpsListener.onItemGet(itemFromList.first + "/" + itemFromList.second);
    }

    private void searchFiles(File dir) {
        if (dir.canRead()) {
            File[] list = dir.listFiles();
            if (list != null) {
                for (File fileInstance : list) {
                    if (!fileInstance.getName().startsWith(".")) {
                        if (fileInstance.isDirectory()) {
                            searchFiles(fileInstance);
                        } else {
                            if (Tools.isAudioFile(fileInstance))
                                addFileToList(dir.getAbsolutePath(), fileInstance.getName());
                        }
                    }
                }
            }
        }
    }

    private void addFileToList(String directory, String filename) {
        boolean dirExist = false;
        for (Pair<String, ArrayList<String>> dir : fileList) {
            if (dir.first.equals(directory)) {
                dirExist = true;
                dir.second.add(filename);
            }
        }
        if (!dirExist) {
            ArrayList<String> fileDirList = new ArrayList<String>();
            fileDirList.add(filename);
            fileList.add(new Pair<String, ArrayList<String>>(directory, fileDirList));
        }
    }

    private void addElementsToAdapter(FileListAdapter fileListAdapter) {
        for (Pair<String, ArrayList<String>> pair : fileList) {
            fileListAdapter.addSectionHeaderItem(new Pair<String, String>(pair.first, pair.first));
            for (String string : pair.second)
                fileListAdapter.addItem(new Pair<String, String>(pair.first, string));
        }
    }

}
