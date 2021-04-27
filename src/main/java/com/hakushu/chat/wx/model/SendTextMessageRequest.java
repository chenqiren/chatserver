package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(fluent = true)
public class SendTextMessageRequest extends BaseSendMessageRequest {

    public SendTextMessageRequest() {
        messageType("text");
    }

    @SerializedName("text")
    private Map<String, String> text;

}
