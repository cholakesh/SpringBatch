package com.batchprocess.model;

public class JobRequestParams {
    private String paramsKey;
    private String paramsVal;

    public JobRequestParams(String paramsKey, String paramsVal) {
        this.paramsKey = paramsKey;
        this.paramsVal = paramsVal;
    }

    public String getParamsKey() {
        return paramsKey;
    }

    public void setParamsKey(String paramsKey) {
        this.paramsKey = paramsKey;
    }

    public String getParamsVal() {
        return paramsVal;
    }

    public void setParamsVal(String paramsVal) {
        this.paramsVal = paramsVal;
    }

}
