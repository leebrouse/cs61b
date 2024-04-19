package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static gitlet.Blobs.*;
import static gitlet.Commit.*;
import static gitlet.Stage.*;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author  Leebrouse
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** File Format : .gitlet/
     *              Store/
     *              Commit/
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static File Branch_DIR=join(GITLET_DIR,"Branch");

    public static File HEAD=join(GITLET_DIR,"HEAD");



    /* TODO: fill in the rest of this class. */
    public static void init(){
        if (!GITLET_DIR.mkdir()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }

        Index_DIR.mkdir();
        addstage.mkdir();
        removalstage.mkdir();
        writeContents(HEAD,"master");

        Commit_DIR.mkdir();
        Branch_DIR.mkdir();

        Commit initCommit = new Commit();
        String commitID = CommitUtils.getCommitID(initCommit);

        File master=join(Branch_DIR,"master");
        File initcommitFile=join(Commit_DIR,commitID);

        Utils.writeContents(master,commitID);
        Utils.writeObject(initcommitFile,initCommit);
    }

    public static void add(String fileName){
        /**思路：
            1.同过判断是否为完全的hashcode，判断是否完全相同的file，true则不进行操作。
            2.当使用add 时要把文件进行hashcode，并把副本放入blobs，在把副本存入addstage
            3.当commit时清空addstage区
            4.stage区有两个区add和remove
        **/
      //如果cwd中的文件有与commit区中的相同则不能被add，test18-nop-add
        initCommit();
        File name=join(CWD,fileName);
        if (!name.exists()){
            System.out.println("File does not exist.");
            return;
        }

        if (addJudge(fileName)){
            return;
        }

        Blobs blob=new Blobs();
        if (blob.makeIndex(fileName)){
            File addfile=join(addstage,fileName);
            String content = Utils.readContentsAsString(name);
            String hashcode = sha1(content);
            writeContents(addfile,hashcode);
        }

    }

    public static void commit(String message) {
        // 读取 addstage，文件放入 commit，清空 addstage;
        Stage.initaddStage();
        Stage.initrmStage();
        HashMap<String, String> fileVersionMap=addStage;
        HashMap<String,String> rmStage=removalStage;

        if (fileVersionMap.isEmpty()&&rmStage.isEmpty()){
            System.out.println("No changes added to the commit.");
            return;
        } else if (message.isEmpty()) {
            System.out.println("Please enter a commit message.");
            return;
        }if (!rmStage.isEmpty()){
            String parent = CommitUtils.getParentID(); // 获取最后一个元素作为父节点
            Commit newCommit = new Commit(message, parent,fileVersionMap);

            // 更新 saveCommit 的状态，确保保存新的 commitID
            String commitID = CommitUtils.getCommitID(newCommit);

            File commitFile = Utils.join(Commit_DIR, commitID);
            Utils.writeObject(commitFile, newCommit);

            //读取HEAD的当前分支
            File HEAD=join(GITLET_DIR,"HEAD");
            String currentBranch=Utils.readContentsAsString(HEAD);
            File HeadCommit=join(Branch_DIR,currentBranch);
            Utils.writeContents(HeadCommit,commitID);
            Stage.cleanRmStage();
            return;
        }

        String parent = CommitUtils.getParentID(); // 获取最后一个元素作为父节点
        Commit newCommit = new Commit(message, parent,fileVersionMap);

        // 更新 saveCommit 的状态，确保保存新的 commitID
        String commitID = CommitUtils.getCommitID(newCommit);

        File commitFile = Utils.join(Commit_DIR, commitID);
        Utils.writeObject(commitFile, newCommit);

        //读取HEAD的当前分支
        File HEAD=join(GITLET_DIR,"HEAD");
        String currentBranch=Utils.readContentsAsString(HEAD);
        File HeadCommit=join(Branch_DIR,currentBranch);
        Utils.writeContents(HeadCommit,commitID);
        Stage.cleanAddStage();
    }

    public static void rm(String fileName){
        //Have bug in this method

        Stage.initaddStage();
        Commit.initCommit();
        File removeFile=join(CWD,fileName);
        if (!removeFile.exists()){
            //Have bug

            File rmStageFile=join(removalstage,fileName);
            writeContents(rmStageFile);
        }else if (addStage.containsKey(fileName)){
            File file=join(addstage,fileName);
            file.delete();
        }else if (Commit.judgeCommit(fileName)){
            File rmStageFile=join(removalstage,fileName);
            String rmContent=readContentsAsString(removeFile);
            writeContents(rmStageFile,rmContent);
            removeFile.delete();
        }else {
            System.out.println("No reason to remove the file.");
        }

    }

    public static void basic_Checkout (String fileName){
        Commit.initCommit();
        File file = join(CWD, fileName);

        for (int i = 0; i < commitList.size(); i++){
            String FileName = commitList.get(i);
            File indexFile = join(Commit_DIR, FileName);
            Commit commit = readObject(indexFile, Commit.class);
            String NeedContent = Commit.readFilename(commit, fileName);
            if (NeedContent != null){
                Utils.writeContents(file, NeedContent);
                return; // 在找到内容后直接返回
            }
        }
        // 如果没有找到内容，最后返回 null
        System.out.println("File not found in any commits.");
    }

    public  static void prev_Checkout(String commitID,String fileName){
        /**1.找到所要的commitID的位置
         * 2.读其hashmap的指定文件数据
         * 3.写回cwd中的同名文件
         */
        //读commit的文件（初始化）
        Commit.initCommit();
        //cwd中的同名文件
        File file = join(CWD, fileName);

        for (int i = 0; i < commitList.size(); i++){
            if (!commitList.contains(commitID)){
                System.out.println("No commit with that id exists.");
                return;
            }
            int indexLocate=commitList.indexOf(commitID);
            String commitName=commitList.get(indexLocate);
            File indexFile=join(Commit_DIR,commitName);
            Commit commit = readObject(indexFile, Commit.class);
            String NeedContent = Commit.readFilename(commit, fileName);
            if (NeedContent != null){
                Utils.writeContents(file, NeedContent);
                return; // 在找到内容后直接返回
            }
        }
        System.out.println("File does not exist in that commit.");
    }

    public static void CheckBranch(String branch){
        //Judge the branch is existed or not
       File Branches=join(Branch_DIR,branch);
       if (!Branches.exists()){
           System.out.println("No such branch exists.");
           return;
       }

        if (Utils.readContentsAsString(HEAD).equals(branch)){
            System.out.println("No need to checkout the current branch.");
            return;
        }

        //Has bug;
        File Head=join(GITLET_DIR,"HEAD");
        writeContents(Head,branch);
        String Branch=Utils.readContentsAsString(HEAD);

        File currentBranch=join(Branch_DIR,Branch);
        String currentCommitID=Utils.readContentsAsString(currentBranch);
        Commit.initCommit();

            if (!commitList.contains(currentCommitID)){
                System.out.println("No commit with that id exists");
                return;
            }
            int indexLocate=commitList.indexOf(currentCommitID);
            String commitName=commitList.get(indexLocate);
            File indexFile=join(Commit_DIR,commitName);
            Commit commit = readObject(indexFile, Commit.class);
            Commit.readBranch(commit);
    }

    public static void branch(String Branch){
        File otherBranch=join(Branch_DIR,Branch);
        if (otherBranch.exists()){
            System.out.println("A branch with that name already exists.");
        }else {
            File currentBranch = join(Branch_DIR, "master");
            String currentCommitID = Utils.readContentsAsString(currentBranch);
            writeContents(otherBranch, currentCommitID);
        }
    }

    public static void rm_branch(String rmBranch){
        File rm_Branch=join(Branch_DIR,rmBranch);
        String currentBranch=Utils.readContentsAsString(HEAD);

        if (rmBranch.equals(currentBranch)){
            System.out.println("Cannot remove the current branch.");
        } else if (!rm_Branch.exists()) {
            System.out.println("A branch with that name does not exist.");
        }else {
            rm_Branch.delete();
        }

    }


    public static void log(){
        //读取Head标记的commitID，找到在commit dir的位置，放问commit.parentID，循环反复。
        Commit.printCommit();
    }


    public static void global_log(){
        List<String>commitList=Utils.plainFilenamesIn(Commit_DIR);
        if (commitList == null || commitList.isEmpty()) {
            return;
        }
        for (String commitId : commitList) {
            Commit.globalPrint(commitId);
        }
    }

    public static void find(String message){
       Commit.findHelper(message);
    }

    public static void status(){
        File initDir=join(CWD,".gitlet");
        if (!initDir.mkdir()) {
            Status.Branch();
            Status.StageFile();
            Status.RemovedFile();
            Status.Modifications();
            Status.UntrackedFiles();
        }else {
            System.out.println("Not in an initialized Gitlet directory.");
        }
    }




}
