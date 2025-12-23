package HW11_1;

import java.util.Map;

public class Test {

    public static void main(String[] args) {
        MultiTypeHashMap<String, Object> map = new MultiTypeHashMap<>();

        map.put("userId", "id");
        map.put("username", "admin");
        map.put("age", 25);

        Integer userId = map.getMulti("userId", Integer.class);
        String username = map.getMulti("username", String.class);

        System.out.println("User ID as Integer: " + userId);
        System.out.println("Username: " + username);
        System.out.println("-----------------------------");
        System.out.println(" create group class :");
        System.out.println(map.groupingSet());
    }
}