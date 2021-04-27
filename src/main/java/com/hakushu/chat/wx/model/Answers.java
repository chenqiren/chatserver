package com.hakushu.chat.wx.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

public class Answers {

    private static Random sRandom = new Random();

    private static List<String> answers = ImmutableList.of(
            "相信你最初的想法",
            "请不要抗拒",
            "这会带来好运",
            "这具有重要意义",
            "研究并享受它",
            "你需要其他人的帮助",
            "这有些特别",
            "那不值得纠结",
            "给自己一点时间",
            "你必须现在就行动",
            "也许吧",
            "研究并享受它",
            "你会为自己所做的感到高兴的",
            "结果是乐观的",
            "不要勉强自己",
            "你肯定会获得支持",
            "相信你最初的想法",
            "先做好自己的事",
            "不要迫于压力草率行事",
            "我知道这很困难"
    );

    public static String getRandAns() {
        int size = answers.size();
        int rand = sRandom.nextInt(size);
        return answers.get(rand);
    }
}
