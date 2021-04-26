/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm.console;

/**
 *
 * @author onyr
 */
public class Utils {
    
    public static void assertEquals(Object a, Object b, String message) {
        if(a == null) {
            if(b != null) {
                throw new AssertionError(message);
            }
        } else {
            if(a.equals(b)) {
            throw new AssertionError(message); 
            }
        }
    }
    
    public static void assertEquals(Object a, Object b) {
        assertEquals(a, b, "Assert Exception");
    }
    
}
