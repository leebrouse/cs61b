package gitlet;

import java.io.File;
import java.util.HashSet;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.join;

public class Stage {
    public static HashSet<File> addStage=new HashSet<>();
    public static HashSet<File> removalStage=new HashSet<>();


    public void setAddStage(String fileName){
        File addfile=new File(fileName);
        addStage.add(addfile);
    }

    public void setRemovalStage(String fileName){
        File addfile=new File(fileName);
        removalStage.add(addfile);
    }

}
