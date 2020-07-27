package com.github.dfplusplus.actions;

import com.github.dfplusplus.args.ArgsBuilder;
import com.github.dfplusplus.args.ArgsOptionalComponent;
import com.github.dfplusplus.args.ArgsStringComponent;
import com.github.dfplusplus.screens.ArgsScreen;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;
import java.util.Objects;

public class ArgCommandAction extends DisplayScreenAction {
    private ArgCommandAction(Screen priorScreen, List<Object> args) {
        super(new ArgsScreen(priorScreen,args));
    }

    private static ArgCommandAction banAction = null;
    public static ArgCommandAction getBanAction(Screen priorScreen) {
        if (Objects.isNull(banAction)) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/ban ");
            addPlayer(argsBuilder);
            addSpace(argsBuilder);
            addOptionalS(argsBuilder);
            addReason(argsBuilder);
            banAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return banAction;
    }

    private static ArgCommandAction warnAction = null;
    public static ArgCommandAction getWarnAction(Screen priorScreen) {
        if (Objects.isNull(warnAction)) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/warn ");
            addPlayer(argsBuilder);
            addSpace(argsBuilder);
            addOptionalS(argsBuilder);
            addReason(argsBuilder);
            warnAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return warnAction;
    }

    private static ArgCommandAction unwarnAction = null;
    public static ArgCommandAction getUnwarnAction(Screen priorScreen) {
        if (unwarnAction == null) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/unwarn ");
            addPlayer(argsBuilder);
            addSpace(argsBuilder);
            addOptionalS(argsBuilder);
            unwarnAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return unwarnAction;
    }

    private static ArgCommandAction tempbanAction = null;
    public static ArgCommandAction getTempbanAction(Screen priorScreen) {
        if (tempbanAction == null) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/tempban ");
            addPlayer(argsBuilder);
            addSpace(argsBuilder);
            addDuration(argsBuilder);
            addSpace(argsBuilder);
            addOptionalS(argsBuilder);
            addReason(argsBuilder);
            tempbanAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return banAction;
    }

    private static ArgCommandAction histAction = null;
    public static ArgCommandAction getHistAction(Screen priorScreen) {
        if (histAction == null) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/hist ");
            addPlayer(argsBuilder);
            histAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return histAction;
    }

    private static ArgCommandAction altsAction = null;
    public static ArgCommandAction getAltsAction(Screen priorScreen) {
        if (altsAction == null) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/alts ");
            addPlayer(argsBuilder);
            altsAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return altsAction;
    }

    private static ArgCommandAction modLogAction = null;
    public static ArgCommandAction getModLogAction(Screen priorScreen) {
        if (modLogAction == null) {
            ArgsBuilder argsBuilder = new ArgsBuilder();
            argsBuilder.addArg("/mod log ");
            argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.from","10m");
            argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.to","");
            argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.filter","");
            modLogAction = new ArgCommandAction(priorScreen, argsBuilder.getArgs());
        }
        return modLogAction;
    }

    private static void addPlayer(ArgsBuilder argsBuilder) {
        argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.player","gui.dfplusplus.player");
    }

    private static void addSpace(ArgsBuilder argsBuilder) {
        argsBuilder.addArg(" ");
    }

    private static void addOptionalS(ArgsBuilder argsBuilder) {
        argsBuilder.addArg(ArgsOptionalComponent.class,"-s?","-s ",false);
    }

    private static void addReason(ArgsBuilder argsBuilder) {
        argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.reason","");
    }

    private static void addDuration(ArgsBuilder argsBuilder) {
        argsBuilder.addArg(ArgsStringComponent.class,"gui.dfplusplus.duration","1d");
    }
}
