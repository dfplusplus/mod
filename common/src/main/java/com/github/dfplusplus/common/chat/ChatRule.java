package com.github.dfplusplus.common.chat;

import com.github.dfplusplus.common.Config;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.Map;
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

    public static void loadFromConfig() {
        for (ChatRuleType chatRuleType : ChatRuleType.values()) {
            getChatRule(chatRuleType).setChatSide(Config.getChatSide(chatRuleType));
            getChatRule(chatRuleType).setChatSound(Config.getChatSound(chatRuleType));
        }
    }

    private final String name;
    private final Predicate<ITextComponent> predicate;
    private ChatSide chatSide = ChatSide.SIDE;
    private ChatSound chatSound = ChatSound.NONE;

    public ChatSide getChatSide() {
        return chatSide;
    }

    public void setChatSide(ChatSide chatSide) {
        this.chatSide = chatSide;
    }

    public String getName() {
        return name;
    }

    public ChatSound getChatSound() {
        return chatSound;
    }

    public void setChatSound(ChatSound chatSound) {
        this.chatSound = chatSound;
    }

    public ChatRule(String name, Predicate<ITextComponent> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    public boolean matches(ITextComponent message) {
        return predicate.test(message);
    }

    public void toggleChatSide() {
        chatSide = chatSide.next();
    }

    public static List<ChatRule> getChatRules() {
        return Lists.newArrayList(chatRuleMap.values());
    }

    public static ChatRule getChatRule(ChatRuleType chatRuleType) {
        return chatRuleMap.get(chatRuleType);
    }

    public static ChatSide toggleChatType(ChatRuleType chatRuleType) {
        ChatRule chatRule = chatRuleMap.get(chatRuleType);
        chatRule.setChatSide(chatRule.getChatSide().next());
        return chatRule.getChatSide();
    }

    public enum ChatSide {
        MAIN,
        SIDE,
        EITHER;

        public ChatSide next() {
            int myIndex = Lists.newArrayList(ChatSide.values()).indexOf(this);
            myIndex++;
            if (myIndex >= ChatSide.values().length) myIndex = 0;
            return ChatSide.values()[myIndex];
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

    public enum ChatSound {
        NONE(null),
        BASS(SoundEvents.BLOCK_NOTE_BLOCK_BASS),
        BASS_DRUM(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM),
        BANJO(SoundEvents.BLOCK_NOTE_BLOCK_BANJO),
        BELL(SoundEvents.BLOCK_NOTE_BLOCK_BELL),
        BIT(SoundEvents.BLOCK_NOTE_BLOCK_BIT),
        CHIME(SoundEvents.BLOCK_NOTE_BLOCK_CHIME),
        CLICK(SoundEvents.BLOCK_NOTE_BLOCK_HAT),
        COW_BELL(SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL),
        DIDGERIDOO(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO),
        FLUTE(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE),
        GUITAR(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR),
        HARP(SoundEvents.BLOCK_NOTE_BLOCK_HARP),
        IRON_XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),
        PLING(SoundEvents.BLOCK_NOTE_BLOCK_PLING),
        SNARE_DRUM(SoundEvents.BLOCK_NOTE_BLOCK_SNARE),
        XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE);

        private SoundEvent soundEvent;

        ChatSound(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
        }

        public SoundEvent getSoundEvent() {
            return soundEvent;
        }

        public ChatSound next() {
            int myIndex = Lists.newArrayList(ChatSound.values()).indexOf(this);
            myIndex++;
            if (myIndex >= ChatSound.values().length) myIndex = 0;
            return ChatSound.values()[myIndex];
        }
    }
}
