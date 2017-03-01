package controller.modal;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public interface ModalController<ParameterT, ResultT> {
    void setParameter(ParameterT parameter);
    ResultT getResult();
}
