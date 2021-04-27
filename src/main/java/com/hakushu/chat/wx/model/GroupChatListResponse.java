package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class GroupChatListResponse {

    @SerializedName("errcode")
    private int errorCode;

    @SerializedName("errmsg")
    private String errorMessasge;

    @SerializedName("group_chat_list")
    private List<GroupChat> groupChatList;

    private class GroupChat {
         @SerializedName("chat_id")
         public String chatId;

         @SerializedName("status")
         public int status;
    }
}
