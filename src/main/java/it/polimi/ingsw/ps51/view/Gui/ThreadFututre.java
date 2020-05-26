package it.polimi.ingsw.ps51.view.Gui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadFututre {

    ExecutorService ex = Executors.newSingleThreadExecutor();
    String result ;
    boolean bool = false;

    public ThreadFututre(){

    }

    public ExecutorService getEx(){
        return ex;
    }

    public Future<String> getFutureString(){
        return ex.submit(() -> {
            while(!bool) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }
            }
            return result;
        });
    }

    public void setResult(String result){
        this.result=result;
    }

    public void setBool(boolean bool){
        this.bool=bool;
    }
}
