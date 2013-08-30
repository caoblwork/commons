package junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.yingzhuo.commons.collections.IterableMap;
import com.github.yingzhuo.commons.collections.iterator.MapIterator;
import com.github.yingzhuo.commons.collections.map.ImmutableMap;

public class TestCases {

	@Test
	public void test1() throws Exception {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("2000", 2000L);
		map.put("3000", 3000L);
		map.put("4000", 4000L);
		
		IterableMap<String, Long> iMap = (IterableMap<String, Long>) ImmutableMap.decorate(map);
		
		MapIterator<String, Long> iterator = iMap.mapIterator();
		while(iterator.hasNext()) {
			iterator.next();
			System.out.println(iterator.getKey() + " " + iterator.getValue());
		}
	}

}

