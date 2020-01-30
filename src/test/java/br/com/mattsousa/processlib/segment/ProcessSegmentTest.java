
package br.com.mattsousa.processlib.segment;

import br.com.mattsousa.processlib.interfaces.ProcessInterface;
import br.com.mattsousa.processlib.interfaces.ProcessIntervalInterface;
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
    public void testAddArgs(){
        ProcessSegment process = new ProcessSegment();
        
        String expected = null;
        String actual = (String) process.addArgs("key1", "value1");
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testRemoveArgs(){
        ProcessSegment process = new ProcessSegment();
        
        String expected = "value1";
        process.addArgs("key1", "value1");
        String actual = (String) process.removeArgs("key1");
        
        assertEquals(expected, actual);
    }    
    
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
    
    @Test
    public void testSegment3(){
        ProcessSegment process = new ProcessSegment();
        ArrayList<Integer> numbers = new ArrayList<>();
        
        for(int i = 1; i < 10; i++){
            numbers.add(i);
        }
        
        Integer expected = 53;
        Integer actual;
        
        Integer[] objNumber = {0};
        process.segment(numbers ,2
        , new ProcessInterface() {
            @Override
            public void run(Object[] subList, HashMap<String, Object> args) {
                for(Object item : subList){
                    Integer number = (Integer)item;
                    objNumber[0] += number;
                }
            }
        }
        , new ProcessIntervalInterface() {
            @Override
            public void run(HashMap<String, Object> args, Object[] before, Object[] after) {
                objNumber[0] += 2;
            }
        });
        
        actual = objNumber[0];
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSegment4(){
        ProcessSegment process = new ProcessSegment();
        ArrayList<Integer> numbers = new ArrayList<>();
        
        for(int i = 1; i < 10; i++){
            numbers.add(i);
        }
        
        Integer expected = 19;
        Integer actual;
        
        Integer[] objNumber = {0};
        process.segment(numbers, 0, 5, 2
        , new ProcessInterface() {
            @Override
            public void run(Object[] subList, HashMap<String, Object> args) {
                for(Object item : subList){
                    Integer number = (Integer)item;
                    objNumber[0] += number;
                }
            }
        }
        , new ProcessIntervalInterface() {
            @Override
            public void run(HashMap<String, Object> args, Object[] before, Object[] after) {
                objNumber[0] += 2;
            }
        });
        
        actual = objNumber[0];
        
        assertEquals(expected, actual);
    }
    
}
