package gitlet;

import java.io.File;

import static gitlet.Repository.*;
import static gitlet.Stage.*;
import static gitlet.Utils.join;

public class Status {
    public static void Branch(){
        File[] branch=join(Branch_DIR).listFiles();
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=Utils.readContentsAsString(HEAD);
        System.out.println("=== Branches ===");
        for (File Branch:branch){
            String branchID=Branch.getName();
            if (Branch.getName().equals(currentBranch)){
                System.out.println("*"+branchID);
            }else {
                System.out.println(branchID);
            }
        }
        System.out.println();
    }

    public static void StageFile(){
        File [] StageFile=join(addstage).listFiles();
        System.out.println("=== Staged Files ===");
        for (File file:StageFile){
            System.out.println(file.getName());
        }
        System.out.println();
    }

    public static void RemovedFile(){
        File [] RemovedFile=join(removalstage).listFiles();
        System.out.println("=== Removed Files ===");
        for (File file:RemovedFile){
            System.out.println(file.getName());
        }
        System.out.println();
    }

    public static void Modifications(){
        //考虑中.....
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
    }

    public static void UntrackedFiles(){

        File[] UntrackedFiles=join(CWD).listFiles();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }
}
