let url = "api/"
let current_group_id = 0
let connection_status = false
let user_id = 2

$(document).ready(function(){

    if(connection_status == false){
        connection(user_id)
    }

    /**
     * 通过用户ID获取群聊组
     * **/
    let get_url = url + "group_chat/all/group/" + user_id
    $.get(get_url,function(data,status){
        // alert("数据: " + data + "\n状态: " + status);
        // console.log(data)
        let len = data.length
        let str = ""
        for(let i = 0; i< len; i++){
            if(i == 0){
                str = "<li class=\"active\"><a href=\"#\">"+ data[i].groupName + "<span class=\"pull-right\">ID:<span class='group_id'>"+ data[i].id +"</span></span></a></li>"
                /**
                 *  默认第一个Group
                 * **/
                $("#group_Name").text(data[i].groupName)
                $("#Notice").text(data[i].notice)
                current_group_id = data[i].id

                /**
                 * 通过群聊ID获取群聊消息
                 * **/
                fad(current_group_id, data[i].adminID)
            } else {
                str = "<li><a href=\"#\">"+ data[i].groupName + "<span class=\"pull-right\">ID:<span class='group_id'>"+ data[i].id +"</span></span></a></li>"
            }
            $("#groupName").append(str)
        }
    });

    //选择群聊
    $("#groupName").on('click',"li",function(){
        let group_id = $(this).children("a").children("span").children(".group_id").text()
        $(this).siblings(".active").attr("class", "no" )
        $(this).attr("class", "active" )
        getGroup(group_id)
    });

    //发送消息
    $("#sendMessage").click(function(){
        let message = $("#message_sent").val()
        if(message.length == 0){
            alert("Message can't be empty!")
        } else {
            send_message(message,user_id,current_group_id)
        }
    });


    /**
     * 通过群聊ID获取群聊消息
     * **/
    function fad(group_id, admin) {
        let get_groupRecord_url = "api/group_chat_record/allbyId/" + group_id
        $.get(get_groupRecord_url,function(data1,status){
            // alert("数据: " + data + "\n状态: " + status);
            // console.log(data1)
            let len_1 = data1.length
            let str_1 = ""
            for (let j = 0; j<len_1;j++){
                str_1 = "<div class=\"post-comment\">\n" +
                    "       <a class=\"pull-left\" href=\"#\">\n" +
                    "           <img class=\"media-object\" src=\"/images/blogdetails/2.png\" style='width: 100px; height: 100px;' alt=\"\">\n" +
                    "       </a>\n" +
                    "       <div class=\"media-body\">\n" +
                    "                   <span><i class=\"fa fa-user\"></i>Posted by <a\n" +
                    "                           href=\"#\">" + data1[j].from + "</a></span>\n" +
                    "           <p>"+ data1[j].record_content +"</p>\n" +
                    "           <ul class=\"nav navbar-nav post-nav\">\n" +
                    "               <li><a href=\"#\"><i class=\"fa fa-clock-o\"></i>"+ data1[j].record_date +"</a></li>\n" +
                    "               <li><a href=\"#\"><i class=\"fa fa-reply\"></i>Reply</a></li>\n" +
                    "           </ul>\n" +
                    "       </div>\n" +
                    "   </div>"
                $("#ChatRecord").append(str_1)
            }
        });
        $("#admin_name").text(admin)   //设置管理员
        // $("#join_time").text()                //获取管理员创建时间
    }

    /**
     * 通过群ID获取群聊组并修改信息
     * **/
    function getGroup(group_id){
        let url = "api/group_chat/group/" + parseInt(group_id)
        $.get(url,function(data,status){
            // alert("数据: " + data + "\n状态: " + status);
            // console.log(data)
            $("#group_Name").text(data[0].groupName)
            $("#Notice").text(data[0].notice)
            current_group_id = data[0].id
            $("#ChatRecord").empty()
            fad(group_id,data[0].adminID)
        });
    }

    /**
     * 连接消息服务器
     * **/
    function connection(user_id){
        var socket;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            //等同于
            index = new WebSocket("ws://localhost:8083/websocket/" + user_id);
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
                let date11 = timestampToTime(data.date)
                if (data.content != null){
                    // 发现消息进入    开始处理前端触发逻辑
                    let str_add = "<div class=\"post-comment\">\n" +
                        "       <a class=\"pull-left\" href=\"#\">\n" +
                        "           <img class=\"media-object\" src=\"/images/blogdetails/2.png\" style='width: 100px; height: 100px;' alt=\"\">\n" +
                        "       </a>\n" +
                        "       <div class=\"media-body\">\n" +
                        "                   <span><i class=\"fa fa-user\"></i>Posted by <a\n" +
                        "                           href=\"#\">" + data.from + "</a></span>\n" +
                        "           <p>"+ data.content +"</p>\n" +
                        "           <ul class=\"nav navbar-nav post-nav\">\n" +
                        "               <li><a href=\"#\"><i class=\"fa fa-clock-o\"></i>"+ date11 +"</a></li>\n" +
                        "               <li><a href=\"#\"><i class=\"fa fa-reply\"></i>Reply</a></li>\n" +
                        "           </ul>\n" +
                        "       </div>\n" +
                        "   </div>"
                    console.log("添加元素！")
                    $("#ChatRecord").append(str_add)
                    $("#message_sent").val("")
                    // $("html,body").animate({scrollTop:$("#ChatRecord").offset().top},1500);
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
            //离开页面时，关闭socket
            //jquery1.8中已经被废弃，3.0中已经移除
            // $(window).unload(function(){
            //     socket.close();
            //});
        }
    }

    /**
     * 发送消息
     * **/
    function send_message(message, from, group_id){
        $.post("/send",
            {
                message:message,
                from:from,
                //to:"2",
                group_id:group_id
            },
            function(data,status){
                alert("数据: \n" + data + "\n状态: " + status);
            });
    }

    /**
     * 将时间戳转换成正常时间格式
     * **/
    function timestampToTime(sec) {
        let hour = Math.floor(sec / 3600);
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

