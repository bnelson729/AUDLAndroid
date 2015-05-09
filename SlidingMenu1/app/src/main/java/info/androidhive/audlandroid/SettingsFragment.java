package info.androidhive.audlandroid;

import info.androidhive.audlandroid.adapter.ListAdapter;
import info.androidhive.audlandroid.adapter.SettingsListBaseAdapter;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
	
	public SettingsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("Settings");
        
        final ListView listview = (ListView) rootView.findViewById(R.id.listview);
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("Send Feedback");
        settings.add("FAQ");
        settings.add("Terms of Use");
        settings.add("Notifications");
        //final ListAdapter adapter = new ListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, settings);
        listview.setAdapter(new SettingsListBaseAdapter(this.getActivity(), settings));
        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
            	
            	switch(position) {
            	
            	case 0:
            	//if(position == 0){
		            	Intent intent = new Intent(Intent.ACTION_VIEW);
		                intent.setData(Uri.parse("mailto:audlappdevteam@gmail.com"));
		                startActivity(intent);
		                break;
            	//}
            	case 1://else if(position == 1){
            			// Create new fragment and transaction
            			Fragment newFragment = new FaqFragment(); 
            		    // consider using Java coding conventions (upper first char class names!!!)
            			FragmentTransaction transaction = getFragmentManager().beginTransaction();

            		    // Replace whatever is in the fragment_container view with this fragment,
            		    // and add the transaction to the back stack
            		    transaction.replace(R.id.frame_container, newFragment);
            		    transaction.addToBackStack(null);

            		    // Commit the transaction
            		    transaction.commit();
            		    break;
            	//}
            	case 2://else if(position ==2){
	            		// Create new fragment and transaction
	           		     Fragment newFragment2 = new ToUFragment(); 
	           		    // consider using Java coding conventions (upper first char class names!!!)
	           		     FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
	
	           		    // Replace whatever is in the fragment_container view with this fragment,
	           		    // and add the transaction to the back stack
	           		    transaction2.replace(R.id.frame_container, newFragment2);
	           		    transaction2.addToBackStack(null);
	
	           		    // Commit the transaction
	           		    transaction2.commit();
	           		    break;
            	}
            
            	
            }
    });
        return rootView;
	}
}
