var fileupload = function(){
    $('.fileupload').each(function () {
        var uploadWidget = $(this).parents('.upload-widget');
        $(this).fileupload({
            dropZone: $(this),
            dataType: 'json',
            url: uploadWidget.find('.file-box').attr('data-server'),
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                uploadWidget.find('.progress .progress-bar').css('display', 'block');
                uploadWidget.find('.progress .progress-bar').css(
                    'width',
                    progress + '%'
                );
            },
            add: function (e, data) {
                uploadWidget.find('.progress .progress-bar').css('width', '0px');
                data.submit();
            },
            done: function (e, data) {
                if (data.result.status == false) {
                    alert(data.result.message);
                } else {
                    var file = data.result;
                    var src = window.URL.createObjectURL(data.files[0]);
                    uploadWidget.find('.file_id').val(file['fileId']);
                    uploadWidget.find('.file_url').val(file['fileUrl']);
                    uploadWidget.find('.file_name').val(file['fileName']);
                    uploadWidget.append('<div class="image"><span class="remove" data-id="' + file['fileId'] + '" data-filename="' + file['name'] + '"></span><img src="' + src + '"></div>');
                    uploadWidget.find('.progress .progress-bar').delay(1000).fadeOut();
                    uploadWidget.find('.file-box').hide();
                }
            }
        });
    });
};

// 移除现有图片功能
function removeUploadWidget() {
    var uploadWidget = $('.upload-widget');
    uploadWidget.delegate('.remove', 'click', function (event) {
        var _uploadWidget = $(event.delegateTarget);
        var file_id = _uploadWidget.find('.file_id');
        file_id.val('');
        $(this).parent('.image').remove(); // 不能用隐藏，否则删除再上传原来的图片则会无法正常显示
        _uploadWidget.find('.file-box').show();
        return false;
    });
}


var videoupload = function(){
    $('.videoupload').fileupload({
        dropZone: $(this),
        dataType: 'json',
        url: $('.videoupload').attr('data-server'),

        add: function (e, data) {
            var acceptFileTypes = /^video\/(mp4)$/i;
            if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])){
                alert("上传文件类型不正确");
                return;
            }
            data.submit();
        },

        done: function (e, data) {
            if (data.result.status == false) {
                alert(data.result.message);
            } else {
                var _uploadwidget = $(this).parents('.vedioUploadWidget');
                var uploadFile = data.result;
                var src = window.URL.createObjectURL(data.files[0]);
                /*_uploadwidget.find('.videoupload').attr('href', src);
                _uploadwidget.find('.videoupload ').val(data['result']['id']);*/
                _uploadwidget.find('.file_id').val(uploadFile['fileId']);
                _uploadwidget.append('<a href="' + src + '" target="_blank">' + uploadFile['fileName'] + '</a>');
                alert('视频上传成功');
            }
        }
    });
};

$(function() {
    fileupload();
    removeUploadWidget();

    videoupload();

    $('.createUpload').click(function(){
        var _this = $(this);
        var upload_multi = _this.parents('.upload-multi');
        var tpl = upload_multi.find('.tpl-upload').html();
        var html = template.render(tpl);
        upload_multi.append(html);

        fileupload();
        removeUploadWidget();
        return false;
    });
});