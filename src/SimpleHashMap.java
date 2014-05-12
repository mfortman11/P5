///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LoadBalancerMain.java
// File:             SimpleHashMap.java
// Semester:         CS367 Spring 2014
//
// Author:           Mike Fortman	mfortman@wisc.edu
// CS Login:         fortman
// Lecturer's Name:  Jim Skrentny
//
// Pair Partner:     Michael Darling
// CS Login:         mdarling
// Lecturer's Name:  Jim Skrentny
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a generic map based on hash tables using chained
 * buckets for collision resolution.
 *
 * <p>A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.</p>
 *
 * <p>The <tt>SimpleHashMap</tt> class takes two generic parameters, <tt>K</tt>
 * and <tt>V</tt>, standing for the types of keys and values respectively. Items
 * are stored in a hash table. Hash values are computed from the
 * <tt>hashCode()</tt> method of the <tt>K</tt> type objects.</p>
 *
 * <p>The chained buckets are implemented using Java's <tt>LinkedList</tt>
 * class.  When a hash table is created, its initial table size and maximum
 * load factor is set to <tt>11</tt> and <tt>0.75</tt>. The hash table can hold
 * arbitrarily many key-value pairs and resizes itself whenever it reaches its
 * maximum load factor.</p>
 *
 * <p><tt>null</tt> values are not allowed in <tt>SimpleHashMap</tt> and a
 * NullPointerException is thrown if used. You can assume that <tt>equals()</tt>
 * and <tt>hashCode()</tt> on <tt>K</tt> are defined, and that, for two
 * non-<tt>null</tt> keys <tt>k1</tt> and <tt>k2</tt>, if <tt>k1.equals(k2)</tt>
 * then <tt>k1.hashCode() == k2.hashCode()</tt>. Do not assume that if the hash
 * codes are the same that the keys are equal since collisions are possible.</p>
 */
public class SimpleHashMap<K, V> {

    /**
     * A map entry (key-value pair).
     */
    public static class Entry<K, V> {
        private K key;
        private V value;

        /**
         * Constructs the map entry with the specified key and value.
         */
        public Entry(K k, V v) {
        	if (k == null || v == null)
        		throw new NullPointerException();
            this.key = k;
            this.value = v;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        public K getKey() {
            return this.key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry
         */
        public V getValue() {
            return this.value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value.
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         */
        public V setValue(V value) {
        	if (value == null)
        		throw new NullPointerException();
            this.value = value;
            return this.value;
        }
    }
    
    private static final double MAXIMUM_LOAD_FACTOR = 0.75;//max % of map which can be filled
    private LinkedList<Entry<K,V>>[] map;//holds the hashmap
    //table of primes to resize map up to 2^31 -1
    private int tableSizes[] = {11,23,47,97,197,397,797,1597,3203,6421,12853,25717,
                                51437,102877,205759,411527,823117,1646237,3292489,
                                6584983,13169977,26339969,52679969,105359939,210719881,
                                421439783,842879579,1685759167};
    private int tableIndex = 0; // used as index for finding the table size
    private int numEntries = 0;//number of entries added to the hashmap

    /**
     * Constructs an empty hash map with initial capacity <tt>11</tt> and
     * maximum load factor <tt>0.75</tt>.
     */
    public SimpleHashMap() {
    	map = new LinkedList[tableSizes[0]];//initializes hashmap
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or <tt>null</tt>
     * if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is <tt>null</tt>
     */
    public V get(Object key) {
    	//makes sure parameter is valid
    	if (key == null)
    		throw new NullPointerException();
    	//finds hashcode for the passed key
    	int i = key.hashCode() % map.length;
    	//if hashcode is negative map length is added to make it postive
    	if (i < 0)
    		i += map.length;
    	//finds the desired LinkedList and if null creates a new LinkedList
    	//otherwise searches through the list to find the passed key
    	LinkedList<Entry<K,V>> l = map[i];
    	if (l == null) return null;
    	
    	Iterator<Entry<K,V>> it = l.iterator();
    	while(it.hasNext()){
    		Entry<K, V> e = it.next();
    		if (e.getKey().equals(key)) {
    			return e.getValue();//returns value of that entry if found
    		}
    	}
    	return null;
    }

    /**
     * <p>Associates the specified value with the specified key in this map.
     * Neither the key nor the value can be <tt>null</tt>. If the map
     * previously contained a mapping for the key, the old value is replaced.</p>
     *
     * <p>If the load factor of the hash table after the insertion would exceed
     * the maximum load factor <tt>0.75</tt>, then the resizing mechanism is
     * triggered. The size of the table should grow at least by a constant
     * factor in order to ensure the amortized constant complexity. You must also
     * ensure that the new selected size is Prime. After that, all of the mappings 
     * are rehashed to the new table.</p>
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws NullPointerException if the key or value is <tt>null</tt>
     */
    public V put(K key, V value) {
        // first check to see if the table needs to be resized according to the load factor.
    	if ((double) numEntries / tableSizes[tableIndex] >= MAXIMUM_LOAD_FACTOR){
    		List<Entry<K, V>> l = entries();
    		map = new LinkedList[tableSizes[tableIndex++]];
    		// map each entry to a new location in the array with the new size.
    		Iterator<Entry<K, V>> it = l.iterator();
    		while(it.hasNext()){
    			Entry<K, V> ent = it.next();
    			reAdd(ent.getKey(),ent.getValue());
    		}
    	}
        if (key == null || value == null)
        	throw new NullPointerException();
    	
        Entry<K,V> e = new Entry<K,V>(key, value); // entry object containing key/value info.
    	
        int i = e.getKey().hashCode() % map.length; // index where value should be placed
    	   
        if (i < 0) i += map.length; // makes sure index is not negative
        
        Entry<K,V> previous = null; // reference to entry previously stored in key
    	
        if (map[i] == null) {
    		map[i] = new LinkedList<Entry<K,V>>(); // initialize a new "bucket" for the array element if necessary
    	}
    	
        LinkedList<Entry<K,V>> l = map[i];
    	numEntries++;
    	
        
        // Iterate through entries sharing the same "bucket", searching for the correct pair.
    	Iterator<Entry<K, V>> t = l.iterator();
    	while(t.hasNext()){
    		Entry<K,V> ent = t.next();
    		if(ent.getKey().equals(key)){
    			previous = ent;
    			ent.setValue(value);
    			return previous.getValue();
    		}	
    	}
    	l.add(e);
    	return null;
    }
    
    /**
     * Recalculates the mapping for the specified key from this map if present.
     *
     * @param key the key to be updated
     * @param val value associated with key
     */
    private void reAdd(K key, V val){
        Entry<K,V> e = new Entry<K,V>(key, val);
    	
        int i = e.getKey().hashCode() % map.length;

        if (i < 0) i += map.length;
    	
        if (map[i] == null) {
    		map[i] = new LinkedList<Entry<K,V>>();
    	}
    	
        LinkedList<Entry<K,V>> l = map[i];
    	
    	Iterator<Entry<K, V>> t = l.iterator();
    	while(t.hasNext()){
    		Entry<K,V> ent = t.next();
    		if(ent.getKey().equals(key)){
    			ent.setValue(val);
    		}	
    	}
    	l.add(e);
    }

    /**
     * Removes the mapping for the specified key from this map if present. This
     * method does nothing if the key is not in the hash table.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     * if there was no mapping for <tt>key</tt>.
     * @throws NullPointerException if key is <tt>null</tt>
     */
    public V remove(Object key) {
        if (key == null)
        	throw new NullPointerException();
        // Get index from hashcode.
    	int i = key.hashCode() % map.length;
    	if (i < 0)
    		i += map.length; // make sure hashcode is non-negative.
    	LinkedList<Entry<K,V>> l = map[i];
    	if(l == null)return null;
    	
        // iterate through elements contained in same bucket.
    	Iterator<Entry<K, V>> it = l.iterator();
    	V v; // container for value
    	while(it.hasNext()){
    		Entry<K, V> e = it.next();
    		if(e.getKey().equals(key)){
                // remove the key/value pair from the hash map
    			v = e.getValue();
    			l.remove(e);
    			numEntries--;
    			return v;
    		}
    			
    	}
    	return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
    	return numEntries;//returns private instance of number of entries
    }

    /**
     * Returns a list of all the mappings contained in this map. This method
     * will iterate over the hash table and collect all the entries into a new
     * list. If the map is empty, return an empty list (not <tt>null</tt>).
     * The order of entries in the list can be arbitrary.
     *
     * @return a list of mappings in this map
     */
    public List<Entry<K, V>> entries() {
    	List<Entry<K, V>> entries = new LinkedList<Entry<K, V>>();
    	//loops through the array
    	for (int i = 0; i < map.length; i++) {
    		//makes sure the linked list isn't empty
    		if (map[i] != null){
    			Iterator<Entry<K,V>> it = map[i].iterator();
    			//if it is not null adds each element of the LinkedList to entries
    			while(it.hasNext()){
    				entries.add(it.next());
    			}
    		}
    	}
    	return entries;
    }
}
