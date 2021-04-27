package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class BaseSendMessageRequest {

    @SerializedName("touser")
    private String toUsers;

    @SerializedName("toparty")
    private String toParty;

    @SerializedName("totag")
    private String toTag;

    @SerializedName("chatid")
    private String toGroup;

    @SerializedName("msgtype")
    private String messageType;

    @SerializedName("agentid")
    private int agentId;

    @SerializedName("safe")
    private int safe;

    @SerializedName("enable_id_trans")
    private int enableIdTrans = 0;

    @SerializedName("enable_duplicate_check")
    private int enableDuplicateCheck = 0;

    @SerializedName("duplicate_check_interval")
    private int duplicate_check_interval = 1800;
}
