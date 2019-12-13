/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Extends the NativeJavaObject to support index based access, if it is
 * an instance of {@link java.util.List}. If possible, the generic type
 * can be specified for 
 *
 * @author Roland Praml, FOCONIS AG
 *
 */
public class NativeJavaMapObject<T> extends NativeJavaObject implements Map<String, T> {

    private static final long serialVersionUID = 1L;
    private Map<String, T> javaMap;
    private Class<?> valueType;

    public NativeJavaMapObject() {
        super();
    }

    public NativeJavaMapObject(Scriptable scope, Map<String, T> javaMap,
            Type staticType, boolean isAdapter) {
        super(scope, javaMap, staticType, isAdapter);
        this.javaMap = javaMap;
        if (staticType == null) {
            staticType = javaMap.getClass().getGenericSuperclass();
        }

        if (staticType instanceof ParameterizedType) {
            Type type = ((ParameterizedType) staticType)
                    .getActualTypeArguments()[1];
            this.valueType = ScriptRuntime.getRawType(type);
        } else {
            this.valueType = Object.class;
        }
        
    }

    public NativeJavaMapObject(Scriptable scope, Map<String, T> javaMap,
            Type staticType) {
        this(scope, javaMap, staticType, false);
    }

    @Override
    public Object[] getIds() {
        return javaMap.keySet().toArray();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object get(String name, Scriptable start) {
        Object rval = super.get(name, start);
        if (rval == null || rval == Scriptable.NOT_FOUND) {
            rval = ((Map)javaMap).getOrDefault(name, Scriptable.NOT_FOUND);
            if (rval != null && rval != Scriptable.NOT_FOUND) {
                // Need to wrap the object before we return it.
                Scriptable scope = ScriptableObject.getTopLevelScope(this);
                Context cx = Context.getContext();
                return cx.getWrapFactory().wrap(cx, scope, rval, valueType);
            }
        }
        return rval;
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
        if (members.has(name, false)) {
            super.put(name, start, value);
        } else if (value == Undefined.instance) {
            javaMap.remove(name);
        } else {
            javaMap.put(name, (T) Context.jsToJava(value, valueType));
        }
    }

    // delegate methods
    public int size()                                              { return javaMap.size(); }
    public boolean isEmpty()                                       { return javaMap.isEmpty(); }
    public boolean containsKey(Object key)                         { return javaMap.containsKey(key); }
    public boolean containsValue(Object value)                     { return javaMap.containsValue(value); }
    public T get(Object key)                                       { return javaMap.get(key); }
    public T put(String key, T value)                              { return javaMap.put(key, value); }
    public T remove(Object key)                                    { return javaMap.remove(key); }
    public void putAll(Map<? extends String, ? extends T> m)       { javaMap.putAll(m); }
    public void clear()                                            { javaMap.clear(); }
    public Set<String> keySet()                                    { return javaMap.keySet(); }
    public Collection<T> values()                                  { return javaMap.values(); }
    public Set<Entry<String, T>> entrySet()                        { return javaMap.entrySet(); }
    public boolean equals(Object o)                                { return javaMap.equals(o); }
    public int hashCode()                                          { return javaMap.hashCode(); }
    public T getOrDefault(Object key, T defaultValue)              { return javaMap.getOrDefault(key, defaultValue); }
    public T putIfAbsent(String key, T value)                      { return javaMap.putIfAbsent(key, value); }
    public boolean remove(Object key, Object value)                { return javaMap.remove(key, value); }
    public boolean replace(String key, T oldValue, T newValue)     { return javaMap.replace(key, oldValue, newValue); }
    public T replace(String key, T value)                          { return javaMap.replace(key, value); }
    public void forEach(BiConsumer<? super String, ? super T> action)
                    { javaMap.forEach(action); }
    public void replaceAll(BiFunction<? super String, ? super T, ? extends T> function)
                    { javaMap.replaceAll(function); }
    public T computeIfAbsent(String key, Function<? super String, ? extends T> mappingFunction)
                    { return javaMap.computeIfAbsent(key, mappingFunction); }
    public T computeIfPresent(String key, BiFunction<? super String, ? super T, ? extends T> remappingFunction)
                    { return javaMap.computeIfPresent(key, remappingFunction); }
    public T compute(String key, BiFunction<? super String, ? super T, ? extends T> remappingFunction)
                    { return javaMap.compute(key, remappingFunction); }
    public T merge(String key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction)
                    { return javaMap.merge(key, value, remappingFunction); }


}
