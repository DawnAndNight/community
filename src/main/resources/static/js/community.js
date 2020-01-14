
function  post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,content,1)
}

function comment(e){
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    comment2target(id,content,2);

}

function collaspComment(e) {
    var id = e.getAttribute("data-id");
    var comment  = $("#comment-"+id);
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        comment.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        $.getJSON("/comment/"+id,function (data) {
            var subCommentContainer = $("#comment-"+id);
            if(subCommentContainer.children().length != 1){
                comment.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            }else {

                $.each(data.data, function (index, comment) {
                    var avartUrlElemet=$("<img/>",
                        {"class":"media-object img-rounded img_class",
                        "src":comment.user.avertUrl
                        });
                    var mediaBody = $("<div/>", {"class":"media-body"}).
                    append($("<h5/>",
                        {"class":"media-heading",
                            html:comment.user.name}))
                        .append($("<div/>", {html:comment.content}))
                        .append($("<div/>", {"class":"menu"})
                            .append($("<span/>",
                                {"class":"pull-right",html:moment(comment.gmtCreate).format("YYYY-MM-DD")})));

                    var mediaLeftElement=$("<div/>",{"class":"media-left" });
                    mediaLeftElement.append(avartUrlElemet);
                    var mediaElement = $("<div/>", {"class":"media"});
                    mediaElement.append(mediaLeftElement).append(mediaBody);
                    subCommentContainer.prepend("<hr/>",{"class": "col-gl-12 col-md-12 col-xs-12 col-sm-12"}).prepend(mediaElement);
                });
                comment.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            }
            subCommentContainer.trigger("create");
        })
    }
}


function comment2target(targetId,content,type){
    if (!content){
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:JSON.stringify({"parentId":targetId,
            "content":content,
            "type":type})
        ,
        success(response){
            if(response.code == 200){
                window.location.reload();
            }else {
                if (response.code == 2003){
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=8c73ddaefc8e741d31f9&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closetab","true");
                    }
                }
            }
        },
    });
}