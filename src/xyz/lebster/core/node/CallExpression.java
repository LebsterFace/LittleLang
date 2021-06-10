package xyz.lebster.core.node;

import xyz.lebster.core.exception.LTypeError;
import xyz.lebster.core.runtime.Interpreter;
import xyz.lebster.core.exception.LanguageException;
import xyz.lebster.core.value.*;

public class CallExpression extends Expression {
	public final Identifier callee;
	public final Expression[] arguments;

	public CallExpression(Identifier callee, Expression... args) {
		this.callee = callee;
		this.arguments = args;
	}

	public CallExpression(String callee, Expression... args) {
		this(new Identifier(callee), args);
	}

	@Override
	public void dump(int indent) {
		Interpreter.dumpIndent(indent);
		System.out.print("CallExpression ");
		System.out.print(callee);
		System.out.println(":");
		for (Expression argument : arguments) {
			argument.dump(indent + 1);
		}
	}

	private Value<?>[] executeArguments(Interpreter interpreter) throws LanguageException {
		final Value<?>[] result = new Value<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) result[i] = arguments[i].execute(interpreter);
		return result;
	}

	@Override
	public Value<?> execute(Interpreter interpreter) throws LanguageException {
		final Value<?> value = interpreter.getVariable(callee);

		if (value.type == Type.Function) {
			final Function func = (Function) value;
			final Value<?>[] arguments = this.executeArguments(interpreter);
			final Value<?> result = func.executeChildren(interpreter, arguments);
			interpreter.exitScope(func.value);
			return result;
		} else if (value.type == Type.NativeFunction) {
			final NativeCode code = ((NativeFunction) value).value;
			final Value<?>[] arguments = this.executeArguments(interpreter);
			final Value<?> result = code.execute(interpreter, arguments);
			return result;
		} else {
			throw new LTypeError("Can only call a function!");
		}
	}
}
