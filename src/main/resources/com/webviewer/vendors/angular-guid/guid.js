/**!
 * ngGuid v0.1.2
 * @copyright 2014 Rui Marques. All Rights Reserved.
 * @license see LICENCE.
 * [https://github.com:ruionwriting/ngUUID.git]
 *
 * Based on this SO discussion: http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript.
 */
(function () {
    'use strict';

    angular
        .module('ngGuid', [])
        .factory('Guid', Guid);

    function Guid() {
        var EMPTY = '00000000-0000-0000-0000-000000000000';

        var service = {
            newGuid: create,
            empty: EMPTY
        };

        return service;

        //////////////

        function _padLeft(paddingString, width, replacementChar) {
            return paddingString.length >= width ? paddingString : _padLeft(replacementChar + paddingString, width, replacementChar || ' ');
        }

        function _s4(number) {
            var hexadecimalResult = number.toString(16);

            return _padLeft(hexadecimalResult, 4, '0');
        }

        function _cryptoGuid() {
            var buffer = new window.Uint16Array(8);
            var crypto = window.crypto || window.msCrypto
            crypto.getRandomValues(buffer);

            return [_s4(buffer[0]) + _s4(buffer[1]), _s4(buffer[2]), _s4(buffer[3]), _s4(buffer[4]), _s4(buffer[5]) + _s4(buffer[6]) + _s4(buffer[7])].join('-');
        }

        function _guid() {
            var currentDateMilliseconds = new Date().getTime();

            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (currentChar) {
                var randomChar = (currentDateMilliseconds + Math.random() * 16) % 16 | 0;
                currentDateMilliseconds = Math.floor(currentDateMilliseconds / 16);

                return (currentChar === 'x' ? randomChar : (randomChar & 0x7 | 0x8)).toString(16);
            });
        }

        function create() {
            var hasCrypto = typeof (window.crypto) != 'undefined',
                hasRandomValues = typeof (window.crypto.getRandomValues) != 'undefined';

            return (hasCrypto && hasRandomValues) ? _cryptoGuid() : _guid();
        }
    }

}());
