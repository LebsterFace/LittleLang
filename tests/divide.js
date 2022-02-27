expect(NaN, NaN / NaN);
expect(NaN, NaN / Infinity);
expect(NaN, NaN / (-Infinity));
expect(NaN, NaN / (-0));
expect(NaN, NaN / 0);
expect(NaN, NaN / 3);
expect(NaN, NaN / 13);
expect(NaN, NaN / 'str');
expect(NaN, NaN / (-14));
expect(NaN, Infinity / NaN);
expect(NaN, Infinity / Infinity);
expect(NaN, Infinity / (-Infinity));
expect(-Infinity, Infinity / (-0));
expect(Infinity, Infinity / 0);
expect(Infinity, Infinity / 3);
expect(Infinity, Infinity / 13);
expect(NaN, Infinity / 'str');
expect(-Infinity, Infinity / (-14));
expect(NaN, (-Infinity) / NaN);
expect(NaN, (-Infinity) / Infinity);
expect(NaN, (-Infinity) / (-Infinity));
expect(Infinity, (-Infinity) / (-0));
expect(-Infinity, (-Infinity) / 0);
expect(-Infinity, (-Infinity) / 3);
expect(-Infinity, (-Infinity) / 13);
expect(NaN, (-Infinity) / 'str');
expect(Infinity, (-Infinity) / (-14));
expect(NaN, (-0) / NaN);
expect(-0, (-0) / Infinity);
expect(0, (-0) / (-Infinity));
expect(NaN, (-0) / (-0));
expect(NaN, (-0) / 0);
expect(-0, (-0) / 3);
expect(-0, (-0) / 13);
expect(NaN, (-0) / 'str');
expect(0, (-0) / (-14));
expect(NaN, 0 / NaN);
expect(0, 0 / Infinity);
expect(-0, 0 / (-Infinity));
expect(NaN, 0 / (-0));
expect(NaN, 0 / 0);
expect(0, 0 / 3);
expect(0, 0 / 13);
expect(NaN, 0 / 'str');
expect(-0, 0 / (-14));
expect(NaN, 3 / NaN);
expect(0, 3 / Infinity);
expect(-0, 3 / (-Infinity));
expect(-Infinity, 3 / (-0));
expect(Infinity, 3 / 0);
expect(1, 3 / 3);
expect(0.23076923076923078, 3 / 13);
expect(NaN, 3 / 'str');
expect(-0.21428571428571427, 3 / (-14));
expect(NaN, 13 / NaN);
expect(0, 13 / Infinity);
expect(-0, 13 / (-Infinity));
expect(-Infinity, 13 / (-0));
expect(Infinity, 13 / 0);
expect(4.333333333333333, 13 / 3);
expect(1, 13 / 13);
expect(NaN, 13 / 'str');
expect(-0.9285714285714286, 13 / (-14));
expect(NaN, 'str' / NaN);
expect(NaN, 'str' / Infinity);
expect(NaN, 'str' / (-Infinity));
expect(NaN, 'str' / (-0));
expect(NaN, 'str' / 0);
expect(NaN, 'str' / 3);
expect(NaN, 'str' / 13);
expect(NaN, 'str' / 'str');
expect(NaN, 'str' / (-14));
expect(NaN, (-14) / NaN);
expect(-0, (-14) / Infinity);
expect(0, (-14) / (-Infinity));
expect(Infinity, (-14) / (-0));
expect(-Infinity, (-14) / 0);
expect(-4.666666666666667, (-14) / 3);
expect(-1.0769230769230769, (-14) / 13);
expect(NaN, (-14) / 'str');
expect(1, (-14) / (-14));