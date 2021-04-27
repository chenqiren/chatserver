package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CreateGroupResponse {

    @SerializedName("errcode")
    private int errorCode;

    @SerializedName("errmsg")
    private String errorMessage;

    @SerializedName("chatid")
    private String chatId;

    public boolean isSuccess() {
        return errorCode == 0;
    }
}
