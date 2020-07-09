package com.github.dfplusplus.chat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.text.ITextComponent;

import java.util.*;
import java.util.function.Predicate;

public class ChatRule {
    private static final Map<ChatRuleType, ChatRule> chatRuleMap = Maps.newHashMap();

    static {
        chatRuleMap.put(ChatRuleType.CUSTOM, new ChatRule("Custom Chat", ChatPredicates.getCustomPredicate()));
        chatRuleMap.put(ChatRuleType.MESSAGE, new ChatRule("Messages", ChatPredicates.getMessagePredicate()));
        chatRuleMap.put(ChatRuleType.SUPPORT, new ChatRule("Support Chat", ChatPredicates.getSupportPredicate()));
        chatRuleMap.put(ChatRuleType.SESSION, new ChatRule("Session Chat", ChatPredicates.getSessionPredicate()));
        chatRuleMap.put(ChatRuleType.MOD, new ChatRule("Mod Chat", ChatPredicates.getModPredicate()));
        chatRuleMap.put(ChatRuleType.ADMIN, new ChatRule("Admin Chat", ChatPredicates.getAdminPredicate()));
    }

    private final String name;
    private final Predicate<ITextComponent> predicate;
    private ChatSide chatSide;

    public ChatSide getChatSide() {
        return chatSide;
    }

    public void setChatSide(ChatSide chatSide) {
        this.chatSide = chatSide;
    }

    public String getName() {
        return name;
    }

    public ChatRule(String name, Predicate<ITextComponent> predicate) {
        this.name = name;
        this.predicate = predicate;
        this.chatSide = ChatSide.SIDE;
    }

    public boolean matches(ITextComponent message) {
        return predicate.test(message);
    }

    public static List<ChatRule> getChatRules() {
        return Lists.newArrayList(chatRuleMap.values());
    }

    public static ChatRule getChatRule(ChatRuleType chatRuleType) {
        return chatRuleMap.get(chatRuleType);
    }

    public static ChatSide toggleChatType(ChatRuleType chatRuleType) {
        ChatRule chatRule = chatRuleMap.get(chatRuleType);
        chatRule.setChatSide(chatRule.getChatSide().other());
        return chatRule.getChatSide();
    }

    public static void setChatTypeSide(ChatRuleType chatRuleType, ChatSide chatSide) {
        chatRuleMap.get(chatRuleType).setChatSide(chatSide);
    }

    public enum ChatSide {
        MAIN,
        SIDE;

        public ChatSide other() {
            if (this == MAIN)   return SIDE;
            else                return MAIN;
        }
    }

    public enum ChatRuleType {
        CUSTOM,
        MESSAGE,
        SUPPORT,
        SESSION,
        MOD,
        ADMIN
    }
}
