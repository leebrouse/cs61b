package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Repository.rm;
import static gitlet.Utils.*;

public class Stage {
    public static File addstage=join(GITLET_DIR,"addstage");
    public static File removalstage=join(GITLET_DIR,"removalstage");
    public static HashMap<String,String> addStage=new HashMap<>();
    public static HashMap<String,String> removalStage=new HashMap();

    public static void initaddStage(){
        File[] stagefile=join(addstage).listFiles();
        for (File file:stagefile){
            String fileName= file.getName();
            String blob=Utils.readContentsAsString(file);
            addStage.put(fileName,blob);
        }
    }
    public static void cleanAddStage(){
        File[] files=join(addstage).listFiles();
        for (File file:files){
            file.delete();
        }
        addStage.clear();
    }

    public static void initrmStage(){
        File[] stagefile=join(removalstage).listFiles();
        for (File file:stagefile){
            String fileName= file.getName();
            String blob=Utils.readContentsAsString(file);
            removalStage.put(fileName,blob);
        }
    }

    public static void cleanRmStage(){
        File[] stagefile=join(removalstage).listFiles();
        for (File rmfile:stagefile){
            rmfile.delete();
        }
        removalStage.clear();
    }

}
