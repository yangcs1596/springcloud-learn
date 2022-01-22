package com.safedog.common.ipsearch.search;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

public final class BufferHolder {
    private final ByteBuffer buffer;

    public BufferHolder(final FileChannel channel,long length, FileMode fileMode) throws IOException {
        Objects.requireNonNull(channel);
        if (fileMode == FileMode.MEMORY) {
            final ByteBuffer buf = ByteBuffer.wrap(new byte[(int) length]);
            int readSize = channel.read(buf, 0);
            if (!Objects.equals(readSize,buf.capacity())) {
                throw new IOException("Unable to read into memory,Unexpected end of stream.");
            }

            this.buffer = buf.asReadOnlyBuffer();
        } else {
            this.buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length).asReadOnlyBuffer();
        }
    }

    public ByteBuffer getBuffer() {
        return this.buffer.duplicate();
    }
}
