package HW11_3;

import java.util.concurrent.atomic.AtomicReference;

public class Container <T,S>{

    private Object firstInput;
    private Object secondInput;

    @SuppressWarnings("unchecked")  //برای رفع warning استفاده از AI

    public S getSecondInput() {
        return (S) secondInput;
    }
    @SuppressWarnings("unchecked")  //برای رفع warning استفاده از AI
    public T getFirstInput() {
        return (T) firstInput;
    }
    @SuppressWarnings("unchecked")  //برای رفع warning استفاده از AI

    public void addFirst(T first){
        this.firstInput=first;
    }
    @SuppressWarnings("unchecked")  //برای رفع warning استفاده از AI

    public void addSecond(S second){
        this.secondInput=second ;
    }


    public void swapObject(){
        Object swap=null;

        swap=firstInput;
        firstInput= (T) secondInput;
        secondInput= (S) swap;

    }

}
