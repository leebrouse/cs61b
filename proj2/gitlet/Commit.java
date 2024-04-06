package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private static LinkedList<String> commitList ;

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

    private static void initCommit(){
        File[] commitFile=join(Commit_DIR).listFiles();
         commitList=new LinkedList<>();
         for (File file:commitFile){
             commitList.add(file.getName());
         }
    }

    private static String DateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        String time= sdf.format(date);
        return time;
    }

    public static void printCommit(){
        /**java.util.Date and
        *java.util.Formatter are useful for getting and formatting times*/
        initCommit();

        File HeadCommit=join(Branch_DIR,"master");
        String CommitID=Utils.readContentsAsString(HeadCommit);

        for (int i=0;i<commitList.size();i++){
                int index=commitList.indexOf(CommitID);
                String FileName=commitList.get(index);
                File indexFile=join(Commit_DIR,FileName);
                Commit commit=readObject(indexFile,Commit.class);

                System.out.println("===");
                System.out.println("commit "+indexFile.getName());

                String FormatDate= DateFormat(commit.time);
                System.out.println("Date: "+FormatDate);

                System.out.println(commit.message);
                if (i!=commitList.size()-1){
                    System.out.println();
                }
                CommitID=commit.parentID;
            }
        }

    }
