package com.samstuff.dfadmintools.chat;

import com.google.common.collect.Lists;
import com.samstuff.dfadmintools.DFPlusPlusConfig;
import com.samstuff.dfadmintools.Main;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChatPredicates {
    private static final String CUSTOM_WORDS_DELIMINITER = " ";

    private static List<String> customWords = Lists.newArrayList();

    public static Predicate<ITextComponent> getSupportPredicate() {
        return iTextComponent -> (new ChatPattern(iTextComponent)).contains(new ChatPattern(
                new ChatPattern.ChatComponent("[SUPPORT] ", TextFormatting.BLUE)
        ));
    }

    public static Predicate<ITextComponent> getModPredicate() {
        return s -> false;
    }

    public static Predicate<ITextComponent> getSessionPredicate() {
        return s -> false;
    }

    public static Predicate<ITextComponent> getCustomPredicate() {
        return iTextComponent -> {
            Main.log(new ChatPattern(iTextComponent).toString());
            ArrayList<String> iTextComponentWords = Lists.newArrayList(iTextComponent.getString().split(CUSTOM_WORDS_DELIMINITER));
            for (String customWord : customWords) {
                Main.log(iTextComponentWords.toString());
                if (iTextComponentWords.contains(customWord)) return true;
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
}
