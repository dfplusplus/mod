for file in "$@"
do
  echo $file
  sed -i 's/com\.mojang\.blaze3d\.matrix\.MatrixStack/net\.minecraft\.client\.util\.math\.MatrixStack/g' $file

  sed -i 's/client\.settings\./client\.options\./g' $file
  sed -i 's/util\.text\./text\./g' $file
  sed -i 's/client\.resources\.I18n/client\.resource\.language\.I18n/g' $file
  sed -i 's/client\.gui\.widget\.button\.Button/client\.gui\.widget\.ButtonWidget/g' $file
  sed -i 's/client\.GameSettings/client\.options\.GameOptions/g' $file
  sed -i 's/client\.gui\.NewChatGui/client\.gui\.hud\.ChatHud/g' $file
  sed -i 's/client\.gui\.ChatLine/client\.gui\.hud\.ChatHudLine/g' $file
  sed -i 's/util\.IReorderingProcessor/text\.OrderedText/g' $file
  sed -i 's/client\.gui\.RenderComponentsUtil/client\.util\.ChatMessages/g' $file
  sed -i 's/text\.TextFormatting/util\.TextFormatting/g' $file
  sed -i 's/util\.SoundEvent/sound\.SoundEvent/g' $file
  sed -i 's/util\.SoundCategory/sound\.SoundCategory/g' $file
  sed -i 's/resources\./resource\./g' $file

  sed -i 's/Minecraft/MinecraftClient/g' $file
  sed -i 's/StringTextComponent/LiteralText/g' $file
  sed -i 's/ITextComponent/Text/g' $file
  sed -i 's/TranslationTextComponent/TranslatableText/g' $file
  sed -i 's/SliderPercentageOption/DoubleOption/g' $file
  sed -i 's/GameSettings/GameOptions/g' $file
  sed -i 's/NewChatGui/ChatHud/g' $file
  sed -i 's/OptionSlider/DoubleOptionSliderWidget/g' $file
  sed -i 's/IPressable/PressAction/g' $file
  sed -i 's/IReorderingProcessor/OrderedText/g' $file
  sed -i 's/RenderComponentsUtil/ChatMessages/g' $file
  sed -i 's/ResourceLocation/Identifier/g' $file
  sed -i 's/IResourceManager/ResourceManager/g' $file

  sed -i 's/\.displayGuiScreen/\.openScreen/g' $file
  sed -i 's/I18n\.format/I18n\.translate/g' $file
  sed -i 's/keyboardListener\.enableRepeatEvents/keyboard\.setRepeatEvents/g' $file
  sed -i 's/fontRenderer/textRenderer/g' $file
  sed -i 's/setMaxStringLength/setMaxLength/g' $file
  sed -i 's/setResponder/setChangedListener/g' $file
  sed -i 's/drawString/drawStringWithShadow/g' $file
  sed -i 's/\.font/\.textRenderer/g' $file
  sed -i 's/gameSettings/options/g' $file
  sed -i 's/getMainWindow/getWindow/g' $file
  sed -i 's/ingameGUI/inGameHud/g' $file
  sed -i 's/calculateChatboxWidth/getWidth/g' $file
  sed -i 's/getMinValue/getMin/g' $file
  sed -i 's/getMaxValue/getMax/g' $file
  sed -i 's/clearChatMessages/clear/g' $file
  sed -i 's/func_238496_i_/isChatHidden/g' $file
  sed -i 's/getLineCount/getVisibleLineCount/g' $file
  sed -i 's/getScale(/getChatScale(/g' $file
  sed -i 's/func_238505_a_/breakRenderedChatMessageLines/g' $file
  sed -i 's/func_238505_a_/breakRenderedChatMessageLines/g' $file
  sed -i 's/func_238492_a_/render/g' $file
  sed -i 's/this\.mc/this\.client/g' $file
  sed -i 's/getChatWidth/getWidth/g' $file
  sed -i 's/accessibilityTextBackgroundOpacity/textBackgroundOpacity/g' $file
  sed -i 's/field_238331_l_/chatLineSpacing/g' $file
  sed -i 's/scrollPos/scrolledLines/g' $file
  sed -i 's/isScrolled/hasUnreadNewMessages/g' $file
  sed -i 's/persistantChatGUI/chatHud/g' $file
  sed -i 's/getUpdatedCounter/getCreationTick/g' $file
  sed -i 's/getLineBrightness/getMessageOpacityMultiplier/g' $file
  sed -i 's/func_238407_a_/drawWithShadow/g' $file
  sed -i 's/func_238169_a_/getText/g' $file
  sed -i 's/func_238493_a_/addMessage/g' $file
  sed -i 's/refreshChat/reset/g' $file
  sed -i 's/deleteChatLine/removeMessage/g' $file
  sed -i 's/hideGUI/hudHidden/g' $file
  sed -i 's/func_238494_b_/getText/g' $file
  sed -i 's/getChatLineID/getId/g' $file
  sed -i 's/func_238420_b_/getTextHandler/g' $file
  sed -i 's/func_243239_a/getStyleAt/g' $file
  sed -i 's/getChatOpen/isChatFocused/g' $file
  sed -i 's/printChatMessageWithOptionalDeletion/addMessage/g' $file
  sed -i 's/func_240747_b_/getName/g' $file
  sed -i 's/TextFormatting/Formatting/g' $file
  sed -i 's/func_240744_a_/fromFormatting/g' $file
  sed -i 's/getChatBackgroundColor/getTextBackgroundColor/g' $file
  sed -i 's/setListener/focusOn/g' $file
  sed -i 's/inputField/chatField/g' $file
  sed -i 's/setFocused2/setSelected/g' $file
  sed -i 's/commandSuggestionHelper/commandSuggestor/g' $file
  sed -i 's/func_238500_a_/render/g' $file
  sed -i 's/getChatGUI/getChatHud/g' $file
  sed -i 's/renderComponentHoverEffect/renderTextHoverEffect/g' $file
  sed -i 's/defaultInputFieldText/originalChatText/g' $file
  sed -i 's/isPressed/wasPressed/g' $file
  sed -i 's/isKeyDown/isPressed/g' $file
  sed -i 's/hasResource/containsResource/g' $file
  sed -i 's/isRemote/isClient/g' $file
  sed -i 's/getPosX/getX/g' $file
  sed -i 's/getPosY/getY/g' $file
  sed -i 's/getPosZ/getZ/g' $file
  sed -i 's/onClose(/removed(/g' $file

  sed -i 's/ Button / ButtonWidget /g' $file
  sed -i 's/ Button(/ ButtonWidget(/g' $file
  sed -i 's/ Button)/ ButtonWidget)/g' $file
  sed -i 's/(Button)/(ButtonWidget)/g' $file
  sed -i 's/(Button /(ButtonWidget /g' $file
  sed -i 's/<Button>/<ButtonWidget>/g' $file
  sed -i 's/ Button\./ ButtonWidget./g' $file
  sed -i 's/ minecraft/ client/g' $file
  sed -i 's/(minecraft/(client/g' $file
  sed -i 's/,minecraft/,client/g' $file
  sed -i 's/this\.minecraft/this\.client/g' $file
  sed -i 's/ ChatLine/ ChatHudLine/g' $file
  sed -i 's/<ChatLine/<ChatHudLine/g' $file
  sed -i 's/ Color/ TextColor/g' $file
  sed -i 's/\.Color/\.TextColor/g' $file
  sed -i 's/,Color/,TextColor/g' $file
  sed -i 's/\.Widget/\.AbstractButtonWidget/g' $file
  sed -i 's/ Widget/ AbstractButtonWidget/g' $file
  sed -i 's/(Widget/(AbstractButtonWidget/g' $file
  sed -i 's/AbstractButtonWidgetSpacer/WidgetSpacer/g' $file
  sed -i 's/(font/(textRenderer/g' $file
  sed -i 's/ font/ textRenderer/g' $file
done