package gitlet;
import java.io.File;

import static gitlet.GitletPath.*;
import static gitlet.Utils.*;

/**To save the file snaps */
public class Blobs {
    private String blobID;
    private String blobContent;

    public Blobs(String fileName){
        File cwdFile=join(CWD,fileName);
        String content = readContentsAsString(cwdFile);

        this.blobContent=content;
        // 使用sha1算法对文件内容进行哈希，生成唯一标识符
        this.blobID = sha1(content);
    }

    public String getBlobID() {
        return blobID;
    }

    public String getBlobContent() {
        return blobContent;
    }
}
