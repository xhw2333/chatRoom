/* 
    聊天室主要功能
*/

// 用户信息展示
const name = window.sessionStorage.getItem('username');
// 用户id
const uid = window.sessionStorage.getItem("uid");

// 聊天对象id
let chatID = -1;
// 聊天类别 0 - 私聊 | 1 - 群组
let chatType = -1;

// 在线用户
let onlineUsers = new Set();

// 显示用户名称
document.querySelector('.user_name').innerText = name;

// 获取好友列表
getFriends(uid, 1);
// 获取群组列表
getGroupList(uid);

// var socket = new WebSocket('ws://localhost:8080/ws/users?uid=' + window.sessionStorage.getItem('uid'));
var socket = new WebSocket("ws://" + "localhost:8080" + "/web_war_exploded/chat?id=" + window.sessionStorage.getItem('uid'));

socket.addEventListener('open', function () {
    console.log('连接成功');
    //主动发送信息
    // setTimeout(() => { socket.send(JSON.stringify("你好1")) }, 2000);
})

// 接收服务端的信息
socket.onmessage = function (e) {
    const DATA = JSON.parse(e.data);
    console.log(DATA);

    let { type, data } = DATA;
    console.log(type);

    // 聊天记录
    let chatMsg = document.querySelector('.chat_msg');

    // 用户登录 用户退出
    if (type == 0 || type == -1) {
        // 在线好友
        onlineUsers = new Set(data);
        console.log(onlineUsers);

        // 好友列表
        let ul = document.getElementById('online_users');

        let li = ul.getElementsByTagName('li');

        for (let el of li) {
            const { fid } = el.dataset;
            console.log(fid,onlineUsers.has("" + fid));
            if (onlineUsers.has("" + fid)) {
                el.children[1].innerText = '(在线)';
            } else {
                el.children[1].innerText = "(离线)";
            }
        }

    }
    else {
        // 发送信息
        const { time, send_id, accept_id, target, send_name, accept_name, content } = data;

        // 不匹配时，不插入
        if (target != chatType) return;
        // 群名不匹配，不插入
        if (target == 1 && accept_id != chatID) return;

        if (uid == send_id) {
            // 自己发送的信息
            chatMsg.innerHTML += `
                <div class="my_msg">
                    <div class="my_say">
                        ${content}
                    </div>
                    <img class="my_head" src="../img/head.jpg" alt="">
                </div>
            `
        } else {
            // 别人发送的信息
            chatMsg.innerHTML += `
                <div class="other_msg">
                    <img class="other_head" src="../img/head.jpg" alt="">
                    <div class="other_say_row">
                        <i class="other_name">${send_name}</i>
                        <div class="other_say">${content}</div>
                    </div>
                </div>
            `
        }
        // 滑动到可视区
        chatMsg.lastElementChild.scrollIntoView(false);
    }
}

// 回车发送内容
let content = document.getElementById('content');

content.addEventListener('keypress', function (e) {
    if (e.keyCode === 13) {
        sendThing();
    }
})

// 点发送按钮
document.getElementById('chat_btn').addEventListener('click', sendThing)

// 发送文件
document.getElementById('file').addEventListener('change', function (e) {

    // 获取此文件
    var file = this.files[0];

    var fr = new FileReader();
    fr.readAsDataURL(file);
    fr.onload = function () {
        // socket.send(JSON.stringify({
        //     type: 0,
        //     data: fr.result
        // }));
        content.innerHTML += `<img style="vertical-align:bottom" src="${fr.result}" alt=""/>`
    }

})


// 添加好友
let friendsContainer = document.getElementById('friends_container');
// 搜索关键字
let search_key = document.querySelector(".search_content");

// 获取触发好友添加的元素
document.querySelector('.add_friends').addEventListener('click', function (e) {
    // 阻止冒泡
    e.stopPropagation();

    friendsContainer.style.display = 'block';
    // 好友id
    let friend_id = -1;

    document.querySelector(".cancel_friend").onclick = function () {
        friendsContainer.style.display = 'none';
        search_key.value = "";
        document.querySelector('.search_result').innerHTML = '';
    }

    // 添加好友
    document.querySelector(".add_friend").onclick = function () {
        // 校验
        if (!search_key.value || friend_id == -1) {
            return Alert("请选择好友");
        }

        // if(uid == friend_id){
        //     return Alert("不能添加自己为好友");
        // }

        let data = {
            uid,
            friend_id
        }
        // 发送数据给后台
        ajaxPost("/friends/add", data).then(res => {
            console.log(res);
            const { code } = res;
            if (code == 1) {
                Alert("添加成功");
            } else {
                Alert("好友已存在");
            }
            search_key.value = "";
            // 获取好友列表
            getFriends(uid, 1);

        }).catch(err => {
            console.log(err);
        })
    }

    // 获取搜索结果元素
    let ul = document.querySelector('.search_result');

    // 搜索好友
    document.querySelector('.search_btn').onclick = function () {
        let data = {
            key: search_key.value
        }

        ajaxPost("/search", data).then(res => {
            console.log(res);
            const { data, code } = res;
            if (data.length == 0) {
                return Alert("查无结果");
            }
            const users = data.map((item, index) => `<li data-id="${item.id}">${item.username}</li>`)

            ul.innerHTML = "";
            for (const u of users) {
                ul.innerHTML += u;
            }
        }).catch(err => {
            console.log(err);
        })
    }

    ul.onclick = function (e) {
        if (e.target.nodeName.toLowerCase() == 'li') {
            console.log(e.target);
            // 好友id
            friend_id = e.target.dataset.id;
            // 搜索内容
            search_key.value = e.target.innerText;
            console.log(friend_id, search_key.value);
        }
    }

});

// 创群
document.querySelector(".add_group").addEventListener('click', function () {
    let groupContainer = document.querySelector(".group_container");
    groupContainer.style.display = "block";

    let group_name = document.querySelector(".group_name");

    const members = new Set();

    getFriends(uid, 0, members);

    // 确认创群
    document.querySelector(".create_group").onclick = function () {
        let data = {
            group_name: group_name.value,
            oid: uid,
            members_id: Array.from(members)
        }
        if (!group_name.value.trim()) {
            return Alert("请输入群名");
        }

        ajaxPost("/group/add", data).then(res => {
            console.log(res);
            const { code } = res;
            if (code == -1) {
                return Alert("建群失败");
            }
            Alert("建群成功");
            group_name.value = "";
            getGroupList(uid);
        }).catch(err => {
            console.log(err);
        })
    }

    // 取消
    document.querySelector(".cancel_group").onclick = function () {
        groupContainer.style.display = "none";
        group_name.value = "";
        document.querySelector(".group_member").innerHTML = "";
    }

})

// 截屏
document.getElementById('screen').onclick = function () {
    // Alert('请确定你要截取的区域,可用鼠标选择');

    html2canvas(document.body).then(canvas => {
        console.log(canvas);
        //转换为base64图片数据
        let img = document.createElement('img');
        img.setAttribute("crossOrigin", 'Anonymous');
        img.style.maxWidth = '500px';
        img.style.maxHeight = '350px';
        // img.setAttribute("height", '90vw');
        // img.setAttribute("width", '90vh');

        img.setAttribute("verticalAlign", 'bottom');

        const imgUrl = canvas.toDataURL("image/png");

        img.src = imgUrl;

        content.appendChild(img);

    })
}

// 获取表情包容器
let faceContainer = document.querySelector('.faceNav');

// 获取触发表情包的元素
document.getElementById('face_emoji').addEventListener('click', function (e) {
    // 阻止冒泡
    e.stopPropagation();

    faceContainer.style.display = 'block';

    document.body.addEventListener("click", function () {
        faceContainer.style.display = 'none';
    })

    document.body.onclick = null;
});

// 显示表情包
faceContainer.onclick = function (e) {
    const event = e || window.event;
    let target = event.target || event.srcElement;

    // 点击的是表情
    if (target.classList.contains('qqface') || Array.from(target.classList).includes('qqface')) {
        const id = target.dataset.id;

        // 插入到聊天框中
        content.innerHTML += `
            <img style="width:20px;height:20px;" src='https://res.wx.qq.com/mpres/zh_CN/htmledition/pages/modules/common/emoji/images/smiley/smiley_${id}4273fb.png' data-id="${id}"/>
        `
    }

}

// 聊天对象
let chatObj = document.getElementById('chat_obj');

// 选择好友
document.getElementById("online_users").onclick = function (e) {

    if (e.target.nodeName.toLowerCase() == 'strong') {
        if (confirm("确认删除好友")) {
            // 删除好友
            deleteFriend(e.target.parentNode.dataset.fid);
        }
        return;
    }

    if (e.target.nodeName.toLowerCase() == 'li' || e.target.parentNode.nodeName.toLowerCase() == 'li') {
        let fid;
        let name = ''; // 好友名
        chatType = 0; // 私聊
        if ("fid" in e.target.dataset) {
            fid = e.target.dataset.fid;
            name = e.target.children[0].innerText;
        } else {
            fid = e.target.parentNode.dataset.fid;
            if (e.target.nodeName.toLowerCase() == 'span') {
                name = e.target.innerText;
            } else {
                console.log(e.target.previousElementSibling);
                name = e.target.parentNode.children[0].innerText;
            }
        }
        chatID = fid;
        chatObj.innerText = name;
        getHistory(uid, fid, chatType);
        document.getElementById("member_more").style.display = 'none';
        console.log("用户id", fid);
    }
}

// 选择群组
document.getElementById("group_list").onclick = function (e) {
    if (e.target.nodeName.toLowerCase() == 'li' || e.target.parentNode.nodeName.toLowerCase() == 'li') {
        chatType = 1; // 群聊
        let gid;
        let name = ''; //群名
        if ("gid" in e.target.dataset) {
            gid = e.target.dataset.gid;
            name = e.target.children[0].innerText;
        } else {
            gid = e.target.parentNode.dataset.gid;
            name = e.target.innerText;
        }
        chatID = gid;
        chatObj.innerText = name;
        getHistory(uid, gid, chatType);
        getGroupMember(gid);
        console.log("聊天群id", gid);
    }
}

// 初始化表情包
showFace();
function showFace() {
    let qqfaceArr = [['[微笑]', '0'], ['[撇嘴]', '1'], ['[色]', '2'], ['[发呆]', '3'], ['[得意]', '4'], ['[流泪]', '5'], ['[害羞]', '6'], ['[闭嘴]', '7'], ['[睡]', '8'], ['[大哭]', '9'], ['[尴尬]', '10'], ['[发怒]', '11'], ['[调皮]', '12'], ['[呲牙]', '13'], ['[惊讶]', '14'], ['[难过]', '15'],
    ['[酷]', '16'], ['[囧]', '17'], ['[抓狂]', '18'], ['[吐]', '19'], ['[偷笑]', '20'], ['[愉快]', '21'], ['[白眼]', '22'], ['[傲慢]', '23'], ['[饥饿]', '24'], ['[困]', '25'], ['[惊恐]', '26'], ['[流汗]', '27'], ['[憨笑]', '28'], ['[悠闲]', '29'], ['[奋斗]', '30'],
    ['[咒骂]', '31'], ['[疑问]', '32'], ['[嘘]', '33'], ['[晕]', '34'], ['[疯了]', '35'], ['[衰]', '36'], ['[骷髅]', '37'], ['[敲打]', '38'], ['[再见]', '39'], ['[擦汗]', '40'], ['[抠鼻]', '41'], ['[鼓掌]', '42'], ['[糗大了]', '43'], ['[坏笑]', '44'], ['[左哼哼]', '45'],
    ['[右哼哼]', '46'], ['[哈欠]', '47'], ['[鄙视]', '48'], ['[委屈]', '49'], ['[快哭了]', '50'], ['[阴险]', '51'], ['[亲亲]', '52'], ['[吓]', '53'], ['[可怜]', '54'], ['[菜刀]', '55'], ['[西瓜]', '56'], ['[啤酒]', '57'], ['[篮球]', '58'], ['[乒乓]', '59'], ['[咖啡]', '60'],
    ['[饭]', '61'], ['[猪头]', '62'], ['[玫瑰]', '63'], ['[凋谢]', '64'], ['[嘴唇]', '65'], ['[爱心]', '66'], ['[心碎]', '67'], ['[蛋糕]', '68'], ['[闪电]', '69'], ['[炸弹]', '70'], ['[刀]', '71'], ['[足球]', '72'], ['[瓢虫]', '73'], ['[便便]', '74'], ['[月亮]', '75'],
    ['[太阳]', '76'], ['[礼物]', '77'], ['[拥抱]', '78'], ['[强]', '79'], ['[弱]', '80'], ['[握手]', '81'], ['[胜利]', '82'], ['[抱拳]', '83'], ['[勾引]', '84'], ['[拳头]', '85'], ['[差劲]', '86'], ['[爱你]', '87'], ['[NO]', '88'], ['[OK]', '89'], ['[爱情]', '90'],
    ['[飞吻]', '91'], ['[跳跳]', '92'], ['[发抖]', '93'], ['[怄火]', '94'], ['[转圈]', '95'], ['[磕头]', '96'], ['[回头]', '97'], ['[跳绳]', '98'], ['[投降]', '99'], ['[激动]', '100'], ['[乱舞]', '101'], ['[献吻]', '102'], ['[左太极]', '103'], ['[右太极]', '104']];
    let str = '';
    qqfaceArr.map((item, index) => {
        str += `<div class="qqface qqface${item[1]}" data-id="${item[1]}" data-name="${item[0]}"></div>`;
    });
    faceContainer.innerHTML = str;
}

// 获取群组
function getGroupList(uid) {
    let data = {
        uid
    }
    ajaxPost("/group", data).then(res => {
        console.log(res);
        const { code, data } = res;
        let ul = document.getElementById("group_list");
        ul.innerHTML = "";
        if (code != 1) {
            return Alert("获取群失败");
        }
        for (const group of data) {
            ul.innerHTML += `
                <li data-gid=${group.group_id}>
                    <span>${group.group_name}</span>
                </li>
            `
        }
    }).catch(err => {
        console.log(err);
    })
}

// 获取好友列表 最后一个参数用在添加好友那
function getFriends(uid, type, members = null) {
    let data = {
        uid
    }
    ajaxPost("/friends", data).then(res => {
        console.log(res);
        const { data, code } = res;
        if (code != 1) {
            return Alert("获取好友失败");
        }
        if (type == 1) {
            let ul = document.getElementById("online_users");
            ul.innerHTML = "";
            for (const msg of data) {
                ul.innerHTML += `
                    <li data-fid=${msg.id}>
                        <span>${msg.username}</span>
                        <i>(离线)</i>
                        <strong>×</strong>
                    </li>
                `;
            }
            let li = ul.getElementsByTagName('li');
            for (let el of li) {
                const { fid } = el.dataset;
                if (onlineUsers.has("" + fid)) {
                    el.children[1].innerText = '(在线)';
                } else {
                    el.children[1].innerText = "(离线)";
                }
            }
        } else {
            let ul = document.querySelector(".group_member");
            ul.innerHTML = "";
            for (const msg of data) {
                ul.innerHTML += `
                    <li data-id=${msg.id}>
                        <input class="group_members" type="checkbox"/>
                        <span>${msg.username}</span>
                    </li>
                `
            }
            // 绑定选择好友事件
            let input = document.getElementsByClassName("group_members");
            for (let i = 0; i < input.length; i++) {
                input[i].onchange = function () {
                    let { id } = this.parentNode.dataset;
                    id = parseInt(id);
                    if (members.has(id)) {
                        members.delete(id);
                    } else {
                        members.add(id);
                    }
                    console.log(Array.from(members));
                }
            }
        }

    }).catch(err => {
        console.log(err);
    })
}

// 删除好友
function deleteFriend(fid) {
    let data = {
        uid,
        friend_id: fid
    }
    ajaxPost("/friends/delete", data).then(res => {
        console.log(res);
        const { code } = res;
        if (code == 1) {
            Alert("删除成功");
            getFriends(uid, 1);
        } else {
            Alert("删除失败");
        }
    }).catch(err => {
        console.log(err);
    })
}

// 获取群成员
function getGroupMember(group_id) {
    document.getElementById("member_more").style.display = 'block';
    let data = {
        group_id
    }
    ajaxPost("/member/list", data).then(res => {
        console.log(res);
        const { code, data } = res;
        let ul = document.getElementById("group_member");
        ul.innerHTML = "";
        if (code == 1) {
            for (const member of data) {
                ul.innerHTML += `
                <li data-mid=${member.member_id}>${member.member_name}</li>
            `;
            }
        } else {
            ul.innerHTML = `<li>无成员</li>`;
        }
    }).catch(err => {
        console.log(err);
    })
}

// 获取聊天记录
function getHistory(uid, accept_id, target) {
    let chatMsg = document.querySelector('.chat_msg');
    chatMsg.innerHTML = '';
    let data = {
        uid,
        accept_id,
        target
    }
    ajaxPost("/msg/history", data).then(res => {
        console.log(res);
        const { code, data } = res;
        if (code == 1) {
            // 处理数据
            let record = '';
            for (const msg of data) {
                const { content, accept_id, send_id, send_name, accept_name } = msg;
                // 自己发送的信息
                if (uid == send_id) {
                    record += `
                        <div class="my_msg">
                            <div class="my_say">
                                ${content}
                            </div>
                            <img class="my_head" src="../img/head.jpg" alt="">
                        </div>
                    `;
                }
                // 别人发送的信息
                else {
                    record += `
                        <div class="other_msg">
                            <img class="other_head" src="../img/head.jpg" alt="">
                            <div class="other_say_row">
                                <i class="other_name">${send_name}</i>
                                <div class="other_say">${content}</div>
                            </div>
                        </div>
                    `
                }
            }
            chatMsg.innerHTML = record;
            // 滑动到可视区
            chatMsg.lastElementChild.scrollIntoView(false);
        } else {
            Alert("获取聊天记录失败");
        }
    }).catch(err => {
        console.log(err);
    })
}

// 处理图片
function handleImg(opts) {
    return new Promise((resolve, reject) => {
        console.log(opts);
        const { src, rect } = opts
        if (!src || !rect) {
            reject(new Error('opts params Error!'))
        }
        const img = new Image();
        img.setAttribute("crossOrigin", 'Anonymous');
        img.src = src;
        img.onload = function () {
            const canvas = document.createElement('canvas')
            const ctx = canvas.getContext('2d')
            const { x, y, width, height } = rect
            canvas.width = width
            canvas.height = height
            ctx.drawImage(this, x, y, width, height, 0, 0, width, height)
            const url = canvas.toDataURL('image/png');
            resolve(url);
        };
        img.onerror = function (err) {
            reject(err)
        }
    })
}

// 发送信息
function sendThing() {
    if (chatID == -1) {
        return Alert("请选择聊天对象");
    }

    let text = content.innerHTML.trim();

    if (text.length >= 65535) {
        text = '[图片]';
    }

    if (!text) {
        // 清空内容
        setTimeout(() => {
            content.innerHTML = '';
        }, 0)
        return Alert('内容不能为空');
    }

    // 发送信息
    socket.send(JSON.stringify({
        send_id: uid,
        accept_id: chatID,
        content: text,
        target: chatType,
    }));

    // 清空内容
    setTimeout(() => {
        content.innerHTML = '';
    }, 0)
}

// 获取所有用户
function getAllUsers() {
    ajaxGet('/list').then(res => {
        const { data } = res;

        console.log(res);
        let li = '';
        for (const item of data) {
            li += `
                <li>
                    <img src="../img/head.jpg" alt="">
                    <span>${item.username}</span>
                </li>
            `
        }
        document.getElementById('allUsers').innerHTML = li;

    }).catch(err => {
        console.log(err);
    })
}