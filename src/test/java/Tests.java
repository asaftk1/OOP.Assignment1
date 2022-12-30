import observer.ConcreteMember;
import observer.GroupAdmin;
import observer.JvmUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    // stub methods to check external dependencies compatibility

    /**@see Tests
     *
     *   The data - members for tests:
     *
     *   Used for GroupAdmin and ConcreteMember ->
     *
     *   @g1 - A variable of GroupAdmin
     *   variables of ConcreteMember:
     *   @shoshi
     *   @eli
     *   @sakura
     *   @raymond
     *   Those ConcreteMembers are used to check the effectivity of the "register" and "unregister" functions.
     *   Also, there's support-function called:
     *   @InitializeMembers - in order to initialize the members of the GroupAdmin.
     *
     *   Used for UndoAbleStringBuilder ->
     *
     * @sb_test -      a regular Java StringBuilder.
     * @str_null -     a String, that equals to null, for unusual tests.
     * @rnd -          a Random, to make random inputs for tests.
     * To see if my UndoableStringBuilder-functions are valid, the outputs-functions between:
     * The UndoableStringBuilder of the GroupAdmin & sb_test are compared.
     * The outputs of the GroupAdmin's UndoableStringBuilder, and @sb_test should be equal in each test-function
     * of: insert, delete, undo, and append.
     *
     * Also, There are two support-functions for the tests:
     * @make_rand_str - to make random strings for tests-inputs.
     * @clear -         to clean-up and delete whole strings of the GroupAdmin's UndoableStringBuilder
     *                  && @sb_test.
     *                  This method is used, to do the tests better, and more accurate.
     *
     * There is also the public void test() - a method which used to check the memory - consumption
     * of the ConcreteMember and the GroupAdmin
     *
     */

     // Initializing the data-members:
    GroupAdmin g1 = new GroupAdmin();
    ConcreteMember shoshi = new ConcreteMember("shoshi");
    ConcreteMember eli = new ConcreteMember("eli");
    ConcreteMember sakura = new ConcreteMember("sakura");
    ConcreteMember raymond = new ConcreteMember("raymond");
    StringBuilder sb_test= new StringBuilder();

    String str_null = null;

    static Random rnd = new Random();

    // The implement of: make_rand_str - the first support-function.
    private String make_rand_str() {
        int str_len = rnd.nextInt(12) + 1;
        int i = 0;
        String str = "";
        while (i < str_len) {
            str += (char) (rnd.nextInt(96) + 32);
            i++;
        }
        return str;
    }
    //  The implement of: clear - the second support-function.
    private void clear() {
        sb_test.delete(0, sb_test.toString().length());
        g1.getBuilder().delete(0, g1.getBuilder().toString().length());
    }
    // The implement of: InitializeMembers - the third support-function.
    private void InitializeMembers() {
        g1.register(shoshi);
        g1.register(eli);
        g1.register(sakura);
        g1.register(raymond);
    }

    @Test
    public void test(){

        logger.info(()-> JvmUtilities.objectFootprint(g1));

//        logger.info(()->JvmUtilities.objectFootprint(s1,s2));

        logger.info(()->JvmUtilities.objectFootprint(shoshi));

        logger.info(() -> JvmUtilities.jvmInfo());

    }

    /**@register_test
     *
     * Checks if the GroupAdmin can register new members to the Set.
     * Also, checks if registering of exist member is possible
     *
     */
    @Test
    public void register_test(){

        g1.register(shoshi);
        Assertions.assertTrue(g1.getMembers().contains(shoshi));

        g1.register(shoshi);
        Assertions.assertNotEquals(g1.getMembers().size(), 2);

        g1.register(eli);
        Assertions.assertTrue(g1.getMembers().contains(eli));
        g1.register(sakura);
        Assertions.assertTrue(g1.getMembers().contains(sakura));
        g1.register(raymond);
        Assertions.assertTrue(g1.getMembers().contains(raymond));

        System.out.println(g1.getMembers());
    }
    /**@unregister_test
     *
     * Checks if the GroupAdmin can unregister members from the Set.
     * Also, checks if  double unregistering of any member is possible
     *
     */
    @Test
    public void unregister_test(){

     g1.unregister(shoshi);
     Assertions.assertFalse(g1.getMembers().contains(shoshi));

     g1.unregister(eli);
     Assertions.assertFalse(g1.getMembers().contains(eli));

     g1.unregister(sakura);
     Assertions.assertFalse(g1.getMembers().contains(sakura));

     g1.unregister(raymond);
     Assertions.assertFalse(g1.getMembers().contains(raymond));

    }

    /**@update_test
     * Checks if the members are actually notified in real time, while the changes of
     * the String are happening.
     *
     */
    @Test
    public void update_test(){
    InitializeMembers();

    g1.append("a");
    Assertions.assertEquals(g1.getBuilder().toString(),sakura.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),eli.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),shoshi.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),raymond.getLast_notification());
        System.out.println();
    g1.insert(1,"b");
    Assertions.assertEquals(g1.getBuilder().toString(),sakura.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),eli.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),shoshi.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),raymond.getLast_notification());
        System.out.println();
    g1.delete(0,1);
    Assertions.assertEquals(g1.getBuilder().toString(),sakura.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),eli.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),shoshi.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),raymond.getLast_notification());
        System.out.println();
    g1.undo();
    Assertions.assertEquals(g1.getBuilder().toString(),sakura.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),eli.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),shoshi.getLast_notification());
    Assertions.assertEquals(g1.getBuilder().toString(),raymond.getLast_notification());
        System.out.println();
    }
    /**@append
     *  For Random tests:
     *  ->
     *  At for loop, chooses random String, in order to see if the function is valid.
     *  Random String: str.
     *  appends str , into sb_test and the UndoAbleStringBuilder of the GroupAdmin.
     *  compares between the outputs of sb_test, and GroupAdmin's UndoAbleStringBuilder .
     *
     * For Unusual tests:
     * ->
     * Checks if the function has valid output, in unusual scenarios, like: null input
     * and empty string.
     *
     */
    @Test
    public void append_test(){
        InitializeMembers();
        //////////Random tests//////////////
        for (int i = 0; i <=15 ; i++) {
            //Initializing the GroupAdmin's UndoAbleStringBuilder and the sb_test, with random string.
            //check out - if the output is valid
            String str = make_rand_str();
            assertEquals(sb_test.append(str).toString(), g1.getBuilder().append(str).toString());
        }

        ////////Unusual tests//////////////
        assertEquals(sb_test.append(str_null).toString(), g1.getBuilder().append(str_null).toString());
        assertEquals(sb_test.append("").toString(), g1.getBuilder().append("").toString());
        clear();
    }
    /**
     * @insert
     *  For Random tests:
     *  ->
     *  At for loop, chooses random Strings and index, in order to see if the function is valid.
     *  Random Strings: str1 && str2.
     *  First, initializes the GroupAdmin's UndoAbleStringBuilder and sb_test with random String(str1).
     *  Therefore: GroupAdmin's UndoAbleStringBuilder.length == sb_test.length == str1.length.
     *  Then, chooses random index between 0 and str1.length. his name: randi.
     *  inserts the String of str2, into sb_test and GroupAdmin's UndoAbleStringBuilder,
     *  by using the function insert with the params of (randi, str2);
     *  compares between the outputs of sb_test, and the GroupAdmin's UndoAbleStringBuilder.
     *
     * For Unusual tests:
     * ->
     * checks if the function has valid output, in unusual scenario, like null input
     *
     * For Exception tests:
     * ->
     * Checks if the function has valid output, in exception scenarios, like:
     * offset < 0 || offset > str.length
     * Compares between the Exceptions thrown by sb_test, and the GroupAdmin's UndoAbleStringBuilder,
     * in order to see if they are the same.
     */
    @Test
    public void insert_test(){

        //////////////Random tests/////////////

        for (int i = 0; i <= 30; i++) {
            //Initializing the GroupAdmin's UndoAbleStringBuilder and the sb_test, with random string.
            //Choosing random index.
            String str1 = make_rand_str(), str2 = make_rand_str();
            g1.getBuilder().append(str1);
            sb_test.append(str1);
            int randi = rnd.nextInt(str1.length());

            // checking out - if the output is valid
            g1.getBuilder().insert(randi, str2);
            sb_test.insert(randi, str2);
            assertEquals(g1.getBuilder().toString(), sb_test.toString());
            clear();

            // The lengths of the GroupAdmin's UndoAbleStringBuilder and sb_test are always the length of str1,
            // during all the for loop.
            // So: 0=< randi <= GroupAdmin's UndoAbleStringBuilder == sb_test.length.
            // randi is completely random.
        }

        //////////Exception tests//////////////
        g1.getBuilder().append("I'm growing up");

        assertThrows(StringIndexOutOfBoundsException.class, ()-> g1.getBuilder().insert(100,"shoko"));

        assertThrows(StringIndexOutOfBoundsException.class, ()-> g1.getBuilder().insert(-2,"shoko"));
        clear();

        ////////////////////Unusual tests//////////////////
        assertEquals(sb_test.insert(0, str_null).toString(), g1.getBuilder().insert(0, str_null).toString());
        clear();
    }
    /**
     * @delete
     * For Random tests:
     * ->
     * At for loop, chooses random String and indexes, in order to see if the function is valid.
     * First, initializes the GroupAdmin's UndoAbleStringBuilder and sb_test with random String(str).
     * Therefore: GroupAdmin's UndoAbleStringBuilder.length == sb_test.length == str.length.
     * Then, chooses random indexes between 0 and str.length. their names: j && k.
     * So: 0=< j <= k <= str.length.
     * deletes sub-strings of GroupAdmin's UndoAbleStringBuilder and sb_test, from index j to index k,  by using the
     * @delete function with params (j, k);
     * compares between the outputs of sb_test, and the GroupAdmin's UndoAbleStringBuilder.
     *
     * For Unusual tests:
     * ->
     * Checks if the function has valid output, in unusual scenarios, like:
     * null input, empty Undo_Builder, empty sb_test and more...
     *
     * For Exception tests:
     * ->
     * Checks if the function has valid output, in exception scenarios, like:
     * k<j || j<0
     * Compares between the Exceptions thrown by sb_test, and the GroupAdmin's UndoAbleStringBuilder
     * in order to see, if they are the same.
     *
     */
    @Test
    public void delete_test(){
        ///////////////Random tests/////////////////

        for (int i = 0; i <=30 ; i++) {
            //Initializing the GroupAdmin's UndoAbleStringBuilder and the sb_test, with random string.
            //Choosing random indexes.
            String str = make_rand_str();
            sb_test.append(str) ;
            g1.getBuilder().append(str);
            int j = rnd.nextInt(str.length());
            int k = rnd.nextInt(str.length()-j)+j;

            // Checking uot - if the output is valid.
            assertEquals(sb_test.delete(j,k).toString(), g1.getBuilder().delete(j,k).toString());
            clear();

            // The lengths of the GroupAdmin's UndoAbleStringBuilder and sb_test are always the length of str,
            // during all the for loop.
            // So: 0=< j <= k <= GroupAdmin's UndoAbleStringBuilder.length == sb_test.length.
            // j && k, are completely random.
        }

        //////////Exception tests//////////////
        g1.getBuilder().append("I'm growing up");

        assertThrows(StringIndexOutOfBoundsException.class, ()->g1.getBuilder().delete(4,2));

        assertThrows(StringIndexOutOfBoundsException.class, ()-> g1.getBuilder().delete(-1,2));
        clear();

        /////////Unusual tests///////////
        assertEquals(sb_test.delete(0,0).toString(), g1.getBuilder().delete(0,0).toString());
        g1.getBuilder().append(str_null); sb_test.append(str_null);
        assertEquals(g1.getBuilder().delete(0,4).toString(),sb_test.delete(0,4).toString());

    }
    /**@undo
     * For Basic tests:
     * ->
     * chooses some Strings, and appends them to the GroupAdmin's UndoAbleStringBuilder.
     * Then, applies the undo-function of the GroupAdmin's UndoAbleStringBuilder and checks out,
     * if the function is valid.
     *
     */
    @Test
    public void undo_test(){
        ////////// Basic tests ///////

        String str1 = "I learn OOP with Elizabeth. ";
        String str2 = str1 + "I have to get 100 of my exercise. ";
        String str3 = str2 + "I love Pizza ";
        String str4 = str3 + "and Shawarma ";
        String str5 = str4 +"with french fries. ";
        String str6 = str5 +"mmmmm yummy!. ";

        g1.getBuilder().append("I learn OOP with Elizabeth. ");
        g1.getBuilder().append("I have to get 100 of my exercise. ");
        g1.getBuilder().append("I love Pizza ");
        g1.getBuilder().append("and Shawarma ");
        g1.getBuilder().append("with french fries. ");
        g1.getBuilder().append("mmmmm yummy!. ");

        g1.getBuilder().undo();
        assertEquals(str5,g1.getBuilder().toString());
        g1.getBuilder().undo();
        assertEquals(str4,g1.getBuilder().toString());
        g1.getBuilder().undo();
        assertEquals(str3,g1.getBuilder().toString());
        g1.getBuilder().undo();
        assertEquals(str2,g1.getBuilder().toString());
        g1.getBuilder().undo();
        assertEquals(str1,g1.getBuilder().toString());
        g1.getBuilder().undo();
        assertEquals("",g1.getBuilder().toString());

////////Unusual tests//////////

        //display undo(), when the GroupAdmin's UndoAbleStringBuilder is empty:
        g1.getBuilder().undo();
        assertEquals("",g1.getBuilder().toString());
    }
}
