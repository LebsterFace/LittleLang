package xyz.lebster.core.runtime.value.prototype;

import xyz.lebster.core.SpecificationURL;
import xyz.lebster.core.interpreter.AbruptCompletion;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.runtime.Names;
import xyz.lebster.core.runtime.value.HasBuiltinTag;
import xyz.lebster.core.runtime.value.Value;
import xyz.lebster.core.runtime.value.constructor.ObjectConstructor;
import xyz.lebster.core.runtime.value.error.TypeError;
import xyz.lebster.core.runtime.value.native_.NativeFunction;
import xyz.lebster.core.runtime.value.object.ObjectValue;
import xyz.lebster.core.runtime.value.primitive.*;

public final class ObjectPrototype extends ObjectValue {
	public static final ObjectPrototype instance = new ObjectPrototype();
	public static final NativeFunction toStringMethod = new NativeFunction(Names.toString, ObjectPrototype::toStringMethod);

	static {
		instance.put("constructor", ObjectConstructor.instance);
		instance.put(Names.toString, toStringMethod);
		instance.putMethod(Names.valueOf, ObjectPrototype::valueOf);
		instance.putMethod("hasOwnProperty", (interpreter, args) -> {
			final ObjectValue object = interpreter.thisValue().toObjectValue(interpreter);
			final ObjectValue.Key<?> property = args.length > 0 ? args[0].toPropertyKey(interpreter) : Names.undefined;
			return BooleanValue.of(object.hasOwnProperty(property));
		});

		instance.putMethod("hasProperty", (interpreter, args) -> {
			final ObjectValue object = interpreter.thisValue().toObjectValue(interpreter);
			final ObjectValue.Key<?> property = args.length > 0 ? args[0].toPropertyKey(interpreter) : Names.undefined;
			return BooleanValue.of(object.hasProperty(property));
		});
	}

	private ObjectPrototype() {
	}

	@SpecificationURL("https://tc39.es/ecma262/multipage#sec-requireobjectcoercible")
	public static Value<?> requireObjectCoercible(Value<?> argument, String methodName) throws AbruptCompletion {
		if (argument.isNullish()) {
			throw AbruptCompletion.error(new TypeError(methodName + " called on null or undefined"));
		} else {
			return argument;
		}
	}

	@SpecificationURL("https://tc39.es/ecma262/multipage#sec-object.prototype.tostring")
	public static StringValue toStringMethod(Interpreter interpreter, Value<?>[] arguments) throws AbruptCompletion {
		// 1. If the `this` value is undefined, return "[object Undefined]".
		if (interpreter.thisValue() == Undefined.instance) {
			return new StringValue("[object Undefined]");
		} else if (interpreter.thisValue() == Null.instance) {
			return new StringValue("[object Null]");
		} else {// 3. Let O be ! ToObject(this value).
			final ObjectValue O = interpreter.thisValue().toObjectValue(interpreter);
			// 4. Let isArray be ? IsArray(O).
			// 5. If isArray is true, let builtinTag be "Array".
			// 6. Else if O has a [[ParameterMap]] internal slot, let builtinTag be "Arguments".
			// 7. Else if O has a [[Call]] internal method, let builtinTag be "Function".
			// 8. Else if O has an [[ErrorData]] internal slot, let builtinTag be "Error".
			// 9. Else if O has a [[BooleanData]] internal slot, let builtinTag be "Boolean".
			// 10. Else if O has a [[NumberData]] internal slot, let builtinTag be "Number".
			// 11. Else if O has a [[StringData]] internal slot, let builtinTag be "String".
			// 12. Else if O has a [[DateValue]] internal slot, let builtinTag be "Date".
			// FIXME: Date Objects
			// 13. Else if O has a [[RegExpMatcher]] internal slot, let builtinTag be "RegExp".
			// FIXME: RegExp Objects
			// 14. Else, let builtinTag be "Object".
			final String builtinTag = (O instanceof final HasBuiltinTag hbt) ? hbt.getBuiltinTag() : "Object";
			// 15. Let tag be ? Get(O, @@toStringTag).
			final Value<?> tag = O.getWellKnownSymbolOrUndefined(interpreter, SymbolValue.toStringTag);
			// 16. If Type(tag) is not String, set tag to builtinTag.
			// 17. Return the string-concatenation of "[object ", tag, and "]".
			if (tag instanceof final StringValue stringValue) {
				return new StringValue("[object " + stringValue.value + "]");
			} else {
				return new StringValue("[object " + builtinTag + "]");
			}
		}
	}

	@SpecificationURL("https://tc39.es/ecma262/multipage#sec-object.prototype.valueof")
	private static Value<?> valueOf(Interpreter interpreter, Value<?>[] arguments) throws AbruptCompletion {
		return interpreter.thisValue().toObjectValue(interpreter);
	}

	@Override
	public ObjectValue getDefaultPrototype() {
		return null;
	}
}