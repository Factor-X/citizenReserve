package eu.factorx.citizens.util.exception;

import eu.factorx.citizens.util.BusinessErrorType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 30/08/14.
 */
public class MyRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String toClientMessage;
    private final BusinessErrorType businessErrorType;
    private final List<String> paramList = new ArrayList<>();


    public MyRuntimeException(String message) {
        super(message);
        toClientMessage = message;
        businessErrorType=null;
    }

    public MyRuntimeException(BusinessErrorType businessErrorType,String... params) {
        super();
        toClientMessage=null;
        this.businessErrorType=businessErrorType;
        for(String param : params){
            paramList.add(param);
        }
    }

    public MyRuntimeException(Throwable cause, String toClientMessage) {
        super(cause);
        this.toClientMessage = toClientMessage;
        businessErrorType=null;
    }

    public String getToClientMessage() {
        if(businessErrorType!=null) {
            return businessErrorType.toString();
        }
        else{
            return toClientMessage;
        }
    }

    public BusinessErrorType getBusinessErrorType() {
        return businessErrorType;
    }

    public List<String> getParamList() {
        return paramList;
    }

    @Override
    public String toString() {
        return "MyrmexRuntimeException{" +
                "toClientMessage='" + toClientMessage + '\'' +
                ", businessErrorType=" + businessErrorType +
                ", paramList=" + paramList +
                '}';
    }

    @Override
    public String getMessage(){
        if(this.businessErrorType!=null){
            return this.businessErrorType.toString()+paramList;
        }
        else{
            return this.toClientMessage;
        }
    }

}
