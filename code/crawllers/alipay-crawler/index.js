const chalk = require('chalk')
const path = require('path');
const puppeteer = require('puppeteer');
// const captcha = require("./captcha.js")
const {
    js1,
    // js2,
    js3,
    js4,
    js5
} = require('./exec')

const HISTORY_URI = 'https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm'
const LOGIN_URI = 'https://passport.jd.com/uc/login'

const setUp = async (page) => {
    await page.evaluate(js1)
    await page.waitFor(1000 + Math.floor(Math.random() * 1000));
    await page.evaluate(js3)

    await page.waitFor(1000 + Math.floor(Math.random() * 1000));

    await page.evaluate(js4)
    await page.waitFor(1000 + Math.floor(Math.random() * 1000));

    await page.evaluate(js5)
    await page.waitFor(1000 + Math.floor(Math.random() * 1000));
}

const login = async (page, username, password) => {
    await setUp(page)

    await page.waitFor(1000 +  Math.floor(Math.random() * 500));
    const opts = {
        delay: 2 + Math.floor(Math.random() * 2), //每个字母之间输入的间隔
    }
    // const dragBtn = await page.$('.JDJRV-slide-btn')
    // const dragBtnPosition = await page.evaluate(element => {
    //     const {x, y, width, height} = element.getBoundingClientRect()
    //     return {x, y, width, height}
    // }, dragBtn)
    await page.tap('li[data-status="show_login"]');
    const password_login = await page.$('li[data-status="show_login"')
    await password_login.click({
        delay:20,
    })
    await page.waitFor(10000)

    await page.tap('#loginname');
    await page.type('#loginname', username, opts);

    await page.waitFor(2000)

    await page.tap('#nloginpwd');
    await page.type('#nloginpwd', password, opts);

    await page.waitFor(500 + Math.floor(Math.random() * 2000));
    console.log("to click")
    await page.$eval('.login-btn > a', el => el.click())
    await page.waitFor(1000)
    console.log("button_clicked")

    await page.waitForSelector(".JDJRV-bigimg > img")
    let img;
    while (img = await page.$(".JDJRV-bigimg > img")) {
        console.log("try slide")
        // 获取缺口左x坐标
        const distance = await captcha.getVerifyPosition(
            await page.evaluate(element => element.getAttribute('src'), img),
            await page.evaluate(element => parseInt(window.getComputedStyle(element).width), img)
        )
// 滑块
        const dragBtn = await page.$('.JDJRV-slide-btn')
        const dragBtnPosition = await page.evaluate(element => {
            const {x, y, width, height} = element.getBoundingClientRect()
            return {x, y, width, height}
        }, dragBtn)
// 按下位置设置在滑块中心
        const x = dragBtnPosition.x + dragBtnPosition.width / 2
        const y = dragBtnPosition.y + dragBtnPosition.height / 2
        console.log(distance)
        await page.mouse.move(x,y)
        await page.mouse.down()
        /* 1 */
        await page.mouse.move(x + distance /2,y, {step: 20})
        await page.waitFor(500)
        /* 1 */
        await page.mouse.move(x + distance + 10 ,y, {step: 18})
        await page.waitFor(500)
        /* 1 */
        await page.mouse.move(x + distance/2,y, {step: 15})
        await page.waitFor(500)
        /* 1 */
        await page.mouse.move(x + distance/3 * 2,y, {step:10})
        await page.waitFor(500)
        /* 1 */
        await page.mouse.move(x + distance ,y, {step: 10})
        await page.waitFor(500)

        await page.mouse.up()
        // if (distance > 10) {
        //     // 如果距离够长，则将距离设置为二段（模拟人工操作）
        //     const distance1 = distance - 10
        //     const distance2 = 10
        //     await page.mouse.move(x, y)
        //     await page.mouse.down()
        //     // 第一次滑动
        //     await page.mouse.move(x + distance1, y, {steps: 20})
        //     await page.waitFor(300)
        //     // 第二次滑动
        //     await page.mouse.move(x + distance1 + distance2, y, {steps: 20})
        //     await page.waitFor(300)
        //     await page.mouse.up()
        // } else {
        //     // 否则直接滑到相应位置
        //     await page.mouse.move(x, y)
        //     await page.mouse.down()
        //     await page.mouse.move(x + distance, y, {steps: 10})
        //     await page.mouse.up()
        // }
// 等待验证结果
        await page.waitFor(3000)
    }



    await page.screenshot({
        'path': path.join(__dirname, 'screenshots', 'done.png')
    })

    const cookies_list = await page.cookies()

    return cookies_list
}

const username = "15258596997"
const password = "zxy13,./0904"
const saved_cookies = null
/*test*/
const startServer = async () => {
    try {
        const width = 1376;
        const height = 1376;
        const browser = await puppeteer.launch({
            headless: true,
            args: [
                `--window-size=${ width },${ height }`,
                // '--no-sandbox'
            ],
            // executablePath: pathToExtension
        });
        await browser.userAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299');

        const page = await browser.newPage();
        await page.setViewport({
            width: 1376,
            height: 1376
        });

        if(saved_cookies != null) {
            let cookie_list = JSON.parse(saved_cookies)
            for (const cookie of cookie_list){
                console.log(cookie)
                await page.setCookie(cookie)
            }
        }else {
            console.log(chalk.green('开始登陆'));
            await page.goto(LOGIN_URI, {
                waitUntil: 'networkidle2'
            });
            const cookies = await login(page, username, password)
            console.log(JSON.stringify(cookies))
            console.log(chalk.green('登陆成功'))
        }
        /*
        await page.goto(HISTORY_URI,{
            waitUntil: 'networkidle2',
        })
        if(await page.$("#fm-login-id") != null){
            const cookies = await login(page, username, password)
            console.log(JSON.stringify(cookies))
            console.log(chalk.green('重新登陆成功'))
            await page.goto(HISTORY_URI,{
                waitUntil: 'networkidle2',
            })
        }

        console.log(chalk.green('正在进入已买到的宝贝页面'))
        const orders = await traverseHistory(page)

        // browser.close()
        */
    } catch (error) {
        console.error('error', error)
    }

}

startServer()