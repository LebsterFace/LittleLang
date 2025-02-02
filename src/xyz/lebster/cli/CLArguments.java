package xyz.lebster.cli;

import xyz.lebster.core.ANSI;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

public record CLArguments(Path filePathOrNull, ExecutionMode mode, ExecutionOptions options) {
	public static CLArguments from(String[] args) throws CLArgumentException {
		final Iterator<String> iterator = Arrays.asList(args).iterator();
		final TemporaryResult result = new TemporaryResult(iterator);

		while (iterator.hasNext()) {
			final String argument = iterator.next();
			if (argument.equalsIgnoreCase("--help") ||
				argument.equalsIgnoreCase("-h") ||
				argument.equals("/?") || argument.equals("-?")
			) {
				// Special case for help:
				System.out.printf("%sLebJS Usage: %slebjs %s[parameters] %s[path]%s%n", ANSI.RED, ANSI.RESET, ANSI.CYAN, ANSI.GREEN, ANSI.RESET);
				System.out.printf("%s-v%s, %s--verbose%s         Don't hide stack traces%n", ANSI.CYAN, ANSI.RESET, ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--parse-only%s          Ignore test failures from parsing%n", ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--ignore-not-impl%s     Ignore test failures from unimplemented features%n", ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--no-buffer%s           Do not buffer test outputs%n", ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--hide-passing%s        Only output skipped / failing tests (ignored if %s--no-buffer%s specified)%n", ANSI.CYAN, ANSI.RESET, ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--disable-prompt%s      Disable the %s'> '%s prompt in the REPL%n", ANSI.CYAN, ANSI.RESET, ANSI.BRIGHT_GREEN, ANSI.RESET);
				System.out.printf("%s--harness %s[value]%s     Test harness. Valid options: %sladybird%s%n", ANSI.CYAN, ANSI.MAGENTA, ANSI.RESET, ANSI.MAGENTA, ANSI.RESET);
				System.out.printf("%s-t%s, %s--test%s            Run tests%n", ANSI.CYAN, ANSI.RESET, ANSI.CYAN, ANSI.RESET);
				System.out.printf("%s--gif%s                 Enable GIF rendering mode (No error handling, no prompt, print delimiter after execution)%n", ANSI.CYAN, ANSI.RESET);
				System.exit(0);
			}

			if (argument.startsWith("--")) {
				result.setFlag(argument.substring(2).toLowerCase());
			} else if (argument.startsWith("-")) {
				for (final String flag : argument.substring(1).toLowerCase().split("")) {
					result.setFlag(flag);
				}
			} else {
				result.setFilename(argument);
			}
		}

		return result.toCLIArguments();
	}

	public record ExecutionOptions(
		boolean hideStackTrace,
		boolean parseOnly,
		boolean ignoreNotImplemented,
		boolean hidePassing,
		boolean disableTestOutputBuffers,
		String harness,
		boolean showPrompt
	) {
	}

	private static final class TemporaryResult {
		private final Iterator<String> arguments;
		private String fileNameOrNull = null;
		private ExecutionMode mode = null;
		private boolean hideStackTrace = true;
		private boolean parseOnly = false;
		private boolean ignoreNotImplemented = false;
		private boolean hidePassing = false;
		private boolean disableTestOutputBuffers = false;
		private String harness;
		private boolean showPrompt = true;

		public TemporaryResult(Iterator<String> arguments) {
			this.arguments = arguments;
		}

		private ExecutionOptions toExecutionOptions() {
			return new ExecutionOptions(
				this.hideStackTrace,
				this.parseOnly,
				this.ignoreNotImplemented,
				this.hidePassing,
				this.disableTestOutputBuffers,
				this.harness,
				this.showPrompt
			);
		}

		private void setFlag(String flag) throws CLArgumentException {
			switch (flag) {
				case "v", "verbose" -> hideStackTrace = false;
				case "parse-only" -> parseOnly = true;
				case "ignore-not-impl" -> ignoreNotImplemented = true;
				case "hide-passing" -> hidePassing = true;
				case "disable-prompt" -> showPrompt = false;
				case "no-buffer" -> disableTestOutputBuffers = true;
				case "harness" -> harness = getFlagValue("Missing harness filepath");
				case "t", "test" -> setMode(ExecutionMode.Tests);
				case "gif" -> setMode(ExecutionMode.GIF);

				default -> throw new CLArgumentException("Unknown flag '%s'".formatted(flag));
			}
		}

		private String getFlagValue(String missingMessage) throws CLArgumentException {
			if (!arguments.hasNext()) throw new CLArgumentException(missingMessage);
			return arguments.next();
		}

		private void setMode(ExecutionMode newMode) throws CLArgumentException {
			if (this.mode == null) {
				this.mode = newMode;
			} else {
				throw new CLArgumentException("Mode was specified twice");
			}
		}

		private void setFilename(String argument) throws CLArgumentException {
			if (this.mode != null) {
				if (this.mode == ExecutionMode.File) {
					throw new CLArgumentException("File name was specified twice");
				} else if (this.mode == ExecutionMode.GIF) {
					throw new CLArgumentException("File name should not be specified for mode GIF");
				}
			}

			this.fileNameOrNull = argument;
			if (this.mode == null)
				this.mode = ExecutionMode.File;
		}

		private CLArguments toCLIArguments() throws CLArgumentException {
			if (mode == null) mode = ExecutionMode.REPL;
			if (mode != ExecutionMode.REPL && !showPrompt) {
				throw new CLArgumentException("Cannot disable prompt when not using REPL mode");
			}

			if (mode != ExecutionMode.Tests) {
				if (disableTestOutputBuffers) throw new CLArgumentException("Cannot disable test output buffers unless mode is Tests");
				if (parseOnly) throw new CLArgumentException("Cannot ignore test failures from runtime errors unless mode is Tests");
				if (ignoreNotImplemented) throw new CLArgumentException("Cannot ignore test failures from unimplemented features unless mode is Tests");
				if (hidePassing) throw new CLArgumentException("Cannot hide passing tests unless mode is Tests");
				if (harness != null) throw new CLArgumentException("Cannot specify test harness unless mode is Tests");
			}

			return new CLArguments(
				fileNameOrNull == null ? null : Path.of(fileNameOrNull),
				mode,
				toExecutionOptions()
			);
		}
	}
}