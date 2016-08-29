package ru.sbt.Service;


import ru.sbt.Cache.Cache;
import ru.sbt.Cache.CacheType;

public interface Loader {
    @Cache(cacheType = CacheType.FILE)
    String load(int number, String url);

    String load(int number);
}
