package gitlet;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/** Represents a gitlet staging area.
 *  @author [你的名字]
 */
public class Stage implements Serializable {
    /** The files staged for addition (filename -> blob id). */
    private Map<String, String> additions;
    /** The files staged for removal. */
    private Set<String> removals;

    /** Create a new staging area. */
    public Stage() {
        additions = new TreeMap<>();
        removals = new TreeSet<>();
    }

    /** Get the files staged for addition. */
    public Map<String, String> getAdditions() { return additions; }

    /** Get the files staged for removal. */
    public Set<String> getRemovals() { return removals; }
}
