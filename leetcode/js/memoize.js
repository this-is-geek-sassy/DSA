// source: https://leetcode.com/problems/memoize/description/?envType=study-plan-v2&envId=30-days-of-javascript

/**
 * @param {Function} fn
 * @return {Function}
 */
function memoize(fn) {
    
    let memory = new Map();
    return function(...args) {

        // console.log(args);
        // console.log(fn(args[0], args[1]));

        if (memory.has(args.join("::"))) {
            // console.log(memory.get(args.join("::")));
            return memory.get(args.join("::"));
        } else {
            let value = fn(args[0], args[1]);
            memory.set(args.join("::"), value);
            return value;
        }
    }
}



let callCount = 0;
const memoizedFn = memoize(function (a, b) {
    callCount += 1;
    return a + b;
});
console.log(memoizedFn(2, 3)); // 5
console.log(memoizedFn(2, 3)); // 5
console.log(callCount) // 1 

// let memory = new Map();
// memory.set([2, 3].join("::"), 5);
// console.log(memory);
// console.log(memory.has([2, 3].join("::")));
// console.log(memory.has([3, 2].join("::")));

// console.log(memory.keys());
// console.log(memory.get([2, 3].join("::")));

// let args = [2, 3];

// if (memory.has(args.join("::")))
//     console.log(memory.get(args.join("::")));