package me.shenfeng.mustache;

import java.util.List;
import java.util.Map;

public class Context {
    private Object data;
    private Context parent;

    public Context(Object data, Context parent) {
        this.data = data;
        this.parent = parent;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isArray(Object v) {
        if (v instanceof List) {
            return ((List) v).size() > 0;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isFalse(Object v) {
        if (v == null) {
            return true;
        } else if (Boolean.FALSE.equals(v)) {
            return true;
        } else if ((v instanceof List) && ((List) v).isEmpty()) {
            return true;
        }
        return false;
    }

    public Object lookup(Object key) {
        if (key.equals(".")) {
            return data;
        }

        Context context = this;
        while (context != null) {
            Object d = context.data;
            if (d instanceof Map) {
                @SuppressWarnings("rawtypes")
                Object val = ((Map) d).get(key);
                if (val != null) {
                    return val;
                }
            }
            context = context.parent;
        }

        return null;
    }
}
