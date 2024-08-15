package gitlet;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import static gitlet.BlobsUtils.*;
import static gitlet.BranchUtils.*;
import static gitlet.CommitUtils.*;
import static gitlet.GitletPath.*;
import static gitlet.Utils.*;
import static java.lang.Math.abs;

public class MergeUtils {
    private String SplitPointCommit;
    public static final String mergeMessage="Merged other into master.";

    public static String conflictFileName;
    public static HashMap<String,String> createMergeCommitHashmap(String branchName){
        HashMap<String,String> requiredMap=new HashMap<>();

        String currentBranchCommitID=getCurrentCommitID();
        String mergeBranchCommitID=readContentsAsString(join(BRANCH_DIR,branchName));

        File currentBranchCommitFile=join(COMMIT_DIR,currentBranchCommitID);
        File mergeBranchCommitFile=join(COMMIT_DIR,mergeBranchCommitID);

        Commit currentBranchCommit=readObject(currentBranchCommitFile, Commit.class);
        Commit mergeBranchCommit=readObject(mergeBranchCommitFile, Commit.class);

        Collection<String> currentBranchFiles=currentBranchCommit.getFileBlob().keySet();
        Collection<String> mergeBranchFiles=mergeBranchCommit.getFileBlob().keySet();

        checkConflict(currentBranchFiles, branchName);
        mergeBranchFiles.remove(conflictFileName);

        for (String mergeBranchFile:mergeBranchFiles){
            if (!currentBranchFiles.contains(mergeBranchFile)){
                String blobID=mergeBranchCommit.getFileBlob().get(mergeBranchFile);

                //creat cwdFile that isn`t existed in mergeBranch
                File cwdFile=join(CWD,mergeBranchFile);
                writeContents(cwdFile,readBlob(blobID));

            } else {
               String currentBranchFileBlobID=currentBranchCommit.getFileBlob().get(mergeBranchFile);
               String mergeBranchFileBlobID=mergeBranchCommit.getFileBlob().get(mergeBranchFile);

               if (!currentBranchFileBlobID.equals(mergeBranchFileBlobID)){
                   String mergeBranchFileBlobContent=readBlob(mergeBranchFileBlobID);
                   writeContents(join(CWD,mergeBranchFile),mergeBranchFileBlobContent);

               }
            }
        }

        return requiredMap;
    }

    public static boolean checkConflict(Collection<String> currentBranchFiles, String branchName){
        String SplitPointID=findSplitPointID(getCurrentBranch(),branchName);
        Commit SplitPointCommit=readObject(join(COMMIT_DIR,SplitPointID), Commit.class);

        Commit currentCommit=getCurrentCommit();

        Collection<String> SplitPointCommitList=SplitPointCommit.getFileBlob().keySet();
        for (String currentBranchFile:currentBranchFiles){
            if (SplitPointCommitList.contains(currentBranchFile)){

                String SplitPointBlobID=SplitPointCommit.getFileBlob().get(currentBranchFile);
                String currentBranchBlobID=currentCommit.getFileBlob().get(currentBranchFile);

                String mergeBlobID;
                Commit mergeCommit=readObject(join(COMMIT_DIR,readContentsAsString(join(BRANCH_DIR,branchName))),Commit.class);
                if (mergeCommit.getFileBlob().containsKey(currentBranchFile)){
                    mergeBlobID = mergeCommit.getFileBlob().get(currentBranchFile);
                }else {
                    mergeBlobID=null;
                }

                String conFlictContent="<<<<<<< HEAD\n" +
                        readBlob(currentBranchBlobID) +
                        "=======\n" +
                        readBlob(mergeBlobID)+
                        ">>>>>>>";

                if (!SplitPointBlobID.equals(currentBranchBlobID)){
                    System.out.println("Encountered a merge conflict.");

                    writeContents(join(CWD,currentBranchFile),conFlictContent);

                    conflictFileName=currentBranchFile;
                    return true;
                }
            }
        }
        return false;
    }


    /** get branch commit length */
    public static int getBranchLength(String branch){
        int length=0;

        String branchStartCommitID=readContentsAsString(join(BRANCH_DIR,branch));
        Commit branchStartCommit=getCommitObject(branchStartCommitID);

        while (true){
            //get the next
            branchStartCommitID=branchStartCommit.getParentID();

            if ( branchStartCommitID==null){
                return length;
            }

            branchStartCommit=getCommitObject(branchStartCommitID);
            length++;

        }

    }

    /** find SplitPoint Commit object  */
    public static Commit findSplitPointCommit(Commit currentBranchCommit,Commit mergeBranchCommit){
        while (true){
            if (currentBranchCommit.getParentID().equals(mergeBranchCommit.getParentID())){
                return getCommitObject(currentBranchCommit.getParentID());
            }

            currentBranchCommit=getCommitObject(currentBranchCommit.getParentID());
            mergeBranchCommit=getCommitObject(mergeBranchCommit.getParentID());
        }
    }

    /** maybe currentBranchLength>=mergeBranchLength,two commitList should have same beginning  */
    public static String findSplitPointID(String currentBranch,String mergeBranch){
        //get currentBranchCommit beginning point;
        String currentBranchCommitID=readContentsAsString(join(BRANCH_DIR,currentBranch));
        Commit currentBranchCommit=getCommitObject(currentBranchCommitID);

        //get mergeBranchCommit beginning point;
        String mergeBranchCommitID=readContentsAsString(join(BRANCH_DIR,mergeBranch));
        Commit mergeBranchCommit=getCommitObject(mergeBranchCommitID);

        int currentBranchLength=getBranchLength(currentBranch);
        int mergeBranchLength=getBranchLength(mergeBranch);
        int deltaLength=abs(currentBranchLength-mergeBranchLength);

        // currentBranchLength is longer ,it go first.
        if (currentBranchLength>=mergeBranchLength){

            for (int i=0;i<deltaLength;i++){
                currentBranchCommit=getCommitObject(currentBranchCommit.getParentID());
            }
            Commit SplitPoint=findSplitPointCommit(currentBranchCommit,mergeBranchCommit);
            return getCommitID(SplitPoint);
        }else {

            for (int i=0;i<deltaLength;i++){
                mergeBranchCommit=getCommitObject(mergeBranchCommit.getParentID());
            }
            Commit SplitPoint=findSplitPointCommit(currentBranchCommit,mergeBranchCommit);
            return  getCommitID(SplitPoint);
        }
    }
}
