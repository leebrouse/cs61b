package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static gitlet.BlobsUtils.*;
import static gitlet.BranchUtils.*;
import static gitlet.CommitUtils.*;
import static gitlet.GitletPath.*;
import static gitlet.MergeUtils.*;
import static gitlet.Status.*;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /* TODO: fill in the rest of this class. */
    public static void init(){
        if (!GITLET_DIR.mkdir()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }

        //blob(for saving snaps)
        BLOBS_DIR.mkdir();

        //addStage
        ADD_DIR.mkdir();

        //commit
        COMMIT_DIR.mkdir();

        /** Push the initCommit into the COMMIT_DIR*/
        Commit initCommit = new Commit();
        String initCommitID = writeNewCommit(initCommit);

        //removeStage
        REMOVE_DIR.mkdir();

        //branch
        BRANCH_DIR.mkdir();
        File master_branch=join(BRANCH_DIR,MASTER_BRANCH_NAME);
        writeContents(master_branch,initCommitID);

        //HEAD FILE
        writeContents(HEAD_DIR,MASTER_BRANCH_NAME);
    }

    public static void add(String fileName){
        File name=join(CWD,fileName);
        if (!name.exists()){
            System.out.println("File does not exist.");
            return;
        }else if (checkAddExisted(fileName)){
            return;
        }else if (fileRemoveStageExist(fileName)){
            File removeFile=join(REMOVE_DIR,fileName);
            removeFile.delete();
            return;
        } else if (!fileCommitTracked(fileName)) {
            //if commit Tracked the same content in this file,don`t add it into addStage

            /** creat the file snap and copy it into INDEX_DIR */
            Blobs blob=new Blobs(fileName);
            String blobName= blob.getBlobID();
            File blobFile=join(BLOBS_DIR,blobName);
            /** input the cwdFile`s content to the blobFile. */
            String blobContent=blob.getBlobContent();
            writeContents(blobFile,blobContent);

            /** copy the file into the ADD_DIR */
            File addFile=join(ADD_DIR,fileName);
            writeContents(addFile,blobContent);

        }

    }

    public static void commit(String message){
        /**Read addFileList */
        List<String> addFileList=plainFilenamesIn(ADD_DIR);

        /**Read removeFileList */
        List<String> removeFileList=plainFilenamesIn(REMOVE_DIR);

        /**Read cwdFileList*/
        List<String> cwdFileList=plainFilenamesIn(CWD);

        if (addFileList.isEmpty()&&removeFileList.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }else {

            HashMap<String,String> fileBlob=new HashMap<>();

            if (!addFileList.isEmpty()){
                /** create fileBlob hashmap */
                for (String fileName:addFileList) {
                    String blobID = sha1(readContentsAsString(
                            join(ADD_DIR,fileName)
                    ));
                    fileBlob.put(fileName, blobID);
                }

                for (String cwdFile:cwdFileList){
                    if (fileCommitTracked(cwdFile)){
                        String blobID = sha1(readContentsAsString(
                                join(CWD,cwdFile)
                        ));
                        fileBlob.put(cwdFile, blobID);
                    }
                }
            }

            //create new Commit
            String parentID=getParentID();
            Commit newCommit=new Commit(message,parentID,fileBlob);
            String newCommitID = writeNewCommit(newCommit);

            //upDateMaster
            upDateMaster(newCommitID);

            //clear the addStage
            addFileClear(addFileList);

            //clear the removeStage
            removeFileClear(removeFileList);
        }


    }

    public static void rm(String fileName){

        File addStageFile=join(ADD_DIR,fileName);

        if (addStageFile.exists()){
           addStageFile.delete();
        } else if (getCurrentCommit().getFileBlob().containsKey(fileName)){

            //file isn`t existed in cwd
            if (!join(CWD,fileName).exists()){

                String blobID=getCurrentCommit().getFileBlob().get(fileName);
                String blobContent=readContentsAsString(join(BLOBS_DIR,blobID));

                File removeFile=join(REMOVE_DIR,fileName);
                writeContents(removeFile, blobContent);
            }else {
                // Copy it into the removeStage
                File removeFile=join(REMOVE_DIR,fileName);
                writeContents(
                        removeFile,
                        readContentsAsString(join(CWD,fileName))
                );

                // Remove the target file in the cwd
                restrictedDelete(join(CWD,fileName));
            }

        }else {
            addStageFile.delete();
            System.out.println("No reason to remove the file.");
        }

    }

    public static void basic_Checkout(String fileName){
        //get current commitID
        String currentBranch=readContentsAsString(join(HEAD_DIR));
        String currentCommitID=readContentsAsString(join(BRANCH_DIR,currentBranch));

        //index the checkoutFile
        File currentCommit=join(COMMIT_DIR,currentCommitID);
        Commit checkoutFile=readObject(currentCommit,Commit.class);

        /** Change the cwdFile */
        if (!checkoutFile.getFileBlob().containsKey(fileName)){
            System.out.println("File does not exist in that commit.");
        }else {
            String blobID=checkoutFile.getFileBlob().get(fileName);
            File cwdFile=join(CWD,fileName);
            writeContents(cwdFile,
                    readContentsAsString(join(BLOBS_DIR,blobID))
                    );
        }
    }

    public static void prev_Checkout(String commitID,String fileName){

        List<String> commitIDList=plainFilenamesIn(COMMIT_DIR);

        //matching required commitID
        commitID=matchingCommit(commitID);

        File checkoutCommitFile=join(COMMIT_DIR,commitID);
        if (!checkoutCommitFile.exists()){
            System.out.println("No commit with that id exists.");
            return;
        } else if (!fileExistInCommit(commitID,fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }else if (!commitID_Exist_In_CommitList(commitIDList,commitID)){
            System.out.println("No commit with that id exists.");
            return;
        }

        //index the checkoutFile
        Commit checkoutCommit=readObject(checkoutCommitFile, Commit.class);

        if (!checkoutCommit.getFileBlob().containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
        } else {
            String blobID=checkoutCommit.getFileBlob().get(fileName);
            File cwdFile=join(CWD,fileName);
            writeContents(cwdFile,
                    readContentsAsString(join(BLOBS_DIR,blobID))
            );
        }
    }

    public static void CheckBranch(String branch) {
       List<String> branchList=plainFilenamesIn(BRANCH_DIR);

       if (!branchList.contains(branch)){
           System.out.println("No such branch exists.");
       } else if (getCurrentBranch().equals(branch)) {
           System.out.println("No need to checkout the current branch.");
       }else if (!branchTracked(branch)){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
       } else {
           //update the branch
           writeContents(join(HEAD_DIR),branch);

           //get current commit
           Commit currentCommit=getCurrentCommit();

           delete_Unessential_CwdFile(currentCommit) ;
           update_cwdFile(currentCommit);
       }
    }

    public static void status(){
        //Branch
        Branch();

        //Staged Files
        stageFile();

        //Removed Files
        removedFile();

        //Modifications Not Staged For Commit
        modifications();

        //Untracked Files
        untrackedFiles();
    }

    public static void log(){
        //get the all file in the commitStage
        initCommit();
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=readContentsAsString(HEAD);
        File HeadCommit=join(BRANCH_DIR,currentBranch);
        String CommitID=readContentsAsString(HeadCommit);

        for (int i=0;i<commitLinkedList.size();i++){

            if (CommitID == null){
                return;
            }

            int index=commitLinkedList.indexOf(CommitID);
            String FileName=commitLinkedList.get(index);
            File indexFile=join(COMMIT_DIR,FileName);
            Commit commit=readObject(indexFile,Commit.class);

            System.out.println("===");
            System.out.println("commit "+indexFile.getName());

            String FormatDate= DateFormat(commit.getTime());
            System.out.println("Date: "+FormatDate);

            System.out.println(commit.getMessage());
            System.out.println();
            CommitID=commit.getParentID();
        }
    }

    public static void global_log() {
        List<String> commitList=plainFilenamesIn(COMMIT_DIR);
        if (!commitList.isEmpty()) {
            for (String commitID:commitList){
                System.out.println("===");
                System.out.println("commit "+commitID);

                Commit commit=readObject(join(COMMIT_DIR,commitID), Commit.class);
                String FormatDate= DateFormat(commit.getTime());
                System.out.println("Date: "+FormatDate);

                System.out.println(commit.getMessage());
                System.out.println();
            }
        }
    }

    public static void find(String message) {
        List<String> commitList=plainFilenamesIn(COMMIT_DIR);

        if (!commitList.isEmpty()){
            // find all required commitIDs and add it into the List
            ArrayList<String> requireCommitIDList=new ArrayList<>();
            for (String commitID:commitList){
               Commit commit=readObject(join(COMMIT_DIR,commitID), Commit.class);
               String commitContent=commit.getMessage();

               if (commitContent.equals(message)){
                   requireCommitIDList.add(commitID);
               }
            }

            if (!requireCommitIDList.isEmpty()){
                //Print all required commitID
                for (String requiredCommitID : requireCommitIDList) {
                    System.out.println(requiredCommitID);
                }
            }else {
                System.out.println("Found no commit with that message.");
            }
        }

    }

    public static void branch(String branch) {
        //Create a new branch
        File newBranch=join(BRANCH_DIR,branch);
        if (newBranch.exists()){
            System.out.println("A branch with that name already exists.");
        }else {
            writeContents(newBranch,getCurrentCommitID());
        }
    }

    public static void rm_branch(String branch) {
        File removeBranch=join(BRANCH_DIR,branch);
        if (!removeBranch.exists()){
            System.out.println("A branch with that name does not exist.");
        }else if (getCurrentBranch().equals(branch)){
            System.out.println("Cannot remove the current branch.");
        } else {
            removeBranch.delete();
        }

    }

    public static void reset(String commitID) {
        if (!commitID_Exist_In_CommitList(commitID)){
            System.out.println("No commit with that id exists.");
        }else if(!stageTracked(commitID)){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
        } else {
            //Change the commitID in the current Branch File
            File currentBranchCommit=join(BRANCH_DIR,getCurrentBranch());
            writeContents(
                    currentBranchCommit,
                    commitID
            );

            //get current commit
            Commit currentCommit=getCurrentCommit();

            //change the cwdFile and clean stage
            delete_Unessential_CwdFile(currentCommit) ;
            update_cwdFile(currentCommit);
            stageClean();
        }
    }


    public static void merge(String branchName) {
        String currentBranchCommitID=getCurrentCommitID();
        File currentBranchCommitFile=join(COMMIT_DIR,currentBranchCommitID);
        Commit currentBranchCommit=readObject(currentBranchCommitFile, Commit.class);
        Collection<String> currentBranchFiles=currentBranchCommit.getFileBlob().keySet();

        if (getCurrentBranch().equals(branchName)) {//merge err check
            System.out.println("Cannot merge a branch with itself.");
        }else if(!checkBranchExist(branchName)){
            System.out.println("A branch with that name does not exist.");
        }else if (!branchTracked(branchName)){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
        }else if (!checkAddStageEmpty()){
            System.out.println("You have uncommitted changes.");
        }else {

            if (checkConflict(currentBranchFiles,branchName)){
                System.out.println("Encountered a merge conflict. ");
            }

            HashMap<String,String> mergeCommitHashmap = createMergeCommitHashmap(branchName);

            //create a newCommit (Name: mergeCommit)
            String secondCommit=readContentsAsString(join(BRANCH_DIR,branchName));
            Commit mergeCommit=new Commit(mergeMessage,getCurrentCommitID(),secondCommit,mergeCommitHashmap);
            String mergeCommitID=writeNewCommit(mergeCommit);

            //upDateMaster
            upDateMaster(mergeCommitID);

        }
        //Change the log part (adding: print the log of merging branch part until the nextCommit is SplitPoint)
    }
}
