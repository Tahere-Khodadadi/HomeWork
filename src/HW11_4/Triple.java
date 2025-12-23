package HW11_4;

public class Triple <T,U,S extends Number>{

    private T firstInput ;
    private U secondInput;
    private S thirdInput;

    public Triple(T firstInput,U secondInput,S thirdInput) {
        this.firstInput = firstInput;
        this.secondInput=secondInput;
        this.thirdInput=thirdInput;

    }


    public S getThirdInput() {
        return thirdInput;
    }

    public void setThirdInput(S thirdInput) {
        this.thirdInput = thirdInput;
    }

    public U getSecondInput() {
        return secondInput;
    }

    public void setSecondInput(U secondInput) {
        this.secondInput = secondInput;
    }

    public T getFirstInput() {
        return firstInput;
    }

    public void setFirstInput(T firstInput) {
        this.firstInput = firstInput;
    }

    public static <T,U,S extends Number> Double sumGeneric(Triple<T,U,S> input) {
        T number1 = input.getFirstInput();
        S number2 = input.getThirdInput();


        if (number1 instanceof Number){
            Number firstNumber= (Number) number1;
            Number secondNumber=(Number) number2;


            return firstNumber.doubleValue()+secondNumber.doubleValue();
        }

            return (double) 0;

    }
}
