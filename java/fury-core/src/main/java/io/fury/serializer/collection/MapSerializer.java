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

package io.fury.serializer.collection;

import io.fury.Fury;
import io.fury.memory.MemoryBuffer;
import java.util.Map;

/**
 * Base serializer for all java maps.
 *
 * @author chaokunyang
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MapSerializer<T extends Map> extends AbstractMapSerializer<T> {
  public MapSerializer(Fury fury, Class<T> cls) {
    super(fury, cls);
  }

  public MapSerializer(Fury fury, Class<T> cls, boolean supportCodegenHook) {
    super(fury, cls, supportCodegenHook);
  }

  @Override
  public Map onMapWrite(MemoryBuffer buffer, T value) {
    buffer.writePositiveVarInt(value.size());
    return value;
  }

  @Override
  public T read(MemoryBuffer buffer) {
    Map map = newMap(buffer);
    readElements(buffer, numElements, map);
    return onMapRead(map);
  }

  @Override
  public T onMapRead(Map map) {
    return (T) map;
  }
}
