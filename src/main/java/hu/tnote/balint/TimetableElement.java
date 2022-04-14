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
