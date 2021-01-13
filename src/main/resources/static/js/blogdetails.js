let url = "api/"
let current_group_id = 0
let connection_status = false
let user_id = 0

// let session_user_id = sessionStorage["user"].toString()
// console.log(session_user_id)

$(document).ready(function(){
    // var pages = [[${session.user}]];  // session(后台是user)
    // console.log(pages)
    user_id = parseInt($("#user_id").text())

    if(connection_status == false){
        connection(user_id)
    }

    $(".group_info").hide()

    /**
     * 通过用户ID获取群聊组
     * **/
    let get_url = url + "group_chat/all/group/" + user_id
    $.get(get_url,function(data,status){
        // alert("数据: " + data + "\n状态: " + status);
        // console.log(data)
        console.log(user_id)
        let len = data.length
        console.log(data)
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
                $.get("/user_search/" + data[i].adminID, function(data1,status){
                    fad(current_group_id, data1.name, data[i].adminID)
                });
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

    //创建群聊
    $("#create").click(function (){
        $(".main").fadeIn();
        $(".mainbox").delay(0).slideDown();
    })
    $(".close_create").click(function(){
        $(".main").fadeOut();
    });
    $("#submit_create").click(function(){
        let group_name = $("#create_group_name").val()
        let group_notice = $("#create_group_notice").val()
        if(group_name.length == 0){
            alert("The Group's Name can't be empty!")
        } else {
            $.post(url + "group_chat/insert",
                {
                    adminid:user_id,
                    group_name:group_name,
                    notice:group_notice
                },
                function(data,status){
                    // alert("数据: \n" + data + "\n状态: " + status);
                    if(status == "success"){
                        let g_id = data
                        $.post(url + "group_members/insert",
                            {
                                user_id:user_id,
                                group_id:g_id
                            },
                            function(data_2,status_2){
                                if(status_2 == "success"){
                                    alert("Creation Success!!")
                                    $("#create_group_name").text("")
                                    $("#create_group_notice").text("")
                                    $(".main").fadeOut();
                                    window.location.reload()
                                } else {
                                    alert("Creation Failed! Please wait and try again later!")
                                }
                            });
                    } else {
                        alert("Creation Failed! Please wait and try again later!")
                    }
            });
        }
    });


    //加入群聊
    $("#join").click(function (){
        $(".main_2").fadeIn();
        $(".mainbox_2").delay(0).slideDown();
    })
    $(".close_create_2").click(function(){
        $(".g_list_member").empty()
        $(".main_2").fadeOut();
    });
    $("#searchByID").click(function(){
        $(".g_list_member").empty()
        let group_id_that = $("#search_group_id").val()
        if(group_id_that.length == 0){
            alert("ID can't be empty!")
        } else {
            let group_id_this = parseInt(group_id_that)
            $.get(url + "group_chat/group/"+ group_id_this,function(data,status){
                if(status == "success"){
                    if(data.length == 0){
                        alert("No groups!")
                        return
                    }
                    let str = "<span>Group Name:</span> <span class=\"group_fix\" id=\"g_info_name\">"+data[0].groupName+"</span>\n" +
                        "<span>Group Admin:</span> <span class=\"group_fix\" id=\"g_info_admin\">"+ data[0].admin_id +"</span>\n" +
                        "<span>Group ID:</span> <span class=\"group_fix\" id=\"g_info_id\">"+ data[0].id +"</span>\n" +
                        "<input type=\"submit\" name=\"submit\" value=\"Join\" id=\"join_group\">"
                    $(".g_list_member").append(str)
                    $(".group_info").show()
                    $("#search_group_id").val("")
                    $("#search_group_name").val("")
                } else {
                    alert("Search Failed! Please wait for a moment!")
                }
            });
        }
    });
    $("#searchByName").click(function(){
        $(".g_list_member").empty()
        let group_name_that = $("#search_group_name").val()
        if(group_name_that.length == 0){
            alert("Name can't be empty!")
        } else {
            $.get(url + "group_chat/group/name/"+ group_name_that, function(data,status){
                if(status == "success"){
                    let str = ""
                    if(data.length == 0){
                        alert("No groups!")
                        return
                    }
                    for(let i = 0; i< data.length; i++){
                        str = "<div><span>Group Name:</span> <span class=\"group_fix\" id=\"g_info_name\">"+data[i].groupName+"</span>\n" +
                            "<span>Group Admin:</span> <span class=\"group_fix\" id=\"g_info_admin\">"+ data[i].admin_id +"</span>\n" +
                            "<span>Group ID:</span> <span class=\"group_fix\" id=\"g_info_id\">"+ data[i].id +"</span>\n" +
                            "<input type=\"submit\" name=\"submit\" value=\"Join\" id=\"join_group\"></div>"
                        $(".g_list_member").append(str)
                    }
                    $(".group_info").show()
                    $("#search_group_name").val("")
                    $("#search_group_id").val("")
                } else {
                    alert("Search Failed! Please wait for a moment!")
                }
            });
        }
    });


    //加入群聊
    $(".g_list_member").on('click',"#join_group",function(){
        let str = $(this).siblings("#g_info_id").text()
        let this_group_id = parseInt(str)
        $.post(url + "/group_members/insert",
            {
                user_id:user_id,
                group_id:this_group_id
            },
            function(data_2,status_2){
                if(status_2 == "success"){
                    alert("Join Success!!")
                    $(".main").fadeOut();
                    window.location.reload()
                } else {
                    alert("Creation Failed! Please wait and try again later!")
                }
            });
    });


    /**
     * 通过群聊ID获取群聊消息
     * **/
    function fad(group_id, admin, admin_id) {
        let get_groupRecord_url = "api/group_chat_record/allbyId/" + group_id
        $.get(get_groupRecord_url,function(data1,status){
            // alert("数据: " + data + "\n状态: " + status);
            // console.log(data1)
            let len_1 = data1.length
            let str_1 = ""
            for (let j = len_1 - 1; j>=0 ;j--){
                $.get("/user_search/" + data1[j].from, function(data2,status){
                    str_1 = "<div class=\"post-comment\">\n" +
                        "       <a class=\"pull-left\" href=\"#\">\n" +
                        "           <img class=\"media-object\" src=\""+ data2.profile_photo +"\" style='width: 100px; height: 100px;' alt=\"\">\n" +
                        "       </a>\n" +
                        "       <div class=\"media-body\">\n" +
                        "                   <span><i class=\"fa fa-user\"></i>Posted by <a\n" +
                        "                           href=\"#\">" + data2.name + "</a></span>\n" +
                        "           <p>"+ data1[j].record_content +"</p>\n" +
                        "           <ul class=\"nav navbar-nav post-nav\">\n" +
                        "               <li><a href=\"#\"><i class=\"fa fa-clock-o\"></i>"+ data1[j].record_date +"</a></li>\n" +
                        "               <li><a href=\"#\"><i class=\"fa fa-reply\"></i>Reply</a></li>\n" +
                        "           </ul>\n" +
                        "       </div>\n" +
                        "   </div>"
                    $("#ChatRecord").append(str_1)
                });
            }
        });
        $("#admin_name").text(admin)   //设置管理员
        $.get("/user_search/" + admin_id, function(data3,status){
            $("#admin_image").attr("src", data3.profile_photo)
        });

        $.get("/api/group_members/all/GroupMembers/" + group_id, function(data4,status){
            for(let m =0;m<data4.length;m++){
                $.get("/user_search/" + data4[m].user_id, function(data5,status){
                    let str = "<li><a href=\"#\"><img src=\""+ data5.profile_photo +"\" alt=\"\"></a></li>"
                    $(".gallery").append(str)
                });
            }
        });

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
            $.get("/user_search/" + data[0].adminID, function(data1,status){
                fad(group_id, data1.name, data[0].adminID)
            });
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
            index = new WebSocket("ws://localhost:8073/websocket/" + user_id);
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
                    $.get("/user_search/" + data.from, function(data1,status){
                        let str_add = "<div class=\"post-comment\">\n" +
                            "       <a class=\"pull-left\" href=\"#\">\n" +
                            "           <img class=\"media-object\" src=\""+ data1.profile_photo +"\" style='width: 100px; height: 100px;' alt=\"\">\n" +
                            "       </a>\n" +
                            "       <div class=\"media-body\">\n" +
                            "                   <span><i class=\"fa fa-user\"></i>Posted by <a\n" +
                            "                           href=\"#\">" + data1.name + "</a></span>\n" +
                            "           <p>"+ data.content +"</p>\n" +
                            "           <ul class=\"nav navbar-nav post-nav\">\n" +
                            "               <li><a href=\"#\"><i class=\"fa fa-clock-o\"></i>"+ date11 +"</a></li>\n" +
                            "               <li><a href=\"#\"><i class=\"fa fa-reply\"></i>Reply</a></li>\n" +
                            "           </ul>\n" +
                            "       </div>\n" +
                            "   </div>"
                        // console.log("添加元素！")
                        $("#ChatRecord").append(str_add)
                        $("#message_sent").val("")
                        // $("html,body").animate({scrollTop:$("#ChatRecord").offset().top},1500);
                    });
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
        var da =  new Date(sec)
        var year = da.getFullYear()+'-';
        var month = da.getMonth()+1+'-';
        var date = da.getDate()+' ';
        var hour = da.getHours()+':'
        var minute= da.getMinutes()+':'
        var second = da.getSeconds()
        return year+month+date+hour+minute+second
    }


});

