package ru.sbt.Cache;

import java.lang.reflect.Proxy;

import static java.lang.ClassLoader.getSystemClassLoader;

public class CacheProxy<T> {

    private final String filePath;

    public CacheProxy(String filePath) {
        this.filePath = filePath;
    }

    private <T> T cache(T delegate) {
        return (T) Proxy.newProxyInstance(getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new InvocationHandlerImpl<>(filePath, delegate));
    }


}
