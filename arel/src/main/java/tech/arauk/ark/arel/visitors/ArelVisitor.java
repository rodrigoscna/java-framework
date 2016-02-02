package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ArelVisitor {
    public ArelConnection connection;
    private DispatchCache mDispatch;

    public ArelVisitor(ArelConnection connection) {
        this.mDispatch = new DispatchCache();
        this.connection = connection;
    }

    public ArelCollector accept(Object object) {
        return visit(object);
    }

    public ArelCollector accept(Object object, ArelCollector collector) {
        return visit(object, collector);
    }

    public ArelCollector visit(Object object) {
        String methodName = this.mDispatch.get(object.getClass());
        try {
            Method method = getClass().getMethod(methodName, Object.class);
            return (ArelCollector) method.invoke(this, object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException("Cannot visit " + object.getClass().getCanonicalName());
        }
    }

    public ArelCollector visit(Object object, ArelCollector collector) {
        String methodName = this.mDispatch.get(object.getClass());
        try {
            Method method = getClass().getMethod(methodName, Object.class, ArelCollector.class);
            return (ArelCollector) method.invoke(this, object, collector);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException("Cannot visit " + object.getClass().getCanonicalName());
        }
    }

    private class DispatchCache {
        private HashMap<Class, String> mDispatch;

        public DispatchCache() {
            this.mDispatch = new HashMap<>();
        }

        public String get(Class<? extends Object> aClass) {
            if (!this.mDispatch.containsKey(aClass)) {
                this.mDispatch.put(aClass, "visit" + aClass.getSimpleName());
            }

            return this.mDispatch.get(aClass);
        }
    }
}
