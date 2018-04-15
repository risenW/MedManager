package ly.remote.medmanager.models;

/**
 * Created by Risen on 4/15/2018.
 */

public class AlarmModel {
    private int pendingRequestId, hour, min,interval;   //pendingRequestId is the item database index

    public AlarmModel(int pendingRequestId, int hour, int min, int interval) {
        this.pendingRequestId = pendingRequestId;
        this.hour = hour;
        this.min = min;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
