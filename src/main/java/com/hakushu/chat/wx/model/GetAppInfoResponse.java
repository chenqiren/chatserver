package com.hakushu.chat.wx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetAppInfoResponse {

    @SerializedName("errcode")
    private int errorCode;

    @SerializedName("errmsg")
    private String errorMessage;

    @SerializedName("agentid")
    private int agentId;

    @SerializedName("name")
    private String name;

    @SerializedName("square_logo_url")
    private String logoUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("allow_userinfos")
    private UserInfo userInfo;

    @SerializedName("allow_partys")
    private PartyInfo partyInfo;

    @SerializedName("allow_tags")
    private TagInfo tagInfo;

    @SerializedName("close")
    private int close;

    @SerializedName("redirect_domain")
    private String redirectDomain;

    @SerializedName("report_location_flag")
    private int reportLocationFlag;

    @SerializedName("isreportenter")
    private int isReportEnter;

    @SerializedName("home_url")
    private String homeUrl;

    @Data
    public class UserInfo {

        @SerializedName("user")
        private List<User> users;
    }

    @Data
    public class User {

        @SerializedName("userid")
        private String userId;
    }

    @Data
    public class PartyInfo {

        @SerializedName("partyid")
        private List<Integer> partyIds;
    }

    @Data
    public class TagInfo {

        @SerializedName("tagid")
        private List<Integer> tagIds;
    }
}