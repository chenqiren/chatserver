package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(fluent = true)
public class SendImageMessageRequest extends BaseSendMessageRequest {

    public SendImageMessageRequest() {
        messageType("image");
    }

    @SerializedName("image")
    private Map<String, String> image;
}
