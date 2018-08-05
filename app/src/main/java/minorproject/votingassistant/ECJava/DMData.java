package minorproject.votingassistant.ECJava;

/**
 * Created by rk on 30/8/17.
 */

public class DMData {
    private String name;
    private int thumbnail;

    public DMData() {
    }

    public DMData(String name, int thumbnail) {
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
