package info.androidhive.audlandroid;

import info.androidhive.audlandroid.adapter.ScheduleListTabsPagerAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.ScheduleListItem;
import info.androidhive.audlandroid.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

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

public class ScheduleListFragment extends Fragment {
	
	public ScheduleListFragment(){}
    
    HashMap<String, ArrayList<ScheduleListItem>> schedLists = new HashMap<String, ArrayList<ScheduleListItem>>();
    public ViewPager viewPager;
	JSONArray jsonResult = null;
    String response = null;
	
	public HashMap<String, ArrayList<ScheduleListItem>> parseJSON(JSONArray jsonResult){
		HashMap<String, ArrayList<ScheduleListItem>> schedLists = new HashMap<String, ArrayList<ScheduleListItem>>();
		try {
			for (int i=0; i<jsonResult.length(); i++){
				ArrayList<ScheduleListItem> schedList = new ArrayList<ScheduleListItem>();
				schedLists.put(jsonResult.getJSONArray(i).getString(0), schedList);
				for(int j = 0; j < jsonResult.getJSONArray(i).getJSONArray(1).length(); j++){
					schedList.add(new ScheduleListItem(jsonResult.getJSONArray(i).getString(0), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(0), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(0), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(1), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(2), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(3), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(4), jsonResult.getJSONArray(i).getJSONArray(1).getJSONArray(j).getString(5)));
				}
			}
		} catch (Exception e) {
			Log.e("ScheduleListFragment", "Error when trying to create news objects from json : " + e.toString());
		}
		return schedLists;
	}
	
	
	public void startAsyncTask(final View rootView, final ScheduleListFragment frag){
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback() {
			@Override
			public void onTaskFailure(){
				Utils.ServerError(frag.getActivity());
			}
			@Override
			public void onTaskDone(String response) {
			        try{
			            jsonResult = new JSONArray(response);
			        } catch (Exception e) {
			        	Log.e("ScheduleListFragment", "Response: " + response + ". Error creating json " + e.toString());
			        }

			        schedLists = parseJSON(jsonResult);
			        
			        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
			        final ScheduleListTabsPagerAdapter adapter = new ScheduleListTabsPagerAdapter(frag.getActivity().getSupportFragmentManager(), schedLists);        
			        viewPager.setAdapter(adapter);

                    ImageView westIcon = (ImageView) rootView.findViewById(R.id.west);
                    ImageView midwestIcon = (ImageView) rootView.findViewById(R.id.midwest);
                    ImageView eastIcon = (ImageView) rootView.findViewById(R.id.east);
                    ImageView southIcon = (ImageView) rootView.findViewById(R.id.south);

                    eastIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(0);
                        }
                    });
                    midwestIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(1);
                        }
                    });
                    westIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(2);
                        }
                    });
                    southIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(3);
                        }
                    });

			        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			        	 
			            @Override
			            public void onPageSelected(int position) {
                            ImageView westIcon = (ImageView) rootView.findViewById(R.id.west);
                            ImageView midwestIcon = (ImageView) rootView.findViewById(R.id.midwest);
                            ImageView eastIcon = (ImageView) rootView.findViewById(R.id.east);
                            ImageView southIcon = (ImageView) rootView.findViewById(R.id.south);

                            westIcon.setImageResource(R.drawable.west_off);
                            midwestIcon.setImageResource(R.drawable.midwest_off);
                            eastIcon.setImageResource(R.drawable.east_off);
                            southIcon.setImageResource(R.drawable.south_off);

                            if (position==0) {
                                eastIcon.setImageResource(R.drawable.east_on);
                            }
                            else if (position==1) {
                                midwestIcon.setImageResource(R.drawable.midwest_on);
                            }
                            else if (position==2) {
                                westIcon.setImageResource(R.drawable.west_on);
                            }
                            else {
                                southIcon.setImageResource(R.drawable.south_on);
                            }
			            }
			         
			            @Override
			            public void onPageScrolled(int arg0, float arg1, int arg2) {
			            }
			         
			            @Override
			            public void onPageScrollStateChanged(int arg0) {
			            }
			        });
			}
		});
        //httpRequester.execute("http://68.190.167.114:4000/News");
		String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Schedule");
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		//new HttpRequestTask().execute("http://192.168.72.235:4000/teams");
        
        View rootView = inflater.inflate(R.layout.pager_container_schedule, container, false);

        TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("SCHEDULE");
        txtView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
        txtView.setTextColor(getResources().getColor(R.color.dark_blue));

        startAsyncTask(rootView, this);
         
        return rootView;
    }
}
