package info.androidhive.audlandroid.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.utils.ImageLoader;

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> listData;
    private LayoutInflater inflater=null;

    public ListAdapter(Activity a, List<String> d) {
        activity = a;
        listData = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return listData.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_simple, null);

        TextView text = (TextView)vi.findViewById(R.id.text); // answer

        // Setting all values in listview
        text.setText(listData.get(position));
        text.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(), "Roboto-Medium.ttf"));
        text.setTextColor(activity.getResources().getColor(R.color.dark_blue));
        return vi;
    }
}
