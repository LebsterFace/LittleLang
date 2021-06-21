package xyz.lebster;

import xyz.lebster.core.value.BooleanLiteral;
import xyz.lebster.core.value.Dictionary;
import xyz.lebster.core.value.NativeFunction;
import xyz.lebster.core.value.Value;
import xyz.lebster.exception.LanguageException;

import java.io.File;

public class Testing {
	public static void test() {
		final File[] files = new File("tests/").listFiles();
		if (files == null) throw new Error("Test directory not found!");
		int successfulTests = 0;
		int totalTests = 0;

		for (final File file : files) {
			if (!file.isFile()) continue;
			totalTests++;
			System.out.println(Main.ANSI_GREEN + "Running test '" + file.getName() + "'..." + Main.ANSI_RESET);
			final Dictionary globalObject = ScriptExecutor.getDefaultGlobalObject();
			addTestingMethods(globalObject);
			final boolean succeeded = ScriptExecutor.executeFileWithHandling(file.toPath(), globalObject, false);
			if (succeeded) {
				System.out.println(Main.ANSI_GREEN + "Passed!" + Main.ANSI_RESET);
				successfulTests++;
			}
		}

		final double percentage = 100.0D * ((double) successfulTests / (double) totalTests);
		System.out.println("--- TESTING FINISHED ---");
		System.out.printf("%d passed out of %d total (%.3f%%)%n", successfulTests, totalTests, percentage);
	}

	private static void addTestingMethods(Dictionary globalObject) {
		globalObject.set("expect", new NativeFunction((interpreter, arguments) -> {
			final Value<?> expected = arguments[0];
			final Value<?> received = arguments[1];

			if (expected.equals(received)) {
				return new BooleanLiteral(true);
			} else {
				System.out.print("Expecting: ");
				expected.dump(0);
				System.out.print("Received: ");
				received.dump(0);
				throw new LanguageException("Assertion failed!");
			}
		}));
	}
}
