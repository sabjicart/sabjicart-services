
package com.sabjicart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListUtil
{
    /**
     Determine if a List is null or empty.

     @param list a List object to check

     @return <b>true</b> if <B>list</B> is null or empty,
     <b>false</b> otherwise
     @aribaapi documented
     */
    public static boolean nullOrEmptyList (List<?> list)
    {
        return (list == null) || list.isEmpty();
    }

    /**
     Primitive constructor. Constructs a List large enough to
     hold <b>initialCapacity</b> elements. The List will grow to
     accommodate additional objects, as needed. <p/>

     To construct a type-safe <code>List</code>:<ul>
     <li><code>List&lt;X> typesafe = ListUtil.list()</code>
     </ul>
     For a raw <code>List</code>:<ul>
     <li><code>List raw = ListUtil.list()</code>
     </ul>
     There will be no compile warnings either way.

     @param initialCapacity the initial capacity; must be greater
     than or equal to zero
     @return an empty List with the specified capacity

     @aribaapi public
     */
    public static <T> List<T> list (int initialCapacity)
    {
        return new ArrayList<T>(initialCapacity); // OK
    }

    //protected method used in hashtableUtil and ListUtil
    static Object copyValue (Object value)
    {
        if (value instanceof Map) {
            return MapUtil.copyMap((Map)value);
        }
        else if (value instanceof List) {
            return copyList((List)value);
        }
        else {
            return value;
        }
    }

    /**
     * @param source the source list to copy.
     * @return the destination list.
     */
    public static List copyList (List source)
    {
        List destination = list(source.size());
        for (int i = 0, s = source.size(); i < s; i++) {
            Object value = source.get(i);
            destination.add(copyValue(value));
        }
        return destination;
    }
}
