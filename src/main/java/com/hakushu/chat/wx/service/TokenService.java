package com.hakushu.chat.wx.service;

import com.google.gson.Gson;
import com.hakushu.chat.wx.WxConst;
import com.hakushu.chat.wx.model.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TokenService {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    private static TokenService manager;
    private static String accessToken;
    private static long accessTokenExpirationTimestamp;
    private static String contactAccessToken;

    public static synchronized TokenService getInstance() {
        if (manager == null) {
            manager = new TokenService();
        }

        return manager;
    }

    private TokenService() { }

    public synchronized String getAccessToken() {
        if (accessTokenExpirationTimestamp < System.currentTimeMillis()) {
            accessToken = null;
        }
        if (accessToken != null) {
            return accessToken;
        }

        try {
            fetchAccessToken();
        } catch (IOException e) {
            log.info("fail to fetch access token %s", e);
        }
        return accessToken;
    }

    public synchronized String getContactAccessToken() {
        if (contactAccessToken != null) {
            return contactAccessToken;
        }

        try {
            fetchContactAccessToken();
        } catch (IOException e) {
            log.info("fail to fetch contact access token %s", e);
        }
        return contactAccessToken;
    }

    private void fetchAccessToken() throws IOException {
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                WxConst.corpId, WxConst.corpsecret);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        AccessTokenResponse body = gson.fromJson(response.body().string(), AccessTokenResponse.class);

        accessToken = body.getAccessToken();
        accessTokenExpirationTimestamp = System.currentTimeMillis() + body.getExpires() * 1000;
    }

    private void fetchContactAccessToken() throws IOException {
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                WxConst.corpId, WxConst.contactsecret);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        AccessTokenResponse body = gson.fromJson(response.body().string(), AccessTokenResponse.class);

        contactAccessToken = body.getAccessToken();
    }
}
