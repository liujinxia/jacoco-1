/*******************************************************************************
 * Copyright (c) 2009, 2019 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Brock Janiczak - analysis and concept 
 *    Marc R. Hoffmann - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.core.internal.analysis;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility to normalize {@link String} instances in a way that if
 * <code>equals()</code> is <code>true</code> for two strings they will be
 * represented the same instance. While this is exactly what
 * {@link String#intern()} does, this implementation avoids VM specific side
 * effects and is supposed to be faster, as neither native code is called nor
 * synchronization is required for concurrent lookup.
 *
 * 实用程序，用于规范化{@link String}实例，如果两个字符串的equals()为true，它们将被表示为同一个实例。
 * 虽然这正是{ @链接字符串#intern()}所做的，
 * 这种实现避免了虚拟机特定的副作用，
 * 因为并发查找既不调用本机代码，也不需要同步。
 */
public final class StringPool {

    private static final String[] EMPTY_ARRAY = new String[0];

    private final Map<String, String> pool = new HashMap<String, String>(1024);

    /**
     * 返回等于给定{@link String}的规范化实例
     *
     * @param s     参数为null, 直接返回null.
     *              如果参数不为null, 查询pool, 存在则直接返回。 不存在添加到pool
     *              避免创建太多string对象
     *
     * @return normalized instance or <code>null</code>
     */
    public String get(final String s) {
        if (s == null) {
            return null;
        }
        final String norm = pool.get(s);
        if (norm == null) {
            pool.put(s, s);
            return s;
        }
        return norm;
    }

    /**
     * Returns a modified version of the array with all string slots normalized.
     * It is up to the implementation to replace strings in the array instance
     * or return a new array instance.
     *
     * @param arr
     *            String array or <code>null</code>
     * @return normalized instance or <code>null</code>
     */
    public String[] get(final String[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return EMPTY_ARRAY;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = get(arr[i]);
        }
        return arr;
    }

    @Override
    public String toString() {
        return "StringPool{" +
                "pool=" + pool +
                '}';
    }
}
