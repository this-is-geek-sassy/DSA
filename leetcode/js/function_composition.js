// source: https://leetcode.com/problems/function-composition/description/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {Function[]} functions
 * @return {Function}
 */
var compose = function(functions) {
    
    return function(x) {
        var intermediateValue = x;
        for (let i = functions.length - 1; i >= 0; i--) {
            const element = functions[i];
            intermediateValue = element(intermediateValue);
        }
        return intermediateValue;
    }
};

/**
 * const fn = compose([x => x + 1, x => 2 * x])
 * fn(4) // 9
 */