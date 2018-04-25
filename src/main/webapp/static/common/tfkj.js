/**
 * jquery插件
 * 
 * @param $
 */
(function($) {
    TFKJTABLE_DEFAULTS = {
        pagination : true,
        paginationDetailHAlign : 'right',
        paginationHAlign : 'left',
        paginationPreText : '上一页',
        paginationNextText : '下一页',
        sidePagination : 'server',
        clickToSelect : true,
//        singleSelect: true,
        pageSize : 10,
        pageNumber : 1
        /*showExport: true,                     //是否显示导出
        exportDataType: "all",              //basic', 'all', 'selected'.
*/    }
    $.fn.tfkjTable = function(options) {
        var $this = $(this);
        options = $.extend({}, TFKJTABLE_DEFAULTS, typeof options === 'object'
                && options);
        $this.bootstrapTable(options);
    };
    $.extend({
        /*===================下载文件
         * options:{
         * url:'',  //下载地址
         * data:{name:value}, //要发送的数据
         * method:'post'
         * }
         */
        downloadfile : function(options) {
            var config = $.extend(true, {
                method : 'post'
            }, options), $form = $('<form action="' + config.url + '" method="'
                    + config.method + '" />');
            for ( var key in config.data) {
                $form.append('<input type="hidden" name="' + key + '" value="'
                        + config.data[key] + '" />');
            }
            $form.submit();
        }
    });
})(jQuery);
