package com.josecuentas.android_recyclerviewendless.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jcuentas on 23/12/16.
 */

public class BaseResponse<E> {
    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("data")
    @Expose
    public List<E> data = null;
    @SerializedName("nextPage")
    @Expose
    public String nextPage;
    @SerializedName("totalObjects")
    @Expose
    public Integer totalObjects;
}
