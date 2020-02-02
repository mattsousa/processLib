package br.com.mattsousa.processlib.segment;

import br.com.mattsousa.processlib.exception.NonPositivePartException;
import br.com.mattsousa.processlib.interfaces.ProcessInterface;
import br.com.mattsousa.processlib.interfaces.ProcessIntervalInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class can divide a {@link Collection} in many parts and process these
 * parts once at a time. It also can process between these sub-lists. It is
 * possible to pass some objects as arguments when processing the sub-lists.
 *
 * @author Matheus Pinheiro de Sousa
 * @version 1.0
 * @since 2020-01-28
 */
public class ProcessSegment {
    
    //****************************************
    // Fields
    //****************************************
    private HashMap<String, Object> args;

    
    //****************************************
    // Constructors
    //****************************************
    /**
     * Construct and initialize all members
     */
    public ProcessSegment() {
        args = new HashMap<>();
    }

    /**
     * Construct and initialize the arguments passing a {@link HashMap}
     * @param args map that contains all arguments
     */
    public ProcessSegment(HashMap<String, Object> args) {
        this.args = args;
    }
    
    //****************************************
    // Protected methods
    //****************************************
    
    protected ArrayList<ArrayList> partCollection(Collection list, int start, int size, int partSize) {
        ArrayList<ArrayList> result = new ArrayList<>();

        if (partSize <= 0) {
            throw new NonPositivePartException();
        }

        if (start < 0 || size > list.size()) {
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

    protected void processPart(ArrayList<ArrayList> listObjects, ProcessIntervalInterface intervalProcess, ProcessInterface process) {
        Object[] beforeList = listObjects.get(0).toArray();

        for (ArrayList sublist : listObjects) {

            Object[] currentList = sublist.toArray();

            if (!Arrays.equals(beforeList, currentList)) {
                intervalProcess.run(args, beforeList, sublist.toArray());
                beforeList = currentList;
            }
            
            process.run(currentList, args);
        }
    }

    protected void processPart(ArrayList<ArrayList> listObjects, ProcessInterface process) {
        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }
    
    //****************************************
    // Public methods
    //****************************************
    
    /**
     * Adds a new argument associated with a key The argument will be replaced
     * if this key already exists
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     * mapping for key. (A null return can also indicate that the map previously
     * associated null with key)
     */
    public Object addArgs(String key, Object value) {
        return args.put(key, value);
    }

    /**
     * Removes a argument associated with a key
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     * mapping for key. (A null return can also indicate that the map previously
     * associated null with key.)
     */
    public Object removeArgs(String key) {
        return args.remove(key);
    }

    /**
     * This method breaks up the collection in many sub-lists with
     * subListSize size or less. Items in the sub-list will be processed 
     * according to their position on the collection.
     *
     * @param collection A Collection of objects to be processed
     * @param subListSize Size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @throws NonPositivePartException if the subListSize is less or
     * equals 0
     */
    public void segment(
            Collection collection,
            int subListSize,
            ProcessInterface process) {
        int start = 0;
        int size = collection.size();
        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, subListSize);

        processPart(listObjects, process);
    }
    
    /**
     * This method breaks up the collection in many sub-lists with
     * subListSize size or less. Only the positions between in the range 
     * (pos >= start && pos <= (start+size)) will be partitioned. Items in the
     * sub-list will be processed according to their position on the collection.
     *
     * @param collection A Collection of objects to be processed
     * @param start First position of collection to process
     * @param size Number of positions to process from start position
     * @param subListSize size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @throws NonPositivePartException if the subListSize is less or
     * equals 0
     * @throws IndexOutOfBoundsException if the start or size is out of range
     * (start < 0 || start >= collection.size())
     * 
     */
    public void segment(
            Collection collection,
            int start,
            int size,
            int subListSize,
            ProcessInterface process) {

        ArrayList<ArrayList> listObjects = partCollection(
                collection
                , start
                , size
                , subListSize
        );

        processPart(listObjects, process);
    }

    /**
     * This method breaks up the collection in many sub-lists with
     * subListSize size or less. Items in the sub-list will be processed 
     * according to their position on the collection. An intervalProcess
     * interface will run during a gap of process between two sub-lists.
     *
     * @param collection A Collection of objects to be processed
     * @param subListSize Size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @param intervalProcess Interface that will repeat between every
     * process interval
     * @throws NonPositivePartException if the {@code partSize} is less or
     * equals 0
     */
    public void segment(
            Collection collection,
            int subListSize,
            ProcessInterface process,
            ProcessIntervalInterface intervalProcess) {
        int start = 0;
        int size = collection.size();

        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, subListSize);

        processPart(listObjects, intervalProcess, process);
    }

    /**
     * This method breaks up the collection in many sub-lists with
     * subListSize size or less. Only the positions between in the range 
     * (pos >= start && pos <= (start+size)) will be partitioned. Items in the
     * sub-list will be processed according to their position on the collection.
     * An intervalProcess interface will run during a gap of process 
     * between two sub-lists.
     *
     * @param collection A Collection of objects to be processed
     * @param start First position of collection to process
     * @param size Number of positions to process from start position
     * @param subListSize size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @param intervalProcess Interface that will repeat between every
     * process interval
     * @throws NonPositivePartException if the subListSize is less or
     * equals 0
     * @throws IndexOutOfBoundsException if the start or size is out of range
     * (start < 0 || start >= collection.size())
     * 
     */
    public void segment(
            Collection collection,
            int start,
            int size,
            int subListSize,
            ProcessInterface process,
            ProcessIntervalInterface intervalProcess) {

        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, subListSize);

        processPart(listObjects, intervalProcess, process);
    }
}
