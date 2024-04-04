package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;

public class Blobs {
    public static final File Index_DIR = join(GITLET_DIR, ".index");
    //save_File_code
    private static HashMap<String,String> saveCode=new HashMap<>();
    public boolean makeIndex(String fileName){
        // 创建一个指向指定文件名的File对象
        File name= new File(fileName);

        // 检查文件是否存在
        if (!name.exists()){
            System.out.println("File does not exist.");
            return false;
        }else {
            // 读取文件内容为字符串
            String content = Utils.readContentsAsString(name);
            // 使用sha1算法对文件内容进行哈希，生成唯一标识符
            String hashcode = sha1(content);

            // 检查该文件的哈希值是否已经被记录在saveCode中，如果没有，表明是新文件或文件内容有更改
            if (!saveCode.containsKey(hashcode)) {
                // 根据哈希值的前两个字符，创建或获取目录
                File blobs = join(Index_DIR, hashcode);

                // 将原文件的内容写入到这个新文件中
                writeObject(blobs, name);

                // 将文件的哈希值加入到saveCode集合中，表示已处理
                saveCode.put(hashcode,fileName);
                return true;
            }
        }
        return false;
    }

}

