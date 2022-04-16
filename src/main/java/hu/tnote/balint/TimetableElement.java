package hu.tnote.balint;

import java.time.LocalTime;

public class TimetableElement {
    private int id;
    private int ttid;
    private String day;
    private String title;
    private String description;
    private LocalTime start;
    private LocalTime end;
    private boolean repeating;

    //region Getter / Setter

    public int getId() {
        return id;
    }

    public int getTtid() {
        return ttid;
    }

    public String getDay() {
        return day;
    }

    public int getDayAsInt() {
        return switch (day) {
            case "Monday" -> 0;
            case "Tuesday" -> 1;
            case "Wednesday" -> 2;
            case "Thursday" -> 3;
            case "Friday" -> 4;
            case "Saturday" -> 5;
            default -> 6;
        };
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTtid(int ttid) {
        this.ttid = ttid;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDayAsInt(int dayInt) {
        switch (dayInt) {
            case 0 -> this.day = "Monday";
            case 1 -> this.day = "Tuesday";
            case 2 -> this.day = "Wednesday";
            case 3 -> this.day = "Thursday";
            case 4 -> this.day = "Friday";
            case 5 -> this.day = "Saturday";
            default -> this.day = "Sunday";
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    //endregion

    public TimetableElement(int id, int ttid, int day) {
        this.id = id;
        this.ttid = ttid;
        setDayAsInt(day);
    }

    public TimetableElement(int id, int ttid, String day, String title, String description, LocalTime start, LocalTime end, boolean repeating) {
        this.id = id;
        this.ttid = ttid;
        this.day = day;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.repeating = repeating;
    }
}
