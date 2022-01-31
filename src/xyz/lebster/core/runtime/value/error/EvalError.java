package xyz.lebster.core.runtime.value.error;

import xyz.lebster.core.ANSI;
import xyz.lebster.core.interpreter.StringRepresentation;

public final class EvalError extends LanguageError {
	public final Throwable wrappedThrowable;

	public EvalError(Throwable e) {
		super(e.getMessage());
		this.wrappedThrowable = e;
	}

	@Override
	public String toString() {
		return wrappedThrowable.getClass().getSimpleName() + ": " + message;
	}

	@Override
	public void display(StringRepresentation representation) {
		representation.append(ANSI.BRIGHT_CYAN);
		representation.append("[");
		representation.append(wrappedThrowable.getClass().getSimpleName());
		representation.append(": ");
		representation.append(message);
		representation.append("]");
		representation.append(ANSI.RESET);
	}
}
