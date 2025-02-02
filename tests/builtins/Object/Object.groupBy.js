const array = [1, 2, 3, 4, 5];
const result = Object.groupBy(array, (num, index) => num % 2 === 0 ? 'even': 'odd');
Test.expectEqual([1, 3, 5], result.odd);
Test.expectEqual([2, 4], result.even);