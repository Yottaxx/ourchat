let connection_status = false
let user_name = $("#single_user_name").text()
let friend_name = $("#single_friend_name").text()
var friend_photo="";

var singleid = 0;

$.post("/api/single_chat/singlechat_user_details", {
    user_name:friend_name
},function(data,status){
    friend_photo = data

});
var my_photo="";
$.post("/api/single_chat/singlechat_user_details", {
    user_name:user_name
},function(data,status){
    my_photo = data

});

//显示聊天记录
$("#single_Record").click(function (){
        $.post("/api/single_chat/singlechat_getid",{
        user_name:user_name,
        friend_name:friend_name
    },function (data2,status){
            console.log(data2)
            singleid=data2;
            $.get("/api/single_chat_record/all",function (data,status){
                let i=data.length
                for(let j=0;j<i;++j){
                    if(data[j].singleChatID ==singleid){
                        let friend_photo_1;
                        $.post("/api/single_chat/singlechat_user_details", {
                            user_name:data[j].mes_from
                        },function(data1,status){
                            friend_photo_1 = data1
                            let single_chat_content = data[j].recordContent
                            let date4 = new Date(data[j].recordDate)
                            let date44 = date4.getFullYear()+"-"+(date4.getMonth()+1)+"-"+ date4.getDate() + "  " +date4.getHours() + ":" + date4.getMinutes() + ":" + date4.getSeconds();
                            let str_left = "<li class=\"media\"><!--对方聊天-->\n" +
                                "                        <div class=\"post-comment\" style=\"padding-left: 10px;padding-right: 10px;border-top: 0px;\">\n" +
                                "                            <a class=\"pull-left\" href=\"#\">\n" +
                                "                                <img style=\"border-left:0px;margin-right: 30px\" class=\"media-object\"\n" +
                                "                                     src=\""+friend_photo_1+"\" height=\"137\" width=\"127\" alt=\"user2\">\n" +
                                "                            </a>\n" +
                                "                            <div class=\"media-body\">\n" +
                                "                                <span style=\"font-size: 14px\" id=\"single_friend_name\"><i\n" +
                                "                                    class=\"fa fa-user\"></i>"+data[j].mes_from+"</span>\n" +
                                "                                <p style=\"padding-bottom: 20px;font-size: 22px;\">"+single_chat_content+"</p>\n" +
                                "                                <ul class=\"nav navbar-nav post-nav\">\n" +
                                "                                    <li><a style=\"font-size: 13px\" href=\"#\"><i class=\"fa fa-clock-o\"></i>"+date44+"</a></li>\n" +
                                "                                </ul>\n" +
                                "                            </div>\n" +
                                "                        </div>\n" +
                                "                    </li>"
                            let str_right = "<li class=\"media\"> <!--我的聊天-->\n" +
                                "                  <div class=\"post-comment\" style=\"padding-left: 10px;padding-right: 10px;border-top: 0px;\">\n" +
                                "                      <a class=\"pull-right\" href=\"#\">\n" +
                                "                          <img style=\"margin-left: 30px\"  src=\""+friend_photo_1+"\" height=\"137\" width=\"127\" alt=\"user1\">\n" +
                                "                      </a>\n" +
                                "                      <div class=\"media-body\">\n" +
                                "                          <span  class=\"pull-right\" style=\"font-size: 14px\"><i class=\"fa fa-user\"></i>"+data[j].mes_from+"</span>\n" +
                                "<!--                          <a href=\"#\">     Tap</a>-->\n" +
                                "                          <p style=\"padding-bottom: 20px;font-size: 22px;padding-top: 20px;text-align: right\">"+single_chat_content+"</p>\n" +
                                "                          <ul class=\"nav navbar-nav post-nav\" style=\"float: right\">\n" +
                                "                              <li style=\"margin-right: 0px;\"><a style=\"font-size: 13px\" href=\"#\"><i class=\"fa fa-clock-o\"></i>"+date44+"</a></li>\n" +
                                "                          </ul>\n" +
                                "                      </div>\n" +
                                "                                            </div>\n" +
                                "                                        </li>"
                            if(data[j].mes_from == user_name){
                                $("#single_chat_window").append(str_right)
                            } else {
                                $("#single_chat_window").append(str_left)
                            }
                        });
                    }
                }
            })
        });
});

$(document).ready(function(){

    if(connection_status == false){
        connection(user_name)
    }
    // data[0].id
    $("#single_sendMessage").click(function (){
        let message = $("#single_message_sent").val()
        let friend_name = $("#single_friend_name").text()
        $.post("/api/single_chat/singlechat_user_details", {
            user_name:user_name
        },function(data,status){
            my_photo = data
            console.log(my_photo)
            // alert("数据: " + data + "\n状态: " + status);
        });

        send_message(message,user_name,friend_name,my_photo);
    });

    /**
     * 连接消息服务器
     * **/
    function connection(user_name){
        var socket;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            //等同于
            index = new WebSocket("ws://localhost:8073/websocket/" + user_name);
            //socket = new WebSocket("${basePath}websocket/${cid}".replace("http","ws"));
            //打开事件
            index.onopen = function() {
                connection_status = true
                console.log("Socket 已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            index.onmessage = function(msg) {
                console.log(msg);
                console.log(msg.data);
                let data = JSON.parse(msg.data)
                let date111 = new Date()
                let date11 = date111.getFullYear()+"-"+(date111.getMonth()+1)+"-"+ date111.getDate() + "  " +date111.getHours() + ":" + date111.getMinutes() + ":" + date111.getSeconds();
                $.post("/api/single_chat/singlechat_user_details", {
                    user_name:data.from
                },function(data,status){
                    friend_photo = data
                    // console.log(friend_photo)
                    // alert("数据: " + data + "\n态: " + status);
                });
                if (data.content != null){
                    // 发现消息进入    开始处理前端触发逻辑
                    let str_add = ""
                    console.log("添加元素！")
                    console.log(friend_photo)
                    // $("#single_friend_message_1").text(data.content)
                    $("#single_chat_window").append("<li class=\"media\"><!--对方聊天-->\n" +
                        "                        <div class=\"post-comment\" style=\"padding-left: 10px;padding-right: 10px;border-top: 0px;\">\n" +
                        "                            <a class=\"pull-left\" href=\"#\">\n" +
                        "                                <img style=\"border-left:0px;margin-right: 30px\" class=\"media-object\"\n" +
                        "                                     src=\""+friend_photo+"\" height=\"137\" width=\"127\" alt=\"user2\">\n" +
                        "                            </a>\n" +
                        "                            <div class=\"media-body\">\n" +
                        "                                <span style=\"font-size: 14px\" id=\"single_friend_name\"><i\n" +
                        "                                    class=\"fa fa-user\"></i>"+data.from+"</span>\n" +
                        "                                <p style=\"padding-bottom: 20px;font-size: 22px;\">"+data.content+"</p>\n" +
                        "                                <ul class=\"nav navbar-nav post-nav\">\n" +
                        "                                    <li><a style=\"font-size: 13px\" href=\"#\"><i class=\"fa fa-clock-o\"></i>"+date11+"</a></li>\n" +
                        "                                </ul>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </li>");

                    // $("#single_sendMessage").val("")
                    $("html,body").animate({scrollTop:$("#single_chat_window").offset().top},1500);
                }
            };
            //关闭事件
            index.onclose = function() {
                console.log("Socket已关闭");
                connection_status = false
            };
            //发生了错误事件
            index.onerror = function() {
                alert("Socket发生了错误");
                connection_status = false
                //此时可以尝试刷新页面
            }
        }
    }

    /**
     * 发送消息
     * **/
    function send_message(message, user_name, friend_name, my_photo){
        $.post("/singlechat_send",
            {
                message:message,
                from_name:user_name,
                to_name:friend_name
            },
            function(data,status){
                // alert("数据: \n" + data + "\n状态: " + status);
            });
        let date22 = new Date();
        let date33 = date22.getFullYear()+"-"+(date22.getMonth()+1)+"-"+ date22.getDate() + "  " +date22.getHours() + ":" + date22.getMinutes() + ":" + date22.getSeconds();
        $("#single_chat_window").append("    <li class=\"media\"> <!--我的聊天-->\n" +
            "        <div class=\"post-comment\" style=\"padding-left: 10px;padding-right: 10px;border-top: 0px;\">\n" +
            "            <a class=\"pull-right\" href=\"#\">\n" +
            "                <img style=\"margin-left: 30px\" src=\""+my_photo+"\" height=\"137\" width=\"127\" alt=\"user1\">\n" +
            "            </a>\n" +
            "            <div class=\"media-body\">\n" +
            "                <span  class=\"pull-right\" style=\"font-size: 14px\" ><i\n" +
            "                    class=\"fa fa-user\"></i>"+user_name+"</span>\n" +
            "                <!--                                                    <a href=\"#\">     Tap</a>-->\n" +
            "                <p id=\"single_my_message\" style=\"padding-bottom: 20px;font-size: 22px;padding-top: 20px;text-align: right\">"+message+"</p>\n" +
            "                <ul class=\"nav navbar-nav post-nav\" style=\"float: right\">\n" +
            "                    <li style=\"margin-right: 0px;\"><a id='single_time' style=\"font-size: 13px\" href=\"#\"><i class=\"fa fa-clock-o\"></i>"+date33+"</a></li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </li>");
        // $("#single_time").text(new Date())
        // $("#single_my_name").text(user_name)
        // $("#single_my_message").text(message)
        $("#single_message_sent").val("")
    }

    /**
     * 将时间戳转换成正常时间格式
     * **/
    function timestampToTime(sec) {
        let hour = Math.floor(sec / 3600);
        let day = Math.floor(hour/24);

        let minute = Math.floor((sec - hour * 3600) / 60);
        let second = sec - hour * 3600 - minute * 60;
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minute < 10) {
            minute = "0" + minute;
        }
        if (second < 10) {
            second = "0" + second;
        }
        return hour + ":" + minute + ":" + second;
    }
});