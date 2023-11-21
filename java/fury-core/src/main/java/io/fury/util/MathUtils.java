/*
 * Copyright 2023 The Fury Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fury.util;

/**
 * Math utils.
 *
 * @author chaokunyang
 */
public class MathUtils {

  public static int doubleExact(int x) {
    long r = (long) x << 1;
    if ((int) r != r) {
      throw new ArithmeticException("integer overflow");
    }
    return (int) r;
  }

    /**
     * @see Math#floorMod(long, long)
     * @param x the dividend
     * @param y the divisor
     * @param floorDiv of java.lang.Math#floorDiv(x, y)
     * @return the floor modulus {@code x - (floorDiv(x, y) * y)}
     * @since 1.8
     */
    public static long floorMod(long x, long y, long floorDiv) {
        return x - floorDiv * y;
    }
}
