package model;

public class MedicationPlan {
    private Integer id;
    private String endDate;
    private String startDate;
    private Integer hourInDay;
    private String interval;
    private Integer doctorId;

    public MedicationPlan(Integer id, String endDate, String startDate, Integer hourInDay, String interval, Integer doctorId) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.hourInDay = hourInDay;
        this.interval = interval;
        this.doctorId = doctorId;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public MedicationPlan() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getHourInDay() {
        return hourInDay;
    }

    public void setHourInDay(Integer hourInDay) {
        this.hourInDay = hourInDay;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id + " ");
        stringBuilder.append(endDate + " ");
        stringBuilder.append(startDate + " ");
        stringBuilder.append(hourInDay + " ");
        stringBuilder.append(interval + " ");
        stringBuilder.append(doctorId + " ");
        return stringBuilder.toString();
    }
}
