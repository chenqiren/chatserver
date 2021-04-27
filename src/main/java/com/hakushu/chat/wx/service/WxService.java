package com.hakushu.chat.wx.service;

import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hakushu.chat.wx.WxConst;
import com.hakushu.chat.wx.model.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WxService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String privateMessageUrl = " https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
    private static final String groupMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=%s";
    private static final String cacheKeyword = "users";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    private static Cache<String, Set<String>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(60 * 60, TimeUnit.SECONDS)
            .build();

    /**
     * Broadcast text message to several users
     */
    public void broadcastText(@NonNull String text, @NonNull String sender) {
        Set<String> recipients = new HashSet<>(getAppUsers());
        recipients.remove(sender);
        String users = Joiner.on("|").join(recipients);

        Map<String, String> meta = ImmutableMap.of("content", text);
        BaseSendMessageRequest request = new SendTextMessageRequest()
                .text(meta)
                .agentId(WxConst.agentid)
                .toUsers(users);
        sendAppMessage(request);

        log.info(String.format("broadcast message to %s", recipients));
    }

    /**
     * Broadcast image message to several users
     */
    public void broadcastImage(@NonNull String mediaId, @NonNull String sender) {
        Set<String> recipients = new HashSet<>(getAppUsers());
        recipients.remove(sender);
        String users = Joiner.on("|").join(recipients);

        Map<String, String> meta = ImmutableMap.of("media_id", mediaId);
        BaseSendMessageRequest request = new SendImageMessageRequest()
                .image(meta)
                .agentId(WxConst.agentid)
                .toUsers(users);
        sendAppMessage(request);

        log.info(String.format("broadcast message to %s", recipients));
    }

    public void invalidUserCache() {
        cache.invalidateAll();
    }

    /**
     * Send message to a 1-1 conversation/group
     */
    private void sendConversationMessage(BaseSendMessageRequest content) {
        sendMessage(content, groupMessageUrl);
    }

    /**
     * Send message to an App
     */
    private void sendAppMessage(BaseSendMessageRequest content) {
        sendMessage(content, privateMessageUrl);
    }

    private void sendMessage(BaseSendMessageRequest body, String requestUrl) {
        String token = TokenService.getInstance().getAccessToken();
        String url = String.format(requestUrl, token);

        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(body));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            SendMessageResponse sendMessageResponse = gson.fromJson(response.body().string(), SendMessageResponse.class);
            log.info(String.format("send message successfully %s", sendMessageResponse));
        } catch (IOException e) {
            log.error("fail to send message %s", e);
        }
    }

    private Set<String> getAppUsers() {
        Set<String> users = cache.asMap().get(cacheKeyword);
        if (users != null) return users;

        GetAppInfoResponse response = getAppInfo();
        if (response == null) {
            return ImmutableSet.of();
        }

        Set<String> appUsers = response
                .getUserInfo()
                .getUsers()
                .stream()
                .map(user -> user.getUserId())
                .collect(Collectors.toSet());
        cache.cleanUp();
        cache.put(cacheKeyword, appUsers);
        return appUsers;
    }

    private GetAppInfoResponse getAppInfo() {
        String token = TokenService.getInstance().getAccessToken();
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=%s&agentid=%s", token, WxConst.agentid);

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            GetAppInfoResponse getAppInfoResponse = gson.fromJson(response.body().string(), GetAppInfoResponse.class);

            log.info(String.format("get app info %s", getAppInfoResponse));
            return getAppInfoResponse;
        } catch (IOException e) {
            log.error(String.format("fail to get app info %s", e));
            return null;
        }
    }

    public void createGroupChat(List<String> userList) {
        String token = TokenService.getInstance().getAccessToken();
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=%s", token);

        CreateGroupRequest groupRequest = new CreateGroupRequest()
                .groupOwner("qiren.chen")
                .groupName("匿名问答")
                .groupId("blackroom")
                .userList(userList);

        RequestBody body = RequestBody.create(JSON, gson.toJson(groupRequest));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            CreateGroupResponse createGroupResponse = gson.fromJson(response.body().string(), CreateGroupResponse.class);
            if (createGroupResponse.isSuccess()) {
                log.info(String.format("create group chat successfully %s", createGroupResponse.getChatId()));
            } else {
                log.info(String.format("failed to create group chat %s", createGroupResponse.getErrorMessage()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGroupChatList() {
        String token = TokenService.getInstance().getAccessToken();
        String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/externalcontact/groupchat/list?access_token=%s", token);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status_filter", 0);
        jsonObject.addProperty("offset", 0);
        jsonObject.addProperty("limit", 100);

        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            GroupChatListResponse groupChatListResponse = gson.fromJson(response.body().string(),
                    GroupChatListResponse.class);
            log.info("get group chat list %s", groupChatListResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
