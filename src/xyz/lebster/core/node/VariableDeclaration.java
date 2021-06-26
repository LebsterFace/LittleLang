package xyz.lebster.core.node;

import xyz.lebster.core.runtime.Interpreter;
import xyz.lebster.core.value.Undefined;
import xyz.lebster.core.value.Value;
import xyz.lebster.exception.LanguageException;

public record VariableDeclaration(VariableDeclarator... declarations) implements Declaration {
	@Override
	public void dump(int indent) {
		Interpreter.dumpIndent(indent);
		System.out.println("VariableDeclaration:");
		for (VariableDeclarator declarator : declarations) declarator.dump(indent + 1);
	}

	@Override
	public Value<?> execute(Interpreter interpreter) throws LanguageException {
		for (VariableDeclarator declarator : declarations) {
			declarator.execute(interpreter);
		}

		return new Undefined();
	}
}
