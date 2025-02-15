// Commit.java
package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  @author [你的名字]
 */
public class Commit implements Serializable {
    /** The message of this Commit. */
    private String message;
    /** The timestamp of this Commit. */
    private String timestamp;
    /** The parent reference of this Commit. */
    private String parent;
    /** The second parent reference of this Commit (for merges). */
    private String secondParent;
    /** The files tracked by this commit (filename -> blob id). */
    private Map<String, String> blobs;

    /** Create a new Commit. */
    public Commit(String message, String parent, String secondParent) {
        this.message = message;
        this.parent = parent;
        this.secondParent = secondParent;
        this.blobs = new TreeMap<>();

        if (parent == null) {
            this.timestamp = "Thu Jan 1 00:00:00 1970 -0800";
        } else {
            SimpleDateFormat formatter =
                    new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
            this.timestamp = formatter.format(new Date());
        }
    }

    /** 从父commit继承文件. */
    public void inheritFromParent(Commit parent) {
        if (parent != null) {
            blobs.putAll(parent.getBlobs());
        }
    }

    /** 返回这个提交的SHA-1标识符. */
    public String getID() {
        return Utils.sha1(Utils.serialize(this));
    }

    // Getters
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public String getParent() { return parent; }
    public String getSecondParent() { return secondParent; }
    public Map<String, String> getBlobs() { return blobs; }
}