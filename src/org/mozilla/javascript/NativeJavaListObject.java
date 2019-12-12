/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Extends the NativeJavaObject to support index based access, if it is an
 * instance of {@link java.util.List}. If possible, the generic type can be
 * specified for
 *
 * @author Roland Praml, FOCONIS AG
 *
 */
public class NativeJavaListObject<T> extends NativeJavaObject implements List<T> {

    private static final long serialVersionUID = 1L;
    private List<T> javaList;
    private Class<?> valueType;

    public NativeJavaListObject() {
        super();
    }

    public NativeJavaListObject(Scriptable scope, List<T> javaList,
            Type staticType, boolean isAdapter) {
        super(scope, javaList, staticType, isAdapter);
        this.javaList = javaList;
        if (staticType == null) {
            staticType = javaList.getClass().getGenericSuperclass();
        }

        if (staticType instanceof ParameterizedType) {
            Type type = ((ParameterizedType) staticType)
                    .getActualTypeArguments()[0];
            this.valueType = ScriptRuntime.getRawType(type);
        } else {
            this.valueType = Object.class;
        }

    }

    public NativeJavaListObject(Scriptable scope, List<T> javaList,
            Type staticType) {
        this(scope, javaList, staticType, false);
    }

    @Override
    public Object get(int index, Scriptable start) {
        Object rval = javaList.get(index);
        if (rval == null) {
            return null;
        }
        // Need to wrap the object before we return it.
        Scriptable scope = ScriptableObject.getTopLevelScope(this);
        Context cx = Context.getContext();
        return cx.getWrapFactory().wrap(cx, scope, rval, valueType);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void put(int index, Scriptable start, Object value) {
        while (index >= javaList.size()) {
            // grow
            javaList.add(null);
        }
        javaList.set(index, (T) Context.jsToJava(value, valueType));
    }

    // delegate methods
    public void forEach(Consumer<? super T> action)             { javaList.forEach(action);                     }
    public int size()                                           { return javaList.size();                       }
    public boolean isEmpty()                                    { return javaList.isEmpty();                    }
    public boolean contains(Object o)                           { return javaList.contains(o);                  }
    public Iterator<T> iterator()                               { return javaList.iterator();                   }
    public Object[] toArray()                                   { return javaList.toArray();                    }
    public <E> E[] toArray(E[] a)                               { return javaList.toArray(a);                   }
    public boolean add(T e)                                     { return javaList.add(e);                       }
    public boolean remove(Object o)                             { return javaList.remove(o);                    }
    public boolean containsAll(Collection<?> c)                 { return javaList.containsAll(c);               }
    public boolean addAll(Collection<? extends T> c)            { return javaList.addAll(c);                    }
    public boolean addAll(int index, Collection<? extends T> c) { return javaList.addAll(index, c);             }
    public boolean removeAll(Collection<?> c)                   { return javaList.removeAll(c);                 }
    public boolean retainAll(Collection<?> c)                   { return javaList.retainAll(c);                 }
    public void replaceAll(UnaryOperator<T> operator)           { javaList.replaceAll(operator);                }
    public boolean removeIf(Predicate<? super T> filter)        { return javaList.removeIf(filter);             }
    public void sort(Comparator<? super T> c)                   { javaList.sort(c);                             }
    public void clear()                                         { javaList.clear();                             }
    public boolean equals(Object o)                             { return javaList.equals(o);                    }
    public int hashCode()                                       { return javaList.hashCode();                   }
    public T get(int index)                                     { return javaList.get(index);                   }
    public T set(int index, T element)                          { return javaList.set(index, element);          }
    public void add(int index, T element)                       { javaList.add(index, element);                 }
    public Stream<T> stream()                                   { return javaList.stream();                     }
    public T remove(int index)                                  { return javaList.remove(index);                }
    public Stream<T> parallelStream()                           { return javaList.parallelStream();             }
    public int indexOf(Object o)                                { return javaList.indexOf(o);                   }
    public int lastIndexOf(Object o)                            { return javaList.lastIndexOf(o);               }
    public ListIterator<T> listIterator()                       { return javaList.listIterator();               }
    public ListIterator<T> listIterator(int index)              { return javaList.listIterator(index);          }
    public List<T> subList(int fromIndex, int toIndex)          { return javaList.subList(fromIndex, toIndex);  }
    public Spliterator<T> spliterator()                         { return javaList.spliterator();                }

  

}
