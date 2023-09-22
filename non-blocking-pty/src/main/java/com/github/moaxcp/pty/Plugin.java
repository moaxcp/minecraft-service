package com.github.moaxcp.pty;

import java.util.function.Consumer;

public interface Plugin {
    String getName();
    void input(Consumer<byte[]> input);
    void output(byte[] bytes);
    void error(byte[] bytes);
    void status(Status status);
}
