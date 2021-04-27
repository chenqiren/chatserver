package com.hakushu.chat.wx.model;

import lombok.Data;

@Data
public class WxMessageBody {

    public String ToUserName;
    public String FromUserName;
    public String CreateTime;
    public String MsgType;
    public String Content;
    public String PicUrl;
    public String MsgId;
    public String MediaId;
    public String AgentID;
    public String Event;

    public boolean isText() {
        return "text".equals(MsgType);
    }

    public boolean isImage() {
        return "image".equals(MsgType);
    }

    public boolean isSubscribe() {
        return "subscribe".equals(Event);
    }
}
