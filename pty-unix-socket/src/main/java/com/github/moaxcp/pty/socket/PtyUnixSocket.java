package com.github.moaxcp.pty.socket;

import com.github.moaxcp.pty.AbstractPlugin;
import com.github.moaxcp.pty.Status;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;

public class PtyUnixSocket extends AbstractPlugin implements Runnable, AutoCloseable {
    private final ServerSocketChannel serverChannel;
    private final List<ClientLoop> clients = new ArrayList<>();
    private volatile boolean running;
    private volatile boolean eventLoopRunning;
    @Getter
    private Throwable failure;

    public PtyUnixSocket(@NonNull Path file) {
        super("PtyUnixSocket-ClientLoop-" + UUID.randomUUID());

        try {
            Files.deleteIfExists(file);
            var address = UnixDomainSocketAddress.of(file);
            serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
            serverChannel.bind(address);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void run() {
        running = true;
        while(running) {
            if (!eventLoopRunning) {
                break;
            }
            try {
                var socketChannel = serverChannel.accept();
                var client = new ClientLoop(name, socketChannel, 1024);
                client.start();
                clients.add(client);
            } catch (IOException e) {
                running = false;
                failure = e;
            }
        }
        clients.forEach(ClientLoop::close);
    }

    @Override
    public void close() {
        running = false;
        try {
            serverChannel.close();
        } catch (IOException e) {
            failure = e;
        }
    }

    @Override
    public void output(byte[] bytes) {

    }

    @Override
    public void error(byte[] bytes) {

    }

    @Override
    public void status(Status status) {
        eventLoopRunning = status.isEventLoopRunning();
    }
}
