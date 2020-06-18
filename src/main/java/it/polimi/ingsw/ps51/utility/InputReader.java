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
        String out;
        do {
            sniff();
            out = Objects.requireNonNull(queue.take()).split(" ")[0];
        } while (out.equals(""));
        return out;
    }

    public String read(long timer, TimeUnit timeUnit) throws InterruptedException {
        String out;
        do {
            sniff();
            out = queue.poll(5, TimeUnit.SECONDS);
            if (out == null){
                return null;
            }else {
                out = out.split(" ")[0];
            }
        } while (out.equals(""));
        return out;
    }

    public Integer readInt() throws InterruptedException {
        String num;
        do {
            sniff();
            num = read().split(" ")[0];
        } while (num.equals(""));
        return Integer.parseInt(num);
    }
}
