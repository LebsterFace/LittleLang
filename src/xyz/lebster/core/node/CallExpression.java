package xyz.lebster.core.node;

import xyz.lebster.core.exception.LTypeError;
import xyz.lebster.core.exception.LanguageException;
import xyz.lebster.core.runtime.Interpreter;
import xyz.lebster.core.value.Function;
import xyz.lebster.core.value.NativeCode;
import xyz.lebster.core.value.Type;
import xyz.lebster.core.value.Value;

public class CallExpression implements Expression {
	public final Expression callee;
	public final Expression[] arguments;

	public CallExpression(Expression callee, Expression... args) {
		this.callee = callee;
		this.arguments = args;
	}

	public CallExpression(String callee, Expression... args) {
		this(new Identifier(callee), args);
	}

	@Override
	public void dump(int indent) {
		Interpreter.dumpIndent(indent);
		System.out.println("CallExpression:");
		Interpreter.dumpIndent(indent + 1);
		System.out.println("Callee:");
		callee.dump(indent + 2);
		Interpreter.dumpIndent(indent + 1);
		System.out.println(arguments.length > 0 ? "Arguments:" : "[[NO ARGS]]");
		for (Expression argument : arguments) {
			argument.dump(indent + 2);
		}
	}

	private Value<?>[] executeArguments(Interpreter interpreter) throws LanguageException {
		final Value<?>[] result = new Value<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) result[i] = arguments[i].execute(interpreter);
		return result;
	}

	@Override
	public Value<?> execute(Interpreter interpreter) throws LanguageException {
		final Value<?> value = callee.execute(interpreter);

		if (value.type == Type.Function) {
			final Function func = (Function) value;
			final Value<?>[] arguments = this.executeArguments(interpreter);
			final Value<?> result = func.executeChildren(interpreter, arguments);
			interpreter.exitScope(func.value);
			return result;
		} else if (value.type == Type.NativeFunction) {
			final NativeCode code = (NativeCode) value.value;
			final Value<?>[] arguments = this.executeArguments(interpreter);
			return code.execute(interpreter, arguments);
		} else {
			throw new LTypeError("'LittleLang::" + value.getClass().getSimpleName() + "' is not a function");
		}
	}
}
