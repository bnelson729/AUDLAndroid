package info.androidhive.audlandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import info.androidhive.audlandroid.adapter.ScoreInfoPagerAdapter;
import info.androidhive.audlandroid.adapter.TeamInfoTabsPagerAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.ScoreListItem;
import info.androidhive.audlandroid.model.TopStatsItem;
import info.androidhive.audlandroid.utils.TeamIconLoader;
import info.androidhive.audlandroid.utils.Utils;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoresInfoFragment extends Fragment{
	
	public ScoresInfoFragment(){}
	
	private ViewPager viewPager;
    private ScoreInfoPagerAdapter mAdapter;

    public TeamIconLoader teamIconLoader;
    private ScoreListItem score;
    private TextView sectionTitle;
    private JSONArray jsonResult = null;
    private String response = null;
    private ArrayList<TopStatsItem> topStats;
    public ScoreListItem getScore(){
    	return score;
    }
    
    private ArrayList<TopStatsItem> parseJSON(JSONArray response){
    	ArrayList<TopStatsItem> list = new ArrayList<TopStatsItem>();
		   try{
			   JSONArray statsArray = response.getJSONArray(4);
			   JSONArray homeStatsArray = statsArray.getJSONArray(0);
			   JSONArray goalsArray = homeStatsArray.getJSONArray(0);
			   JSONArray assistsArray = homeStatsArray.getJSONArray(1);
			   JSONArray dropsArray = homeStatsArray.getJSONArray(2);
			   JSONArray tAsArray  = homeStatsArray.getJSONArray(3);
			   JSONArray DsArray = homeStatsArray.getJSONArray(4);
			   TopStatsItem home = new TopStatsItem(goalsArray.getString(1),goalsArray.getString(2),
					   								assistsArray.getString(1),assistsArray.getString(2),
					   								dropsArray.getString(1),dropsArray.getString(2),
					   								tAsArray.getString(1),tAsArray.getString(2),
					   								DsArray.getString(1),DsArray.getString(2));
			   list.add(home);
			   JSONArray awayStatsArray = statsArray.getJSONArray(1);
			   goalsArray = awayStatsArray.getJSONArray(0);
			   assistsArray = awayStatsArray.getJSONArray(1);
			   dropsArray = awayStatsArray.getJSONArray(2);
			   tAsArray  = awayStatsArray.getJSONArray(3);
			   DsArray = awayStatsArray.getJSONArray(4);
			   TopStatsItem away = new TopStatsItem(goalsArray.getString(1),goalsArray.getString(2),
							assistsArray.getString(1),assistsArray.getString(2),
							dropsArray.getString(1),dropsArray.getString(2),
							tAsArray.getString(1),tAsArray.getString(2),
							DsArray.getString(1),DsArray.getString(2));
			   list.add(away);
		   }catch(JSONException e){
			   
		   }
		   return list;
    }
    public void startAsyncTask(final View rootView,final ScoresInfoFragment frag){
    	final String homeTeamName = getArguments().getString("HOMETEAM");
    	final String awayTeamName = getArguments().getString("AWAYTEAM");
    	final String homeTeamID = getArguments().getString("HOMETEAMID");
    	final String date = getArguments().getString("DATE");
    	final String time = getArguments().getString("TIME");
    	final String awayTeamID = getArguments().getString("AWAYTEAMID");
    	final String homeTeamScore = getArguments().getString("HOMETEAMSCORE");
    	final String awayTeamScore = getArguments().getString("AWAYTEAMSCORE");
    	final String gameStatus = getArguments().getString("GAMESTATUS");
    	final String ISOTime = getArguments().getString("ISOTIME");
    	final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback(){
			@Override
			public void onTaskFailure() {
				Utils.ServerError(getActivity());
			}

			@Override
			public void onTaskDone(String response) {
				try{
					jsonResult = new JSONArray(response);
					topStats = parseJSON(jsonResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				viewPager = (ViewPager) rootView.findViewById(R.id.detailed_scores_pager);
				ScoreListItem item = new ScoreListItem(homeTeamName,homeTeamID,awayTeamName,awayTeamID,date,time,
						homeTeamScore,awayTeamScore,gameStatus,ISOTime);
				mAdapter = new ScoreInfoPagerAdapter(frag.getActivity().getSupportFragmentManager(),topStats,item,frag.getActivity());
				viewPager.setAdapter(mAdapter);

                ImageView leftArrow = (ImageView) rootView.findViewById(R.id.leftArrow);
                ImageView rightArrow = (ImageView) rootView.findViewById(R.id.rightArrow);

                leftArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("ArrowClicks","ScoreLeft");
                        int index = viewPager.getCurrentItem();
                        if (index>0) viewPager.setCurrentItem(index-1);
                    }
                });
                rightArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("ArrowClicks","RightLeft");
                        int index = viewPager.getCurrentItem();
                        if (index < mAdapter.getCount()) viewPager.setCurrentItem(index+1);
                    }
                });

				viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int position) {
                        String title = "UNKNOWN";
                        switch (position) {
                            case 0: title="TOP PLAYERS"; break;
                            case 1: title="GRAPHICAL VIEW"; break;
                            default:
                                Log.e("ScoresInfoFragment","No section title");
                        }
                        sectionTitle.setText(title);
						
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}
    		
    	});
    	String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Game/" + homeTeamID + "/" + date);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final String divisionName = getArguments().getString("DIVISIONNAME");

        View rootView = inflater.inflate(R.layout.pager_container_score_info, container, false);

        if (divisionName.equals("West")) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.west);
            imageView.setImageResource(R.drawable.west_on);
        }
        else if (divisionName.equals("Midwest")) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.midwest);
            imageView.setImageResource(R.drawable.midwest_on);
        }
        else if (divisionName.equals("East")) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.east);
            imageView.setImageResource(R.drawable.east_on);
        }
        else {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.south);
            imageView.setImageResource(R.drawable.south_on);
        }

        Typeface boldTypeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf");
        Typeface mediumTypeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Medium.ttf");

        TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("SCOREBOARD");
        txtView.setTypeface(boldTypeface);
        txtView.setTextColor(getResources().getColor(R.color.dark_blue));

        sectionTitle = (TextView) rootView.findViewById(R.id.sectionTitle);
        sectionTitle.setText("TOP PLAYERS");
        sectionTitle.setTypeface(boldTypeface);
        sectionTitle.setTextColor(getResources().getColor(R.color.white_text));

        final String homeTeamName = getArguments().getString("HOMETEAM");
        final String awayTeamName = getArguments().getString("AWAYTEAM");
        final String homeTeamScore = getArguments().getString("HOMETEAMSCORE");
        final String awayTeamScore = getArguments().getString("AWAYTEAMSCORE");
        final String gameStatus = getArguments().getString("GAMESTATUS");

        ImageView homeIcon = (ImageView) rootView.findViewById(R.id.homeIcon);
        ImageView awayIcon = (ImageView) rootView.findViewById(R.id.awayIcon);
        ImageView homeTeamStroke=(ImageView) rootView.findViewById(R.id.hTeamStroke); // home team image
        ImageView awayTeamStroke=(ImageView) rootView.findViewById(R.id.aTeamStroke); // away team image
        TextView homeScore = (TextView) rootView.findViewById(R.id.homeScore);
        TextView awayScore = (TextView) rootView.findViewById(R.id.awayScore);
        TextView homeTeam = (TextView) rootView.findViewById(R.id.homeTeamName);
        TextView awayTeam = (TextView) rootView.findViewById(R.id.awayTeamName);
        TextView status = (TextView) rootView.findViewById(R.id.gameStatus);

        homeScore.setText(homeTeamScore);
        awayScore.setText(awayTeamScore);
        homeTeam.setText(homeTeamName);
        awayTeam.setText(awayTeamName);
        if(gameStatus.compareTo("1")==0){
            status.setText("ONGOING");
        }else{
            status.setText("FINAL");
        }

        homeScore.setTypeface(boldTypeface);

        awayScore.setTypeface(boldTypeface);

        if(gameStatus.compareTo("0")==0) {
            homeScore.setTextColor(getResources().getColor(R.color.light_gray));
            awayScore.setTextColor(getResources().getColor(R.color.light_gray));
            homeTeamStroke.setVisibility(View.INVISIBLE);
            awayTeamStroke.setVisibility(View.INVISIBLE);
        }
        else if (Integer.parseInt(homeTeamScore) < Integer.parseInt(awayTeamScore))
        {
            awayScore.setTextColor(getResources().getColor(R.color.dark_blue));
            homeScore.setTextColor(getResources().getColor(R.color.light_gray));
            homeTeamStroke.setVisibility(View.INVISIBLE);
        }
        else
        {
            homeScore.setTextColor(getResources().getColor(R.color.dark_blue));
            awayScore.setTextColor(getResources().getColor(R.color.light_gray));
            awayTeamStroke.setVisibility(View.INVISIBLE);
        }

        homeTeam.setTypeface(mediumTypeface);
        homeTeam.setTextColor(getResources().getColor(R.color.light_gray));

        awayTeam.setTypeface(mediumTypeface);
        awayTeam.setTextColor(getResources().getColor(R.color.light_gray));

        status.setTypeface(mediumTypeface);
        status.setTextColor(getResources().getColor(R.color.light_gray));

        teamIconLoader=new TeamIconLoader();
        homeIcon.setImageResource(teamIconLoader.get(homeTeamName.toUpperCase()));
        awayIcon.setImageResource(teamIconLoader.get(awayTeamName.toUpperCase()));

        startAsyncTask(rootView, this);
         
        return rootView;
    }
    
}
