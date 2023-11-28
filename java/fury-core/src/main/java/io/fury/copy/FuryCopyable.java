package io.fury.copy;

import io.fury.Fury;

/**
 *
 *
 * @author onlylyj
 */
public interface FuryCopyable<T> {

    T copy(Fury fury);
}
