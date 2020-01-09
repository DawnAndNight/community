function  post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content){
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type:"post",
        url:"/comment",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data:JSON.stringify({"parentId":questionId,
        "content":content,
        "type":1})
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
            console.log(response);
        },
    });
    console.log(questionId);
}