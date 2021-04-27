package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SendMessageResponse {

    @SerializedName("errcode")
    private int errorCode;

    @SerializedName("errmsg")
    private String errorMessage;

    @SerializedName("invaliduser")
    private String invalidUser;

    @SerializedName("invalidparty")
    private String invalidParty;

    @SerializedName("invalidtag")
    private String invalidtag;
}
