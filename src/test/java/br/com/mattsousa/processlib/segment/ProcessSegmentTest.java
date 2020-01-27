
package br.com.mattsousa.processlib.segment;

import br.com.mattsousa.processlib.interfaces.ProcessInterface;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author mattsousa
 */
public class ProcessSegmentTest {

    @Test
    public void testSegment(){
        ProcessSegment process = new ProcessSegment();
        ArrayList<Integer> numbers = new ArrayList<>();
        
        for(int i = 1; i < 10; i++){
            numbers.add(i);
        }
        
        Integer expected = 45;
        Integer actual;
        
        Integer[] objNumber = {0};
        process.segment(numbers, 2, new ProcessInterface() {
            @Override
            public void run(Object[] subList, HashMap<String, Object> args) {
                for(Object item : subList){
                    Integer number = (Integer)item;
                    objNumber[0] += number;
                }
            }
        });
        
        actual = objNumber[0];
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSegment2(){
        ProcessSegment process = new ProcessSegment();
        ArrayList<Integer> numbers = new ArrayList<>();
        
        for(int i = 1; i < 10; i++){
            numbers.add(i);
        }
        
        Integer expected = 15;
        Integer actual;
        
        Integer[] objNumber = {0};
        process.segment(numbers, 0, 5, 2, new ProcessInterface() {
            @Override
            public void run(Object[] subList, HashMap<String, Object> args) {
                for(Object item : subList){
                    Integer number = (Integer)item;
                    objNumber[0] += number;
                }
            }
        });
        
        actual = objNumber[0];
        
        assertEquals(expected, actual);
    }
    
}
