package br.com.mattsousa.processlib.segment;

import br.com.mattsousa.processlib.exception.NonPositivePartException;
import br.com.mattsousa.processlib.interfaces.ProcessInterface;
import br.com.mattsousa.processlib.interfaces.ProcessIntervalInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author mattsousa
 */
public class ProcessSegment {

    private HashMap<String, Object> args;

    public ProcessSegment() {
        args = new HashMap<>();
    }
    
    public Object addArgs(String key, Object value){
        return args.put(key, value);
    }
    
    public Object removeArgs(String key){
        return args.remove(key);
    }

    public void segment(
            Collection collection
            , int partSize
            , ProcessInterface process) {
        int start = 0;
        int size = collection.size();
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, partSize);
        
        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }
    
    public void segment(
            Collection collection
            , int partSize
            , ProcessInterface process
            , ProcessIntervalInterface intervalProcess) {
        int start = 0;
        int size = collection.size();
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, partSize);
        Object[] beforeList = listObjects.get(0).toArray();
        for (ArrayList sublist : listObjects) {
            if(!beforeList.equals(sublist)){
                intervalProcess.run(args, beforeList, sublist.toArray());
                beforeList = sublist.toArray();
            }
            
            process.run(sublist.toArray(), args);
        }
    }
    
    public void segment(
            Collection collection
            , int start
            , int size
            , int partSize
            , ProcessInterface process){
        
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, partSize);

        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }

    public void segmentWithInterval(
            Collection collection
            , int partSize
            , ProcessInterface process){
        int start = 0;
        int size = collection.size();
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, partSize);
        
        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }
    
    public void segmentWithInterval(
            Collection collection
            , int start
            , int size
            , int partSize
            , ProcessInterface process ){
        
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, partSize);

        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }
    
    private ArrayList<ArrayList> partCollection(Collection list, int start, int size, int partSize) {
        ArrayList<ArrayList> result = new ArrayList<>();
        
        if(partSize <= 0){
            throw new NonPositivePartException();
        }
        
        if(start < 0 || size > list.size()){
            throw new IndexOutOfBoundsException();
        }
        
        ArrayList subList = new ArrayList<>();

        int count = 0;
        
        for (Object item : list) {
            
            count++;
            
            if (count >= start && count <= size) {
                
                subList.add(item);
                if ((count % partSize) == 0 || count == size) {
                    
                    result.add(subList);
                    subList = new ArrayList();
                }
            }
            
        }

        return result;
    }
}
