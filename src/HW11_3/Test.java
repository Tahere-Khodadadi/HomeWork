package HW11_3;

public class Test {
    public static void main(String[] args) {

        Container<Object,Object> container=new Container<>();

       container.addFirst("zahra");
       container.addSecond(1500);

        System.out.println("Before Swap:");
        System.out.println("FirstInput: " + container.getFirstInput());
        System.out.println("SecondInput: " + container.getSecondInput());

        System.out.println("------------------------");
        container.swapObject();

        System.out.println("After Swap:");
        System.out.println("FirstOutput: " + container.getFirstInput());
        System.out.println("SecondOutput: " + container.getSecondInput());


    }
}
