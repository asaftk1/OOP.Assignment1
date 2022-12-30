package observer;


import java.util.Stack;

/*
Use the class you've implemented in previous assignment
 */
public class UndoableStringBuilder {
    /**@UndoableStringBuilder
     * @see StringBuilder
     * * The Class of UndoableStringBuilder has 2 data-members:
     * @operations - Stack of Strings, in order to save our last changes, used for undo-function.
     * @sb         - a regular Java StringBuilder, used for StringBuilder methods.
     */
    Stack<String> operations ;
    StringBuilder sb;

    /**@public_UndoAbleStringBuilder
     * Initializes UndoableStringBuilder with:
     * @operations - as Stack of 1 String - ""
     * @sb -         as empty StringBuilder
     */

    public UndoableStringBuilder(){
        this.operations = new Stack<String>();
        this.sb = new StringBuilder();
        this.operations.push("");
    }

    /**
     * @append
     * @param str - String input.
     * Appends the specified string to this character sequence.
     *
     * Implementation:
     *
     * Clears sb.
     * Initializes String s - as the last String of the stack appended with str.
     * Applies append-function of sb, with the param(s).
     * Pushes the string of sb, into the stack.
     * @return this;
     */
    public UndoableStringBuilder append(String str){

        sb.delete(0,sb.toString().length());
        String s = operations.peek()+str;
        sb.append(s);
        operations.push(sb.toString());
        return this;
    }

    /**
     * @delete
     * @param start - start-index
     * @param end   - end index
     *
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified start and continues to the character at index end - 1,
     * or to the end of the sequence if no such character exists.
     *
     * Implementation:
     * Clears sb.
     * Initializes sb, with the last String of the Stack.
     * Applies the delete-function of sb with params(start , end)
     * Pushes the string of sb, into the stack.
     * @return this;
     */
    public UndoableStringBuilder delete(int start, int end) {

        sb.delete(0,sb.toString().length());

        sb.append(operations.peek());

        if (end < start|| start<0 ){
            throw new StringIndexOutOfBoundsException("start "+start+", end "+end+", length "+
                    sb.toString().length());
        }

        sb.delete(start, end);
        operations.push(sb.toString());
        return this;
    }

    /**
     * @insert
     * @param offset - The index, where the String-input is inserted into the UndoableStringBuilder.
     * @param str    - String-input.
     *
     * Inserts the string into this character sequence.
     *
     * Implementation:
     *
     * Clears sb.
     * Initializes sb, with the last String of the Stack.
     * Applies the insert-function of sb with params(offset , str);
     * Pushes the string of sb, into the stack.
     * @return this;
     */
    public UndoableStringBuilder insert(int offset, String str){

        sb.delete(0,sb.toString().length());

        sb.append(operations.peek());

        if (offset > operations.peek().length()|| offset<0 ){
            throw new StringIndexOutOfBoundsException("offset "+offset+", length "+sb.toString().length());
        }

        sb.insert(offset, str);
        operations.push(sb.toString());
        return this;
    }

    /**
     * @replace
     * @param start - start index
     * @param end   - end index
     * @param str   - String input
     *
     * Replaces the characters in a substring of this sequence with characters in the specified String.
     * The substring begins at the specified start and extends to the character at index end - 1,
     * or to the end of the sequence if no such character exists.
     * First the characters in the substring are removed and then the specified String is inserted at start.
     *
     * Implementation:
     * Clears sb.
     * Initializes sb, with the last String of the Stack.
     * Applies the replace-function of sb, with params(start , end , str);
     * Pushes the string of sb, into the stack.
     * @return this;
     *
     */
    public UndoableStringBuilder replace(int start,int end, String str){

        sb.delete(0,sb.toString().length());

        sb.append(operations.peek());

        if (end < start|| start<0 ){
            throw new StringIndexOutOfBoundsException("start "+start+", end "+end+", length "+
                    sb.toString().length());
        }

        if(str==null) {
            throw new NullPointerException("Cannot invoke " +'"'+"String.length()"+'"'+" because "+'"'+"str"
                    +'"'+" is null");

        }
        sb.replace(start, end, str);
        operations.push(sb.toString());
        return this;
    }

    /**
     * @revese
     *
     * Causes this character sequence to be replaced by the reverse of the sequence.
     *
     * Implementation:
     *
     * Clears sb.
     * Initializes sb, with the last String of the Stack.
     * Applies the reverse-function of sb.
     * Pushes the string of sb, into the stack.
     * @return this;
     */
    public UndoableStringBuilder reverse(){

        sb.delete(0,sb.toString().length());
        sb.append(operations.peek());
        sb.reverse();
        operations.push(sb.toString());

        return this;
    }

    /**@undo
     *
     * Cancels the last change and shows the previous change of UndoableStringBuilder.
     *
     * Implementation:
     * Checks:
     * if the Stack has only 1 string - do nothing.
     * Else - remove the last String of the Stack.
     */
    public void undo(){
        if(operations.size()>1){
            operations.pop();
        }
    }

    /**@toString
     *
     * Redefines the implementation of the method toString()- which comes from
     * @Object Class , in order to print UndoableStringBuilder last show.
     *
     * @return the last String of the Stack.
     */
    public String toString(){
        return operations.peek();
    }
}
