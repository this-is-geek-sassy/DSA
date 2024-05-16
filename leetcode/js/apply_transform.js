// https://leetcode.com/problems/apply-transform-over-each-element-in-array/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {number[]} arr
 * @param {Function} fn
 * @return {number[]}
 */
var map = function(arr, fn) {
    
    for (let i = 0; i < arr.length; i++) {
        const element = arr[i];
        arr[i] = fn(element, i);
    }
    return arr;
};