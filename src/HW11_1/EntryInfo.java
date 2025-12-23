package HW11_1;

public class EntryInfo {

        private final Object key;
        public final Object value;


    public EntryInfo(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return " key: "+ getKey()+ " value : "+getValue();
    }
}
