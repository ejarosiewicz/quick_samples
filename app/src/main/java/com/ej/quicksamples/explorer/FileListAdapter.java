package com.ej.quicksamples.explorer;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;


import com.ej.quicksamples.R;
import com.ej.quicksamples.utils.Constants;




/**
 * Adapter of File's lits. It contains list of file's paths.
 *
 * @author Emil Jarosiewicz
 */
public class FileListAdapter extends BaseAdapter {

    /**
     * Data is represents by list of pairs, which contains file name, and directory.
     */
    private ArrayList<Pair<String, String>> mData = new ArrayList<Pair<String, String>>();
    private ArrayList<Pair<String, String>> orig = new ArrayList<Pair<String, String>>();
    private LayoutInflater mInflater;
    private TreeSet<Integer> mHeaderSet = new TreeSet<Integer>();
    private String dir;
    private boolean filterMode = false;


    /**
     * Getter of directory.
     *
     * @return directory of file.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Setter of directory.
     *
     * @param dir new directory.
     */
    public void setDir(String dir) {
        this.dir = dir;
    }


    /**
     * Default constructor of File List Adapter.
     *
     * @param context current context of application.
     */
    public FileListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     * Adds item to the data list.
     *
     * @param item added item
     */
    public void addItem(final Pair<String, String> item) {
        mData.add(item);
        notifyDataSetChanged();
    }


    /**
     * Adds headet to the list with signification on the Header set.
     *
     * @param item
     */
    public void addSectionHeaderItem(final Pair<String, String> item) {
        mData.add(item);
        mHeaderSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    /**
     * Gets type of the element: item or header.
     *
     * @param position position of item on the list
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return (mHeaderSet.contains(position) && !filterMode) ? Constants.TYPE_HEADER : Constants.TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Returns header or item view according to the filter mode
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (filterMode) return getWithoutHeaderView(position, convertView, parent);
        else return getWithHeaderView(position, convertView, parent);
    }


    /**
     * Gets items with header.
     *
     * @param position    position on the list
     * @param convertView converted view
     * @param parent      parent of view
     * @return view of list
     */
    private View getWithHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);
        boolean create = true;
        if (convertView != null) {
            //checks, if view holder is instance of header or item
            holder = (ViewHolder) convertView.getTag();
            if (holder instanceof ViewHolderItem)
                create = mHeaderSet.contains(position) ? true : false;
            else if (holder instanceof ViewHolderHeader)
                create = mHeaderSet.contains(position) ? false : true;
        }
        if (create) {
            switch (type) {
                case Constants.TYPE_HEADER:
                    holder = new ViewHolderHeader();
                    convertView = mInflater.inflate(R.layout.list_header, null);
                    break;
                case Constants.TYPE_ITEM:
                    holder = new ViewHolderItem();
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    break;
            }
        }
        holder.textView = (TextView) convertView.findViewById(R.id.textView);
        holder.textView.setText(mData.get(position).second);
        return convertView;
    }

    /**
     * Gets view without headers.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private View getWithoutHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        holder = new ViewHolderItem();
        convertView = mInflater.inflate(R.layout.list_item, null);
        holder.textView = (TextView) convertView.findViewById(R.id.textView);
        holder.textView.setText(mData.get(position).second);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

    public static class ViewHolderHeader extends ViewHolder {
    }

    public static class ViewHolderItem extends ViewHolder {
    }


    /**
     * Getter of search filter.
     *
     * @return new filter of seach
     */
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                ArrayList<Pair<String, String>> results = new ArrayList<Pair<String, String>>();
                if (orig == null || orig.size() == 0)
                    orig = new ArrayList<Pair<String, String>>(mData);
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        int i = 0;
                        if (constraint.toString().equals("")) {
                            filterMode = false;
                            results = orig;
                        } else {
                            filterMode = true;
                            for (final Pair<String, String> pair : orig) {
                                if (pair.second.contains(constraint.toString())
                                        && !mHeaderSet.contains(i))
                                    results.add(pair);
                                i++;
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mData = (ArrayList<Pair<String, String>>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
