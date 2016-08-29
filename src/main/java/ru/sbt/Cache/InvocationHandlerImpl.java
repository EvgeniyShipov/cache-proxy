package ru.sbt.Cache;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import ru.sbt.Utils.SerializationUtils;
import ru.sbt.Utils.Utils;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class InvocationHandlerImpl<T> implements InvocationHandler {
    private final String filePath;
    private final T delegate;
    private final Map<Object, Object> cacheMap = new HashMap<>();

    public InvocationHandlerImpl(String filePath, T delegate) {
        this.filePath = filePath;
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method delegateMethod = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
        Cache cache = method.getAnnotation(Cache.class);
        if (!method.isAnnotationPresent(Cache.class)) {
            return invoke(method, args);
        }
        if (cache.cacheType().equals(CacheType.FILE)) {
            return getResultFromFile(delegateMethod, args);
        }
        if (cache.cacheType().equals(CacheType.IN_MEMORY)) {
            return getResultFromMemory(delegateMethod, args);
        }
        return null;
    }

    private Object getResultFromMemory(Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        Object result;
        Object key = Utils.key(method, Utils.getArgumentsByIdentityInMethod(args, Arrays.asList(cache.identityBy())));

        if (!cacheMap.containsKey(key)) {
            result = invoke(method, args);
            cacheMap.put(key, result);
        } else {
            result = cacheMap.get(key);
        }

        return result;
    }

    private Object getResultFromFile(Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        Object result = Utils.cutList(method, invoke(method, args));
        String fileName = (cache.fileNamePrefix() + method.getName() + Utils.key(method, args));

        SerializationUtils.serialize((Serializable) result, fileName);
        if (cache.zip()) {
            zipFile(fileName);
        } else {
            saveFile(fileName, result);
        }
        return SerializationUtils.deserialize(fileName);
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile(String fileName, Object result) throws IOException {
        File file = new File(filePath + fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(result);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zipFile(String name) throws SerializationException {
        String zipFileName = filePath + name.substring(0, name.lastIndexOf(".")) + ".zip";
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
             FileInputStream fileInputStream = new FileInputStream(new File(name))) {
            String fileName = name.substring(name.lastIndexOf(System.getProperty("file.separator")) + 1);
            ZipEntry entry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entry);
            int temp;
            while ((temp = fileInputStream.read()) > -1) {
                zipOutputStream.write(temp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
