package minorproject.votingassistant.DMJava;

/**
 * Created by lenovo-pc on 9/7/2017.
 */

public class SDMData {
    private String name;
    private int thumbnail;

    public SDMData(String name, int thumbnail) {
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
