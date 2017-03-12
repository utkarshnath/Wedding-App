package Models;

/**
 * Created by utkarshnath on 03/10/15.
 */
public class Invitee {
    String name;
    String fbId;

    public Invitee(String name,String fbId){
        this.name = name;
        this.fbId = fbId;
    }

    public String getName(){
        return name;
    }
    public String getFbId(){
        return fbId;
    }

}
