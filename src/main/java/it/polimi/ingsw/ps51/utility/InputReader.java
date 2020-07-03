package it.polimi.ingsw.ps51.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

public class InputReader {
    InputStream in = System.in;
    Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(in)));
    ExecutorService executor = Executors.newSingleThreadExecutor();
    SynchronousQueue<String> queue = new SynchronousQueue<>();

    private void sniff() {
        executor.submit(() -> {
            try {
                queue.put(scanner.nextLine());
            } catch (InterruptedException e) {
                System.out.println("InputReader anomaly interrupted");
            }
        });
    }

    public void close(){
        executor.shutdownNow();
    }

    public String read() throws InterruptedException {
        sniff();
        return Objects.requireNonNull(queue.take()).trim().split(" ")[0];
    }

    public String read(long timer, TimeUnit timeUnit) throws InterruptedException {
        String out;
        sniff();
        out = queue.poll(timer, timeUnit);
        if (out == null){
            return null;
        }else {
            out = out.trim().split(" ")[0];
        }
        return out;
    }

    public Integer readInt() throws InterruptedException, NumberFormatException {
        return Integer.parseInt(read());
    }
}
