package controller.modal;

public interface ModalController<ParameterT, ResultT> {
    void setParameter(ParameterT parameter);
    ResultT getResult();
}
