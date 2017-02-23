package controller.modal;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public interface ValueSettable<T, T2> {
    void setParameter(T parameter);
    T2 getResult();
}
