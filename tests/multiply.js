expect(NaN, NaN * NaN);
expect(NaN, NaN * Infinity);
expect(NaN, NaN * (-Infinity));
expect(NaN, NaN * (-0));
expect(NaN, NaN * 0);
expect(NaN, NaN * 3);
expect(NaN, NaN * 13);
expect(NaN, NaN * 'str');
expect(NaN, NaN * (-14));
expect(NaN, Infinity * NaN);
expect(Infinity, Infinity * Infinity);
expect(-Infinity, Infinity * (-Infinity));
expect(NaN, Infinity * (-0));
expect(NaN, Infinity * 0);
expect(Infinity, Infinity * 3);
expect(Infinity, Infinity * 13);
expect(NaN, Infinity * 'str');
expect(-Infinity, Infinity * (-14));
expect(NaN, (-Infinity) * NaN);
expect(-Infinity, (-Infinity) * Infinity);
expect(Infinity, (-Infinity) * (-Infinity));
expect(NaN, (-Infinity) * (-0));
expect(NaN, (-Infinity) * 0);
expect(-Infinity, (-Infinity) * 3);
expect(-Infinity, (-Infinity) * 13);
expect(NaN, (-Infinity) * 'str');
expect(Infinity, (-Infinity) * (-14));
expect(NaN, (-0) * NaN);
expect(NaN, (-0) * Infinity);
expect(NaN, (-0) * (-Infinity));
expect(0, (-0) * (-0));
expect(-0, (-0) * 0);
expect(-0, (-0) * 3);
expect(-0, (-0) * 13);
expect(NaN, (-0) * 'str');
expect(0, (-0) * (-14));
expect(NaN, 0 * NaN);
expect(NaN, 0 * Infinity);
expect(NaN, 0 * (-Infinity));
expect(-0, 0 * (-0));
expect(0, 0 * 0);
expect(0, 0 * 3);
expect(0, 0 * 13);
expect(NaN, 0 * 'str');
expect(-0, 0 * (-14));
expect(NaN, 3 * NaN);
expect(Infinity, 3 * Infinity);
expect(-Infinity, 3 * (-Infinity));
expect(-0, 3 * (-0));
expect(0, 3 * 0);
expect(9, 3 * 3);
expect(39, 3 * 13);
expect(NaN, 3 * 'str');
expect(-42, 3 * (-14));
expect(NaN, 13 * NaN);
expect(Infinity, 13 * Infinity);
expect(-Infinity, 13 * (-Infinity));
expect(-0, 13 * (-0));
expect(0, 13 * 0);
expect(39, 13 * 3);
expect(169, 13 * 13);
expect(NaN, 13 * 'str');
expect(-182, 13 * (-14));
expect(NaN, 'str' * NaN);
expect(NaN, 'str' * Infinity);
expect(NaN, 'str' * (-Infinity));
expect(NaN, 'str' * (-0));
expect(NaN, 'str' * 0);
expect(NaN, 'str' * 3);
expect(NaN, 'str' * 13);
expect(NaN, 'str' * 'str');
expect(NaN, 'str' * (-14));
expect(NaN, (-14) * NaN);
expect(-Infinity, (-14) * Infinity);
expect(Infinity, (-14) * (-Infinity));
expect(0, (-14) * (-0));
expect(-0, (-14) * 0);
expect(-42, (-14) * 3);
expect(-182, (-14) * 13);
expect(NaN, (-14) * 'str');
expect(196, (-14) * (-14));