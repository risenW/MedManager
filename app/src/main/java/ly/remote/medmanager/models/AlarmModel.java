package ly.remote.medmanager.models;

/**
 * Created by Risen on 4/15/2018.
 */

public class AlarmModel {
    private int pendingRequestId, hour,interval;   //pendingRequestId is the item database index

    public AlarmModel(int pendingRequestId, int hour, int interval) {
        this.pendingRequestId = pendingRequestId;
        this.hour = hour;
        this.interval = interval;
    }

    public int getPendingRequestId() {
        return pendingRequestId;
    }

    public void setPendingRequestId(int pendingRequestId) {
        this.pendingRequestId = pendingRequestId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }


    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
