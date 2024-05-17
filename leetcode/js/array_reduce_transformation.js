// source: https://leetcode.com/problems/function-composition/description/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {number[]} nums
 * @param {Function} fn
 * @param {number} init
 * @return {number}
 */
var reduce = function(nums, fn, init) {

    if (nums.length == 0)
        return init;
    
    var val = fn(init, nums[0]);
    for (let i = 1; i < nums.length; i++) {
        const element = nums[i];
        val = fn(val, nums[i]);
    }
    return val;
};