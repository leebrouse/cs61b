package gitlet;

import java.io.File;
import java.util.Collection;
import java.util.List;

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

}
