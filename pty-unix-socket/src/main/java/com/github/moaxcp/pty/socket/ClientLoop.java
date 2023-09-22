package com.github.moaxcp.pty.socket;

import com.github.moaxcp.pty.AbstractPlugin;
import com.github.moaxcp.pty.Status;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientLoop extends AbstractPlugin implements Runnable, AutoCloseable {

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
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void output(byte[] bytes) {
        try {
            socket.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void error(byte[] bytes) {
        try {
            socket.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
