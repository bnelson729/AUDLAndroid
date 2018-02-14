package info.androidhive.audlandroid.messaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import info.androidhive.audlandroid.R;

public class MessageSubscriber {
    public static final String PREFS_NAME = "Subscriptions";
    public static final String TOPIC_LIST_NAME = "topicList";
    private String TAG="MessageSub";

    public void subscribeToAllTopics(Activity activity) {
        String[] topicList = getTopicList(activity);
        for (String topic : topicList) {
            if (!topic.equals("")) {
                Log.i(TAG, "All subscribing to: [" + topic + "]");
                FirebaseMessaging.getInstance().subscribeToTopic(topic);
            }
        }
    }

    public void subscribeToTopic(Activity activity, String topic) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        String subscriptions = settings.getString(TOPIC_LIST_NAME, activity.getResources().getString(R.string.genSubscribe));
        Log.i(TAG,"Subscribing to: " + topic);
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOPIC_LIST_NAME, addTopicToString(subscriptions,topic));
        editor.commit();
    }

    public void UnSubscribeToTopic(Activity activity, String topic) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        String subscriptions = settings.getString(TOPIC_LIST_NAME, activity.getResources().getString(R.string.genSubscribe));
        Log.i(TAG,"Unsubscribing to: " + topic);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOPIC_LIST_NAME, removeTopicFromString(subscriptions,topic));
        editor.commit();
    }

    private String addTopicToString (String subscriptions, String topic) {
        if (subscriptions.equals("")) {
            subscriptions = topic;
        }
        else {
            subscriptions += "," + topic;
        }
        return subscriptions;
    }

    private String removeTopicFromString (String subscriptions, String topicToRemove) {
        String[] topicList = subscriptions.split(",");
        String newSubscriptions = "";
        for (String topic : topicList) {
            if (!topic.equals(topicToRemove)) {
                newSubscriptions = addTopicToString(newSubscriptions, topic);
            }
        }
        return newSubscriptions;
    }

    public String[] getTopicList(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        String subscriptions = settings.getString(TOPIC_LIST_NAME, activity.getResources().getString(R.string.genSubscribe));
        return subscriptions.split(",");
    }
}
