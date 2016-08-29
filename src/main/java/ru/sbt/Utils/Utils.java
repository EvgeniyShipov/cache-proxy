package ru.sbt.Utils;


import ru.sbt.Cache.Cache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Utils {

    public static Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(asList(args));
        return key;
    }

    public static Object[] getArgumentsByIdentityInMethod(Object[] args, List<Class> identityByClass) {
        Object[] argsKey;

        if (identityByClass.size() != 0) {
            List<Object> list = new ArrayList<>();
            for (Object arg : args) {
                if (identityByClass.contains(arg.getClass())) {
                    list.add(arg);
                }
            }
            argsKey = list.toArray();
        } else {
            argsKey = args;
        }
        return argsKey;
    }

    public static Object cutList(Method method, Object result) {
        if (!(result instanceof List)) {
            return result;
        }
        List<Object> cutResult = (List<Object>) result;
        int index = method.getAnnotation(Cache.class).listSize();
        if (index < cutResult.size())
            result = new ArrayList<>(cutResult.subList(0, index));
        return result;
    }


}
