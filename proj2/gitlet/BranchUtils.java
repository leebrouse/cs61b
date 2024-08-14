package gitlet;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static gitlet.CommitUtils.*;
import static gitlet.GitletPath.*;
import static gitlet.Utils.*;

public class BranchUtils {
    /** To delete unessential cwdFile  */
    public static void delete_Unessential_CwdFile(Commit currentCommit){
        //get cwd File List
        List<String> cwdFileList=plainFilenamesIn(CWD);
        for (String cwdFileName:cwdFileList ){
            //delete the unessential cwdFile
            if (!currentCommit.getFileBlob().containsKey(cwdFileName)) {
                join(CWD, cwdFileName).delete();
            }
        }
    }

    /** To update the cwdFile for checkout Branch*/
    public static void update_cwdFile(Commit currentCommit){
        //get current commit Hashmap key Collection
        Collection<String> commitFileSets=currentCommit.getFileBlob().keySet();
        for (String commitFileName:commitFileSets ){
            //check the master pointing commitFile,and replace the cwdFile
            File newCwdFile=join(CWD,commitFileName);
            String blobID=currentCommit.getFileBlob().get(commitFileName);
            writeContents(newCwdFile,
                    readContentsAsString(join(BLOBS_DIR,blobID))
            );
        }
    }

    /** Get current Branch */
    public static String getCurrentBranch(){
        return readContentsAsString(HEAD_DIR);
    }

    /** Check whether the cwdFile should be overwritten (using branchName) ,when checkout branch,check the file is tracked */
    public static boolean branchFileOverWrite(String cwdFileName,String branch){
        File cwdFile=join(CWD,cwdFileName);
        String blobID=sha1(readContentsAsString(cwdFile));
        //should get branch commitID
        String branchCommitID=readContentsAsString(join(BRANCH_DIR,branch));
        Commit branchCommit=readObject(join(COMMIT_DIR,branchCommitID), Commit.class);

        if (!fileExistInCommit(branchCommit,cwdFileName)){
            return true;
        }else {
            String currentBranchBlobID=branchCommit.getFileBlob().get(cwdFileName);

            return !blobID.equals(currentBranchBlobID);
        }
    }

    /** Check whether the addStage has tracked this file(using Branch)*/
    public static boolean fileAddStageTracked(List<String> cwdFileList,String branch){
        List<String> addStageList=plainFilenamesIn(ADD_DIR);

        if (!cwdFileList.isEmpty()){
            for (String cwdFile:cwdFileList){
                if (!addStageList.contains(cwdFile)&&branchFileOverWrite(cwdFile,branch)&&!fileCommitTracked(cwdFile)){
                    return false;
                }
            }
        }
        return true;
    }

    /** find the matching commitID in the commitList*/
    public static String matchingCommit(String subCommitID){
        List<String> commitList=plainFilenamesIn(COMMIT_DIR);
        for (String commitID:commitList){
            String matchingSubCommitID=commitID.substring(0,subCommitID.length());
            if (matchingSubCommitID.equals(subCommitID)){
                return commitID;
            }
        }
        return subCommitID;
    }

    public static boolean  branchTracked(String branch){
        List<String> cwdFileList=plainFilenamesIn(CWD);
        return fileAddStageTracked(cwdFileList,branch);
    }
}
