package ru.sbt.Service;


public class LoaderImpl implements Loader {
    @Override
    public String load(int number, String url) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return number + " " + url;
    }

    @Override
    public String load(int number) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "" + number;
    }
}