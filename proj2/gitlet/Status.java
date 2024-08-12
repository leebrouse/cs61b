package gitlet;

import java.util.List;

import static gitlet.GitletPath.*;
import static gitlet.Utils.plainFilenamesIn;
import static gitlet.Utils.readContentsAsString;

public class Status {
    public static void branch(){
        //Branch
        String currentBranch=readContentsAsString(HEAD_DIR);
        List<String> BranchList=plainFilenamesIn(BRANCH_DIR);
        System.out.println("=== Branches ===");
        for (String branchFileName:BranchList){
            if (branchFileName.equals(currentBranch)){
                System.out.println("*"+branchFileName);
            }else {
                System.out.println(branchFileName);
            }
        }
        System.out.println();
    }

    public static void stageFile(){
        List<String> addList=plainFilenamesIn(ADD_DIR);
        System.out.println("=== Staged Files ===");
        if (addList != null) {
            for (String addFileName:addList){
                System.out.println(addFileName);
            }
        }
        System.out.println();
    }

    public static void removedFile(){
        List<String> removeList=plainFilenamesIn(REMOVE_DIR);
        System.out.println("=== Removed Files ===");
        if (removeList != null) {
            for (String removeFileName:removeList){
                System.out.println(removeFileName);
            }
        }
        System.out.println();
    }

    public static void modifications(){
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
    }

    public static void untrackedFiles(){
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }
}
