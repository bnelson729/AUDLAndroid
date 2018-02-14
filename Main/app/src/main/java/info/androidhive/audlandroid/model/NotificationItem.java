package info.androidhive.audlandroid.model;

public class NotificationItem {

	private String notificationTitle;
	private String topic;
	private boolean checked;

	public NotificationItem(String _notificationTitle, String _topic, boolean _checked) {
		this.notificationTitle = _notificationTitle;
		this.topic = _topic;
		this.checked = _checked;
	}

	public boolean getChecked() {
		return this.checked;
	}

	public void toggleChecked() {
		this.checked = !this.checked;
	}

	public String getTopic() {
		return this.topic;
	}

	public String getNotificationTitle() {
		return this.notificationTitle;
	}
}