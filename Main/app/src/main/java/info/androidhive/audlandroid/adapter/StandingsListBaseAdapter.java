package info.androidhive.audlandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.utils.TeamIconLoader;

public class StandingsListBaseAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<ArrayList<String>> data;
    private LayoutInflater inflater=null;
    public TeamIconLoader teamIconLoader;
    String serverURL;

    public StandingsListBaseAdapter(Activity a, ArrayList<ArrayList<String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        teamIconLoader=new TeamIconLoader();
        serverURL = a.getResources().getString(R.string.ServerURL);
    }

    public int getCount() {
        return data.get(0).size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View sch;
        if(convertView==null)
            sch = inflater.inflate(R.layout.list_item_standings, null);
        else{
            sch=convertView;
        }

        TextView wlHeader = (TextView)sch.findViewById(R.id.wlHeader);
        TextView wlValue = (TextView)sch.findViewById(R.id.wlValue);
        TextView pmHeader = (TextView)sch.findViewById(R.id.pmHeader); // title
        TextView pmValue = (TextView)sch.findViewById(R.id.pmValue);
        ImageView teamImage=(ImageView)sch.findViewById(R.id.teamIcon); // home team image
        // Code to convert to local time

        Typeface mediumFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Medium.ttf");
        Typeface boldFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Bold.ttf");

        wlHeader.setText("W/L");
        wlHeader.setTypeface(mediumFont);
        wlHeader.setTextColor(activity.getResources().getColor(R.color.light_gray));

        pmHeader.setText("+/-");
        pmHeader.setTypeface(mediumFont);
        pmHeader.setTextColor(activity.getResources().getColor(R.color.light_gray));


        wlValue.setText(data.get(1).get(position) + "-" + data.get(2).get(position));
        wlValue.setTypeface(boldFont);
        wlValue.setTextColor(activity.getResources().getColor(R.color.dark_blue));
        pmValue.setText(data.get(3).get(position));

        pmValue.setTypeface(boldFont);
        pmValue.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        teamImage.setImageResource(teamIconLoader.get(data.get(4).get(position).toUpperCase()));
        return sch;
    }
}
