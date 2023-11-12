package com.github.moaxcp.pty.socket;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PtyUnixSocket implements Runnable, AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(PtyUnixSocket.class);

    private final String name;
    private final ServerSocketChannel serverChannel;
    private final List<ClientLoop> clients = new ArrayList<>();
    private volatile boolean running;
    private volatile boolean eventLoopRunning;
    @Getter
    private Throwable failure;

    public PtyUnixSocket(@NonNull String name, @NonNull Path file) {
        this.name = name;
        try {
            Files.deleteIfExists(file);
            var address = UnixDomainSocketAddress.of(file);
            serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
            serverChannel.bind(address);
            logger.info("{} bound to {}", name, address);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public void start() {
        var thread = new Thread(this);
        thread.setName(name);
        thread.start();
    }

    public void run() {
        running = true;
        logger.info("{} started.", name);
        while(running) {
            try {
                logger.info("{} waiting for connection.", name);
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
        logger.info("{} stopped.", name);
    }

    @Override
    public void close() {
        running = false;
        try {
            serverChannel.close();
        } catch (IOException e) {
            logger.error("error closing {}", name, e);
            failure = e;
        }

        logger.info("{} closed", name);
    }
}
