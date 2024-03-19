package gitlet;

import java.io.File;
import java.io.Serializable;

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

    public static final File Store_DIR = join(GITLET_DIR, "Store");
    public static final File Commit_DIR = join(GITLET_DIR, "Commit");

    /* TODO: fill in the rest of this class. */
    public static void initgit(){
        if (!GITLET_DIR.mkdir()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }

        Store_DIR.mkdir();
        Commit_DIR.mkdir();

        Commit commit=new Commit();
        File initcommitFile=join(Commit_DIR,"initialCommit");
        Utils.writeObject(initcommitFile,commit);
    }

    public static void add(File name){
        if (name.exists()) {
            File storeFile = join(Store_DIR, name.toString());
            Utils.writeContents(storeFile, Utils.readContents(name));
        }else {
            System.out.println("File does not exist.");
        }
//       Utils.readObject();
    }
}
