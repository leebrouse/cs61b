package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Blobs.Index_DIR;
import static gitlet.Repository.*;
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
    public static LinkedList<String> commitList ;

    /* TODO: fill in the rest of this class. */
    public Commit(){
        this.message="initial commit";
        this.time= new Date(0);
        this.parentID=null;
        this.fileBlob=new HashMap<>();
    }

    public Commit(String message,String parentID,HashMap<String,String>fileVersionMap){
        this.message=message;
        this.time= new Date();
        this.parentID=parentID;
        this.fileBlob=fileVersionMap;
    }

    public static void initCommit(){
        File[] commitFile=join(Commit_DIR).listFiles();
         commitList=new LinkedList<>();
         for (File file:commitFile){
             commitList.add(file.getName());
         }
    }

    public static boolean judgeCommit(String fileName){
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=readContentsAsString(HEAD);

        File headCommit=join(Branch_DIR,currentBranch);
        String headCommitContent=readContentsAsString(headCommit);
        int index=Commit.commitList.indexOf(headCommitContent);
        String CommitID=Commit.commitList.get(index);
        File indexCommit=join(Commit_DIR,CommitID);
        Commit commit=readObject(indexCommit,Commit.class);
        if (commit.fileBlob.containsKey(fileName)){
            return true;
        }
            return false;
    }

    private static String DateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        String time= sdf.format(date);
        return time;
    }

    /**Just for global_log*/
    public static void globalPrint(String commitID){
        File indexFile=join(Commit_DIR,commitID);
        Commit commit=readObject(indexFile,Commit.class);

        System.out.println("===");
        System.out.println("commit "+indexFile.getName());

        String FormatDate= DateFormat(commit.time);
        System.out.println("Date: "+FormatDate);

        System.out.println(commit.message+"\n");
    }

    /** Just for basic_log
     * "Rubbish Method!!!!"*/
    public static void printCommit(){
        /**java.util.Date and
        *java.util.Formatter are useful for getting and formatting times*/
        initCommit();
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=Utils.readContentsAsString(HEAD);
        File HeadCommit=join(Branch_DIR,currentBranch);
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

                System.out.println(commit.message+"\n");
                CommitID=commit.parentID;
            }
        }

    public static String readFilename(Commit commit, String fileName){
        //for basic_checkout
        if (commit.fileBlob.isEmpty()){
            return null;
        }else {
            String blobCode = commit.fileBlob.get(fileName);
            File[] blobs = join(Index_DIR).listFiles();
            for (File file : blobs){
                if (file.getName().equals(blobCode)){
                    return Utils.readContentsAsString(file);
                }
            }
        }// 如果没有找到文件内容，则返回 null
        return null;
    }

    //Shit!!!!
    public static void readBranch(Commit commit){
        if (commit.fileBlob.isEmpty()){
            //如何让cwd区初始化；
            File[] cwdFile=join(CWD).listFiles();
            for (File file:cwdFile){
                Utils.restrictedDelete(file);
            }
        }else{

            File[] cwdFile=join(CWD).listFiles();
            for (File file:cwdFile){
                if (!commit.fileBlob.containsKey(file.getName())){
                    Utils.restrictedDelete(file);
                }
            }

            Set<String> keys = commit.fileBlob.keySet();
            for (String key : keys) {
                String currentKey=key;
                String blobCode=commit.fileBlob.get(currentKey);

                File[] blobs = join(Index_DIR).listFiles();
                for (File file : blobs){
                    if (file.getName().equals(blobCode)){
                        File File=join(CWD,currentKey);
                        String content=readContentsAsString(file);
                        writeContents(File,content);
                        break;
                    }
                }
            }

        }
    }

    /** To help find method search the message that the users require*/
    public static void findHelper(String message){
            List<String> commitList = Utils.plainFilenamesIn(Commit_DIR);
            if (commitList == null || commitList.isEmpty()) {
                System.out.println("Found no commit with that message.");
                return;
            }

            boolean found = false;  // 标记是否找到匹配的提交信息

            for (String commitId : commitList) {
                File commitFile = join(Commit_DIR, commitId);
                Commit commitObject = readObject(commitFile, Commit.class);
                if (commitObject.message.equals(message)) {
                    System.out.println(commitFile.getName());
                    found = true;  // 找到至少一个匹配的提交，将标记设置为true
                }
            }

            if (!found) {
                // 如果没有找到任何匹配的提交信息
                System.out.println("Found no commit with that message.");
            }
        }

}
