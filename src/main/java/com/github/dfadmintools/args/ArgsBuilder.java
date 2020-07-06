package com.github.dfadmintools.args;

import java.util.LinkedList;
import java.util.List;

public class ArgsBuilder {
    private final List<Object> args;

    public List<Object> getArgs() {
        return args;
    }

    public ArgsBuilder() {
        args = new LinkedList<>();
    }

    public void addArg(String value) {
        args.add(value);
    }

    public <T extends ArgsStringComponent> void addArg(Class<T> tClass, String title, String defaultValue) {
        args.add(new ArgsStringComponent(title, ((String) defaultValue)));
    }

    public <T extends ArgsOptionalComponent> void addArg(Class<T> tClass, String title, String optionalComponent, boolean defaultValue) {
        args.add(new ArgsOptionalComponent(title,optionalComponent,defaultValue));
    }
}
