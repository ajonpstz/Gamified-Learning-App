var app = require('express')();
var http = require('http').createServer(app);
var io = require('socket.io')(http);
var fs = require("fs");

app.get('/', function(req, res){
     res.send('<h1>Hello world</h1>');
});

http.listen(3000, function(){
     console.log('listening on *:3000');
});

io.on('connection', function(socket){
     console.log('Connection')
     socket.on('get_courses', function(msg){
          console.log('message: ' + msg);
     });
     fs.readFile("Courses.data",function(error, filedata){
        if(error) throw error;
        else socket.emit("Courses", filedata.toString() );
    });
});
