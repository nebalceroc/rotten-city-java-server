package FileIO;

import java.io.File;

/**
 *
 * @author Nicolas
 */
public class FileIO {

    public void createFolders() {
        deleteWithChildren("src/ServerIO");
        File root = new File("src/ServerIO");
        root.mkdir();
        root = new File("src/ServerIO/Login/");
        root.mkdir();
        root = new File("src/ServerIO/Pjs/");
        root.mkdir();
        root = new File("src/ServerIO/Connections/");
        root.mkdir();
        root = new File("src/ServerIO/Instances/");
        root.mkdir();
        root = new File("src/ServerIO/Instances/2D/");
        root.mkdir();
        root = new File("src/ServerIO/Chat/");
        root.mkdir();
        root = new File("src/ServerIO/Chat/Instances");
        root.mkdir();
        root = new File("src/Shell/");
        root.mkdir();
        root = new File("src/Shell/logs/");
        root.mkdir();
     }
    
    /** 
 * Deletes the given path and, if it is a directory, deletes all its children. 
 */  
public boolean deleteWithChildren(String path) {  
    File file = new File(path);  
    if (!file.exists()) {  
        return true;  
    }  
    if (!file.isDirectory()) {  
        return file.delete();  
    }  
    return this.deleteChildren(file) && file.delete();  
}  
  
private boolean deleteChildren(File dir) {  
    File[] children = dir.listFiles();  
    boolean childrenDeleted = true;  
    for (int i = 0; children != null && i < children.length; i++) {  
        File child = children[i];  
        if (child.isDirectory()) {  
            childrenDeleted = this.deleteChildren(child) && childrenDeleted;  
        }  
        if (child.exists()) {  
            childrenDeleted = child.delete() && childrenDeleted;  
        }  
    }  
    return childrenDeleted;  
}  
    
}
