package minorproject.votingassistant.ZonalJava;

/**
 * Created by RR on 05-09-2017.
 */

public class ZonalData {
    private String name;
    private int thumbnail;

    public ZonalData() {
    }

    public ZonalData(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

