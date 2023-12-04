/* Copyright (c) 2008-2023, Nathan Sweet
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * - Neither the name of Esoteric Software nor the names of its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package io.fury.copy;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

import io.fury.Fury;
import io.fury.FuryTestBase;
import java.util.ArrayList;
import org.testng.annotations.Test;

public class CopyTest extends FuryTestBase {

  @Test
  void testBasic() {
    ArrayList test = new ArrayList();
    test.add("one");
    test.add("two");
    test.add("three");

    ArrayList copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertEquals(test, copy);
  }

  private <T> Fury getFury() {
    return FuryTestBase.getJavaFury();
  }

  @Test
  void testNested() {
    ArrayList test = new ArrayList();
    test.add("one");
    test.add("two");
    test.add("three");

    ArrayList test2 = new ArrayList();
    test2.add(1);
    test2.add(2f);
    test2.add(3d);
    test2.add((byte) 4);
    test2.add((short) 5);
    test.add(test2);

    ArrayList copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertNotSame(test.get(3), copy.get(3));
    assertEquals(test, copy);

    copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertNotSame(test.get(3), copy.get(3));
    assertEquals(test, copy);
  }

  @Test
  void testReferences() {
    ArrayList test = new ArrayList();
    test.add("one");
    test.add("two");
    test.add("three");

    ArrayList test2 = new ArrayList();
    test2.add(1);
    test2.add(2f);
    test2.add(3d);
    test2.add((byte) 4);
    test2.add((short) 5);
    test.add(test2);
    test.add(test2);
    test.add(test2);

    ArrayList copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertEquals(test, copy);
    assertNotSame(test.get(3), copy.get(4));
    assertSame(copy.get(3), copy.get(4));
    assertSame(copy.get(3), copy.get(5));

    copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertEquals(test, copy);
    assertNotSame(test.get(3), copy.get(4));
    assertNotSame(copy.get(3), copy.get(4));
    assertNotSame(copy.get(3), copy.get(5));
  }

  @Test
  void testCircularReferences() {
    ArrayList test = new ArrayList();
    test.add("one");
    test.add("two");
    test.add("three");
    test.add(test);

    ArrayList copy = getFury().copy(test);
    assertNotSame(test, copy);
    assertEquals(copy.get(0), "one");
    assertEquals(copy.get(1), "two");
    assertEquals(copy.get(2), "three");
    assertSame(copy.get(3), copy);

    Moo root = new Moo();
    Moo moo1 = new Moo();
    Moo moo2 = new Moo();
    Moo moo3 = new Moo();
    root.moo = moo1;
    moo1.moo = moo2;
    moo2.moo = moo3;
    moo3.moo = root;
    Moo root2 = getFury().copy(root);
    assertNotSame(root, root2);
    assertNotSame(root.moo, root2.moo);
    assertNotSame(root.moo.moo, root2.moo.moo);
    assertNotSame(root.moo.moo.moo, root2.moo.moo.moo);
    assertNotSame(root.moo.moo.moo.moo, root2.moo.moo.moo.moo);
    assertSame(root.moo.moo.moo.moo, root);
    assertSame(root2.moo.moo.moo.moo, root2);
  }

  @Test
  void testShallow() {
    ArrayList test = new ArrayList();
    test.add("one");
    test.add("two");
    test.add("three");

    ArrayList test2 = new ArrayList();
    test2.add(1);
    test2.add(2f);
    test2.add(3d);
    test2.add((byte) 4);
    test2.add((short) 5);
    test.add(test2);

    ArrayList copy = getFury().copyShallow(test);
    assertNotSame(test, copy);
    assertSame(test.get(3), copy.get(3));
    assertEquals(test, copy);
  }

  public static class Moo {
    Moo moo;
  }
}
