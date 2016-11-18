var http = require('http');
var fs = require('fs');
var socketio = require('socket.io');

var mongoose  = require('mongoose');

var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function() {
    console.log('connect db');
});
mongoose.connect('mongodb://localhost/hitchhiker');

var schema = mongoose.Schema;

var user_model = new schema({
    name: String,
    gender: Boolean,
    univ: schema.Types.ObjectId,
    email: String,
    password: String,
    money: Number
});

var driver_model = new schema({
    name: String,
    gender: Boolean,
    is_in_serve: Boolean,
    univ: schema.Types.ObjectId,
    email: String,
    password: String,
    money: Number
});

var deal_model = new schema({
    user: schema.Types.ObjectId,
    driver: schema.Types.ObjectId,
    money: Number,
    time: Date
});

var party_model = new schema({
    member: [schema.Types.ObjectId],
    driver: schema.Types.ObjectId,
    state: String,
    position: String,
    univ: schema.Types.ObjectId
});

var univ_model = new schema({
    name: String,
    position: String
});

var user = mongoose.model('user', user_model, 'user');
var driver = mongoose.model('driver', driver_model, 'driver');
var deal = mongoose.model('deal', deal_model, 'deal');
var party = mongoose.model('party', party_model, 'party');
var univ = mongoose.model('univ', univ_model, 'univ');

var server = http.createServer(function(req, res) {

}).listen(8080, function() {
    console.log('server on');

    // user.find({}, function(err, data) {
    //     console.log(data);
    // });
});

var io = socketio.listen(server);
io.sockets.on('connection', function(socket) {
    // 회원가입
    socket.on('sign_up', function(data){
        user.findOne({ 
            email: data.email 
        }).exec(function(err, usr){
            if(err) {
                console.log('error');
            } else if(!usr) {
                // 디비에 중복되는 이메일이 없음
                new user({name: data.name, gender: data.gender, univ: null, email: data.email, password: data.password, money: 0}).save();
                socket.emit('sign_up_success');
            } else {
                // 이미 가입됨
                socket.emit('sign_up_fail');
            }
        });
    });

    // 로그인
    socket.on('sign_in', function(data) {
        user.findOne({
            email: data.email,
            password: data.password
        }).exec(function(err, usr){
            if(err) {
                console.log('error');
            } else if(!usr) {
                socket.emit('sign_in_fail');
            } else {
                socket.emit('sign_in_success', {name: usr.name, gender: usr.gender, univ: usr.univ, money: usr.money});
            }
        });
    });

    // ㅁㄹ
    socket.on()
});