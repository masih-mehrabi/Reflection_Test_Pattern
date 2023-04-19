package de.tum.in.ase.pse;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;


@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
class RentalSystemTest {

	private String basePackage = "de.tum.in.ase.pse.";

	@Test
	void testRentItem() throws ClassNotFoundException {
		try {
			Class.forName(basePackage + "Book");
		} catch (ClassNotFoundException e) {
			fail("<i>class Book</i> was not found");
		}
		try {
			Class<?> book = Class.forName(basePackage + "Rentable");
			if (!book.isInterface()) {
				fail("<i>interface Rentable</i> was not implemented as expected");
			}
		} catch (ClassNotFoundException e) {
			fail("<i>interface Rentable</i> was not implemented as expected");
		}
		if (!Class.forName(basePackage + "Rentable").isAssignableFrom(Class.forName(basePackage + "Book"))) {
				fail("<i>Book does not implement the Rentable interface</i>");
		}
		try {
			Class.forName(basePackage + "Book").getConstructor();
		} catch (NoSuchMethodException e) {
			fail("Book <i>does not provide a default constructor</i>");
		} try {
			Field field = Class.forName(basePackage + "Book").getDeclaredField("available");
			
			int mod = field.getModifiers();
			boolean result = Modifier.isPrivate(mod);
			if (!result) {
				fail("the <i>attribute available in class Book</i> is not provided as described");
			}
		} catch (NoSuchFieldException e) {
			fail("the <i>attribute available in class Book</i> is not provided as described");
		} try {
			Class<?> book = Class.forName(basePackage + "Book");
			Object obj = book.newInstance();
			
			Method method = Class.forName(basePackage + "Book").getDeclaredMethod("rentItem");
			method.invoke(obj);
			
			Field field = book.getDeclaredField("available");
			boolean flag = true;
			field.setAccessible(flag);
			Object value = field.get(obj);
			if (!Objects.equals(value.toString(), "false")) {
				fail("<i>available was not updated correctly</i>");
			}
			
		} catch (NoSuchMethodException
		         | NoSuchFieldException
		         | InstantiationException
		         | IllegalAccessException
		         | InvocationTargetException e) {
			fail("<i>available was not updated correctly</i>");
		}
	}
}
