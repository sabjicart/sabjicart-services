
package com.sabjicart.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil
{

    /**
     Determine if a Map is null or empty.

     @param map a map object to check

     @return <b>true</b> if <B>map</B> is null or empty,
     <b>false</b> otherwise
     @aribaapi documented
     */
    public static boolean nullOrEmptyMap (Map<?, ?> map)
    {
        return (map == null) || map.isEmpty();
    }

    /**
     Constructs a Map capable of holding a specified number
     of elements. <p/>

     To construct a type-safe <code>Map</code>:<ul>
     <li><code>Map&lt;K,V> typesafe = MapUtil.map()</code>
     </ul>
     For a raw <code>Map</code>:<ul>
     <li><code>Map raw = MapUtil.map()</code>
     </ul>
     There will be no compile warnings either way.

     @param initialCapacity the number of elements this Map
     is capable of holding before needing to grow.
     @return new empty Map with given initial capacity
     @aribaapi documented
     */
    public static <K, V> Map<K, V> map (int initialCapacity)
    {
        return new HashMap<K, V>(initialCapacity); // OK
    }

    /**
     Copy a Map.  This method will copy the source map and output
     a destination map.
     For each list or hastable within the source map they will also be
     copied,all other types will be shared.  So for a map of lists or
     maps, this will recurse.

     @param source the source map to copy.
     @return the destination map.
     @aribaapi documented
     */
    public static Map copyMap (Map source)
    {
        Map destination = map(source.size());
        Iterator iterator = source.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            Object value = source.get(key);
            destination.put(key, ListUtil.copyValue(value));
        }
        return destination;
    }

}
