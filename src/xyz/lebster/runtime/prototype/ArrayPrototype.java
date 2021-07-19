package xyz.lebster.runtime.prototype;

import xyz.lebster.interpreter.AbruptCompletion;
import xyz.lebster.node.value.*;
import xyz.lebster.runtime.ArrayObject;
import xyz.lebster.runtime.TypeError;

public final class ArrayPrototype extends Dictionary {
	public static final ArrayPrototype instance = new ArrayPrototype();
	public static final long MAX_LENGTH = 9007199254740991L;

	private ArrayPrototype() {
//		https://tc39.es/ecma262/multipage#sec-array.prototype.push
		set("push", new NativeFunction((interpreter, elements) -> {
			final Dictionary O = interpreter.thisValue().toDictionary();
			final long len = Long.min(MAX_LENGTH, O.get(ArrayObject.length).toNumericLiteral().value.longValue());

			if ((len + elements.length) > MAX_LENGTH) {
				throw AbruptCompletion.error(new TypeError("Pushing " + elements.length + " elements on an array-like of length " + len + " is disallowed, as the total surpasses 2**53-1"));
			}

			for (final Value<?> E : elements) O.set(new StringLiteral(len), E);
			final NumericLiteral newLength = new NumericLiteral(len + elements.length);
			O.set(ArrayObject.length, newLength);
			return newLength;
		}));

//		https://tc39.es/ecma262/multipage#sec-array.prototype.join
		set("join", new NativeFunction((interpreter, elements) -> {
			final Dictionary O = interpreter.thisValue().toDictionary();
			final long len = Long.min(MAX_LENGTH, O.get(ArrayObject.length).toNumericLiteral().value.longValue());
			final String sep = elements.length == 0 || elements[0].type == Type.Undefined ? "," : elements[0].toString();

			final StringBuilder R = new StringBuilder();
			for (int k = 0; k < len; k++) {
				if (k > 0) R.append(sep);
				final Value<?> element = O.get(new StringLiteral(k));
				R.append(element.isNullish() ? "" : element.toString());
			}

			return new StringLiteral(R.toString());
		}));
	}
}