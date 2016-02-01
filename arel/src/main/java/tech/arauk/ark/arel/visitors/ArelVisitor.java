package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.ArelNodeStatement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ArelVisitor {
    public ArelConnection connection;
    private DispatchCache mDispatch;

    public ArelVisitor(ArelConnection arelConnection) {
        mDispatch = new DispatchCache();
        connection = arelConnection;
    }

    public ArelCollector accept(Object object) {
        return visit(object);
    }

    public ArelCollector accept(Object object, ArelCollector arelCollector) {
        return visit(object, arelCollector);
    }

    public ArelCollector visit(Object object) {
        String methodName = mDispatch.get(object.getClass());
        try {
            Method method = getClass().getMethod(methodName, Object.class);
            return (ArelCollector) method.invoke(this, object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException("Cannot visit " + object.getClass());
        }
    }

    public ArelCollector visit(Object object, ArelCollector arelCollector) {
        String methodName = mDispatch.get(object.getClass());
        try {
            Method method = getClass().getMethod(methodName, Object.class, ArelCollector.class);
            return (ArelCollector) method.invoke(this, object, arelCollector);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException("Cannot visit " + object.getClass());
        }
    }

    private class DispatchCache {
        private HashMap<Class, String> mDispatch;

        public DispatchCache() {
            this.mDispatch = new HashMap<>();
        }

        public String get(Class<? extends Object> aClass) {
            if (!mDispatch.containsKey(aClass)) {
                mDispatch.put(aClass, "visit" + aClass.getSimpleName());
            }

            return mDispatch.get(aClass);
        }
    }
}
