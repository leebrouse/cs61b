package gitlet;

import java.io.File;
import java.util.LinkedList;
import static gitlet.Utils.*;



public class CommitUtils {
    public static String getCommitID(Commit commit) {
        byte[] commitCode = serialize(commit); // 序列化提交对象
        String commitID = sha1(commitCode);
        return commitID;
    }

}
