package info.androidhive.audlandroid;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import info.androidhive.audlandroid.adapter.NotificationBaseAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.messaging.MessageSubscriber;
import info.androidhive.audlandroid.model.NotificationItem;
import info.androidhive.audlandroid.utils.Utils;

public class NotificationFragment extends Fragment {
	JSONArray jsonResult = null;
    String response = null;

	public NotificationFragment(){}
	
	public ArrayList<NotificationItem> parseJSON(JSONArray jsonResult, final Activity activity){
		ArrayList<NotificationItem> notificationList = new ArrayList<NotificationItem>();
		MessageSubscriber subscriber = new MessageSubscriber();
		String[] topicList = subscriber.getTopicList(activity);
		notificationList.add(new NotificationItem("Test",activity.getResources().getString(R.string.testSubscribe),Arrays.asList(topicList).contains(activity.getResources().getString(R.string.testSubscribe))));

		notificationList.add(new NotificationItem("General",activity.getResources().getString(R.string.genSubscribe),Arrays.asList(topicList).contains(activity.getResources().getString(R.string.genSubscribe))));

		try {
			for (int i=0; i<jsonResult.length(); i++){
				String topic = jsonResult.getJSONArray(i).getString(2);
				notificationList.add(new NotificationItem(jsonResult.getJSONArray(i).getString(0), topic, Arrays.asList(topicList).contains(topic)));
			}
		} catch (Exception e) {
			Log.e("NotificationFragment", "Error when trying to create news objects from json : " + e.toString());
		}
		return notificationList;
	}
	public void startAsyncTask(final ListView listview, final Activity activity){
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback() {
			@Override
			public void onTaskFailure(){
				Utils.ServerError(activity);
			}
			@Override
			public void onTaskDone(String response) {
		        try{
		            jsonResult = new JSONArray(response);
		        } catch (Exception e) {
		        	Log.e("TeamsListFragment", "Response: " + response + ". Error creating json " + e.toString());
		        }
				
		        final ArrayList<NotificationItem> notifications = parseJSON(jsonResult, activity);

		        final NotificationBaseAdapter adapter = new NotificationBaseAdapter(activity, notifications);
		        listview.setAdapter(adapter);
			}
		});
		String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Teams");
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.list_container_w_emws_header, container, false);

		getActivity().getActionBar().setTitle("Notifications");

		TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
		txtView.setText("NOTIFICATIONS");
		txtView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
		txtView.setTextColor(getResources().getColor(R.color.dark_blue));
		
		final ListView listview = (ListView) rootView.findViewById(R.id.listview);
		startAsyncTask(listview, getActivity());
		
        return rootView;
    }		
}
