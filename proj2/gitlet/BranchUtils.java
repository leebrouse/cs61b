package gitlet;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static gitlet.GitletPath.BLOBS_DIR;
import static gitlet.GitletPath.CWD;
import static gitlet.Utils.*;

public class BranchUtils {
    /** To delete unessential cwdFile  */
    public static void delete_Unessential_CwdFile(List<String> cwdFileList,Commit currentCommit){
        for (String cwdFileName:cwdFileList ){
            //delete the unessential cwdFile
            if (!currentCommit.getFileBlob().containsKey(cwdFileName)) {
                join(CWD, cwdFileName).delete();
            }
        }
    }

    /** To update the cwdFile for checkout Branch*/
    public static void update_cwdFile( Collection<String> commitFileSets,Commit currentCommit){
        for (String commitFileName:commitFileSets ){
            //check the master pointing commitFile,and replace the cwdFile
            File newCwdFile=join(CWD,commitFileName);
            String blobID=currentCommit.getFileBlob().get(commitFileName);
            writeContents(newCwdFile,
                    readContentsAsString(join(BLOBS_DIR,blobID))
            );
        }
    }
}
