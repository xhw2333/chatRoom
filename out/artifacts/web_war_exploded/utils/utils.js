// 封装一些公共方法


let Timer = null;
//alert弹框
function Alert(value) {
    // jq
    // let tip = $('.alert');
    // tip.text(value).fadeIn();
    // timer = setTimeout(function () {
    //     tip.fadeOut(2000);
    //     clearTimeout(timer);
    //     timer = null;
    // }, 1500);
    if (Timer === null) {
        Timer = setTimeout(() => {
            // 原生js
            let tip = document.querySelector('.alert');
            tip.innerText = value;

            // 渐显
            fadein(tip, 0.3, 'block');

            // 渐隐
            fadeout(tip, 0.1, 'none', 1700);

            let timer = setTimeout(() => {
                clearTimeout(Timer);
                clearTimeout(timer);
                Timer = null
            }, 3000);
        });
    }

    //     // 原生js
    //     let tip = document.querySelector('.alert');
    //     tip.innerText = value;

    //     // 渐显
    //     fadein(tip, 0.3, 'block');

    //     // 渐隐
    //     fadeout(tip, 0.1, 'none', 1700);
}


//ajax get方法
function ajaxGet(url) {

    return new Promise((resolve, reject) => {
        //创建对象
        var xhr;
        if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }

        //放松请求 true代表异步
        xhr.open("GET", domain + url, true);
        // xhr.setRequestHeader('token',window.sessionStorage.getItem('token'));

        //响应
        xhr.onreadystatechange = function () {

            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    resolve(JSON.parse(xhr.responseText));
                }
                else {
                    // reject(xhr.responseText);
                    reject("请求失败");
                }
            }
        }

        xhr.send();
    })
}

//ajax post方法
function ajaxPost(url, data) {
    // data 为 JSON 字符串
    return new Promise((resolve, reject) => {
        var xhr;
        if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }

        //true代表异步
        xhr.open("POST", domain + url, true);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.setRequestHeader('token',window.sessionStorage.getItem('token'));


        //响应
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    resolve(JSON.parse(xhr.responseText));
                }
                else {
                    // reject(xhr.responseText);
                    reject("请求失败");
                }
            }
        }

        xhr.send(JSON.stringify(data));
    })
}

// fadeOut 渐隐
function fadeout(ele, speed, display, wait = 0) {

    let delay = setTimeout(() => {
        let count = 1;

        let timer = setInterval(() => {
            ele.style.opacity = count;
            count -= speed;
            if (count < 0) {
                clearInterval(timer);
                ele.style.opacity = 0;
                ele.style.display = display;
            }
        }, 100);

        clearTimeout(delay);
    }, wait);
}

// fadeIn 渐显
function fadein(ele, speed, display, wait = 0) {

    let delay = setTimeout(() => {
        let count = 0;

        let timer = setInterval(() => {
            ele.style.display = display;
            ele.style.opacity = count;
            count += speed;
            if (count > 1) {
                ele.style.opacity = 1;
                clearInterval(timer);
            }
        }, 100);
        clearTimeout(delay);
    }, wait);
}

// 设置透明度
function setOpacity(ele, opacity) {
    if (ele.style.opacity != undefined) {
        ///兼容FF和GG和新版本IE
        ele.style.opacity = opacity / 100;
    } else {
        ///兼容老版本ie
        ele.style.filter = "alpha(opacity=" + opacity + ")";
    }
}


function formatDate(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
}



// 防抖
// const debounce = (fn, wait) => {
//     let timer, startTimeStamp = 0;
//     let context, args;

//     let run = (timerInterval) => {
//         timer = setTimeout(() => {
//             let now = (new Date()).getTime();
//             let interval = now - startTimeStamp
//             if (interval < timerInterval) { // the timer start time has been reset, so the interval is less than timerInterval
//                 console.log('debounce reset', timerInterval - interval);
//                 startTimeStamp = now;
//                 run(wait - interval);  // reset timer for left time 
//             } else {
//                 fn.apply(context, args);
//                 clearTimeout(timer);
//                 timer = null;
//             }

//         }, timerInterval);
//     }

//     return function () {
//         context = this;
//         args = arguments;
//         let now = (new Date()).getTime();
//         startTimeStamp = now;

//         if (!timer) {
//             console.log('debounce set', wait);
//             run(wait);    // last timer alreay executed, set a new timer
//         }

//     }
// }

// const Alert = debounce(Alert1, 0);

// 节流
// function throttle(fn, delay) {
//     let valid = true;
//     let context, arg;
//     return function () {
//         context = this;
//         arg = arguments;
//         if (!valid) {
//             //休息时间 暂不接客
//             return false
//         }
//         // 工作时间，执行函数并且在间隔期内把状态位设为无效
//         valid = false
//         setTimeout(() => {
//             fn.apply(context, arg);
//             valid = true;
//         }, delay)
//     }
// }

// const Alert = throttle(Alert1, 1000);