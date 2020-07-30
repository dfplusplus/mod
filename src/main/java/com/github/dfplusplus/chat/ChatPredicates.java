package com.github.dfplusplus.chat;

import com.github.dfplusplus.Config;
import com.github.dfplusplus.Main;
import com.google.common.collect.Lists;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChatPredicates {
    private static final String CUSTOM_WORDS_DELIMINITER = ",";

    private static List<String> customWords = Lists.newArrayList();

    //CUSTOM
    public static Predicate<ITextComponent> getCustomPredicate() {
        return iTextComponent -> {
            if (customWords.size() == 0 || getCustomWords().trim().length()==0) return false; // do no checks if the input is empty

//            Main.log(new ChatPattern(iTextComponent).toString());
            for (String customWord : customWords) {
                if (iTextComponent.getString().contains(customWord)) return true;
            }

            return false;
        };
    }

    public static void setCustomWords(String words) {
        customWords = Lists.newArrayList(words.split(CUSTOM_WORDS_DELIMINITER))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static String getCustomWords() {
        return String.join(CUSTOM_WORDS_DELIMINITER, customWords);
    }

    public static void loadFromConfig() {
        setCustomWords(Config.getCustomWords());
    }

    //MESSAGE
    private static final ChatPattern messageChatPattern = new ChatPattern(
            new ChatPattern.ChatComponent("[", Color.func_240744_a_(TextFormatting.DARK_RED),0),
            new ChatPattern.ChatComponent(null,Color.func_240744_a_(TextFormatting.AQUA)),
            new ChatPattern.ChatComponent(" -> ",Color.func_240744_a_(TextFormatting.GOLD)),
            new ChatPattern.ChatComponent(null,Color.func_240744_a_(TextFormatting.AQUA)),
            new ChatPattern.ChatComponent("] ",Color.func_240744_a_(TextFormatting.DARK_RED))
    );
    private static final ChatPattern crossNodeMessagePattern = new ChatPattern(
            new ChatPattern.ChatComponent("[",Color.func_240744_a_(TextFormatting.GOLD),0),
            new ChatPattern.ChatComponent(null,Color.func_240744_a_(TextFormatting.AQUA)),
            new ChatPattern.ChatComponent(" -> ",Color.func_240744_a_(TextFormatting.DARK_RED)),
            new ChatPattern.ChatComponent(null,Color.func_240744_a_(TextFormatting.AQUA)),
            new ChatPattern.ChatComponent("] ",Color.func_240744_a_(TextFormatting.GOLD))
    );
    public static Predicate<ITextComponent> getMessagePredicate() {
        return iTextComponent -> {
            ChatPattern chatPattern = new ChatPattern(iTextComponent);
            return chatPattern.contains(messageChatPattern) || chatPattern.contains(crossNodeMessagePattern);
        };
    }

    //SUPPORT
    private static final ChatPattern supportChatPattern = new ChatPattern(
            new ChatPattern.ChatComponent("[SUPPORT] ", Color.func_240744_a_(TextFormatting.BLUE),0)
    );
    public static Predicate<ITextComponent> getSupportPredicate() {
        return iTextComponent -> (new ChatPattern(iTextComponent)).contains(supportChatPattern);
    }

    //MOD
    private static final ChatPattern modChatPattern = new ChatPattern(
            new ChatPattern.ChatComponent("[MOD] ", Color.func_240744_a_(TextFormatting.DARK_GREEN),0)
    );
    public static Predicate<ITextComponent> getModPredicate() {
        return iTextComponent -> (new ChatPattern(iTextComponent)).contains(modChatPattern);
    }

    //SESSION
    private static final ChatPattern sessionChatPattern = new ChatPattern(
            new ChatPattern.ChatComponent("*",Color.func_240744_a_(TextFormatting.GREEN),0)
    );
    public static Predicate<ITextComponent> getSessionPredicate() {
        return iTextComponent -> (new ChatPattern(iTextComponent)).contains(sessionChatPattern);
    }

    //ADMIN
    private static final ChatPattern adminChatPattern = new ChatPattern(
            new ChatPattern.ChatComponent("[ADMIN] ", Color.func_240744_a_(TextFormatting.RED),0)
    );
    public static Predicate<ITextComponent> getAdminPredicate() {
        return iTextComponent -> (new ChatPattern(iTextComponent)).contains(modChatPattern);
    }
}
