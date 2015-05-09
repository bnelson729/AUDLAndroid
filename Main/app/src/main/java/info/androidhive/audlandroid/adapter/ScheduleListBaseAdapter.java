package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.model.ScheduleListItem;
import info.androidhive.audlandroid.utils.TeamIconLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ScheduleListBaseAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<ScheduleListItem> data;
    private LayoutInflater inflater=null;
    public TeamIconLoader teamIconLoader;
    String serverURL;
    
    public ScheduleListBaseAdapter(Activity a, ArrayList<ScheduleListItem> d) {
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
        View sch =convertView;
        if(convertView==null)
            sch = inflater.inflate(R.layout.list_item_schedule, null);
 
        TextView homeTeamTitle = (TextView)sch.findViewById(R.id.hometeam); // title
        TextView awayTeamTitle = (TextView)sch.findViewById(R.id.awayteam); // title
        TextView vsTitle = (TextView)sch.findViewById(R.id.vs); // title
        TextView gametime = (TextView)sch.findViewById(R.id.gametime); // title
        ImageView homeTeamImage=(ImageView)sch.findViewById(R.id.homeTeamIcon); // home team image
        ImageView awayTeamImage=(ImageView)sch.findViewById(R.id.awayTeamIcon); // away team image
        
        ScheduleListItem scheduleEntry = data.get(position);

        Typeface mediumFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Medium.ttf");
        Typeface boldFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Bold.ttf");

        homeTeamTitle.setText(scheduleEntry.getHomeTeamAbbrev());
        homeTeamTitle.setTypeface(boldFont);
        homeTeamTitle.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        awayTeamTitle.setText(scheduleEntry.getAwayTeam());
        awayTeamTitle.setTypeface(boldFont);
        awayTeamTitle.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        vsTitle.setTypeface(mediumFont);
        vsTitle.setTextColor(activity.getResources().getColor(R.color.dark_blue));

        DateTimeFormatter parser = DateTimeFormat.forPattern("MM/dd/yyyy");
        String date = scheduleEntry.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(parser.parseDateTime(date).getMillis());
        String timeString = formatter.format(calendar.getTime());

        gametime.setText(timeString.toUpperCase() + " | " + scheduleEntry.getTime());
        gametime.setTypeface(mediumFont);
        gametime.setTextColor(activity.getResources().getColor(R.color.light_gray));

        homeTeamImage.setImageResource(teamIconLoader.get(scheduleEntry.getHomeTeamAbbrev().toUpperCase()));
        awayTeamImage.setImageResource(teamIconLoader.get(scheduleEntry.getAwayTeam().toUpperCase()));
        
        
        return sch;
    }
}