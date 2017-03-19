package com.objectpartners;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
* Convenience test case base that considers all classes in the package to be Dto's. 
*/
public abstract class AllDtoInPackageTest {

    private static final Class JUNIT_TEST = Test.class;
    private final Map<Class<?>, Supplier<?>> mappers;

    public AllDtoInPackageTest() {
        this.mappers = Collections.<Class<?>, Supplier<?>> emptyMap();
    }

    public AllDtoInPackageTest(Map<Class<?>, Supplier<?>> mappers) {
        final ImmutableMap.Builder<Class<?>, Supplier<?>> builder = ImmutableMap.builder();
        builder.putAll( mappers );
        this.mappers = builder.build();
    }

    protected Set<Class<?>> classesToExclude() {
        return new HashSet<>();
    }

    @Test
    public void testAllDtoInPackage() throws Exception {
        Set<Class<?>> classesToExclude = classesToExclude();

        ClassPath cp = ClassPath.from( getClass().getClassLoader() );
        for ( ClassInfo ci : cp.getTopLevelClasses( getClass().getPackage()
                                                              .getName() ) ) {
            Class classToTest = Class.forName( ci.getName() );
            if ( !classesToExclude.contains( classToTest )
                    && !isTestCase( classToTest )
                    && !isAbstract( classToTest )
                    && hasParameterlessConstructor( classToTest ) ) {
                System.out.println( "testing: " + ci.getName() );
                new InternalDtoTest( classToTest.newInstance(), mappers ).testGettersAndSetters();
            }
        }
    }

    private boolean isTestCase(Class clazz) {
        for ( Method method : clazz.getMethods() ) {
            if ( method.getAnnotationsByType( JUNIT_TEST ).length > 0 ) {
                return true;
            }
        }
        return false;
    }

    private boolean hasParameterlessConstructor(Class<?> clazz) {
        return Stream.of( clazz.getConstructors() )
                     .anyMatch( (c) -> c.getParameterCount() == 0 );
    }

    private boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract( clazz.getModifiers() );
    }

    private static class InternalDtoTest<T> extends DtoTest<T> {

        private final T instance;

        public InternalDtoTest(T instance, Map<Class<?>, Supplier<?>> customMappers) {
            super( customMappers, null );
            this.instance = instance;
        }

        public InternalDtoTest(T instance) {
            this.instance = instance;
        }

        @Override
        protected T getInstance() {
            return instance;
        }

    }
}
