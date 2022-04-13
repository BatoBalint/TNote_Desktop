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

    public String getTitle() { return this.title; }

    public String getDescription() { return this.description; }

    public LocalTime getStart() { return this.start; }

    public LocalTime getEnd() { return this.end; }

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
