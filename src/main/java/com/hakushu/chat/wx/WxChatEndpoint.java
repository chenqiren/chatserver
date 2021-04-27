package com.hakushu.chat.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hakushu.chat.wx.helper.AesException;
import com.hakushu.chat.wx.helper.WXBizMsgCrypt;
import com.hakushu.chat.wx.model.WxMessageBody;
import com.hakushu.chat.wx.service.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class WxChatEndpoint {

    @Autowired
    private WxService wxService;

    @PostMapping(value = "wxmessage")
    public void onWxMessage(@RequestParam String msg_signature,
                            @RequestParam String timestamp,
                            @RequestParam String nonce,
                            @RequestBody String body) {
        log.info("I am receiving message");

        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WxConst.token, WxConst.encodingAESKey, WxConst.corpId);
            result = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, body);
        } catch (AesException e) {
            e.printStackTrace();
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            WxMessageBody message = xmlMapper.readValue(result, WxMessageBody.class);
            log.info("message content is " + message);
            if (message.isText()) {
                wxService.broadcastText(message.getContent(), message.getFromUserName());
            } else if (message.isImage()) {
                wxService.broadcastImage(message.getMediaId(), message.getFromUserName());
            } else if (message.isSubscribe()) {
                wxService.invalidUserCache();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "wxmessage")
    public String verifyUrl(@RequestParam String msg_signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam String echostr) {
        log.info("msg_signature " + msg_signature + " echostr " + echostr);

        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WxConst.token, WxConst.encodingAESKey, WxConst.corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "wxtest")
    public String onWxTest() {
        log.info("I am receiving wx message");
        return "ok";
    }

}
