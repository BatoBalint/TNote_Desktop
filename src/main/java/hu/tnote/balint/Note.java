package hu.tnote.balint;

public class Note {
    private int id;
    private int ownerId;
    private String content;
    private String title;

    public Note(int id, int ownerId, String title, String content) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%2d : %2d %-30s %s", this.id, this.ownerId, this.title, this.content);
    }
}
