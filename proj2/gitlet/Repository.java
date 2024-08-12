package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.BlobsUtils.*;
import static gitlet.CommitUtils.*;
import static gitlet.GitletPath.*;
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
        }

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

    public static void commit(String message){

        File[] addFILE=join(ADD_DIR).listFiles();
        if (addFILE == null) {
            System.out.println("The addStage is empty ");
            return;
        }else {
            HashMap<String,String> fileBlob=new HashMap<>();

            /** create fileBlob hashmap */
            for (File file:addFILE) {
                String blobID = sha1(readContentsAsString(file));
                fileBlob.put(file.getName(), blobID);
            }

            String parentID=getParentID();
            Commit newCommit=new Commit(message,parentID,fileBlob);
            String newCommitID = writeNewCommit(newCommit);

            //upDateMaster
            upDateMaster(newCommitID);

            //clear the addStage
            addFileClear(addFILE);
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

        File checkoutCommitFile=join(COMMIT_DIR,commitID);
        if (!checkoutCommitFile.exists()){
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

    public static void log(){
        //get the all file in the commitStage
        initCommit();
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=readContentsAsString(HEAD);
        File HeadCommit=join(BRANCH_DIR,currentBranch);
        String CommitID=readContentsAsString(HeadCommit);

        for (int i=0;i<commitLinkedList.size();i++){
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

}
