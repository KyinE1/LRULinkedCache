public class Driver {
	public static void main(String[] args) {
		LRULinkedCache<Integer, Integer> cache = new LRULinkedCache<Integer, Integer>(4);
		
		cache.LRUPut(1,5);
		System.out.println("cache after calling LRUPUT(1, 5): " + cache.toString());
		
		cache.LRUPut(2,2);
		System.out.println("cache after calling LRUPUT(2, 2): " + cache.toString());
		
		cache.LRUPut(3,7);
		System.out.println("cache after calling LRUPUT(3, 7): " + cache.toString());
		
		cache.LRUPut(4,9);
		System.out.println("cache after calling LRUPUT(4, 9): " + cache.toString());
		
		cache.LRUPut(1,9);
		System.out.println("cache after calling LRUPUT(1, 9): " + cache.toString());
		
		cache.LRUGet(3);
		System.out.println("LRUGET(3) returned: " + cache.LRUGet(3));
		System.out.println("cache after calling LRUGET(3): " + cache.toString());
	
		cache.LRUPut(5,10);
		System.out.println("cache after calling LRUPUT(5, 10): " + cache.toString());
	
		cache.LRUGet(4);
		System.out.println("LRUGET(4) returned: " + cache.LRUGet(4));
		System.out.println("cache after calling LRUGET(4): " + cache.toString());
	
		cache.LRUGet(10);
		System.out.println("cache after calling LRUGet(10): " + cache.toString());
	}
}
