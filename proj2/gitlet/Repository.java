package gitlet;

import java.io.File;


import static gitlet.Blobs.Index_DIR;
import static gitlet.Commit.Commit_DIR;
import static gitlet.Stage.Info_DIR;
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

    //Two pointer of the commit: 1.Master,2.Head
    public static Commit master,head;

    /* TODO: fill in the rest of this class. */
    public static void init(){
        if (!GITLET_DIR.mkdir()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }

        Commit_DIR.mkdir();
        Index_DIR.mkdir();
        Info_DIR.mkdir();

        Commit initcommit=new Commit();
        master=initcommit;
        head=initcommit;

        File initcommitFile=join(Commit_DIR,"initialCommit");
        Utils.writeObject(initcommitFile,initcommit);
    }

    public static void add(String fileName){
        /**思路：
            1.同过判断是否为完全的hashcode，判断是否完全相同的file，true则不进行操作。
            2.当使用add 时要把文件进行hashcode，并把副本放入blobs，在把副本存入addstage
            3.当commit时清空addstage区
            4.stage区有两个区add和remove
        **/
      Blobs blob=new Blobs();
      if (blob.makeindex(fileName)){
          Stage addStage=new Stage();
          addStage.setAddStage(fileName);
      }

    }

    public static void commit(String message) {
        if(Stage.addStage.isEmpty()){
            System.out.println("No changes added to the commit.");
        }else if (message==null){
            System.out.println("Please enter a commit message");
        }else{
            Commit newcommit=new Commit(message);
            newcommit.addCommit();
        }
    }

    public static void checkout (String message){

    }

}
