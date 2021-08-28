// import {Alert} from '../utils/utils';

//处理登录、注册

// 初始化用户、密码输入框状态
document.getElementById('lgUsername').value = window.localStorage.getItem('username') || '';
document.getElementById('lgPassword').value = window.localStorage.getItem('password') || '';
document.getElementById('remember').checked = window.localStorage.getItem('checked') || '';

//去登陆页面
let toLog = document.querySelector('#toLogin');
toLog.addEventListener('click', function () {
    let logPanel = document.querySelector('.lg_content');
    let regPanel = document.querySelector('.reg_content');

    // logPanel.style.display = "flex";
    regPanel.style.display = "none";
    fadein(logPanel, 0.5, 'flex');

    //清空注册字段
    document.getElementById('regUsername').value = '';
    document.getElementById('regPassword').value = '';
    // document.getElementById('regEmail').value = '';
})

//去注册页面
let toReg = document.getElementById('toRegister');
toReg.addEventListener('click', function () {
    let logPanel = document.querySelector('.lg_content');
    let regPanel = document.querySelector('.reg_content');

    logPanel.style.display = "none";
    fadein(regPanel, 0.5, 'flex');
    // regPanel.style.display = "flex";

    //清空登录字段
    document.getElementById('lgUsername').value = '';
    document.getElementById('lgPassword').value = '';
})

//登录
let lgBtn = document.getElementById('lgBtn');
lgBtn.addEventListener('click', function () {

    //获取用户名、密码
    let username = document.getElementById('lgUsername').value.trim();
    let password = document.getElementById('lgPassword').value;
    if (!username) {
        Alert('请输入用户名');
        return;
    }
    if (!password) {
        Alert('请输入密码');
        return;
    }

    //判断有无勾选记住密码，以存储状态
    let checked = document.getElementById('remember').checked;
    
    if (checked) {
        window.localStorage.setItem('checked', checked);
        window.localStorage.setItem('username', username);
        window.localStorage.setItem('password', password);
    } else {
        window.localStorage.removeItem('checked');
        window.localStorage.removeItem('username');
        window.localStorage.removeItem('password');
    }
    window.sessionStorage.setItem('username',username);


    let data = {
        username,
        password
    }
    ajaxPost("/login", data).then(res => {
        console.log(res);
        const { code, data } = res;
        if (code == 1) {
            //登录成功
            Alert('登录成功 ,跳转中...');

            const { id } = data;
            window.sessionStorage.setItem('uid', id);

            // 清空登录字段
            document.getElementById('lgUsername').value = '';
            document.getElementById('lgPassword').value = '';

            //跳转首页
            let timer = setTimeout(function () {
                window.location.href = './pages/index.html?nickname=' + data.username;
                clearTimeout(timer);
            }, 1000);

        } else {
            //登陆失败
            Alert("登录失败");
        }
    }).catch(err => {
        Alert("服务器错误！");
        console.log(err);
    })

});

//注册
let regBtn = document.getElementById('regBtn');
regBtn.addEventListener('click', function () {

    //获取用户名、密码
    let name = document.getElementById('regUsername').value.trim();
    let password = document.getElementById('regPassword').value;
    // let email = document.getElementById('regEmail').value;
    if (!name) {
        Alert('请输入用户名');
        return;
    }
    if (!password) {
        Alert('请输入密码');
        return;
    }
    if (!email || !checkEmail(email)) {
        Alert('邮箱格式无效');
        return;
    }

    //处理数据
    let data = {
        username:name,
        password,
        // email,
        // gender: -1,
        // avatar: "http://localhost:3000/img/head.jpg",
    };

    //发送请求
    ajaxPost("/register", data).then(res => {
        console.log(res);
        const { code,data } = res;
        if (code == 1) {
            //注册成功
            Alert("注册成功");
            let timer = setTimeout(function () {
                //跳去登陆页面
                let logPanel = document.querySelector('.lg_content');
                let regPanel = document.querySelector('.reg_content');

                logPanel.style.display = "flex";
                regPanel.style.display = "none";

                //清空注册字段
                document.getElementById('regUsername').value = '';
                document.getElementById('regPassword').value = '';
                document.getElementById('regEmail').value = '';

                clearTimeout(timer);
            }, 1000);

        } else {
            //注册失败
            Alert("用户已存在")
        }
    }).catch(err => {
        Alert("服务器错误！");
        console.log(err);
    });
});

//校验邮箱
function checkEmail(email) {
    let exp = /^[A-Za-zd0-9]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
    if (!exp.test(email)) {
        return false;
    }
    return true;
}

