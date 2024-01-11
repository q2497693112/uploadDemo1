package cn.cvs.tool;

public class ResponseObject {
    private Integer code;
    private Object data;
    private String mes;
    private static ResponseObject responseObject = new ResponseObject();
    public static ResponseObject cg(String data,String code){
        responseObject.setCode(1);
        responseObject.setData(data);
        return responseObject;
    }
    public static ResponseObject sb(String data,String code){
        responseObject.setCode(2);
        responseObject.setData(data);
        return responseObject;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public static ResponseObject getResponseObject() {
        return responseObject;
    }

    public static void setResponseObject(ResponseObject responseObject) {
        ResponseObject.responseObject = responseObject;
    }
}
