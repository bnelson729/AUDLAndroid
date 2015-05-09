package info.androidhive.audlandroid.adapter;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.utils.ImageLoader;
import info.androidhive.audlandroid.utils.TeamIconLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreListBaseAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<ArrayList<String>> data;
    private LayoutInflater inflater=null;
    public TeamIconLoader teamIconLoader;
    String serverURL;

    public ScoreListBaseAdapter(Activity a, ArrayList<ArrayList<String>> d) {
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
            sch = inflater.inflate(R.layout.list_item_score, null);
        else{
            sch=convertView;
        }
        TextView separator = (TextView) sch.findViewById(R.id.separator);
        separator.setVisibility(View.GONE);
        TextView homeTeamName = (TextView)sch.findViewById(R.id.homeTeamName);
        TextView awayTeamName = (TextView)sch.findViewById(R.id.awayTeamName);
        TextView status = (TextView)sch.findViewById(R.id.game_status); // title
        TextView homeTeamScore = (TextView)sch.findViewById(R.id.homeTeamScore);
        TextView awayTeamScore = (TextView)sch.findViewById(R.id.awayTeamScore);
        ImageView homeTeamImage=(ImageView)sch.findViewById(R.id.hTeamIcon); // home team image
        ImageView awayTeamImage=(ImageView)sch.findViewById(R.id.aTeamIcon); // away team image
        ImageView homeTeamStroke=(ImageView)sch.findViewById(R.id.hTeamStroke); // home team image
        ImageView awayTeamStroke=(ImageView)sch.findViewById(R.id.aTeamStroke); // away team image
        // Code to convert to local time
        DateTimeFormatter timeParser = ISODateTimeFormat.dateTimeNoMillis();
        String time = data.get(9).get(position);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(timeParser.parseDateTime(time).getMillis());
        String timeString = timeFormatter.format(timeCalendar.getTime());

        DateTimeFormatter dateParser = DateTimeFormat.forPattern("MM/dd/yyyy");
        String date = data.get(4).get(position);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM.dd.yyyy");
        Calendar dateCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
        dateCalendar.setTimeInMillis(dateParser.parseDateTime(date).getMillis());
        String dateString;
        if (dateFormatter.format(dateCalendar.getTime()).equals(dateFormatter.format(currentCalendar.getTime()))) {
            dateString = "TODAY";
        }
        else {
            dateString = dateFormatter.format(dateCalendar.getTime());
        }

        Typeface mediumFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Medium.ttf");
        Typeface boldFont = Typeface.createFromAsset(activity.getResources().getAssets(),"Roboto-Bold.ttf");

        if(position == 0){
            separator.setVisibility(View.VISIBLE);
            separator.setText(dateString);

            separator.setTypeface(boldFont);
            separator.setTextColor(activity.getResources().getColor(R.color.white_text));
        }
        else{
            if(data.get(4).get(position).compareTo(data.get(4).get(position-1)) != 0){
                separator.setVisibility(View.VISIBLE);
                separator.setText(dateString);

                separator.setTypeface(boldFont);
                separator.setTextColor(activity.getResources().getColor(R.color.white_text));
            }
        }
        homeTeamName.setText(data.get(0).get(position));
        homeTeamName.setTypeface(mediumFont);
        homeTeamName.setTextColor(activity.getResources().getColor(R.color.light_gray));
        awayTeamName.setText(data.get(2).get(position));
        awayTeamName.setTypeface(mediumFont);
        awayTeamName.setTextColor(activity.getResources().getColor(R.color.light_gray));
        if(data.get(8).get(position).compareTo("0")!=0) {
            homeTeamScore.setText(data.get(6).get(position));
            awayTeamScore.setText(data.get(7).get(position));
        }
        else {
            homeTeamScore.setText("00");
            awayTeamScore.setText("00");
        }
        homeTeamScore.setTypeface(boldFont);
        awayTeamScore.setTypeface(boldFont);
        if(data.get(8).get(position).compareTo("0")==0){
            homeTeamScore.setTextColor(activity.getResources().getColor(R.color.light_gray));
            awayTeamScore.setTextColor(activity.getResources().getColor(R.color.light_gray));
            homeTeamStroke.setVisibility(View.INVISIBLE);
            awayTeamStroke.setVisibility(View.INVISIBLE);

        }
        else if (Integer.parseInt(data.get(6).get(position)) < Integer.parseInt(data.get(7).get(position))) {
            awayTeamScore.setTextColor(activity.getResources().getColor(R.color.dark_blue));
            homeTeamScore.setTextColor(activity.getResources().getColor(R.color.light_gray));
            homeTeamStroke.setVisibility(View.INVISIBLE);
        }
        else {
            homeTeamScore.setTextColor(activity.getResources().getColor(R.color.dark_blue));
            awayTeamScore.setTextColor(activity.getResources().getColor(R.color.light_gray));
            awayTeamStroke.setVisibility(View.INVISIBLE);
        }

        String gameStatus="";
        if(data.get(8).get(position).compareTo("0")==0){
            gameStatus=timeString;
        }else if(data.get(8).get(position).compareTo("1")==0){
            gameStatus="ONGOING";
        }else{
            gameStatus="FINAL";
        }
        status.setText(gameStatus);
        status.setTypeface(mediumFont);
        status.setTextColor(activity.getResources().getColor(R.color.light_gray));

        homeTeamImage.setImageResource(teamIconLoader.get(data.get(0).get(position).toUpperCase()));
        awayTeamImage.setImageResource(teamIconLoader.get(data.get(2).get(position).toUpperCase()));
        return sch;
    }
}
