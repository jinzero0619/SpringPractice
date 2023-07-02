<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        .container {
            height: 50%;
            width : 50%;
            margin : auto;
        }
        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }
        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
        #textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }
        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }
        .btn:hover {
            text-decoration: underline;
        }
        #commentList {
            height: 50%;
            width : 50%;
            margin : auto;
        }
        .sort {
            display:flex;
            flex-direction: column;
            border:  1px solid rgb(235,236,239);
            border-bottom : 0;
        }
        .commentoption {
            width : 100%;
            display: flex;
            flex-direction: row;
            vertical-align: middle;
            background: #1b2133;
        }
        #comment-writebox {
            position: static;
            background-color: white;
            border : 1px solid #e5e5e5;
            border-radius: 5px;
        }

        textarea {
            display: block;
            width: 100%;
            min-height: 17px;
            padding: 0 20px;
            border: 0;
            outline: 0;
            font-size: 13px;
            resize: none;
            box-sizing: border-box;
            background: transparent;
            overflow-wrap: break-word;
            overflow-x: hidden;
            overflow-y: auto;
        }

        #comment-writebox-bottom {
            padding : 3px 10px 10px 10px;
            min-height : 35px;
        }
        .comment-img {
            font-size:36px;
            position: absolute;
        }
        .comment-area {
            position: static;
        }

    /*modal*/
        #modify-writebox {
            background-color: white;
            border : 1px solid #e5e5e5;
            border-radius: 5px;
            margin : 10px;
        }

        #modify-writebox-bottom {
            padding : 3px 10px 10px 10px;
            min-height : 35px;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        /* The Close Button */
        .close {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }

    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<script>
    let msg = "${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="Write_Ok") alert("댓글 성공");
</script>
<div class="container">
    <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" class="frm" action="" method="post">
        <input type="hidden" name="bno" value="${boardDto.bno}">

        <input name="title" type="text" value="${boardDto.title}" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
        <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>${boardDto.content}</textarea><br>

        <c:if test="${mode eq 'new'}">
            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
        </c:if>
        <c:if test="${mode ne 'new'}">
            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
        </c:if>
        <c:if test="${boardDto.writer eq loginId}">
            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
        </c:if>
        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
        <button type="button" id="commentListBtn" class="btn btn-list"><i class="fa fa-bars"></i> 댓글</button>
    </form>
</div>
<script>
    $(document).ready(function(){
        let formCheck = function() {
            let form = document.getElementById("form");
            if(form.title.value=="") {
                alert("제목을 입력해 주세요.");
                form.title.focus();
                return false;
            }
            if(form.content.value=="") {
                alert("내용을 입력해 주세요.");
                form.content.focus();
                return false;
            }
            return true;
        }
        $("#writeNewBtn").on("click", function(){
            location.href="<c:url value='/board/write'/>";
        });
        $("#writeBtn").on("click", function(){
            let form = $("#form");
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#modifyBtn").on("click", function(){
            let form = $("#form");
            let isReadonly = $("input[name=title]").attr('readonly');
            // 1. 읽기 상태이면, 수정 상태로 변경
            if(isReadonly=='readonly') {
                $(".writing-header").html("게시판 수정");
                $("input[name=title]").attr('readonly', false);
                $("textarea").attr('readonly', false);
                $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                return;
            }
            // 2. 수정 상태이면, 수정된 내용을 서버로 전송
            form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#removeBtn").on("click", function(){
            if(!confirm("정말로 삭제하시겠습니까?")) return;
            let form = $("#form");
            form.attr("action", "<c:url value='/board/remove${searchCondition.queryString}'/>");
            form.attr("method", "post");
            form.submit();
        });
        $("#listBtn").on("click", function(){
            location.href="<c:url value='/board/list${searchCondition.queryString}'/>";
        });
        $("#commentListBtn").on("click",function (){
            showList(${boardDto.bno});
        })
    });
</script>
<div id="commentList" class="commentList">
    <div id="commentarea" class="comment-area"></div>
    <div id="comment-writebox">
<%--        <div class="commenter-writebox">${id}</div>--%>
        <div class="comment-writebox-content">
            <textarea name="comment" id="textarea" cols="30" rows="3" placeholder="${id}님 댓글을 남겨보세요!"></textarea>
        </div>
        <div id="comment-writebox-bottom">
            <div class="register-box">
                <button id="registerBtn" class="btn btn-list"><i class="fa fa-pencil"></i>등록</button>
            </div>
        </div>
    </div>
</div>
<div id="modalWin" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>
        <h2> | 댓글 수정</h2>
        <div id="modify-writebox">
            <div class="commenter commenter-writebox"></div>
            <div class="modify-writebox-content">
                <textarea name="modal_content" id="modaltextarea" cols="30" rows="5" placeholder="댓글을 남겨보세요">안녕</textarea>
            </div>
            <div id="modify-writebox-bottom">
                <div class="register-box">
                    <button class="modal_modifyBtn"><i class="fa fa-pencil"></i>등록</button>
                </div>
            </div>
        </div>
        </p>
    </div>
</div>
<script>
    let showList = function (bno) {
        $.ajax({
            type : 'GET',
            url : '/ch4/comments?bno=' + bno,
            // headers: {"content-type" : "application/json"},
            // dataType : 'json',   # 데이터타입 생략시 default 가 JSON
            // data : JSON.stringify()
            success : function (result) {
                $("#commentarea").html(toHtml(result));
            },
            error : function () { alert("error")}
        });
    }

    let toHtml = function (comments) {
        let tmp = "<ul class='sort'>"
        let space = "&nbsp";
        comments.forEach(function(comment){

            tmp += '<li class="commentoption" data-cno=' + comment.cno
            tmp += ' data-pcno=' + comment.pcno
            tmp += ' data-bno=' + comment.bno + '>'
            tmp += ' <div id="commenter" class="commenter">' +"[" +comment.commenter + "] " +'</div>' + space
            tmp += ' <div id="content" class="comment-content">' + comment.comment + '</div>' + space + space
            tmp +=  "[" + comment.up_date  + "]"
            tmp += ' <div id="commentBottom" class="comment-bottom">'
            if(comment.commenter == "${sessionScope.id}"){
                tmp += '<button class="delBtn">삭제</button>'
                tmp += '<button class="modifyBtn">수정</button>'
            }
            tmp += '</div>'
            tmp += '</li>'
        })
            return tmp + "</ul>";
    }

    // commentList 불러오고 나서의 작업이기 때문에 ( ajax 비동기 과정)
    // 고정된 요소에 이벤트를 걸어야
    // 서버에서 데이터를 수신받고 난 이후의 처리 가능
    $(document).ready(function () {
       $("#commentList").on("click", ".delBtn",function (){
           let cno = $('#commentBottom').parent().attr("data-cno");
           let bno = $('#commentBottom').parent().attr("data-bno");
           $.ajax({
               type : 'DELETE',
               url : '/ch4/comments/'+cno + '?bno=' + bno,
               success : function (result) {
                   showList(bno);
               },
               error : function () { alert("error")}
           });
       })
       $("#commentList").on("click",".modifyBtn",function(e){
           let target = e.target;
           let cno = $(this).parent().parent().attr("data-cno");
           let bno = $(this).parent().parent().attr("data-bno");
           let commentList = $(this).parent().parent().children('div')
           let comment = "${comment[1].innerHTML}"
           $("#modalWin .commenter").text("${loginId}");
           $("#modalWin .content").text(comment);
           // $("#btn-write-modify").attr("data-pcno", pcno);
           $("#modify-writebox").attr("data-cno", cno);
           $("#modify-writebox").attr("data-bno", bno);
           $("#modalWin").css("display","block");
       })
        $(".close").click(function(){
            $("#modalWin").css("display","none");
        });
        $("#modalWin").on("click",".modal_modifyBtn",function () {
            let cno =  $("#modify-writebox").attr("data-cno")
            let bno =  $("#modify-writebox").attr("data-bno")
            let comment = $('textarea[name=modal_content]').val();
            $.ajax({
                type : 'PATCH',
                url : '/ch4/comments/'+cno,
                headers : { "content-type": "application/json"},
                data : JSON.stringify({bno : bno, comment : comment}),
                success : function (result) {
                    alert(result)
                    showList(bno);
                },
                error : function () { showList(${boardDto.bno});}
            });
        })

      $("#registerBtn").on("click", function (){
          let comment = $('textarea[name=comment]').val();
          $.ajax({
              type : 'POST',
              url : '/ch4/comments?bno='+${boardDto.bno},
              headers : { "content-type": "application/json"},
              data : JSON.stringify({comment : comment}),
              success : function (result) {
                  alert(result)
                  showList(${boardDto.bno});
              },
              error : function () { showList(${boardDto.bno});}
          });
      })
    })
</script>
</body>
</html>