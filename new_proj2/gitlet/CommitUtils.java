package gitlet;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static gitlet.GitletPath.*;
import static gitlet.Utils.*;
import static gitlet.Utils.readContentsAsString;

public class CommitUtils {
    /** read Commit records  */
    public static void initCommit(){
        File[] commitFile=join(COMMIT_DIR).listFiles();
        if (commitFile==null){
            System.out.println("The commitStage is empty");
            return;
        }else {
            for (File file:commitFile){
                commitLinkedList.add(file.getName());
            }
        }

    }

    /** get new commitID*/
    public static String getCommitID(Commit commit) {
        byte[] commitCode = serialize(commit); // 序列化提交对象
        String commitID = sha1(commitCode);
        return commitID;
    }

    /** get prev commitID  */
    public static String getParentID(){
        File master=join(BRANCH_DIR,"master");
        String parentID = readContentsAsString(master);
        return parentID;
    }

    /** input newCommit to the addStage and return this commitID  */
    public static String writeNewCommit(Commit newCommit){
        String commitID = getCommitID(newCommit);
        File newcommitFile=join(COMMIT_DIR,commitID);
        writeObject(newcommitFile,newCommit);

        return commitID;
    }

    public static void upDateMaster(String newCommitID){
        File currentBranch=join(BRANCH_DIR,MASTER_BRANCH_NAME);
        writeContents(currentBranch,newCommitID);
    }

    public static void addFileClear(File[] addFILE){
        for (File file:addFILE) {
            file.delete();
        }
    }

    public static String DateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        String time= sdf.format(date);
        return time;
    }
}
