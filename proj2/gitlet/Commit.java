package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import static gitlet.Repository.Branch_DIR;
import static gitlet.Repository.GITLET_DIR;
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
    private static LinkedList<String> commitList=new LinkedList<>() ;;

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

    private static void initCommit(File[] commitFile){
         for (File file:commitFile){
             commitList.add(file.getName());
         }
    }

    public static void printCommit(){
        /**java.util.Date and
        *java.util.Formatter are useful for getting and formatting times*/

        File HeadCommit=join(Branch_DIR,"master");
        String CommitID=Utils.readContentsAsString(HeadCommit);

        File[] commitFile=join(Commit_DIR).listFiles();
        if (commitFile != null) {
            initCommit(commitFile);
        }

        for (int i=0;i<commitList.size();i++){
                int index=commitList.indexOf(CommitID);
                String FileName=commitList.get(index);
                File indexFile=join(Commit_DIR,FileName);
                Commit commit=readObject(indexFile,Commit.class);
                System.out.println("===");
                System.out.println("commit"+" "+indexFile.getName());
                System.out.println("Date:"+" "+commit.time);
                System.out.println(commit.message);
                System.out.println();
                CommitID=commit.parentID;
            }
        }




    }
