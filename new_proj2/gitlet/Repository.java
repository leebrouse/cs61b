package gitlet;

import java.io.File;

import static gitlet.BlobsUtils.*;
import static gitlet.CommitUtills.*;
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
        String  commitID = getCommitID(initCommit);
        File initcommitFile=join(COMMIT_DIR,commitID);
        Utils.writeObject(initcommitFile,initCommit);

        //removeStage
        REMOVE_DIR.mkdir();

        //branch
        BRANCH_DIR.mkdir();

        //HEAD FILE

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

    }

}
