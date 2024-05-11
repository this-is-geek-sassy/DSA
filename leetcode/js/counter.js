// https://leetcode.com/problems/counter/description/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {number} n
 * @return {Function} counter
 */
var createCounter = function(n) {
    
    let v = n-1;
    return function() {
        v+=1;
        return v;
    };
};


const counter = createCounter(10)
console.log(counter()); /* counter() // 10
 * counter() // 11
 * counter() // 12    */
console.log(counter());
console.log(counter());
