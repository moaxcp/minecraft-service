package com.github.moaxcp.pty;

import lombok.Getter;

import java.util.function.Consumer;

public abstract class AbstractPlugin implements Plugin {

    @Getter
    protected final String name;
    protected Consumer<byte[]> input;

    protected AbstractPlugin(String name) {
        this.name = name;
    }

    @Override
    public void input(Consumer<byte[]> input) {
        this.input = input;
    }
}
