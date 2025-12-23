package HW11_4;

public class Test {
    public static void main(String[] args) {



        Triple<Double, Integer, Integer> triple1 = new Triple<>(5.5, 100, 15);
        Double sum1 = Triple.sumGeneric(triple1);
        System.out.println(" final result for first sample: " +sum1);


        Triple<Integer, String, Double> triple2 = new Triple<>(10, "maktab", 30.5);
        Double sum2 = Triple.sumGeneric(triple2);

        System.out.println(" final result for second sample:  " + sum2);


        Triple<Long, Boolean, Integer> triple3 = new Triple<>(1000L, true, 25);
        Double sum3 = Triple.sumGeneric(triple3);
        System.out.println(" final result for third sample:  " + sum3);



        Triple<String, String, Float> triple4 = new Triple<>("Maktab", "Sharif", 20.0f);
        Double sum4 = Triple.sumGeneric(triple4);
        System.out.println(" final result for forth sample: " + sum4);






    }
}
