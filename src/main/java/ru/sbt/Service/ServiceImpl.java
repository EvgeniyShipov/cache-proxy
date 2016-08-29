package ru.sbt.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public double doHardWork(String item, double value) {
        System.out.println("doHardWork" + item);
        for (int i = 10; i < 1_000_000_000; i++) {
            value += ((value + i) / (i - 1));
            System.out.println(value);
        }
        return value;
    }

    @Override
    public List<String> doHardWork(String item, double value, Date date) {
        List<String> list = new ArrayList<>();
        System.out.println("run" + item);
        for (int i = 0; i < value; i++) {
            list.add(new Date().toString());
        }
        return list;
    }

    @Override
    public List<String> doHardWork(String item) {
        List<String> list = new ArrayList<>();
        System.out.println("work" + item);
        return list;
    }
}
