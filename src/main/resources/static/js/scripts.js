/*!
* Start Bootstrap - Modern Business v5.0.5 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

var CONTEXT_PATH = "http://plusl.natapp1.cc";

$(function(){
    $("#publishBtn").click(publish);
});

function publish() {
    var title = $("title").val();
    var content = $("description").val();
    //发送异步请求
    $.post(
        "/community/add",
        {"title":title,"content":content},
        function (data) {
            data = $.parseJSON(data);
            $("#hintBody").text(data.msg);
        }
    )
}