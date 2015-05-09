package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.model.NewsListItem;
import info.androidhive.audlandroid.model.TeamsListItem;
import info.androidhive.audlandroid.model.VideosListItem;
import info.androidhive.audlandroid.utils.ImageLoader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;

public class NewsListBaseAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<NewsListItem> data;
    private LayoutInflater inflater=null;
    
    public NewsListBaseAdapter(Activity a, ArrayList<NewsListItem> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            vi = inflater.inflate(R.layout.list_item_news, null);
 
        TextView title = (TextView)vi.findViewById(R.id.name); // title
        TextView datetime = (TextView)vi.findViewById(R.id.datetime); // datetime
 
        NewsListItem news = data.get(position);
 
        // Setting all values in listview
        title.setText(news.getNewsHeadline());
        title.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Bold.ttf"));
        title.setTextColor(activity.getResources().getColor(R.color.dark_blue));
        datetime.setText(news.getDatetime());
        datetime.setTypeface(Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Medium.ttf"));
        datetime.setTextColor(activity.getResources().getColor(R.color.light_gray));
        return vi;
    }
}