package com.github.moaxcp.pty.socket;

import com.github.moaxcp.pty.AbstractPlugin;
import com.github.moaxcp.pty.Status;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.UUID;

public class PtyUnixSocketPlugin extends AbstractPlugin {
    private final PtyUnixSocket socket;
    private boolean started = false;

    public PtyUnixSocketPlugin(@NonNull Path file) {
        super("PtyUnixSocket-ClientLoop-" + UUID.randomUUID());
        socket = new PtyUnixSocket(getName(), file);
    }

    @Override
    public void output(byte[] bytes) {

    }

    @Override
    public void error(byte[] bytes) {

    }

    @Override
    public void status(Status status) {
        if (!started && status.isEventLoopRunning()) {
            socket.start();
        }
    }
}
