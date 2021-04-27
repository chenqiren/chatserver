package com.hakushu.chat.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AccessTokenResponse {

    @JsonProperty("errcode")
    @SerializedName("errcode")
    private int errorCode;

    @JsonProperty("errmsg")
    @SerializedName("errmsg")
    private String errorMessage;

    @JsonProperty("access_token")
    @SerializedName("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    @SerializedName("expires_in")
    private long expires;
}
