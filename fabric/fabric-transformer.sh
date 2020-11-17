#!/bin/bash

# names of classes or methods
GENERIC_NAMES=(
# old                           new
  "Minecraft"                   "MinecraftClient"
  "StringTextComponent"         "LiteralText"
  "ITextComponent"              "Text"
  "TranslationTextComponent"    "TranslatableText"
  "SliderPercentageOption"      "DoubleOption"
  "GameSettings"                "GameOptions"
  "NewChatGui"                  "ChatHud"
  "OptionSlider"                "DoubleOptionSliderWidget"
  "IPressable"                  "PressAction"
  "IReorderingProcessor"        "OrderedText"
  "RenderComponentsUtil"        "ChatMessages"
  "ResourceLocation"            "Identifier"
  "IResourceManager"            "ResourceManager"
  "Button"                      "ButtonWidget"
  "ChatLine"                    "ChatHudLine"
  "Color"                       "TextColor"
  "Widget"                      "AbstractButtonWidget"
  "AbstractButtonWidgetSpacer"  "WidgetSpacer"
  "TextFormatting"              "Formatting"

  "displayGuiScreen"            "openScreen"
  "enableRepeatEvents"          "setRepeatEvents"
  "fontRenderer"                "textRenderer"
  "setMaxStringLength"          "setMaxLength"
  "setResponder"                "setChangedListener"
  "drawString"                  "drawStringWithShadow"
  "font"                        "textRenderer"
  "gameSettings"                "options"
  "getMainWindow"               "getWindow"
  "ingameGUI"                   "inGameHud"
  "calculateChatboxWidth"       "getWidth"
  "getMinValue"                 "getMin"
  "getMaxValue"                 "getMax"
  "clearChatMessages"           "clear"
  "func_238496_i_"              "isChatHidden"
  "getLineCount"                "getVisibleLineCount"
  "getScale"                    "getChatScale"
  "func_238505_a_"              "breakRenderedChatMessageLines"
  "func_238492_a_"              "render"
  "mc"                          "client"
  "getChatWidth"                "getWidth"
  "accessibilityTextBackgroundOpacity" "textBackgroundOpacity"
  "field_238331_l_"             "chatLineSpacing"
  "scrollPos"                   "scrolledLines"
  "isScrolled"                  "hasUnreadNewMessages"
  "persistantChatGUI"           "chatHud"
  "getUpdatedCounter"           "getCreationTick"
  "getLineBrightness"           "getMessageOpacityMultiplier"
  "func_238407_a_"              "drawWithShadow"
  "func_238169_a_"              "getText"
  "func_238493_a_"              "addMessage"
  "refreshChat"                 "reset"
  "deleteChatLine"              "removeMessage"
  "hideGUI"                     "hudHidden"
  "func_238494_b_"              "getText"
  "getChatLineID"               "getId"
  "func_238420_b_"              "getTextHandler"
  "func_243239_a"               "getStyleAt"
  "getChatOpen"                 "isChatFocused"
  "printChatMessageWithOptionalDeletion" "addMessage"
  "func_240747_b_"              "getName"
  "func_240744_a_"              "fromFormatting"
  "getChatBackgroundColor"      "getTextBackgroundColor"
  "setListener"                 "focusOn"
  "inputField"                  "chatField"
  "setFocused2"                 "setSelected"
  "commandSuggestionHelper"     "commandSuggestor"
  "func_238500_a_"              "render"
  "getChatGUI"                  "getChatHud"
  "renderComponentHoverEffect"  "renderTextHoverEffect"
  "defaultInputFieldText"       "originalChatText"
  "isPressed"                   "wasPressed"
  "isKeyDown"                   "isPressed"
  "hasResource"                 "containsResource"
  "isRemote"                    "isClient"
  "getPosX"                     "getX"
  "getPosY"                     "getY"
  "getPosZ"                     "getZ"
  "onClose"                     "removed"
  "minecraft"                   "client"
  "font"                        "textRenderer"
  "keyboardListener"            "keyboard"
  "fromTextFormatting"          "fromFormatting"
  "drawSuggestionList"          "render"
  "getLineString"               "getText"
  "getCharacterManager"         "getTextHandler"
)

# imports to rename. a special filter as the . needs to be escaped
DOTTED_NAMES=(
  "net.minecraft.client.settings." "net.minecraft.client.options."
  "net.minecraft.util.text." "net.minecraft.text."
  "net.minecraft.resources." "net.minecraft.resource."

  "net.minecraft.client.resources.I18n" "net.minecraft.client.resource.language.I18n"
  "net.minecraft.client.gui.widget.button.Button" "net.minecraft.client.gui.widget.ButtonWidget"
  "net.minecraft.client.GameSettings" "net.minecraft.client.options.GameOptions"
  "net.minecraft.client.gui.NewChatGui" "net.minecraft.client.gui.hud.ChatHud"
  "net.minecraft.client.gui.ChatLine" "net.minecraft.client.gui.hud.ChatHudLine"
  "net.minecraft.util.IReorderingProcessor" "net.minecraft.text.OrderedText"
  "net.minecraft.client.gui.RenderComponentsUtil" "net.minecraft.client.util.ChatMessages"
  "net.minecraft.text.TextFormatting" "net.minecraft.util.TextFormatting"
  "net.minecraft.util.SoundEvent" "net.minecraft.sound.SoundEvent"
  "net.minecraft.util.SoundCategory" "net.minecraft.sound.SoundCategory"

  "com.mojang.blaze3d.matrix.MatrixStack" "net.minecraft.client.util.math.MatrixStack"

  "I18n.format"                      "I18n.translate"
)

POST_NAMES=(
  "import(.)net\.client" "import\$1net\.minecraft"
)

declare -a OUTPUT_NAMES
declare -i OUTPUT_NAMES_INDEX=0

EVEN=0
for I in $(seq 0 1 $((${#DOTTED_NAMES[*]} - 1)))
do
  DOTTED_NAMES[I]="${DOTTED_NAMES[I]//"."/"\."}" # uses global replacement
  if [ $EVEN = 1 ]; then
    EVEN=0
    # only process every other value, then check the one behind me
    OUTPUT_NAMES[$OUTPUT_NAMES_INDEX]="s/${DOTTED_NAMES[$I-1]}/${DOTTED_NAMES[$I]}/g;"
#    OUTPUT_NAMES[$OUTPUT_NAMES_INDEX]="s/import(.)${DOTTED_NAMES[$I-1]}/import\$1${DOTTED_NAMES[$I]}/g;"
    OUTPUT_NAMES_INDEX+=1
  else
    EVEN=1
  fi
done

EVEN=0
for I in $(seq 0 1 $((${#GENERIC_NAMES[*]} - 1)))
do
  if [ $EVEN = 1 ]; then
    EVEN=0
    # only process every other value, then check the one behind me
    OUTPUT_NAMES[$OUTPUT_NAMES_INDEX]="s/(\W)${GENERIC_NAMES[$I-1]}(\W)/\$1${GENERIC_NAMES[$I]}\$2/g;"
    OUTPUT_NAMES_INDEX+=1
  else
    EVEN=1
  fi
done

EVEN=0
for I in $(seq 0 1 $((${#POST_NAMES[*]} - 1)))
do
  if [ $EVEN = 1 ]; then
    EVEN=0
    # only process every other value, then check the one behind me
    OUTPUT_NAMES[$OUTPUT_NAMES_INDEX]="s/${POST_NAMES[$I-1]}/${POST_NAMES[$I]}/g;"
    OUTPUT_NAMES_INDEX+=1
  else
    EVEN=1
  fi
done

declare -i COUNT=0
for NAME in ${OUTPUT_NAMES[*]}
do echo "$NAME $COUNT"
COUNT+=1
done

# shellcheck disable=SC2086
perl -pi -e "${OUTPUT_NAMES[*]}" "$@"
