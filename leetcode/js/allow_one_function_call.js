// source: https://leetcode.com/problems/allow-one-function-call/?envType=study-plan-v2&envId=30-days-of-javascript
/**
 * @param {Function} fn
 * @return {Function}
 */
var once = function(fn) {
    
    let no_of_times_function_is_called = 0;
    return function(...args){

        if (no_of_times_function_is_called != 0)
            return undefined;
        else {
            no_of_times_function_is_called++;
            return fn(...args);
        }
    }
};


let fn = (a,b,c) => (a + b + c)
let onceFn = once(fn)

console.log(onceFn(1,2,3)); // 6
console.log(onceFn(2,3,6)); // returns undefined without calling fn

