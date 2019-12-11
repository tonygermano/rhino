/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;

import junit.framework.TestCase;

/*
 * This testcase tests the basic access to Java classess implementing Iterable
 * (eg. ArrayList)
 */
public class JavaMapIteratorTest extends TestCase {

    private static final String EXPECTED_VALUES = "7,2,5,";
    private static final String EXPECTED_KEYS = "foo,bar,baz,";
    
    private Map<String, Integer> createJavaMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("foo", 7);
        map.put("bar", 2);
        map.put("baz", 5);
        return map;
    }


    @Test
    public void testArrayForEach() {
        String js = "var ret = '';\n"
                + "for each(value in map)  ret += value + ',';\n"
                + "ret";
        testJsMap(js, EXPECTED_VALUES);
        testJavaMap(js, EXPECTED_VALUES);
    }

    @Test
    public void testArrayForKeys() {
        String js = "var ret = '';\n"
                + "for(key in map)  ret += key + ',';\n"
                + "ret";
        testJsMap(js, EXPECTED_KEYS);
        testJavaMap(js, EXPECTED_KEYS);
    }

    private void testJavaMap(String script, Object expected) {
        Utils.runWithAllOptimizationLevels(cx -> {
            final ScriptableObject scope = cx.initStandardObjects();
            scope.put("map", scope, createJavaMap());
            Object o = cx.evaluateString(scope, script,
                    "testJavaMap.js", 1, null);
            assertEquals(expected, o);

            return null;
        });
    }

    private void testJsMap(String script, Object expected) {
        Utils.runWithAllOptimizationLevels(cx -> {
            final ScriptableObject scope = cx.initStandardObjects();
            Scriptable obj = cx.newObject(scope);
            createJavaMap().forEach((key,value)->obj.put(key, obj, value));
            scope.put("map", scope, obj);
            Object o = cx.evaluateString(scope, script,
                    "testJsMap.js", 1, null);
            assertEquals(expected, o);
            
            return null;
        });
    }

}
