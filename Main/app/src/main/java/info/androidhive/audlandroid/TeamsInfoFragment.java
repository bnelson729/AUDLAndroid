package info.androidhive.audlandroid;



import info.androidhive.audlandroid.R;

import info.androidhive.audlandroid.adapter.TeamInfoTabsPagerAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.ScoreListItem;
import info.androidhive.audlandroid.model.TeamsListItem;
import info.androidhive.audlandroid.utils.Utils;

import org.json.JSONArray;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamsInfoFragment extends Fragment {
	
	public TeamsInfoFragment(){}
	
	private ViewPager viewPager;
    private TeamInfoTabsPagerAdapter mAdapter;
    private TeamsListItem team;
    private TextView sectionTitle;

    private ImageView scoreIcon;
    private ImageView scheduleIcon;
    private ImageView statsIcon;
    private ImageView rosterIcon;
    JSONArray jsonResult = null;
    String response = null;
    
    public TeamsListItem getTeam(){
    	return team;
    }
    
    public TeamsListItem parseJSON(JSONArray jsonResult, String team_name, String team_id, String team_abbrev, String division){
    	TeamsListItem team = null;
		try {
			team = new TeamsListItem(team_name, team_id, team_abbrev, division);
			JSONArray playersList = jsonResult.getJSONArray(0);
			JSONArray scheduleList = jsonResult.getJSONArray(1);
			JSONArray statsList = jsonResult.getJSONArray(2);
			JSONArray gamesList = jsonResult.getJSONArray(3);

			//add players
			Log.i("TeamsInfoFragment", "adding players...");
			for (int i=1; i<playersList.length(); i++){
				team.addPlayer(playersList.getJSONArray(i).getString(0), playersList.getJSONArray(i).getString(1));
			}
			Log.i("TeamsInfoFragment", "adding schedule...");
			//add schedule
			for (int i=2; i<scheduleList.length(); i++){
				team.addSchedule(scheduleList.getJSONArray(i).getString(2), scheduleList.getJSONArray(i).getString(3), scheduleList.getJSONArray(i).getString(0), scheduleList.getJSONArray(i).getString(1));
			}
			
			//add stats
			for (int i=1; i<statsList.length(); i++){
				String statsType = statsList.getJSONArray(i).getString(0);
				JSONArray statsData = statsList.getJSONArray(i).getJSONArray(1);
				for (int j=0; j<statsData.length(); j++){
					team.addStats(statsType, statsData.getJSONArray(j).getString(0), statsData.getJSONArray(j).getString(1));
				}
			}
			
			//add games
			for (int j=0; j<gamesList.length(); j++) {
				ScoreListItem scoreItem = new ScoreListItem(gamesList.getJSONArray(j).getString(0),gamesList.getJSONArray(j).getString(1),gamesList.getJSONArray(j).getString(2),
						gamesList.getJSONArray(j).getString(3),gamesList.getJSONArray(j).getString(4),gamesList.getJSONArray(j).getString(5),gamesList.getJSONArray(j).getString(6),
						gamesList.getJSONArray(j).getString(7),gamesList.getJSONArray(j).getString(8),gamesList.getJSONArray(j).getString(9));
				team.addScores(scoreItem);
			}
			
		} catch (Exception e) {
			Log.e("TeamsInfoFragment", "Error when trying to create info objects from json : " + e.toString());
			e.printStackTrace();
		}
		return team;
	}
    
    public void startAsyncTask(final View rootView, final TeamsInfoFragment frag){
		final String team_id = getArguments().getString("TEAM_ID");
		final String team_name = getArguments().getString("TEAM_NAME");
        final String team_abbrev = getArguments().getString("TEAM_ABBREV");
        final String division = getArguments().getString("DIVISION_NAME");
		
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback() {
			@Override
			public void onTaskFailure(){
				Utils.ServerError(getActivity());
			}
			@Override
			public void onTaskDone(String response) {
		        Log.i("TeamsInfoFragment", "response : " + response);
		        try{
		            jsonResult = new JSONArray(response);
		            team = parseJSON(jsonResult, team_name, team_id, team_abbrev, division);
		        } catch (Exception e) {
		        	Log.e("TeamsInfoFragment", "Response: " + response + ". Error creating json " + e.toString());
		        }
		        
		        //header
		        ActionBar act = getActivity().getActionBar();
		        act.setTitle(team.getTeamName());
		        
		        // Initilization
		        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		        mAdapter = new TeamInfoTabsPagerAdapter(frag.getActivity().getSupportFragmentManager(), team);
		 
		        viewPager.setAdapter(mAdapter);

                scoreIcon = (ImageView) rootView.findViewById(R.id.icon_scoreboard);
                scheduleIcon = (ImageView) rootView.findViewById(R.id.icon_schedule);
                statsIcon = (ImageView) rootView.findViewById(R.id.icon_statistics);
                rosterIcon = (ImageView) rootView.findViewById(R.id.icon_roster);

                scoreIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(0);
                    }
                });
                scheduleIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(1);
                    }
                });
                statsIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(2);
                    }
                });
                rosterIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(3);
                    }
                });

		        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		        	 
		            @Override
		            public void onPageSelected(int position) {

                        scoreIcon.setImageResource(R.drawable.icon_scoreboard_off);
                        scheduleIcon.setImageResource(R.drawable.icon_schedule_off);
                        statsIcon.setImageResource(R.drawable.icon_statistics_off);
                        rosterIcon.setImageResource(R.drawable.icon_roster_off);

                        if (position==0) {
                            scoreIcon.setImageResource(R.drawable.icon_scoreboard_on);
                            sectionTitle.setText("SCOREBOARD");
                        }
                        else if (position==1) {
                            scheduleIcon.setImageResource(R.drawable.icon_schedule_on);
                            sectionTitle.setText("SCHEDULE");
                        }
                        else if (position==2) {
                            statsIcon.setImageResource(R.drawable.icon_statistics_on);
                            sectionTitle.setText("STATISTICS");
                        }
                        else {
                            rosterIcon.setImageResource(R.drawable.icon_roster_on);
                            sectionTitle.setText("ROSTER");
                        }
		            }
		         
		            @Override
		            public void onPageScrolled(int position, float arg1, int arg2) {

		            }
		         
		            @Override
		            public void onPageScrollStateChanged(int arg0) {
		            }
		        });
				
			}
		});
		String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Teams/" + team_id);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final String divisionName = getArguments().getString("DIVISION_NAME");

        View rootView = inflater.inflate(R.layout.pager_container_team_info, container, false);
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

        scoreIcon = (ImageView) rootView.findViewById(R.id.icon_scoreboard);
        scheduleIcon = (ImageView) rootView.findViewById(R.id.icon_schedule);
        statsIcon = (ImageView) rootView.findViewById(R.id.icon_statistics);
        rosterIcon = (ImageView) rootView.findViewById(R.id.icon_roster);
        sectionTitle = (TextView) rootView.findViewById(R.id.list_header);

        scoreIcon.setImageResource(R.drawable.icon_scoreboard_on);
        sectionTitle.setText("SCOREBOARD");
        sectionTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
        sectionTitle.setTextColor(getResources().getColor(R.color.dark_blue));
        
        startAsyncTask(rootView, this);
         
        return rootView;
    }
}