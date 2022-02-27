expect(NaN, NaN ** NaN);
expect(NaN, NaN ** Infinity);
expect(NaN, NaN ** (-Infinity));
expect(1, NaN ** (-0));
expect(1, NaN ** 0);
expect(NaN, NaN ** 3);
expect(NaN, NaN ** 13);
expect(NaN, NaN ** 'str');
expect(NaN, NaN ** (-14));
expect(NaN, Infinity ** NaN);
expect(Infinity, Infinity ** Infinity);
expect(0, Infinity ** (-Infinity));
expect(1, Infinity ** (-0));
expect(1, Infinity ** 0);
expect(Infinity, Infinity ** 3);
expect(Infinity, Infinity ** 13);
expect(NaN, Infinity ** 'str');
expect(0, Infinity ** (-14));
expect(NaN, (-Infinity) ** NaN);
expect(Infinity, (-Infinity) ** Infinity);
expect(0, (-Infinity) ** (-Infinity));
expect(1, (-Infinity) ** (-0));
expect(1, (-Infinity) ** 0);
expect(-Infinity, (-Infinity) ** 3);
expect(-Infinity, (-Infinity) ** 13);
expect(NaN, (-Infinity) ** 'str');
expect(0, (-Infinity) ** (-14));
expect(NaN, (-0) ** NaN);
expect(0, (-0) ** Infinity);
expect(Infinity, (-0) ** (-Infinity));
expect(1, (-0) ** (-0));
expect(1, (-0) ** 0);
expect(-0, (-0) ** 3);
expect(-0, (-0) ** 13);
expect(NaN, (-0) ** 'str');
expect(Infinity, (-0) ** (-14));
expect(NaN, 0 ** NaN);
expect(0, 0 ** Infinity);
expect(Infinity, 0 ** (-Infinity));
expect(1, 0 ** (-0));
expect(1, 0 ** 0);
expect(0, 0 ** 3);
expect(0, 0 ** 13);
expect(NaN, 0 ** 'str');
expect(Infinity, 0 ** (-14));
expect(NaN, 3 ** NaN);
expect(Infinity, 3 ** Infinity);
expect(0, 3 ** (-Infinity));
expect(1, 3 ** (-0));
expect(1, 3 ** 0);
expect(27, 3 ** 3);
expect(1594323, 3 ** 13);
expect(NaN, 3 ** 'str');
expect(0.00000020907515812876897, 3 ** (-14));
expect(NaN, 13 ** NaN);
expect(Infinity, 13 ** Infinity);
expect(0, 13 ** (-Infinity));
expect(1, 13 ** (-0));
expect(1, 13 ** 0);
expect(2197, 13 ** 3);
expect(302875106592253, 13 ** 13);
expect(NaN, 13 ** 'str');
expect(0.0000000000000002539762273253938, 13 ** (-14));
expect(NaN, 'str' ** NaN);
expect(NaN, 'str' ** Infinity);
expect(NaN, 'str' ** (-Infinity));
expect(1, 'str' ** (-0));
expect(1, 'str' ** 0);
expect(NaN, 'str' ** 3);
expect(NaN, 'str' ** 13);
expect(NaN, 'str' ** 'str');
expect(NaN, 'str' ** (-14));
expect(NaN, (-14) ** NaN);
expect(Infinity, (-14) ** Infinity);
expect(0, (-14) ** (-Infinity));
expect(1, (-14) ** (-0));
expect(1, (-14) ** 0);
expect(-2744, (-14) ** 3);
expect(-793714773254144, (-14) ** 13);
expect(NaN, (-14) ** 'str');
expect(0.00000000000000008999274529781281, (-14) ** (-14));