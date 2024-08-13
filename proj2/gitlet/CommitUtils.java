package gitlet;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static gitlet.GitletPath.*;
import static gitlet.Utils.*;
import static gitlet.Utils.readContentsAsString;

public class CommitUtils {
    /** Read Commit records  */
    public static void initCommit(){
       List<String> commitFile=plainFilenamesIn(COMMIT_DIR);
        if (commitFile.isEmpty()){
            System.out.println("The commitStage is empty");
        }else {
            commitLinkedList.addAll(commitFile);
        }

    }

    /** Get new commitID*/
    public static String getCommitID(Commit commit) {
        byte[] commitCode = serialize(commit); // 序列化提交对象
        String commitID = sha1(commitCode);
        return commitID;
    }

    /** Get prev commitID  */
    public static String getParentID(){
        File master=join(BRANCH_DIR,"master");
        String parentID = readContentsAsString(master);
        return parentID;
    }

    /** Get current CommitID*/
    public static String getCurrentCommitID(){

        String currentBranch=readContentsAsString(HEAD_DIR);
        return readContentsAsString(
                join(BRANCH_DIR,currentBranch)
                );
    }

    /** Get current Commit object */
    public static Commit getCurrentCommit(){

        File currentFile=join(COMMIT_DIR,getCurrentCommitID());
        return readObject(currentFile,Commit.class);

    }

    /** input newCommit to the addStage and return this commitID  */
    public static String writeNewCommit(Commit newCommit){
        String commitID = getCommitID(newCommit);
        File newcommitFile=join(COMMIT_DIR,commitID);
        writeObject(newcommitFile,newCommit);

        return commitID;
    }

    /** Update Master Position */
    public static void upDateMaster(String newCommitID){
        File currentBranch=join(BRANCH_DIR,readContentsAsString(HEAD_DIR));
        writeContents(currentBranch,newCommitID);
    }

    /** Clean addStage */
    public static void addFileClear(List<String> addFILEList){
        if (!addFILEList.isEmpty()){

            for (String fileName:addFILEList) {
                //clear index addFile
                join(ADD_DIR,fileName).delete();
            }

        }

    }

    /** Clean removeStage */
    public static void removeFileClear(List<String> removeFILEList){
        if (!removeFILEList.isEmpty()){

            for (String fileName:removeFILEList) {
                //clear index removeFile
                join(REMOVE_DIR,fileName).delete();
            }

        }

    }

    /** Check whether the file exists in the commit*/
    public static boolean fileExistInCommit(Commit currentCommit,String fileName){
        return currentCommit.getFileBlob().containsKey(fileName);
    }

    public static boolean fileExistInCommit(String currentCommitID,String fileName){
        Commit currentCommit=readObject(join(COMMIT_DIR,currentCommitID), Commit.class);
        return currentCommit.getFileBlob().containsKey(fileName);
    }

    /** Check whether the file content which exists in the commit is same*/
    public static boolean fileContentIsSame(Commit currentCommit,String fileName){

        if (fileExistInCommit(currentCommit,fileName)){

            String cwdFileContent=readContentsAsString(join(CWD,fileName));

            //Get blob content
            String blobID=currentCommit.getFileBlob().get(fileName);
            File blob=join(BLOBS_DIR,blobID);
            String blobFileContent=readContentsAsString(blob);

            return cwdFileContent.equals(blobFileContent);
        }
        return false;
    }

    /** Check whether the commitID is existed in commitDir */
    public static boolean commitID_Exist_In_CommitList(List<String> commitIDList,String commitID){
        return commitIDList.contains(commitID);
    }

    /** Check whether the currentCommit has tracked this file*/
    public static boolean fileCommitTracked(String fileName){
        Commit currentCommit=getCurrentCommit();
        boolean check1=fileExistInCommit(currentCommit,fileName);
        boolean check2=fileContentIsSame(currentCommit,fileName);

        if (check1&&check2){
            return true;
        }

        return false;
    }

    /** Get current Branch */
    public static String getCurrentBranch(){
        return readContentsAsString(HEAD_DIR);
    }

    /** The log of Date Format */
    public static String DateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        String time= sdf.format(date);
        return time;
    }
}
