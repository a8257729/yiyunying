(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(factory.toString()).default;
	else if(typeof exports === 'object')
		exports["Quagga"] = factory(factory.toString()).default;
	else
		root["Quagga"] = factory(factory.toString()).default;
})(this, function(__factorySource__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 166);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

/**
 * Checks if `value` is the
 * [language type](http://www.ecma-international.org/ecma-262/7.0/#sec-ecmascript-language-types)
 * of `Object`. (e.g. arrays, functions, objects, regexes, `new Number(0)`, and `new String('')`)
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an object, else `false`.
 * @example
 *
 * _.isObject({});
 * // => true
 *
 * _.isObject([1, 2, 3]);
 * // => true
 *
 * _.isObject(_.noop);
 * // => true
 *
 * _.isObject(null);
 * // => false
 */
function isObject(value) {
  var type = typeof value;
  return value != null && (type == 'object' || type == 'function');
}

module.exports = isObject;


/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__common_array_helper__ = __webpack_require__(3);


function BarcodeReader(config, supplements) {
    this._row = [];
    this.config = config || {};
    this.supplements = supplements;
    return this;
}

BarcodeReader.prototype._nextUnset = function (line, start) {
    var i;

    if (start === undefined) {
        start = 0;
    }
    for (i = start; i < line.length; i++) {
        if (!line[i]) {
            return i;
        }
    }
    return line.length;
};

BarcodeReader.prototype._matchPattern = function (counter, code, maxSingleError) {
    var i,
        error = 0,
        singleError = 0,
        sum = 0,
        modulo = 0,
        barWidth,
        count,
        scaled;

    maxSingleError = maxSingleError || this.SINGLE_CODE_ERROR || 1;

    for (i = 0; i < counter.length; i++) {
        sum += counter[i];
        modulo += code[i];
    }
    if (sum < modulo) {
        return Number.MAX_VALUE;
    }
    barWidth = sum / modulo;
    maxSingleError *= barWidth;

    for (i = 0; i < counter.length; i++) {
        count = counter[i];
        scaled = code[i] * barWidth;
        singleError = Math.abs(count - scaled) / scaled;
        if (singleError > maxSingleError) {
            return Number.MAX_VALUE;
        }
        error += singleError;
    }
    return error / modulo;
};

BarcodeReader.prototype._nextSet = function (line, offset) {
    var i;

    offset = offset || 0;
    for (i = offset; i < line.length; i++) {
        if (line[i]) {
            return i;
        }
    }
    return line.length;
};

BarcodeReader.prototype._correctBars = function (counter, correction, indices) {
    var length = indices.length,
        tmp = 0;
    while (length--) {
        tmp = counter[indices[length]] * (1 - (1 - correction) / 2);
        if (tmp > 1) {
            counter[indices[length]] = tmp;
        }
    }
};

BarcodeReader.prototype._matchTrace = function (cmpCounter, epsilon) {
    var counter = [],
        i,
        self = this,
        offset = self._nextSet(self._row),
        isWhite = !self._row[offset],
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0
    },
        error;

    if (cmpCounter) {
        for (i = 0; i < cmpCounter.length; i++) {
            counter.push(0);
        }
        for (i = offset; i < self._row.length; i++) {
            if (self._row[i] ^ isWhite) {
                counter[counterPos]++;
            } else {
                if (counterPos === counter.length - 1) {
                    error = self._matchPattern(counter, cmpCounter);

                    if (error < epsilon) {
                        bestMatch.start = i - offset;
                        bestMatch.end = i;
                        bestMatch.counter = counter;
                        return bestMatch;
                    } else {
                        return null;
                    }
                } else {
                    counterPos++;
                }
                counter[counterPos] = 1;
                isWhite = !isWhite;
            }
        }
    } else {
        counter.push(0);
        for (i = offset; i < self._row.length; i++) {
            if (self._row[i] ^ isWhite) {
                counter[counterPos]++;
            } else {
                counterPos++;
                counter.push(0);
                counter[counterPos] = 1;
                isWhite = !isWhite;
            }
        }
    }

    // if cmpCounter was not given
    bestMatch.start = offset;
    bestMatch.end = self._row.length - 1;
    bestMatch.counter = counter;
    return bestMatch;
};

BarcodeReader.prototype.decodePattern = function (pattern) {
    var self = this,
        result;

    self._row = pattern;
    result = self._decode();
    if (result === null) {
        self._row.reverse();
        result = self._decode();
        if (result) {
            result.direction = BarcodeReader.DIRECTION.REVERSE;
            result.start = self._row.length - result.start;
            result.end = self._row.length - result.end;
        }
    } else {
        result.direction = BarcodeReader.DIRECTION.FORWARD;
    }
    if (result) {
        result.format = self.FORMAT;
    }
    return result;
};

BarcodeReader.prototype._matchRange = function (start, end, value) {
    var i;

    start = start < 0 ? 0 : start;
    for (i = start; i < end; i++) {
        if (this._row[i] !== value) {
            return false;
        }
    }
    return true;
};

BarcodeReader.prototype._fillCounters = function (offset, end, isWhite) {
    var self = this,
        counterPos = 0,
        i,
        counters = [];

    isWhite = typeof isWhite !== 'undefined' ? isWhite : true;
    offset = typeof offset !== 'undefined' ? offset : self._nextUnset(self._row);
    end = end || self._row.length;

    counters[counterPos] = 0;
    for (i = offset; i < end; i++) {
        if (self._row[i] ^ isWhite) {
            counters[counterPos]++;
        } else {
            counterPos++;
            counters[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return counters;
};

BarcodeReader.prototype._toCounters = function (start, counter) {
    var self = this,
        numCounters = counter.length,
        end = self._row.length,
        isWhite = !self._row[start],
        i,
        counterPos = 0;

    __WEBPACK_IMPORTED_MODULE_0__common_array_helper__["a" /* default */].init(counter, 0);

    for (i = start; i < end; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            counterPos++;
            if (counterPos === numCounters) {
                break;
            } else {
                counter[counterPos] = 1;
                isWhite = !isWhite;
            }
        }
    }

    return counter;
};

Object.defineProperty(BarcodeReader.prototype, "FORMAT", {
    value: 'unknown',
    writeable: false
});

BarcodeReader.DIRECTION = {
    FORWARD: 1,
    REVERSE: -1
};

BarcodeReader.Exception = {
    StartNotFoundException: "Start-Info was not found!",
    CodeNotFoundException: "Code could not be found!",
    PatternNotFoundException: "Pattern could not be found!"
};

BarcodeReader.CONFIG_KEYS = {};

/* harmony default export */ __webpack_exports__["a"] = BarcodeReader;

/***/ }),
/* 2 */
/***/ (function(module, exports) {

/**
 * Checks if `value` is classified as an `Array` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an array, else `false`.
 * @example
 *
 * _.isArray([1, 2, 3]);
 * // => true
 *
 * _.isArray(document.body.children);
 * // => false
 *
 * _.isArray('abc');
 * // => false
 *
 * _.isArray(_.noop);
 * // => false
 */
var isArray = Array.isArray;

module.exports = isArray;


/***/ }),
/* 3 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony default export */ __webpack_exports__["a"] = {
    init: function init(arr, val) {
        var l = arr.length;
        while (l--) {
            arr[l] = val;
        }
    },

    /**
     * Shuffles the content of an array
     * @return {Array} the array itself shuffled
     */
    shuffle: function shuffle(arr) {
        var i = arr.length - 1,
            j,
            x;
        for (i; i >= 0; i--) {
            j = Math.floor(Math.random() * i);
            x = arr[i];
            arr[i] = arr[j];
            arr[j] = x;
        }
        return arr;
    },

    toPointList: function toPointList(arr) {
        var i,
            j,
            row = [],
            rows = [];
        for (i = 0; i < arr.length; i++) {
            row = [];
            for (j = 0; j < arr[i].length; j++) {
                row[j] = arr[i][j];
            }
            rows[i] = "[" + row.join(",") + "]";
        }
        return "[" + rows.join(",\r\n") + "]";
    },

    /**
     * returns the elements which's score is bigger than the threshold
     * @return {Array} the reduced array
     */
    threshold: function threshold(arr, _threshold, scoreFunc) {
        var i,
            queue = [];
        for (i = 0; i < arr.length; i++) {
            if (scoreFunc.apply(arr, [arr[i]]) >= _threshold) {
                queue.push(arr[i]);
            }
        }
        return queue;
    },

    maxIndex: function maxIndex(arr) {
        var i,
            max = 0;
        for (i = 0; i < arr.length; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    },

    max: function max(arr) {
        var i,
            max = 0;
        for (i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    },

    sum: function sum(arr) {
        var length = arr.length,
            sum = 0;

        while (length--) {
            sum += arr[length];
        }
        return sum;
    }
};

/***/ }),
/* 4 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash_merge__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__barcode_reader__ = __webpack_require__(1);


var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };




function EANReader(opts, supplements) {
    opts = __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default()(getDefaulConfig(), opts);
    __WEBPACK_IMPORTED_MODULE_1__barcode_reader__["a" /* default */].call(this, opts, supplements);
}

function getDefaulConfig() {
    var config = {};

    Object.keys(EANReader.CONFIG_KEYS).forEach(function (key) {
        config[key] = EANReader.CONFIG_KEYS[key].default;
    });
    return config;
}

var properties = {
    CODE_L_START: { value: 0 },
    CODE_G_START: { value: 10 },
    START_PATTERN: { value: [1, 1, 1] },
    STOP_PATTERN: { value: [1, 1, 1] },
    MIDDLE_PATTERN: { value: [1, 1, 1, 1, 1] },
    EXTENSION_START_PATTERN: { value: [1, 1, 2] },
    CODE_PATTERN: { value: [[3, 2, 1, 1], [2, 2, 2, 1], [2, 1, 2, 2], [1, 4, 1, 1], [1, 1, 3, 2], [1, 2, 3, 1], [1, 1, 1, 4], [1, 3, 1, 2], [1, 2, 1, 3], [3, 1, 1, 2], [1, 1, 2, 3], [1, 2, 2, 2], [2, 2, 1, 2], [1, 1, 4, 1], [2, 3, 1, 1], [1, 3, 2, 1], [4, 1, 1, 1], [2, 1, 3, 1], [3, 1, 2, 1], [2, 1, 1, 3]] },
    CODE_FREQUENCY: { value: [0, 11, 13, 14, 19, 25, 28, 21, 22, 26] },
    SINGLE_CODE_ERROR: { value: 0.70 },
    AVG_CODE_ERROR: { value: 0.48 },
    FORMAT: { value: "ean_13", writeable: false }
};

EANReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_1__barcode_reader__["a" /* default */].prototype, properties);
EANReader.prototype.constructor = EANReader;

EANReader.prototype._decodeCode = function (start, coderange) {
    var counter = [0, 0, 0, 0],
        i,
        self = this,
        offset = start,
        isWhite = !self._row[offset],
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: start,
        end: start
    },
        code,
        error;

    if (!coderange) {
        coderange = self.CODE_PATTERN.length;
    }

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                for (code = 0; code < coderange; code++) {
                    error = self._matchPattern(counter, self.CODE_PATTERN[code]);
                    if (error < bestMatch.error) {
                        bestMatch.code = code;
                        bestMatch.error = error;
                    }
                }
                bestMatch.end = i;
                if (bestMatch.error > self.AVG_CODE_ERROR) {
                    return null;
                }
                return bestMatch;
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

EANReader.prototype._findPattern = function (pattern, offset, isWhite, tryHarder, epsilon) {
    var counter = [],
        self = this,
        i,
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0
    },
        error,
        j,
        sum;

    if (!offset) {
        offset = self._nextSet(self._row);
    }

    if (isWhite === undefined) {
        isWhite = false;
    }

    if (tryHarder === undefined) {
        tryHarder = true;
    }

    if (epsilon === undefined) {
        epsilon = self.AVG_CODE_ERROR;
    }

    for (i = 0; i < pattern.length; i++) {
        counter[i] = 0;
    }

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                sum = 0;
                for (j = 0; j < counter.length; j++) {
                    sum += counter[j];
                }
                error = self._matchPattern(counter, pattern);

                if (error < epsilon) {
                    bestMatch.error = error;
                    bestMatch.start = i - sum;
                    bestMatch.end = i;
                    return bestMatch;
                }
                if (tryHarder) {
                    for (j = 0; j < counter.length - 2; j++) {
                        counter[j] = counter[j + 2];
                    }
                    counter[counter.length - 2] = 0;
                    counter[counter.length - 1] = 0;
                    counterPos--;
                } else {
                    return null;
                }
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

EANReader.prototype._findStart = function () {
    var self = this,
        leadingWhitespaceStart,
        offset = self._nextSet(self._row),
        startInfo;

    while (!startInfo) {
        startInfo = self._findPattern(self.START_PATTERN, offset);
        if (!startInfo) {
            return null;
        }
        leadingWhitespaceStart = startInfo.start - (startInfo.end - startInfo.start);
        if (leadingWhitespaceStart >= 0) {
            if (self._matchRange(leadingWhitespaceStart, startInfo.start, 0)) {
                return startInfo;
            }
        }
        offset = startInfo.end;
        startInfo = null;
    }
};

EANReader.prototype._verifyTrailingWhitespace = function (endInfo) {
    var self = this,
        trailingWhitespaceEnd;

    trailingWhitespaceEnd = endInfo.end + (endInfo.end - endInfo.start);
    if (trailingWhitespaceEnd < self._row.length) {
        if (self._matchRange(endInfo.end, trailingWhitespaceEnd, 0)) {
            return endInfo;
        }
    }
    return null;
};

EANReader.prototype._findEnd = function (offset, isWhite) {
    var self = this,
        endInfo = self._findPattern(self.STOP_PATTERN, offset, isWhite, false);

    return endInfo !== null ? self._verifyTrailingWhitespace(endInfo) : null;
};

EANReader.prototype._calculateFirstDigit = function (codeFrequency) {
    var i,
        self = this;

    for (i = 0; i < self.CODE_FREQUENCY.length; i++) {
        if (codeFrequency === self.CODE_FREQUENCY[i]) {
            return i;
        }
    }
    return null;
};

EANReader.prototype._decodePayload = function (code, result, decodedCodes) {
    var i,
        self = this,
        codeFrequency = 0x0,
        firstDigit;

    for (i = 0; i < 6; i++) {
        code = self._decodeCode(code.end);
        if (!code) {
            return null;
        }
        if (code.code >= self.CODE_G_START) {
            code.code = code.code - self.CODE_G_START;
            codeFrequency |= 1 << 5 - i;
        } else {
            codeFrequency |= 0 << 5 - i;
        }
        result.push(code.code);
        decodedCodes.push(code);
    }

    firstDigit = self._calculateFirstDigit(codeFrequency);
    if (firstDigit === null) {
        return null;
    }
    result.unshift(firstDigit);

    code = self._findPattern(self.MIDDLE_PATTERN, code.end, true, false);
    if (code === null) {
        return null;
    }
    decodedCodes.push(code);

    for (i = 0; i < 6; i++) {
        code = self._decodeCode(code.end, self.CODE_G_START);
        if (!code) {
            return null;
        }
        decodedCodes.push(code);
        result.push(code.code);
    }

    return code;
};

EANReader.prototype._decode = function () {
    var startInfo,
        self = this,
        code,
        result = [],
        decodedCodes = [],
        resultInfo = {};

    startInfo = self._findStart();
    if (!startInfo) {
        return null;
    }
    code = {
        code: startInfo.code,
        start: startInfo.start,
        end: startInfo.end
    };
    decodedCodes.push(code);
    code = self._decodePayload(code, result, decodedCodes);
    if (!code) {
        return null;
    }
    code = self._findEnd(code.end, false);
    if (!code) {
        return null;
    }

    decodedCodes.push(code);

    // Checksum
    if (!self._checksum(result)) {
        return null;
    }

    if (this.supplements.length > 0) {
        var ext = this._decodeExtensions(code.end);
        if (!ext) {
            return null;
        }
        var lastCode = ext.decodedCodes[ext.decodedCodes.length - 1],
            endInfo = {
            start: lastCode.start + ((lastCode.end - lastCode.start) / 2 | 0),
            end: lastCode.end
        };
        if (!self._verifyTrailingWhitespace(endInfo)) {
            return null;
        }
        resultInfo = {
            supplement: ext,
            code: result.join("") + ext.code
        };
    }

    return _extends({
        code: result.join(""),
        start: startInfo.start,
        end: code.end,
        codeset: "",
        startInfo: startInfo,
        decodedCodes: decodedCodes
    }, resultInfo);
};

EANReader.prototype._decodeExtensions = function (offset) {
    var i,
        start = this._nextSet(this._row, offset),
        startInfo = this._findPattern(this.EXTENSION_START_PATTERN, start, false, false),
        result;

    if (startInfo === null) {
        return null;
    }

    for (i = 0; i < this.supplements.length; i++) {
        result = this.supplements[i].decode(this._row, startInfo.end);
        if (result !== null) {
            return {
                code: result.code,
                start: start,
                startInfo: startInfo,
                end: result.end,
                codeset: "",
                decodedCodes: result.decodedCodes
            };
        }
    }
    return null;
};

EANReader.prototype._checksum = function (result) {
    var sum = 0,
        i;

    for (i = result.length - 2; i >= 0; i -= 2) {
        sum += result[i];
    }
    sum *= 3;
    for (i = result.length - 1; i >= 0; i -= 2) {
        sum += result[i];
    }
    return sum % 10 === 0;
};

EANReader.CONFIG_KEYS = {
    supplements: {
        'type': 'arrayOf(string)',
        'default': [],
        'description': 'Allowed extensions to be decoded (2 and/or 5)'
    }
};

/* harmony default export */ __webpack_exports__["a"] = EANReader;

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

var freeGlobal = __webpack_require__(38);

/** Detect free variable `self`. */
var freeSelf = typeof self == 'object' && self && self.Object === Object && self;

/** Used as a reference to the global object. */
var root = freeGlobal || freeSelf || Function('return this')();

module.exports = root;


/***/ }),
/* 6 */
/***/ (function(module, exports) {

/**
 * Checks if `value` is object-like. A value is object-like if it's not `null`
 * and has a `typeof` result of "object".
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is object-like, else `false`.
 * @example
 *
 * _.isObjectLike({});
 * // => true
 *
 * _.isObjectLike([1, 2, 3]);
 * // => true
 *
 * _.isObjectLike(_.noop);
 * // => false
 *
 * _.isObjectLike(null);
 * // => false
 */
function isObjectLike(value) {
  return value != null && typeof value == 'object';
}

module.exports = isObjectLike;


/***/ }),
/* 7 */
/***/ (function(module, exports) {

module.exports = clone

/**
 * Creates a new vec2 initialized with values from an existing vector
 *
 * @param {vec2} a vector to clone
 * @returns {vec2} a new 2D vector
 */
function clone(a) {
    var out = new Float32Array(2)
    out[0] = a[0]
    out[1] = a[1]
    return out
}

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

var Symbol = __webpack_require__(11),
    getRawTag = __webpack_require__(119),
    objectToString = __webpack_require__(146);

/** `Object#toString` result references. */
var nullTag = '[object Null]',
    undefinedTag = '[object Undefined]';

/** Built-in value references. */
var symToStringTag = Symbol ? Symbol.toStringTag : undefined;

/**
 * The base implementation of `getTag` without fallbacks for buggy environments.
 *
 * @private
 * @param {*} value The value to query.
 * @returns {string} Returns the `toStringTag`.
 */
function baseGetTag(value) {
  if (value == null) {
    return value === undefined ? undefinedTag : nullTag;
  }
  return (symToStringTag && symToStringTag in Object(value))
    ? getRawTag(value)
    : objectToString(value);
}

module.exports = baseGetTag;


/***/ }),
/* 9 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony default export */ __webpack_exports__["a"] = {
    drawRect: function drawRect(pos, size, ctx, style) {
        ctx.strokeStyle = style.color;
        ctx.fillStyle = style.color;
        ctx.lineWidth = 1;
        ctx.beginPath();
        ctx.strokeRect(pos.x, pos.y, size.x, size.y);
    },
    drawPath: function drawPath(path, def, ctx, style) {
        ctx.strokeStyle = style.color;
        ctx.fillStyle = style.color;
        ctx.lineWidth = style.lineWidth;
        ctx.beginPath();
        ctx.moveTo(path[0][def.x], path[0][def.y]);
        for (var j = 1; j < path.length; j++) {
            ctx.lineTo(path[j][def.x], path[j][def.y]);
        }
        ctx.closePath();
        ctx.stroke();
    },
    drawImage: function drawImage(imageData, size, ctx) {
        var canvasData = ctx.getImageData(0, 0, size.x, size.y),
            data = canvasData.data,
            imageDataPos = imageData.length,
            canvasDataPos = data.length,
            value;

        if (canvasDataPos / imageDataPos !== 4) {
            return false;
        }
        while (imageDataPos--) {
            value = imageData[imageDataPos];
            data[--canvasDataPos] = 255;
            data[--canvasDataPos] = value;
            data[--canvasDataPos] = value;
            data[--canvasDataPos] = value;
        }
        ctx.putImageData(canvasData, 0, 0);
        return true;
    }
};

/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

var listCacheClear = __webpack_require__(133),
    listCacheDelete = __webpack_require__(134),
    listCacheGet = __webpack_require__(135),
    listCacheHas = __webpack_require__(136),
    listCacheSet = __webpack_require__(137);

/**
 * Creates an list cache object.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function ListCache(entries) {
  var index = -1,
      length = entries == null ? 0 : entries.length;

  this.clear();
  while (++index < length) {
    var entry = entries[index];
    this.set(entry[0], entry[1]);
  }
}

// Add methods to `ListCache`.
ListCache.prototype.clear = listCacheClear;
ListCache.prototype['delete'] = listCacheDelete;
ListCache.prototype.get = listCacheGet;
ListCache.prototype.has = listCacheHas;
ListCache.prototype.set = listCacheSet;

module.exports = ListCache;


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

var root = __webpack_require__(5);

/** Built-in value references. */
var Symbol = root.Symbol;

module.exports = Symbol;


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

var eq = __webpack_require__(17);

/**
 * Gets the index at which the `key` is found in `array` of key-value pairs.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {*} key The key to search for.
 * @returns {number} Returns the index of the matched value, else `-1`.
 */
function assocIndexOf(array, key) {
  var length = array.length;
  while (length--) {
    if (eq(array[length][0], key)) {
      return length;
    }
  }
  return -1;
}

module.exports = assocIndexOf;


/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

var isArray = __webpack_require__(2),
    isKey = __webpack_require__(130),
    stringToPath = __webpack_require__(154),
    toString = __webpack_require__(165);

/**
 * Casts `value` to a path array if it's not one.
 *
 * @private
 * @param {*} value The value to inspect.
 * @param {Object} [object] The object to query keys on.
 * @returns {Array} Returns the cast property path array.
 */
function castPath(value, object) {
  if (isArray(value)) {
    return value;
  }
  return isKey(value, object) ? [value] : stringToPath(toString(value));
}

module.exports = castPath;


/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

var isKeyable = __webpack_require__(131);

/**
 * Gets the data for `map`.
 *
 * @private
 * @param {Object} map The map to query.
 * @param {string} key The reference key.
 * @returns {*} Returns the map data.
 */
function getMapData(map, key) {
  var data = map.__data__;
  return isKeyable(key)
    ? data[typeof key == 'string' ? 'string' : 'hash']
    : data.map;
}

module.exports = getMapData;


/***/ }),
/* 15 */
/***/ (function(module, exports) {

/** Used as references for various `Number` constants. */
var MAX_SAFE_INTEGER = 9007199254740991;

/** Used to detect unsigned integer values. */
var reIsUint = /^(?:0|[1-9]\d*)$/;

/**
 * Checks if `value` is a valid array-like index.
 *
 * @private
 * @param {*} value The value to check.
 * @param {number} [length=MAX_SAFE_INTEGER] The upper bounds of a valid index.
 * @returns {boolean} Returns `true` if `value` is a valid index, else `false`.
 */
function isIndex(value, length) {
  length = length == null ? MAX_SAFE_INTEGER : length;
  return !!length &&
    (typeof value == 'number' || reIsUint.test(value)) &&
    (value > -1 && value % 1 == 0 && value < length);
}

module.exports = isIndex;


/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

var getNative = __webpack_require__(22);

/* Built-in method references that are verified to be native. */
var nativeCreate = getNative(Object, 'create');

module.exports = nativeCreate;


/***/ }),
/* 17 */
/***/ (function(module, exports) {

/**
 * Performs a
 * [`SameValueZero`](http://ecma-international.org/ecma-262/7.0/#sec-samevaluezero)
 * comparison between two values to determine if they are equivalent.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to compare.
 * @param {*} other The other value to compare.
 * @returns {boolean} Returns `true` if the values are equivalent, else `false`.
 * @example
 *
 * var object = { 'a': 1 };
 * var other = { 'a': 1 };
 *
 * _.eq(object, object);
 * // => true
 *
 * _.eq(object, other);
 * // => false
 *
 * _.eq('a', 'a');
 * // => true
 *
 * _.eq('a', Object('a'));
 * // => false
 *
 * _.eq(NaN, NaN);
 * // => true
 */
function eq(value, other) {
  return value === other || (value !== value && other !== other);
}

module.exports = eq;


/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

var baseIsArguments = __webpack_require__(96),
    isObjectLike = __webpack_require__(6);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Built-in value references. */
var propertyIsEnumerable = objectProto.propertyIsEnumerable;

/**
 * Checks if `value` is likely an `arguments` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an `arguments` object,
 *  else `false`.
 * @example
 *
 * _.isArguments(function() { return arguments; }());
 * // => true
 *
 * _.isArguments([1, 2, 3]);
 * // => false
 */
var isArguments = baseIsArguments(function() { return arguments; }()) ? baseIsArguments : function(value) {
  return isObjectLike(value) && hasOwnProperty.call(value, 'callee') &&
    !propertyIsEnumerable.call(value, 'callee');
};

module.exports = isArguments;


/***/ }),
/* 19 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__cluster__ = __webpack_require__(50);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__array_helper__ = __webpack_require__(3);
/* harmony export (immutable) */ __webpack_exports__["b"] = imageRef;
/* unused harmony export computeIntegralImage2 */
/* unused harmony export computeIntegralImage */
/* unused harmony export thresholdImage */
/* unused harmony export computeHistogram */
/* unused harmony export sharpenLine */
/* unused harmony export determineOtsuThreshold */
/* harmony export (immutable) */ __webpack_exports__["f"] = otsuThreshold;
/* unused harmony export computeBinaryImage */
/* harmony export (immutable) */ __webpack_exports__["g"] = cluster;
/* unused harmony export Tracer */
/* unused harmony export DILATE */
/* unused harmony export ERODE */
/* unused harmony export dilate */
/* unused harmony export erode */
/* unused harmony export subtract */
/* unused harmony export bitwiseOr */
/* unused harmony export countNonZero */
/* harmony export (immutable) */ __webpack_exports__["h"] = topGeneric;
/* unused harmony export grayArrayFromImage */
/* unused harmony export grayArrayFromContext */
/* harmony export (immutable) */ __webpack_exports__["c"] = grayAndHalfSampleFromCanvasData;
/* harmony export (immutable) */ __webpack_exports__["d"] = computeGray;
/* unused harmony export loadImageArray */
/* harmony export (immutable) */ __webpack_exports__["i"] = halfSample;
/* harmony export (immutable) */ __webpack_exports__["a"] = hsv2rgb;
/* unused harmony export _computeDivisors */
/* harmony export (immutable) */ __webpack_exports__["e"] = calculatePatchSize;
/* unused harmony export _parseCSSDimensionValues */
/* unused harmony export _dimensionsConverters */
/* harmony export (immutable) */ __webpack_exports__["j"] = computeImageArea;


var vec2 = {
    clone: __webpack_require__(7)
};
var vec3 = {
    clone: __webpack_require__(83)
};

/**
 * @param x x-coordinate
 * @param y y-coordinate
 * @return ImageReference {x,y} Coordinate
 */
function imageRef(x, y) {
    var that = {
        x: x,
        y: y,
        toVec2: function toVec2() {
            return vec2.clone([this.x, this.y]);
        },
        toVec3: function toVec3() {
            return vec3.clone([this.x, this.y, 1]);
        },
        round: function round() {
            this.x = this.x > 0.0 ? Math.floor(this.x + 0.5) : Math.floor(this.x - 0.5);
            this.y = this.y > 0.0 ? Math.floor(this.y + 0.5) : Math.floor(this.y - 0.5);
            return this;
        }
    };
    return that;
};

/**
 * Computes an integral image of a given grayscale image.
 * @param imageDataContainer {ImageDataContainer} the image to be integrated
 */
function computeIntegralImage2(imageWrapper, integralWrapper) {
    var imageData = imageWrapper.data;
    var width = imageWrapper.size.x;
    var height = imageWrapper.size.y;
    var integralImageData = integralWrapper.data;
    var sum = 0,
        posA = 0,
        posB = 0,
        posC = 0,
        posD = 0,
        x,
        y;

    // sum up first column
    posB = width;
    sum = 0;
    for (y = 1; y < height; y++) {
        sum += imageData[posA];
        integralImageData[posB] += sum;
        posA += width;
        posB += width;
    }

    posA = 0;
    posB = 1;
    sum = 0;
    for (x = 1; x < width; x++) {
        sum += imageData[posA];
        integralImageData[posB] += sum;
        posA++;
        posB++;
    }

    for (y = 1; y < height; y++) {
        posA = y * width + 1;
        posB = (y - 1) * width + 1;
        posC = y * width;
        posD = (y - 1) * width;
        for (x = 1; x < width; x++) {
            integralImageData[posA] += imageData[posA] + integralImageData[posB] + integralImageData[posC] - integralImageData[posD];
            posA++;
            posB++;
            posC++;
            posD++;
        }
    }
};

function computeIntegralImage(imageWrapper, integralWrapper) {
    var imageData = imageWrapper.data;
    var width = imageWrapper.size.x;
    var height = imageWrapper.size.y;
    var integralImageData = integralWrapper.data;
    var sum = 0;

    // sum up first row
    for (var i = 0; i < width; i++) {
        sum += imageData[i];
        integralImageData[i] = sum;
    }

    for (var v = 1; v < height; v++) {
        sum = 0;
        for (var u = 0; u < width; u++) {
            sum += imageData[v * width + u];
            integralImageData[v * width + u] = sum + integralImageData[(v - 1) * width + u];
        }
    }
};

function thresholdImage(imageWrapper, threshold, targetWrapper) {
    if (!targetWrapper) {
        targetWrapper = imageWrapper;
    }
    var imageData = imageWrapper.data,
        length = imageData.length,
        targetData = targetWrapper.data;

    while (length--) {
        targetData[length] = imageData[length] < threshold ? 1 : 0;
    }
};

function computeHistogram(imageWrapper, bitsPerPixel) {
    if (!bitsPerPixel) {
        bitsPerPixel = 8;
    }
    var imageData = imageWrapper.data,
        length = imageData.length,
        bitShift = 8 - bitsPerPixel,
        bucketCnt = 1 << bitsPerPixel,
        hist = new Int32Array(bucketCnt);

    while (length--) {
        hist[imageData[length] >> bitShift]++;
    }
    return hist;
};

function sharpenLine(line) {
    var i,
        length = line.length,
        left = line[0],
        center = line[1],
        right;

    for (i = 1; i < length - 1; i++) {
        right = line[i + 1];
        //  -1 4 -1 kernel
        line[i - 1] = center * 2 - left - right & 255;
        left = center;
        center = right;
    }
    return line;
};

function determineOtsuThreshold(imageWrapper, bitsPerPixel) {
    if (!bitsPerPixel) {
        bitsPerPixel = 8;
    }
    var hist,
        threshold,
        bitShift = 8 - bitsPerPixel;

    function px(init, end) {
        var sum = 0,
            i;
        for (i = init; i <= end; i++) {
            sum += hist[i];
        }
        return sum;
    }

    function mx(init, end) {
        var i,
            sum = 0;

        for (i = init; i <= end; i++) {
            sum += i * hist[i];
        }

        return sum;
    }

    function determineThreshold() {
        var vet = [0],
            p1,
            p2,
            p12,
            k,
            m1,
            m2,
            m12,
            max = (1 << bitsPerPixel) - 1;

        hist = computeHistogram(imageWrapper, bitsPerPixel);
        for (k = 1; k < max; k++) {
            p1 = px(0, k);
            p2 = px(k + 1, max);
            p12 = p1 * p2;
            if (p12 === 0) {
                p12 = 1;
            }
            m1 = mx(0, k) * p2;
            m2 = mx(k + 1, max) * p1;
            m12 = m1 - m2;
            vet[k] = m12 * m12 / p12;
        }
        return __WEBPACK_IMPORTED_MODULE_1__array_helper__["a" /* default */].maxIndex(vet);
    }

    threshold = determineThreshold();
    return threshold << bitShift;
};

function otsuThreshold(imageWrapper, targetWrapper) {
    var threshold = determineOtsuThreshold(imageWrapper);

    thresholdImage(imageWrapper, threshold, targetWrapper);
    return threshold;
};

// local thresholding
function computeBinaryImage(imageWrapper, integralWrapper, targetWrapper) {
    computeIntegralImage(imageWrapper, integralWrapper);

    if (!targetWrapper) {
        targetWrapper = imageWrapper;
    }
    var imageData = imageWrapper.data;
    var targetData = targetWrapper.data;
    var width = imageWrapper.size.x;
    var height = imageWrapper.size.y;
    var integralImageData = integralWrapper.data;
    var sum = 0,
        v,
        u,
        kernel = 3,
        A,
        B,
        C,
        D,
        avg,
        size = (kernel * 2 + 1) * (kernel * 2 + 1);

    // clear out top & bottom-border
    for (v = 0; v <= kernel; v++) {
        for (u = 0; u < width; u++) {
            targetData[v * width + u] = 0;
            targetData[(height - 1 - v) * width + u] = 0;
        }
    }

    // clear out left & right border
    for (v = kernel; v < height - kernel; v++) {
        for (u = 0; u <= kernel; u++) {
            targetData[v * width + u] = 0;
            targetData[v * width + (width - 1 - u)] = 0;
        }
    }

    for (v = kernel + 1; v < height - kernel - 1; v++) {
        for (u = kernel + 1; u < width - kernel; u++) {
            A = integralImageData[(v - kernel - 1) * width + (u - kernel - 1)];
            B = integralImageData[(v - kernel - 1) * width + (u + kernel)];
            C = integralImageData[(v + kernel) * width + (u - kernel - 1)];
            D = integralImageData[(v + kernel) * width + (u + kernel)];
            sum = D - C - B + A;
            avg = sum / size;
            targetData[v * width + u] = imageData[v * width + u] > avg + 5 ? 0 : 1;
        }
    }
};

function cluster(points, threshold, property) {
    var i,
        k,
        cluster,
        point,
        clusters = [];

    if (!property) {
        property = "rad";
    }

    function addToCluster(newPoint) {
        var found = false;
        for (k = 0; k < clusters.length; k++) {
            cluster = clusters[k];
            if (cluster.fits(newPoint)) {
                cluster.add(newPoint);
                found = true;
            }
        }
        return found;
    }

    // iterate over each cloud
    for (i = 0; i < points.length; i++) {
        point = __WEBPACK_IMPORTED_MODULE_0__cluster__["a" /* default */].createPoint(points[i], i, property);
        if (!addToCluster(point)) {
            clusters.push(__WEBPACK_IMPORTED_MODULE_0__cluster__["a" /* default */].create(point, threshold));
        }
    }
    return clusters;
};

var Tracer = {
    trace: function trace(points, vec) {
        var iteration,
            maxIterations = 10,
            top = [],
            result = [],
            centerPos = 0,
            currentPos = 0;

        function trace(idx, forward) {
            var from,
                to,
                toIdx,
                predictedPos,
                thresholdX = 1,
                thresholdY = Math.abs(vec[1] / 10),
                found = false;

            function match(pos, predicted) {
                if (pos.x > predicted.x - thresholdX && pos.x < predicted.x + thresholdX && pos.y > predicted.y - thresholdY && pos.y < predicted.y + thresholdY) {
                    return true;
                } else {
                    return false;
                }
            }

            // check if the next index is within the vec specifications
            // if not, check as long as the threshold is met

            from = points[idx];
            if (forward) {
                predictedPos = {
                    x: from.x + vec[0],
                    y: from.y + vec[1]
                };
            } else {
                predictedPos = {
                    x: from.x - vec[0],
                    y: from.y - vec[1]
                };
            }

            toIdx = forward ? idx + 1 : idx - 1;
            to = points[toIdx];
            while (to && (found = match(to, predictedPos)) !== true && Math.abs(to.y - from.y) < vec[1]) {
                toIdx = forward ? toIdx + 1 : toIdx - 1;
                to = points[toIdx];
            }

            return found ? toIdx : null;
        }

        for (iteration = 0; iteration < maxIterations; iteration++) {
            // randomly select point to start with
            centerPos = Math.floor(Math.random() * points.length);

            // trace forward
            top = [];
            currentPos = centerPos;
            top.push(points[currentPos]);
            while ((currentPos = trace(currentPos, true)) !== null) {
                top.push(points[currentPos]);
            }
            if (centerPos > 0) {
                currentPos = centerPos;
                while ((currentPos = trace(currentPos, false)) !== null) {
                    top.push(points[currentPos]);
                }
            }

            if (top.length > result.length) {
                result = top;
            }
        }
        return result;
    }
};

var DILATE = 1;
var ERODE = 2;

function dilate(inImageWrapper, outImageWrapper) {
    var v,
        u,
        inImageData = inImageWrapper.data,
        outImageData = outImageWrapper.data,
        height = inImageWrapper.size.y,
        width = inImageWrapper.size.x,
        sum,
        yStart1,
        yStart2,
        xStart1,
        xStart2;

    for (v = 1; v < height - 1; v++) {
        for (u = 1; u < width - 1; u++) {
            yStart1 = v - 1;
            yStart2 = v + 1;
            xStart1 = u - 1;
            xStart2 = u + 1;
            sum = inImageData[yStart1 * width + xStart1] + inImageData[yStart1 * width + xStart2] + inImageData[v * width + u] + inImageData[yStart2 * width + xStart1] + inImageData[yStart2 * width + xStart2];
            outImageData[v * width + u] = sum > 0 ? 1 : 0;
        }
    }
};

function erode(inImageWrapper, outImageWrapper) {
    var v,
        u,
        inImageData = inImageWrapper.data,
        outImageData = outImageWrapper.data,
        height = inImageWrapper.size.y,
        width = inImageWrapper.size.x,
        sum,
        yStart1,
        yStart2,
        xStart1,
        xStart2;

    for (v = 1; v < height - 1; v++) {
        for (u = 1; u < width - 1; u++) {
            yStart1 = v - 1;
            yStart2 = v + 1;
            xStart1 = u - 1;
            xStart2 = u + 1;
            sum = inImageData[yStart1 * width + xStart1] + inImageData[yStart1 * width + xStart2] + inImageData[v * width + u] + inImageData[yStart2 * width + xStart1] + inImageData[yStart2 * width + xStart2];
            outImageData[v * width + u] = sum === 5 ? 1 : 0;
        }
    }
};

function subtract(aImageWrapper, bImageWrapper, resultImageWrapper) {
    if (!resultImageWrapper) {
        resultImageWrapper = aImageWrapper;
    }
    var length = aImageWrapper.data.length,
        aImageData = aImageWrapper.data,
        bImageData = bImageWrapper.data,
        cImageData = resultImageWrapper.data;

    while (length--) {
        cImageData[length] = aImageData[length] - bImageData[length];
    }
};

function bitwiseOr(aImageWrapper, bImageWrapper, resultImageWrapper) {
    if (!resultImageWrapper) {
        resultImageWrapper = aImageWrapper;
    }
    var length = aImageWrapper.data.length,
        aImageData = aImageWrapper.data,
        bImageData = bImageWrapper.data,
        cImageData = resultImageWrapper.data;

    while (length--) {
        cImageData[length] = aImageData[length] || bImageData[length];
    }
};

function countNonZero(imageWrapper) {
    var length = imageWrapper.data.length,
        data = imageWrapper.data,
        sum = 0;

    while (length--) {
        sum += data[length];
    }
    return sum;
};

function topGeneric(list, top, scoreFunc) {
    var i,
        minIdx = 0,
        min = 0,
        queue = [],
        score,
        hit,
        pos;

    for (i = 0; i < top; i++) {
        queue[i] = {
            score: 0,
            item: null
        };
    }

    for (i = 0; i < list.length; i++) {
        score = scoreFunc.apply(this, [list[i]]);
        if (score > min) {
            hit = queue[minIdx];
            hit.score = score;
            hit.item = list[i];
            min = Number.MAX_VALUE;
            for (pos = 0; pos < top; pos++) {
                if (queue[pos].score < min) {
                    min = queue[pos].score;
                    minIdx = pos;
                }
            }
        }
    }

    return queue;
};

function grayArrayFromImage(htmlImage, offsetX, ctx, array) {
    ctx.drawImage(htmlImage, offsetX, 0, htmlImage.width, htmlImage.height);
    var ctxData = ctx.getImageData(offsetX, 0, htmlImage.width, htmlImage.height).data;
    computeGray(ctxData, array);
};

function grayArrayFromContext(ctx, size, offset, array) {
    var ctxData = ctx.getImageData(offset.x, offset.y, size.x, size.y).data;
    computeGray(ctxData, array);
};

function grayAndHalfSampleFromCanvasData(canvasData, size, outArray) {
    var topRowIdx = 0;
    var bottomRowIdx = size.x;
    var endIdx = Math.floor(canvasData.length / 4);
    var outWidth = size.x / 2;
    var outImgIdx = 0;
    var inWidth = size.x;
    var i;

    while (bottomRowIdx < endIdx) {
        for (i = 0; i < outWidth; i++) {
            outArray[outImgIdx] = (0.299 * canvasData[topRowIdx * 4 + 0] + 0.587 * canvasData[topRowIdx * 4 + 1] + 0.114 * canvasData[topRowIdx * 4 + 2] + (0.299 * canvasData[(topRowIdx + 1) * 4 + 0] + 0.587 * canvasData[(topRowIdx + 1) * 4 + 1] + 0.114 * canvasData[(topRowIdx + 1) * 4 + 2]) + (0.299 * canvasData[bottomRowIdx * 4 + 0] + 0.587 * canvasData[bottomRowIdx * 4 + 1] + 0.114 * canvasData[bottomRowIdx * 4 + 2]) + (0.299 * canvasData[(bottomRowIdx + 1) * 4 + 0] + 0.587 * canvasData[(bottomRowIdx + 1) * 4 + 1] + 0.114 * canvasData[(bottomRowIdx + 1) * 4 + 2])) / 4;
            outImgIdx++;
            topRowIdx = topRowIdx + 2;
            bottomRowIdx = bottomRowIdx + 2;
        }
        topRowIdx = topRowIdx + inWidth;
        bottomRowIdx = bottomRowIdx + inWidth;
    }
};

function computeGray(imageData, outArray, config) {
    var l = imageData.length / 4 | 0,
        i,
        singleChannel = config && config.singleChannel === true;

    if (singleChannel) {
        for (i = 0; i < l; i++) {
            outArray[i] = imageData[i * 4 + 0];
        }
    } else {
        for (i = 0; i < l; i++) {
            outArray[i] = 0.299 * imageData[i * 4 + 0] + 0.587 * imageData[i * 4 + 1] + 0.114 * imageData[i * 4 + 2];
        }
    }
};

function loadImageArray(src, callback, canvas) {
    if (!canvas) {
        canvas = document.createElement('canvas');
    }
    var img = new Image();
    img.callback = callback;
    img.onload = function () {
        canvas.width = this.width;
        canvas.height = this.height;
        var ctx = canvas.getContext('2d');
        ctx.drawImage(this, 0, 0);
        var array = new Uint8Array(this.width * this.height);
        ctx.drawImage(this, 0, 0);
        var data = ctx.getImageData(0, 0, this.width, this.height).data;
        computeGray(data, array);
        this.callback(array, {
            x: this.width,
            y: this.height
        }, this);
    };
    img.src = src;
};

/**
 * @param inImg {ImageWrapper} input image to be sampled
 * @param outImg {ImageWrapper} to be stored in
 */
function halfSample(inImgWrapper, outImgWrapper) {
    var inImg = inImgWrapper.data;
    var inWidth = inImgWrapper.size.x;
    var outImg = outImgWrapper.data;
    var topRowIdx = 0;
    var bottomRowIdx = inWidth;
    var endIdx = inImg.length;
    var outWidth = inWidth / 2;
    var outImgIdx = 0;
    while (bottomRowIdx < endIdx) {
        for (var i = 0; i < outWidth; i++) {
            outImg[outImgIdx] = Math.floor((inImg[topRowIdx] + inImg[topRowIdx + 1] + inImg[bottomRowIdx] + inImg[bottomRowIdx + 1]) / 4);
            outImgIdx++;
            topRowIdx = topRowIdx + 2;
            bottomRowIdx = bottomRowIdx + 2;
        }
        topRowIdx = topRowIdx + inWidth;
        bottomRowIdx = bottomRowIdx + inWidth;
    }
};

function hsv2rgb(hsv, rgb) {
    var h = hsv[0],
        s = hsv[1],
        v = hsv[2],
        c = v * s,
        x = c * (1 - Math.abs(h / 60 % 2 - 1)),
        m = v - c,
        r = 0,
        g = 0,
        b = 0;

    rgb = rgb || [0, 0, 0];

    if (h < 60) {
        r = c;
        g = x;
    } else if (h < 120) {
        r = x;
        g = c;
    } else if (h < 180) {
        g = c;
        b = x;
    } else if (h < 240) {
        g = x;
        b = c;
    } else if (h < 300) {
        r = x;
        b = c;
    } else if (h < 360) {
        r = c;
        b = x;
    }
    rgb[0] = (r + m) * 255 | 0;
    rgb[1] = (g + m) * 255 | 0;
    rgb[2] = (b + m) * 255 | 0;
    return rgb;
};

function _computeDivisors(n) {
    var largeDivisors = [],
        divisors = [],
        i;

    for (i = 1; i < Math.sqrt(n) + 1; i++) {
        if (n % i === 0) {
            divisors.push(i);
            if (i !== n / i) {
                largeDivisors.unshift(Math.floor(n / i));
            }
        }
    }
    return divisors.concat(largeDivisors);
};

function _computeIntersection(arr1, arr2) {
    var i = 0,
        j = 0,
        result = [];

    while (i < arr1.length && j < arr2.length) {
        if (arr1[i] === arr2[j]) {
            result.push(arr1[i]);
            i++;
            j++;
        } else if (arr1[i] > arr2[j]) {
            j++;
        } else {
            i++;
        }
    }
    return result;
};

function calculatePatchSize(patchSize, imgSize) {
    var divisorsX = _computeDivisors(imgSize.x),
        divisorsY = _computeDivisors(imgSize.y),
        wideSide = Math.max(imgSize.x, imgSize.y),
        common = _computeIntersection(divisorsX, divisorsY),
        nrOfPatchesList = [8, 10, 15, 20, 32, 60, 80],
        nrOfPatchesMap = {
        "x-small": 5,
        "small": 4,
        "medium": 3,
        "large": 2,
        "x-large": 1
    },
        nrOfPatchesIdx = nrOfPatchesMap[patchSize] || nrOfPatchesMap.medium,
        nrOfPatches = nrOfPatchesList[nrOfPatchesIdx],
        desiredPatchSize = Math.floor(wideSide / nrOfPatches),
        optimalPatchSize;

    function findPatchSizeForDivisors(divisors) {
        var i = 0,
            found = divisors[Math.floor(divisors.length / 2)];

        while (i < divisors.length - 1 && divisors[i] < desiredPatchSize) {
            i++;
        }
        if (i > 0) {
            if (Math.abs(divisors[i] - desiredPatchSize) > Math.abs(divisors[i - 1] - desiredPatchSize)) {
                found = divisors[i - 1];
            } else {
                found = divisors[i];
            }
        }
        if (desiredPatchSize / found < nrOfPatchesList[nrOfPatchesIdx + 1] / nrOfPatchesList[nrOfPatchesIdx] && desiredPatchSize / found > nrOfPatchesList[nrOfPatchesIdx - 1] / nrOfPatchesList[nrOfPatchesIdx]) {
            return { x: found, y: found };
        }
        return null;
    }

    optimalPatchSize = findPatchSizeForDivisors(common);
    if (!optimalPatchSize) {
        optimalPatchSize = findPatchSizeForDivisors(_computeDivisors(wideSide));
        if (!optimalPatchSize) {
            optimalPatchSize = findPatchSizeForDivisors(_computeDivisors(desiredPatchSize * nrOfPatches));
        }
    }
    return optimalPatchSize;
};

function _parseCSSDimensionValues(value) {
    var dimension = {
        value: parseFloat(value),
        unit: value.indexOf("%") === value.length - 1 ? "%" : "%"
    };

    return dimension;
};

var _dimensionsConverters = {
    top: function top(dimension, context) {
        if (dimension.unit === "%") {
            return Math.floor(context.height * (dimension.value / 100));
        }
    },
    right: function right(dimension, context) {
        if (dimension.unit === "%") {
            return Math.floor(context.width - context.width * (dimension.value / 100));
        }
    },
    bottom: function bottom(dimension, context) {
        if (dimension.unit === "%") {
            return Math.floor(context.height - context.height * (dimension.value / 100));
        }
    },
    left: function left(dimension, context) {
        if (dimension.unit === "%") {
            return Math.floor(context.width * (dimension.value / 100));
        }
    }
};

function computeImageArea(inputWidth, inputHeight, area) {
    var context = { width: inputWidth, height: inputHeight };

    var parsedArea = Object.keys(area).reduce(function (result, key) {
        var value = area[key],
            parsed = _parseCSSDimensionValues(value),
            calculated = _dimensionsConverters[key](parsed, context);

        result[key] = calculated;
        return result;
    }, {});

    return {
        sx: parsedArea.left,
        sy: parsedArea.top,
        sw: parsedArea.right - parsedArea.left,
        sh: parsedArea.bottom - parsedArea.top
    };
};

/***/ }),
/* 20 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__subImage__ = __webpack_require__(53);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_cv_utils__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_array_helper__ = __webpack_require__(3);



var vec2 = {
    clone: __webpack_require__(7)
};

/**
 * Represents a basic image combining the data and size.
 * In addition, some methods for manipulation are contained.
 * @param size {x,y} The size of the image in pixel
 * @param data {Array} If given, a flat array containing the pixel data
 * @param ArrayType {Type} If given, the desired DataType of the Array (may be typed/non-typed)
 * @param initialize {Boolean} Indicating if the array should be initialized on creation.
 * @returns {ImageWrapper}
 */
function ImageWrapper(size, data, ArrayType, initialize) {
    if (!data) {
        if (ArrayType) {
            this.data = new ArrayType(size.x * size.y);
            if (ArrayType === Array && initialize) {
                __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(this.data, 0);
            }
        } else {
            this.data = new Uint8Array(size.x * size.y);
            if (Uint8Array === Array && initialize) {
                __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(this.data, 0);
            }
        }
    } else {
        this.data = data;
    }
    this.size = size;
}

/**
 * tests if a position is within the image with a given offset
 * @param imgRef {x, y} The location to test
 * @param border Number the padding value in pixel
 * @returns {Boolean} true if location inside the image's border, false otherwise
 * @see cvd/image.h
 */
ImageWrapper.prototype.inImageWithBorder = function (imgRef, border) {
    return imgRef.x >= border && imgRef.y >= border && imgRef.x < this.size.x - border && imgRef.y < this.size.y - border;
};

/**
 * Performs bilinear sampling
 * @param inImg Image to extract sample from
 * @param x the x-coordinate
 * @param y the y-coordinate
 * @returns the sampled value
 * @see cvd/vision.h
 */
ImageWrapper.sample = function (inImg, x, y) {
    var lx = Math.floor(x);
    var ly = Math.floor(y);
    var w = inImg.size.x;
    var base = ly * inImg.size.x + lx;
    var a = inImg.data[base + 0];
    var b = inImg.data[base + 1];
    var c = inImg.data[base + w];
    var d = inImg.data[base + w + 1];
    var e = a - b;
    x -= lx;
    y -= ly;

    var result = Math.floor(x * (y * (e - c + d) - e) + y * (c - a) + a);
    return result;
};

/**
 * Initializes a given array. Sets each element to zero.
 * @param array {Array} The array to initialize
 */
ImageWrapper.clearArray = function (array) {
    var l = array.length;
    while (l--) {
        array[l] = 0;
    }
};

/**
 * Creates a {SubImage} from the current image ({this}).
 * @param from {ImageRef} The position where to start the {SubImage} from. (top-left corner)
 * @param size {ImageRef} The size of the resulting image
 * @returns {SubImage} A shared part of the original image
 */
ImageWrapper.prototype.subImage = function (from, size) {
    return new __WEBPACK_IMPORTED_MODULE_0__subImage__["a" /* default */](from, size, this);
};

/**
 * Creates an {ImageWrapper) and copies the needed underlying image-data area
 * @param imageWrapper {ImageWrapper} The target {ImageWrapper} where the data should be copied
 * @param from {ImageRef} The location where to copy from (top-left location)
 */
ImageWrapper.prototype.subImageAsCopy = function (imageWrapper, from) {
    var sizeY = imageWrapper.size.y,
        sizeX = imageWrapper.size.x;
    var x, y;
    for (x = 0; x < sizeX; x++) {
        for (y = 0; y < sizeY; y++) {
            imageWrapper.data[y * sizeX + x] = this.data[(from.y + y) * this.size.x + from.x + x];
        }
    }
};

ImageWrapper.prototype.copyTo = function (imageWrapper) {
    var length = this.data.length,
        srcData = this.data,
        dstData = imageWrapper.data;

    while (length--) {
        dstData[length] = srcData[length];
    }
};

/**
 * Retrieves a given pixel position from the image
 * @param x {Number} The x-position
 * @param y {Number} The y-position
 * @returns {Number} The grayscale value at the pixel-position
 */
ImageWrapper.prototype.get = function (x, y) {
    return this.data[y * this.size.x + x];
};

/**
 * Retrieves a given pixel position from the image
 * @param x {Number} The x-position
 * @param y {Number} The y-position
 * @returns {Number} The grayscale value at the pixel-position
 */
ImageWrapper.prototype.getSafe = function (x, y) {
    var i;

    if (!this.indexMapping) {
        this.indexMapping = {
            x: [],
            y: []
        };
        for (i = 0; i < this.size.x; i++) {
            this.indexMapping.x[i] = i;
            this.indexMapping.x[i + this.size.x] = i;
        }
        for (i = 0; i < this.size.y; i++) {
            this.indexMapping.y[i] = i;
            this.indexMapping.y[i + this.size.y] = i;
        }
    }
    return this.data[this.indexMapping.y[y + this.size.y] * this.size.x + this.indexMapping.x[x + this.size.x]];
};

/**
 * Sets a given pixel position in the image
 * @param x {Number} The x-position
 * @param y {Number} The y-position
 * @param value {Number} The grayscale value to set
 * @returns {ImageWrapper} The Image itself (for possible chaining)
 */
ImageWrapper.prototype.set = function (x, y, value) {
    this.data[y * this.size.x + x] = value;
    return this;
};

/**
 * Sets the border of the image (1 pixel) to zero
 */
ImageWrapper.prototype.zeroBorder = function () {
    var i,
        width = this.size.x,
        height = this.size.y,
        data = this.data;
    for (i = 0; i < width; i++) {
        data[i] = data[(height - 1) * width + i] = 0;
    }
    for (i = 1; i < height - 1; i++) {
        data[i * width] = data[i * width + (width - 1)] = 0;
    }
};

/**
 * Inverts a binary image in place
 */
ImageWrapper.prototype.invert = function () {
    var data = this.data,
        length = data.length;

    while (length--) {
        data[length] = data[length] ? 0 : 1;
    }
};

ImageWrapper.prototype.convolve = function (kernel) {
    var x,
        y,
        kx,
        ky,
        kSize = kernel.length / 2 | 0,
        accu = 0;
    for (y = 0; y < this.size.y; y++) {
        for (x = 0; x < this.size.x; x++) {
            accu = 0;
            for (ky = -kSize; ky <= kSize; ky++) {
                for (kx = -kSize; kx <= kSize; kx++) {
                    accu += kernel[ky + kSize][kx + kSize] * this.getSafe(x + kx, y + ky);
                }
            }
            this.data[y * this.size.x + x] = accu;
        }
    }
};

ImageWrapper.prototype.moments = function (labelcount) {
    var data = this.data,
        x,
        y,
        height = this.size.y,
        width = this.size.x,
        val,
        ysq,
        labelsum = [],
        i,
        label,
        mu11,
        mu02,
        mu20,
        x_,
        y_,
        tmp,
        result = [],
        PI = Math.PI,
        PI_4 = PI / 4;

    if (labelcount <= 0) {
        return result;
    }

    for (i = 0; i < labelcount; i++) {
        labelsum[i] = {
            m00: 0,
            m01: 0,
            m10: 0,
            m11: 0,
            m02: 0,
            m20: 0,
            theta: 0,
            rad: 0
        };
    }

    for (y = 0; y < height; y++) {
        ysq = y * y;
        for (x = 0; x < width; x++) {
            val = data[y * width + x];
            if (val > 0) {
                label = labelsum[val - 1];
                label.m00 += 1;
                label.m01 += y;
                label.m10 += x;
                label.m11 += x * y;
                label.m02 += ysq;
                label.m20 += x * x;
            }
        }
    }

    for (i = 0; i < labelcount; i++) {
        label = labelsum[i];
        if (!isNaN(label.m00) && label.m00 !== 0) {
            x_ = label.m10 / label.m00;
            y_ = label.m01 / label.m00;
            mu11 = label.m11 / label.m00 - x_ * y_;
            mu02 = label.m02 / label.m00 - y_ * y_;
            mu20 = label.m20 / label.m00 - x_ * x_;
            tmp = (mu02 - mu20) / (2 * mu11);
            tmp = 0.5 * Math.atan(tmp) + (mu11 >= 0 ? PI_4 : -PI_4) + PI;
            label.theta = (tmp * 180 / PI + 90) % 180 - 90;
            if (label.theta < 0) {
                label.theta += 180;
            }
            label.rad = tmp > PI ? tmp - PI : tmp;
            label.vec = vec2.clone([Math.cos(tmp), Math.sin(tmp)]);
            result.push(label);
        }
    }

    return result;
};

/**
 * Displays the {ImageWrapper} in a given canvas
 * @param canvas {Canvas} The canvas element to write to
 * @param scale {Number} Scale which is applied to each pixel-value
 */
ImageWrapper.prototype.show = function (canvas, scale) {
    var ctx, frame, data, current, pixel, x, y;

    if (!scale) {
        scale = 1.0;
    }
    ctx = canvas.getContext('2d');
    canvas.width = this.size.x;
    canvas.height = this.size.y;
    frame = ctx.getImageData(0, 0, canvas.width, canvas.height);
    data = frame.data;
    current = 0;
    for (y = 0; y < this.size.y; y++) {
        for (x = 0; x < this.size.x; x++) {
            pixel = y * this.size.x + x;
            current = this.get(x, y) * scale;
            data[pixel * 4 + 0] = current;
            data[pixel * 4 + 1] = current;
            data[pixel * 4 + 2] = current;
            data[pixel * 4 + 3] = 255;
        }
    }
    //frame.data = data;
    ctx.putImageData(frame, 0, 0);
};

/**
 * Displays the {SubImage} in a given canvas
 * @param canvas {Canvas} The canvas element to write to
 * @param scale {Number} Scale which is applied to each pixel-value
 */
ImageWrapper.prototype.overlay = function (canvas, scale, from) {
    if (!scale || scale < 0 || scale > 360) {
        scale = 360;
    }
    var hsv = [0, 1, 1];
    var rgb = [0, 0, 0];
    var whiteRgb = [255, 255, 255];
    var blackRgb = [0, 0, 0];
    var result = [];
    var ctx = canvas.getContext('2d');
    var frame = ctx.getImageData(from.x, from.y, this.size.x, this.size.y);
    var data = frame.data;
    var length = this.data.length;
    while (length--) {
        hsv[0] = this.data[length] * scale;
        result = hsv[0] <= 0 ? whiteRgb : hsv[0] >= 360 ? blackRgb : __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["a" /* hsv2rgb */])(hsv, rgb);
        data[length * 4 + 0] = result[0];
        data[length * 4 + 1] = result[1];
        data[length * 4 + 2] = result[2];
        data[length * 4 + 3] = 255;
    }
    ctx.putImageData(frame, from.x, from.y);
};

/* harmony default export */ __webpack_exports__["a"] = ImageWrapper;

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

var defineProperty = __webpack_require__(37);

/**
 * The base implementation of `assignValue` and `assignMergeValue` without
 * value checks.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {string} key The key of the property to assign.
 * @param {*} value The value to assign.
 */
function baseAssignValue(object, key, value) {
  if (key == '__proto__' && defineProperty) {
    defineProperty(object, key, {
      'configurable': true,
      'enumerable': true,
      'value': value,
      'writable': true
    });
  } else {
    object[key] = value;
  }
}

module.exports = baseAssignValue;


/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

var baseIsNative = __webpack_require__(97),
    getValue = __webpack_require__(120);

/**
 * Gets the native function at `key` of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {string} key The key of the method to get.
 * @returns {*} Returns the function if it's native, else `undefined`.
 */
function getNative(object, key) {
  var value = getValue(object, key);
  return baseIsNative(value) ? value : undefined;
}

module.exports = getNative;


/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

var isSymbol = __webpack_require__(27);

/** Used as references for various `Number` constants. */
var INFINITY = 1 / 0;

/**
 * Converts `value` to a string key if it's not a string or symbol.
 *
 * @private
 * @param {*} value The value to inspect.
 * @returns {string|symbol} Returns the key.
 */
function toKey(value) {
  if (typeof value == 'string' || isSymbol(value)) {
    return value;
  }
  var result = (value + '');
  return (result == '0' && (1 / value) == -INFINITY) ? '-0' : result;
}

module.exports = toKey;


/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

var isFunction = __webpack_require__(25),
    isLength = __webpack_require__(26);

/**
 * Checks if `value` is array-like. A value is considered array-like if it's
 * not a function and has a `value.length` that's an integer greater than or
 * equal to `0` and less than or equal to `Number.MAX_SAFE_INTEGER`.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is array-like, else `false`.
 * @example
 *
 * _.isArrayLike([1, 2, 3]);
 * // => true
 *
 * _.isArrayLike(document.body.children);
 * // => true
 *
 * _.isArrayLike('abc');
 * // => true
 *
 * _.isArrayLike(_.noop);
 * // => false
 */
function isArrayLike(value) {
  return value != null && isLength(value.length) && !isFunction(value);
}

module.exports = isArrayLike;


/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

var baseGetTag = __webpack_require__(8),
    isObject = __webpack_require__(0);

/** `Object#toString` result references. */
var asyncTag = '[object AsyncFunction]',
    funcTag = '[object Function]',
    genTag = '[object GeneratorFunction]',
    proxyTag = '[object Proxy]';

/**
 * Checks if `value` is classified as a `Function` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a function, else `false`.
 * @example
 *
 * _.isFunction(_);
 * // => true
 *
 * _.isFunction(/abc/);
 * // => false
 */
function isFunction(value) {
  if (!isObject(value)) {
    return false;
  }
  // The use of `Object#toString` avoids issues with the `typeof` operator
  // in Safari 9 which returns 'object' for typed arrays and other constructors.
  var tag = baseGetTag(value);
  return tag == funcTag || tag == genTag || tag == asyncTag || tag == proxyTag;
}

module.exports = isFunction;


/***/ }),
/* 26 */
/***/ (function(module, exports) {

/** Used as references for various `Number` constants. */
var MAX_SAFE_INTEGER = 9007199254740991;

/**
 * Checks if `value` is a valid array-like length.
 *
 * **Note:** This method is loosely based on
 * [`ToLength`](http://ecma-international.org/ecma-262/7.0/#sec-tolength).
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a valid length, else `false`.
 * @example
 *
 * _.isLength(3);
 * // => true
 *
 * _.isLength(Number.MIN_VALUE);
 * // => false
 *
 * _.isLength(Infinity);
 * // => false
 *
 * _.isLength('3');
 * // => false
 */
function isLength(value) {
  return typeof value == 'number' &&
    value > -1 && value % 1 == 0 && value <= MAX_SAFE_INTEGER;
}

module.exports = isLength;


/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

var baseGetTag = __webpack_require__(8),
    isObjectLike = __webpack_require__(6);

/** `Object#toString` result references. */
var symbolTag = '[object Symbol]';

/**
 * Checks if `value` is classified as a `Symbol` primitive or object.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a symbol, else `false`.
 * @example
 *
 * _.isSymbol(Symbol.iterator);
 * // => true
 *
 * _.isSymbol('abc');
 * // => false
 */
function isSymbol(value) {
  return typeof value == 'symbol' ||
    (isObjectLike(value) && baseGetTag(value) == symbolTag);
}

module.exports = isSymbol;


/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

var baseMerge = __webpack_require__(100),
    createAssigner = __webpack_require__(116);

/**
 * This method is like `_.assign` except that it recursively merges own and
 * inherited enumerable string keyed properties of source objects into the
 * destination object. Source properties that resolve to `undefined` are
 * skipped if a destination value exists. Array and plain object properties
 * are merged recursively. Other objects and value types are overridden by
 * assignment. Source objects are applied from left to right. Subsequent
 * sources overwrite property assignments of previous sources.
 *
 * **Note:** This method mutates `object`.
 *
 * @static
 * @memberOf _
 * @since 0.5.0
 * @category Object
 * @param {Object} object The destination object.
 * @param {...Object} [sources] The source objects.
 * @returns {Object} Returns `object`.
 * @example
 *
 * var object = {
 *   'a': [{ 'b': 2 }, { 'd': 4 }]
 * };
 *
 * var other = {
 *   'a': [{ 'c': 3 }, { 'e': 5 }]
 * };
 *
 * _.merge(object, other);
 * // => { 'a': [{ 'b': 2, 'c': 3 }, { 'd': 4, 'e': 5 }] }
 */
var merge = createAssigner(function(object, source, srcIndex) {
  baseMerge(object, source, srcIndex);
});

module.exports = merge;


/***/ }),
/* 29 */
/***/ (function(module, exports) {

module.exports = function(module) {
	if(!module.webpackPolyfill) {
		module.deprecate = function() {};
		module.paths = [];
		// module.parent = undefined by default
		if(!module.children) module.children = [];
		Object.defineProperty(module, "loaded", {
			enumerable: true,
			get: function() {
				return module.l;
			}
		});
		Object.defineProperty(module, "id", {
			enumerable: true,
			get: function() {
				return module.i;
			}
		});
		module.webpackPolyfill = 1;
	}
	return module;
};


/***/ }),
/* 30 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/**
 * http://www.codeproject.com/Tips/407172/Connected-Component-Labeling-and-Vectorization
 */
var Tracer = {
    searchDirections: [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]],
    create: function create(imageWrapper, labelWrapper) {
        var imageData = imageWrapper.data,
            labelData = labelWrapper.data,
            searchDirections = this.searchDirections,
            width = imageWrapper.size.x,
            pos;

        function _trace(current, color, label, edgelabel) {
            var i, y, x;

            for (i = 0; i < 7; i++) {
                y = current.cy + searchDirections[current.dir][0];
                x = current.cx + searchDirections[current.dir][1];
                pos = y * width + x;
                if (imageData[pos] === color && (labelData[pos] === 0 || labelData[pos] === label)) {
                    labelData[pos] = label;
                    current.cy = y;
                    current.cx = x;
                    return true;
                } else {
                    if (labelData[pos] === 0) {
                        labelData[pos] = edgelabel;
                    }
                    current.dir = (current.dir + 1) % 8;
                }
            }
            return false;
        }

        function vertex2D(x, y, dir) {
            return {
                dir: dir,
                x: x,
                y: y,
                next: null,
                prev: null
            };
        }

        function _contourTracing(sy, sx, label, color, edgelabel) {
            var Fv = null,
                Cv,
                P,
                ldir,
                current = {
                cx: sx,
                cy: sy,
                dir: 0
            };

            if (_trace(current, color, label, edgelabel)) {
                Fv = vertex2D(sx, sy, current.dir);
                Cv = Fv;
                ldir = current.dir;
                P = vertex2D(current.cx, current.cy, 0);
                P.prev = Cv;
                Cv.next = P;
                P.next = null;
                Cv = P;
                do {
                    current.dir = (current.dir + 6) % 8;
                    _trace(current, color, label, edgelabel);
                    if (ldir !== current.dir) {
                        Cv.dir = current.dir;
                        P = vertex2D(current.cx, current.cy, 0);
                        P.prev = Cv;
                        Cv.next = P;
                        P.next = null;
                        Cv = P;
                    } else {
                        Cv.dir = ldir;
                        Cv.x = current.cx;
                        Cv.y = current.cy;
                    }
                    ldir = current.dir;
                } while (current.cx !== sx || current.cy !== sy);
                Fv.prev = Cv.prev;
                Cv.prev.next = Fv;
            }
            return Fv;
        }

        return {
            trace: function trace(current, color, label, edgelabel) {
                return _trace(current, color, label, edgelabel);
            },
            contourTracing: function contourTracing(sy, sx, label, color, edgelabel) {
                return _contourTracing(sy, sx, label, color, edgelabel);
            }
        };
    }
};

/* harmony default export */ __webpack_exports__["a"] = Tracer;

/***/ }),
/* 31 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__barcode_reader__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_array_helper__ = __webpack_require__(3);



function Code39Reader() {
    __WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].call(this);
}

var properties = {
    ALPHABETH_STRING: { value: "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%" },
    ALPHABET: { value: [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 45, 46, 32, 42, 36, 47, 43, 37] },
    CHARACTER_ENCODINGS: { value: [0x034, 0x121, 0x061, 0x160, 0x031, 0x130, 0x070, 0x025, 0x124, 0x064, 0x109, 0x049, 0x148, 0x019, 0x118, 0x058, 0x00D, 0x10C, 0x04C, 0x01C, 0x103, 0x043, 0x142, 0x013, 0x112, 0x052, 0x007, 0x106, 0x046, 0x016, 0x181, 0x0C1, 0x1C0, 0x091, 0x190, 0x0D0, 0x085, 0x184, 0x0C4, 0x094, 0x0A8, 0x0A2, 0x08A, 0x02A] },
    ASTERISK: { value: 0x094 },
    FORMAT: { value: "code_39", writeable: false }
};

Code39Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype, properties);
Code39Reader.prototype.constructor = Code39Reader;

Code39Reader.prototype._decode = function () {
    var self = this,
        counters = [0, 0, 0, 0, 0, 0, 0, 0, 0],
        result = [],
        start = self._findStart(),
        decodedChar,
        lastStart,
        pattern,
        nextStart;

    if (!start) {
        return null;
    }
    nextStart = self._nextSet(self._row, start.end);

    do {
        counters = self._toCounters(nextStart, counters);
        pattern = self._toPattern(counters);
        if (pattern < 0) {
            return null;
        }
        decodedChar = self._patternToChar(pattern);
        if (decodedChar < 0) {
            return null;
        }
        result.push(decodedChar);
        lastStart = nextStart;
        nextStart += __WEBPACK_IMPORTED_MODULE_1__common_array_helper__["a" /* default */].sum(counters);
        nextStart = self._nextSet(self._row, nextStart);
    } while (decodedChar !== '*');
    result.pop();

    if (!result.length) {
        return null;
    }

    if (!self._verifyTrailingWhitespace(lastStart, nextStart, counters)) {
        return null;
    }

    return {
        code: result.join(""),
        start: start.start,
        end: nextStart,
        startInfo: start,
        decodedCodes: result
    };
};

Code39Reader.prototype._verifyTrailingWhitespace = function (lastStart, nextStart, counters) {
    var trailingWhitespaceEnd,
        patternSize = __WEBPACK_IMPORTED_MODULE_1__common_array_helper__["a" /* default */].sum(counters);

    trailingWhitespaceEnd = nextStart - lastStart - patternSize;
    if (trailingWhitespaceEnd * 3 >= patternSize) {
        return true;
    }
    return false;
};

Code39Reader.prototype._patternToChar = function (pattern) {
    var i,
        self = this;

    for (i = 0; i < self.CHARACTER_ENCODINGS.length; i++) {
        if (self.CHARACTER_ENCODINGS[i] === pattern) {
            return String.fromCharCode(self.ALPHABET[i]);
        }
    }
    return -1;
};

Code39Reader.prototype._findNextWidth = function (counters, current) {
    var i,
        minWidth = Number.MAX_VALUE;

    for (i = 0; i < counters.length; i++) {
        if (counters[i] < minWidth && counters[i] > current) {
            minWidth = counters[i];
        }
    }

    return minWidth;
};

Code39Reader.prototype._toPattern = function (counters) {
    var numCounters = counters.length,
        maxNarrowWidth = 0,
        numWideBars = numCounters,
        wideBarWidth = 0,
        self = this,
        pattern,
        i;

    while (numWideBars > 3) {
        maxNarrowWidth = self._findNextWidth(counters, maxNarrowWidth);
        numWideBars = 0;
        pattern = 0;
        for (i = 0; i < numCounters; i++) {
            if (counters[i] > maxNarrowWidth) {
                pattern |= 1 << numCounters - 1 - i;
                numWideBars++;
                wideBarWidth += counters[i];
            }
        }

        if (numWideBars === 3) {
            for (i = 0; i < numCounters && numWideBars > 0; i++) {
                if (counters[i] > maxNarrowWidth) {
                    numWideBars--;
                    if (counters[i] * 2 >= wideBarWidth) {
                        return -1;
                    }
                }
            }
            return pattern;
        }
    }
    return -1;
};

Code39Reader.prototype._findStart = function () {
    var self = this,
        offset = self._nextSet(self._row),
        patternStart = offset,
        counter = [0, 0, 0, 0, 0, 0, 0, 0, 0],
        counterPos = 0,
        isWhite = false,
        i,
        j,
        whiteSpaceMustStart;

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                // find start pattern
                if (self._toPattern(counter) === self.ASTERISK) {
                    whiteSpaceMustStart = Math.floor(Math.max(0, patternStart - (i - patternStart) / 4));
                    if (self._matchRange(whiteSpaceMustStart, patternStart, 0)) {
                        return {
                            start: patternStart,
                            end: i
                        };
                    }
                }

                patternStart += counter[0] + counter[1];
                for (j = 0; j < 7; j++) {
                    counter[j] = counter[j + 2];
                }
                counter[7] = 0;
                counter[8] = 0;
                counterPos--;
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

/* harmony default export */ __webpack_exports__["a"] = Code39Reader;

/***/ }),
/* 32 */
/***/ (function(module, exports) {

module.exports = dot

/**
 * Calculates the dot product of two vec2's
 *
 * @param {vec2} a the first operand
 * @param {vec2} b the second operand
 * @returns {Number} dot product of a and b
 */
function dot(a, b) {
    return a[0] * b[0] + a[1] * b[1]
}

/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

var getNative = __webpack_require__(22),
    root = __webpack_require__(5);

/* Built-in method references that are verified to be native. */
var Map = getNative(root, 'Map');

module.exports = Map;


/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

var mapCacheClear = __webpack_require__(138),
    mapCacheDelete = __webpack_require__(139),
    mapCacheGet = __webpack_require__(140),
    mapCacheHas = __webpack_require__(141),
    mapCacheSet = __webpack_require__(142);

/**
 * Creates a map cache object to store key-value pairs.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function MapCache(entries) {
  var index = -1,
      length = entries == null ? 0 : entries.length;

  this.clear();
  while (++index < length) {
    var entry = entries[index];
    this.set(entry[0], entry[1]);
  }
}

// Add methods to `MapCache`.
MapCache.prototype.clear = mapCacheClear;
MapCache.prototype['delete'] = mapCacheDelete;
MapCache.prototype.get = mapCacheGet;
MapCache.prototype.has = mapCacheHas;
MapCache.prototype.set = mapCacheSet;

module.exports = MapCache;


/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

var baseAssignValue = __webpack_require__(21),
    eq = __webpack_require__(17);

/**
 * This function is like `assignValue` except that it doesn't assign
 * `undefined` values.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {string} key The key of the property to assign.
 * @param {*} value The value to assign.
 */
function assignMergeValue(object, key, value) {
  if ((value !== undefined && !eq(object[key], value)) ||
      (value === undefined && !(key in object))) {
    baseAssignValue(object, key, value);
  }
}

module.exports = assignMergeValue;


/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

var baseAssignValue = __webpack_require__(21),
    eq = __webpack_require__(17);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Assigns `value` to `key` of `object` if the existing value is not equivalent
 * using [`SameValueZero`](http://ecma-international.org/ecma-262/7.0/#sec-samevaluezero)
 * for equality comparisons.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {string} key The key of the property to assign.
 * @param {*} value The value to assign.
 */
function assignValue(object, key, value) {
  var objValue = object[key];
  if (!(hasOwnProperty.call(object, key) && eq(objValue, value)) ||
      (value === undefined && !(key in object))) {
    baseAssignValue(object, key, value);
  }
}

module.exports = assignValue;


/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

var getNative = __webpack_require__(22);

var defineProperty = (function() {
  try {
    var func = getNative(Object, 'defineProperty');
    func({}, '', {});
    return func;
  } catch (e) {}
}());

module.exports = defineProperty;


/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/** Detect free variable `global` from Node.js. */
var freeGlobal = typeof global == 'object' && global && global.Object === Object && global;

module.exports = freeGlobal;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(47)))

/***/ }),
/* 39 */
/***/ (function(module, exports, __webpack_require__) {

var overArg = __webpack_require__(147);

/** Built-in value references. */
var getPrototype = overArg(Object.getPrototypeOf, Object);

module.exports = getPrototype;


/***/ }),
/* 40 */
/***/ (function(module, exports) {

/** Used for built-in method references. */
var objectProto = Object.prototype;

/**
 * Checks if `value` is likely a prototype object.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a prototype, else `false`.
 */
function isPrototype(value) {
  var Ctor = value && value.constructor,
      proto = (typeof Ctor == 'function' && Ctor.prototype) || objectProto;

  return value === proto;
}

module.exports = isPrototype;


/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

var apply = __webpack_require__(87);

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMax = Math.max;

/**
 * A specialized version of `baseRest` which transforms the rest array.
 *
 * @private
 * @param {Function} func The function to apply a rest parameter to.
 * @param {number} [start=func.length-1] The start position of the rest parameter.
 * @param {Function} transform The rest array transform.
 * @returns {Function} Returns the new function.
 */
function overRest(func, start, transform) {
  start = nativeMax(start === undefined ? (func.length - 1) : start, 0);
  return function() {
    var args = arguments,
        index = -1,
        length = nativeMax(args.length - start, 0),
        array = Array(length);

    while (++index < length) {
      array[index] = args[start + index];
    }
    index = -1;
    var otherArgs = Array(start + 1);
    while (++index < start) {
      otherArgs[index] = args[index];
    }
    otherArgs[start] = transform(array);
    return apply(func, this, otherArgs);
  };
}

module.exports = overRest;


/***/ }),
/* 42 */
/***/ (function(module, exports, __webpack_require__) {

var baseSetToString = __webpack_require__(106),
    shortOut = __webpack_require__(148);

/**
 * Sets the `toString` method of `func` to return `string`.
 *
 * @private
 * @param {Function} func The function to modify.
 * @param {Function} string The `toString` result.
 * @returns {Function} Returns `func`.
 */
var setToString = shortOut(baseSetToString);

module.exports = setToString;


/***/ }),
/* 43 */
/***/ (function(module, exports) {

/**
 * This method returns the first argument it receives.
 *
 * @static
 * @since 0.1.0
 * @memberOf _
 * @category Util
 * @param {*} value Any value.
 * @returns {*} Returns `value`.
 * @example
 *
 * var object = { 'a': 1 };
 *
 * console.log(_.identity(object) === object);
 * // => true
 */
function identity(value) {
  return value;
}

module.exports = identity;


/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(module) {var root = __webpack_require__(5),
    stubFalse = __webpack_require__(163);

/** Detect free variable `exports`. */
var freeExports = typeof exports == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && typeof module == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Built-in value references. */
var Buffer = moduleExports ? root.Buffer : undefined;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeIsBuffer = Buffer ? Buffer.isBuffer : undefined;

/**
 * Checks if `value` is a buffer.
 *
 * @static
 * @memberOf _
 * @since 4.3.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a buffer, else `false`.
 * @example
 *
 * _.isBuffer(new Buffer(2));
 * // => true
 *
 * _.isBuffer(new Uint8Array(2));
 * // => false
 */
var isBuffer = nativeIsBuffer || stubFalse;

module.exports = isBuffer;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(29)(module)))

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

var baseIsTypedArray = __webpack_require__(98),
    baseUnary = __webpack_require__(109),
    nodeUtil = __webpack_require__(145);

/* Node.js helper references. */
var nodeIsTypedArray = nodeUtil && nodeUtil.isTypedArray;

/**
 * Checks if `value` is classified as a typed array.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
 * @example
 *
 * _.isTypedArray(new Uint8Array);
 * // => true
 *
 * _.isTypedArray([]);
 * // => false
 */
var isTypedArray = nodeIsTypedArray ? baseUnary(nodeIsTypedArray) : baseIsTypedArray;

module.exports = isTypedArray;


/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

var arrayLikeKeys = __webpack_require__(88),
    baseKeysIn = __webpack_require__(99),
    isArrayLike = __webpack_require__(24);

/**
 * Creates an array of the own and inherited enumerable property names of `object`.
 *
 * **Note:** Non-object values are coerced to objects.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Object
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 * @example
 *
 * function Foo() {
 *   this.a = 1;
 *   this.b = 2;
 * }
 *
 * Foo.prototype.c = 3;
 *
 * _.keysIn(new Foo);
 * // => ['a', 'b', 'c'] (iteration order is not guaranteed)
 */
function keysIn(object) {
  return isArrayLike(object) ? arrayLikeKeys(object, true) : baseKeysIn(object);
}

module.exports = keysIn;


/***/ }),
/* 47 */
/***/ (function(module, exports) {

var g;

// This works in non-strict mode
g = (function() {
	return this;
})();

try {
	// This works if eval is allowed (see CSP)
	g = g || Function("return this")() || (1,eval)("this");
} catch(e) {
	// This works if the window reference is available
	if(typeof window === "object")
		g = window;
}

// g can still be undefined, but nothing to do about it...
// We return undefined, instead of nothing here, so it's
// easier to handle this case. if(!global) { ...}

module.exports = g;


/***/ }),
/* 48 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash_merge__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_typedefs__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_typedefs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__common_typedefs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_image_wrapper__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__locator_barcode_locator__ = __webpack_require__(64);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__decoder_barcode_decoder__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__common_events__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__input_camera_access__ = __webpack_require__(59);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__common_image_debug__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__analytics_result_collector__ = __webpack_require__(49);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__config_config__ = __webpack_require__(56);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_input_stream__ = __webpack_require__(63);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_frame_grabber__ = __webpack_require__(61);


var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

 // eslint-disable-line no-unused-vars











var vec2 = {
    clone: __webpack_require__(7)
};

var _inputStream,
    _framegrabber,
    _stopped,
    _canvasContainer = {
    ctx: {
        image: null,
        overlay: null
    },
    dom: {
        image: null,
        overlay: null
    }
},
    _inputImageWrapper,
    _boxSize,
    _decoder,
    _workerPool = [],
    _onUIThread = true,
    _resultCollector,
    _config = {};

function initializeData(imageWrapper) {
    initBuffers(imageWrapper);
    _decoder = __WEBPACK_IMPORTED_MODULE_4__decoder_barcode_decoder__["a" /* default */].create(_config.decoder, _inputImageWrapper);
}

function initInputStream(cb) {
	debugger;
    var video;
    if (_config.inputStream.type === "VideoStream") {
        video = document.createElement("video");
        _inputStream = __WEBPACK_IMPORTED_MODULE_10_input_stream__["a" /* default */].createVideoStream(video);
    } else if (_config.inputStream.type === "ImageStream") {
        _inputStream = __WEBPACK_IMPORTED_MODULE_10_input_stream__["a" /* default */].createImageStream();
    } else if (_config.inputStream.type === "LiveStream") {
        var $viewport = getViewPort();
        if ($viewport) {
            video = $viewport.querySelector("video");
            if (!video) {
                video = document.createElement("video");
                $viewport.appendChild(video);
            }
        }
        _inputStream = __WEBPACK_IMPORTED_MODULE_10_input_stream__["a" /* default */].createLiveStream(video);
        __WEBPACK_IMPORTED_MODULE_6__input_camera_access__["a" /* default */].request(video, _config.inputStream.constraints).then(function () {
            _inputStream.trigger("canrecord");
        }).catch(function (err) {
            return cb(err);
        });
    }

    _inputStream.setAttribute("preload", "auto");
    _inputStream.setInputStream(_config.inputStream);
    _inputStream.addEventListener("canrecord", canRecord.bind(undefined, cb));
}

function getViewPort() {
    var target = _config.inputStream.target;
    // Check if target is already a DOM element
    if (target && target.nodeName && target.nodeType === 1) {
        return target;
    } else {
        // Use '#interactive.viewport' as a fallback selector (backwards compatibility)
        var selector = typeof target === 'string' ? target : '#interactive.viewport';
        return document.querySelector(selector);
    }
}

function canRecord(cb) {
    __WEBPACK_IMPORTED_MODULE_3__locator_barcode_locator__["a" /* default */].checkImageConstraints(_inputStream, _config.locator);
    initCanvas(_config);
    _framegrabber = __WEBPACK_IMPORTED_MODULE_11_frame_grabber__["a" /* default */].create(_inputStream, _canvasContainer.dom.image);

    adjustWorkerPool(_config.numOfWorkers, function () {
        if (_config.numOfWorkers === 0) {
            initializeData();
        }
        ready(cb);
    });
}

function ready(cb) {
    _inputStream.play();
    cb();
}

function initCanvas() {
    if (typeof document !== "undefined") {
        var $viewport = getViewPort();
        _canvasContainer.dom.image = document.querySelector("canvas.imgBuffer");
        if (!_canvasContainer.dom.image) {
            _canvasContainer.dom.image = document.createElement("canvas");
            _canvasContainer.dom.image.className = "imgBuffer";
            if ($viewport && _config.inputStream.type === "ImageStream") {
                $viewport.appendChild(_canvasContainer.dom.image);
            }
        }
        _canvasContainer.ctx.image = _canvasContainer.dom.image.getContext("2d");
        _canvasContainer.dom.image.width = _inputStream.getCanvasSize().x;
        _canvasContainer.dom.image.height = _inputStream.getCanvasSize().y;

        _canvasContainer.dom.overlay = document.querySelector("canvas.drawingBuffer");
        if (!_canvasContainer.dom.overlay) {
            _canvasContainer.dom.overlay = document.createElement("canvas");
            _canvasContainer.dom.overlay.className = "drawingBuffer";
            if ($viewport) {
                $viewport.appendChild(_canvasContainer.dom.overlay);
            }
            var clearFix = document.createElement("br");
            clearFix.setAttribute("clear", "all");
            if ($viewport) {
                $viewport.appendChild(clearFix);
            }
        }
        _canvasContainer.ctx.overlay = _canvasContainer.dom.overlay.getContext("2d");
        _canvasContainer.dom.overlay.width = _inputStream.getCanvasSize().x;
        _canvasContainer.dom.overlay.height = _inputStream.getCanvasSize().y;
    }
}

function initBuffers(imageWrapper) {
    if (imageWrapper) {
        _inputImageWrapper = imageWrapper;
    } else {
        _inputImageWrapper = new __WEBPACK_IMPORTED_MODULE_2__common_image_wrapper__["a" /* default */]({
            x: _inputStream.getWidth(),
            y: _inputStream.getHeight()
        });
    }

    if (true) {
        console.log(_inputImageWrapper.size);
    }
    _boxSize = [vec2.clone([0, 0]), vec2.clone([0, _inputImageWrapper.size.y]), vec2.clone([_inputImageWrapper.size.x, _inputImageWrapper.size.y]), vec2.clone([_inputImageWrapper.size.x, 0])];
    __WEBPACK_IMPORTED_MODULE_3__locator_barcode_locator__["a" /* default */].init(_inputImageWrapper, _config.locator);
}

function getBoundingBoxes() {
    if (_config.locate) {
        return __WEBPACK_IMPORTED_MODULE_3__locator_barcode_locator__["a" /* default */].locate();
    } else {
        return [[vec2.clone(_boxSize[0]), vec2.clone(_boxSize[1]), vec2.clone(_boxSize[2]), vec2.clone(_boxSize[3])]];
    }
}

function transformResult(result) {
    var topRight = _inputStream.getTopRight(),
        xOffset = topRight.x,
        yOffset = topRight.y,
        i;

    if (xOffset === 0 && yOffset === 0) {
        return;
    }

    if (result.barcodes) {
        for (i = 0; i < result.barcodes.length; i++) {
            transformResult(result.barcodes[i]);
        }
    }

    if (result.line && result.line.length === 2) {
        moveLine(result.line);
    }

    if (result.box) {
        moveBox(result.box);
    }

    if (result.boxes && result.boxes.length > 0) {
        for (i = 0; i < result.boxes.length; i++) {
            moveBox(result.boxes[i]);
        }
    }

    function moveBox(box) {
        var corner = box.length;

        while (corner--) {
            box[corner][0] += xOffset;
            box[corner][1] += yOffset;
        }
    }

    function moveLine(line) {
        line[0].x += xOffset;
        line[0].y += yOffset;
        line[1].x += xOffset;
        line[1].y += yOffset;
    }
}

function addResult(result, imageData) {
    if (!imageData || !_resultCollector) {
        return;
    }

    if (result.barcodes) {
        result.barcodes.filter(function (barcode) {
            return barcode.codeResult;
        }).forEach(function (barcode) {
            return addResult(barcode, imageData);
        });
    } else if (result.codeResult) {
        _resultCollector.addResult(imageData, _inputStream.getCanvasSize(), result.codeResult);
    }
}

function hasCodeResult(result) {
    return result && (result.barcodes ? result.barcodes.some(function (barcode) {
        return barcode.codeResult;
    }) : result.codeResult);
}

function publishResult(result, imageData) {
    var resultToPublish = result;

    if (result && _onUIThread) {
        transformResult(result);
        addResult(result, imageData);
        resultToPublish = result.barcodes || result;
    }

    __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].publish("processed", resultToPublish);
    if (hasCodeResult(result)) {
        __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].publish("detected", resultToPublish);
    }
}

function locateAndDecode() {
    var result, boxes;

    boxes = getBoundingBoxes();
    if (boxes) {
        result = _decoder.decodeFromBoundingBoxes(boxes);
        result = result || {};
        result.boxes = boxes;
        publishResult(result, _inputImageWrapper.data);
    } else {
        publishResult();
    }
}

function update() {
    var availableWorker;

    if (_onUIThread) {
        if (_workerPool.length > 0) {
            availableWorker = _workerPool.filter(function (workerThread) {
                return !workerThread.busy;
            })[0];
            if (availableWorker) {
                _framegrabber.attachData(availableWorker.imageData);
            } else {
                return; // all workers are busy
            }
        } else {
            _framegrabber.attachData(_inputImageWrapper.data);
        }
        if (_framegrabber.grab()) {
            if (availableWorker) {
                availableWorker.busy = true;
                availableWorker.worker.postMessage({
                    cmd: 'process',
                    imageData: availableWorker.imageData
                }, [availableWorker.imageData.buffer]);
            } else {
                locateAndDecode();
            }
        }
    } else {
        locateAndDecode();
    }
}

function startContinuousUpdate() {
    var next = null,
        delay = 1000 / (_config.frequency || 60);

    _stopped = false;
    (function frame(timestamp) {
        next = next || timestamp;
        if (!_stopped) {
            if (timestamp >= next) {
                next += delay;
                update();
            }
            window.requestAnimFrame(frame);
        }
    })(performance.now());
}

function _start() {
    if (_onUIThread && _config.inputStream.type === "LiveStream") {
        startContinuousUpdate();
    } else {
        update();
    }
}

function initWorker(cb) {
    var blobURL,
        workerThread = {
        worker: undefined,
        imageData: new Uint8Array(_inputStream.getWidth() * _inputStream.getHeight()),
        busy: true
    };

    blobURL = generateWorkerBlob();
    workerThread.worker = new Worker(blobURL);

    workerThread.worker.onmessage = function (e) {
        if (e.data.event === 'initialized') {
            URL.revokeObjectURL(blobURL);
            workerThread.busy = false;
            workerThread.imageData = new Uint8Array(e.data.imageData);
            if (true) {
                console.log("Worker initialized");
            }
            return cb(workerThread);
        } else if (e.data.event === 'processed') {
            workerThread.imageData = new Uint8Array(e.data.imageData);
            workerThread.busy = false;
            publishResult(e.data.result, workerThread.imageData);
        } else if (e.data.event === 'error') {
            if (true) {
                console.log("Worker error: " + e.data.message);
            }
        }
    };

    workerThread.worker.postMessage({
        cmd: 'init',
        size: { x: _inputStream.getWidth(), y: _inputStream.getHeight() },
        imageData: workerThread.imageData,
        config: configForWorker(_config)
    }, [workerThread.imageData.buffer]);
}

function configForWorker(config) {
    return _extends({}, config, {
        inputStream: _extends({}, config.inputStream, {
            target: null
        })
    });
}

function workerInterface(factory) {
    /* eslint-disable no-undef*/
    if (factory) {
        var Quagga = factory().default;
        if (!Quagga) {
            self.postMessage({ 'event': 'error', message: 'Quagga could not be created' });
            return;
        }
    }
    var imageWrapper;

    self.onmessage = function (e) {
        if (e.data.cmd === 'init') {
            var config = e.data.config;
            config.numOfWorkers = 0;
            imageWrapper = new Quagga.ImageWrapper({
                x: e.data.size.x,
                y: e.data.size.y
            }, new Uint8Array(e.data.imageData));
            Quagga.init(config, ready, imageWrapper);
            Quagga.onProcessed(onProcessed);
        } else if (e.data.cmd === 'process') {
            imageWrapper.data = new Uint8Array(e.data.imageData);
            Quagga.start();
        } else if (e.data.cmd === 'setReaders') {
            Quagga.setReaders(e.data.readers);
        }
    };

    function onProcessed(result) {
        self.postMessage({
            'event': 'processed',
            imageData: imageWrapper.data,
            result: result
        }, [imageWrapper.data.buffer]);
    }

    function ready() {
        // eslint-disable-line
        self.postMessage({ 'event': 'initialized', imageData: imageWrapper.data }, [imageWrapper.data.buffer]);
    }

    /* eslint-enable */
}

function generateWorkerBlob() {
    var blob, factorySource;

    /* jshint ignore:start */
    if (typeof __factorySource__ !== 'undefined') {
        factorySource = __factorySource__; // eslint-disable-line no-undef
    }
    /* jshint ignore:end */

    blob = new Blob(['(' + workerInterface.toString() + ')(' + factorySource + ');'], { type: 'text/javascript' });

    return window.URL.createObjectURL(blob);
}

function _setReaders(readers) {
    if (_decoder) {
        _decoder.setReaders(readers);
    } else if (_onUIThread && _workerPool.length > 0) {
        _workerPool.forEach(function (workerThread) {
            workerThread.worker.postMessage({ cmd: 'setReaders', readers: readers });
        });
    }
}

function adjustWorkerPool(capacity, cb) {
    var increaseBy = capacity - _workerPool.length;
    if (increaseBy === 0) {
        return cb && cb();
    }
    if (increaseBy < 0) {
        var workersToTerminate = _workerPool.slice(increaseBy);
        workersToTerminate.forEach(function (workerThread) {
            workerThread.worker.terminate();
            if (true) {
                console.log("Worker terminated!");
            }
        });
        _workerPool = _workerPool.slice(0, increaseBy);
        return cb && cb();
    } else {
        var workerInitialized = function workerInitialized(workerThread) {
            _workerPool.push(workerThread);
            if (_workerPool.length >= capacity) {
                cb && cb();
            }
        };

        for (var i = 0; i < increaseBy; i++) {
            initWorker(workerInitialized);
        }
    }
}

/* harmony default export */ __webpack_exports__["default"] = {
    init: function init(config, cb, imageWrapper) {
        _config = __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default()({}, __WEBPACK_IMPORTED_MODULE_9__config_config__["a" /* default */], config);
        if (imageWrapper) {
            _onUIThread = false;
            initializeData(imageWrapper);
            return cb();
        } else {
            initInputStream(cb);
        }
    },
    start: function start() {
        _start();
    },
    stop: function stop() {
        _stopped = true;
        adjustWorkerPool(0);
        if (_config.inputStream.type === "LiveStream") {
            __WEBPACK_IMPORTED_MODULE_6__input_camera_access__["a" /* default */].release();
            _inputStream.clearEventHandlers();
        }
    },
    pause: function pause() {
        _stopped = true;
    },
    onDetected: function onDetected(callback) {
        __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].subscribe("detected", callback);
    },
    offDetected: function offDetected(callback) {
        __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].unsubscribe("detected", callback);
    },
    onProcessed: function onProcessed(callback) {
        __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].subscribe("processed", callback);
    },
    offProcessed: function offProcessed(callback) {
        __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].unsubscribe("processed", callback);
    },
    setReaders: function setReaders(readers) {
        _setReaders(readers);
    },
    registerResultCollector: function registerResultCollector(resultCollector) {
        if (resultCollector && typeof resultCollector.addResult === 'function') {
            _resultCollector = resultCollector;
        }
    },
    canvas: _canvasContainer,
    decodeSingle: function decodeSingle(config, resultCallback) {
        var _this = this;

        config = __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default()({
            inputStream: {
                type: "ImageStream",
                sequence: false,
                size: 800,
                src: config.src
            },
            numOfWorkers: true && config.debug ? 0 : 1,
            locator: {
                halfSample: false
            }
        }, config);
        this.init(config, function () {
            __WEBPACK_IMPORTED_MODULE_5__common_events__["a" /* default */].once("processed", function (result) {
                _this.stop();
                resultCallback.call(null, result);
            }, true);
            _start();
        });
    },
    ImageWrapper: __WEBPACK_IMPORTED_MODULE_2__common_image_wrapper__["a" /* default */],
    ImageDebug: __WEBPACK_IMPORTED_MODULE_7__common_image_debug__["a" /* default */],
    ResultCollector: __WEBPACK_IMPORTED_MODULE_8__analytics_result_collector__["a" /* default */],
    CameraAccess: __WEBPACK_IMPORTED_MODULE_6__input_camera_access__["a" /* default */]
};

/***/ }),
/* 49 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__common_image_debug__ = __webpack_require__(9);


function contains(codeResult, list) {
    if (list) {
        return list.some(function (item) {
            return Object.keys(item).every(function (key) {
                return item[key] === codeResult[key];
            });
        });
    }
    return false;
}

function passesFilter(codeResult, filter) {
    if (typeof filter === 'function') {
        return filter(codeResult);
    }
    return true;
}

/* harmony default export */ __webpack_exports__["a"] = {
    create: function create(config) {
        var canvas = document.createElement("canvas"),
            ctx = canvas.getContext("2d"),
            results = [],
            capacity = config.capacity || 20,
            capture = config.capture === true;

        function matchesConstraints(codeResult) {
            return capacity && codeResult && !contains(codeResult, config.blacklist) && passesFilter(codeResult, config.filter);
        }

        return {
            addResult: function addResult(data, imageSize, codeResult) {
                var result = {};

                if (matchesConstraints(codeResult)) {
                    capacity--;
                    result.codeResult = codeResult;
                    if (capture) {
                        canvas.width = imageSize.x;
                        canvas.height = imageSize.y;
                        __WEBPACK_IMPORTED_MODULE_0__common_image_debug__["a" /* default */].drawImage(data, imageSize, ctx);
                        result.frame = canvas.toDataURL();
                    }
                    results.push(result);
                }
            },
            getResults: function getResults() {
                return results;
            }
        };
    }
};

/***/ }),
/* 50 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var vec2 = {
    clone: __webpack_require__(7),
    dot: __webpack_require__(32)
};
/**
 * Creates a cluster for grouping similar orientations of datapoints
 */
/* harmony default export */ __webpack_exports__["a"] = {
    create: function create(point, threshold) {
        var points = [],
            center = {
            rad: 0,
            vec: vec2.clone([0, 0])
        },
            pointMap = {};

        function init() {
            _add(point);
            updateCenter();
        }

        function _add(pointToAdd) {
            pointMap[pointToAdd.id] = pointToAdd;
            points.push(pointToAdd);
        }

        function updateCenter() {
            var i,
                sum = 0;
            for (i = 0; i < points.length; i++) {
                sum += points[i].rad;
            }
            center.rad = sum / points.length;
            center.vec = vec2.clone([Math.cos(center.rad), Math.sin(center.rad)]);
        }

        init();

        return {
            add: function add(pointToAdd) {
                if (!pointMap[pointToAdd.id]) {
                    _add(pointToAdd);
                    updateCenter();
                }
            },
            fits: function fits(otherPoint) {
                // check cosine similarity to center-angle
                var similarity = Math.abs(vec2.dot(otherPoint.point.vec, center.vec));
                if (similarity > threshold) {
                    return true;
                }
                return false;
            },
            getPoints: function getPoints() {
                return points;
            },
            getCenter: function getCenter() {
                return center;
            }
        };
    },
    createPoint: function createPoint(newPoint, id, property) {
        return {
            rad: newPoint[property],
            point: newPoint,
            id: id
        };
    }
};

/***/ }),
/* 51 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony default export */ __webpack_exports__["a"] = (function () {
    var events = {};

    function getEvent(eventName) {
        if (!events[eventName]) {
            events[eventName] = {
                subscribers: []
            };
        }
        return events[eventName];
    }

    function clearEvents() {
        events = {};
    }

    function publishSubscription(subscription, data) {
        if (subscription.async) {
            setTimeout(function () {
                subscription.callback(data);
            }, 4);
        } else {
            subscription.callback(data);
        }
    }

    function _subscribe(event, callback, async) {
        var subscription;

        if (typeof callback === "function") {
            subscription = {
                callback: callback,
                async: async
            };
        } else {
            subscription = callback;
            if (!subscription.callback) {
                throw "Callback was not specified on options";
            }
        }

        getEvent(event).subscribers.push(subscription);
    }

    return {
        subscribe: function subscribe(event, callback, async) {
            return _subscribe(event, callback, async);
        },
        publish: function publish(eventName, data) {
            var event = getEvent(eventName),
                subscribers = event.subscribers;

            // Publish one-time subscriptions
            subscribers.filter(function (subscriber) {
                return !!subscriber.once;
            }).forEach(function (subscriber) {
                publishSubscription(subscriber, data);
            });

            // remove them from the subscriber
            event.subscribers = subscribers.filter(function (subscriber) {
                return !subscriber.once;
            });

            // publish the rest
            event.subscribers.forEach(function (subscriber) {
                publishSubscription(subscriber, data);
            });
        },
        once: function once(event, callback, async) {
            _subscribe(event, {
                callback: callback,
                async: async,
                once: true
            });
        },
        unsubscribe: function unsubscribe(eventName, callback) {
            var event;

            if (eventName) {
                event = getEvent(eventName);
                if (event && callback) {
                    event.subscribers = event.subscribers.filter(function (subscriber) {
                        return subscriber.callback !== callback;
                    });
                } else {
                    event.subscribers = [];
                }
            } else {
                clearEvents();
            }
        }
    };
})();

/***/ }),
/* 52 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["b"] = enumerateDevices;
/* harmony export (immutable) */ __webpack_exports__["a"] = getUserMedia;

function enumerateDevices() {
    if (navigator.mediaDevices && typeof navigator.mediaDevices.enumerateDevices === 'function') {
        return navigator.mediaDevices.enumerateDevices();
    }
    return Promise.reject(new Error('enumerateDevices is not defined'));
};

function getUserMedia(constraints) {
    if (navigator.mediaDevices && typeof navigator.mediaDevices.getUserMedia === 'function') {
        return navigator.mediaDevices.getUserMedia(constraints);
    }
	
    return Promise.reject(new Error('getUserMedia is not defined'));
}

/***/ }),
/* 53 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/**
 * Construct representing a part of another {ImageWrapper}. Shares data
 * between the parent and the child.
 * @param from {ImageRef} The position where to start the {SubImage} from. (top-left corner)
 * @param size {ImageRef} The size of the resulting image
 * @param I {ImageWrapper} The {ImageWrapper} to share from
 * @returns {SubImage} A shared part of the original image
 */
function SubImage(from, size, I) {
    if (!I) {
        I = {
            data: null,
            size: size
        };
    }
    this.data = I.data;
    this.originalSize = I.size;
    this.I = I;

    this.from = from;
    this.size = size;
}

/**
 * Displays the {SubImage} in a given canvas
 * @param canvas {Canvas} The canvas element to write to
 * @param scale {Number} Scale which is applied to each pixel-value
 */
SubImage.prototype.show = function (canvas, scale) {
    var ctx, frame, data, current, y, x, pixel;

    if (!scale) {
        scale = 1.0;
    }
    ctx = canvas.getContext('2d');
    canvas.width = this.size.x;
    canvas.height = this.size.y;
    frame = ctx.getImageData(0, 0, canvas.width, canvas.height);
    data = frame.data;
    current = 0;
    for (y = 0; y < this.size.y; y++) {
        for (x = 0; x < this.size.x; x++) {
            pixel = y * this.size.x + x;
            current = this.get(x, y) * scale;
            data[pixel * 4 + 0] = current;
            data[pixel * 4 + 1] = current;
            data[pixel * 4 + 2] = current;
            data[pixel * 4 + 3] = 255;
        }
    }
    frame.data = data;
    ctx.putImageData(frame, 0, 0);
};

/**
 * Retrieves a given pixel position from the {SubImage}
 * @param x {Number} The x-position
 * @param y {Number} The y-position
 * @returns {Number} The grayscale value at the pixel-position
 */
SubImage.prototype.get = function (x, y) {
    return this.data[(this.from.y + y) * this.originalSize.x + this.from.x + x];
};

/**
 * Updates the underlying data from a given {ImageWrapper}
 * @param image {ImageWrapper} The updated image
 */
SubImage.prototype.updateData = function (image) {
    this.originalSize = image.size;
    this.data = image.data;
};

/**
 * Updates the position of the shared area
 * @param from {x,y} The new location
 * @returns {SubImage} returns {this} for possible chaining
 */
SubImage.prototype.updateFrom = function (from) {
    this.from = from;
    return this;
};

/* harmony default export */ __webpack_exports__["a"] = SubImage;

/***/ }),
/* 54 */
/***/ (function(module, exports) {

/*
 * typedefs.js
 * Normalizes browser-specific prefixes
 */

if (typeof window !== 'undefined') {
    window.requestAnimFrame = function () {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function ( /* function FrameRequestCallback */callback) {
            window.setTimeout(callback, 1000 / 60);
        };
    }();
}
Math.imul = Math.imul || function (a, b) {
    var ah = a >>> 16 & 0xffff,
        al = a & 0xffff,
        bh = b >>> 16 & 0xffff,
        bl = b & 0xffff;
    // the shift by 0 fixes the sign on the high part
    // the final |0 converts the unsigned value into a signed value
    return al * bl + (ah * bl + al * bh << 16 >>> 0) | 0;
};

if (typeof Object.assign !== 'function') {
    Object.assign = function (target) {
        // .length of function is 2
        'use strict';

        if (target === null) {
            // TypeError if undefined or null
            throw new TypeError('Cannot convert undefined or null to object');
        }

        var to = Object(target);

        for (var index = 1; index < arguments.length; index++) {
            var nextSource = arguments[index];

            if (nextSource !== null) {
                // Skip over if undefined or null
                for (var nextKey in nextSource) {
                    // Avoid bugs when hasOwnProperty is shadowed
                    if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
                        to[nextKey] = nextSource[nextKey];
                    }
                }
            }
        }
        return to;
    };
}

/***/ }),
/* 55 */
/***/ (function(module, exports) {

module.exports = {
    inputStream: {
        name: "Live",
        type: "LiveStream",
        constraints: {
            width: 640,
            height: 480,
            // aspectRatio: 640/480, // optional
            facingMode: "environment" },
        area: {
            top: "0%",
            right: "0%",
            left: "0%",
            bottom: "0%"
        },
        singleChannel: false // true: only the red color-channel is read
    },
    locate: true,
    numOfWorkers: 0,
    decoder: {
        readers: ['code_128_reader'],
        debug: {
            drawBoundingBox: false,
            showFrequency: false,
            drawScanline: false,
            showPattern: false
        }
    },
    locator: {
        halfSample: true,
        patchSize: "medium", // x-small, small, medium, large, x-large
        debug: {
            showCanvas: false,
            showPatches: false,
            showFoundPatches: false,
            showSkeleton: false,
            showLabels: false,
            showPatchLabels: false,
            showRemainingPatchLabels: false,
            boxFromPatches: {
                showTransformed: false,
                showTransformedBox: false,
                showBB: false
            }
        }
    }
};

/***/ }),
/* 56 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var config = void 0;

if (true) {
    config = __webpack_require__(55);
} else if (ENV.node) {
    config = require('./config.node.js');
} else {
    config = require('./config.prod.js');
}

/* harmony default export */ __webpack_exports__["a"] = config;

/***/ }),
/* 57 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__bresenham__ = __webpack_require__(58);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_image_debug__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__reader_code_128_reader__ = __webpack_require__(69);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__reader_ean_reader__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__reader_code_39_reader__ = __webpack_require__(31);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__reader_code_39_vin_reader__ = __webpack_require__(70);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__reader_codabar_reader__ = __webpack_require__(68);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__reader_upc_reader__ = __webpack_require__(77);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__reader_ean_8_reader__ = __webpack_require__(74);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__reader_ean_2_reader__ = __webpack_require__(72);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__reader_ean_5_reader__ = __webpack_require__(73);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__reader_upc_e_reader__ = __webpack_require__(76);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__reader_i2of5_reader__ = __webpack_require__(75);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__reader_2of5_reader__ = __webpack_require__(67);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__reader_code_93_reader__ = __webpack_require__(71);
var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

















var READERS = {
    code_128_reader: __WEBPACK_IMPORTED_MODULE_2__reader_code_128_reader__["a" /* default */],
    ean_reader: __WEBPACK_IMPORTED_MODULE_3__reader_ean_reader__["a" /* default */],
    ean_5_reader: __WEBPACK_IMPORTED_MODULE_10__reader_ean_5_reader__["a" /* default */],
    ean_2_reader: __WEBPACK_IMPORTED_MODULE_9__reader_ean_2_reader__["a" /* default */],
    ean_8_reader: __WEBPACK_IMPORTED_MODULE_8__reader_ean_8_reader__["a" /* default */],
    code_39_reader: __WEBPACK_IMPORTED_MODULE_4__reader_code_39_reader__["a" /* default */],
    code_39_vin_reader: __WEBPACK_IMPORTED_MODULE_5__reader_code_39_vin_reader__["a" /* default */],
    codabar_reader: __WEBPACK_IMPORTED_MODULE_6__reader_codabar_reader__["a" /* default */],
    upc_reader: __WEBPACK_IMPORTED_MODULE_7__reader_upc_reader__["a" /* default */],
    upc_e_reader: __WEBPACK_IMPORTED_MODULE_11__reader_upc_e_reader__["a" /* default */],
    i2of5_reader: __WEBPACK_IMPORTED_MODULE_12__reader_i2of5_reader__["a" /* default */],
    '2of5_reader': __WEBPACK_IMPORTED_MODULE_13__reader_2of5_reader__["a" /* default */],
    code_93_reader: __WEBPACK_IMPORTED_MODULE_14__reader_code_93_reader__["a" /* default */]
};
/* harmony default export */ __webpack_exports__["a"] = {
    create: function create(config, inputImageWrapper) {
        var _canvas = {
            ctx: {
                frequency: null,
                pattern: null,
                overlay: null
            },
            dom: {
                frequency: null,
                pattern: null,
                overlay: null
            }
        },
            _barcodeReaders = [];

        initCanvas();
        initReaders();
        initConfig();

        function initCanvas() {
            if (true && typeof document !== 'undefined') {
                var $debug = document.querySelector("#debug.detection");
                _canvas.dom.frequency = document.querySelector("canvas.frequency");
                if (!_canvas.dom.frequency) {
                    _canvas.dom.frequency = document.createElement("canvas");
                    _canvas.dom.frequency.className = "frequency";
                    if ($debug) {
                        $debug.appendChild(_canvas.dom.frequency);
                    }
                }
                _canvas.ctx.frequency = _canvas.dom.frequency.getContext("2d");

                _canvas.dom.pattern = document.querySelector("canvas.patternBuffer");
                if (!_canvas.dom.pattern) {
                    _canvas.dom.pattern = document.createElement("canvas");
                    _canvas.dom.pattern.className = "patternBuffer";
                    if ($debug) {
                        $debug.appendChild(_canvas.dom.pattern);
                    }
                }
                _canvas.ctx.pattern = _canvas.dom.pattern.getContext("2d");

                _canvas.dom.overlay = document.querySelector("canvas.drawingBuffer");
                if (_canvas.dom.overlay) {
                    _canvas.ctx.overlay = _canvas.dom.overlay.getContext("2d");
                }
            }
        }

        function initReaders() {
            config.readers.forEach(function (readerConfig) {
                var reader,
                    configuration = {},
                    supplements = [];

                if ((typeof readerConfig === 'undefined' ? 'undefined' : _typeof(readerConfig)) === 'object') {
                    reader = readerConfig.format;
                    configuration = readerConfig.config;
                } else if (typeof readerConfig === 'string') {
                    reader = readerConfig;
                }
                if (true) {
                    console.log("Before registering reader: ", reader);
                }
                if (configuration.supplements) {
                    supplements = configuration.supplements.map(function (supplement) {
                        return new READERS[supplement]();
                    });
                }
                _barcodeReaders.push(new READERS[reader](configuration, supplements));
            });
            if (true) {
                console.log("Registered Readers: " + _barcodeReaders.map(function (reader) {
                    return JSON.stringify({ format: reader.FORMAT, config: reader.config });
                }).join(', '));
            }
        }

        function initConfig() {
            if (true && typeof document !== 'undefined') {
                var i,
                    vis = [{
                    node: _canvas.dom.frequency,
                    prop: config.debug.showFrequency
                }, {
                    node: _canvas.dom.pattern,
                    prop: config.debug.showPattern
                }];

                for (i = 0; i < vis.length; i++) {
                    if (vis[i].prop === true) {
                        vis[i].node.style.display = "block";
                    } else {
                        vis[i].node.style.display = "none";
                    }
                }
            }
        }

        /**
         * extend the line on both ends
         * @param {Array} line
         * @param {Number} angle
         */
        function getExtendedLine(line, angle, ext) {
            function extendLine(amount) {
                var extension = {
                    y: amount * Math.sin(angle),
                    x: amount * Math.cos(angle)
                };

                line[0].y -= extension.y;
                line[0].x -= extension.x;
                line[1].y += extension.y;
                line[1].x += extension.x;
            }

            // check if inside image
            extendLine(ext);
            while (ext > 1 && (!inputImageWrapper.inImageWithBorder(line[0], 0) || !inputImageWrapper.inImageWithBorder(line[1], 0))) {
                ext -= Math.ceil(ext / 2);
                extendLine(-ext);
            }
            return line;
        }

        function getLine(box) {
            return [{
                x: (box[1][0] - box[0][0]) / 2 + box[0][0],
                y: (box[1][1] - box[0][1]) / 2 + box[0][1]
            }, {
                x: (box[3][0] - box[2][0]) / 2 + box[2][0],
                y: (box[3][1] - box[2][1]) / 2 + box[2][1]
            }];
        }

        function tryDecode(line) {
            var result = null,
                i,
                barcodeLine = __WEBPACK_IMPORTED_MODULE_0__bresenham__["a" /* default */].getBarcodeLine(inputImageWrapper, line[0], line[1]);

            if (true && config.debug.showFrequency) {
                __WEBPACK_IMPORTED_MODULE_1__common_image_debug__["a" /* default */].drawPath(line, { x: 'x', y: 'y' }, _canvas.ctx.overlay, { color: 'red', lineWidth: 3 });
                __WEBPACK_IMPORTED_MODULE_0__bresenham__["a" /* default */].debug.printFrequency(barcodeLine.line, _canvas.dom.frequency);
            }

            __WEBPACK_IMPORTED_MODULE_0__bresenham__["a" /* default */].toBinaryLine(barcodeLine);

            if (true && config.debug.showPattern) {
                __WEBPACK_IMPORTED_MODULE_0__bresenham__["a" /* default */].debug.printPattern(barcodeLine.line, _canvas.dom.pattern);
            }

            for (i = 0; i < _barcodeReaders.length && result === null; i++) {
                result = _barcodeReaders[i].decodePattern(barcodeLine.line);
            }
            if (result === null) {
                return null;
            }
            return {
                codeResult: result,
                barcodeLine: barcodeLine
            };
        }

        /**
         * This method slices the given area apart and tries to detect a barcode-pattern
         * for each slice. It returns the decoded barcode, or null if nothing was found
         * @param {Array} box
         * @param {Array} line
         * @param {Number} lineAngle
         */
        function tryDecodeBruteForce(box, line, lineAngle) {
            var sideLength = Math.sqrt(Math.pow(box[1][0] - box[0][0], 2) + Math.pow(box[1][1] - box[0][1], 2)),
                i,
                slices = 16,
                result = null,
                dir,
                extension,
                xdir = Math.sin(lineAngle),
                ydir = Math.cos(lineAngle);

            for (i = 1; i < slices && result === null; i++) {
                // move line perpendicular to angle
                dir = sideLength / slices * i * (i % 2 === 0 ? -1 : 1);
                extension = {
                    y: dir * xdir,
                    x: dir * ydir
                };
                line[0].y += extension.x;
                line[0].x -= extension.y;
                line[1].y += extension.x;
                line[1].x -= extension.y;

                result = tryDecode(line);
            }
            return result;
        }

        function getLineLength(line) {
            return Math.sqrt(Math.pow(Math.abs(line[1].y - line[0].y), 2) + Math.pow(Math.abs(line[1].x - line[0].x), 2));
        }

        /**
         * With the help of the configured readers (Code128 or EAN) this function tries to detect a
         * valid barcode pattern within the given area.
         * @param {Object} box The area to search in
         * @returns {Object} the result {codeResult, line, angle, pattern, threshold}
         */
        function _decodeFromBoundingBox(box) {
            var line,
                lineAngle,
                ctx = _canvas.ctx.overlay,
                result,
                lineLength;

            if (true) {
                if (config.debug.drawBoundingBox && ctx) {
                    __WEBPACK_IMPORTED_MODULE_1__common_image_debug__["a" /* default */].drawPath(box, { x: 0, y: 1 }, ctx, { color: "blue", lineWidth: 2 });
                }
            }

            line = getLine(box);
            lineLength = getLineLength(line);
            lineAngle = Math.atan2(line[1].y - line[0].y, line[1].x - line[0].x);
            line = getExtendedLine(line, lineAngle, Math.floor(lineLength * 0.1));
            if (line === null) {
                return null;
            }

            result = tryDecode(line);
            if (result === null) {
                result = tryDecodeBruteForce(box, line, lineAngle);
            }

            if (result === null) {
                return null;
            }

            if (true && result && config.debug.drawScanline && ctx) {
                __WEBPACK_IMPORTED_MODULE_1__common_image_debug__["a" /* default */].drawPath(line, { x: 'x', y: 'y' }, ctx, { color: 'red', lineWidth: 3 });
            }

            return {
                codeResult: result.codeResult,
                line: line,
                angle: lineAngle,
                pattern: result.barcodeLine.line,
                threshold: result.barcodeLine.threshold
            };
        }

        return {
            decodeFromBoundingBox: function decodeFromBoundingBox(box) {
                return _decodeFromBoundingBox(box);
            },
            decodeFromBoundingBoxes: function decodeFromBoundingBoxes(boxes) {
                var i,
                    result,
                    barcodes = [],
                    multiple = config.multiple;

                for (i = 0; i < boxes.length; i++) {
                    var box = boxes[i];
                    result = _decodeFromBoundingBox(box) || {};
                    result.box = box;

                    if (multiple) {
                        barcodes.push(result);
                    } else if (result.codeResult) {
                        return result;
                    }
                }

                if (multiple) {
                    return {
                        barcodes: barcodes
                    };
                }
            },
            setReaders: function setReaders(readers) {
                config.readers = readers;
                _barcodeReaders.length = 0;
                initReaders();
            }
        };
    }
};

/***/ }),
/* 58 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__ = __webpack_require__(20);


var Bresenham = {};

var Slope = {
    DIR: {
        UP: 1,
        DOWN: -1
    }
};
/**
 * Scans a line of the given image from point p1 to p2 and returns a result object containing
 * gray-scale values (0-255) of the underlying pixels in addition to the min
 * and max values.
 * @param {Object} imageWrapper
 * @param {Object} p1 The start point {x,y}
 * @param {Object} p2 The end point {x,y}
 * @returns {line, min, max}
 */
Bresenham.getBarcodeLine = function (imageWrapper, p1, p2) {
    var x0 = p1.x | 0,
        y0 = p1.y | 0,
        x1 = p2.x | 0,
        y1 = p2.y | 0,
        steep = Math.abs(y1 - y0) > Math.abs(x1 - x0),
        deltax,
        deltay,
        error,
        ystep,
        y,
        tmp,
        x,
        line = [],
        imageData = imageWrapper.data,
        width = imageWrapper.size.x,
        sum = 0,
        val,
        min = 255,
        max = 0;

    function read(a, b) {
        val = imageData[b * width + a];
        sum += val;
        min = val < min ? val : min;
        max = val > max ? val : max;
        line.push(val);
    }

    if (steep) {
        tmp = x0;
        x0 = y0;
        y0 = tmp;

        tmp = x1;
        x1 = y1;
        y1 = tmp;
    }
    if (x0 > x1) {
        tmp = x0;
        x0 = x1;
        x1 = tmp;

        tmp = y0;
        y0 = y1;
        y1 = tmp;
    }
    deltax = x1 - x0;
    deltay = Math.abs(y1 - y0);
    error = deltax / 2 | 0;
    y = y0;
    ystep = y0 < y1 ? 1 : -1;
    for (x = x0; x < x1; x++) {
        if (steep) {
            read(y, x);
        } else {
            read(x, y);
        }
        error = error - deltay;
        if (error < 0) {
            y = y + ystep;
            error = error + deltax;
        }
    }

    return {
        line: line,
        min: min,
        max: max
    };
};

/**
 * Converts the result from getBarcodeLine into a binary representation
 * also considering the frequency and slope of the signal for more robust results
 * @param {Object} result {line, min, max}
 */
Bresenham.toBinaryLine = function (result) {
    var min = result.min,
        max = result.max,
        line = result.line,
        slope,
        slope2,
        center = min + (max - min) / 2,
        extrema = [],
        currentDir,
        dir,
        threshold = (max - min) / 12,
        rThreshold = -threshold,
        i,
        j;

    // 1. find extrema
    currentDir = line[0] > center ? Slope.DIR.UP : Slope.DIR.DOWN;
    extrema.push({
        pos: 0,
        val: line[0]
    });
    for (i = 0; i < line.length - 2; i++) {
        slope = line[i + 1] - line[i];
        slope2 = line[i + 2] - line[i + 1];
        if (slope + slope2 < rThreshold && line[i + 1] < center * 1.5) {
            dir = Slope.DIR.DOWN;
        } else if (slope + slope2 > threshold && line[i + 1] > center * 0.5) {
            dir = Slope.DIR.UP;
        } else {
            dir = currentDir;
        }

        if (currentDir !== dir) {
            extrema.push({
                pos: i,
                val: line[i]
            });
            currentDir = dir;
        }
    }
    extrema.push({
        pos: line.length,
        val: line[line.length - 1]
    });

    for (j = extrema[0].pos; j < extrema[1].pos; j++) {
        line[j] = line[j] > center ? 0 : 1;
    }

    // iterate over extrema and convert to binary based on avg between minmax
    for (i = 1; i < extrema.length - 1; i++) {
        if (extrema[i + 1].val > extrema[i].val) {
            threshold = extrema[i].val + (extrema[i + 1].val - extrema[i].val) / 3 * 2 | 0;
        } else {
            threshold = extrema[i + 1].val + (extrema[i].val - extrema[i + 1].val) / 3 | 0;
        }

        for (j = extrema[i].pos; j < extrema[i + 1].pos; j++) {
            line[j] = line[j] > threshold ? 0 : 1;
        }
    }

    return {
        line: line,
        threshold: threshold
    };
};

/**
 * Used for development only
 */
Bresenham.debug = {
    printFrequency: function printFrequency(line, canvas) {
        var i,
            ctx = canvas.getContext("2d");
        canvas.width = line.length;
        canvas.height = 256;

        ctx.beginPath();
        ctx.strokeStyle = "blue";
        for (i = 0; i < line.length; i++) {
            ctx.moveTo(i, 255);
            ctx.lineTo(i, 255 - line[i]);
        }
        ctx.stroke();
        ctx.closePath();
    },

    printPattern: function printPattern(line, canvas) {
        var ctx = canvas.getContext("2d"),
            i;

        canvas.width = line.length;
        ctx.fillColor = "black";
        for (i = 0; i < line.length; i++) {
            if (line[i] === 1) {
                ctx.fillRect(i, 0, 1, 100);
            }
        }
    }
};

/* harmony default export */ __webpack_exports__["a"] = Bresenham;

/***/ }),
/* 59 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_pick__ = __webpack_require__(162);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_pick___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash_pick__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_mediaDevices__ = __webpack_require__(52);
/* unused harmony export pickConstraints */




var facingMatching = {
    "user": /front/i,
    "environment": /back/i
};

var streamRef;

function waitForVideo(video) {
    return new Promise(function (resolve, reject) {
        var attempts = 10;

        function checkVideo() {
            if (attempts > 0) {
                if (video.videoWidth > 10 && video.videoHeight > 10) {
                    if (true) {
                        console.log(video.videoWidth + "px x " + video.videoHeight + "px");
                    }
                    resolve();
                } else {
                    window.setTimeout(checkVideo, 500);
                }
            } else {
                reject('Unable to play video stream. Is webcam working?');
            }
            attempts--;
        }
        checkVideo();
    });
}

/**
 * Tries to attach the camera-stream to a given video-element
 * and calls the callback function when the content is ready
 * @param {Object} constraints
 * @param {Object} video
 */
function initCamera(video, constraints) {
    return __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1_mediaDevices__["a" /* getUserMedia */])(constraints).then(function (stream) {
        return new Promise(function (resolve) {
			debugger;
            streamRef = stream;
            video.setAttribute("autoplay", true);
            video.setAttribute('muted', true);
            video.setAttribute('playsinline', true);
            video.srcObject = stream;
            video.addEventListener('loadedmetadata', function () {
                video.play();
                resolve();
            });
        });
    }).then(waitForVideo.bind(null, video));
}

function deprecatedConstraints(videoConstraints) {
    var normalized = __WEBPACK_IMPORTED_MODULE_0_lodash_pick___default()(videoConstraints, ["width", "height", "facingMode", "aspectRatio", "deviceId"]);

    if (typeof videoConstraints.minAspectRatio !== 'undefined' && videoConstraints.minAspectRatio > 0) {
        normalized.aspectRatio = videoConstraints.minAspectRatio;
        console.log("WARNING: Constraint 'minAspectRatio' is deprecated; Use 'aspectRatio' instead");
    }
    if (typeof videoConstraints.facing !== 'undefined') {
        normalized.facingMode = videoConstraints.facing;
        console.log("WARNING: Constraint 'facing' is deprecated. Use 'facingMode' instead'");
    }
    return normalized;
}

function pickConstraints(videoConstraints) {
    var normalizedConstraints = {
        audio: false,
        video: deprecatedConstraints(videoConstraints)
    };

    if (normalizedConstraints.video.deviceId && normalizedConstraints.video.facingMode) {
        delete normalizedConstraints.video.facingMode;
    }
    return Promise.resolve(normalizedConstraints);
}

function enumerateVideoDevices() {
    return __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1_mediaDevices__["b" /* enumerateDevices */])().then(function (devices) {
        return devices.filter(function (device) {
            return device.kind === 'videoinput';
        });
    });
}

function getActiveTrack() {
    if (streamRef) {
        var tracks = streamRef.getVideoTracks();
        if (tracks && tracks.length) {
            return tracks[0];
        }
    }
}

/* harmony default export */ __webpack_exports__["a"] = {
    request: function request(video, videoConstraints) {
        return pickConstraints(videoConstraints).then(initCamera.bind(null, video));
    },
    release: function release() {
        var tracks = streamRef && streamRef.getVideoTracks();
        if (tracks && tracks.length) {
            tracks[0].stop();
        }
        streamRef = null;
    },
    enumerateVideoDevices: enumerateVideoDevices,
    getActiveStreamLabel: function getActiveStreamLabel() {
        var track = getActiveTrack();
        return track ? track.label : '';
    },
    getActiveTrack: getActiveTrack
};

/***/ }),
/* 60 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export AvailableTags */
/* harmony export (immutable) */ __webpack_exports__["a"] = findTagsInObjectURL;
/* unused harmony export base64ToArrayBuffer */
/* unused harmony export findTagsInBuffer */
// Scraped from https://github.com/exif-js/exif-js

var ExifTags = { 0x0112: "orientation" };
var AvailableTags = Object.keys(ExifTags).map(function (key) {
    return ExifTags[key];
});

function findTagsInObjectURL(src) {
    var tags = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : AvailableTags;

    if (/^blob\:/i.test(src)) {
        return objectURLToBlob(src).then(readToBuffer).then(function (buffer) {
            return findTagsInBuffer(buffer, tags);
        });
    }
    return Promise.resolve(null);
}

function base64ToArrayBuffer(dataUrl) {
    var base64 = dataUrl.replace(/^data\:([^\;]+)\;base64,/gmi, ''),
        binary = atob(base64),
        len = binary.length,
        buffer = new ArrayBuffer(len),
        view = new Uint8Array(buffer);

    for (var i = 0; i < len; i++) {
        view[i] = binary.charCodeAt(i);
    }
    return buffer;
}

function readToBuffer(blob) {
    return new Promise(function (resolve) {
        var fileReader = new FileReader();
        fileReader.onload = function (e) {
            return resolve(e.target.result);
        };
        fileReader.readAsArrayBuffer(blob);
    });
}

function objectURLToBlob(url) {
    return new Promise(function (resolve, reject) {
        var http = new XMLHttpRequest();
        http.open("GET", url, true);
        http.responseType = "blob";
        http.onreadystatechange = function () {
            if (http.readyState === XMLHttpRequest.DONE && (http.status === 200 || http.status === 0)) {
                resolve(this.response);
            }
        };
        http.onerror = reject;
        http.send();
    });
}

function findTagsInBuffer(file) {
    var selectedTags = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : AvailableTags;

    var dataView = new DataView(file),
        length = file.byteLength,
        exifTags = selectedTags.reduce(function (result, selectedTag) {
        var exifTag = Object.keys(ExifTags).filter(function (tag) {
            return ExifTags[tag] === selectedTag;
        })[0];
        if (exifTag) {
            result[exifTag] = selectedTag;
        }
        return result;
    }, {});
    var offset = 2,
        marker = void 0;

    if (dataView.getUint8(0) !== 0xFF || dataView.getUint8(1) !== 0xD8) {
        return false;
    }

    while (offset < length) {
        if (dataView.getUint8(offset) !== 0xFF) {
            return false;
        }

        marker = dataView.getUint8(offset + 1);
        if (marker === 0xE1) {
            return readEXIFData(dataView, offset + 4, exifTags);
        } else {
            offset += 2 + dataView.getUint16(offset + 2);
        }
    }
}

function readEXIFData(file, start, exifTags) {
    if (getStringFromBuffer(file, start, 4) !== "Exif") {
        return false;
    }

    var tiffOffset = start + 6;
    var bigEnd = void 0,
        tags = void 0;

    if (file.getUint16(tiffOffset) === 0x4949) {
        bigEnd = false;
    } else if (file.getUint16(tiffOffset) === 0x4D4D) {
        bigEnd = true;
    } else {
        return false;
    }

    if (file.getUint16(tiffOffset + 2, !bigEnd) !== 0x002A) {
        return false;
    }

    var firstIFDOffset = file.getUint32(tiffOffset + 4, !bigEnd);
    if (firstIFDOffset < 0x00000008) {
        return false;
    }

    tags = readTags(file, tiffOffset, tiffOffset + firstIFDOffset, exifTags, bigEnd);
    return tags;
}

function readTags(file, tiffStart, dirStart, strings, bigEnd) {
    var entries = file.getUint16(dirStart, !bigEnd),
        tags = {};

    for (var i = 0; i < entries; i++) {
        var entryOffset = dirStart + i * 12 + 2,
            tag = strings[file.getUint16(entryOffset, !bigEnd)];
        if (tag) {
            tags[tag] = readTagValue(file, entryOffset, tiffStart, dirStart, bigEnd);
        }
    }
    return tags;
}

function readTagValue(file, entryOffset, tiffStart, dirStart, bigEnd) {
    var type = file.getUint16(entryOffset + 2, !bigEnd),
        numValues = file.getUint32(entryOffset + 4, !bigEnd);

    switch (type) {
        case 3:
            if (numValues === 1) {
                return file.getUint16(entryOffset + 8, !bigEnd);
            }
    }
}

function getStringFromBuffer(buffer, start, length) {
    var outstr = "";
    for (var n = start; n < start + length; n++) {
        outstr += String.fromCharCode(buffer.getUint8(n));
    }
    return outstr;
}

/***/ }),
/* 61 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__common_cv_utils__ = __webpack_require__(19);


var TO_RADIANS = Math.PI / 180;

function adjustCanvasSize(canvas, targetSize) {
    if (canvas.width !== targetSize.x) {
        if (true) {
            console.log("WARNING: canvas-size needs to be adjusted");
        }
        canvas.width = targetSize.x;
    }
    if (canvas.height !== targetSize.y) {
        if (true) {
            console.log("WARNING: canvas-size needs to be adjusted");
        }
        canvas.height = targetSize.y;
    }
}

var FrameGrabber = {};

FrameGrabber.create = function (inputStream, canvas) {
    var _that = {},
        _streamConfig = inputStream.getConfig(),
        _video_size = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__common_cv_utils__["b" /* imageRef */])(inputStream.getRealWidth(), inputStream.getRealHeight()),
        _canvasSize = inputStream.getCanvasSize(),
        _size = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__common_cv_utils__["b" /* imageRef */])(inputStream.getWidth(), inputStream.getHeight()),
        topRight = inputStream.getTopRight(),
        _sx = topRight.x,
        _sy = topRight.y,
        _canvas,
        _ctx = null,
        _data = null;

    _canvas = canvas ? canvas : document.createElement("canvas");
    _canvas.width = _canvasSize.x;
    _canvas.height = _canvasSize.y;
    _ctx = _canvas.getContext("2d");
    _data = new Uint8Array(_size.x * _size.y);
    if (true) {
        console.log("FrameGrabber", JSON.stringify({
            size: _size,
            topRight: topRight,
            videoSize: _video_size,
            canvasSize: _canvasSize
        }));
    }

    /**
     * Uses the given array as frame-buffer
     */
    _that.attachData = function (data) {
        _data = data;
    };

    /**
     * Returns the used frame-buffer
     */
    _that.getData = function () {
        return _data;
    };

    /**
     * Fetches a frame from the input-stream and puts into the frame-buffer.
     * The image-data is converted to gray-scale and then half-sampled if configured.
     */
    _that.grab = function () {
        var doHalfSample = _streamConfig.halfSample,
            frame = inputStream.getFrame(),
            drawable = frame,
            drawAngle = 0,
            ctxData;
        if (drawable) {
            adjustCanvasSize(_canvas, _canvasSize);
            if (_streamConfig.type === 'ImageStream') {
                drawable = frame.img;
                if (frame.tags && frame.tags.orientation) {
                    switch (frame.tags.orientation) {
                        case 6:
                            drawAngle = 90 * TO_RADIANS;
                            break;
                        case 8:
                            drawAngle = -90 * TO_RADIANS;
                            break;
                    }
                }
            }

            if (drawAngle !== 0) {
                _ctx.translate(_canvasSize.x / 2, _canvasSize.y / 2);
                _ctx.rotate(drawAngle);
                _ctx.drawImage(drawable, -_canvasSize.y / 2, -_canvasSize.x / 2, _canvasSize.y, _canvasSize.x);
                _ctx.rotate(-drawAngle);
                _ctx.translate(-_canvasSize.x / 2, -_canvasSize.y / 2);
            } else {
                _ctx.drawImage(drawable, 0, 0, _canvasSize.x, _canvasSize.y);
            }

            ctxData = _ctx.getImageData(_sx, _sy, _size.x, _size.y).data;
            if (doHalfSample) {
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__common_cv_utils__["c" /* grayAndHalfSampleFromCanvasData */])(ctxData, _size, _data);
            } else {
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__common_cv_utils__["d" /* computeGray */])(ctxData, _data, _streamConfig);
            }
            return true;
        } else {
            return false;
        }
    };

    _that.getSize = function () {
        return _size;
    };

    return _that;
};

/* harmony default export */ __webpack_exports__["a"] = FrameGrabber;

/***/ }),
/* 62 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__exif_helper__ = __webpack_require__(60);


var ImageLoader = {};
ImageLoader.load = function (directory, callback, offset, size, sequence) {
    var htmlImagesSrcArray = new Array(size),
        htmlImagesArray = new Array(htmlImagesSrcArray.length),
        i,
        img,
        num;

    if (sequence === false) {
        htmlImagesSrcArray[0] = directory;
    } else {
        for (i = 0; i < htmlImagesSrcArray.length; i++) {
            num = offset + i;
            htmlImagesSrcArray[i] = directory + "image-" + ("00" + num).slice(-3) + ".jpg";
        }
    }
    htmlImagesArray.notLoaded = [];
    htmlImagesArray.addImage = function (image) {
        htmlImagesArray.notLoaded.push(image);
    };
    htmlImagesArray.loaded = function (loadedImg) {
        var notloadedImgs = htmlImagesArray.notLoaded;
        for (var x = 0; x < notloadedImgs.length; x++) {
            if (notloadedImgs[x] === loadedImg) {
                notloadedImgs.splice(x, 1);
                for (var y = 0; y < htmlImagesSrcArray.length; y++) {
                    var imgName = htmlImagesSrcArray[y].substr(htmlImagesSrcArray[y].lastIndexOf("/"));
                    if (loadedImg.src.lastIndexOf(imgName) !== -1) {
                        htmlImagesArray[y] = { img: loadedImg };
                        break;
                    }
                }
                break;
            }
        }
        if (notloadedImgs.length === 0) {
            if (true) {
                console.log("Images loaded");
            }
            if (sequence === false) {
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__exif_helper__["a" /* findTagsInObjectURL */])(directory, ['orientation']).then(function (tags) {
                    htmlImagesArray[0].tags = tags;
                    callback(htmlImagesArray);
                }).catch(function (e) {
                    console.log(e);
                    callback(htmlImagesArray);
                });
            } else {
                callback(htmlImagesArray);
            }
        }
    };

    for (i = 0; i < htmlImagesSrcArray.length; i++) {
        img = new Image();
        htmlImagesArray.addImage(img);
        addOnloadHandler(img, htmlImagesArray);
        img.src = htmlImagesSrcArray[i];
    }
};

function addOnloadHandler(img, htmlImagesArray) {
    img.onload = function () {
        htmlImagesArray.loaded(this);
    };
}

/* harmony default export */ __webpack_exports__["a"] = ImageLoader;

/***/ }),
/* 63 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__image_loader__ = __webpack_require__(62);


var InputStream = {};
InputStream.createVideoStream = function (video) {
    var that = {},
        _config = null,
        _eventNames = ['canrecord', 'ended'],
        _eventHandlers = {},
        _calculatedWidth,
        _calculatedHeight,
        _topRight = { x: 0, y: 0 },
        _canvasSize = { x: 0, y: 0 };

    function initSize() {
        var width = video.videoWidth,
            height = video.videoHeight;

        _calculatedWidth = _config.size ? width / height > 1 ? _config.size : Math.floor(width / height * _config.size) : width;
        _calculatedHeight = _config.size ? width / height > 1 ? Math.floor(height / width * _config.size) : _config.size : height;

        _canvasSize.x = _calculatedWidth;
        _canvasSize.y = _calculatedHeight;
    }

    that.getRealWidth = function () {
        return video.videoWidth;
    };

    that.getRealHeight = function () {
        return video.videoHeight;
    };

    that.getWidth = function () {
        return _calculatedWidth;
    };

    that.getHeight = function () {
        return _calculatedHeight;
    };

    that.setWidth = function (width) {
        _calculatedWidth = width;
    };

    that.setHeight = function (height) {
        _calculatedHeight = height;
    };

    that.setInputStream = function (config) {
        _config = config;
        video.src = typeof config.src !== 'undefined' ? config.src : '';
    };

    that.ended = function () {
        return video.ended;
    };

    that.getConfig = function () {
        return _config;
    };

    that.setAttribute = function (name, value) {
        video.setAttribute(name, value);
    };

    that.pause = function () {
        video.pause();
    };

    that.play = function () {
        video.play();
    };

    that.setCurrentTime = function (time) {
        if (_config.type !== "LiveStream") {
            video.currentTime = time;
        }
    };

    that.addEventListener = function (event, f, bool) {
        if (_eventNames.indexOf(event) !== -1) {
            if (!_eventHandlers[event]) {
                _eventHandlers[event] = [];
            }
            _eventHandlers[event].push(f);
        } else {
            video.addEventListener(event, f, bool);
        }
    };

    that.clearEventHandlers = function () {
        _eventNames.forEach(function (eventName) {
            var handlers = _eventHandlers[eventName];
            if (handlers && handlers.length > 0) {
                handlers.forEach(function (handler) {
                    video.removeEventListener(eventName, handler);
                });
            }
        });
    };

    that.trigger = function (eventName, args) {
        var j,
            handlers = _eventHandlers[eventName];

        if (eventName === 'canrecord') {
            initSize();
        }
        if (handlers && handlers.length > 0) {
            for (j = 0; j < handlers.length; j++) {
                handlers[j].apply(that, args);
            }
        }
    };

    that.setTopRight = function (topRight) {
        _topRight.x = topRight.x;
        _topRight.y = topRight.y;
    };

    that.getTopRight = function () {
        return _topRight;
    };

    that.setCanvasSize = function (size) {
        _canvasSize.x = size.x;
        _canvasSize.y = size.y;
    };

    that.getCanvasSize = function () {
        return _canvasSize;
    };

    that.getFrame = function () {
        return video;
    };

    return that;
};

InputStream.createLiveStream = function (video) {
    video.setAttribute("autoplay", true);
    var that = InputStream.createVideoStream(video);

    that.ended = function () {
        return false;
    };

    return that;
};

InputStream.createImageStream = function () {
    var that = {};
    var _config = null;

    var width = 0,
        height = 0,
        frameIdx = 0,
        paused = true,
        loaded = false,
        imgArray = null,
        size = 0,
        offset = 1,
        baseUrl = null,
        ended = false,
        calculatedWidth,
        calculatedHeight,
        _eventNames = ['canrecord', 'ended'],
        _eventHandlers = {},
        _topRight = { x: 0, y: 0 },
        _canvasSize = { x: 0, y: 0 };

    function loadImages() {
        loaded = false;
        __WEBPACK_IMPORTED_MODULE_0__image_loader__["a" /* default */].load(baseUrl, function (imgs) {
            imgArray = imgs;
            if (imgs[0].tags && imgs[0].tags.orientation) {
                switch (imgs[0].tags.orientation) {
                    case 6:
                    case 8:
                        width = imgs[0].img.height;
                        height = imgs[0].img.width;
                        break;
                    default:
                        width = imgs[0].img.width;
                        height = imgs[0].img.height;
                }
            } else {
                width = imgs[0].img.width;
                height = imgs[0].img.height;
            }
            calculatedWidth = _config.size ? width / height > 1 ? _config.size : Math.floor(width / height * _config.size) : width;
            calculatedHeight = _config.size ? width / height > 1 ? Math.floor(height / width * _config.size) : _config.size : height;
            _canvasSize.x = calculatedWidth;
            _canvasSize.y = calculatedHeight;
            loaded = true;
            frameIdx = 0;
            setTimeout(function () {
                publishEvent("canrecord", []);
            }, 0);
        }, offset, size, _config.sequence);
    }

    function publishEvent(eventName, args) {
        var j,
            handlers = _eventHandlers[eventName];

        if (handlers && handlers.length > 0) {
            for (j = 0; j < handlers.length; j++) {
                handlers[j].apply(that, args);
            }
        }
    }

    that.trigger = publishEvent;

    that.getWidth = function () {
        return calculatedWidth;
    };

    that.getHeight = function () {
        return calculatedHeight;
    };

    that.setWidth = function (newWidth) {
        calculatedWidth = newWidth;
    };

    that.setHeight = function (newHeight) {
        calculatedHeight = newHeight;
    };

    that.getRealWidth = function () {
        return width;
    };

    that.getRealHeight = function () {
        return height;
    };

    that.setInputStream = function (stream) {
        _config = stream;
        if (stream.sequence === false) {
            baseUrl = stream.src;
            size = 1;
        } else {
            baseUrl = stream.src;
            size = stream.length;
        }
        loadImages();
    };

    that.ended = function () {
        return ended;
    };

    that.setAttribute = function () {};

    that.getConfig = function () {
        return _config;
    };

    that.pause = function () {
        paused = true;
    };

    that.play = function () {
        paused = false;
    };

    that.setCurrentTime = function (time) {
        frameIdx = time;
    };

    that.addEventListener = function (event, f) {
        if (_eventNames.indexOf(event) !== -1) {
            if (!_eventHandlers[event]) {
                _eventHandlers[event] = [];
            }
            _eventHandlers[event].push(f);
        }
    };

    that.setTopRight = function (topRight) {
        _topRight.x = topRight.x;
        _topRight.y = topRight.y;
    };

    that.getTopRight = function () {
        return _topRight;
    };

    that.setCanvasSize = function (canvasSize) {
        _canvasSize.x = canvasSize.x;
        _canvasSize.y = canvasSize.y;
    };

    that.getCanvasSize = function () {
        return _canvasSize;
    };

    that.getFrame = function () {
        var frame;

        if (!loaded) {
            return null;
        }
        if (!paused) {
            frame = imgArray[frameIdx];
            if (frameIdx < size - 1) {
                frameIdx++;
            } else {
                setTimeout(function () {
                    ended = true;
                    publishEvent("ended", []);
                }, 0);
            }
        }
        return frame;
    };

    return that;
};

/* harmony default export */ __webpack_exports__["a"] = InputStream;

/***/ }),
/* 64 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_cv_utils__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_array_helper__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__common_image_debug__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__rasterizer__ = __webpack_require__(65);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__tracer__ = __webpack_require__(30);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__skeletonizer__ = __webpack_require__(66);







var vec2 = {
    clone: __webpack_require__(7),
    dot: __webpack_require__(32),
    scale: __webpack_require__(81),
    transformMat2: __webpack_require__(82)
};
var mat2 = {
    copy: __webpack_require__(78),
    create: __webpack_require__(79),
    invert: __webpack_require__(80)
};

var _config,
    _currentImageWrapper,
    _skelImageWrapper,
    _subImageWrapper,
    _labelImageWrapper,
    _patchGrid,
    _patchLabelGrid,
    _imageToPatchGrid,
    _binaryImageWrapper,
    _patchSize,
    _canvasContainer = {
    ctx: {
        binary: null
    },
    dom: {
        binary: null
    }
},
    _numPatches = { x: 0, y: 0 },
    _inputImageWrapper,
    _skeletonizer;

function initBuffers() {
    var skeletonImageData;

    if (_config.halfSample) {
        _currentImageWrapper = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */]({
            x: _inputImageWrapper.size.x / 2 | 0,
            y: _inputImageWrapper.size.y / 2 | 0
        });
    } else {
        _currentImageWrapper = _inputImageWrapper;
    }

    _patchSize = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["e" /* calculatePatchSize */])(_config.patchSize, _currentImageWrapper.size);

    _numPatches.x = _currentImageWrapper.size.x / _patchSize.x | 0;
    _numPatches.y = _currentImageWrapper.size.y / _patchSize.y | 0;

    _binaryImageWrapper = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_currentImageWrapper.size, undefined, Uint8Array, false);

    _labelImageWrapper = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_patchSize, undefined, Array, true);

    skeletonImageData = new ArrayBuffer(64 * 1024);
    _subImageWrapper = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_patchSize, new Uint8Array(skeletonImageData, 0, _patchSize.x * _patchSize.y));
    _skelImageWrapper = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_patchSize, new Uint8Array(skeletonImageData, _patchSize.x * _patchSize.y * 3, _patchSize.x * _patchSize.y), undefined, true);
    _skeletonizer = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_6__skeletonizer__["a" /* default */])(typeof window !== 'undefined' ? window : typeof self !== 'undefined' ? self : global, {
        size: _patchSize.x
    }, skeletonImageData);

    _imageToPatchGrid = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */]({
        x: _currentImageWrapper.size.x / _subImageWrapper.size.x | 0,
        y: _currentImageWrapper.size.y / _subImageWrapper.size.y | 0
    }, undefined, Array, true);
    _patchGrid = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_imageToPatchGrid.size, undefined, undefined, true);
    _patchLabelGrid = new __WEBPACK_IMPORTED_MODULE_0__common_image_wrapper__["a" /* default */](_imageToPatchGrid.size, undefined, Int32Array, true);
}

function initCanvas() {
    if (_config.useWorker || typeof document === 'undefined') {
        return;
    }
    _canvasContainer.dom.binary = document.createElement("canvas");
    _canvasContainer.dom.binary.className = "binaryBuffer";
    if (true && _config.debug.showCanvas === true) {
        document.querySelector("#debug").appendChild(_canvasContainer.dom.binary);
    }
    _canvasContainer.ctx.binary = _canvasContainer.dom.binary.getContext("2d");
    _canvasContainer.dom.binary.width = _binaryImageWrapper.size.x;
    _canvasContainer.dom.binary.height = _binaryImageWrapper.size.y;
}

/**
 * Creates a bounding box which encloses all the given patches
 * @returns {Array} The minimal bounding box
 */
function boxFromPatches(patches) {
    var overAvg,
        i,
        j,
        patch,
        transMat,
        minx = _binaryImageWrapper.size.x,
        miny = _binaryImageWrapper.size.y,
        maxx = -_binaryImageWrapper.size.x,
        maxy = -_binaryImageWrapper.size.y,
        box,
        scale;

    // draw all patches which are to be taken into consideration
    overAvg = 0;
    for (i = 0; i < patches.length; i++) {
        patch = patches[i];
        overAvg += patch.rad;
        if (true && _config.debug.showPatches) {
            __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawRect(patch.pos, _subImageWrapper.size, _canvasContainer.ctx.binary, { color: "red" });
        }
    }

    overAvg /= patches.length;
    overAvg = (overAvg * 180 / Math.PI + 90) % 180 - 90;
    if (overAvg < 0) {
        overAvg += 180;
    }

    overAvg = (180 - overAvg) * Math.PI / 180;
    transMat = mat2.copy(mat2.create(), [Math.cos(overAvg), Math.sin(overAvg), -Math.sin(overAvg), Math.cos(overAvg)]);

    // iterate over patches and rotate by angle
    for (i = 0; i < patches.length; i++) {
        patch = patches[i];
        for (j = 0; j < 4; j++) {
            vec2.transformMat2(patch.box[j], patch.box[j], transMat);
        }

        if (true && _config.debug.boxFromPatches.showTransformed) {
            __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawPath(patch.box, { x: 0, y: 1 }, _canvasContainer.ctx.binary, { color: '#99ff00', lineWidth: 2 });
        }
    }

    // find bounding box
    for (i = 0; i < patches.length; i++) {
        patch = patches[i];
        for (j = 0; j < 4; j++) {
            if (patch.box[j][0] < minx) {
                minx = patch.box[j][0];
            }
            if (patch.box[j][0] > maxx) {
                maxx = patch.box[j][0];
            }
            if (patch.box[j][1] < miny) {
                miny = patch.box[j][1];
            }
            if (patch.box[j][1] > maxy) {
                maxy = patch.box[j][1];
            }
        }
    }

    box = [[minx, miny], [maxx, miny], [maxx, maxy], [minx, maxy]];

    if (true && _config.debug.boxFromPatches.showTransformedBox) {
        __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawPath(box, { x: 0, y: 1 }, _canvasContainer.ctx.binary, { color: '#ff0000', lineWidth: 2 });
    }

    scale = _config.halfSample ? 2 : 1;
    // reverse rotation;
    transMat = mat2.invert(transMat, transMat);
    for (j = 0; j < 4; j++) {
        vec2.transformMat2(box[j], box[j], transMat);
    }

    if (true && _config.debug.boxFromPatches.showBB) {
        __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawPath(box, { x: 0, y: 1 }, _canvasContainer.ctx.binary, { color: '#ff0000', lineWidth: 2 });
    }

    for (j = 0; j < 4; j++) {
        vec2.scale(box[j], box[j], scale);
    }

    return box;
}

/**
 * Creates a binary image of the current image
 */
function binarizeImage() {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["f" /* otsuThreshold */])(_currentImageWrapper, _binaryImageWrapper);
    _binaryImageWrapper.zeroBorder();
    if (true && _config.debug.showCanvas) {
        _binaryImageWrapper.show(_canvasContainer.dom.binary, 255);
    }
}

/**
 * Iterate over the entire image
 * extract patches
 */
function findPatches() {
    var i,
        j,
        x,
        y,
        moments,
        patchesFound = [],
        rasterizer,
        rasterResult,
        patch;
    for (i = 0; i < _numPatches.x; i++) {
        for (j = 0; j < _numPatches.y; j++) {
            x = _subImageWrapper.size.x * i;
            y = _subImageWrapper.size.y * j;

            // seperate parts
            skeletonize(x, y);

            // Rasterize, find individual bars
            _skelImageWrapper.zeroBorder();
            __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(_labelImageWrapper.data, 0);
            rasterizer = __WEBPACK_IMPORTED_MODULE_4__rasterizer__["a" /* default */].create(_skelImageWrapper, _labelImageWrapper);
            rasterResult = rasterizer.rasterize(0);

            if (true && _config.debug.showLabels) {
                _labelImageWrapper.overlay(_canvasContainer.dom.binary, Math.floor(360 / rasterResult.count), { x: x, y: y });
            }

            // calculate moments from the skeletonized patch
            moments = _labelImageWrapper.moments(rasterResult.count);

            // extract eligible patches
            patchesFound = patchesFound.concat(describePatch(moments, [i, j], x, y));
        }
    }

    if (true && _config.debug.showFoundPatches) {
        for (i = 0; i < patchesFound.length; i++) {
            patch = patchesFound[i];
            __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawRect(patch.pos, _subImageWrapper.size, _canvasContainer.ctx.binary, { color: "#99ff00", lineWidth: 2 });
        }
    }

    return patchesFound;
}

/**
 * Finds those connected areas which contain at least 6 patches
 * and returns them ordered DESC by the number of contained patches
 * @param {Number} maxLabel
 */
function findBiggestConnectedAreas(maxLabel) {
    var i,
        sum,
        labelHist = [],
        topLabels = [];

    for (i = 0; i < maxLabel; i++) {
        labelHist.push(0);
    }
    sum = _patchLabelGrid.data.length;
    while (sum--) {
        if (_patchLabelGrid.data[sum] > 0) {
            labelHist[_patchLabelGrid.data[sum] - 1]++;
        }
    }

    labelHist = labelHist.map(function (val, idx) {
        return {
            val: val,
            label: idx + 1
        };
    });

    labelHist.sort(function (a, b) {
        return b.val - a.val;
    });

    // extract top areas with at least 6 patches present
    topLabels = labelHist.filter(function (el) {
        return el.val >= 5;
    });

    return topLabels;
}

/**
 *
 */
function findBoxes(topLabels, maxLabel) {
    var i,
        j,
        sum,
        patches = [],
        patch,
        box,
        boxes = [],
        hsv = [0, 1, 1],
        rgb = [0, 0, 0];

    for (i = 0; i < topLabels.length; i++) {
        sum = _patchLabelGrid.data.length;
        patches.length = 0;
        while (sum--) {
            if (_patchLabelGrid.data[sum] === topLabels[i].label) {
                patch = _imageToPatchGrid.data[sum];
                patches.push(patch);
            }
        }
        box = boxFromPatches(patches);
        if (box) {
            boxes.push(box);

            // draw patch-labels if requested
            if (true && _config.debug.showRemainingPatchLabels) {
                for (j = 0; j < patches.length; j++) {
                    patch = patches[j];
                    hsv[0] = topLabels[i].label / (maxLabel + 1) * 360;
                    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["a" /* hsv2rgb */])(hsv, rgb);
                    __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawRect(patch.pos, _subImageWrapper.size, _canvasContainer.ctx.binary, { color: "rgb(" + rgb.join(",") + ")", lineWidth: 2 });
                }
            }
        }
    }
    return boxes;
}

/**
 * Find similar moments (via cluster)
 * @param {Object} moments
 */
function similarMoments(moments) {
    var clusters = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["g" /* cluster */])(moments, 0.90);
    var topCluster = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["h" /* topGeneric */])(clusters, 1, function (e) {
        return e.getPoints().length;
    });
    var points = [],
        result = [];
    if (topCluster.length === 1) {
        points = topCluster[0].item.getPoints();
        for (var i = 0; i < points.length; i++) {
            result.push(points[i].point);
        }
    }
    return result;
}

function skeletonize(x, y) {
    _binaryImageWrapper.subImageAsCopy(_subImageWrapper, __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["b" /* imageRef */])(x, y));
    _skeletonizer.skeletonize();

    // Show skeleton if requested
    if (true && _config.debug.showSkeleton) {
        _skelImageWrapper.overlay(_canvasContainer.dom.binary, 360, __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["b" /* imageRef */])(x, y));
    }
}

/**
 * Extracts and describes those patches which seem to contain a barcode pattern
 * @param {Array} moments
 * @param {Object} patchPos,
 * @param {Number} x
 * @param {Number} y
 * @returns {Array} list of patches
 */
function describePatch(moments, patchPos, x, y) {
    var k,
        avg,
        eligibleMoments = [],
        matchingMoments,
        patch,
        patchesFound = [],
        minComponentWeight = Math.ceil(_patchSize.x / 3);

    if (moments.length >= 2) {
        // only collect moments which's area covers at least minComponentWeight pixels.
        for (k = 0; k < moments.length; k++) {
            if (moments[k].m00 > minComponentWeight) {
                eligibleMoments.push(moments[k]);
            }
        }

        // if at least 2 moments are found which have at least minComponentWeights covered
        if (eligibleMoments.length >= 2) {
            matchingMoments = similarMoments(eligibleMoments);
            avg = 0;
            // determine the similarity of the moments
            for (k = 0; k < matchingMoments.length; k++) {
                avg += matchingMoments[k].rad;
            }

            // Only two of the moments are allowed not to fit into the equation
            // add the patch to the set
            if (matchingMoments.length > 1 && matchingMoments.length >= eligibleMoments.length / 4 * 3 && matchingMoments.length > moments.length / 4) {
                avg /= matchingMoments.length;
                patch = {
                    index: patchPos[1] * _numPatches.x + patchPos[0],
                    pos: {
                        x: x,
                        y: y
                    },
                    box: [vec2.clone([x, y]), vec2.clone([x + _subImageWrapper.size.x, y]), vec2.clone([x + _subImageWrapper.size.x, y + _subImageWrapper.size.y]), vec2.clone([x, y + _subImageWrapper.size.y])],
                    moments: matchingMoments,
                    rad: avg,
                    vec: vec2.clone([Math.cos(avg), Math.sin(avg)])
                };
                patchesFound.push(patch);
            }
        }
    }
    return patchesFound;
}

/**
 * finds patches which are connected and share the same orientation
 * @param {Object} patchesFound
 */
function rasterizeAngularSimilarity(patchesFound) {
    var label = 0,
        threshold = 0.95,
        currIdx = 0,
        j,
        patch,
        hsv = [0, 1, 1],
        rgb = [0, 0, 0];

    function notYetProcessed() {
        var i;
        for (i = 0; i < _patchLabelGrid.data.length; i++) {
            if (_patchLabelGrid.data[i] === 0 && _patchGrid.data[i] === 1) {
                return i;
            }
        }
        return _patchLabelGrid.length;
    }

    function trace(currentIdx) {
        var x,
            y,
            currentPatch,
            idx,
            dir,
            current = {
            x: currentIdx % _patchLabelGrid.size.x,
            y: currentIdx / _patchLabelGrid.size.x | 0
        },
            similarity;

        if (currentIdx < _patchLabelGrid.data.length) {
            currentPatch = _imageToPatchGrid.data[currentIdx];
            // assign label
            _patchLabelGrid.data[currentIdx] = label;
            for (dir = 0; dir < __WEBPACK_IMPORTED_MODULE_5__tracer__["a" /* default */].searchDirections.length; dir++) {
                y = current.y + __WEBPACK_IMPORTED_MODULE_5__tracer__["a" /* default */].searchDirections[dir][0];
                x = current.x + __WEBPACK_IMPORTED_MODULE_5__tracer__["a" /* default */].searchDirections[dir][1];
                idx = y * _patchLabelGrid.size.x + x;

                // continue if patch empty
                if (_patchGrid.data[idx] === 0) {
                    _patchLabelGrid.data[idx] = Number.MAX_VALUE;
                    continue;
                }

                if (_patchLabelGrid.data[idx] === 0) {
                    similarity = Math.abs(vec2.dot(_imageToPatchGrid.data[idx].vec, currentPatch.vec));
                    if (similarity > threshold) {
                        trace(idx);
                    }
                }
            }
        }
    }

    // prepare for finding the right patches
    __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(_patchGrid.data, 0);
    __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(_patchLabelGrid.data, 0);
    __WEBPACK_IMPORTED_MODULE_2__common_array_helper__["a" /* default */].init(_imageToPatchGrid.data, null);

    for (j = 0; j < patchesFound.length; j++) {
        patch = patchesFound[j];
        _imageToPatchGrid.data[patch.index] = patch;
        _patchGrid.data[patch.index] = 1;
    }

    // rasterize the patches found to determine area
    _patchGrid.zeroBorder();

    while ((currIdx = notYetProcessed()) < _patchLabelGrid.data.length) {
        label++;
        trace(currIdx);
    }

    // draw patch-labels if requested
    if (true && _config.debug.showPatchLabels) {
        for (j = 0; j < _patchLabelGrid.data.length; j++) {
            if (_patchLabelGrid.data[j] > 0 && _patchLabelGrid.data[j] <= label) {
                patch = _imageToPatchGrid.data[j];
                hsv[0] = _patchLabelGrid.data[j] / (label + 1) * 360;
                __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["a" /* hsv2rgb */])(hsv, rgb);
                __WEBPACK_IMPORTED_MODULE_3__common_image_debug__["a" /* default */].drawRect(patch.pos, _subImageWrapper.size, _canvasContainer.ctx.binary, { color: "rgb(" + rgb.join(",") + ")", lineWidth: 2 });
            }
        }
    }

    return label;
}

/* harmony default export */ __webpack_exports__["a"] = {
    init: function init(inputImageWrapper, config) {
        _config = config;
        _inputImageWrapper = inputImageWrapper;

        initBuffers();
        initCanvas();
    },

    locate: function locate() {
        var patchesFound, topLabels, boxes;

        if (_config.halfSample) {
            __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["i" /* halfSample */])(_inputImageWrapper, _currentImageWrapper);
        }

        binarizeImage();
        patchesFound = findPatches();
        // return unless 5% or more patches are found
        if (patchesFound.length < _numPatches.x * _numPatches.y * 0.05) {
            return null;
        }

        // rasterrize area by comparing angular similarity;
        var maxLabel = rasterizeAngularSimilarity(patchesFound);
        if (maxLabel < 1) {
            return null;
        }

        // search for area with the most patches (biggest connected area)
        topLabels = findBiggestConnectedAreas(maxLabel);
        if (topLabels.length === 0) {
            return null;
        }

        boxes = findBoxes(topLabels, maxLabel);
        return boxes;
    },

    checkImageConstraints: function checkImageConstraints(inputStream, config) {
        var patchSize,
            width = inputStream.getWidth(),
            height = inputStream.getHeight(),
            halfSample = config.halfSample ? 0.5 : 1,
            size,
            area;

        // calculate width and height based on area
        if (inputStream.getConfig().area) {
            area = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["j" /* computeImageArea */])(width, height, inputStream.getConfig().area);
            inputStream.setTopRight({ x: area.sx, y: area.sy });
            inputStream.setCanvasSize({ x: width, y: height });
            width = area.sw;
            height = area.sh;
        }

        size = {
            x: Math.floor(width * halfSample),
            y: Math.floor(height * halfSample)
        };

        patchSize = __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__common_cv_utils__["e" /* calculatePatchSize */])(config.patchSize, size);
        if (true) {
            console.log("Patch-Size: " + JSON.stringify(patchSize));
        }

        inputStream.setWidth(Math.floor(Math.floor(size.x / patchSize.x) * (1 / halfSample) * patchSize.x));
        inputStream.setHeight(Math.floor(Math.floor(size.y / patchSize.y) * (1 / halfSample) * patchSize.y));

        if (inputStream.getWidth() % patchSize.x === 0 && inputStream.getHeight() % patchSize.y === 0) {
            return true;
        }

        throw new Error("Image dimensions do not comply with the current settings: Width (" + width + " )and height (" + height + ") must a multiple of " + patchSize.x);
    }
};
/* WEBPACK VAR INJECTION */}.call(__webpack_exports__, __webpack_require__(47)))

/***/ }),
/* 65 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__tracer__ = __webpack_require__(30);


/**
 * http://www.codeproject.com/Tips/407172/Connected-Component-Labeling-and-Vectorization
 */
var Rasterizer = {
    createContour2D: function createContour2D() {
        return {
            dir: null,
            index: null,
            firstVertex: null,
            insideContours: null,
            nextpeer: null,
            prevpeer: null
        };
    },
    CONTOUR_DIR: {
        CW_DIR: 0,
        CCW_DIR: 1,
        UNKNOWN_DIR: 2
    },
    DIR: {
        OUTSIDE_EDGE: -32767,
        INSIDE_EDGE: -32766
    },
    create: function create(imageWrapper, labelWrapper) {
        var imageData = imageWrapper.data,
            labelData = labelWrapper.data,
            width = imageWrapper.size.x,
            height = imageWrapper.size.y,
            tracer = __WEBPACK_IMPORTED_MODULE_0__tracer__["a" /* default */].create(imageWrapper, labelWrapper);

        return {
            rasterize: function rasterize(depthlabel) {
                var color,
                    bc,
                    lc,
                    labelindex,
                    cx,
                    cy,
                    colorMap = [],
                    vertex,
                    p,
                    cc,
                    sc,
                    pos,
                    connectedCount = 0,
                    i;

                for (i = 0; i < 400; i++) {
                    colorMap[i] = 0;
                }

                colorMap[0] = imageData[0];
                cc = null;
                for (cy = 1; cy < height - 1; cy++) {
                    labelindex = 0;
                    bc = colorMap[0];
                    for (cx = 1; cx < width - 1; cx++) {
                        pos = cy * width + cx;
                        if (labelData[pos] === 0) {
                            color = imageData[pos];
                            if (color !== bc) {
                                if (labelindex === 0) {
                                    lc = connectedCount + 1;
                                    colorMap[lc] = color;
                                    bc = color;
                                    vertex = tracer.contourTracing(cy, cx, lc, color, Rasterizer.DIR.OUTSIDE_EDGE);
                                    if (vertex !== null) {
                                        connectedCount++;
                                        labelindex = lc;
                                        p = Rasterizer.createContour2D();
                                        p.dir = Rasterizer.CONTOUR_DIR.CW_DIR;
                                        p.index = labelindex;
                                        p.firstVertex = vertex;
                                        p.nextpeer = cc;
                                        p.insideContours = null;
                                        if (cc !== null) {
                                            cc.prevpeer = p;
                                        }
                                        cc = p;
                                    }
                                } else {
                                    vertex = tracer.contourTracing(cy, cx, Rasterizer.DIR.INSIDE_EDGE, color, labelindex);
                                    if (vertex !== null) {
                                        p = Rasterizer.createContour2D();
                                        p.firstVertex = vertex;
                                        p.insideContours = null;
                                        if (depthlabel === 0) {
                                            p.dir = Rasterizer.CONTOUR_DIR.CCW_DIR;
                                        } else {
                                            p.dir = Rasterizer.CONTOUR_DIR.CW_DIR;
                                        }
                                        p.index = depthlabel;
                                        sc = cc;
                                        while (sc !== null && sc.index !== labelindex) {
                                            sc = sc.nextpeer;
                                        }
                                        if (sc !== null) {
                                            p.nextpeer = sc.insideContours;
                                            if (sc.insideContours !== null) {
                                                sc.insideContours.prevpeer = p;
                                            }
                                            sc.insideContours = p;
                                        }
                                    }
                                }
                            } else {
                                labelData[pos] = labelindex;
                            }
                        } else if (labelData[pos] === Rasterizer.DIR.OUTSIDE_EDGE || labelData[pos] === Rasterizer.DIR.INSIDE_EDGE) {
                            labelindex = 0;
                            if (labelData[pos] === Rasterizer.DIR.INSIDE_EDGE) {
                                bc = imageData[pos];
                            } else {
                                bc = colorMap[0];
                            }
                        } else {
                            labelindex = labelData[pos];
                            bc = colorMap[labelindex];
                        }
                    }
                }
                sc = cc;
                while (sc !== null) {
                    sc.index = depthlabel;
                    sc = sc.nextpeer;
                }
                return {
                    cc: cc,
                    count: connectedCount
                };
            },
            debug: {
                drawContour: function drawContour(canvas, firstContour) {
                    var ctx = canvas.getContext("2d"),
                        pq = firstContour,
                        iq,
                        q,
                        p;

                    ctx.strokeStyle = "red";
                    ctx.fillStyle = "red";
                    ctx.lineWidth = 1;

                    if (pq !== null) {
                        iq = pq.insideContours;
                    } else {
                        iq = null;
                    }

                    while (pq !== null) {
                        if (iq !== null) {
                            q = iq;
                            iq = iq.nextpeer;
                        } else {
                            q = pq;
                            pq = pq.nextpeer;
                            if (pq !== null) {
                                iq = pq.insideContours;
                            } else {
                                iq = null;
                            }
                        }

                        switch (q.dir) {
                            case Rasterizer.CONTOUR_DIR.CW_DIR:
                                ctx.strokeStyle = "red";
                                break;
                            case Rasterizer.CONTOUR_DIR.CCW_DIR:
                                ctx.strokeStyle = "blue";
                                break;
                            case Rasterizer.CONTOUR_DIR.UNKNOWN_DIR:
                                ctx.strokeStyle = "green";
                                break;
                        }

                        p = q.firstVertex;
                        ctx.beginPath();
                        ctx.moveTo(p.x, p.y);
                        do {
                            p = p.next;
                            ctx.lineTo(p.x, p.y);
                        } while (p !== q.firstVertex);
                        ctx.stroke();
                    }
                }
            }
        };
    }
};

/* harmony default export */ __webpack_exports__["a"] = Rasterizer;

/***/ }),
/* 66 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* @preserve ASM BEGIN */
/* eslint-disable eqeqeq*/
function Skeletonizer(stdlib, foreign, buffer) {
    "use asm";

    var images = new stdlib.Uint8Array(buffer),
        size = foreign.size | 0,
        imul = stdlib.Math.imul;

    function erode(inImagePtr, outImagePtr) {
        inImagePtr = inImagePtr | 0;
        outImagePtr = outImagePtr | 0;

        var v = 0,
            u = 0,
            sum = 0,
            yStart1 = 0,
            yStart2 = 0,
            xStart1 = 0,
            xStart2 = 0,
            offset = 0;

        for (v = 1; (v | 0) < (size - 1 | 0); v = v + 1 | 0) {
            offset = offset + size | 0;
            for (u = 1; (u | 0) < (size - 1 | 0); u = u + 1 | 0) {
                yStart1 = offset - size | 0;
                yStart2 = offset + size | 0;
                xStart1 = u - 1 | 0;
                xStart2 = u + 1 | 0;
                sum = (images[inImagePtr + yStart1 + xStart1 | 0] | 0) + (images[inImagePtr + yStart1 + xStart2 | 0] | 0) + (images[inImagePtr + offset + u | 0] | 0) + (images[inImagePtr + yStart2 + xStart1 | 0] | 0) + (images[inImagePtr + yStart2 + xStart2 | 0] | 0) | 0;
                if ((sum | 0) == (5 | 0)) {
                    images[outImagePtr + offset + u | 0] = 1;
                } else {
                    images[outImagePtr + offset + u | 0] = 0;
                }
            }
        }
        return;
    }

    function subtract(aImagePtr, bImagePtr, outImagePtr) {
        aImagePtr = aImagePtr | 0;
        bImagePtr = bImagePtr | 0;
        outImagePtr = outImagePtr | 0;

        var length = 0;

        length = imul(size, size) | 0;

        while ((length | 0) > 0) {
            length = length - 1 | 0;
            images[outImagePtr + length | 0] = (images[aImagePtr + length | 0] | 0) - (images[bImagePtr + length | 0] | 0) | 0;
        }
    }

    function bitwiseOr(aImagePtr, bImagePtr, outImagePtr) {
        aImagePtr = aImagePtr | 0;
        bImagePtr = bImagePtr | 0;
        outImagePtr = outImagePtr | 0;

        var length = 0;

        length = imul(size, size) | 0;

        while ((length | 0) > 0) {
            length = length - 1 | 0;
            images[outImagePtr + length | 0] = images[aImagePtr + length | 0] | 0 | (images[bImagePtr + length | 0] | 0) | 0;
        }
    }

    function countNonZero(imagePtr) {
        imagePtr = imagePtr | 0;

        var sum = 0,
            length = 0;

        length = imul(size, size) | 0;

        while ((length | 0) > 0) {
            length = length - 1 | 0;
            sum = (sum | 0) + (images[imagePtr + length | 0] | 0) | 0;
        }

        return sum | 0;
    }

    function init(imagePtr, value) {
        imagePtr = imagePtr | 0;
        value = value | 0;

        var length = 0;

        length = imul(size, size) | 0;

        while ((length | 0) > 0) {
            length = length - 1 | 0;
            images[imagePtr + length | 0] = value;
        }
    }

    function dilate(inImagePtr, outImagePtr) {
        inImagePtr = inImagePtr | 0;
        outImagePtr = outImagePtr | 0;

        var v = 0,
            u = 0,
            sum = 0,
            yStart1 = 0,
            yStart2 = 0,
            xStart1 = 0,
            xStart2 = 0,
            offset = 0;

        for (v = 1; (v | 0) < (size - 1 | 0); v = v + 1 | 0) {
            offset = offset + size | 0;
            for (u = 1; (u | 0) < (size - 1 | 0); u = u + 1 | 0) {
                yStart1 = offset - size | 0;
                yStart2 = offset + size | 0;
                xStart1 = u - 1 | 0;
                xStart2 = u + 1 | 0;
                sum = (images[inImagePtr + yStart1 + xStart1 | 0] | 0) + (images[inImagePtr + yStart1 + xStart2 | 0] | 0) + (images[inImagePtr + offset + u | 0] | 0) + (images[inImagePtr + yStart2 + xStart1 | 0] | 0) + (images[inImagePtr + yStart2 + xStart2 | 0] | 0) | 0;
                if ((sum | 0) > (0 | 0)) {
                    images[outImagePtr + offset + u | 0] = 1;
                } else {
                    images[outImagePtr + offset + u | 0] = 0;
                }
            }
        }
        return;
    }

    function memcpy(srcImagePtr, dstImagePtr) {
        srcImagePtr = srcImagePtr | 0;
        dstImagePtr = dstImagePtr | 0;

        var length = 0;

        length = imul(size, size) | 0;

        while ((length | 0) > 0) {
            length = length - 1 | 0;
            images[dstImagePtr + length | 0] = images[srcImagePtr + length | 0] | 0;
        }
    }

    function zeroBorder(imagePtr) {
        imagePtr = imagePtr | 0;

        var x = 0,
            y = 0;

        for (x = 0; (x | 0) < (size - 1 | 0); x = x + 1 | 0) {
            images[imagePtr + x | 0] = 0;
            images[imagePtr + y | 0] = 0;
            y = y + size - 1 | 0;
            images[imagePtr + y | 0] = 0;
            y = y + 1 | 0;
        }
        for (x = 0; (x | 0) < (size | 0); x = x + 1 | 0) {
            images[imagePtr + y | 0] = 0;
            y = y + 1 | 0;
        }
    }

    function skeletonize() {
        var subImagePtr = 0,
            erodedImagePtr = 0,
            tempImagePtr = 0,
            skelImagePtr = 0,
            sum = 0,
            done = 0;

        erodedImagePtr = imul(size, size) | 0;
        tempImagePtr = erodedImagePtr + erodedImagePtr | 0;
        skelImagePtr = tempImagePtr + erodedImagePtr | 0;

        // init skel-image
        init(skelImagePtr, 0);
        zeroBorder(subImagePtr);

        do {
            erode(subImagePtr, erodedImagePtr);
            dilate(erodedImagePtr, tempImagePtr);
            subtract(subImagePtr, tempImagePtr, tempImagePtr);
            bitwiseOr(skelImagePtr, tempImagePtr, skelImagePtr);
            memcpy(erodedImagePtr, subImagePtr);
            sum = countNonZero(subImagePtr) | 0;
            done = (sum | 0) == 0 | 0;
        } while (!done);
    }
    return {
        skeletonize: skeletonize
    };
}
/* @preserve ASM END */
/* harmony default export */ __webpack_exports__["a"] = Skeletonizer;
/* eslint-enable eqeqeq*/

/***/ }),
/* 67 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__barcode_reader__ = __webpack_require__(1);


function TwoOfFiveReader(opts) {
    __WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].call(this, opts);
    this.barSpaceRatio = [1, 1];
}

var N = 1,
    W = 3,
    properties = {
    START_PATTERN: { value: [W, N, W, N, N, N] },
    STOP_PATTERN: { value: [W, N, N, N, W] },
    CODE_PATTERN: { value: [[N, N, W, W, N], [W, N, N, N, W], [N, W, N, N, W], [W, W, N, N, N], [N, N, W, N, W], [W, N, W, N, N], [N, W, W, N, N], [N, N, N, W, W], [W, N, N, W, N], [N, W, N, W, N]] },
    SINGLE_CODE_ERROR: { value: 0.78, writable: true },
    AVG_CODE_ERROR: { value: 0.30, writable: true },
    FORMAT: { value: "2of5" }
};

var startPatternLength = properties.START_PATTERN.value.reduce(function (sum, val) {
    return sum + val;
}, 0);

TwoOfFiveReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype, properties);
TwoOfFiveReader.prototype.constructor = TwoOfFiveReader;

TwoOfFiveReader.prototype._findPattern = function (pattern, offset, isWhite, tryHarder) {
    var counter = [],
        self = this,
        i,
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0
    },
        error,
        j,
        sum,
        epsilon = self.AVG_CODE_ERROR;

    isWhite = isWhite || false;
    tryHarder = tryHarder || false;

    if (!offset) {
        offset = self._nextSet(self._row);
    }

    for (i = 0; i < pattern.length; i++) {
        counter[i] = 0;
    }

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                sum = 0;
                for (j = 0; j < counter.length; j++) {
                    sum += counter[j];
                }
                error = self._matchPattern(counter, pattern);
                if (error < epsilon) {
                    bestMatch.error = error;
                    bestMatch.start = i - sum;
                    bestMatch.end = i;
                    return bestMatch;
                }
                if (tryHarder) {
                    for (j = 0; j < counter.length - 2; j++) {
                        counter[j] = counter[j + 2];
                    }
                    counter[counter.length - 2] = 0;
                    counter[counter.length - 1] = 0;
                    counterPos--;
                } else {
                    return null;
                }
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

TwoOfFiveReader.prototype._findStart = function () {
    var self = this,
        leadingWhitespaceStart,
        offset = self._nextSet(self._row),
        startInfo,
        narrowBarWidth = 1;

    while (!startInfo) {
        startInfo = self._findPattern(self.START_PATTERN, offset, false, true);
        if (!startInfo) {
            return null;
        }
        narrowBarWidth = Math.floor((startInfo.end - startInfo.start) / startPatternLength);
        leadingWhitespaceStart = startInfo.start - narrowBarWidth * 5;
        if (leadingWhitespaceStart >= 0) {
            if (self._matchRange(leadingWhitespaceStart, startInfo.start, 0)) {
                return startInfo;
            }
        }
        offset = startInfo.end;
        startInfo = null;
    }
};

TwoOfFiveReader.prototype._verifyTrailingWhitespace = function (endInfo) {
    var self = this,
        trailingWhitespaceEnd;

    trailingWhitespaceEnd = endInfo.end + (endInfo.end - endInfo.start) / 2;
    if (trailingWhitespaceEnd < self._row.length) {
        if (self._matchRange(endInfo.end, trailingWhitespaceEnd, 0)) {
            return endInfo;
        }
    }
    return null;
};

TwoOfFiveReader.prototype._findEnd = function () {
    var self = this,
        endInfo,
        tmp,
        offset;

    self._row.reverse();
    offset = self._nextSet(self._row);
    endInfo = self._findPattern(self.STOP_PATTERN, offset, false, true);
    self._row.reverse();

    if (endInfo === null) {
        return null;
    }

    // reverse numbers
    tmp = endInfo.start;
    endInfo.start = self._row.length - endInfo.end;
    endInfo.end = self._row.length - tmp;

    return endInfo !== null ? self._verifyTrailingWhitespace(endInfo) : null;
};

TwoOfFiveReader.prototype._decodeCode = function (counter) {
    var j,
        self = this,
        sum = 0,
        normalized,
        error,
        epsilon = self.AVG_CODE_ERROR,
        code,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0
    };

    for (j = 0; j < counter.length; j++) {
        sum += counter[j];
    }
    for (code = 0; code < self.CODE_PATTERN.length; code++) {
        error = self._matchPattern(counter, self.CODE_PATTERN[code]);
        if (error < bestMatch.error) {
            bestMatch.code = code;
            bestMatch.error = error;
        }
    }
    if (bestMatch.error < epsilon) {
        return bestMatch;
    }
};

TwoOfFiveReader.prototype._decodePayload = function (counters, result, decodedCodes) {
    var i,
        self = this,
        pos = 0,
        counterLength = counters.length,
        counter = [0, 0, 0, 0, 0],
        code;

    while (pos < counterLength) {
        for (i = 0; i < 5; i++) {
            counter[i] = counters[pos] * this.barSpaceRatio[0];
            pos += 2;
        }
        code = self._decodeCode(counter);
        if (!code) {
            return null;
        }
        result.push(code.code + "");
        decodedCodes.push(code);
    }
    return code;
};

TwoOfFiveReader.prototype._verifyCounterLength = function (counters) {
    return counters.length % 10 === 0;
};

TwoOfFiveReader.prototype._decode = function () {
    var startInfo,
        endInfo,
        self = this,
        code,
        result = [],
        decodedCodes = [],
        counters;

    startInfo = self._findStart();
    if (!startInfo) {
        return null;
    }
    decodedCodes.push(startInfo);

    endInfo = self._findEnd();
    if (!endInfo) {
        return null;
    }

    counters = self._fillCounters(startInfo.end, endInfo.start, false);
    if (!self._verifyCounterLength(counters)) {
        return null;
    }
    code = self._decodePayload(counters, result, decodedCodes);
    if (!code) {
        return null;
    }
    if (result.length < 5) {
        return null;
    }

    decodedCodes.push(endInfo);
    return {
        code: result.join(""),
        start: startInfo.start,
        end: endInfo.end,
        startInfo: startInfo,
        decodedCodes: decodedCodes
    };
};

/* harmony default export */ __webpack_exports__["a"] = TwoOfFiveReader;

/***/ }),
/* 68 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__barcode_reader__ = __webpack_require__(1);


function CodabarReader() {
    __WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].call(this);
    this._counters = [];
}

var properties = {
    ALPHABETH_STRING: { value: "0123456789-$:/.+ABCD" },
    ALPHABET: { value: [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 36, 58, 47, 46, 43, 65, 66, 67, 68] },
    CHARACTER_ENCODINGS: { value: [0x003, 0x006, 0x009, 0x060, 0x012, 0x042, 0x021, 0x024, 0x030, 0x048, 0x00c, 0x018, 0x045, 0x051, 0x054, 0x015, 0x01A, 0x029, 0x00B, 0x00E] },
    START_END: { value: [0x01A, 0x029, 0x00B, 0x00E] },
    MIN_ENCODED_CHARS: { value: 4 },
    MAX_ACCEPTABLE: { value: 2.0 },
    PADDING: { value: 1.5 },
    FORMAT: { value: "codabar", writeable: false }
};

CodabarReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype, properties);
CodabarReader.prototype.constructor = CodabarReader;

CodabarReader.prototype._decode = function () {
    var self = this,
        result = [],
        start,
        decodedChar,
        pattern,
        nextStart,
        end;

    this._counters = self._fillCounters();
    start = self._findStart();
    if (!start) {
        return null;
    }
    nextStart = start.startCounter;

    do {
        pattern = self._toPattern(nextStart);
        if (pattern < 0) {
            return null;
        }
        decodedChar = self._patternToChar(pattern);
        if (decodedChar < 0) {
            return null;
        }
        result.push(decodedChar);
        nextStart += 8;
        if (result.length > 1 && self._isStartEnd(pattern)) {
            break;
        }
    } while (nextStart < self._counters.length);

    // verify end
    if (result.length - 2 < self.MIN_ENCODED_CHARS || !self._isStartEnd(pattern)) {
        return null;
    }

    // verify end white space
    if (!self._verifyWhitespace(start.startCounter, nextStart - 8)) {
        return null;
    }

    if (!self._validateResult(result, start.startCounter)) {
        return null;
    }

    nextStart = nextStart > self._counters.length ? self._counters.length : nextStart;
    end = start.start + self._sumCounters(start.startCounter, nextStart - 8);

    return {
        code: result.join(""),
        start: start.start,
        end: end,
        startInfo: start,
        decodedCodes: result
    };
};

CodabarReader.prototype._verifyWhitespace = function (startCounter, endCounter) {
    if (startCounter - 1 <= 0 || this._counters[startCounter - 1] >= this._calculatePatternLength(startCounter) / 2.0) {
        if (endCounter + 8 >= this._counters.length || this._counters[endCounter + 7] >= this._calculatePatternLength(endCounter) / 2.0) {
            return true;
        }
    }
    return false;
};

CodabarReader.prototype._calculatePatternLength = function (offset) {
    var i,
        sum = 0;

    for (i = offset; i < offset + 7; i++) {
        sum += this._counters[i];
    }

    return sum;
};

CodabarReader.prototype._thresholdResultPattern = function (result, startCounter) {
    var self = this,
        categorization = {
        space: {
            narrow: { size: 0, counts: 0, min: 0, max: Number.MAX_VALUE },
            wide: { size: 0, counts: 0, min: 0, max: Number.MAX_VALUE }
        },
        bar: {
            narrow: { size: 0, counts: 0, min: 0, max: Number.MAX_VALUE },
            wide: { size: 0, counts: 0, min: 0, max: Number.MAX_VALUE }
        }
    },
        kind,
        cat,
        i,
        j,
        pos = startCounter,
        pattern;

    for (i = 0; i < result.length; i++) {
        pattern = self._charToPattern(result[i]);
        for (j = 6; j >= 0; j--) {
            kind = (j & 1) === 2 ? categorization.bar : categorization.space;
            cat = (pattern & 1) === 1 ? kind.wide : kind.narrow;
            cat.size += self._counters[pos + j];
            cat.counts++;
            pattern >>= 1;
        }
        pos += 8;
    }

    ["space", "bar"].forEach(function (key) {
        var newkind = categorization[key];
        newkind.wide.min = Math.floor((newkind.narrow.size / newkind.narrow.counts + newkind.wide.size / newkind.wide.counts) / 2);
        newkind.narrow.max = Math.ceil(newkind.wide.min);
        newkind.wide.max = Math.ceil((newkind.wide.size * self.MAX_ACCEPTABLE + self.PADDING) / newkind.wide.counts);
    });

    return categorization;
};

CodabarReader.prototype._charToPattern = function (char) {
    var self = this,
        charCode = char.charCodeAt(0),
        i;

    for (i = 0; i < self.ALPHABET.length; i++) {
        if (self.ALPHABET[i] === charCode) {
            return self.CHARACTER_ENCODINGS[i];
        }
    }
    return 0x0;
};

CodabarReader.prototype._validateResult = function (result, startCounter) {
    var self = this,
        thresholds = self._thresholdResultPattern(result, startCounter),
        i,
        j,
        kind,
        cat,
        size,
        pos = startCounter,
        pattern;

    for (i = 0; i < result.length; i++) {
        pattern = self._charToPattern(result[i]);
        for (j = 6; j >= 0; j--) {
            kind = (j & 1) === 0 ? thresholds.bar : thresholds.space;
            cat = (pattern & 1) === 1 ? kind.wide : kind.narrow;
            size = self._counters[pos + j];
            if (size < cat.min || size > cat.max) {
                return false;
            }
            pattern >>= 1;
        }
        pos += 8;
    }
    return true;
};

CodabarReader.prototype._patternToChar = function (pattern) {
    var i,
        self = this;

    for (i = 0; i < self.CHARACTER_ENCODINGS.length; i++) {
        if (self.CHARACTER_ENCODINGS[i] === pattern) {
            return String.fromCharCode(self.ALPHABET[i]);
        }
    }
    return -1;
};

CodabarReader.prototype._computeAlternatingThreshold = function (offset, end) {
    var i,
        min = Number.MAX_VALUE,
        max = 0,
        counter;

    for (i = offset; i < end; i += 2) {
        counter = this._counters[i];
        if (counter > max) {
            max = counter;
        }
        if (counter < min) {
            min = counter;
        }
    }

    return (min + max) / 2.0 | 0;
};

CodabarReader.prototype._toPattern = function (offset) {
    var numCounters = 7,
        end = offset + numCounters,
        barThreshold,
        spaceThreshold,
        bitmask = 1 << numCounters - 1,
        pattern = 0,
        i,
        threshold;

    if (end > this._counters.length) {
        return -1;
    }

    barThreshold = this._computeAlternatingThreshold(offset, end);
    spaceThreshold = this._computeAlternatingThreshold(offset + 1, end);

    for (i = 0; i < numCounters; i++) {
        threshold = (i & 1) === 0 ? barThreshold : spaceThreshold;
        if (this._counters[offset + i] > threshold) {
            pattern |= bitmask;
        }
        bitmask >>= 1;
    }

    return pattern;
};

CodabarReader.prototype._isStartEnd = function (pattern) {
    var i;

    for (i = 0; i < this.START_END.length; i++) {
        if (this.START_END[i] === pattern) {
            return true;
        }
    }
    return false;
};

CodabarReader.prototype._sumCounters = function (start, end) {
    var i,
        sum = 0;

    for (i = start; i < end; i++) {
        sum += this._counters[i];
    }
    return sum;
};

CodabarReader.prototype._findStart = function () {
    var self = this,
        i,
        pattern,
        start = self._nextUnset(self._row),
        end;

    for (i = 1; i < this._counters.length; i++) {
        pattern = self._toPattern(i);
        if (pattern !== -1 && self._isStartEnd(pattern)) {
            // TODO: Look for whitespace ahead
            start += self._sumCounters(0, i);
            end = start + self._sumCounters(i, i + 8);
            return {
                start: start,
                end: end,
                startCounter: i,
                endCounter: i + 8
            };
        }
    }
};

/* harmony default export */ __webpack_exports__["a"] = CodabarReader;

/***/ }),
/* 69 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__barcode_reader__ = __webpack_require__(1);


function Code128Reader() {
    __WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].call(this);
}

var properties = {
    CODE_SHIFT: { value: 98 },
    CODE_C: { value: 99 },
    CODE_B: { value: 100 },
    CODE_A: { value: 101 },
    START_CODE_A: { value: 103 },
    START_CODE_B: { value: 104 },
    START_CODE_C: { value: 105 },
    STOP_CODE: { value: 106 },
    CODE_PATTERN: { value: [[2, 1, 2, 2, 2, 2], [2, 2, 2, 1, 2, 2], [2, 2, 2, 2, 2, 1], [1, 2, 1, 2, 2, 3], [1, 2, 1, 3, 2, 2], [1, 3, 1, 2, 2, 2], [1, 2, 2, 2, 1, 3], [1, 2, 2, 3, 1, 2], [1, 3, 2, 2, 1, 2], [2, 2, 1, 2, 1, 3], [2, 2, 1, 3, 1, 2], [2, 3, 1, 2, 1, 2], [1, 1, 2, 2, 3, 2], [1, 2, 2, 1, 3, 2], [1, 2, 2, 2, 3, 1], [1, 1, 3, 2, 2, 2], [1, 2, 3, 1, 2, 2], [1, 2, 3, 2, 2, 1], [2, 2, 3, 2, 1, 1], [2, 2, 1, 1, 3, 2], [2, 2, 1, 2, 3, 1], [2, 1, 3, 2, 1, 2], [2, 2, 3, 1, 1, 2], [3, 1, 2, 1, 3, 1], [3, 1, 1, 2, 2, 2], [3, 2, 1, 1, 2, 2], [3, 2, 1, 2, 2, 1], [3, 1, 2, 2, 1, 2], [3, 2, 2, 1, 1, 2], [3, 2, 2, 2, 1, 1], [2, 1, 2, 1, 2, 3], [2, 1, 2, 3, 2, 1], [2, 3, 2, 1, 2, 1], [1, 1, 1, 3, 2, 3], [1, 3, 1, 1, 2, 3], [1, 3, 1, 3, 2, 1], [1, 1, 2, 3, 1, 3], [1, 3, 2, 1, 1, 3], [1, 3, 2, 3, 1, 1], [2, 1, 1, 3, 1, 3], [2, 3, 1, 1, 1, 3], [2, 3, 1, 3, 1, 1], [1, 1, 2, 1, 3, 3], [1, 1, 2, 3, 3, 1], [1, 3, 2, 1, 3, 1], [1, 1, 3, 1, 2, 3], [1, 1, 3, 3, 2, 1], [1, 3, 3, 1, 2, 1], [3, 1, 3, 1, 2, 1], [2, 1, 1, 3, 3, 1], [2, 3, 1, 1, 3, 1], [2, 1, 3, 1, 1, 3], [2, 1, 3, 3, 1, 1], [2, 1, 3, 1, 3, 1], [3, 1, 1, 1, 2, 3], [3, 1, 1, 3, 2, 1], [3, 3, 1, 1, 2, 1], [3, 1, 2, 1, 1, 3], [3, 1, 2, 3, 1, 1], [3, 3, 2, 1, 1, 1], [3, 1, 4, 1, 1, 1], [2, 2, 1, 4, 1, 1], [4, 3, 1, 1, 1, 1], [1, 1, 1, 2, 2, 4], [1, 1, 1, 4, 2, 2], [1, 2, 1, 1, 2, 4], [1, 2, 1, 4, 2, 1], [1, 4, 1, 1, 2, 2], [1, 4, 1, 2, 2, 1], [1, 1, 2, 2, 1, 4], [1, 1, 2, 4, 1, 2], [1, 2, 2, 1, 1, 4], [1, 2, 2, 4, 1, 1], [1, 4, 2, 1, 1, 2], [1, 4, 2, 2, 1, 1], [2, 4, 1, 2, 1, 1], [2, 2, 1, 1, 1, 4], [4, 1, 3, 1, 1, 1], [2, 4, 1, 1, 1, 2], [1, 3, 4, 1, 1, 1], [1, 1, 1, 2, 4, 2], [1, 2, 1, 1, 4, 2], [1, 2, 1, 2, 4, 1], [1, 1, 4, 2, 1, 2], [1, 2, 4, 1, 1, 2], [1, 2, 4, 2, 1, 1], [4, 1, 1, 2, 1, 2], [4, 2, 1, 1, 1, 2], [4, 2, 1, 2, 1, 1], [2, 1, 2, 1, 4, 1], [2, 1, 4, 1, 2, 1], [4, 1, 2, 1, 2, 1], [1, 1, 1, 1, 4, 3], [1, 1, 1, 3, 4, 1], [1, 3, 1, 1, 4, 1], [1, 1, 4, 1, 1, 3], [1, 1, 4, 3, 1, 1], [4, 1, 1, 1, 1, 3], [4, 1, 1, 3, 1, 1], [1, 1, 3, 1, 4, 1], [1, 1, 4, 1, 3, 1], [3, 1, 1, 1, 4, 1], [4, 1, 1, 1, 3, 1], [2, 1, 1, 4, 1, 2], [2, 1, 1, 2, 1, 4], [2, 1, 1, 2, 3, 2], [2, 3, 3, 1, 1, 1, 2]] },
    SINGLE_CODE_ERROR: { value: 0.64 },
    AVG_CODE_ERROR: { value: 0.30 },
    FORMAT: { value: "code_128", writeable: false },
    MODULE_INDICES: { value: { bar: [0, 2, 4], space: [1, 3, 5] } }
};

Code128Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype, properties);
Code128Reader.prototype.constructor = Code128Reader;

Code128Reader.prototype._decodeCode = function (start, correction) {
    var counter = [0, 0, 0, 0, 0, 0],
        i,
        self = this,
        offset = start,
        isWhite = !self._row[offset],
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: start,
        end: start,
        correction: {
            bar: 1,
            space: 1
        }
    },
        code,
        error;

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                if (correction) {
                    self._correct(counter, correction);
                }
                for (code = 0; code < self.CODE_PATTERN.length; code++) {
                    error = self._matchPattern(counter, self.CODE_PATTERN[code]);
                    if (error < bestMatch.error) {
                        bestMatch.code = code;
                        bestMatch.error = error;
                    }
                }
                bestMatch.end = i;
                if (bestMatch.code === -1 || bestMatch.error > self.AVG_CODE_ERROR) {
                    return null;
                }
                if (self.CODE_PATTERN[bestMatch.code]) {
                    bestMatch.correction.bar = calculateCorrection(self.CODE_PATTERN[bestMatch.code], counter, this.MODULE_INDICES.bar);
                    bestMatch.correction.space = calculateCorrection(self.CODE_PATTERN[bestMatch.code], counter, this.MODULE_INDICES.space);
                }
                return bestMatch;
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

Code128Reader.prototype._correct = function (counter, correction) {
    this._correctBars(counter, correction.bar, this.MODULE_INDICES.bar);
    this._correctBars(counter, correction.space, this.MODULE_INDICES.space);
};

Code128Reader.prototype._findStart = function () {
    var counter = [0, 0, 0, 0, 0, 0],
        i,
        self = this,
        offset = self._nextSet(self._row),
        isWhite = false,
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0,
        correction: {
            bar: 1,
            space: 1
        }
    },
        code,
        error,
        j,
        sum;

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                sum = 0;
                for (j = 0; j < counter.length; j++) {
                    sum += counter[j];
                }
                for (code = self.START_CODE_A; code <= self.START_CODE_C; code++) {
                    error = self._matchPattern(counter, self.CODE_PATTERN[code]);
                    if (error < bestMatch.error) {
                        bestMatch.code = code;
                        bestMatch.error = error;
                    }
                }
                if (bestMatch.error < self.AVG_CODE_ERROR) {
                    bestMatch.start = i - sum;
                    bestMatch.end = i;
                    bestMatch.correction.bar = calculateCorrection(self.CODE_PATTERN[bestMatch.code], counter, this.MODULE_INDICES.bar);
                    bestMatch.correction.space = calculateCorrection(self.CODE_PATTERN[bestMatch.code], counter, this.MODULE_INDICES.space);
                    return bestMatch;
                }

                for (j = 0; j < 4; j++) {
                    counter[j] = counter[j + 2];
                }
                counter[4] = 0;
                counter[5] = 0;
                counterPos--;
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

Code128Reader.prototype._decode = function () {
    var self = this,
        startInfo = self._findStart(),
        code = null,
        done = false,
        result = [],
        multiplier = 0,
        checksum = 0,
        codeset,
        rawResult = [],
        decodedCodes = [],
        shiftNext = false,
        unshift,
        removeLastCharacter = true;

    if (startInfo === null) {
        return null;
    }
    code = {
        code: startInfo.code,
        start: startInfo.start,
        end: startInfo.end,
        correction: {
            bar: startInfo.correction.bar,
            space: startInfo.correction.space
        }
    };
    decodedCodes.push(code);
    checksum = code.code;
    switch (code.code) {
        case self.START_CODE_A:
            codeset = self.CODE_A;
            break;
        case self.START_CODE_B:
            codeset = self.CODE_B;
            break;
        case self.START_CODE_C:
            codeset = self.CODE_C;
            break;
        default:
            return null;
    }

    while (!done) {
        unshift = shiftNext;
        shiftNext = false;
        code = self._decodeCode(code.end, code.correction);
        if (code !== null) {
            if (code.code !== self.STOP_CODE) {
                removeLastCharacter = true;
            }

            if (code.code !== self.STOP_CODE) {
                rawResult.push(code.code);
                multiplier++;
                checksum += multiplier * code.code;
            }
            decodedCodes.push(code);

            switch (codeset) {
                case self.CODE_A:
                    if (code.code < 64) {
                        result.push(String.fromCharCode(32 + code.code));
                    } else if (code.code < 96) {
                        result.push(String.fromCharCode(code.code - 64));
                    } else {
                        if (code.code !== self.STOP_CODE) {
                            removeLastCharacter = false;
                        }
                        switch (code.code) {
                            case self.CODE_SHIFT:
                                shiftNext = true;
                                codeset = self.CODE_B;
                                break;
                            case self.CODE_B:
                                codeset = self.CODE_B;
                                break;
                            case self.CODE_C:
                                codeset = self.CODE_C;
                                break;
                            case self.STOP_CODE:
                                done = true;
                                break;
                        }
                    }
                    break;
                case self.CODE_B:
                    if (code.code < 96) {
                        result.push(String.fromCharCode(32 + code.code));
                    } else {
                        if (code.code !== self.STOP_CODE) {
                            removeLastCharacter = false;
                        }
                        switch (code.code) {
                            case self.CODE_SHIFT:
                                shiftNext = true;
                                codeset = self.CODE_A;
                                break;
                            case self.CODE_A:
                                codeset = self.CODE_A;
                                break;
                            case self.CODE_C:
                                codeset = self.CODE_C;
                                break;
                            case self.STOP_CODE:
                                done = true;
                                break;
                        }
                    }
                    break;
                case self.CODE_C:
                    if (code.code < 100) {
                        result.push(code.code < 10 ? "0" + code.code : code.code);
                    } else {
                        if (code.code !== self.STOP_CODE) {
                            removeLastCharacter = false;
                        }
                        switch (code.code) {
                            case self.CODE_A:
                                codeset = self.CODE_A;
                                break;
                            case self.CODE_B:
                                codeset = self.CODE_B;
                                break;
                            case self.STOP_CODE:
                                done = true;
                                break;
                        }
                    }
                    break;
            }
        } else {
            done = true;
        }
        if (unshift) {
            codeset = codeset === self.CODE_A ? self.CODE_B : self.CODE_A;
        }
    }

    if (code === null) {
        return null;
    }

    code.end = self._nextUnset(self._row, code.end);
    if (!self._verifyTrailingWhitespace(code)) {
        return null;
    }

    checksum -= multiplier * rawResult[rawResult.length - 1];
    if (checksum % 103 !== rawResult[rawResult.length - 1]) {
        return null;
    }

    if (!result.length) {
        return null;
    }

    // remove last code from result (checksum)
    if (removeLastCharacter) {
        result.splice(result.length - 1, 1);
    }

    return {
        code: result.join(""),
        start: startInfo.start,
        end: code.end,
        codeset: codeset,
        startInfo: startInfo,
        decodedCodes: decodedCodes,
        endInfo: code
    };
};

__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype._verifyTrailingWhitespace = function (endInfo) {
    var self = this,
        trailingWhitespaceEnd;

    trailingWhitespaceEnd = endInfo.end + (endInfo.end - endInfo.start) / 2;
    if (trailingWhitespaceEnd < self._row.length) {
        if (self._matchRange(endInfo.end, trailingWhitespaceEnd, 0)) {
            return endInfo;
        }
    }
    return null;
};

function calculateCorrection(expected, normalized, indices) {
    var length = indices.length,
        sumNormalized = 0,
        sumExpected = 0;

    while (length--) {
        sumExpected += expected[indices[length]];
        sumNormalized += normalized[indices[length]];
    }
    return sumExpected / sumNormalized;
}

/* harmony default export */ __webpack_exports__["a"] = Code128Reader;

/***/ }),
/* 70 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__code_39_reader__ = __webpack_require__(31);


function Code39VINReader() {
    __WEBPACK_IMPORTED_MODULE_0__code_39_reader__["a" /* default */].call(this);
}

var patterns = {
    IOQ: /[IOQ]/g,
    AZ09: /[A-Z0-9]{17}/
};

Code39VINReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__code_39_reader__["a" /* default */].prototype);
Code39VINReader.prototype.constructor = Code39VINReader;

// Cribbed from:
// https://github.com/zxing/zxing/blob/master/core/src/main/java/com/google/zxing/client/result/VINResultParser.java
Code39VINReader.prototype._decode = function () {
    var result = __WEBPACK_IMPORTED_MODULE_0__code_39_reader__["a" /* default */].prototype._decode.apply(this);
    if (!result) {
        return null;
    }

    var code = result.code;

    if (!code) {
        return null;
    }

    code = code.replace(patterns.IOQ, '');

    if (!code.match(patterns.AZ09)) {
        if (true) {
            console.log('Failed AZ09 pattern code:', code);
        }
        return null;
    }

    if (!this._checkChecksum(code)) {
        return null;
    }

    result.code = code;
    return result;
};

Code39VINReader.prototype._checkChecksum = function (code) {
    // TODO
    return !!code;
};

/* harmony default export */ __webpack_exports__["a"] = Code39VINReader;

/***/ }),
/* 71 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__barcode_reader__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_array_helper__ = __webpack_require__(3);



function Code93Reader() {
    __WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].call(this);
}

var ALPHABETH_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";

var properties = {
    ALPHABETH_STRING: { value: ALPHABETH_STRING },
    ALPHABET: { value: ALPHABETH_STRING.split('').map(function (char) {
            return char.charCodeAt(0);
        }) },
    CHARACTER_ENCODINGS: { value: [0x114, 0x148, 0x144, 0x142, 0x128, 0x124, 0x122, 0x150, 0x112, 0x10A, 0x1A8, 0x1A4, 0x1A2, 0x194, 0x192, 0x18A, 0x168, 0x164, 0x162, 0x134, 0x11A, 0x158, 0x14C, 0x146, 0x12C, 0x116, 0x1B4, 0x1B2, 0x1AC, 0x1A6, 0x196, 0x19A, 0x16C, 0x166, 0x136, 0x13A, 0x12E, 0x1D4, 0x1D2, 0x1CA, 0x16E, 0x176, 0x1AE, 0x126, 0x1DA, 0x1D6, 0x132, 0x15E] },
    ASTERISK: { value: 0x15E },
    FORMAT: { value: "code_93", writeable: false }
};

Code93Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__barcode_reader__["a" /* default */].prototype, properties);
Code93Reader.prototype.constructor = Code93Reader;

Code93Reader.prototype._decode = function () {
    var self = this,
        counters = [0, 0, 0, 0, 0, 0],
        result = [],
        start = self._findStart(),
        decodedChar,
        lastStart,
        pattern,
        nextStart;

    if (!start) {
        return null;
    }
    nextStart = self._nextSet(self._row, start.end);

    do {
        counters = self._toCounters(nextStart, counters);
        pattern = self._toPattern(counters);
        if (pattern < 0) {
            return null;
        }
        decodedChar = self._patternToChar(pattern);
        if (decodedChar < 0) {
            return null;
        }
        result.push(decodedChar);
        lastStart = nextStart;
        nextStart += __WEBPACK_IMPORTED_MODULE_1__common_array_helper__["a" /* default */].sum(counters);
        nextStart = self._nextSet(self._row, nextStart);
    } while (decodedChar !== '*');
    result.pop();

    if (!result.length) {
        return null;
    }

    if (!self._verifyEnd(lastStart, nextStart, counters)) {
        return null;
    }

    if (!self._verifyChecksums(result)) {
        return null;
    }

    result = result.slice(0, result.length - 2);
    if ((result = self._decodeExtended(result)) === null) {
        return null;
    };

    return {
        code: result.join(""),
        start: start.start,
        end: nextStart,
        startInfo: start,
        decodedCodes: result
    };
};

Code93Reader.prototype._verifyEnd = function (lastStart, nextStart) {
    if (lastStart === nextStart || !this._row[nextStart]) {
        return false;
    }
    return true;
};

Code93Reader.prototype._patternToChar = function (pattern) {
    var i,
        self = this;

    for (i = 0; i < self.CHARACTER_ENCODINGS.length; i++) {
        if (self.CHARACTER_ENCODINGS[i] === pattern) {
            return String.fromCharCode(self.ALPHABET[i]);
        }
    }
    return -1;
};

Code93Reader.prototype._toPattern = function (counters) {
    var numCounters = counters.length;
    var pattern = 0;
    var sum = 0;
    for (var i = 0; i < numCounters; i++) {
        sum += counters[i];
    }

    for (var _i = 0; _i < numCounters; _i++) {
        var normalized = Math.round(counters[_i] * 9 / sum);
        if (normalized < 1 || normalized > 4) {
            return -1;
        }
        if ((_i & 1) === 0) {
            for (var j = 0; j < normalized; j++) {
                pattern = pattern << 1 | 1;
            }
        } else {
            pattern <<= normalized;
        }
    }

    return pattern;
};

Code93Reader.prototype._findStart = function () {
    var self = this,
        offset = self._nextSet(self._row),
        patternStart = offset,
        counter = [0, 0, 0, 0, 0, 0],
        counterPos = 0,
        isWhite = false,
        i,
        j,
        whiteSpaceMustStart;

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                // find start pattern
                if (self._toPattern(counter) === self.ASTERISK) {
                    whiteSpaceMustStart = Math.floor(Math.max(0, patternStart - (i - patternStart) / 4));
                    if (self._matchRange(whiteSpaceMustStart, patternStart, 0)) {
                        return {
                            start: patternStart,
                            end: i
                        };
                    }
                }

                patternStart += counter[0] + counter[1];
                for (j = 0; j < 4; j++) {
                    counter[j] = counter[j + 2];
                }
                counter[4] = 0;
                counter[5] = 0;
                counterPos--;
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

Code93Reader.prototype._decodeExtended = function (charArray) {
    var length = charArray.length;
    var result = [];
    for (var i = 0; i < length; i++) {
        var char = charArray[i];
        if (char >= 'a' && char <= 'd') {
            if (i > length - 2) {
                return null;
            }
            var nextChar = charArray[++i];
            var nextCharCode = nextChar.charCodeAt(0);
            var decodedChar = void 0;
            switch (char) {
                case 'a':
                    if (nextChar >= 'A' && nextChar <= 'Z') {
                        decodedChar = String.fromCharCode(nextCharCode - 64);
                    } else {
                        return null;
                    }
                    break;
                case 'b':
                    if (nextChar >= 'A' && nextChar <= 'E') {
                        decodedChar = String.fromCharCode(nextCharCode - 38);
                    } else if (nextChar >= 'F' && nextChar <= 'J') {
                        decodedChar = String.fromCharCode(nextCharCode - 11);
                    } else if (nextChar >= 'K' && nextChar <= 'O') {
                        decodedChar = String.fromCharCode(nextCharCode + 16);
                    } else if (nextChar >= 'P' && nextChar <= 'S') {
                        decodedChar = String.fromCharCode(nextCharCode + 43);
                    } else if (nextChar >= 'T' && nextChar <= 'Z') {
                        decodedChar = String.fromCharCode(127);
                    } else {
                        return null;
                    }
                    break;
                case 'c':
                    if (nextChar >= 'A' && nextChar <= 'O') {
                        decodedChar = String.fromCharCode(nextCharCode - 32);
                    } else if (nextChar === 'Z') {
                        decodedChar = ':';
                    } else {
                        return null;
                    }
                    break;
                case 'd':
                    if (nextChar >= 'A' && nextChar <= 'Z') {
                        decodedChar = String.fromCharCode(nextCharCode + 32);
                    } else {
                        return null;
                    }
                    break;
            }
            result.push(decodedChar);
        } else {
            result.push(char);
        }
    }
    return result;
};

Code93Reader.prototype._verifyChecksums = function (charArray) {
    return this._matchCheckChar(charArray, charArray.length - 2, 20) && this._matchCheckChar(charArray, charArray.length - 1, 15);
};

Code93Reader.prototype._matchCheckChar = function (charArray, index, maxWeight) {
    var _this = this;

    var arrayToCheck = charArray.slice(0, index);
    var length = arrayToCheck.length;
    var weightedSums = arrayToCheck.reduce(function (sum, char, i) {
        var weight = (i * -1 + (length - 1)) % maxWeight + 1;
        var value = _this.ALPHABET.indexOf(char.charCodeAt(0));
        return sum + weight * value;
    }, 0);

    var checkChar = this.ALPHABET[weightedSums % 47];
    return checkChar === charArray[index].charCodeAt(0);
};

/* harmony default export */ __webpack_exports__["a"] = Code93Reader;

/***/ }),
/* 72 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ean_reader__ = __webpack_require__(4);


function EAN2Reader() {
    __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].call(this);
}

var properties = {
    FORMAT: { value: "ean_2", writeable: false }
};

EAN2Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype, properties);
EAN2Reader.prototype.constructor = EAN2Reader;

EAN2Reader.prototype.decode = function (row, start) {
    this._row = row;
    var counters = [0, 0, 0, 0],
        codeFrequency = 0,
        i = 0,
        offset = start,
        end = this._row.length,
        code,
        result = [],
        decodedCodes = [];

    for (i = 0; i < 2 && offset < end; i++) {
        code = this._decodeCode(offset);
        if (!code) {
            return null;
        }
        decodedCodes.push(code);
        result.push(code.code % 10);
        if (code.code >= this.CODE_G_START) {
            codeFrequency |= 1 << 1 - i;
        }
        if (i != 1) {
            offset = this._nextSet(this._row, code.end);
            offset = this._nextUnset(this._row, offset);
        }
    }

    if (result.length != 2 || parseInt(result.join("")) % 4 !== codeFrequency) {
        return null;
    }
    return {
        code: result.join(""),
        decodedCodes: decodedCodes,
        end: code.end
    };
};

/* harmony default export */ __webpack_exports__["a"] = EAN2Reader;

/***/ }),
/* 73 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ean_reader__ = __webpack_require__(4);


function EAN5Reader() {
    __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].call(this);
}

var properties = {
    FORMAT: { value: "ean_5", writeable: false }
};

var CHECK_DIGIT_ENCODINGS = [24, 20, 18, 17, 12, 6, 3, 10, 9, 5];

EAN5Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype, properties);
EAN5Reader.prototype.constructor = EAN5Reader;

EAN5Reader.prototype.decode = function (row, start) {
    this._row = row;
    var counters = [0, 0, 0, 0],
        codeFrequency = 0,
        i = 0,
        offset = start,
        end = this._row.length,
        code,
        result = [],
        decodedCodes = [];

    for (i = 0; i < 5 && offset < end; i++) {
        code = this._decodeCode(offset);
        if (!code) {
            return null;
        }
        decodedCodes.push(code);
        result.push(code.code % 10);
        if (code.code >= this.CODE_G_START) {
            codeFrequency |= 1 << 4 - i;
        }
        if (i != 4) {
            offset = this._nextSet(this._row, code.end);
            offset = this._nextUnset(this._row, offset);
        }
    }

    if (result.length != 5) {
        return null;
    }

    if (extensionChecksum(result) !== determineCheckDigit(codeFrequency)) {
        return null;
    }
    return {
        code: result.join(""),
        decodedCodes: decodedCodes,
        end: code.end
    };
};

function determineCheckDigit(codeFrequency) {
    var i;
    for (i = 0; i < 10; i++) {
        if (codeFrequency === CHECK_DIGIT_ENCODINGS[i]) {
            return i;
        }
    }
    return null;
}

function extensionChecksum(result) {
    var length = result.length,
        sum = 0,
        i;

    for (i = length - 2; i >= 0; i -= 2) {
        sum += result[i];
    }
    sum *= 3;
    for (i = length - 1; i >= 0; i -= 2) {
        sum += result[i];
    }
    sum *= 3;
    return sum % 10;
}

/* harmony default export */ __webpack_exports__["a"] = EAN5Reader;

/***/ }),
/* 74 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ean_reader__ = __webpack_require__(4);


function EAN8Reader(opts, supplements) {
    __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].call(this, opts, supplements);
}

var properties = {
    FORMAT: { value: "ean_8", writeable: false }
};

EAN8Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype, properties);
EAN8Reader.prototype.constructor = EAN8Reader;

EAN8Reader.prototype._decodePayload = function (code, result, decodedCodes) {
    var i,
        self = this;

    for (i = 0; i < 4; i++) {
        code = self._decodeCode(code.end, self.CODE_G_START);
        if (!code) {
            return null;
        }
        result.push(code.code);
        decodedCodes.push(code);
    }

    code = self._findPattern(self.MIDDLE_PATTERN, code.end, true, false);
    if (code === null) {
        return null;
    }
    decodedCodes.push(code);

    for (i = 0; i < 4; i++) {
        code = self._decodeCode(code.end, self.CODE_G_START);
        if (!code) {
            return null;
        }
        decodedCodes.push(code);
        result.push(code.code);
    }

    return code;
};

/* harmony default export */ __webpack_exports__["a"] = EAN8Reader;

/***/ }),
/* 75 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_lodash_merge__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__barcode_reader__ = __webpack_require__(1);




function I2of5Reader(opts) {
    opts = __WEBPACK_IMPORTED_MODULE_0_lodash_merge___default()(getDefaulConfig(), opts);
    __WEBPACK_IMPORTED_MODULE_1__barcode_reader__["a" /* default */].call(this, opts);
    this.barSpaceRatio = [1, 1];
    if (opts.normalizeBarSpaceWidth) {
        this.SINGLE_CODE_ERROR = 0.38;
        this.AVG_CODE_ERROR = 0.09;
    }
}

function getDefaulConfig() {
    var config = {};

    Object.keys(I2of5Reader.CONFIG_KEYS).forEach(function (key) {
        config[key] = I2of5Reader.CONFIG_KEYS[key].default;
    });
    return config;
}

var N = 1,
    W = 3,
    properties = {
    START_PATTERN: { value: [N, N, N, N] },
    STOP_PATTERN: { value: [N, N, W] },
    CODE_PATTERN: { value: [[N, N, W, W, N], [W, N, N, N, W], [N, W, N, N, W], [W, W, N, N, N], [N, N, W, N, W], [W, N, W, N, N], [N, W, W, N, N], [N, N, N, W, W], [W, N, N, W, N], [N, W, N, W, N]] },
    SINGLE_CODE_ERROR: { value: 0.78, writable: true },
    AVG_CODE_ERROR: { value: 0.38, writable: true },
    MAX_CORRECTION_FACTOR: { value: 5 },
    FORMAT: { value: "i2of5" }
};

I2of5Reader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_1__barcode_reader__["a" /* default */].prototype, properties);
I2of5Reader.prototype.constructor = I2of5Reader;

I2of5Reader.prototype._matchPattern = function (counter, code) {
    if (this.config.normalizeBarSpaceWidth) {
        var i,
            counterSum = [0, 0],
            codeSum = [0, 0],
            correction = [0, 0],
            correctionRatio = this.MAX_CORRECTION_FACTOR,
            correctionRatioInverse = 1 / correctionRatio;

        for (i = 0; i < counter.length; i++) {
            counterSum[i % 2] += counter[i];
            codeSum[i % 2] += code[i];
        }
        correction[0] = codeSum[0] / counterSum[0];
        correction[1] = codeSum[1] / counterSum[1];

        correction[0] = Math.max(Math.min(correction[0], correctionRatio), correctionRatioInverse);
        correction[1] = Math.max(Math.min(correction[1], correctionRatio), correctionRatioInverse);
        this.barSpaceRatio = correction;
        for (i = 0; i < counter.length; i++) {
            counter[i] *= this.barSpaceRatio[i % 2];
        }
    }
    return __WEBPACK_IMPORTED_MODULE_1__barcode_reader__["a" /* default */].prototype._matchPattern.call(this, counter, code);
};

I2of5Reader.prototype._findPattern = function (pattern, offset, isWhite, tryHarder) {
    var counter = [],
        self = this,
        i,
        counterPos = 0,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0
    },
        error,
        j,
        sum,
        normalized,
        epsilon = self.AVG_CODE_ERROR;

    isWhite = isWhite || false;
    tryHarder = tryHarder || false;

    if (!offset) {
        offset = self._nextSet(self._row);
    }

    for (i = 0; i < pattern.length; i++) {
        counter[i] = 0;
    }

    for (i = offset; i < self._row.length; i++) {
        if (self._row[i] ^ isWhite) {
            counter[counterPos]++;
        } else {
            if (counterPos === counter.length - 1) {
                sum = 0;
                for (j = 0; j < counter.length; j++) {
                    sum += counter[j];
                }
                error = self._matchPattern(counter, pattern);
                if (error < epsilon) {
                    bestMatch.error = error;
                    bestMatch.start = i - sum;
                    bestMatch.end = i;
                    return bestMatch;
                }
                if (tryHarder) {
                    for (j = 0; j < counter.length - 2; j++) {
                        counter[j] = counter[j + 2];
                    }
                    counter[counter.length - 2] = 0;
                    counter[counter.length - 1] = 0;
                    counterPos--;
                } else {
                    return null;
                }
            } else {
                counterPos++;
            }
            counter[counterPos] = 1;
            isWhite = !isWhite;
        }
    }
    return null;
};

I2of5Reader.prototype._findStart = function () {
    var self = this,
        leadingWhitespaceStart,
        offset = self._nextSet(self._row),
        startInfo,
        narrowBarWidth = 1;

    while (!startInfo) {
        startInfo = self._findPattern(self.START_PATTERN, offset, false, true);
        if (!startInfo) {
            return null;
        }
        narrowBarWidth = Math.floor((startInfo.end - startInfo.start) / 4);
        leadingWhitespaceStart = startInfo.start - narrowBarWidth * 10;
        if (leadingWhitespaceStart >= 0) {
            if (self._matchRange(leadingWhitespaceStart, startInfo.start, 0)) {
                return startInfo;
            }
        }
        offset = startInfo.end;
        startInfo = null;
    }
};

I2of5Reader.prototype._verifyTrailingWhitespace = function (endInfo) {
    var self = this,
        trailingWhitespaceEnd;

    trailingWhitespaceEnd = endInfo.end + (endInfo.end - endInfo.start) / 2;
    if (trailingWhitespaceEnd < self._row.length) {
        if (self._matchRange(endInfo.end, trailingWhitespaceEnd, 0)) {
            return endInfo;
        }
    }
    return null;
};

I2of5Reader.prototype._findEnd = function () {
    var self = this,
        endInfo,
        tmp;

    self._row.reverse();
    endInfo = self._findPattern(self.STOP_PATTERN);
    self._row.reverse();

    if (endInfo === null) {
        return null;
    }

    // reverse numbers
    tmp = endInfo.start;
    endInfo.start = self._row.length - endInfo.end;
    endInfo.end = self._row.length - tmp;

    return endInfo !== null ? self._verifyTrailingWhitespace(endInfo) : null;
};

I2of5Reader.prototype._decodePair = function (counterPair) {
    var i,
        code,
        codes = [],
        self = this;

    for (i = 0; i < counterPair.length; i++) {
        code = self._decodeCode(counterPair[i]);
        if (!code) {
            return null;
        }
        codes.push(code);
    }
    return codes;
};

I2of5Reader.prototype._decodeCode = function (counter) {
    var j,
        self = this,
        sum = 0,
        normalized,
        error,
        epsilon = self.AVG_CODE_ERROR,
        code,
        bestMatch = {
        error: Number.MAX_VALUE,
        code: -1,
        start: 0,
        end: 0
    };

    for (j = 0; j < counter.length; j++) {
        sum += counter[j];
    }
    for (code = 0; code < self.CODE_PATTERN.length; code++) {
        error = self._matchPattern(counter, self.CODE_PATTERN[code]);
        if (error < bestMatch.error) {
            bestMatch.code = code;
            bestMatch.error = error;
        }
    }
    if (bestMatch.error < epsilon) {
        return bestMatch;
    }
};

I2of5Reader.prototype._decodePayload = function (counters, result, decodedCodes) {
    var i,
        self = this,
        pos = 0,
        counterLength = counters.length,
        counterPair = [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0]],
        codes;

    while (pos < counterLength) {
        for (i = 0; i < 5; i++) {
            counterPair[0][i] = counters[pos] * this.barSpaceRatio[0];
            counterPair[1][i] = counters[pos + 1] * this.barSpaceRatio[1];
            pos += 2;
        }
        codes = self._decodePair(counterPair);
        if (!codes) {
            return null;
        }
        for (i = 0; i < codes.length; i++) {
            result.push(codes[i].code + "");
            decodedCodes.push(codes[i]);
        }
    }
    return codes;
};

I2of5Reader.prototype._verifyCounterLength = function (counters) {
    return counters.length % 10 === 0;
};

I2of5Reader.prototype._decode = function () {
    var startInfo,
        endInfo,
        self = this,
        code,
        result = [],
        decodedCodes = [],
        counters;

    startInfo = self._findStart();
    if (!startInfo) {
        return null;
    }
    decodedCodes.push(startInfo);

    endInfo = self._findEnd();
    if (!endInfo) {
        return null;
    }

    counters = self._fillCounters(startInfo.end, endInfo.start, false);
    if (!self._verifyCounterLength(counters)) {
        return null;
    }
    code = self._decodePayload(counters, result, decodedCodes);
    if (!code) {
        return null;
    }
    if (result.length % 2 !== 0 || result.length < 6) {
        return null;
    }

    decodedCodes.push(endInfo);
    return {
        code: result.join(""),
        start: startInfo.start,
        end: endInfo.end,
        startInfo: startInfo,
        decodedCodes: decodedCodes
    };
};

I2of5Reader.CONFIG_KEYS = {
    normalizeBarSpaceWidth: {
        'type': 'boolean',
        'default': false,
        'description': 'If true, the reader tries to normalize the' + 'width-difference between bars and spaces'
    }
};

/* harmony default export */ __webpack_exports__["a"] = I2of5Reader;

/***/ }),
/* 76 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ean_reader__ = __webpack_require__(4);


function UPCEReader(opts, supplements) {
    __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].call(this, opts, supplements);
}

var properties = {
    CODE_FREQUENCY: { value: [[56, 52, 50, 49, 44, 38, 35, 42, 41, 37], [7, 11, 13, 14, 19, 25, 28, 21, 22, 26]] },
    STOP_PATTERN: { value: [1 / 6 * 7, 1 / 6 * 7, 1 / 6 * 7, 1 / 6 * 7, 1 / 6 * 7, 1 / 6 * 7] },
    FORMAT: { value: "upc_e", writeable: false }
};

UPCEReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype, properties);
UPCEReader.prototype.constructor = UPCEReader;

UPCEReader.prototype._decodePayload = function (code, result, decodedCodes) {
    var i,
        self = this,
        codeFrequency = 0x0;

    for (i = 0; i < 6; i++) {
        code = self._decodeCode(code.end);
        if (!code) {
            return null;
        }
        if (code.code >= self.CODE_G_START) {
            code.code = code.code - self.CODE_G_START;
            codeFrequency |= 1 << 5 - i;
        }
        result.push(code.code);
        decodedCodes.push(code);
    }
    if (!self._determineParity(codeFrequency, result)) {
        return null;
    }

    return code;
};

UPCEReader.prototype._determineParity = function (codeFrequency, result) {
    var i, nrSystem;

    for (nrSystem = 0; nrSystem < this.CODE_FREQUENCY.length; nrSystem++) {
        for (i = 0; i < this.CODE_FREQUENCY[nrSystem].length; i++) {
            if (codeFrequency === this.CODE_FREQUENCY[nrSystem][i]) {
                result.unshift(nrSystem);
                result.push(i);
                return true;
            }
        }
    }
    return false;
};

UPCEReader.prototype._convertToUPCA = function (result) {
    var upca = [result[0]],
        lastDigit = result[result.length - 2];

    if (lastDigit <= 2) {
        upca = upca.concat(result.slice(1, 3)).concat([lastDigit, 0, 0, 0, 0]).concat(result.slice(3, 6));
    } else if (lastDigit === 3) {
        upca = upca.concat(result.slice(1, 4)).concat([0, 0, 0, 0, 0]).concat(result.slice(4, 6));
    } else if (lastDigit === 4) {
        upca = upca.concat(result.slice(1, 5)).concat([0, 0, 0, 0, 0, result[5]]);
    } else {
        upca = upca.concat(result.slice(1, 6)).concat([0, 0, 0, 0, lastDigit]);
    }

    upca.push(result[result.length - 1]);
    return upca;
};

UPCEReader.prototype._checksum = function (result) {
    return __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype._checksum.call(this, this._convertToUPCA(result));
};

UPCEReader.prototype._findEnd = function (offset, isWhite) {
    isWhite = true;
    return __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype._findEnd.call(this, offset, isWhite);
};

UPCEReader.prototype._verifyTrailingWhitespace = function (endInfo) {
    var self = this,
        trailingWhitespaceEnd;

    trailingWhitespaceEnd = endInfo.end + (endInfo.end - endInfo.start) / 2;
    if (trailingWhitespaceEnd < self._row.length) {
        if (self._matchRange(endInfo.end, trailingWhitespaceEnd, 0)) {
            return endInfo;
        }
    }
};

/* harmony default export */ __webpack_exports__["a"] = UPCEReader;

/***/ }),
/* 77 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ean_reader__ = __webpack_require__(4);


function UPCReader(opts, supplements) {
    __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].call(this, opts, supplements);
}

var properties = {
    FORMAT: { value: "upc_a", writeable: false }
};

UPCReader.prototype = Object.create(__WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype, properties);
UPCReader.prototype.constructor = UPCReader;

UPCReader.prototype._decode = function () {
    var result = __WEBPACK_IMPORTED_MODULE_0__ean_reader__["a" /* default */].prototype._decode.call(this);

    if (result && result.code && result.code.length === 13 && result.code.charAt(0) === "0") {
        result.code = result.code.substring(1);
        return result;
    }
    return null;
};

/* harmony default export */ __webpack_exports__["a"] = UPCReader;

/***/ }),
/* 78 */
/***/ (function(module, exports) {

module.exports = copy

/**
 * Copy the values from one mat2 to another
 *
 * @alias mat2.copy
 * @param {mat2} out the receiving matrix
 * @param {mat2} a the source matrix
 * @returns {mat2} out
 */
function copy(out, a) {
  out[0] = a[0]
  out[1] = a[1]
  out[2] = a[2]
  out[3] = a[3]
  return out
}


/***/ }),
/* 79 */
/***/ (function(module, exports) {

module.exports = create

/**
 * Creates a new identity mat2
 *
 * @alias mat2.create
 * @returns {mat2} a new 2x2 matrix
 */
function create() {
  var out = new Float32Array(4)
  out[0] = 1
  out[1] = 0
  out[2] = 0
  out[3] = 1
  return out
}


/***/ }),
/* 80 */
/***/ (function(module, exports) {

module.exports = invert

/**
 * Inverts a mat2
 *
 * @alias mat2.invert
 * @param {mat2} out the receiving matrix
 * @param {mat2} a the source matrix
 * @returns {mat2} out
 */
function invert(out, a) {
  var a0 = a[0]
  var a1 = a[1]
  var a2 = a[2]
  var a3 = a[3]
  var det = a0 * a3 - a2 * a1

  if (!det) return null
  det = 1.0 / det

  out[0] =  a3 * det
  out[1] = -a1 * det
  out[2] = -a2 * det
  out[3] =  a0 * det

  return out
}


/***/ }),
/* 81 */
/***/ (function(module, exports) {

module.exports = scale

/**
 * Scales a vec2 by a scalar number
 *
 * @param {vec2} out the receiving vector
 * @param {vec2} a the vector to scale
 * @param {Number} b amount to scale the vector by
 * @returns {vec2} out
 */
function scale(out, a, b) {
    out[0] = a[0] * b
    out[1] = a[1] * b
    return out
}

/***/ }),
/* 82 */
/***/ (function(module, exports) {

module.exports = transformMat2

/**
 * Transforms the vec2 with a mat2
 *
 * @param {vec2} out the receiving vector
 * @param {vec2} a the vector to transform
 * @param {mat2} m matrix to transform with
 * @returns {vec2} out
 */
function transformMat2(out, a, m) {
    var x = a[0],
        y = a[1]
    out[0] = m[0] * x + m[2] * y
    out[1] = m[1] * x + m[3] * y
    return out
}

/***/ }),
/* 83 */
/***/ (function(module, exports) {

module.exports = clone;

/**
 * Creates a new vec3 initialized with values from an existing vector
 *
 * @param {vec3} a vector to clone
 * @returns {vec3} a new 3D vector
 */
function clone(a) {
    var out = new Float32Array(3)
    out[0] = a[0]
    out[1] = a[1]
    out[2] = a[2]
    return out
}

/***/ }),
/* 84 */
/***/ (function(module, exports, __webpack_require__) {

var hashClear = __webpack_require__(122),
    hashDelete = __webpack_require__(123),
    hashGet = __webpack_require__(124),
    hashHas = __webpack_require__(125),
    hashSet = __webpack_require__(126);

/**
 * Creates a hash object.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function Hash(entries) {
  var index = -1,
      length = entries == null ? 0 : entries.length;

  this.clear();
  while (++index < length) {
    var entry = entries[index];
    this.set(entry[0], entry[1]);
  }
}

// Add methods to `Hash`.
Hash.prototype.clear = hashClear;
Hash.prototype['delete'] = hashDelete;
Hash.prototype.get = hashGet;
Hash.prototype.has = hashHas;
Hash.prototype.set = hashSet;

module.exports = Hash;


/***/ }),
/* 85 */
/***/ (function(module, exports, __webpack_require__) {

var ListCache = __webpack_require__(10),
    stackClear = __webpack_require__(149),
    stackDelete = __webpack_require__(150),
    stackGet = __webpack_require__(151),
    stackHas = __webpack_require__(152),
    stackSet = __webpack_require__(153);

/**
 * Creates a stack cache object to store key-value pairs.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function Stack(entries) {
  var data = this.__data__ = new ListCache(entries);
  this.size = data.size;
}

// Add methods to `Stack`.
Stack.prototype.clear = stackClear;
Stack.prototype['delete'] = stackDelete;
Stack.prototype.get = stackGet;
Stack.prototype.has = stackHas;
Stack.prototype.set = stackSet;

module.exports = Stack;


/***/ }),
/* 86 */
/***/ (function(module, exports, __webpack_require__) {

var root = __webpack_require__(5);

/** Built-in value references. */
var Uint8Array = root.Uint8Array;

module.exports = Uint8Array;


/***/ }),
/* 87 */
/***/ (function(module, exports) {

/**
 * A faster alternative to `Function#apply`, this function invokes `func`
 * with the `this` binding of `thisArg` and the arguments of `args`.
 *
 * @private
 * @param {Function} func The function to invoke.
 * @param {*} thisArg The `this` binding of `func`.
 * @param {Array} args The arguments to invoke `func` with.
 * @returns {*} Returns the result of `func`.
 */
function apply(func, thisArg, args) {
  switch (args.length) {
    case 0: return func.call(thisArg);
    case 1: return func.call(thisArg, args[0]);
    case 2: return func.call(thisArg, args[0], args[1]);
    case 3: return func.call(thisArg, args[0], args[1], args[2]);
  }
  return func.apply(thisArg, args);
}

module.exports = apply;


/***/ }),
/* 88 */
/***/ (function(module, exports, __webpack_require__) {

var baseTimes = __webpack_require__(107),
    isArguments = __webpack_require__(18),
    isArray = __webpack_require__(2),
    isBuffer = __webpack_require__(44),
    isIndex = __webpack_require__(15),
    isTypedArray = __webpack_require__(45);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Creates an array of the enumerable property names of the array-like `value`.
 *
 * @private
 * @param {*} value The value to query.
 * @param {boolean} inherited Specify returning inherited property names.
 * @returns {Array} Returns the array of property names.
 */
function arrayLikeKeys(value, inherited) {
  var isArr = isArray(value),
      isArg = !isArr && isArguments(value),
      isBuff = !isArr && !isArg && isBuffer(value),
      isType = !isArr && !isArg && !isBuff && isTypedArray(value),
      skipIndexes = isArr || isArg || isBuff || isType,
      result = skipIndexes ? baseTimes(value.length, String) : [],
      length = result.length;

  for (var key in value) {
    if ((inherited || hasOwnProperty.call(value, key)) &&
        !(skipIndexes && (
           // Safari 9 has enumerable `arguments.length` in strict mode.
           key == 'length' ||
           // Node.js 0.10 has enumerable non-index properties on buffers.
           (isBuff && (key == 'offset' || key == 'parent')) ||
           // PhantomJS 2 has enumerable non-index properties on typed arrays.
           (isType && (key == 'buffer' || key == 'byteLength' || key == 'byteOffset')) ||
           // Skip index properties.
           isIndex(key, length)
        ))) {
      result.push(key);
    }
  }
  return result;
}

module.exports = arrayLikeKeys;


/***/ }),
/* 89 */
/***/ (function(module, exports) {

/**
 * A specialized version of `_.map` for arrays without support for iteratee
 * shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} iteratee The function invoked per iteration.
 * @returns {Array} Returns the new mapped array.
 */
function arrayMap(array, iteratee) {
  var index = -1,
      length = array == null ? 0 : array.length,
      result = Array(length);

  while (++index < length) {
    result[index] = iteratee(array[index], index, array);
  }
  return result;
}

module.exports = arrayMap;


/***/ }),
/* 90 */
/***/ (function(module, exports) {

/**
 * Appends the elements of `values` to `array`.
 *
 * @private
 * @param {Array} array The array to modify.
 * @param {Array} values The values to append.
 * @returns {Array} Returns `array`.
 */
function arrayPush(array, values) {
  var index = -1,
      length = values.length,
      offset = array.length;

  while (++index < length) {
    array[offset + index] = values[index];
  }
  return array;
}

module.exports = arrayPush;


/***/ }),
/* 91 */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(0);

/** Built-in value references. */
var objectCreate = Object.create;

/**
 * The base implementation of `_.create` without support for assigning
 * properties to the created object.
 *
 * @private
 * @param {Object} proto The object to inherit from.
 * @returns {Object} Returns the new object.
 */
var baseCreate = (function() {
  function object() {}
  return function(proto) {
    if (!isObject(proto)) {
      return {};
    }
    if (objectCreate) {
      return objectCreate(proto);
    }
    object.prototype = proto;
    var result = new object;
    object.prototype = undefined;
    return result;
  };
}());

module.exports = baseCreate;


/***/ }),
/* 92 */
/***/ (function(module, exports, __webpack_require__) {

var arrayPush = __webpack_require__(90),
    isFlattenable = __webpack_require__(128);

/**
 * The base implementation of `_.flatten` with support for restricting flattening.
 *
 * @private
 * @param {Array} array The array to flatten.
 * @param {number} depth The maximum recursion depth.
 * @param {boolean} [predicate=isFlattenable] The function invoked per iteration.
 * @param {boolean} [isStrict] Restrict to values that pass `predicate` checks.
 * @param {Array} [result=[]] The initial result value.
 * @returns {Array} Returns the new flattened array.
 */
function baseFlatten(array, depth, predicate, isStrict, result) {
  var index = -1,
      length = array.length;

  predicate || (predicate = isFlattenable);
  result || (result = []);

  while (++index < length) {
    var value = array[index];
    if (depth > 0 && predicate(value)) {
      if (depth > 1) {
        // Recursively flatten arrays (susceptible to call stack limits).
        baseFlatten(value, depth - 1, predicate, isStrict, result);
      } else {
        arrayPush(result, value);
      }
    } else if (!isStrict) {
      result[result.length] = value;
    }
  }
  return result;
}

module.exports = baseFlatten;


/***/ }),
/* 93 */
/***/ (function(module, exports, __webpack_require__) {

var createBaseFor = __webpack_require__(117);

/**
 * The base implementation of `baseForOwn` which iterates over `object`
 * properties returned by `keysFunc` and invokes `iteratee` for each property.
 * Iteratee functions may exit iteration early by explicitly returning `false`.
 *
 * @private
 * @param {Object} object The object to iterate over.
 * @param {Function} iteratee The function invoked per iteration.
 * @param {Function} keysFunc The function to get the keys of `object`.
 * @returns {Object} Returns `object`.
 */
var baseFor = createBaseFor();

module.exports = baseFor;


/***/ }),
/* 94 */
/***/ (function(module, exports, __webpack_require__) {

var castPath = __webpack_require__(13),
    toKey = __webpack_require__(23);

/**
 * The base implementation of `_.get` without support for default values.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Array|string} path The path of the property to get.
 * @returns {*} Returns the resolved value.
 */
function baseGet(object, path) {
  path = castPath(path, object);

  var index = 0,
      length = path.length;

  while (object != null && index < length) {
    object = object[toKey(path[index++])];
  }
  return (index && index == length) ? object : undefined;
}

module.exports = baseGet;


/***/ }),
/* 95 */
/***/ (function(module, exports) {

/**
 * The base implementation of `_.hasIn` without support for deep paths.
 *
 * @private
 * @param {Object} [object] The object to query.
 * @param {Array|string} key The key to check.
 * @returns {boolean} Returns `true` if `key` exists, else `false`.
 */
function baseHasIn(object, key) {
  return object != null && key in Object(object);
}

module.exports = baseHasIn;


/***/ }),
/* 96 */
/***/ (function(module, exports, __webpack_require__) {

var baseGetTag = __webpack_require__(8),
    isObjectLike = __webpack_require__(6);

/** `Object#toString` result references. */
var argsTag = '[object Arguments]';

/**
 * The base implementation of `_.isArguments`.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an `arguments` object,
 */
function baseIsArguments(value) {
  return isObjectLike(value) && baseGetTag(value) == argsTag;
}

module.exports = baseIsArguments;


/***/ }),
/* 97 */
/***/ (function(module, exports, __webpack_require__) {

var isFunction = __webpack_require__(25),
    isMasked = __webpack_require__(132),
    isObject = __webpack_require__(0),
    toSource = __webpack_require__(155);

/**
 * Used to match `RegExp`
 * [syntax characters](http://ecma-international.org/ecma-262/7.0/#sec-patterns).
 */
var reRegExpChar = /[\\^$.*+?()[\]{}|]/g;

/** Used to detect host constructors (Safari). */
var reIsHostCtor = /^\[object .+?Constructor\]$/;

/** Used for built-in method references. */
var funcProto = Function.prototype,
    objectProto = Object.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Used to detect if a method is native. */
var reIsNative = RegExp('^' +
  funcToString.call(hasOwnProperty).replace(reRegExpChar, '\\$&')
  .replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, '$1.*?') + '$'
);

/**
 * The base implementation of `_.isNative` without bad shim checks.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a native function,
 *  else `false`.
 */
function baseIsNative(value) {
  if (!isObject(value) || isMasked(value)) {
    return false;
  }
  var pattern = isFunction(value) ? reIsNative : reIsHostCtor;
  return pattern.test(toSource(value));
}

module.exports = baseIsNative;


/***/ }),
/* 98 */
/***/ (function(module, exports, __webpack_require__) {

var baseGetTag = __webpack_require__(8),
    isLength = __webpack_require__(26),
    isObjectLike = __webpack_require__(6);

/** `Object#toString` result references. */
var argsTag = '[object Arguments]',
    arrayTag = '[object Array]',
    boolTag = '[object Boolean]',
    dateTag = '[object Date]',
    errorTag = '[object Error]',
    funcTag = '[object Function]',
    mapTag = '[object Map]',
    numberTag = '[object Number]',
    objectTag = '[object Object]',
    regexpTag = '[object RegExp]',
    setTag = '[object Set]',
    stringTag = '[object String]',
    weakMapTag = '[object WeakMap]';

var arrayBufferTag = '[object ArrayBuffer]',
    dataViewTag = '[object DataView]',
    float32Tag = '[object Float32Array]',
    float64Tag = '[object Float64Array]',
    int8Tag = '[object Int8Array]',
    int16Tag = '[object Int16Array]',
    int32Tag = '[object Int32Array]',
    uint8Tag = '[object Uint8Array]',
    uint8ClampedTag = '[object Uint8ClampedArray]',
    uint16Tag = '[object Uint16Array]',
    uint32Tag = '[object Uint32Array]';

/** Used to identify `toStringTag` values of typed arrays. */
var typedArrayTags = {};
typedArrayTags[float32Tag] = typedArrayTags[float64Tag] =
typedArrayTags[int8Tag] = typedArrayTags[int16Tag] =
typedArrayTags[int32Tag] = typedArrayTags[uint8Tag] =
typedArrayTags[uint8ClampedTag] = typedArrayTags[uint16Tag] =
typedArrayTags[uint32Tag] = true;
typedArrayTags[argsTag] = typedArrayTags[arrayTag] =
typedArrayTags[arrayBufferTag] = typedArrayTags[boolTag] =
typedArrayTags[dataViewTag] = typedArrayTags[dateTag] =
typedArrayTags[errorTag] = typedArrayTags[funcTag] =
typedArrayTags[mapTag] = typedArrayTags[numberTag] =
typedArrayTags[objectTag] = typedArrayTags[regexpTag] =
typedArrayTags[setTag] = typedArrayTags[stringTag] =
typedArrayTags[weakMapTag] = false;

/**
 * The base implementation of `_.isTypedArray` without Node.js optimizations.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
 */
function baseIsTypedArray(value) {
  return isObjectLike(value) &&
    isLength(value.length) && !!typedArrayTags[baseGetTag(value)];
}

module.exports = baseIsTypedArray;


/***/ }),
/* 99 */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(0),
    isPrototype = __webpack_require__(40),
    nativeKeysIn = __webpack_require__(144);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * The base implementation of `_.keysIn` which doesn't treat sparse arrays as dense.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 */
function baseKeysIn(object) {
  if (!isObject(object)) {
    return nativeKeysIn(object);
  }
  var isProto = isPrototype(object),
      result = [];

  for (var key in object) {
    if (!(key == 'constructor' && (isProto || !hasOwnProperty.call(object, key)))) {
      result.push(key);
    }
  }
  return result;
}

module.exports = baseKeysIn;


/***/ }),
/* 100 */
/***/ (function(module, exports, __webpack_require__) {

var Stack = __webpack_require__(85),
    assignMergeValue = __webpack_require__(35),
    baseFor = __webpack_require__(93),
    baseMergeDeep = __webpack_require__(101),
    isObject = __webpack_require__(0),
    keysIn = __webpack_require__(46);

/**
 * The base implementation of `_.merge` without support for multiple sources.
 *
 * @private
 * @param {Object} object The destination object.
 * @param {Object} source The source object.
 * @param {number} srcIndex The index of `source`.
 * @param {Function} [customizer] The function to customize merged values.
 * @param {Object} [stack] Tracks traversed source values and their merged
 *  counterparts.
 */
function baseMerge(object, source, srcIndex, customizer, stack) {
  if (object === source) {
    return;
  }
  baseFor(source, function(srcValue, key) {
    if (isObject(srcValue)) {
      stack || (stack = new Stack);
      baseMergeDeep(object, source, key, srcIndex, baseMerge, customizer, stack);
    }
    else {
      var newValue = customizer
        ? customizer(object[key], srcValue, (key + ''), object, source, stack)
        : undefined;

      if (newValue === undefined) {
        newValue = srcValue;
      }
      assignMergeValue(object, key, newValue);
    }
  }, keysIn);
}

module.exports = baseMerge;


/***/ }),
/* 101 */
/***/ (function(module, exports, __webpack_require__) {

var assignMergeValue = __webpack_require__(35),
    cloneBuffer = __webpack_require__(111),
    cloneTypedArray = __webpack_require__(112),
    copyArray = __webpack_require__(113),
    initCloneObject = __webpack_require__(127),
    isArguments = __webpack_require__(18),
    isArray = __webpack_require__(2),
    isArrayLikeObject = __webpack_require__(159),
    isBuffer = __webpack_require__(44),
    isFunction = __webpack_require__(25),
    isObject = __webpack_require__(0),
    isPlainObject = __webpack_require__(160),
    isTypedArray = __webpack_require__(45),
    toPlainObject = __webpack_require__(164);

/**
 * A specialized version of `baseMerge` for arrays and objects which performs
 * deep merges and tracks traversed objects enabling objects with circular
 * references to be merged.
 *
 * @private
 * @param {Object} object The destination object.
 * @param {Object} source The source object.
 * @param {string} key The key of the value to merge.
 * @param {number} srcIndex The index of `source`.
 * @param {Function} mergeFunc The function to merge values.
 * @param {Function} [customizer] The function to customize assigned values.
 * @param {Object} [stack] Tracks traversed source values and their merged
 *  counterparts.
 */
function baseMergeDeep(object, source, key, srcIndex, mergeFunc, customizer, stack) {
  var objValue = object[key],
      srcValue = source[key],
      stacked = stack.get(srcValue);

  if (stacked) {
    assignMergeValue(object, key, stacked);
    return;
  }
  var newValue = customizer
    ? customizer(objValue, srcValue, (key + ''), object, source, stack)
    : undefined;

  var isCommon = newValue === undefined;

  if (isCommon) {
    var isArr = isArray(srcValue),
        isBuff = !isArr && isBuffer(srcValue),
        isTyped = !isArr && !isBuff && isTypedArray(srcValue);

    newValue = srcValue;
    if (isArr || isBuff || isTyped) {
      if (isArray(objValue)) {
        newValue = objValue;
      }
      else if (isArrayLikeObject(objValue)) {
        newValue = copyArray(objValue);
      }
      else if (isBuff) {
        isCommon = false;
        newValue = cloneBuffer(srcValue, true);
      }
      else if (isTyped) {
        isCommon = false;
        newValue = cloneTypedArray(srcValue, true);
      }
      else {
        newValue = [];
      }
    }
    else if (isPlainObject(srcValue) || isArguments(srcValue)) {
      newValue = objValue;
      if (isArguments(objValue)) {
        newValue = toPlainObject(objValue);
      }
      else if (!isObject(objValue) || (srcIndex && isFunction(objValue))) {
        newValue = initCloneObject(srcValue);
      }
    }
    else {
      isCommon = false;
    }
  }
  if (isCommon) {
    // Recursively merge objects and arrays (susceptible to call stack limits).
    stack.set(srcValue, newValue);
    mergeFunc(newValue, srcValue, srcIndex, customizer, stack);
    stack['delete'](srcValue);
  }
  assignMergeValue(object, key, newValue);
}

module.exports = baseMergeDeep;


/***/ }),
/* 102 */
/***/ (function(module, exports, __webpack_require__) {

var basePickBy = __webpack_require__(103),
    hasIn = __webpack_require__(158);

/**
 * The base implementation of `_.pick` without support for individual
 * property identifiers.
 *
 * @private
 * @param {Object} object The source object.
 * @param {string[]} paths The property paths to pick.
 * @returns {Object} Returns the new object.
 */
function basePick(object, paths) {
  return basePickBy(object, paths, function(value, path) {
    return hasIn(object, path);
  });
}

module.exports = basePick;


/***/ }),
/* 103 */
/***/ (function(module, exports, __webpack_require__) {

var baseGet = __webpack_require__(94),
    baseSet = __webpack_require__(105),
    castPath = __webpack_require__(13);

/**
 * The base implementation of  `_.pickBy` without support for iteratee shorthands.
 *
 * @private
 * @param {Object} object The source object.
 * @param {string[]} paths The property paths to pick.
 * @param {Function} predicate The function invoked per property.
 * @returns {Object} Returns the new object.
 */
function basePickBy(object, paths, predicate) {
  var index = -1,
      length = paths.length,
      result = {};

  while (++index < length) {
    var path = paths[index],
        value = baseGet(object, path);

    if (predicate(value, path)) {
      baseSet(result, castPath(path, object), value);
    }
  }
  return result;
}

module.exports = basePickBy;


/***/ }),
/* 104 */
/***/ (function(module, exports, __webpack_require__) {

var identity = __webpack_require__(43),
    overRest = __webpack_require__(41),
    setToString = __webpack_require__(42);

/**
 * The base implementation of `_.rest` which doesn't validate or coerce arguments.
 *
 * @private
 * @param {Function} func The function to apply a rest parameter to.
 * @param {number} [start=func.length-1] The start position of the rest parameter.
 * @returns {Function} Returns the new function.
 */
function baseRest(func, start) {
  return setToString(overRest(func, start, identity), func + '');
}

module.exports = baseRest;


/***/ }),
/* 105 */
/***/ (function(module, exports, __webpack_require__) {

var assignValue = __webpack_require__(36),
    castPath = __webpack_require__(13),
    isIndex = __webpack_require__(15),
    isObject = __webpack_require__(0),
    toKey = __webpack_require__(23);

/**
 * The base implementation of `_.set`.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {Array|string} path The path of the property to set.
 * @param {*} value The value to set.
 * @param {Function} [customizer] The function to customize path creation.
 * @returns {Object} Returns `object`.
 */
function baseSet(object, path, value, customizer) {
  if (!isObject(object)) {
    return object;
  }
  path = castPath(path, object);

  var index = -1,
      length = path.length,
      lastIndex = length - 1,
      nested = object;

  while (nested != null && ++index < length) {
    var key = toKey(path[index]),
        newValue = value;

    if (index != lastIndex) {
      var objValue = nested[key];
      newValue = customizer ? customizer(objValue, key, nested) : undefined;
      if (newValue === undefined) {
        newValue = isObject(objValue)
          ? objValue
          : (isIndex(path[index + 1]) ? [] : {});
      }
    }
    assignValue(nested, key, newValue);
    nested = nested[key];
  }
  return object;
}

module.exports = baseSet;


/***/ }),
/* 106 */
/***/ (function(module, exports, __webpack_require__) {

var constant = __webpack_require__(156),
    defineProperty = __webpack_require__(37),
    identity = __webpack_require__(43);

/**
 * The base implementation of `setToString` without support for hot loop shorting.
 *
 * @private
 * @param {Function} func The function to modify.
 * @param {Function} string The `toString` result.
 * @returns {Function} Returns `func`.
 */
var baseSetToString = !defineProperty ? identity : function(func, string) {
  return defineProperty(func, 'toString', {
    'configurable': true,
    'enumerable': false,
    'value': constant(string),
    'writable': true
  });
};

module.exports = baseSetToString;


/***/ }),
/* 107 */
/***/ (function(module, exports) {

/**
 * The base implementation of `_.times` without support for iteratee shorthands
 * or max array length checks.
 *
 * @private
 * @param {number} n The number of times to invoke `iteratee`.
 * @param {Function} iteratee The function invoked per iteration.
 * @returns {Array} Returns the array of results.
 */
function baseTimes(n, iteratee) {
  var index = -1,
      result = Array(n);

  while (++index < n) {
    result[index] = iteratee(index);
  }
  return result;
}

module.exports = baseTimes;


/***/ }),
/* 108 */
/***/ (function(module, exports, __webpack_require__) {

var Symbol = __webpack_require__(11),
    arrayMap = __webpack_require__(89),
    isArray = __webpack_require__(2),
    isSymbol = __webpack_require__(27);

/** Used as references for various `Number` constants. */
var INFINITY = 1 / 0;

/** Used to convert symbols to primitives and strings. */
var symbolProto = Symbol ? Symbol.prototype : undefined,
    symbolToString = symbolProto ? symbolProto.toString : undefined;

/**
 * The base implementation of `_.toString` which doesn't convert nullish
 * values to empty strings.
 *
 * @private
 * @param {*} value The value to process.
 * @returns {string} Returns the string.
 */
function baseToString(value) {
  // Exit early for strings to avoid a performance hit in some environments.
  if (typeof value == 'string') {
    return value;
  }
  if (isArray(value)) {
    // Recursively convert values (susceptible to call stack limits).
    return arrayMap(value, baseToString) + '';
  }
  if (isSymbol(value)) {
    return symbolToString ? symbolToString.call(value) : '';
  }
  var result = (value + '');
  return (result == '0' && (1 / value) == -INFINITY) ? '-0' : result;
}

module.exports = baseToString;


/***/ }),
/* 109 */
/***/ (function(module, exports) {

/**
 * The base implementation of `_.unary` without support for storing metadata.
 *
 * @private
 * @param {Function} func The function to cap arguments for.
 * @returns {Function} Returns the new capped function.
 */
function baseUnary(func) {
  return function(value) {
    return func(value);
  };
}

module.exports = baseUnary;


/***/ }),
/* 110 */
/***/ (function(module, exports, __webpack_require__) {

var Uint8Array = __webpack_require__(86);

/**
 * Creates a clone of `arrayBuffer`.
 *
 * @private
 * @param {ArrayBuffer} arrayBuffer The array buffer to clone.
 * @returns {ArrayBuffer} Returns the cloned array buffer.
 */
function cloneArrayBuffer(arrayBuffer) {
  var result = new arrayBuffer.constructor(arrayBuffer.byteLength);
  new Uint8Array(result).set(new Uint8Array(arrayBuffer));
  return result;
}

module.exports = cloneArrayBuffer;


/***/ }),
/* 111 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(module) {var root = __webpack_require__(5);

/** Detect free variable `exports`. */
var freeExports = typeof exports == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && typeof module == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Built-in value references. */
var Buffer = moduleExports ? root.Buffer : undefined,
    allocUnsafe = Buffer ? Buffer.allocUnsafe : undefined;

/**
 * Creates a clone of  `buffer`.
 *
 * @private
 * @param {Buffer} buffer The buffer to clone.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Buffer} Returns the cloned buffer.
 */
function cloneBuffer(buffer, isDeep) {
  if (isDeep) {
    return buffer.slice();
  }
  var length = buffer.length,
      result = allocUnsafe ? allocUnsafe(length) : new buffer.constructor(length);

  buffer.copy(result);
  return result;
}

module.exports = cloneBuffer;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(29)(module)))

/***/ }),
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

var cloneArrayBuffer = __webpack_require__(110);

/**
 * Creates a clone of `typedArray`.
 *
 * @private
 * @param {Object} typedArray The typed array to clone.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the cloned typed array.
 */
function cloneTypedArray(typedArray, isDeep) {
  var buffer = isDeep ? cloneArrayBuffer(typedArray.buffer) : typedArray.buffer;
  return new typedArray.constructor(buffer, typedArray.byteOffset, typedArray.length);
}

module.exports = cloneTypedArray;


/***/ }),
/* 113 */
/***/ (function(module, exports) {

/**
 * Copies the values of `source` to `array`.
 *
 * @private
 * @param {Array} source The array to copy values from.
 * @param {Array} [array=[]] The array to copy values to.
 * @returns {Array} Returns `array`.
 */
function copyArray(source, array) {
  var index = -1,
      length = source.length;

  array || (array = Array(length));
  while (++index < length) {
    array[index] = source[index];
  }
  return array;
}

module.exports = copyArray;


/***/ }),
/* 114 */
/***/ (function(module, exports, __webpack_require__) {

var assignValue = __webpack_require__(36),
    baseAssignValue = __webpack_require__(21);

/**
 * Copies properties of `source` to `object`.
 *
 * @private
 * @param {Object} source The object to copy properties from.
 * @param {Array} props The property identifiers to copy.
 * @param {Object} [object={}] The object to copy properties to.
 * @param {Function} [customizer] The function to customize copied values.
 * @returns {Object} Returns `object`.
 */
function copyObject(source, props, object, customizer) {
  var isNew = !object;
  object || (object = {});

  var index = -1,
      length = props.length;

  while (++index < length) {
    var key = props[index];

    var newValue = customizer
      ? customizer(object[key], source[key], key, object, source)
      : undefined;

    if (newValue === undefined) {
      newValue = source[key];
    }
    if (isNew) {
      baseAssignValue(object, key, newValue);
    } else {
      assignValue(object, key, newValue);
    }
  }
  return object;
}

module.exports = copyObject;


/***/ }),
/* 115 */
/***/ (function(module, exports, __webpack_require__) {

var root = __webpack_require__(5);

/** Used to detect overreaching core-js shims. */
var coreJsData = root['__core-js_shared__'];

module.exports = coreJsData;


/***/ }),
/* 116 */
/***/ (function(module, exports, __webpack_require__) {

var baseRest = __webpack_require__(104),
    isIterateeCall = __webpack_require__(129);

/**
 * Creates a function like `_.assign`.
 *
 * @private
 * @param {Function} assigner The function to assign values.
 * @returns {Function} Returns the new assigner function.
 */
function createAssigner(assigner) {
  return baseRest(function(object, sources) {
    var index = -1,
        length = sources.length,
        customizer = length > 1 ? sources[length - 1] : undefined,
        guard = length > 2 ? sources[2] : undefined;

    customizer = (assigner.length > 3 && typeof customizer == 'function')
      ? (length--, customizer)
      : undefined;

    if (guard && isIterateeCall(sources[0], sources[1], guard)) {
      customizer = length < 3 ? undefined : customizer;
      length = 1;
    }
    object = Object(object);
    while (++index < length) {
      var source = sources[index];
      if (source) {
        assigner(object, source, index, customizer);
      }
    }
    return object;
  });
}

module.exports = createAssigner;


/***/ }),
/* 117 */
/***/ (function(module, exports) {

/**
 * Creates a base function for methods like `_.forIn` and `_.forOwn`.
 *
 * @private
 * @param {boolean} [fromRight] Specify iterating from right to left.
 * @returns {Function} Returns the new base function.
 */
function createBaseFor(fromRight) {
  return function(object, iteratee, keysFunc) {
    var index = -1,
        iterable = Object(object),
        props = keysFunc(object),
        length = props.length;

    while (length--) {
      var key = props[fromRight ? length : ++index];
      if (iteratee(iterable[key], key, iterable) === false) {
        break;
      }
    }
    return object;
  };
}

module.exports = createBaseFor;


/***/ }),
/* 118 */
/***/ (function(module, exports, __webpack_require__) {

var flatten = __webpack_require__(157),
    overRest = __webpack_require__(41),
    setToString = __webpack_require__(42);

/**
 * A specialized version of `baseRest` which flattens the rest array.
 *
 * @private
 * @param {Function} func The function to apply a rest parameter to.
 * @returns {Function} Returns the new function.
 */
function flatRest(func) {
  return setToString(overRest(func, undefined, flatten), func + '');
}

module.exports = flatRest;


/***/ }),
/* 119 */
/***/ (function(module, exports, __webpack_require__) {

var Symbol = __webpack_require__(11);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Used to resolve the
 * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
 * of values.
 */
var nativeObjectToString = objectProto.toString;

/** Built-in value references. */
var symToStringTag = Symbol ? Symbol.toStringTag : undefined;

/**
 * A specialized version of `baseGetTag` which ignores `Symbol.toStringTag` values.
 *
 * @private
 * @param {*} value The value to query.
 * @returns {string} Returns the raw `toStringTag`.
 */
function getRawTag(value) {
  var isOwn = hasOwnProperty.call(value, symToStringTag),
      tag = value[symToStringTag];

  try {
    value[symToStringTag] = undefined;
    var unmasked = true;
  } catch (e) {}

  var result = nativeObjectToString.call(value);
  if (unmasked) {
    if (isOwn) {
      value[symToStringTag] = tag;
    } else {
      delete value[symToStringTag];
    }
  }
  return result;
}

module.exports = getRawTag;


/***/ }),
/* 120 */
/***/ (function(module, exports) {

/**
 * Gets the value at `key` of `object`.
 *
 * @private
 * @param {Object} [object] The object to query.
 * @param {string} key The key of the property to get.
 * @returns {*} Returns the property value.
 */
function getValue(object, key) {
  return object == null ? undefined : object[key];
}

module.exports = getValue;


/***/ }),
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

var castPath = __webpack_require__(13),
    isArguments = __webpack_require__(18),
    isArray = __webpack_require__(2),
    isIndex = __webpack_require__(15),
    isLength = __webpack_require__(26),
    toKey = __webpack_require__(23);

/**
 * Checks if `path` exists on `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Array|string} path The path to check.
 * @param {Function} hasFunc The function to check properties.
 * @returns {boolean} Returns `true` if `path` exists, else `false`.
 */
function hasPath(object, path, hasFunc) {
  path = castPath(path, object);

  var index = -1,
      length = path.length,
      result = false;

  while (++index < length) {
    var key = toKey(path[index]);
    if (!(result = object != null && hasFunc(object, key))) {
      break;
    }
    object = object[key];
  }
  if (result || ++index != length) {
    return result;
  }
  length = object == null ? 0 : object.length;
  return !!length && isLength(length) && isIndex(key, length) &&
    (isArray(object) || isArguments(object));
}

module.exports = hasPath;


/***/ }),
/* 122 */
/***/ (function(module, exports, __webpack_require__) {

var nativeCreate = __webpack_require__(16);

/**
 * Removes all key-value entries from the hash.
 *
 * @private
 * @name clear
 * @memberOf Hash
 */
function hashClear() {
  this.__data__ = nativeCreate ? nativeCreate(null) : {};
  this.size = 0;
}

module.exports = hashClear;


/***/ }),
/* 123 */
/***/ (function(module, exports) {

/**
 * Removes `key` and its value from the hash.
 *
 * @private
 * @name delete
 * @memberOf Hash
 * @param {Object} hash The hash to modify.
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function hashDelete(key) {
  var result = this.has(key) && delete this.__data__[key];
  this.size -= result ? 1 : 0;
  return result;
}

module.exports = hashDelete;


/***/ }),
/* 124 */
/***/ (function(module, exports, __webpack_require__) {

var nativeCreate = __webpack_require__(16);

/** Used to stand-in for `undefined` hash values. */
var HASH_UNDEFINED = '__lodash_hash_undefined__';

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Gets the hash value for `key`.
 *
 * @private
 * @name get
 * @memberOf Hash
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function hashGet(key) {
  var data = this.__data__;
  if (nativeCreate) {
    var result = data[key];
    return result === HASH_UNDEFINED ? undefined : result;
  }
  return hasOwnProperty.call(data, key) ? data[key] : undefined;
}

module.exports = hashGet;


/***/ }),
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

var nativeCreate = __webpack_require__(16);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Checks if a hash value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf Hash
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function hashHas(key) {
  var data = this.__data__;
  return nativeCreate ? (data[key] !== undefined) : hasOwnProperty.call(data, key);
}

module.exports = hashHas;


/***/ }),
/* 126 */
/***/ (function(module, exports, __webpack_require__) {

var nativeCreate = __webpack_require__(16);

/** Used to stand-in for `undefined` hash values. */
var HASH_UNDEFINED = '__lodash_hash_undefined__';

/**
 * Sets the hash `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf Hash
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the hash instance.
 */
function hashSet(key, value) {
  var data = this.__data__;
  this.size += this.has(key) ? 0 : 1;
  data[key] = (nativeCreate && value === undefined) ? HASH_UNDEFINED : value;
  return this;
}

module.exports = hashSet;


/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

var baseCreate = __webpack_require__(91),
    getPrototype = __webpack_require__(39),
    isPrototype = __webpack_require__(40);

/**
 * Initializes an object clone.
 *
 * @private
 * @param {Object} object The object to clone.
 * @returns {Object} Returns the initialized clone.
 */
function initCloneObject(object) {
  return (typeof object.constructor == 'function' && !isPrototype(object))
    ? baseCreate(getPrototype(object))
    : {};
}

module.exports = initCloneObject;


/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

var Symbol = __webpack_require__(11),
    isArguments = __webpack_require__(18),
    isArray = __webpack_require__(2);

/** Built-in value references. */
var spreadableSymbol = Symbol ? Symbol.isConcatSpreadable : undefined;

/**
 * Checks if `value` is a flattenable `arguments` object or array.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is flattenable, else `false`.
 */
function isFlattenable(value) {
  return isArray(value) || isArguments(value) ||
    !!(spreadableSymbol && value && value[spreadableSymbol]);
}

module.exports = isFlattenable;


/***/ }),
/* 129 */
/***/ (function(module, exports, __webpack_require__) {

var eq = __webpack_require__(17),
    isArrayLike = __webpack_require__(24),
    isIndex = __webpack_require__(15),
    isObject = __webpack_require__(0);

/**
 * Checks if the given arguments are from an iteratee call.
 *
 * @private
 * @param {*} value The potential iteratee value argument.
 * @param {*} index The potential iteratee index or key argument.
 * @param {*} object The potential iteratee object argument.
 * @returns {boolean} Returns `true` if the arguments are from an iteratee call,
 *  else `false`.
 */
function isIterateeCall(value, index, object) {
  if (!isObject(object)) {
    return false;
  }
  var type = typeof index;
  if (type == 'number'
        ? (isArrayLike(object) && isIndex(index, object.length))
        : (type == 'string' && index in object)
      ) {
    return eq(object[index], value);
  }
  return false;
}

module.exports = isIterateeCall;


/***/ }),
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

var isArray = __webpack_require__(2),
    isSymbol = __webpack_require__(27);

/** Used to match property names within property paths. */
var reIsDeepProp = /\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,
    reIsPlainProp = /^\w*$/;

/**
 * Checks if `value` is a property name and not a property path.
 *
 * @private
 * @param {*} value The value to check.
 * @param {Object} [object] The object to query keys on.
 * @returns {boolean} Returns `true` if `value` is a property name, else `false`.
 */
function isKey(value, object) {
  if (isArray(value)) {
    return false;
  }
  var type = typeof value;
  if (type == 'number' || type == 'symbol' || type == 'boolean' ||
      value == null || isSymbol(value)) {
    return true;
  }
  return reIsPlainProp.test(value) || !reIsDeepProp.test(value) ||
    (object != null && value in Object(object));
}

module.exports = isKey;


/***/ }),
/* 131 */
/***/ (function(module, exports) {

/**
 * Checks if `value` is suitable for use as unique object key.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is suitable, else `false`.
 */
function isKeyable(value) {
  var type = typeof value;
  return (type == 'string' || type == 'number' || type == 'symbol' || type == 'boolean')
    ? (value !== '__proto__')
    : (value === null);
}

module.exports = isKeyable;


/***/ }),
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

var coreJsData = __webpack_require__(115);

/** Used to detect methods masquerading as native. */
var maskSrcKey = (function() {
  var uid = /[^.]+$/.exec(coreJsData && coreJsData.keys && coreJsData.keys.IE_PROTO || '');
  return uid ? ('Symbol(src)_1.' + uid) : '';
}());

/**
 * Checks if `func` has its source masked.
 *
 * @private
 * @param {Function} func The function to check.
 * @returns {boolean} Returns `true` if `func` is masked, else `false`.
 */
function isMasked(func) {
  return !!maskSrcKey && (maskSrcKey in func);
}

module.exports = isMasked;


/***/ }),
/* 133 */
/***/ (function(module, exports) {

/**
 * Removes all key-value entries from the list cache.
 *
 * @private
 * @name clear
 * @memberOf ListCache
 */
function listCacheClear() {
  this.__data__ = [];
  this.size = 0;
}

module.exports = listCacheClear;


/***/ }),
/* 134 */
/***/ (function(module, exports, __webpack_require__) {

var assocIndexOf = __webpack_require__(12);

/** Used for built-in method references. */
var arrayProto = Array.prototype;

/** Built-in value references. */
var splice = arrayProto.splice;

/**
 * Removes `key` and its value from the list cache.
 *
 * @private
 * @name delete
 * @memberOf ListCache
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function listCacheDelete(key) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  if (index < 0) {
    return false;
  }
  var lastIndex = data.length - 1;
  if (index == lastIndex) {
    data.pop();
  } else {
    splice.call(data, index, 1);
  }
  --this.size;
  return true;
}

module.exports = listCacheDelete;


/***/ }),
/* 135 */
/***/ (function(module, exports, __webpack_require__) {

var assocIndexOf = __webpack_require__(12);

/**
 * Gets the list cache value for `key`.
 *
 * @private
 * @name get
 * @memberOf ListCache
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function listCacheGet(key) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  return index < 0 ? undefined : data[index][1];
}

module.exports = listCacheGet;


/***/ }),
/* 136 */
/***/ (function(module, exports, __webpack_require__) {

var assocIndexOf = __webpack_require__(12);

/**
 * Checks if a list cache value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf ListCache
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function listCacheHas(key) {
  return assocIndexOf(this.__data__, key) > -1;
}

module.exports = listCacheHas;


/***/ }),
/* 137 */
/***/ (function(module, exports, __webpack_require__) {

var assocIndexOf = __webpack_require__(12);

/**
 * Sets the list cache `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf ListCache
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the list cache instance.
 */
function listCacheSet(key, value) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  if (index < 0) {
    ++this.size;
    data.push([key, value]);
  } else {
    data[index][1] = value;
  }
  return this;
}

module.exports = listCacheSet;


/***/ }),
/* 138 */
/***/ (function(module, exports, __webpack_require__) {

var Hash = __webpack_require__(84),
    ListCache = __webpack_require__(10),
    Map = __webpack_require__(33);

/**
 * Removes all key-value entries from the map.
 *
 * @private
 * @name clear
 * @memberOf MapCache
 */
function mapCacheClear() {
  this.size = 0;
  this.__data__ = {
    'hash': new Hash,
    'map': new (Map || ListCache),
    'string': new Hash
  };
}

module.exports = mapCacheClear;


/***/ }),
/* 139 */
/***/ (function(module, exports, __webpack_require__) {

var getMapData = __webpack_require__(14);

/**
 * Removes `key` and its value from the map.
 *
 * @private
 * @name delete
 * @memberOf MapCache
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function mapCacheDelete(key) {
  var result = getMapData(this, key)['delete'](key);
  this.size -= result ? 1 : 0;
  return result;
}

module.exports = mapCacheDelete;


/***/ }),
/* 140 */
/***/ (function(module, exports, __webpack_require__) {

var getMapData = __webpack_require__(14);

/**
 * Gets the map value for `key`.
 *
 * @private
 * @name get
 * @memberOf MapCache
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function mapCacheGet(key) {
  return getMapData(this, key).get(key);
}

module.exports = mapCacheGet;


/***/ }),
/* 141 */
/***/ (function(module, exports, __webpack_require__) {

var getMapData = __webpack_require__(14);

/**
 * Checks if a map value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf MapCache
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function mapCacheHas(key) {
  return getMapData(this, key).has(key);
}

module.exports = mapCacheHas;


/***/ }),
/* 142 */
/***/ (function(module, exports, __webpack_require__) {

var getMapData = __webpack_require__(14);

/**
 * Sets the map `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf MapCache
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the map cache instance.
 */
function mapCacheSet(key, value) {
  var data = getMapData(this, key),
      size = data.size;

  data.set(key, value);
  this.size += data.size == size ? 0 : 1;
  return this;
}

module.exports = mapCacheSet;


/***/ }),
/* 143 */
/***/ (function(module, exports, __webpack_require__) {

var memoize = __webpack_require__(161);

/** Used as the maximum memoize cache size. */
var MAX_MEMOIZE_SIZE = 500;

/**
 * A specialized version of `_.memoize` which clears the memoized function's
 * cache when it exceeds `MAX_MEMOIZE_SIZE`.
 *
 * @private
 * @param {Function} func The function to have its output memoized.
 * @returns {Function} Returns the new memoized function.
 */
function memoizeCapped(func) {
  var result = memoize(func, function(key) {
    if (cache.size === MAX_MEMOIZE_SIZE) {
      cache.clear();
    }
    return key;
  });

  var cache = result.cache;
  return result;
}

module.exports = memoizeCapped;


/***/ }),
/* 144 */
/***/ (function(module, exports) {

/**
 * This function is like
 * [`Object.keys`](http://ecma-international.org/ecma-262/7.0/#sec-object.keys)
 * except that it includes inherited enumerable properties.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 */
function nativeKeysIn(object) {
  var result = [];
  if (object != null) {
    for (var key in Object(object)) {
      result.push(key);
    }
  }
  return result;
}

module.exports = nativeKeysIn;


/***/ }),
/* 145 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(module) {var freeGlobal = __webpack_require__(38);

/** Detect free variable `exports`. */
var freeExports = typeof exports == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && typeof module == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Detect free variable `process` from Node.js. */
var freeProcess = moduleExports && freeGlobal.process;

/** Used to access faster Node.js helpers. */
var nodeUtil = (function() {
  try {
    return freeProcess && freeProcess.binding && freeProcess.binding('util');
  } catch (e) {}
}());

module.exports = nodeUtil;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(29)(module)))

/***/ }),
/* 146 */
/***/ (function(module, exports) {

/** Used for built-in method references. */
var objectProto = Object.prototype;

/**
 * Used to resolve the
 * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
 * of values.
 */
var nativeObjectToString = objectProto.toString;

/**
 * Converts `value` to a string using `Object.prototype.toString`.
 *
 * @private
 * @param {*} value The value to convert.
 * @returns {string} Returns the converted string.
 */
function objectToString(value) {
  return nativeObjectToString.call(value);
}

module.exports = objectToString;


/***/ }),
/* 147 */
/***/ (function(module, exports) {

/**
 * Creates a unary function that invokes `func` with its argument transformed.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {Function} transform The argument transform.
 * @returns {Function} Returns the new function.
 */
function overArg(func, transform) {
  return function(arg) {
    return func(transform(arg));
  };
}

module.exports = overArg;


/***/ }),
/* 148 */
/***/ (function(module, exports) {

/** Used to detect hot functions by number of calls within a span of milliseconds. */
var HOT_COUNT = 800,
    HOT_SPAN = 16;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeNow = Date.now;

/**
 * Creates a function that'll short out and invoke `identity` instead
 * of `func` when it's called `HOT_COUNT` or more times in `HOT_SPAN`
 * milliseconds.
 *
 * @private
 * @param {Function} func The function to restrict.
 * @returns {Function} Returns the new shortable function.
 */
function shortOut(func) {
  var count = 0,
      lastCalled = 0;

  return function() {
    var stamp = nativeNow(),
        remaining = HOT_SPAN - (stamp - lastCalled);

    lastCalled = stamp;
    if (remaining > 0) {
      if (++count >= HOT_COUNT) {
        return arguments[0];
      }
    } else {
      count = 0;
    }
    return func.apply(undefined, arguments);
  };
}

module.exports = shortOut;


/***/ }),
/* 149 */
/***/ (function(module, exports, __webpack_require__) {

var ListCache = __webpack_require__(10);

/**
 * Removes all key-value entries from the stack.
 *
 * @private
 * @name clear
 * @memberOf Stack
 */
function stackClear() {
  this.__data__ = new ListCache;
  this.size = 0;
}

module.exports = stackClear;


/***/ }),
/* 150 */
/***/ (function(module, exports) {

/**
 * Removes `key` and its value from the stack.
 *
 * @private
 * @name delete
 * @memberOf Stack
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function stackDelete(key) {
  var data = this.__data__,
      result = data['delete'](key);

  this.size = data.size;
  return result;
}

module.exports = stackDelete;


/***/ }),
/* 151 */
/***/ (function(module, exports) {

/**
 * Gets the stack value for `key`.
 *
 * @private
 * @name get
 * @memberOf Stack
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function stackGet(key) {
  return this.__data__.get(key);
}

module.exports = stackGet;


/***/ }),
/* 152 */
/***/ (function(module, exports) {

/**
 * Checks if a stack value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf Stack
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function stackHas(key) {
  return this.__data__.has(key);
}

module.exports = stackHas;


/***/ }),
/* 153 */
/***/ (function(module, exports, __webpack_require__) {

var ListCache = __webpack_require__(10),
    Map = __webpack_require__(33),
    MapCache = __webpack_require__(34);

/** Used as the size to enable large array optimizations. */
var LARGE_ARRAY_SIZE = 200;

/**
 * Sets the stack `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf Stack
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the stack cache instance.
 */
function stackSet(key, value) {
  var data = this.__data__;
  if (data instanceof ListCache) {
    var pairs = data.__data__;
    if (!Map || (pairs.length < LARGE_ARRAY_SIZE - 1)) {
      pairs.push([key, value]);
      this.size = ++data.size;
      return this;
    }
    data = this.__data__ = new MapCache(pairs);
  }
  data.set(key, value);
  this.size = data.size;
  return this;
}

module.exports = stackSet;


/***/ }),
/* 154 */
/***/ (function(module, exports, __webpack_require__) {

var memoizeCapped = __webpack_require__(143);

/** Used to match property names within property paths. */
var reLeadingDot = /^\./,
    rePropName = /[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g;

/** Used to match backslashes in property paths. */
var reEscapeChar = /\\(\\)?/g;

/**
 * Converts `string` to a property path array.
 *
 * @private
 * @param {string} string The string to convert.
 * @returns {Array} Returns the property path array.
 */
var stringToPath = memoizeCapped(function(string) {
  var result = [];
  if (reLeadingDot.test(string)) {
    result.push('');
  }
  string.replace(rePropName, function(match, number, quote, string) {
    result.push(quote ? string.replace(reEscapeChar, '$1') : (number || match));
  });
  return result;
});

module.exports = stringToPath;


/***/ }),
/* 155 */
/***/ (function(module, exports) {

/** Used for built-in method references. */
var funcProto = Function.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/**
 * Converts `func` to its source code.
 *
 * @private
 * @param {Function} func The function to convert.
 * @returns {string} Returns the source code.
 */
function toSource(func) {
  if (func != null) {
    try {
      return funcToString.call(func);
    } catch (e) {}
    try {
      return (func + '');
    } catch (e) {}
  }
  return '';
}

module.exports = toSource;


/***/ }),
/* 156 */
/***/ (function(module, exports) {

/**
 * Creates a function that returns `value`.
 *
 * @static
 * @memberOf _
 * @since 2.4.0
 * @category Util
 * @param {*} value The value to return from the new function.
 * @returns {Function} Returns the new constant function.
 * @example
 *
 * var objects = _.times(2, _.constant({ 'a': 1 }));
 *
 * console.log(objects);
 * // => [{ 'a': 1 }, { 'a': 1 }]
 *
 * console.log(objects[0] === objects[1]);
 * // => true
 */
function constant(value) {
  return function() {
    return value;
  };
}

module.exports = constant;


/***/ }),
/* 157 */
/***/ (function(module, exports, __webpack_require__) {

var baseFlatten = __webpack_require__(92);

/**
 * Flattens `array` a single level deep.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Array
 * @param {Array} array The array to flatten.
 * @returns {Array} Returns the new flattened array.
 * @example
 *
 * _.flatten([1, [2, [3, [4]], 5]]);
 * // => [1, 2, [3, [4]], 5]
 */
function flatten(array) {
  var length = array == null ? 0 : array.length;
  return length ? baseFlatten(array, 1) : [];
}

module.exports = flatten;


/***/ }),
/* 158 */
/***/ (function(module, exports, __webpack_require__) {

var baseHasIn = __webpack_require__(95),
    hasPath = __webpack_require__(121);

/**
 * Checks if `path` is a direct or inherited property of `object`.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Object
 * @param {Object} object The object to query.
 * @param {Array|string} path The path to check.
 * @returns {boolean} Returns `true` if `path` exists, else `false`.
 * @example
 *
 * var object = _.create({ 'a': _.create({ 'b': 2 }) });
 *
 * _.hasIn(object, 'a');
 * // => true
 *
 * _.hasIn(object, 'a.b');
 * // => true
 *
 * _.hasIn(object, ['a', 'b']);
 * // => true
 *
 * _.hasIn(object, 'b');
 * // => false
 */
function hasIn(object, path) {
  return object != null && hasPath(object, path, baseHasIn);
}

module.exports = hasIn;


/***/ }),
/* 159 */
/***/ (function(module, exports, __webpack_require__) {

var isArrayLike = __webpack_require__(24),
    isObjectLike = __webpack_require__(6);

/**
 * This method is like `_.isArrayLike` except that it also checks if `value`
 * is an object.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an array-like object,
 *  else `false`.
 * @example
 *
 * _.isArrayLikeObject([1, 2, 3]);
 * // => true
 *
 * _.isArrayLikeObject(document.body.children);
 * // => true
 *
 * _.isArrayLikeObject('abc');
 * // => false
 *
 * _.isArrayLikeObject(_.noop);
 * // => false
 */
function isArrayLikeObject(value) {
  return isObjectLike(value) && isArrayLike(value);
}

module.exports = isArrayLikeObject;


/***/ }),
/* 160 */
/***/ (function(module, exports, __webpack_require__) {

var baseGetTag = __webpack_require__(8),
    getPrototype = __webpack_require__(39),
    isObjectLike = __webpack_require__(6);

/** `Object#toString` result references. */
var objectTag = '[object Object]';

/** Used for built-in method references. */
var funcProto = Function.prototype,
    objectProto = Object.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Used to infer the `Object` constructor. */
var objectCtorString = funcToString.call(Object);

/**
 * Checks if `value` is a plain object, that is, an object created by the
 * `Object` constructor or one with a `[[Prototype]]` of `null`.
 *
 * @static
 * @memberOf _
 * @since 0.8.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a plain object, else `false`.
 * @example
 *
 * function Foo() {
 *   this.a = 1;
 * }
 *
 * _.isPlainObject(new Foo);
 * // => false
 *
 * _.isPlainObject([1, 2, 3]);
 * // => false
 *
 * _.isPlainObject({ 'x': 0, 'y': 0 });
 * // => true
 *
 * _.isPlainObject(Object.create(null));
 * // => true
 */
function isPlainObject(value) {
  if (!isObjectLike(value) || baseGetTag(value) != objectTag) {
    return false;
  }
  var proto = getPrototype(value);
  if (proto === null) {
    return true;
  }
  var Ctor = hasOwnProperty.call(proto, 'constructor') && proto.constructor;
  return typeof Ctor == 'function' && Ctor instanceof Ctor &&
    funcToString.call(Ctor) == objectCtorString;
}

module.exports = isPlainObject;


/***/ }),
/* 161 */
/***/ (function(module, exports, __webpack_require__) {

var MapCache = __webpack_require__(34);

/** Error message constants. */
var FUNC_ERROR_TEXT = 'Expected a function';

/**
 * Creates a function that memoizes the result of `func`. If `resolver` is
 * provided, it determines the cache key for storing the result based on the
 * arguments provided to the memoized function. By default, the first argument
 * provided to the memoized function is used as the map cache key. The `func`
 * is invoked with the `this` binding of the memoized function.
 *
 * **Note:** The cache is exposed as the `cache` property on the memoized
 * function. Its creation may be customized by replacing the `_.memoize.Cache`
 * constructor with one whose instances implement the
 * [`Map`](http://ecma-international.org/ecma-262/7.0/#sec-properties-of-the-map-prototype-object)
 * method interface of `clear`, `delete`, `get`, `has`, and `set`.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Function
 * @param {Function} func The function to have its output memoized.
 * @param {Function} [resolver] The function to resolve the cache key.
 * @returns {Function} Returns the new memoized function.
 * @example
 *
 * var object = { 'a': 1, 'b': 2 };
 * var other = { 'c': 3, 'd': 4 };
 *
 * var values = _.memoize(_.values);
 * values(object);
 * // => [1, 2]
 *
 * values(other);
 * // => [3, 4]
 *
 * object.a = 2;
 * values(object);
 * // => [1, 2]
 *
 * // Modify the result cache.
 * values.cache.set(object, ['a', 'b']);
 * values(object);
 * // => ['a', 'b']
 *
 * // Replace `_.memoize.Cache`.
 * _.memoize.Cache = WeakMap;
 */
function memoize(func, resolver) {
  if (typeof func != 'function' || (resolver != null && typeof resolver != 'function')) {
    throw new TypeError(FUNC_ERROR_TEXT);
  }
  var memoized = function() {
    var args = arguments,
        key = resolver ? resolver.apply(this, args) : args[0],
        cache = memoized.cache;

    if (cache.has(key)) {
      return cache.get(key);
    }
    var result = func.apply(this, args);
    memoized.cache = cache.set(key, result) || cache;
    return result;
  };
  memoized.cache = new (memoize.Cache || MapCache);
  return memoized;
}

// Expose `MapCache`.
memoize.Cache = MapCache;

module.exports = memoize;


/***/ }),
/* 162 */
/***/ (function(module, exports, __webpack_require__) {

var basePick = __webpack_require__(102),
    flatRest = __webpack_require__(118);

/**
 * Creates an object composed of the picked `object` properties.
 *
 * @static
 * @since 0.1.0
 * @memberOf _
 * @category Object
 * @param {Object} object The source object.
 * @param {...(string|string[])} [paths] The property paths to pick.
 * @returns {Object} Returns the new object.
 * @example
 *
 * var object = { 'a': 1, 'b': '2', 'c': 3 };
 *
 * _.pick(object, ['a', 'c']);
 * // => { 'a': 1, 'c': 3 }
 */
var pick = flatRest(function(object, paths) {
  return object == null ? {} : basePick(object, paths);
});

module.exports = pick;


/***/ }),
/* 163 */
/***/ (function(module, exports) {

/**
 * This method returns `false`.
 *
 * @static
 * @memberOf _
 * @since 4.13.0
 * @category Util
 * @returns {boolean} Returns `false`.
 * @example
 *
 * _.times(2, _.stubFalse);
 * // => [false, false]
 */
function stubFalse() {
  return false;
}

module.exports = stubFalse;


/***/ }),
/* 164 */
/***/ (function(module, exports, __webpack_require__) {

var copyObject = __webpack_require__(114),
    keysIn = __webpack_require__(46);

/**
 * Converts `value` to a plain object flattening inherited enumerable string
 * keyed properties of `value` to own properties of the plain object.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Lang
 * @param {*} value The value to convert.
 * @returns {Object} Returns the converted plain object.
 * @example
 *
 * function Foo() {
 *   this.b = 2;
 * }
 *
 * Foo.prototype.c = 3;
 *
 * _.assign({ 'a': 1 }, new Foo);
 * // => { 'a': 1, 'b': 2 }
 *
 * _.assign({ 'a': 1 }, _.toPlainObject(new Foo));
 * // => { 'a': 1, 'b': 2, 'c': 3 }
 */
function toPlainObject(value) {
  return copyObject(value, keysIn(value));
}

module.exports = toPlainObject;


/***/ }),
/* 165 */
/***/ (function(module, exports, __webpack_require__) {

var baseToString = __webpack_require__(108);

/**
 * Converts `value` to a string. An empty string is returned for `null`
 * and `undefined` values. The sign of `-0` is preserved.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to convert.
 * @returns {string} Returns the converted string.
 * @example
 *
 * _.toString(null);
 * // => ''
 *
 * _.toString(-0);
 * // => '-0'
 *
 * _.toString([1, 2, 3]);
 * // => '1,2,3'
 */
function toString(value) {
  return value == null ? '' : baseToString(value);
}

module.exports = toString;


/***/ }),
/* 166 */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(48);


/***/ })
/******/ ])
});
;
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vd2VicGFjay9teU1vZHVsZURlZmluaXRpb24iLCJ3ZWJwYWNrOi8vL3dlYnBhY2svYm9vdHN0cmFwIDc1NjBjYzQ5MjcyMzFiMmUzNzg5Iiwid2VicGFjazovLy8uL34vbG9kYXNoL2lzT2JqZWN0LmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvYmFyY29kZV9yZWFkZXIuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNBcnJheS5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvY29tbW9uL2FycmF5X2hlbHBlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcmVhZGVyL2Vhbl9yZWFkZXIuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3Jvb3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNPYmplY3RMaWtlLmpzIiwid2VicGFjazovLy8uL34vZ2wtdmVjMi9jbG9uZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUdldFRhZy5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvY29tbW9uL2ltYWdlX2RlYnVnLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19MaXN0Q2FjaGUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX1N5bWJvbC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYXNzb2NJbmRleE9mLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19jYXN0UGF0aC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fZ2V0TWFwRGF0YS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faXNJbmRleC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fbmF0aXZlQ3JlYXRlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2VxLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2lzQXJndW1lbnRzLmpzIiwid2VicGFjazovLy8uL3NyYy9jb21tb24vY3ZfdXRpbHMuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2NvbW1vbi9pbWFnZV93cmFwcGVyLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlQXNzaWduVmFsdWUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2dldE5hdGl2ZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fdG9LZXkuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNBcnJheUxpa2UuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNGdW5jdGlvbi5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9pc0xlbmd0aC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9pc1N5bWJvbC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9tZXJnZS5qcyIsIndlYnBhY2s6Ly8vKHdlYnBhY2spL2J1aWxkaW4vbW9kdWxlLmpzIiwid2VicGFjazovLy8uL3NyYy9sb2NhdG9yL3RyYWNlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcmVhZGVyL2NvZGVfMzlfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL34vZ2wtdmVjMi9kb3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX01hcC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fTWFwQ2FjaGUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Fzc2lnbk1lcmdlVmFsdWUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Fzc2lnblZhbHVlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19kZWZpbmVQcm9wZXJ0eS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fZnJlZUdsb2JhbC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fZ2V0UHJvdG90eXBlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19pc1Byb3RvdHlwZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fb3ZlclJlc3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3NldFRvU3RyaW5nLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2lkZW50aXR5LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2lzQnVmZmVyLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2lzVHlwZWRBcnJheS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9rZXlzSW4uanMiLCJ3ZWJwYWNrOi8vLyh3ZWJwYWNrKS9idWlsZGluL2dsb2JhbC5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcXVhZ2dhLmpzIiwid2VicGFjazovLy8uL3NyYy9hbmFseXRpY3MvcmVzdWx0X2NvbGxlY3Rvci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvY29tbW9uL2NsdXN0ZXIuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2NvbW1vbi9ldmVudHMuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2NvbW1vbi9tZWRpYURldmljZXMuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2NvbW1vbi9zdWJJbWFnZS5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvY29tbW9uL3R5cGVkZWZzLmpzIiwid2VicGFjazovLy8uL3NyYy9jb25maWcvY29uZmlnLmRldi5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvY29uZmlnL2NvbmZpZy5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvZGVjb2Rlci9iYXJjb2RlX2RlY29kZXIuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2RlY29kZXIvYnJlc2VuaGFtLmpzIiwid2VicGFjazovLy8uL3NyYy9pbnB1dC9jYW1lcmFfYWNjZXNzLmpzIiwid2VicGFjazovLy8uL3NyYy9pbnB1dC9leGlmX2hlbHBlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvaW5wdXQvZnJhbWVfZ3JhYmJlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvaW5wdXQvaW1hZ2VfbG9hZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9pbnB1dC9pbnB1dF9zdHJlYW0uanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2xvY2F0b3IvYmFyY29kZV9sb2NhdG9yLmpzIiwid2VicGFjazovLy8uL3NyYy9sb2NhdG9yL3Jhc3Rlcml6ZXIuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2xvY2F0b3Ivc2tlbGV0b25pemVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvMm9mNV9yZWFkZXIuanMiLCJ3ZWJwYWNrOi8vLy4vc3JjL3JlYWRlci9jb2RhYmFyX3JlYWRlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcmVhZGVyL2NvZGVfMTI4X3JlYWRlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcmVhZGVyL2NvZGVfMzlfdmluX3JlYWRlci5qcyIsIndlYnBhY2s6Ly8vLi9zcmMvcmVhZGVyL2NvZGVfOTNfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvZWFuXzJfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvZWFuXzVfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvZWFuXzhfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvaTJvZjVfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvdXBjX2VfcmVhZGVyLmpzIiwid2VicGFjazovLy8uL3NyYy9yZWFkZXIvdXBjX3JlYWRlci5qcyIsIndlYnBhY2s6Ly8vLi9+L2dsLW1hdDIvY29weS5qcyIsIndlYnBhY2s6Ly8vLi9+L2dsLW1hdDIvY3JlYXRlLmpzIiwid2VicGFjazovLy8uL34vZ2wtbWF0Mi9pbnZlcnQuanMiLCJ3ZWJwYWNrOi8vLy4vfi9nbC12ZWMyL3NjYWxlLmpzIiwid2VicGFjazovLy8uL34vZ2wtdmVjMi90cmFuc2Zvcm1NYXQyLmpzIiwid2VicGFjazovLy8uL34vZ2wtdmVjMy9jbG9uZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fSGFzaC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fU3RhY2suanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX1VpbnQ4QXJyYXkuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2FwcGx5LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19hcnJheUxpa2VLZXlzLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19hcnJheU1hcC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYXJyYXlQdXNoLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlQ3JlYXRlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlRmxhdHRlbi5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUZvci5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUdldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUhhc0luLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlSXNBcmd1bWVudHMuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Jhc2VJc05hdGl2ZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUlzVHlwZWRBcnJheS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZUtleXNJbi5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZU1lcmdlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlTWVyZ2VEZWVwLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlUGljay5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZVBpY2tCeS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZVJlc3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Jhc2VTZXQuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Jhc2VTZXRUb1N0cmluZy5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fYmFzZVRpbWVzLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19iYXNlVG9TdHJpbmcuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Jhc2VVbmFyeS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fY2xvbmVBcnJheUJ1ZmZlci5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fY2xvbmVCdWZmZXIuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2Nsb25lVHlwZWRBcnJheS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fY29weUFycmF5LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19jb3B5T2JqZWN0LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19jb3JlSnNEYXRhLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19jcmVhdGVBc3NpZ25lci5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fY3JlYXRlQmFzZUZvci5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fZmxhdFJlc3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2dldFJhd1RhZy5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fZ2V0VmFsdWUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2hhc1BhdGguanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2hhc2hDbGVhci5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faGFzaERlbGV0ZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faGFzaEdldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faGFzaEhhcy5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faGFzaFNldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faW5pdENsb25lT2JqZWN0LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19pc0ZsYXR0ZW5hYmxlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19pc0l0ZXJhdGVlQ2FsbC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faXNLZXkuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2lzS2V5YWJsZS5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9faXNNYXNrZWQuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2xpc3RDYWNoZUNsZWFyLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19saXN0Q2FjaGVEZWxldGUuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX2xpc3RDYWNoZUdldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fbGlzdENhY2hlSGFzLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19saXN0Q2FjaGVTZXQuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX21hcENhY2hlQ2xlYXIuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX21hcENhY2hlRGVsZXRlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19tYXBDYWNoZUdldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fbWFwQ2FjaGVIYXMuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX21hcENhY2hlU2V0LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19tZW1vaXplQ2FwcGVkLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19uYXRpdmVLZXlzSW4uanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX25vZGVVdGlsLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19vYmplY3RUb1N0cmluZy5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fb3ZlckFyZy5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fc2hvcnRPdXQuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3N0YWNrQ2xlYXIuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3N0YWNrRGVsZXRlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19zdGFja0dldC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9fc3RhY2tIYXMuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3N0YWNrU2V0LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL19zdHJpbmdUb1BhdGguanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvX3RvU291cmNlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2NvbnN0YW50LmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL2ZsYXR0ZW4uanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaGFzSW4uanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNBcnJheUxpa2VPYmplY3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvaXNQbGFpbk9iamVjdC5qcyIsIndlYnBhY2s6Ly8vLi9+L2xvZGFzaC9tZW1vaXplLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL3BpY2suanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvc3R1YkZhbHNlLmpzIiwid2VicGFjazovLy8uL34vbG9kYXNoL3RvUGxhaW5PYmplY3QuanMiLCJ3ZWJwYWNrOi8vLy4vfi9sb2Rhc2gvdG9TdHJpbmcuanMiXSwibmFtZXMiOlsiQmFyY29kZVJlYWRlciIsImNvbmZpZyIsInN1cHBsZW1lbnRzIiwiX3JvdyIsInByb3RvdHlwZSIsIl9uZXh0VW5zZXQiLCJsaW5lIiwic3RhcnQiLCJpIiwidW5kZWZpbmVkIiwibGVuZ3RoIiwiX21hdGNoUGF0dGVybiIsImNvdW50ZXIiLCJjb2RlIiwibWF4U2luZ2xlRXJyb3IiLCJlcnJvciIsInNpbmdsZUVycm9yIiwic3VtIiwibW9kdWxvIiwiYmFyV2lkdGgiLCJjb3VudCIsInNjYWxlZCIsIlNJTkdMRV9DT0RFX0VSUk9SIiwiTnVtYmVyIiwiTUFYX1ZBTFVFIiwiTWF0aCIsImFicyIsIl9uZXh0U2V0Iiwib2Zmc2V0IiwiX2NvcnJlY3RCYXJzIiwiY29ycmVjdGlvbiIsImluZGljZXMiLCJ0bXAiLCJfbWF0Y2hUcmFjZSIsImNtcENvdW50ZXIiLCJlcHNpbG9uIiwic2VsZiIsImlzV2hpdGUiLCJjb3VudGVyUG9zIiwiYmVzdE1hdGNoIiwicHVzaCIsImVuZCIsImRlY29kZVBhdHRlcm4iLCJwYXR0ZXJuIiwicmVzdWx0IiwiX2RlY29kZSIsInJldmVyc2UiLCJkaXJlY3Rpb24iLCJESVJFQ1RJT04iLCJSRVZFUlNFIiwiRk9SV0FSRCIsImZvcm1hdCIsIkZPUk1BVCIsIl9tYXRjaFJhbmdlIiwidmFsdWUiLCJfZmlsbENvdW50ZXJzIiwiY291bnRlcnMiLCJfdG9Db3VudGVycyIsIm51bUNvdW50ZXJzIiwiQXJyYXlIZWxwZXIiLCJpbml0IiwiT2JqZWN0IiwiZGVmaW5lUHJvcGVydHkiLCJ3cml0ZWFibGUiLCJFeGNlcHRpb24iLCJTdGFydE5vdEZvdW5kRXhjZXB0aW9uIiwiQ29kZU5vdEZvdW5kRXhjZXB0aW9uIiwiUGF0dGVybk5vdEZvdW5kRXhjZXB0aW9uIiwiQ09ORklHX0tFWVMiLCJhcnIiLCJ2YWwiLCJsIiwic2h1ZmZsZSIsImoiLCJ4IiwiZmxvb3IiLCJyYW5kb20iLCJ0b1BvaW50TGlzdCIsInJvdyIsInJvd3MiLCJqb2luIiwidGhyZXNob2xkIiwic2NvcmVGdW5jIiwicXVldWUiLCJhcHBseSIsIm1heEluZGV4IiwibWF4IiwiRUFOUmVhZGVyIiwib3B0cyIsImdldERlZmF1bENvbmZpZyIsImNhbGwiLCJrZXlzIiwiZm9yRWFjaCIsImtleSIsImRlZmF1bHQiLCJwcm9wZXJ0aWVzIiwiQ09ERV9MX1NUQVJUIiwiQ09ERV9HX1NUQVJUIiwiU1RBUlRfUEFUVEVSTiIsIlNUT1BfUEFUVEVSTiIsIk1JRERMRV9QQVRURVJOIiwiRVhURU5TSU9OX1NUQVJUX1BBVFRFUk4iLCJDT0RFX1BBVFRFUk4iLCJDT0RFX0ZSRVFVRU5DWSIsIkFWR19DT0RFX0VSUk9SIiwiY3JlYXRlIiwiY29uc3RydWN0b3IiLCJfZGVjb2RlQ29kZSIsImNvZGVyYW5nZSIsIl9maW5kUGF0dGVybiIsInRyeUhhcmRlciIsIl9maW5kU3RhcnQiLCJsZWFkaW5nV2hpdGVzcGFjZVN0YXJ0Iiwic3RhcnRJbmZvIiwiX3ZlcmlmeVRyYWlsaW5nV2hpdGVzcGFjZSIsImVuZEluZm8iLCJ0cmFpbGluZ1doaXRlc3BhY2VFbmQiLCJfZmluZEVuZCIsIl9jYWxjdWxhdGVGaXJzdERpZ2l0IiwiY29kZUZyZXF1ZW5jeSIsIl9kZWNvZGVQYXlsb2FkIiwiZGVjb2RlZENvZGVzIiwiZmlyc3REaWdpdCIsInVuc2hpZnQiLCJyZXN1bHRJbmZvIiwiX2NoZWNrc3VtIiwiZXh0IiwiX2RlY29kZUV4dGVuc2lvbnMiLCJsYXN0Q29kZSIsInN1cHBsZW1lbnQiLCJjb2Rlc2V0IiwiZGVjb2RlIiwiZHJhd1JlY3QiLCJwb3MiLCJzaXplIiwiY3R4Iiwic3R5bGUiLCJzdHJva2VTdHlsZSIsImNvbG9yIiwiZmlsbFN0eWxlIiwibGluZVdpZHRoIiwiYmVnaW5QYXRoIiwic3Ryb2tlUmVjdCIsInkiLCJkcmF3UGF0aCIsInBhdGgiLCJkZWYiLCJtb3ZlVG8iLCJsaW5lVG8iLCJjbG9zZVBhdGgiLCJzdHJva2UiLCJkcmF3SW1hZ2UiLCJpbWFnZURhdGEiLCJjYW52YXNEYXRhIiwiZ2V0SW1hZ2VEYXRhIiwiZGF0YSIsImltYWdlRGF0YVBvcyIsImNhbnZhc0RhdGFQb3MiLCJwdXRJbWFnZURhdGEiLCJ2ZWMyIiwiY2xvbmUiLCJyZXF1aXJlIiwidmVjMyIsImltYWdlUmVmIiwidGhhdCIsInRvVmVjMiIsInRvVmVjMyIsInJvdW5kIiwiY29tcHV0ZUludGVncmFsSW1hZ2UyIiwiaW1hZ2VXcmFwcGVyIiwiaW50ZWdyYWxXcmFwcGVyIiwid2lkdGgiLCJoZWlnaHQiLCJpbnRlZ3JhbEltYWdlRGF0YSIsInBvc0EiLCJwb3NCIiwicG9zQyIsInBvc0QiLCJjb21wdXRlSW50ZWdyYWxJbWFnZSIsInYiLCJ1IiwidGhyZXNob2xkSW1hZ2UiLCJ0YXJnZXRXcmFwcGVyIiwidGFyZ2V0RGF0YSIsImNvbXB1dGVIaXN0b2dyYW0iLCJiaXRzUGVyUGl4ZWwiLCJiaXRTaGlmdCIsImJ1Y2tldENudCIsImhpc3QiLCJJbnQzMkFycmF5Iiwic2hhcnBlbkxpbmUiLCJsZWZ0IiwiY2VudGVyIiwicmlnaHQiLCJkZXRlcm1pbmVPdHN1VGhyZXNob2xkIiwicHgiLCJteCIsImRldGVybWluZVRocmVzaG9sZCIsInZldCIsInAxIiwicDIiLCJwMTIiLCJrIiwibTEiLCJtMiIsIm0xMiIsIm90c3VUaHJlc2hvbGQiLCJjb21wdXRlQmluYXJ5SW1hZ2UiLCJrZXJuZWwiLCJBIiwiQiIsIkMiLCJEIiwiYXZnIiwiY2x1c3RlciIsInBvaW50cyIsInByb3BlcnR5IiwicG9pbnQiLCJjbHVzdGVycyIsImFkZFRvQ2x1c3RlciIsIm5ld1BvaW50IiwiZm91bmQiLCJmaXRzIiwiYWRkIiwiQ2x1c3RlcjIiLCJjcmVhdGVQb2ludCIsIlRyYWNlciIsInRyYWNlIiwidmVjIiwiaXRlcmF0aW9uIiwibWF4SXRlcmF0aW9ucyIsInRvcCIsImNlbnRlclBvcyIsImN1cnJlbnRQb3MiLCJpZHgiLCJmb3J3YXJkIiwiZnJvbSIsInRvIiwidG9JZHgiLCJwcmVkaWN0ZWRQb3MiLCJ0aHJlc2hvbGRYIiwidGhyZXNob2xkWSIsIm1hdGNoIiwicHJlZGljdGVkIiwiRElMQVRFIiwiRVJPREUiLCJkaWxhdGUiLCJpbkltYWdlV3JhcHBlciIsIm91dEltYWdlV3JhcHBlciIsImluSW1hZ2VEYXRhIiwib3V0SW1hZ2VEYXRhIiwieVN0YXJ0MSIsInlTdGFydDIiLCJ4U3RhcnQxIiwieFN0YXJ0MiIsImVyb2RlIiwic3VidHJhY3QiLCJhSW1hZ2VXcmFwcGVyIiwiYkltYWdlV3JhcHBlciIsInJlc3VsdEltYWdlV3JhcHBlciIsImFJbWFnZURhdGEiLCJiSW1hZ2VEYXRhIiwiY0ltYWdlRGF0YSIsImJpdHdpc2VPciIsImNvdW50Tm9uWmVybyIsInRvcEdlbmVyaWMiLCJsaXN0IiwibWluSWR4IiwibWluIiwic2NvcmUiLCJoaXQiLCJpdGVtIiwiZ3JheUFycmF5RnJvbUltYWdlIiwiaHRtbEltYWdlIiwib2Zmc2V0WCIsImFycmF5IiwiY3R4RGF0YSIsImNvbXB1dGVHcmF5IiwiZ3JheUFycmF5RnJvbUNvbnRleHQiLCJncmF5QW5kSGFsZlNhbXBsZUZyb21DYW52YXNEYXRhIiwib3V0QXJyYXkiLCJ0b3BSb3dJZHgiLCJib3R0b21Sb3dJZHgiLCJlbmRJZHgiLCJvdXRXaWR0aCIsIm91dEltZ0lkeCIsImluV2lkdGgiLCJzaW5nbGVDaGFubmVsIiwibG9hZEltYWdlQXJyYXkiLCJzcmMiLCJjYWxsYmFjayIsImNhbnZhcyIsImRvY3VtZW50IiwiY3JlYXRlRWxlbWVudCIsImltZyIsIkltYWdlIiwib25sb2FkIiwiZ2V0Q29udGV4dCIsIlVpbnQ4QXJyYXkiLCJoYWxmU2FtcGxlIiwiaW5JbWdXcmFwcGVyIiwib3V0SW1nV3JhcHBlciIsImluSW1nIiwib3V0SW1nIiwiaHN2MnJnYiIsImhzdiIsInJnYiIsImgiLCJzIiwiYyIsIm0iLCJyIiwiZyIsImIiLCJfY29tcHV0ZURpdmlzb3JzIiwibiIsImxhcmdlRGl2aXNvcnMiLCJkaXZpc29ycyIsInNxcnQiLCJjb25jYXQiLCJfY29tcHV0ZUludGVyc2VjdGlvbiIsImFycjEiLCJhcnIyIiwiY2FsY3VsYXRlUGF0Y2hTaXplIiwicGF0Y2hTaXplIiwiaW1nU2l6ZSIsImRpdmlzb3JzWCIsImRpdmlzb3JzWSIsIndpZGVTaWRlIiwiY29tbW9uIiwibnJPZlBhdGNoZXNMaXN0IiwibnJPZlBhdGNoZXNNYXAiLCJuck9mUGF0Y2hlc0lkeCIsIm1lZGl1bSIsIm5yT2ZQYXRjaGVzIiwiZGVzaXJlZFBhdGNoU2l6ZSIsIm9wdGltYWxQYXRjaFNpemUiLCJmaW5kUGF0Y2hTaXplRm9yRGl2aXNvcnMiLCJfcGFyc2VDU1NEaW1lbnNpb25WYWx1ZXMiLCJkaW1lbnNpb24iLCJwYXJzZUZsb2F0IiwidW5pdCIsImluZGV4T2YiLCJfZGltZW5zaW9uc0NvbnZlcnRlcnMiLCJjb250ZXh0IiwiYm90dG9tIiwiY29tcHV0ZUltYWdlQXJlYSIsImlucHV0V2lkdGgiLCJpbnB1dEhlaWdodCIsImFyZWEiLCJwYXJzZWRBcmVhIiwicmVkdWNlIiwicGFyc2VkIiwiY2FsY3VsYXRlZCIsInN4Iiwic3kiLCJzdyIsInNoIiwiSW1hZ2VXcmFwcGVyIiwiQXJyYXlUeXBlIiwiaW5pdGlhbGl6ZSIsIkFycmF5IiwiaW5JbWFnZVdpdGhCb3JkZXIiLCJpbWdSZWYiLCJib3JkZXIiLCJzYW1wbGUiLCJseCIsImx5IiwidyIsImJhc2UiLCJhIiwiZCIsImUiLCJjbGVhckFycmF5Iiwic3ViSW1hZ2UiLCJzdWJJbWFnZUFzQ29weSIsInNpemVZIiwic2l6ZVgiLCJjb3B5VG8iLCJzcmNEYXRhIiwiZHN0RGF0YSIsImdldCIsImdldFNhZmUiLCJpbmRleE1hcHBpbmciLCJzZXQiLCJ6ZXJvQm9yZGVyIiwiaW52ZXJ0IiwiY29udm9sdmUiLCJreCIsImt5Iiwia1NpemUiLCJhY2N1IiwibW9tZW50cyIsImxhYmVsY291bnQiLCJ5c3EiLCJsYWJlbHN1bSIsImxhYmVsIiwibXUxMSIsIm11MDIiLCJtdTIwIiwieF8iLCJ5XyIsIlBJIiwiUElfNCIsIm0wMCIsIm0wMSIsIm0xMCIsIm0xMSIsIm0wMiIsIm0yMCIsInRoZXRhIiwicmFkIiwiaXNOYU4iLCJhdGFuIiwiY29zIiwic2luIiwic2hvdyIsInNjYWxlIiwiZnJhbWUiLCJjdXJyZW50IiwicGl4ZWwiLCJvdmVybGF5Iiwid2hpdGVSZ2IiLCJibGFja1JnYiIsInNlYXJjaERpcmVjdGlvbnMiLCJsYWJlbFdyYXBwZXIiLCJsYWJlbERhdGEiLCJlZGdlbGFiZWwiLCJjeSIsImRpciIsImN4IiwidmVydGV4MkQiLCJuZXh0IiwicHJldiIsImNvbnRvdXJUcmFjaW5nIiwiRnYiLCJDdiIsIlAiLCJsZGlyIiwiQ29kZTM5UmVhZGVyIiwiQUxQSEFCRVRIX1NUUklORyIsIkFMUEhBQkVUIiwiQ0hBUkFDVEVSX0VOQ09ESU5HUyIsIkFTVEVSSVNLIiwiZGVjb2RlZENoYXIiLCJsYXN0U3RhcnQiLCJuZXh0U3RhcnQiLCJfdG9QYXR0ZXJuIiwiX3BhdHRlcm5Ub0NoYXIiLCJwb3AiLCJwYXR0ZXJuU2l6ZSIsIlN0cmluZyIsImZyb21DaGFyQ29kZSIsIl9maW5kTmV4dFdpZHRoIiwibWluV2lkdGgiLCJtYXhOYXJyb3dXaWR0aCIsIm51bVdpZGVCYXJzIiwid2lkZUJhcldpZHRoIiwicGF0dGVyblN0YXJ0Iiwid2hpdGVTcGFjZU11c3RTdGFydCIsIl9pbnB1dFN0cmVhbSIsIl9mcmFtZWdyYWJiZXIiLCJfc3RvcHBlZCIsIl9jYW52YXNDb250YWluZXIiLCJpbWFnZSIsImRvbSIsIl9pbnB1dEltYWdlV3JhcHBlciIsIl9ib3hTaXplIiwiX2RlY29kZXIiLCJfd29ya2VyUG9vbCIsIl9vblVJVGhyZWFkIiwiX3Jlc3VsdENvbGxlY3RvciIsIl9jb25maWciLCJpbml0aWFsaXplRGF0YSIsImluaXRCdWZmZXJzIiwiQmFyY29kZURlY29kZXIiLCJkZWNvZGVyIiwiaW5pdElucHV0U3RyZWFtIiwiY2IiLCJ2aWRlbyIsImlucHV0U3RyZWFtIiwidHlwZSIsIklucHV0U3RyZWFtIiwiY3JlYXRlVmlkZW9TdHJlYW0iLCJjcmVhdGVJbWFnZVN0cmVhbSIsIiR2aWV3cG9ydCIsImdldFZpZXdQb3J0IiwicXVlcnlTZWxlY3RvciIsImFwcGVuZENoaWxkIiwiY3JlYXRlTGl2ZVN0cmVhbSIsIkNhbWVyYUFjY2VzcyIsInJlcXVlc3QiLCJjb25zdHJhaW50cyIsInRoZW4iLCJ0cmlnZ2VyIiwiY2F0Y2giLCJlcnIiLCJzZXRBdHRyaWJ1dGUiLCJzZXRJbnB1dFN0cmVhbSIsImFkZEV2ZW50TGlzdGVuZXIiLCJjYW5SZWNvcmQiLCJiaW5kIiwidGFyZ2V0Iiwibm9kZU5hbWUiLCJub2RlVHlwZSIsInNlbGVjdG9yIiwiQmFyY29kZUxvY2F0b3IiLCJjaGVja0ltYWdlQ29uc3RyYWludHMiLCJsb2NhdG9yIiwiaW5pdENhbnZhcyIsIkZyYW1lR3JhYmJlciIsImFkanVzdFdvcmtlclBvb2wiLCJudW1PZldvcmtlcnMiLCJyZWFkeSIsInBsYXkiLCJjbGFzc05hbWUiLCJnZXRDYW52YXNTaXplIiwiY2xlYXJGaXgiLCJnZXRXaWR0aCIsImdldEhlaWdodCIsImNvbnNvbGUiLCJsb2ciLCJnZXRCb3VuZGluZ0JveGVzIiwibG9jYXRlIiwidHJhbnNmb3JtUmVzdWx0IiwidG9wUmlnaHQiLCJnZXRUb3BSaWdodCIsInhPZmZzZXQiLCJ5T2Zmc2V0IiwiYmFyY29kZXMiLCJtb3ZlTGluZSIsImJveCIsIm1vdmVCb3giLCJib3hlcyIsImNvcm5lciIsImFkZFJlc3VsdCIsImZpbHRlciIsImJhcmNvZGUiLCJjb2RlUmVzdWx0IiwiaGFzQ29kZVJlc3VsdCIsInNvbWUiLCJwdWJsaXNoUmVzdWx0IiwicmVzdWx0VG9QdWJsaXNoIiwiRXZlbnRzIiwicHVibGlzaCIsImxvY2F0ZUFuZERlY29kZSIsImRlY29kZUZyb21Cb3VuZGluZ0JveGVzIiwidXBkYXRlIiwiYXZhaWxhYmxlV29ya2VyIiwid29ya2VyVGhyZWFkIiwiYnVzeSIsImF0dGFjaERhdGEiLCJncmFiIiwid29ya2VyIiwicG9zdE1lc3NhZ2UiLCJjbWQiLCJidWZmZXIiLCJzdGFydENvbnRpbnVvdXNVcGRhdGUiLCJkZWxheSIsImZyZXF1ZW5jeSIsInRpbWVzdGFtcCIsIndpbmRvdyIsInJlcXVlc3RBbmltRnJhbWUiLCJwZXJmb3JtYW5jZSIsIm5vdyIsImluaXRXb3JrZXIiLCJibG9iVVJMIiwiZ2VuZXJhdGVXb3JrZXJCbG9iIiwiV29ya2VyIiwib25tZXNzYWdlIiwiZXZlbnQiLCJVUkwiLCJyZXZva2VPYmplY3RVUkwiLCJtZXNzYWdlIiwiY29uZmlnRm9yV29ya2VyIiwid29ya2VySW50ZXJmYWNlIiwiZmFjdG9yeSIsIlF1YWdnYSIsIm9uUHJvY2Vzc2VkIiwic2V0UmVhZGVycyIsInJlYWRlcnMiLCJibG9iIiwiZmFjdG9yeVNvdXJjZSIsIl9fZmFjdG9yeVNvdXJjZV9fIiwiQmxvYiIsInRvU3RyaW5nIiwiY3JlYXRlT2JqZWN0VVJMIiwiY2FwYWNpdHkiLCJpbmNyZWFzZUJ5Iiwid29ya2Vyc1RvVGVybWluYXRlIiwic2xpY2UiLCJ0ZXJtaW5hdGUiLCJ3b3JrZXJJbml0aWFsaXplZCIsInN0b3AiLCJyZWxlYXNlIiwiY2xlYXJFdmVudEhhbmRsZXJzIiwicGF1c2UiLCJvbkRldGVjdGVkIiwic3Vic2NyaWJlIiwib2ZmRGV0ZWN0ZWQiLCJ1bnN1YnNjcmliZSIsIm9mZlByb2Nlc3NlZCIsInJlZ2lzdGVyUmVzdWx0Q29sbGVjdG9yIiwicmVzdWx0Q29sbGVjdG9yIiwiZGVjb2RlU2luZ2xlIiwicmVzdWx0Q2FsbGJhY2siLCJzZXF1ZW5jZSIsImRlYnVnIiwib25jZSIsIkltYWdlRGVidWciLCJSZXN1bHRDb2xsZWN0b3IiLCJjb250YWlucyIsImV2ZXJ5IiwicGFzc2VzRmlsdGVyIiwicmVzdWx0cyIsImNhcHR1cmUiLCJtYXRjaGVzQ29uc3RyYWludHMiLCJibGFja2xpc3QiLCJpbWFnZVNpemUiLCJ0b0RhdGFVUkwiLCJnZXRSZXN1bHRzIiwiZG90IiwicG9pbnRNYXAiLCJ1cGRhdGVDZW50ZXIiLCJwb2ludFRvQWRkIiwiaWQiLCJvdGhlclBvaW50Iiwic2ltaWxhcml0eSIsImdldFBvaW50cyIsImdldENlbnRlciIsImV2ZW50cyIsImdldEV2ZW50IiwiZXZlbnROYW1lIiwic3Vic2NyaWJlcnMiLCJjbGVhckV2ZW50cyIsInB1Ymxpc2hTdWJzY3JpcHRpb24iLCJzdWJzY3JpcHRpb24iLCJhc3luYyIsInNldFRpbWVvdXQiLCJzdWJzY3JpYmVyIiwiZW51bWVyYXRlRGV2aWNlcyIsIm5hdmlnYXRvciIsIm1lZGlhRGV2aWNlcyIsIlByb21pc2UiLCJyZWplY3QiLCJFcnJvciIsImdldFVzZXJNZWRpYSIsIlN1YkltYWdlIiwiSSIsIm9yaWdpbmFsU2l6ZSIsInVwZGF0ZURhdGEiLCJ1cGRhdGVGcm9tIiwicmVxdWVzdEFuaW1hdGlvbkZyYW1lIiwid2Via2l0UmVxdWVzdEFuaW1hdGlvbkZyYW1lIiwibW96UmVxdWVzdEFuaW1hdGlvbkZyYW1lIiwib1JlcXVlc3RBbmltYXRpb25GcmFtZSIsIm1zUmVxdWVzdEFuaW1hdGlvbkZyYW1lIiwiaW11bCIsImFoIiwiYWwiLCJiaCIsImJsIiwiYXNzaWduIiwiVHlwZUVycm9yIiwiaW5kZXgiLCJhcmd1bWVudHMiLCJuZXh0U291cmNlIiwibmV4dEtleSIsImhhc093blByb3BlcnR5IiwibW9kdWxlIiwiZXhwb3J0cyIsIm5hbWUiLCJmYWNpbmdNb2RlIiwiZHJhd0JvdW5kaW5nQm94Iiwic2hvd0ZyZXF1ZW5jeSIsImRyYXdTY2FubGluZSIsInNob3dQYXR0ZXJuIiwic2hvd0NhbnZhcyIsInNob3dQYXRjaGVzIiwic2hvd0ZvdW5kUGF0Y2hlcyIsInNob3dTa2VsZXRvbiIsInNob3dMYWJlbHMiLCJzaG93UGF0Y2hMYWJlbHMiLCJzaG93UmVtYWluaW5nUGF0Y2hMYWJlbHMiLCJib3hGcm9tUGF0Y2hlcyIsInNob3dUcmFuc2Zvcm1lZCIsInNob3dUcmFuc2Zvcm1lZEJveCIsInNob3dCQiIsIkVOViIsIm5vZGUiLCJSRUFERVJTIiwiY29kZV8xMjhfcmVhZGVyIiwiZWFuX3JlYWRlciIsImVhbl81X3JlYWRlciIsImVhbl8yX3JlYWRlciIsImVhbl84X3JlYWRlciIsImNvZGVfMzlfcmVhZGVyIiwiY29kZV8zOV92aW5fcmVhZGVyIiwiY29kYWJhcl9yZWFkZXIiLCJ1cGNfcmVhZGVyIiwidXBjX2VfcmVhZGVyIiwiaTJvZjVfcmVhZGVyIiwiY29kZV85M19yZWFkZXIiLCJDb2RlOTNSZWFkZXIiLCJpbnB1dEltYWdlV3JhcHBlciIsIl9jYW52YXMiLCJfYmFyY29kZVJlYWRlcnMiLCJpbml0UmVhZGVycyIsImluaXRDb25maWciLCIkZGVidWciLCJyZWFkZXJDb25maWciLCJyZWFkZXIiLCJjb25maWd1cmF0aW9uIiwibWFwIiwiSlNPTiIsInN0cmluZ2lmeSIsInZpcyIsInByb3AiLCJkaXNwbGF5IiwiZ2V0RXh0ZW5kZWRMaW5lIiwiYW5nbGUiLCJleHRlbmRMaW5lIiwiYW1vdW50IiwiZXh0ZW5zaW9uIiwiY2VpbCIsImdldExpbmUiLCJ0cnlEZWNvZGUiLCJiYXJjb2RlTGluZSIsIkJyZXNlbmhhbSIsImdldEJhcmNvZGVMaW5lIiwicHJpbnRGcmVxdWVuY3kiLCJ0b0JpbmFyeUxpbmUiLCJwcmludFBhdHRlcm4iLCJ0cnlEZWNvZGVCcnV0ZUZvcmNlIiwibGluZUFuZ2xlIiwic2lkZUxlbmd0aCIsInBvdyIsInNsaWNlcyIsInhkaXIiLCJ5ZGlyIiwiZ2V0TGluZUxlbmd0aCIsImRlY29kZUZyb21Cb3VuZGluZ0JveCIsImxpbmVMZW5ndGgiLCJhdGFuMiIsIm11bHRpcGxlIiwiU2xvcGUiLCJESVIiLCJVUCIsIkRPV04iLCJ4MCIsInkwIiwieDEiLCJ5MSIsInN0ZWVwIiwiZGVsdGF4IiwiZGVsdGF5IiwieXN0ZXAiLCJyZWFkIiwic2xvcGUiLCJzbG9wZTIiLCJleHRyZW1hIiwiY3VycmVudERpciIsInJUaHJlc2hvbGQiLCJmaWxsQ29sb3IiLCJmaWxsUmVjdCIsImZhY2luZ01hdGNoaW5nIiwic3RyZWFtUmVmIiwid2FpdEZvclZpZGVvIiwicmVzb2x2ZSIsImF0dGVtcHRzIiwiY2hlY2tWaWRlbyIsInZpZGVvV2lkdGgiLCJ2aWRlb0hlaWdodCIsImluaXRDYW1lcmEiLCJzdHJlYW0iLCJzcmNPYmplY3QiLCJkZXByZWNhdGVkQ29uc3RyYWludHMiLCJ2aWRlb0NvbnN0cmFpbnRzIiwibm9ybWFsaXplZCIsIm1pbkFzcGVjdFJhdGlvIiwiYXNwZWN0UmF0aW8iLCJmYWNpbmciLCJwaWNrQ29uc3RyYWludHMiLCJub3JtYWxpemVkQ29uc3RyYWludHMiLCJhdWRpbyIsImRldmljZUlkIiwiZW51bWVyYXRlVmlkZW9EZXZpY2VzIiwiZGV2aWNlcyIsImRldmljZSIsImtpbmQiLCJnZXRBY3RpdmVUcmFjayIsInRyYWNrcyIsImdldFZpZGVvVHJhY2tzIiwiZ2V0QWN0aXZlU3RyZWFtTGFiZWwiLCJ0cmFjayIsIkV4aWZUYWdzIiwiQXZhaWxhYmxlVGFncyIsImZpbmRUYWdzSW5PYmplY3RVUkwiLCJ0YWdzIiwidGVzdCIsIm9iamVjdFVSTFRvQmxvYiIsInJlYWRUb0J1ZmZlciIsImZpbmRUYWdzSW5CdWZmZXIiLCJiYXNlNjRUb0FycmF5QnVmZmVyIiwiZGF0YVVybCIsImJhc2U2NCIsInJlcGxhY2UiLCJiaW5hcnkiLCJhdG9iIiwibGVuIiwiQXJyYXlCdWZmZXIiLCJ2aWV3IiwiY2hhckNvZGVBdCIsImZpbGVSZWFkZXIiLCJGaWxlUmVhZGVyIiwicmVhZEFzQXJyYXlCdWZmZXIiLCJ1cmwiLCJodHRwIiwiWE1MSHR0cFJlcXVlc3QiLCJvcGVuIiwicmVzcG9uc2VUeXBlIiwib25yZWFkeXN0YXRlY2hhbmdlIiwicmVhZHlTdGF0ZSIsIkRPTkUiLCJzdGF0dXMiLCJyZXNwb25zZSIsIm9uZXJyb3IiLCJzZW5kIiwiZmlsZSIsInNlbGVjdGVkVGFncyIsImRhdGFWaWV3IiwiRGF0YVZpZXciLCJieXRlTGVuZ3RoIiwiZXhpZlRhZ3MiLCJzZWxlY3RlZFRhZyIsImV4aWZUYWciLCJ0YWciLCJtYXJrZXIiLCJnZXRVaW50OCIsInJlYWRFWElGRGF0YSIsImdldFVpbnQxNiIsImdldFN0cmluZ0Zyb21CdWZmZXIiLCJ0aWZmT2Zmc2V0IiwiYmlnRW5kIiwiZmlyc3RJRkRPZmZzZXQiLCJnZXRVaW50MzIiLCJyZWFkVGFncyIsInRpZmZTdGFydCIsImRpclN0YXJ0Iiwic3RyaW5ncyIsImVudHJpZXMiLCJlbnRyeU9mZnNldCIsInJlYWRUYWdWYWx1ZSIsIm51bVZhbHVlcyIsIm91dHN0ciIsIlRPX1JBRElBTlMiLCJhZGp1c3RDYW52YXNTaXplIiwidGFyZ2V0U2l6ZSIsIl90aGF0IiwiX3N0cmVhbUNvbmZpZyIsImdldENvbmZpZyIsIl92aWRlb19zaXplIiwiZ2V0UmVhbFdpZHRoIiwiZ2V0UmVhbEhlaWdodCIsIl9jYW52YXNTaXplIiwiX3NpemUiLCJfc3giLCJfc3kiLCJfY3R4IiwiX2RhdGEiLCJ2aWRlb1NpemUiLCJjYW52YXNTaXplIiwiZ2V0RGF0YSIsImRvSGFsZlNhbXBsZSIsImdldEZyYW1lIiwiZHJhd2FibGUiLCJkcmF3QW5nbGUiLCJvcmllbnRhdGlvbiIsInRyYW5zbGF0ZSIsInJvdGF0ZSIsImdldFNpemUiLCJJbWFnZUxvYWRlciIsImxvYWQiLCJkaXJlY3RvcnkiLCJodG1sSW1hZ2VzU3JjQXJyYXkiLCJodG1sSW1hZ2VzQXJyYXkiLCJudW0iLCJub3RMb2FkZWQiLCJhZGRJbWFnZSIsImxvYWRlZCIsImxvYWRlZEltZyIsIm5vdGxvYWRlZEltZ3MiLCJzcGxpY2UiLCJpbWdOYW1lIiwic3Vic3RyIiwibGFzdEluZGV4T2YiLCJhZGRPbmxvYWRIYW5kbGVyIiwiX2V2ZW50TmFtZXMiLCJfZXZlbnRIYW5kbGVycyIsIl9jYWxjdWxhdGVkV2lkdGgiLCJfY2FsY3VsYXRlZEhlaWdodCIsIl90b3BSaWdodCIsImluaXRTaXplIiwic2V0V2lkdGgiLCJzZXRIZWlnaHQiLCJlbmRlZCIsInNldEN1cnJlbnRUaW1lIiwidGltZSIsImN1cnJlbnRUaW1lIiwiZiIsImJvb2wiLCJoYW5kbGVycyIsImhhbmRsZXIiLCJyZW1vdmVFdmVudExpc3RlbmVyIiwiYXJncyIsInNldFRvcFJpZ2h0Iiwic2V0Q2FudmFzU2l6ZSIsImZyYW1lSWR4IiwicGF1c2VkIiwiaW1nQXJyYXkiLCJiYXNlVXJsIiwiY2FsY3VsYXRlZFdpZHRoIiwiY2FsY3VsYXRlZEhlaWdodCIsImxvYWRJbWFnZXMiLCJpbWdzIiwicHVibGlzaEV2ZW50IiwibmV3V2lkdGgiLCJuZXdIZWlnaHQiLCJ0cmFuc2Zvcm1NYXQyIiwibWF0MiIsImNvcHkiLCJfY3VycmVudEltYWdlV3JhcHBlciIsIl9za2VsSW1hZ2VXcmFwcGVyIiwiX3N1YkltYWdlV3JhcHBlciIsIl9sYWJlbEltYWdlV3JhcHBlciIsIl9wYXRjaEdyaWQiLCJfcGF0Y2hMYWJlbEdyaWQiLCJfaW1hZ2VUb1BhdGNoR3JpZCIsIl9iaW5hcnlJbWFnZVdyYXBwZXIiLCJfcGF0Y2hTaXplIiwiX251bVBhdGNoZXMiLCJfc2tlbGV0b25pemVyIiwic2tlbGV0b25JbWFnZURhdGEiLCJza2VsZXRvbml6ZXIiLCJnbG9iYWwiLCJ1c2VXb3JrZXIiLCJwYXRjaGVzIiwib3ZlckF2ZyIsInBhdGNoIiwidHJhbnNNYXQiLCJtaW54IiwibWlueSIsIm1heHgiLCJtYXh5IiwiYmluYXJpemVJbWFnZSIsImZpbmRQYXRjaGVzIiwicGF0Y2hlc0ZvdW5kIiwicmFzdGVyaXplciIsInJhc3RlclJlc3VsdCIsInNrZWxldG9uaXplIiwiUmFzdGVyaXplciIsInJhc3Rlcml6ZSIsImRlc2NyaWJlUGF0Y2giLCJmaW5kQmlnZ2VzdENvbm5lY3RlZEFyZWFzIiwibWF4TGFiZWwiLCJsYWJlbEhpc3QiLCJ0b3BMYWJlbHMiLCJzb3J0IiwiZWwiLCJmaW5kQm94ZXMiLCJzaW1pbGFyTW9tZW50cyIsInRvcENsdXN0ZXIiLCJwYXRjaFBvcyIsImVsaWdpYmxlTW9tZW50cyIsIm1hdGNoaW5nTW9tZW50cyIsIm1pbkNvbXBvbmVudFdlaWdodCIsInJhc3Rlcml6ZUFuZ3VsYXJTaW1pbGFyaXR5IiwiY3VycklkeCIsIm5vdFlldFByb2Nlc3NlZCIsImN1cnJlbnRJZHgiLCJjdXJyZW50UGF0Y2giLCJjcmVhdGVDb250b3VyMkQiLCJmaXJzdFZlcnRleCIsImluc2lkZUNvbnRvdXJzIiwibmV4dHBlZXIiLCJwcmV2cGVlciIsIkNPTlRPVVJfRElSIiwiQ1dfRElSIiwiQ0NXX0RJUiIsIlVOS05PV05fRElSIiwiT1VUU0lERV9FREdFIiwiSU5TSURFX0VER0UiLCJ0cmFjZXIiLCJkZXB0aGxhYmVsIiwiYmMiLCJsYyIsImxhYmVsaW5kZXgiLCJjb2xvck1hcCIsInZlcnRleCIsInAiLCJjYyIsInNjIiwiY29ubmVjdGVkQ291bnQiLCJkcmF3Q29udG91ciIsImZpcnN0Q29udG91ciIsInBxIiwiaXEiLCJxIiwiU2tlbGV0b25pemVyIiwic3RkbGliIiwiZm9yZWlnbiIsImltYWdlcyIsImluSW1hZ2VQdHIiLCJvdXRJbWFnZVB0ciIsImFJbWFnZVB0ciIsImJJbWFnZVB0ciIsImltYWdlUHRyIiwibWVtY3B5Iiwic3JjSW1hZ2VQdHIiLCJkc3RJbWFnZVB0ciIsInN1YkltYWdlUHRyIiwiZXJvZGVkSW1hZ2VQdHIiLCJ0ZW1wSW1hZ2VQdHIiLCJza2VsSW1hZ2VQdHIiLCJkb25lIiwiVHdvT2ZGaXZlUmVhZGVyIiwiYmFyU3BhY2VSYXRpbyIsIk4iLCJXIiwid3JpdGFibGUiLCJzdGFydFBhdHRlcm5MZW5ndGgiLCJuYXJyb3dCYXJXaWR0aCIsImNvdW50ZXJMZW5ndGgiLCJfdmVyaWZ5Q291bnRlckxlbmd0aCIsIkNvZGFiYXJSZWFkZXIiLCJfY291bnRlcnMiLCJTVEFSVF9FTkQiLCJNSU5fRU5DT0RFRF9DSEFSUyIsIk1BWF9BQ0NFUFRBQkxFIiwiUEFERElORyIsInN0YXJ0Q291bnRlciIsIl9pc1N0YXJ0RW5kIiwiX3ZlcmlmeVdoaXRlc3BhY2UiLCJfdmFsaWRhdGVSZXN1bHQiLCJfc3VtQ291bnRlcnMiLCJlbmRDb3VudGVyIiwiX2NhbGN1bGF0ZVBhdHRlcm5MZW5ndGgiLCJfdGhyZXNob2xkUmVzdWx0UGF0dGVybiIsImNhdGVnb3JpemF0aW9uIiwic3BhY2UiLCJuYXJyb3ciLCJjb3VudHMiLCJ3aWRlIiwiYmFyIiwiY2F0IiwiX2NoYXJUb1BhdHRlcm4iLCJuZXdraW5kIiwiY2hhciIsImNoYXJDb2RlIiwidGhyZXNob2xkcyIsIl9jb21wdXRlQWx0ZXJuYXRpbmdUaHJlc2hvbGQiLCJiYXJUaHJlc2hvbGQiLCJzcGFjZVRocmVzaG9sZCIsImJpdG1hc2siLCJDb2RlMTI4UmVhZGVyIiwiQ09ERV9TSElGVCIsIkNPREVfQyIsIkNPREVfQiIsIkNPREVfQSIsIlNUQVJUX0NPREVfQSIsIlNUQVJUX0NPREVfQiIsIlNUQVJUX0NPREVfQyIsIlNUT1BfQ09ERSIsIk1PRFVMRV9JTkRJQ0VTIiwiX2NvcnJlY3QiLCJjYWxjdWxhdGVDb3JyZWN0aW9uIiwibXVsdGlwbGllciIsImNoZWNrc3VtIiwicmF3UmVzdWx0Iiwic2hpZnROZXh0IiwicmVtb3ZlTGFzdENoYXJhY3RlciIsImV4cGVjdGVkIiwic3VtTm9ybWFsaXplZCIsInN1bUV4cGVjdGVkIiwiQ29kZTM5VklOUmVhZGVyIiwicGF0dGVybnMiLCJJT1EiLCJBWjA5IiwiX2NoZWNrQ2hlY2tzdW0iLCJzcGxpdCIsIl92ZXJpZnlFbmQiLCJfdmVyaWZ5Q2hlY2tzdW1zIiwiX2RlY29kZUV4dGVuZGVkIiwiY2hhckFycmF5IiwibmV4dENoYXIiLCJuZXh0Q2hhckNvZGUiLCJfbWF0Y2hDaGVja0NoYXIiLCJtYXhXZWlnaHQiLCJhcnJheVRvQ2hlY2siLCJ3ZWlnaHRlZFN1bXMiLCJ3ZWlnaHQiLCJjaGVja0NoYXIiLCJFQU4yUmVhZGVyIiwicGFyc2VJbnQiLCJFQU41UmVhZGVyIiwiQ0hFQ0tfRElHSVRfRU5DT0RJTkdTIiwiZXh0ZW5zaW9uQ2hlY2tzdW0iLCJkZXRlcm1pbmVDaGVja0RpZ2l0IiwiRUFOOFJlYWRlciIsIkkyb2Y1UmVhZGVyIiwibm9ybWFsaXplQmFyU3BhY2VXaWR0aCIsIk1BWF9DT1JSRUNUSU9OX0ZBQ1RPUiIsImNvdW50ZXJTdW0iLCJjb2RlU3VtIiwiY29ycmVjdGlvblJhdGlvIiwiY29ycmVjdGlvblJhdGlvSW52ZXJzZSIsIl9kZWNvZGVQYWlyIiwiY291bnRlclBhaXIiLCJjb2RlcyIsIlVQQ0VSZWFkZXIiLCJfZGV0ZXJtaW5lUGFyaXR5IiwibnJTeXN0ZW0iLCJfY29udmVydFRvVVBDQSIsInVwY2EiLCJsYXN0RGlnaXQiLCJVUENSZWFkZXIiLCJjaGFyQXQiLCJzdWJzdHJpbmciXSwibWFwcGluZ3MiOiJBQUFBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQztBQUNELE87QUNSQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQTtBQUNBOztBQUVBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFFQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQTtBQUNBOzs7QUFHQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQSxtREFBMkMsY0FBYzs7QUFFekQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxhQUFLO0FBQ0w7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQSxtQ0FBMkIsMEJBQTBCLEVBQUU7QUFDdkQseUNBQWlDLGVBQWU7QUFDaEQ7QUFDQTtBQUNBOztBQUVBO0FBQ0EsOERBQXNELCtEQUErRDs7QUFFckg7QUFDQTs7QUFFQTtBQUNBOzs7Ozs7O0FDaEVBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFdBQVcsRUFBRTtBQUNiLGFBQWEsUUFB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QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFFQTs7Ozs7OztBQ3BCQTtBQUNBOztBQUVBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFdBQVcsRUFBRTtBQUNiLGFBQWEsUUFBUTtBQUNyQjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBOztBQUVBOzs7Ozs7O0FDaENBO0FBQ0E7O0FBRUE7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFdBQVcsRUFBRTtBQUNiLGFBQWEsUUFBUTtBQUNyQjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBOztBQUVBOzs7Ozs7O0FDcENBO0FBQ0E7O0FBRUE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxXQUFXLEVBQUU7QUFDYixhQUFhLFFBQVE7QUFDckI7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBOztBQUVBOzs7Ozs7O0FDbENBO0FBQ0E7O0FBRUE7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFdBQVcsRUFBRTtBQUNiLGFBQWEsUUFBUTtBQUNyQjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7O0FBRUE7Ozs7Ozs7QUM1QkE7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxXQUFXLE9BQU87QUFDbEIsV0FB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