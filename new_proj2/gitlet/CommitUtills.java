package gitlet;
import java.io.File;

import static gitlet.GitletPath.*;
import static gitlet.Utils.*;
import static gitlet.Utils.readContentsAsString;

public class CommitUtills {
    public static String getCommitID(Commit commit) {
        byte[] commitCode = serialize(commit); // 序列化提交对象
        String commitID = sha1(commitCode);
        return commitID;
    }

    public static String getParentID(){
        File master=join(BRANCH_DIR,"master");
        String parent = readContentsAsString(master);
        return parent;
    }
}
