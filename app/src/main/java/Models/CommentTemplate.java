package Models;

/**
 * Created by utkarshnath on 05/10/15.
 */
public class CommentTemplate {
    String name;
    String dpId;
    String comment;

    public CommentTemplate(String name,String dpId,String comment){
        this.name = name;
        this.dpId = dpId;
        this.comment = comment;
    }

     public String getName(){
        return name;
    }
    public String getDpId(){
        return dpId;
    }
    public String getComment(){
        return comment;
    }
}
