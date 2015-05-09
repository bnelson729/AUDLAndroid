package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;

import java.util.HashMap;
import java.util.List;
 
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context context;
    private LayoutInflater inflater;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataVal;
 
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, HashMap<String, List<String>> listDataVal) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listDataVal = listDataVal;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
    
    public Object getChildVal(int groupPosition, int childPosition) {
    	return this._listDataVal.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
        final String childVal = (String) getChildVal(groupPosition, childPosition);
 
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_stats_value, null);
        }
 
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        TextView txtListVal = (TextView) convertView
                .findViewById(R.id.lblListItemVal);
        
 
        txtListChild.setText(childText);
        txtListChild.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "Roboto-Bold.ttf"));
        txtListChild.setTextColor(context.getResources().getColor(R.color.dark_blue));
        txtListVal.setText(childVal);
        txtListVal.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "Roboto-Medium.ttf"));
        txtListVal.setTextColor(context.getResources().getColor(R.color.light_gray));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_stats_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        String headerTitle = (String) getGroup(groupPosition);
        lblListHeader.setText(headerTitle.toUpperCase());
        lblListHeader.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "Roboto-Bold.ttf"));
        lblListHeader.setTextColor(context.getResources().getColor(R.color.white_text));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}