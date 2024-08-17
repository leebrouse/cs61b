package gitlet;

import java.io.File;
import java.util.List;

import static gitlet.GitletPath.*;
import static gitlet.Utils.*;

public class BlobsUtils {

    public static boolean checkAddExisted(String fileName){

        File addFile =join(ADD_DIR,fileName);
        if (addFile.exists()){
            return true;
        }

        addFile.delete();
        return false;
    }

    public static boolean checkRemoveExisted(String fileName){

        File removeFile =join(REMOVE_DIR,fileName);
        if (removeFile.exists()){
            return true;
        }

        removeFile.delete();
        return false;
    }

    public static boolean checkAddStageEmpty(){
        List<String> addStageList=plainFilenamesIn(ADD_DIR);

        if (addStageList.isEmpty()){
            return true;
        }
        return false;
    }


    public static boolean checkReadBlob(String blobID){
        return readBlob(blobID) != null;
    }

    public static String readBlob(String blobID){
        if (blobID==null){
            return null;
        }
        return readContentsAsString(join(BLOBS_DIR,blobID));
    }
}
