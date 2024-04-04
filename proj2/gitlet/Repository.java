package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;


import static gitlet.Blobs.Index_DIR;
import static gitlet.Commit.Commit_DIR;
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
    public static File addstage=join(GITLET_DIR,"addstage");
    public static File removalstage=join(GITLET_DIR,"removalstage");
    public static File HEAD;
    private static Commit Head;
    private static LinkedList<String> saveCommit=new LinkedList<>();

    /* TODO: fill in the rest of this class. */
    public static void init(){
        if (!GITLET_DIR.mkdir()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }

        //branch.mkdir();

        addstage.mkdir();
        removalstage.mkdir();

        HEAD=join(GITLET_DIR,"HEAD");
        writeContents(HEAD,"*master");

        Commit_DIR.mkdir();
        Index_DIR.mkdir();

        Commit initCommit = new Commit("Initial commit", null);
        String commitID = CommitUtils.getCommitID(initCommit);
        Head=initCommit;

        File initcommitFile=join(Commit_DIR,commitID);
        Utils.writeObject(initcommitFile,initCommit);
    }

    public static void add(String fileName){
        /**思路：
            1.同过判断是否为完全的hashcode，判断是否完全相同的file，true则不进行操作。
            2.当使用add 时要把文件进行hashcode，并把副本放入blobs，在把副本存入addstage
            3.当commit时清空addstage区
            4.stage区有两个区add和remove
        **/

      Blobs blob=new Blobs();
      File name=join(CWD,fileName);

      if (blob.makeIndex(fileName)){
          File addfile=join(addstage,fileName);
          writeObject(addfile,name);
      }

    }

    public static void commit(String message) {
        // 读取 addstage，文件放入 commit，清空 addstage;
        File[] commit= join(Commit_DIR).listFiles();
        for (File file:commit){
            if (file==null){
                break;
            }
            String filename=file.getName();
            saveCommit.addLast(filename);
        }

        String parent = saveCommit.getLast(); // 获取最后一个元素作为父节点
        Commit newCommit = new Commit(message, parent);

        // 更新 saveCommit 的状态，确保保存新的 commitID
        String commitID = CommitUtils.getCommitID(newCommit);

        File commitFile = Utils.join(Commit_DIR, commitID);
        Utils.writeObject(commitFile, newCommit);
        Head = newCommit;
    }


    public static void checkout (String message){

    }

}
