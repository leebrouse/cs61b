package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import static gitlet.GitletPath.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date time;
    private String parentID;
    private String secondParentID;
    private HashMap<String,String> fileBlob;

    /* TODO: fill in the rest of this class. */
    public Commit(){
        this.message="initial commit";
        this.time= new Date(0);
        this.parentID=null;
        this.fileBlob=new HashMap<>();
    }

    public Commit(String message, String parentID,HashMap<String,String> fileBlob){
        this.message=message;
        this.time= new Date();
        this.parentID=parentID;
        this.secondParentID=null;
        this.fileBlob=fileBlob;
    }

    public Commit(String message, String parentID,String secondParentID,HashMap<String,String> fileBlob){
        this.message=message;
        this.time= new Date();
        this.parentID=parentID;
        this.secondParentID=secondParentID;
        this.fileBlob=fileBlob;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public String getParentID() {
        return parentID;
    }

    public String getSecondParentID() {
        return secondParentID;
    }

    public HashMap<String, String> getFileBlob() {
        return fileBlob;
    }

}
