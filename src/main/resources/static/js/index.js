let url = "http://10.62.35.25:8083/api/"
let uu = "10.62.35.25:8083/"
// window.onload=function(){
//     // let text = $("#test2").text()
//     // console.log(text)
// }

$("#test2").click(function(){
    $("#test2").text("Hello world!");
});

$("#btn1").click(function(){
    let get_url = url + "group_chat/all/group/2"
    $.get(get_url,function(data,status){
        // alert("数据: " + data + "\n状态: " + status);
        console.log(data)
    });
});

$("#btn2").click(function(){
    let post_url = url + "group_members/insert"
    $.post(post_url,
        {
            user_id:3,
            group_id:2
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
        });
});

$("#btn3").click(function(){
    var socket;
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于
        index = new WebSocket("ws://"+ uu +"websocket/4");
        //socket = new WebSocket("${basePath}websocket/${cid}".replace("http","ws"));
        //打开事件
        index.onopen = function() {
            console.log("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        index.onmessage = function(msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        index.onclose = function() {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        index.onerror = function() {
            alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }
});

$("#btn4").click(function(){
    $.post("/send",
        {
            message:"socket_1's test message.",
            from:"1",
            //to:"2",
            group_id:1
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
        });
});

$("#btn5").click(function(){
    var socket;
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于
        index = new WebSocket("ws://"+ uu +"websocket/2");
        //socket = new WebSocket("${basePath}websocket/${cid}".replace("http","ws"));
        //打开事件
        index.onopen = function() {
            console.log("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        index.onmessage = function(msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        index.onclose = function() {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        index.onerror = function() {
            alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }
});

$("#btn6").click(function(){
    $.post("/send",
        {
            message:"socket_2's test message.",
            from:"2",
            //to:"1",
            group_id:2
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
        });
});

$("#btn7").click(function(){
    var socket;
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于
        index = new WebSocket("ws:"+ uu +"websocket/3");
        //socket = new WebSocket("${basePath}websocket/${cid}".replace("http","ws"));
        //打开事件
        index.onopen = function() {
            console.log("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        index.onmessage = function(msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        index.onclose = function() {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        index.onerror = function() {
            alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }
});

$("#btn8").click(function(){
    $.post("/send",
        {
            message:"socket_3's test message.",
            from:"3",
            //to:"2",
            group_id:2
        },
        function(data,status){
            alert("数据: \n" + data + "\n状态: " + status);
        });
});