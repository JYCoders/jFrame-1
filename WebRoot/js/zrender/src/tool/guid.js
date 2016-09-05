/**
 * zrender: 生成唯一id
 *
 */

define(
    function() {
        var idStart = 0x0907;

        return function () {
            return 'zrender__' + (idStart++);
        };
    }
);
