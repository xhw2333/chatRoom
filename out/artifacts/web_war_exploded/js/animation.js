
// 画流星
setInterval(function () {
    // 限制一下，不然可能会很卡
    if (document.querySelector('.star_container').getElementsByClassName('meteor').length <= 10) {
        createMeteor(Math.ceil(Math.random() * 4 + 1));
    }
}, 300);

let i = 0; // 画星星
const starCount = 100; // 星星数量
let timer = setInterval(function () {
    createStar();
    if (i++ > starCount) {
        clearInterval(timer);
    }
}, 10);


// 画星星
function createStar() {
    // 宽高
    const Width = document.body.clientWidth || document.body.scrollWidth;
    const Height = document.body.clientHeight || document.body.scrollHeight;

    // 获取容器
    let container = document.querySelector('.star_container');

    // 创建 li标签 星星
    let star = document.createElement('li');

    // 添加样式
    star.setAttribute('class', 'star');

    // 随机位置
    star.style.left = Math.round(Math.random() * Width) + 'px';
    star.style.top = Math.round(Math.random() * Height) + 'px';
    // 随机大小 2-4
    const starSize = Math.round(Math.random() * 3 + 2);
    star.style.width = starSize + 'px';
    star.style.height = starSize + 'px';

    // 添加子元素
    container.appendChild(star);
}


// 画流星
function createMeteor(speed = 1) {
    // 宽高
    const Width = document.body.clientWidth || document.body.scrollWidth;
    const Height = document.body.clientHeight || document.body.scrollHeight;


    // 获取容器
    let container = document.querySelector('.star_container');

    // 创建 li标签 星星
    let meteor = document.createElement('li');

    // 添加样式
    meteor.setAttribute('class', 'meteor');

    // 随机位置
    meteor.style.left = Math.round(Math.random() * parseInt(3 / 2 * Width)) + 'px';
    meteor.style.top = -100 + 'px';
    // 透明度 0.2-1
    meteor.style.opacity = parseInt(Math.random() * 10 + 2) / 10;

    // 40-80
    meteor.style.borderLeftWidth = Math.ceil(Math.random() * 40 + 40) + 'px';

    // 添加元素
    container.appendChild(meteor);

    let timer1 = setInterval(function () {
        let height = parseInt(meteor.style.top) + speed;
        let left = parseInt(meteor.style.left) - speed;

        meteor.style.top = height + 'px';
        meteor.style.left = left + 'px';
        // console.log(height, left);

        if (height >= Height + meteor.offsetWidth || left < -meteor.offsetWidth) {
            clearInterval(timer1);
            meteor.parentNode.removeChild(meteor);
        }

    }, 10);
}