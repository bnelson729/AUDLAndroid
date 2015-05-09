package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.model.TeamsListItem;
import info.androidhive.audlandroid.utils.ImageLoader;
import info.androidhive.audlandroid.utils.TeamIconLoader;

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

public class TeamsListBaseAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<TeamsListItem> data;
    private LayoutInflater inflater=null;
    public TeamIconLoader teamIconLoader;
    String serverURL;
    
    public TeamsListBaseAdapter(Activity a, ArrayList<TeamsListItem> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamIconLoader=new TeamIconLoader();
        serverURL = a.getResources().getString(R.string.ServerURL);
    }
    
    public int getCount() {
        return data.size();
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
            vi = inflater.inflate(R.layout.list_item_team, null);
 
        TextView title = (TextView)vi.findViewById(R.id.name); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.icon); // thumb image
 
        TeamsListItem team = data.get(position);
 
        // Setting all values in listview
        title.setText(team.getTeamName().toUpperCase());
        title.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(), "Roboto-Bold.ttf"));
        title.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        thumb_image.setImageResource(teamIconLoader.get(team.getTeamAbbrev().toUpperCase()));
        return vi;
    }
}
