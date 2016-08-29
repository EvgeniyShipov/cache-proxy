package ru.sbt.Service;

import ru.sbt.Cache.Cache;
import ru.sbt.Cache.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {

    public double doHardWork(String line, double x);

    @Cache(cacheType = CacheType.FILE , fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    List<String> doHardWork(String item, double value, Date date);

    @ Cache (cacheType = CacheType.IN_MEMORY, listSize = 100_000)
    List<String> doHardWork(String item);

}
