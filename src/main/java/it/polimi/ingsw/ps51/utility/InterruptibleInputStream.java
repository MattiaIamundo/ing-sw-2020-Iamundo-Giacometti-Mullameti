package it.polimi.ingsw.ps51.utility;

import java.io.IOException;
import java.io.InputStream;

public class InterruptibleInputStream extends InputStream {

    protected final InputStream in;

    public InterruptibleInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        while (!Thread.interrupted())
            if (in.available() > 0)
                return in.read();
            else
                Thread.yield();
        throw new IOException("Thread interrupted while reading");
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        int c = -1;
        while (!Thread.interrupted())
            if (in.available() > 0) {
                c = in.read();
                break;
            } else
                Thread.yield();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;

        int i = 1;
        try {
            for (; i < len; i++) {
                c = -1;
                if (in.available() > 0)
                    c = in.read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

}
