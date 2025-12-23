package HW11_2;

public class Generic {

        public static void main(String[] args) {
            Box<Number> numberBox = new Box<>();
            numberBox.setItem(10); // Integer is a Number
            Box<? extends Number> wildcardBox = numberBox;
/* //وقتی از extends استفاده کنیم فقط میتونیم بخوانیم و نمیتوانیم ست کنیم پس مشکل داره
            // The following line will cause a compilation error:
            //wildcardBox.setItem(5.5); // Double is also a Number

            List<Box<? super Integer>> list = new ArrayList<>();
            list.add(new Box<Number>()); // This is valid
            list.add(new Box<Object>()); // This would also be valid

            //The following line will cause a compilation error:
            // اینجا هم وقتی از superاستفاده میکنیم فقط میتونیم عملیات ست انجام دهیم و نمیتونیم get کنیم
             Box<Integer> intBox = list.get(0);

            // The following line will cause a compilation error:
            //قبلا همچین varaibleاستفاده کردیم پس خطا میده و مشابه خطای قبلی
             Integer intBox = intBox.getItem();
*/

}


}
