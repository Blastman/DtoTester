DTO Tester, as explained in the Blog Post "[Automatically JUnit Test DTO and Transfer Objects](https://objectpartners.com/2016/02/16/automatically-junit-test-dto-and-transfer-objects/)"


To use it as a maven dependency, build it and install in your local maven repository using `mvn install` and import in your project as a test-scoped dependency. E.g.:

```
<dependency>
	<groupId>com.objectpartners</groupId>
	<artifactId>dtotester</artifactId>
	<classifier>tests</classifier>
	<type>test-jar</type>
	<version>0.0.1-SNAPSHOT</version>
	<scope>test</scope>
</dependency>
```

To test simple DTOs, extend DtoTest<> in your test class:

```
package your.package;

import com.objectpartners.DtoTest;

public class MyOwnDtoTest extends DtoTest<MyOwnDto> {

	@Override
	protected MyOwnDto getInstance() {
		return new MyOwnDto();
	}

}
```

For more information, please read the [blog post](https://objectpartners.com/2016/02/16/automatically-junit-test-dto-and-transfer-objects/)