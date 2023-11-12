package com.github.moaxcp.pty.socket;

import com.github.moaxcp.pty.AbstractPlugin;
import com.github.moaxcp.pty.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientLoop extends AbstractPlugin implements Runnable, AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(ClientLoop.class);
    private final SocketChannel socket;
    private final int bufferSize;

    private volatile boolean running;

    ClientLoop(String name, SocketChannel socket,int bufferSize) {
        super(name);
        this.socket = socket;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        running = true;
        logger.info("{} started.", getName());
        while (socket.isConnected()) {
            var buffer = ByteBuffer.allocate(bufferSize);
            try {
                int read = socket.read(buffer);
                if (read < 0) {
                    continue;
                }

                byte[] bytes = new byte[read];
                buffer.flip();
                buffer.get(bytes);
                input.accept(bytes);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        logger.info("{} stopped.", getName());
    }

    @Override
    public void output(byte[] bytes) {
        try {
            socket.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void error(byte[] bytes) {
        try {
            socket.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void status(Status status) {

    }

    public void stop() {
        running = false;
    }

    @Override
    public void close() {
        running = false;
    }

    public void start() {
        var thread = new Thread(this);
        thread.setName(getName());
        thread.start();
    }
}
