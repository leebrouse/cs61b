package gitlet;

import java.io.File;

import static gitlet.Commit.Commit_DIR;
import static gitlet.Repository.CWD;
import static gitlet.Repository.GITLET_DIR;
import static gitlet.Stage.*;
import static gitlet.Utils.*;

public class Remove {
    public static boolean judgeStage(String fileName){
        if (addStage.containsKey(fileName)){
            return true;
        }
        return false;
    }

}
