package observer;

import java.util.HashSet;
import java.util.Set;

/**@GroupAdmin
 * Represents the Observerable part of the "observer pattern".
 * Includes 2 data-members:
 * 1 - UndoableStringBuilder called builder - delegate of UndoAbleStringBuilder, in order to use its functionality.
 * 2 - Set of Members implements by HashSet.
 * The builder make changes on strings, and then the GroupAdmin notifies the Members about the changes.
 */
public class GroupAdmin implements Sender{
    UndoableStringBuilder builder;
    Set<Member> members;

    public GroupAdmin(){
        this.builder = new UndoableStringBuilder();

        this.members = new HashSet<>();
    }

    public String toString(){
        return members.toString();
    }
    public Set<Member> getMembers(){
        return this.members;
    }
    public UndoableStringBuilder getBuilder(){
        return this.builder;
    }

    /**@register
     * Registers new member to the Set of the Members.
     * if the Member is already exists in the Set, then does nothing.
     * else, adds the new member.
     * @param obj
     */
    @Override
    public void register(Member obj) {
        if (members.contains(obj)) return;
        else if(obj == null) return;
        else{
            this.members.add(obj);
        }
    }

    /**@unregister
     * Unregisters member from the Set.
     * if the Member is not exists in the Set, then does nothing.
     * else, removes the member from the Set.
     * @param obj
     */
    @Override
    public void unregister(Member obj) {

        if ( ! members.contains(obj)) return;

        else{
            this.members.remove(obj);
        }

    }

    /**@insert
     @param offset - The index, where the String-input is inserted into the UndoableStringBuilder.
     @param obj    - String-input.
     *
     * Inserts the string into this character sequence.
     * The UndoableStringBuilder includes a StringBuilder that applies the function "insert",
     *of the StringBuilder-Class with the input-params.
     *
     *Then, notifies the whole Members about the last change of the UndoAbleStringBuilder.
     */
    @Override
    public void insert(int offset, String obj) {
    this.builder.insert(offset, obj);
    notify_all();
    }

    /**@append
     *
     * @param obj - The String-input.
     * Appends the specified string to this character sequence.
     *
     * The UndoableStringBuilder includes a StringBuilder that applies the function "append",
     *of the StringBuilder-Class with the input-params.
     *
     *Then, notifies the whole Members about the last change of the UndoAbleStringBuilder.
     */
    @Override
    public void append(String obj) {
    this.builder.append(obj);
    notify_all();
    }
    /**@delete
     *
     * @param start - Start Index.
     * @param end -   End Index.
     *
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified start and continues to the character at index end - 1,
     * or to the end of the sequence if no such character exists.
     *
     * The UndoableStringBuilder includes a StringBuilder that applies the function "delete",
     * of the StringBuilder-Class with the input-params.
     *
     *Then, notifies the whole Members about the last change of the UndoAbleStringBuilder.
     */
    @Override
    public void delete(int start, int end) {
    this.builder.delete(start, end);
    notify_all();
    }

    /**@undo
     *
     * Cancels the last change and shows the previous change of the UndoableStringBuilder.
     *
     *Then, notifies the whole Members about the last change of the UndoAbleStringBuilder.
     */
    @Override
    public void undo() {
    this.builder.undo();
    notify_all();
    }

    /**@notify_all
     * Notifies the whole Members that update was occurred.
     * The main function of the "observer-pattern" implement.
     * Sends to the Members a sallow copy of the GroupAdmin's UndoAbleStringBuilder, in order to let them
     * follow after the UndoAbleStringBuilder and it's changes.
     *
     */
    public void notify_all(){
        int counter = 0;
        for (Member o: members) {
            o.update(builder);
        }
    }
}
