package xyz.lebster.core;

@Deprecated
public final class Dumper {
	private Dumper() {
	}

	public static void dumpIndent(int indent) {
		System.out.print("  ".repeat(indent));
	}

	public static void dumpName(int indent, String name) {
		dumpIndent(indent);
		System.out.printf("%s%s%s:%n", ANSI.BRIGHT_GREEN, name, ANSI.RESET);
	}

	public static void dumpParameterized(int indent, String name, String param) {
		dumpIndent(indent);
		System.out.printf("%s%s %s%s%s:%n", ANSI.BRIGHT_GREEN, name, ANSI.BRIGHT_YELLOW, param, ANSI.RESET);
	}

	public static void dumpValue(int indent, String name, String value) {
		dumpIndent(indent);
		System.out.printf("%s%s %s%s%s%n", ANSI.CYAN, name, ANSI.BRIGHT_YELLOW, value, ANSI.RESET);
	}

	public static void dumpValue(int indent, String value) {
		dumpIndent(indent);
		System.out.printf("%s%s%s%n", ANSI.CYAN, value, ANSI.RESET);
	}

	public static void dumpString(int indent, String value) {
		dumpIndent(indent);
		System.out.printf("%s%s%s%n", ANSI.BRIGHT_GREEN, value, ANSI.RESET);
	}

	public static void dumpEnum(int indent, Enum<?> value) {
		dumpIndent(indent);
		System.out.printf("%s%s %s%s%s%n", ANSI.BRIGHT_RED, value.getClass().getSimpleName(), ANSI.BRIGHT_YELLOW, value, ANSI.RESET);
	}

	public static void dumpIndicator(int indent, String indicator) {
		dumpIndent(indent);
		System.out.printf("%s(%s)%s%n", ANSI.BRIGHT_MAGENTA, indicator, ANSI.RESET);
	}
}