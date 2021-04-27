package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class CreateGroupRequest {

    @SerializedName("name")
    private String groupName;

    @SerializedName("owner")
    private String groupOwner;

    @SerializedName("userlist")
    private List<String> userList;

    @SerializedName("chatid")
    private String groupId;
}
