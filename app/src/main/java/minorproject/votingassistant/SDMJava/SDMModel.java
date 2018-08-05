package minorproject.votingassistant.SDMJava;

/**
 * Created by dk on 30/8/17.
 */

public class SDMModel {
    private String name;
    private int thmbnail;


    public SDMModel(String name, int thmbnail){
             this.name=name;
             this.thmbnail=thmbnail;

         }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThmbnail() {
        return thmbnail;
    }

    public void setThmbnail(int thmbnail) {
        this.thmbnail = thmbnail;
    }
}



