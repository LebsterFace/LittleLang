package xyz.lebster.core.node;

import xyz.lebster.core.expression.Expression;
import xyz.lebster.core.runtime.Interpreter;
import xyz.lebster.core.value.Type;
import xyz.lebster.core.value.Undefined;
import xyz.lebster.core.value.Value;
import xyz.lebster.exception.LanguageException;
import xyz.lebster.exception.NotImplemented;

public class IfStatement extends ScopeNode {
	public final Expression condition;

	public IfStatement(Expression condition) {
		this.condition = condition;
	}

	@Override
	public void dump(int indent) {
		Interpreter.dumpName(indent, "IfStatement");
		condition.dump(indent + 1);
		for (final ASTNode child : children) {
			child.dump(indent + 1);
		}
	}

	@Override
	public Value<?> execute(Interpreter interpreter) throws LanguageException {
		final Value<?> res = condition.execute(interpreter);
//		FIXME: truthy/falsy
		if (res.type != Type.Boolean) throw new NotImplemented("Truthy/Falsy");
		else if ((boolean) res.value) {
			return executeChildren(interpreter);
		} else {
			return new Undefined();
		}
	}
}
