function expectToBeCloseTo(expected, received) {
    let difference = expected - received;
    if (difference < 0) difference = -difference;
    if (difference > 0.000001)
        Test.expect(expected, received)
}

expectToBeCloseTo(Math.E, 2.718281);
expectToBeCloseTo(Math.LN2, 0.693147);
expectToBeCloseTo(Math.LN10, 2.302585);
expectToBeCloseTo(Math.LOG2E, 1.442695);
expectToBeCloseTo(Math.LOG10E, 0.434294);
expectToBeCloseTo(Math.PI, 3.1415926);
expectToBeCloseTo(Math.SQRT1_2, 0.707106);
expectToBeCloseTo(Math.SQRT2, 1.414213);
Test.expect("[object Math]", Math.toString());
Test.expect(1, Math.abs("-1"));
Test.expect(2, Math.abs(-2));
Test.expect(0, Math.abs(null));
Test.expect(0, Math.abs(""));
Test.expect(0, Math.abs([]));
Test.expect(2, Math.abs([2]));
Test.expect(NaN, Math.abs([1, 2]));
Test.expect(NaN, Math.abs({}));
Test.expect(NaN, Math.abs("string"));
Test.expect(NaN, Math.abs());
Test.expect(NaN, Math.acosh(-1));
Test.expect(NaN, Math.acosh(0));
Test.expect(NaN, Math.acosh(0.5));
expectToBeCloseTo(Math.acosh(1), 0);
expectToBeCloseTo(Math.acosh(2), 1.316957);
Test.expect(0, Math.asin(0));
Test.expect(0, Math.asin(null));
Test.expect(0, Math.asin(""));
Test.expect(0, Math.asin([]));
Test.expect(NaN, Math.asin());
Test.expect(NaN, Math.asin(undefined));
Test.expect(NaN, Math.asin([1, 2, 3]));
Test.expect(NaN, Math.asin({}));
Test.expect(NaN, Math.asin("foo"));
expectToBeCloseTo(Math.asinh(0), 0);
expectToBeCloseTo(Math.asinh(1), 0.881373);
Test.expect(0, Math.atan(0));
Test.expect(-0, Math.atan(-0));
Test.expect(NaN, Math.atan(NaN));
expectToBeCloseTo(Math.atan(-2), -1.1071487177940904);
expectToBeCloseTo(Math.atan(2), 1.1071487177940904);
expectToBeCloseTo(Math.atan(Infinity), Math.PI / 2);
expectToBeCloseTo(Math.atan(-Infinity), -Math.PI / 2);
expectToBeCloseTo(Math.atan(0.5), 0.4636476090008061);
expectToBeCloseTo(Math.atan2(90, 15), 1.4056476493802699);
expectToBeCloseTo(Math.atan2(15, 90), 0.16514867741462683);
expectToBeCloseTo(Math.atan2(0, -0), Math.PI);
expectToBeCloseTo(Math.atan2(-0, -0), -Math.PI);
Test.expect(0, Math.atan2(0, 0));
Test.expect(-0, Math.atan2(-0, 0));
expectToBeCloseTo(Math.atan2(0, -1), Math.PI);
expectToBeCloseTo(Math.atan2(-0, -1), -Math.PI);
Test.expect(0, Math.atan2(0, 1));
Test.expect(-0, Math.atan2(-0, 1));
expectToBeCloseTo(Math.atan2(-1, 0), -Math.PI / 2);
expectToBeCloseTo(Math.atan2(-1, -0), -Math.PI / 2);
expectToBeCloseTo(Math.atan2(1, 0), Math.PI / 2);
expectToBeCloseTo(Math.atan2(1, -0), Math.PI / 2);
expectToBeCloseTo(Math.atan2(1, -Infinity), Math.PI);
expectToBeCloseTo(Math.atan2(-1, -Infinity), -Math.PI);
Test.expect(0, Math.atan2(1, Infinity));
Test.expect(-0, Math.atan2(-1, Infinity));
expectToBeCloseTo(Math.atan2(Infinity, 1), Math.PI / 2);
expectToBeCloseTo(Math.atan2(-Infinity, 1), -Math.PI / 2);
expectToBeCloseTo(Math.atan2(Infinity, -Infinity), (3 * Math.PI) / 4);
expectToBeCloseTo(Math.atan2(-Infinity, -Infinity), (-3 * Math.PI) / 4);
expectToBeCloseTo(Math.atan2(Infinity, Infinity), Math.PI / 4);
expectToBeCloseTo(Math.atan2(-Infinity, Infinity), -Math.PI / 4);
Test.expect(NaN, Math.atanh(-2));
Test.expect(NaN, Math.atanh(2));
Test.expect(-Infinity, Math.atanh(-1));
Test.expect(0, Math.atanh(0));
expectToBeCloseTo(Math.atanh(0.5), 0.549306);
Test.expect(Infinity, Math.atanh(1));
Test.expect(NaN, Math.cbrt(NaN));
Test.expect(-1, Math.cbrt(-1));
Test.expect(-0, Math.cbrt(-0));
Test.expect(-Infinity, Math.cbrt(-Infinity));
Test.expect(1, Math.cbrt(1));
Test.expect(Infinity, Math.cbrt(Infinity));
Test.expect(0, Math.cbrt(null));
expectToBeCloseTo(Math.cbrt(2), 1.259921);
Test.expect(1, Math.ceil(0.95));
Test.expect(4, Math.ceil(4));
Test.expect(8, Math.ceil(7.004));
Test.expect(-0, Math.ceil(-0.95));
Test.expect(-4, Math.ceil(-4));
Test.expect(-7, Math.ceil(-7.004));
Test.expect(NaN, Math.ceil());
Test.expect(NaN, Math.ceil(NaN));
Test.expect(1, Math.cos(0));
Test.expect(1, Math.cos(null));
Test.expect(1, Math.cos(""));
Test.expect(1, Math.cos([]));
Test.expect(-1, Math.cos(Math.PI));
Test.expect(NaN, Math.cos());
Test.expect(NaN, Math.cos(undefined));
Test.expect(NaN, Math.cos([1, 2, 3]));
Test.expect(NaN, Math.cos({}));
Test.expect(NaN, Math.cos("foo"));
Test.expect(1, Math.cosh(0));
expectToBeCloseTo(Math.cosh(1), 1.5430806348152437);
expectToBeCloseTo(Math.cosh(-1), 1.5430806348152437);
Test.expect(1, Math.exp(0));
expectToBeCloseTo(Math.exp(-2), 0.135335);
expectToBeCloseTo(Math.exp(-1), 0.367879);
expectToBeCloseTo(Math.exp(1), 2.718281);
expectToBeCloseTo(Math.exp(2), 7.389056);
Test.expect(NaN, Math.exp());
Test.expect(NaN, Math.exp(undefined));
Test.expect(NaN, Math.exp("foo"));
Test.expect(0, Math.expm1(0));
expectToBeCloseTo(Math.expm1(-2), -0.864664);
expectToBeCloseTo(Math.expm1(-1), -0.63212);
expectToBeCloseTo(Math.expm1(1), 1.718281);
expectToBeCloseTo(Math.expm1(2), 6.389056);
Test.expect(NaN, Math.expm1());
Test.expect(NaN, Math.expm1(undefined));
Test.expect(NaN, Math.expm1("foo"));
Test.expect(0, Math.floor(0.95));
Test.expect(4, Math.floor(4));
Test.expect(7, Math.floor(7.004));
Test.expect(-1, Math.floor(-0.95));
Test.expect(-4, Math.floor(-4));
Test.expect(-8, Math.floor(-7.004));
Test.expect(NaN, Math.floor());
Test.expect(NaN, Math.floor(NaN));
Test.expect(5, Math.hypot(3, 4));
expectToBeCloseTo(Math.hypot(3, 4, 5), 7.0710678118654755);
Test.expect(0, Math.hypot());
Test.expect(NaN, Math.hypot(NaN));
Test.expect(NaN, Math.hypot(3, 4, "foo"));
expectToBeCloseTo(Math.hypot(3, 4, "5"), 7.0710678118654755);
Test.expect(3, Math.hypot(-3));
Test.expect(NaN, Math.log(-1));
Test.expect(-Infinity, Math.log(0));
Test.expect(0, Math.log(1));
expectToBeCloseTo(Math.log(10), 2.302585092994046);
expectToBeCloseTo(Math.log10(2), 0.3010299956639812);
Test.expect(0, Math.log10(1));
Test.expect(-Infinity, Math.log10(0));
Test.expect(NaN, Math.log10(-2));
Test.expect(5, Math.log10(100000));
Test.expect(NaN, Math.log1p(-2));
Test.expect(-Infinity, Math.log1p(-1));
Test.expect(0, Math.log1p(0));
expectToBeCloseTo(Math.log1p(1), 0.693147);
expectToBeCloseTo(Math.log2(3), 1.584962500721156);
Test.expect(1, Math.log2(2));
Test.expect(0, Math.log2(1));
Test.expect(-Infinity, Math.log2(0));
Test.expect(NaN, Math.log2(-2));
Test.expect(10, Math.log2(1024));
Test.expect(-Infinity, Math.max());
Test.expect(1, Math.max(1));
Test.expect(2, Math.max(2, 1));
Test.expect(3, Math.max(1, 2, 3));
Test.expect(0, Math.max(-0, 0));
Test.expect(0, Math.max(0, -0));
Test.expect(NaN, Math.max(NaN));
Test.expect(NaN, Math.max("String", 1));
Test.expect(1, Math.min(1));
Test.expect(1, Math.min(2, 1));
Test.expect(1, Math.min(1, 2, 3));
Test.expect(-0, Math.min(-0, 0));
Test.expect(-0, Math.min(0, -0));
Test.expect(NaN, Math.min(NaN));
Test.expect(NaN, Math.min("String", 1));
Test.expect(1, Math.pow(2, 0));
Test.expect(2, Math.pow(2, 1));
Test.expect(4, Math.pow(2, 2));
Test.expect(8, Math.pow(2, 3));
Test.expect(0.125, Math.pow(2, -3));
Test.expect(9, Math.pow(3, 2));
Test.expect(1, Math.pow(0, 0));
Test.expect(512, Math.pow(2, Math.pow(3, 2)));
Test.expect(64, Math.pow(Math.pow(2, 3), 2));
Test.expect(8, Math.pow("2", "3"));
Test.expect(1, Math.pow("", []));
Test.expect(1, Math.pow([], null));
Test.expect(1, Math.pow(null, null));
Test.expect(1, Math.pow(undefined, null));
Test.expect(NaN, Math.pow(NaN, 2));
Test.expect(NaN, Math.pow(2, NaN));
Test.expect(NaN, Math.pow(undefined, 2));
Test.expect(NaN, Math.pow(2, undefined));
Test.expect(NaN, Math.pow(null, undefined));
Test.expect(NaN, Math.pow(2, "foo"));
Test.expect(NaN, Math.pow("foo", 2));
Test.expect(1, Math.sign(0.0001));
Test.expect(1, Math.sign(1));
Test.expect(1, Math.sign(42));
Test.expect(1, Math.sign(Infinity));
Test.expect(0, Math.sign(0));
Test.expect(0, Math.sign(null));
Test.expect(0, Math.sign(""));
Test.expect(0, Math.sign([]));
Test.expect(-1, Math.sign(-0.0001));
Test.expect(-1, Math.sign(-1));
Test.expect(-1, Math.sign(-42));
Test.expect(-1, Math.sign(-Infinity));
Test.expect(-0, Math.sign(-0));
Test.expect(-0, Math.sign(-null));
Test.expect(-0, Math.sign(-""));
Test.expect(-0, Math.sign(-[]));
Test.expect(NaN, Math.sign());
Test.expect(NaN, Math.sign(undefined));
Test.expect(NaN, Math.sign([1, 2, 3]));
Test.expect(NaN, Math.sign({}));
Test.expect(NaN, Math.sign(NaN));
Test.expect(NaN, Math.sign("foo"));
Test.expect(0, Math.sin(0));
Test.expect(0, Math.sin(null));
Test.expect(0, Math.sin(""));
Test.expect(0, Math.sin([]));
Test.expect(-1, Math.sin((Math.PI * 3) / 2));
Test.expect(1, Math.sin(Math.PI / 2));
Test.expect(NaN, Math.sin());
Test.expect(NaN, Math.sin(undefined));
Test.expect(NaN, Math.sin([1, 2, 3]));
Test.expect(NaN, Math.sin({}));
Test.expect(NaN, Math.sin("foo"));
Test.expect(0, Math.sinh(0));
expectToBeCloseTo(Math.sinh(1), 1.1752011936438014);
Test.expect(3, Math.sqrt(9));
Test.expect(0, Math.tan(0));
Test.expect(0, Math.tan(null));
Test.expect(0, Math.tan(""));
Test.expect(0, Math.tan([]));
Test.expect(1, Math.ceil(Math.tan(Math.PI / 4)));
Test.expect(NaN, Math.tan());
Test.expect(NaN, Math.tan(undefined));
Test.expect(NaN, Math.tan([1, 2, 3]));
Test.expect(NaN, Math.tan({}));
Test.expect(NaN, Math.tan("foo"));
Test.expect(0, Math.tanh(0));
Test.expect(1, Math.tanh(Infinity));
Test.expect(-1, Math.tanh(-Infinity));
expectToBeCloseTo(Math.tanh(1), 0.7615941559557649);
Test.expect(13, Math.trunc(13.37));
Test.expect(42, Math.trunc(42.84));
Test.expect(0, Math.trunc(0.123));
Test.expect(-0, Math.trunc(-0.123));
Test.expect(NaN, Math.trunc(NaN));
Test.expect(NaN, Math.trunc("foo"));
Test.expect(NaN, Math.trunc());
Test.expect(1, Math.round(1.25));
Test.expect(-1, Math.round(-1.25));
Test.expect(2, Math.round(1.5));
Test.expect(-1, Math.round(-1.5));
Test.expect(2, Math.round(1.75));
Test.expect(-2, Math.round(-1.75));
Test.expect(1, Math.round(1));
Test.expect(-1, Math.round(-1));
Test.expect(4294967297, Math.round(4294967296.5));
Test.expect(-4294967296, Math.round(-4294967296.5));
Test.expect(4294967297, Math.round(4294967297));
Test.expect(-4294967297, Math.round(-4294967297));
Test.expect(1, Math.floor(1.25));
Test.expect(-2, Math.floor(-1.25));
Test.expect(1, Math.floor(1.5));
Test.expect(-2, Math.floor(-1.5));
Test.expect(1, Math.floor(1.75));
Test.expect(-2, Math.floor(-1.75));
Test.expect(1, Math.floor(1));
Test.expect(-1, Math.floor(-1));
Test.expect(4294967296, Math.floor(4294967296.5));
Test.expect(-4294967297, Math.floor(-4294967296.5));
Test.expect(4294967297, Math.floor(4294967297));
Test.expect(-4294967297, Math.floor(-4294967297));