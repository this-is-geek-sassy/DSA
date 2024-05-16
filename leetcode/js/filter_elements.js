// https://leetcode.com/problems/filter-elements-from-array/description/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {number[]} arr
 * @param {Function} fn
 * @return {number[]}
 */
var filter = function(arr, fn) {
    let returnArr = [];
    for (let i = 0; i < arr.length; i++) {
        const element = arr[i];
        if (fn(element, i)) {
            returnArr.push(element);
        }
    }
    return returnArr;
};