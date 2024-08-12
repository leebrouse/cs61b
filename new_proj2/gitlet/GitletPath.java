package gitlet;

import java.io.File;

import static gitlet.Utils.join;

public class GitletPath {
    public static final String MASTER_BRANCH_NAME = "master";
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /**The add directory. */
    public static final File ADD_DIR = join(GITLET_DIR, "addStage");

    /** The remove directory. */
    public static final File REMOVE_DIR = join(GITLET_DIR, "removeStage");

    /** The blobs directory. */
    public static final File BLOBS_DIR = join(GITLET_DIR, ".index");

    /** The commit directory. */
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    /** The Branch directory. */
    public static final File BRANCH_DIR = join(GITLET_DIR, "Branch");

}
