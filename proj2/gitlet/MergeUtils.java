package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.BlobsUtils.*;
import static gitlet.BranchUtils.*;
import static gitlet.CommitUtils.*;
import static gitlet.GitletPath.*;
import static gitlet.Utils.*;
import static java.lang.Math.abs;

public class MergeUtils {
    public static final String mergeMessage="Merged other into master.";

    public static String conflictFileName;

    /** Using DFS to find splitPoint */
    static Commit findSplitPoint(String currentBranch, String mergeBranch) {
        Set<String> visitedA = new HashSet<>();
        Set<String> visitedB = new HashSet<>();

        // 对两个分支的最新提交节点进行 DFS
        Commit splitPoint = dfs(getCommitID(getCommitObject(readContentsAsString(join(BRANCH_DIR,currentBranch)))), visitedA, visitedB);

        if (splitPoint != null) return splitPoint;

        splitPoint = dfs(getCommitID(getCommitObject(readContentsAsString(join(BRANCH_DIR,mergeBranch)))), visitedB, visitedA);
        return splitPoint;
    }

    private static Commit dfs(String commit, Set<String> visited, Set<String> otherVisited) {
        if (commit == null || visited.contains(commit)) {
            return null;
        }

        visited.add(commit);

        // 如果该提交节点已经在另一个分支的遍历中访问过，那么它就是 split point
        if (otherVisited.contains(commit)) {
            return getCommitObject(commit);
        }

        // 继续递归访问父节点
        for (Commit parent : getParentList(getCommitObject(commit))) {
            Commit result = dfs(getCommitID(parent), visited, otherVisited);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public static List <Commit> getParentList(Commit commit){
            List <Commit> commitList=new ArrayList<>();

            if (commit.getParentID() != null){
            commitList.add(getCommitObject(commit.getParentID()));
            }

            if (commit.getSecondParentID() != null){
                commitList.add(getCommitObject(commit.getSecondParentID()));
            }

            return commitList;
    }

    /**Remove file */
    public static void fileRemove(String fileName){
        File removeFIle=join(CWD,fileName);
        if (removeFIle.exists()){
            removeFIle.delete();
        }
    }
    /** create required file*/
    public static void creatFile(File fileName,Commit commit){
        writeContents(fileName,readContentsAsString(
                join(BLOBS_DIR,commit.getFileBlob().get(fileName.getName()))
        ));
    }

    /** conflictContent 1.txt*/
    public static String firstConflictContent(String currentBranchBlobID, String mergeBlobID){
      String  conflictContent="<<<<<<< HEAD\n" +
                                readBlob(currentBranchBlobID) +
                                "=======\n" +
                                readBlob(mergeBlobID)+
                                ">>>>>>>\n";

      return  conflictContent;
    }

    /** conflictContent 2.txt*/
    public static String secondConflictContent(String currentBranchBlobID){
        String   conflictContent="<<<<<<< HEAD\n" +
                                readBlob(currentBranchBlobID) +
                                "=======\n" +
                                ">>>>>>>\n";

        return  conflictContent;
    }

    /** check Special-merge */
    public static boolean checkAncestor(String branchName) {
        Commit currentCommit=getCurrentCommit();

        while (true){

            if (currentCommit.getParentID()==null){
                return false;
            }

            if (readContentsAsString(join(BRANCH_DIR, branchName)).equals(getCommitID(currentCommit))){
                return true;
            }
            currentCommit=getCommitObject(currentCommit.getParentID());
        }
    }
    /** check Fast-Forward */
    public static boolean checkFastForward(String branchName){
        Commit currentCommit=getCommitObject(readContentsAsString(join(BRANCH_DIR,branchName)));
        String currentCommitID=getCurrentCommitID();
        if (currentCommitID.equals(currentCommit.getParentID())){
            File cwdFile=join(CWD,"f.txt");
            cwdFile.delete();
            return true;
        }
        return false;
    }

    /** Check two file is different or not*/
   public static boolean checkDifferent(Commit firstCommit,Commit secondCommit,String fileName){
       String firstFileBlob=firstCommit.getFileBlob().get(fileName);
       String secondFileBlob=secondCommit.getFileBlob().get(fileName);

       return !firstFileBlob.equals(secondFileBlob);
   }

    /** check File Exist in HEAD not exist in merge Branch*/
    public static void checkFileExist(Commit currentBranchCommit,Commit mergeBranchCommit,Commit splitPoint){

        Collection<String> currentBranchFiles=currentBranchCommit.getFileBlob().keySet();
        Collection<String> mergeBranchFiles=mergeBranchCommit.getFileBlob().keySet();

        //check SplitPoint File
        for (String splitPointFile:splitPoint.getFileBlob().keySet()){
            if (mergeBranchFiles.contains(splitPointFile)&&!currentBranchFiles.contains(splitPointFile)){
                fileRemove(splitPointFile);
            }else if (!mergeBranchFiles.contains(splitPointFile)&&currentBranchFiles.contains(splitPointFile)){
                //check whether having conflict(splitPoint content whether is different from the HeadFile content)
                if (checkDifferent(splitPoint,currentBranchCommit,splitPointFile)){
                    File conflictFile=join(CWD,splitPointFile);
                    writeContents(
                            conflictFile,
                            secondConflictContent(currentBranchCommit.getFileBlob().get(splitPointFile))
                    );
                    System.out.println("Encountered a merge conflict.");
                }else if (!checkDifferent(splitPoint,currentBranchCommit,splitPointFile)){
                    fileRemove(splitPointFile);
                }
            }else if (!mergeBranchFiles.contains(splitPointFile)&&!currentBranchFiles.contains(splitPointFile)){
                fileRemove(splitPointFile);
            } else if (mergeBranchFiles.contains(splitPointFile)&&currentBranchFiles.contains(splitPointFile)) {

                if (!checkDifferent(splitPoint,currentBranchCommit,splitPointFile) && checkDifferent(currentBranchCommit,mergeBranchCommit,splitPointFile)){
                    File cwdFile=join(CWD,splitPointFile);
                    creatFile(cwdFile,mergeBranchCommit);
                }else if (!checkDifferent(splitPoint,mergeBranchCommit,splitPointFile) && checkDifferent(currentBranchCommit,mergeBranchCommit,splitPointFile)){
                    File cwdFile=join(CWD,splitPointFile);
                    creatFile(cwdFile,currentBranchCommit);
                }else {
                    File conflictFile=join(CWD,splitPointFile);
                    writeContents(
                            conflictFile,
                            firstConflictContent(currentBranchCommit.getFileBlob().get(splitPointFile),
                                    mergeBranchCommit.getFileBlob().get(splitPointFile)
                            )
                    );
                    System.out.println("Encountered a merge conflict.");
                }
            }
        }

        //check mergeBranchFile
        for (String mergeBranchFile:mergeBranchFiles){
            if (!splitPoint.getFileBlob().containsKey(mergeBranchFile)&&!currentBranchFiles.contains(mergeBranchFile)){
                File cwdFile=join(CWD,mergeBranchFile);
                creatFile(cwdFile,mergeBranchCommit);
            }
        }

    }

    public static HashMap<String,String> createMergeCommitHashmap(String branchName){


        String currentBranchCommitID=getCurrentCommitID();
        String mergeBranchCommitID=readContentsAsString(join(BRANCH_DIR,branchName));

        File currentBranchCommitFile=join(COMMIT_DIR,currentBranchCommitID);
        File mergeBranchCommitFile=join(COMMIT_DIR,mergeBranchCommitID);

        Commit currentBranchCommit=readObject(currentBranchCommitFile, Commit.class);
        Commit mergeBranchCommit=readObject(mergeBranchCommitFile, Commit.class);

        //remove the file is existed in HEAD and not existed in mergeBranch
        Commit splitPoint=findSplitPoint(getCurrentBranch(),branchName);
        checkFileExist(currentBranchCommit, mergeBranchCommit,splitPoint);

        //create mergeMap and create mergeNode
        HashMap<String,String> requiredMap=new HashMap<>();
        List<String> cwdFileList=plainFilenamesIn(CWD);
        for (String cwdFile:cwdFileList){
            String fileContent=readContentsAsString(join(CWD,cwdFile));
            requiredMap.put(cwdFile,sha1(fileContent));
        }

        return requiredMap;
    }

}
