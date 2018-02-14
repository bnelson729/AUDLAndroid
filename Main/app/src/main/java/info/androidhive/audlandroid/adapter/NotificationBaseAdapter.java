package info.androidhive.audlandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.model.NotificationItem;
import info.androidhive.audlandroid.utils.TeamIconLoader;

public class NotificationBaseAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<NotificationItem> data;
    private LayoutInflater inflater=null;
    public TeamIconLoader teamIconLoader;
    String serverURL;

    public NotificationBaseAdapter(Activity a, ArrayList<NotificationItem> d) {
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
            vi = inflater.inflate(R.layout.list_item_notification, null);

        TextView title = (TextView)vi.findViewById(R.id.name); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.icon); // thumb image
        CheckBox check_box=(CheckBox)vi.findViewById(R.id.checkBox); // thumb image

        NotificationItem notifItem = data.get(position);
 
        // Setting all values in listview
        title.setText(notifItem.getNotificationTitle().toUpperCase());
        title.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(), "Roboto-Bold.ttf"));
        title.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        thumb_image.setImageResource(teamIconLoader.get(notifItem.getTopic().toUpperCase()));

        check_box.setChecked(notifItem.getChecked());
        check_box.setTag(notifItem);
        return vi;
    }
}
