package observer;

/**@ConcreteMember
 * Represents the Observer part of the "observer-pattern".
 * Includes 2 data-members:
 * 1 - UndoableStringBuilder called usb.
 * 2 - String name - represent the name of the Member.
 * The usb has to be initialized by the sallow copy of the GroupAdmin's UndoAbleStringBuilder, in order
 * to be updated about the whole updates of the UndoableStringBuilder which the member follows.
 */
public class ConcreteMember implements Member {
   private String name;
    private UndoableStringBuilder usb;

    public ConcreteMember(String name) {
        this.usb= new UndoableStringBuilder();
        this.name = name;
    }

    /**@getLast_notification
     *
     * @return the last notification of the UndoableStringBuilder which the member follows.
     */
    public String getLast_notification(){
        return this.usb.toString();
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**@update
     *
     * @param usb - the UndoableStringBuilder of the GroupAdmin.
     * Initializes the UndoableStringBuilder of the Member with sallow copy from the GroupAdmin.
     * Let the Member follow about the whole changes of the GroupAdmin's UndoableStringBuilder.
     * prints a new message to let the member know about the last change.
     */
    @Override
    public void update(UndoableStringBuilder usb) {
        this.usb = usb;
        if(!usb.toString().equals(""))System.out.println(" Hey " +this.name+"," +
                " the current String is: "+ this.getLast_notification());
    }


}