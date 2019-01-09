package net.menwei;

public class ResultSet implements java.io.Serializable {
    private int counts;
    private String msg;
    private boolean success;
    private Object data;
    private int errCode;

    public ResultSet() {

    }

    public ResultSet(int counts, Object data) {
        this.counts = counts;
        this.data = data;
    }

    public ResultSet(int counts, String msg, int errCode, boolean success, Object data) {
        this.counts = counts;
        this.msg = msg;
        this.success = success;
        this.data = data;
        this.errCode = errCode;
    }

    public ResultSet(int counts, String msg, boolean success, Object data) {
        this.counts = counts;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public static ResultSet createSuccess(int counts) {
        return new ResultSet(counts, "", true, null);
    }

    public static ResultSet createSuccess(String msg) {
        return new ResultSet(1, msg, true, null);
    }

    public static ResultSet createSuccess(String msg, Object data) {
        return new ResultSet(0, msg, true, data);
    }

    public static ResultSet createSuccess(int count, String msg, Object data) {
        return new ResultSet(count, msg, true, data);
    }

    public static ResultSet createError(String msg, int errCode) {
        return new ResultSet(0, msg, errCode, false, null);
    }

    public static ResultSet createError(String msg) {
        return new ResultSet(0, msg, false, null);
    }

    public static ResultSet createError(String msg, Object data) {
        return new ResultSet(0, msg, false, data);
    }

    public int getCounts() {
        return counts;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
