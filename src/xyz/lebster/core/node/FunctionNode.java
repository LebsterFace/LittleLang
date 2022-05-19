package xyz.lebster.core.node;

import xyz.lebster.core.interpreter.AbruptCompletion;
import xyz.lebster.core.interpreter.ExecutionContext;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.node.statement.BlockStatement;
import xyz.lebster.core.value.Value;
import xyz.lebster.core.value.globals.Undefined;

public interface FunctionNode extends ASTNode {
	String name();

	String[] arguments();

	BlockStatement body();

	default String toCallString() {
		final String name = name();
		final String[] arguments = arguments();

		final StringBuilder builder = new StringBuilder(name == null ? "" : name);
		builder.append('(');
		if (arguments.length > 0) {
			builder.append(arguments[0]);
			for (int i = 1; i < arguments.length; i++) {
				builder.append(", ");
				builder.append(arguments[i]);
			}
		}
		builder.append(')');
		return builder.toString();
	}

	default Value<?> execute(Interpreter interpreter, ExecutionContext context, Value<?>[] passedArguments) throws AbruptCompletion {
		// Declare passed arguments as variables
		int i = 0;
		for (; i < arguments().length && i < passedArguments.length; i++)
			interpreter.declareVariable(arguments()[i], passedArguments[i]);
		for (; i < arguments().length; i++)
			interpreter.declareVariable(arguments()[i], Undefined.instance);

		try {
			body().execute(interpreter);
			return Undefined.instance;
		} catch (AbruptCompletion e) {
			if (e.type != AbruptCompletion.Type.Return) throw e;
			return e.value;
		} finally {
			interpreter.exitExecutionContext(context);
		}
	}
}