package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.TreeMap;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Stage.addStage;
import static gitlet.Utils.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Leebrouse
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
    public static final File Commit_DIR = join(GITLET_DIR, "Commit");
    private String message;
    private Date time;
    private String parentID;
    private HashMap<String,String> fileBlob;

    /* TODO: fill in the rest of this class. */
    public Commit(){
        this.message="Initial commit ";
        this.time= new Date(0);
        this.parentID=null;
    }

    public Commit(String message,String parentID,HashMap<String,String>fileVersionMap){
        this.message=message;
        this.time= new Date();
        this.parentID=parentID;
        this.fileBlob=fileVersionMap;
    }

}
