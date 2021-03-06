public class LRULinkedCache<K, V> {
	private int theSize;		// Current number of items in cache.
	private int capacity;		// The capacity of cache.
	private CacheNode<K, V> head;	// Reference to list header node.
	private CacheNode<K, V> tail;	// Reference to list tail node.
	
	/** Constructor. Return new empty cache with a given capacity. */
	public LRULinkedCache(int capacity) {
		this.capacity = capacity;
		clear();
	}
	
	// 	Methods inherited from class Object.
	/** Return a String representation of the LinkedList.
	 *
	 * @return: Linked list as String.
	 * @note: Otems are listed in order from beginning to end in 
	 * comma-delimited fashion.
	 */
	public String toString() {
		String s = "";
		CacheNode<K, V> current = head.next;
		
		// Append key values to the string until tail is reached.
		while (current != tail) {
			s += "(" + current.key + "," + current.value + ")";
			
			// Check if there's another value to append.
			if (current.next != tail)
				s += ",";

			// Move to next node.
			current = current.next;
		}
		
		return s;
	}

	// Methods inherited from interface Collection.
	/** Empty the LRUCache. */
	public void clear() {
		// Reset header node.
		head = new CacheNode<K, V>(null, null, null, null);
		
		// Reset tail node and point to head node.
        tail = new CacheNode<K, V>(null, null, head, null);
        head.next = tail;
        tail.prev = head;
        
        // Reset size to 0.
		theSize = 0;
	}

	/** Getter method. */
	public int size() {
		return theSize;
	}

	/** Check if the cache is empty.
	 *
	 * @return: True if the cache is empty.
	 */
	public boolean isEmpty() {
		return theSize == 0;
	}

	/** Operation returns the value for a given key in the cache. 
	 *	
	 * @param key: The data to return.
	 * @return: Null if the key is not in the cache.
	 * @note: Moves the data that is accessed to the end of 
	 * the list and inserts it before tail.
	 */
	public V LRUGet(K key) {
		CacheNode<K, V> n = head.next;	// Since head is null, start at the subsequent node.
		
		// Find key in list. 
		while (n != tail) {
			if (key.equals(n.key)) {
				// Connect adjacent nodes of n.
				n.prev.next = n.next;	
				n.next.prev = n.prev;

				// Move node to end of list (recently used).
				n.prev = tail.prev;		// New node's reference to tail's previous node.
				tail.prev.next = n;		// Node before tail's next reference to new node.
				tail.prev = n;			// Tail node's previous reference to new node.
				n.next = tail;			// New node's next reference to tail.
				
				return n.value;
			}

			// Advance to next node in list.
			n = n.next;
		}

		return null;
	}
	
	/** Add node to end of the cache.
	 * 	
	 * @param key: Generic type.
	 * @param value: Generic type.
	 */
	public void LRUPut(K key, V value) {
		// Start at the node before the tail.
		CacheNode<K, V> n = new CacheNode<K, V>(key, value, tail.prev, tail);
		V returnValue = LRUGet(key);
		
		// Case where key already exists in cache.
		if (returnValue != null) {
			tail.prev.value = value;	// Update value.
			return;						// Value of key has already been updated.
		}

		// Case where cache is empty.
	 	else if (isEmpty()) {
			head.next = n;				// Link forward to new node.
			tail.prev = n;				// Link back to new node.
			n.prev = head;				// Link back to head.
			n.next = tail;				// Link forward to tail.
			theSize++;
		}
	 	
		// New node will be added to the end of the list.
		else {
			// Case where cache is full.
			if (size() == capacity) {
				// Remove first node by referencing the subsequent node's surrounding nodes in list.
				head.next = head.next.next;		// Point to node after subsequent node.
				head.next.prev = head;			// Subsequent node points back to head.
				theSize--;
			}

			tail.prev.next = n;			// Link forward to new node from tail's previous node.
			n.prev = tail.prev;			// Link back to tail's previous node.
			tail.prev = n;				// Link back to new node.
			n.next = tail;				// Link forward to tail.
			theSize++;
		}
	}
	
	/** Nested class ListNode encapsulates the fundamental building 
	 * block of a LRU cache node contains a key and value, as well 
	 * as references to both the next and previous nodes in the cache
	 * K is the type of the key and V is the type of value.
	 */
	private static class CacheNode<K, V> {
		K key; 
		V value; 
		private CacheNode<K, V> next;
		private CacheNode<K, V> prev;
		
		/** Constructor. 
		 *	
		 * @param keyOfPair: The key of the CacheNode pair.
		 * @param valueOfPair: The value of the CacheNode pair.
		 * @param nextRef: The reference of the next node in the list.
		 * @param prevRef: The reference of the previous node in the list.
		 */
		public CacheNode(K keyOfPair, V valueOfPair, CacheNode<K, V> nextRef, CacheNode<K, V> prevRef) {
			key = keyOfPair;
			value = valueOfPair;
			next = nextRef;
			prev = prevRef;
		}
	}
}
