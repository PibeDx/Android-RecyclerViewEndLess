package com.josecuentas.android_recyclerviewendless.rest.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jcuentas on 23/12/16.
 */

public class JobEntity {
    @SerializedName("created")
    @Expose
    public long created;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("___class")
    @Expose
    public String _class;
    @SerializedName("ownerId")
    @Expose
    public String ownerId;
    @SerializedName("updated")
    @Expose
    public String updated;
    @SerializedName("objectId")
    @Expose
    public String objectId;
    @SerializedName("__meta")
    @Expose
    public String meta;
}
