package xyz.lebster.core.runtime;

import xyz.lebster.core.ANSI;
import xyz.lebster.core.SpecificationURL;
import xyz.lebster.core.exception.NotImplemented;
import xyz.lebster.core.interpreter.AbruptCompletion;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.StringRepresentation;
import xyz.lebster.core.node.value.*;
import xyz.lebster.core.runtime.prototype.ArrayPrototype;

import java.util.Set;


public final class ArrayObject extends Dictionary {
	public final static StringLiteral LENGTH_KEY = new StringLiteral("length");
	private int length;

	public final NativeProperty LENGTH_GETTER_SETTER = new NativeProperty(new NativeGetterSetter() {
		@Override
		public Value<?> get(Interpreter interpreter) {
			return new NumericLiteral(length);
		}

		@Override
		@SpecificationURL("https://tc39.es/ecma262/multipage/#sec-arraysetlength")
		// FIXME: Follow spec
		public void set(Interpreter interpreter, Value<?> value) throws AbruptCompletion {
			final int newLen = (int) Math.floor(value.toNumericLiteral(interpreter).value);
			if (newLen > length) {
				ArrayObject.this.length = newLen;
			} else {
				throw new NotImplemented("Reducing array length");
			}
		}
	});

	@Override
	public void representRecursive(StringRepresentation representation, Set<Dictionary> parents) {
		representation.append('[');
		if (value.isEmpty()) {
			representation.append(']');
			return;
		}

		parents.add(this);
		for (int index = 0; index < this.length; index++) {
			final Value<?> element = this.get(new StringLiteral(index));
			if (element instanceof final Dictionary dictionary) {
				if (parents.contains(dictionary)) {
					representation.append(ANSI.RED);
					representation.append(this == element ? "[self]" : "[parent]");
					representation.append(ANSI.RESET);
				} else {
					dictionary.representRecursive(representation, parents);
				}
			} else {
				element.represent(representation);
			}

			if (index != this.length - 1)
				representation.append(", ");
		}

		representation.append(']');
	}

	public ArrayObject(Value<?>[] values) {
		this.length = values.length;
		this.put(LENGTH_KEY, LENGTH_GETTER_SETTER);

		for (int i = 0; i < values.length; i++) {
			this.put(String.valueOf(i), values[i]);
		}
	}

	@Override
	public Dictionary getPrototype() {
		return ArrayPrototype.instance;
	}
}