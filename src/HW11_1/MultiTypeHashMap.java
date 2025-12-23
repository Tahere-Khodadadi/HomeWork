package HW11_1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiTypeHashMap<K,V> extends HashMap<K,V> {




    //تشخیص و تبدیل حودکار رو انجام میده ورودی ار نوع class
public <T> T getMulti(Object key, Class<T> valueType) {

    Object input = super.get(key);
    if (valueType.isInstance(input)) {
        return (T) input;
    }
    return null;

}



    public Map<Class<?>, List<EntryInfo>> groupingSet() {
        return this.entrySet().stream()
                .map(result -> new EntryInfo(result.getKey(), result.getValue()))
                .collect(Collectors.groupingBy(info -> info.getValue().getClass()));
}
    }