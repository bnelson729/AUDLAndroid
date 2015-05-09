package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.model.Tweet;
import info.androidhive.audlandroid.model.TwitterUser;
import info.androidhive.audlandroid.utils.ImageLoader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NowListBaseAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<Tweet> data;
    private LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    
    public NowListBaseAdapter(Activity a, ArrayList<Tweet> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
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
        View twit =convertView;
        if(convertView==null)
            twit = inflater.inflate(R.layout.list_item_tweet, null);
 
        TextView title = (TextView)twit.findViewById(R.id.name); // name
        TextView message = (TextView)twit.findViewById(R.id.message); //message
        
        Tweet nowEntry = data.get(position);
        String text = nowEntry.getText();
        TwitterUser user = nowEntry.getUser();
        String name = user.getName();
        if(text.substring(0,4).equals("RT @") &&  text.indexOf(':') > 0){
        	name =  name + " (RT " + text.substring(3, text.indexOf(':')) + ")";
        	text = text.substring(text.indexOf(':') + 2);
        }
        
        // Setting all values in listview
        title.setText(name);
        message.setText(text);

        title.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(), "Roboto-Bold.ttf"));
        title.setTextColor(activity.getResources().getColor(R.color.light_gray));

        message.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Medium.ttf"));
        message.setTextColor(activity.getResources().getColor(R.color.dark_blue));
        
        return twit;
    }
}