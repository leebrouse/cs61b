package gitlet;

import java.io.File;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class CommitUtils {
    public static String getCommitID(Commit commit) {
        byte[] commitCode = serialize(commit); // 序列化提交对象
        String commitID = sha1(commitCode);
        return commitID;
    }

    public static String getParentID(){
        File master=join(Branch_DIR,"master");
        String parent = readContentsAsString(master);
        return parent;
    }



}
