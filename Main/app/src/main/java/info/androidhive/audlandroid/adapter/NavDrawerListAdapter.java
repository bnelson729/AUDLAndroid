package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.model.NavDrawerItem;
import info.androidhive.audlandroid.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {

    private Activity activity;
	private Context context;
	private ArrayList<String> navDrawerItems;
	
	public NavDrawerListAdapter(Activity a, Context context, ArrayList<String> navDrawerItems) {
        activity = a;
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_drawer, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        txtTitle.setText(navDrawerItems.get(position));
        txtTitle.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(), "Roboto-Bold.ttf"));

        
        return convertView;
	}

}
