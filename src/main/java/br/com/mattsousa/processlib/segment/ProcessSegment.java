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
 * parts once at a time and it also can process between these sub-lists. It is
 * possible to add some objects as arguments.
 *
 * @author Matheus Pinheiro de Sousa
 * @version 1.0
 * @since 2020-01-28
 */
public class ProcessSegment {

    private HashMap<String, Object> args;

    /**
     * Construct and initialize all members
     */
    public ProcessSegment() {
        args = new HashMap<>();
    }

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
     * This method breaks up the {@code collection} in many sub-lists with
     * {@code subListSize} size or less. Each one of these sub-lists will be
     * processed.
     *
     * @param collection A Collection of objects to be processed
     * @param subListSize Size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @throws NonPositivePartException if the {@code partSize} is less or
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

    public void segment(
            Collection collection,
            int start,
            int size,
            int subListSize,
            ProcessInterface process) {

        ArrayList<ArrayList> listObjects = partCollection(collection, start, size, subListSize);

        processPart(listObjects, process);
    }

    /**
     * This method breaks up the {@code collection} in many sub-lists with
     * {@code subListSize} size or less. Each one of these sub-lists will be
     * processed in a {@code process} interface. a {@code intervalProcess}
     * interface will run during a interval of process between two sub-lists.
     *
     * @param collection A Collection of objects to be processed
     * @param subListSize Size of the sub-list
     * @param process Interface that will repeat for each sub-list generated
     * @param intervalProcess Interface that will repeat between every
     * {@code process} interval
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

    private ArrayList<ArrayList> partCollection(Collection list, int start, int size, int partSize) {
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

    private void processPart(ArrayList<ArrayList> listObjects, ProcessIntervalInterface intervalProcess, ProcessInterface process) {
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

    private void processPart(ArrayList<ArrayList> listObjects, ProcessInterface process) {
        for (ArrayList sublist : listObjects) {
            process.run(sublist.toArray(), args);
        }
    }
}
