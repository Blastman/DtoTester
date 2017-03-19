package com.objectpartners;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

/**
 * Tests all the Dto's in the package.
 *
 * Test classes themselves are skipped, based on the condition that they have a public, @Test annotated method.
 * However, for the {@link GetterSetterPair} there's no such condition so it's excluded explicitely.
 */
public class PackageTest extends AllDtoInPackageTest {

    @Override
    protected Set<Class<?>> classesToExclude() {
        return ImmutableSet.of( GetterSetterPair.class );
    }


}
