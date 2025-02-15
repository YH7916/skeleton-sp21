//只是完成了基础项目init, add和commit的实现，接下来我们继续完成其他命令的实现。
package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Represents a gitlet repository.
 *  @author YH
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with their types.
     * NOTE: DO NOT PREFIX with "static", since these should not be class variables.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    /** The objects directory for storing blobs and commits. */
    public static final File OBJECTS_DIR = Utils.join(GITLET_DIR, "objects");
    /** The refs directory for storing branch heads. */
    public static final File REFS_DIR = Utils.join(GITLET_DIR, "refs");
    /** The HEAD file. */
    public static final File HEAD_FILE = Utils.join(GITLET_DIR, "HEAD");
    /** The staging area file. */
    public static final File STAGE_FILE = Utils.join(GITLET_DIR, "stage");

    /** 初始化版本控制系统. */
    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.exitWithError("A Gitlet version-control system already exists in the current directory.");
        }

        // 创建必要的目录
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        REFS_DIR.mkdir();

        // 创建初始commit
        Commit initialCommit = new Commit("initial commit", null, null);

        // 保存初始commit
        String commitId = saveCommit(initialCommit);

        // 创建master分支并指向初始commit
        File masterFile = Utils.join(REFS_DIR, "master");
        Utils.writeContents(masterFile, commitId);

        // 将HEAD指向master分支
        Utils.writeContents(HEAD_FILE, "master");

        // 创建空的暂存区
        Stage stage = new Stage();
        Utils.writeObject(STAGE_FILE, stage);
    }

    // TODO: 添加更多方法
    public static void add(String filename) {
        // 1. 检查文件是否存在
        File file = Utils.join(CWD, filename);
        if (!file.exists()) {
            Utils.exitWithError("File does not exist.");
        }

        // 2. 读取当前commit和暂存区
        Stage stage = loadStage();
        String currentCommitId = getCurrentCommitId();
        Commit currentCommit = loadCommit(currentCommitId);

        // 3. 读取文件内容并计算blob id
        byte[] fileContents = Utils.readContents(file);
        String blobId = Utils.sha1(fileContents);

        // 4. 检查文件是否已经在当前commit中且内容相同
        Map<String, String> currentBlobs = currentCommit.getBlobs();
        if (currentBlobs.containsKey(filename) &&
                currentBlobs.get(filename).equals(blobId)) {
            // 如果文件内容没有改变，从暂存区移除
            stage.getAdditions().remove(filename);
        } else {
            // 5. 存储文件内容（blob）
            File blobFile = Utils.join(OBJECTS_DIR, blobId);
            if (!blobFile.exists()) {
                Utils.writeContents(blobFile, fileContents);
            }

            // 6. 将文件添加到暂存区
            stage.getAdditions().put(filename, blobId);
        }

        // 7. 如果文件在删除暂存区中，移除它
        stage.getRemovals().remove(filename);

        // 8. 保存更新后的暂存区
        saveStage(stage);
    }

    public static void commit(String message) {
        // 1. 检查message是否为空
        if (message.trim().length() == 0) {
            Utils.exitWithError("Please enter a commit message.");
        }

        // 2. 加载暂存区和当前commit
        Stage stage = loadStage();
        if (stage.getAdditions().isEmpty() && stage.getRemovals().isEmpty()) {
            Utils.exitWithError("No changes added to the commit.");
        }

        String currentCommitId = getCurrentCommitId();
        Commit currentCommit = loadCommit(currentCommitId);

        // 3. 创建新commit
        Commit newCommit = new Commit(message, currentCommitId, null);
        newCommit.inheritFromParent(currentCommit);

        // 4. 更新文件跟踪
        Map<String, String> blobs = newCommit.getBlobs();

        // 添加暂存区的文件
        blobs.putAll(stage.getAdditions());

        // 移除标记为删除的文件
        for (String file : stage.getRemovals()) {
            blobs.remove(file);
        }

        // 5. 保存新commit
        String newCommitId = saveCommit(newCommit);

        // 6. 更新当前分支指向
        String currentBranch = Utils.readContentsAsString(HEAD_FILE);
        File branchFile = Utils.join(REFS_DIR, currentBranch);
        Utils.writeContents(branchFile, newCommitId);

        // 7. 清空暂存区
        saveStage(new Stage());
    }

    /** 加载暂存区. */
    private static Stage loadStage() {
        return Utils.readObject(STAGE_FILE, Stage.class);
    }

    /** 保存暂存区. */
    private static void saveStage(Stage stage) {
        Utils.writeObject(STAGE_FILE, stage);
    }

    /** 获取当前commit ID. */
    private static String getCurrentCommitId() {
        String currentBranch = Utils.readContentsAsString(HEAD_FILE);
        return Utils.readContentsAsString(Utils.join(REFS_DIR, currentBranch));
    }

    /** 加载commit. */
    private static Commit loadCommit(String commitId) {
        File commitFile = Utils.join(OBJECTS_DIR, commitId);
        return Utils.readObject(commitFile, Commit.class);
    }

    /** 保存commit并返回其ID. */
    private static String saveCommit(Commit commit) {
        String id = commit.getID();
        File commitFile = Utils.join(OBJECTS_DIR, id);
        Utils.writeObject(commitFile, commit);
        return id;
    }

}
