const chalk = require("chalk");

const express = require('express');
const crawler = require('./index.js')
const fs = require("fs")

const browsers = new Map();
const app = express();

const HEADLESS = true ;
// app.get('/login',async function (req, res) {
//     console.log("login with: ", req.query)
//     const {username, password} = req.query
//     const filename = "./cookies/" + username + ".cookie"
//     const browser = await crawler.newBrowser(HEADLESS)
//     fs.readFile(filename,async(err, data) => {
//             if (err) {
//                 console.log(err)
//                 const cookies = await login(browser,username,password)
//                 fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
//
//                 await browser.close()
//                 res.send("success")
//             } else {
//                 const cookies = JSON.parse(data.toLocaleString())
//                 const page = await crawler.newPage(browser)
//                 await crawler.gotoHistory(page,cookies)
//                 if(await page.$("#fm-login-id") != null){
//                     const cookies = await login(browser,username, password)
//                     fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
//                 }
//                 await browser.close()
//                 res.send("success")
//             }
//         }
//     )
// })
app.get('/login/sms/fetch',async function (req, res) {
    console.log("login with: ", req.query)
    const {phone} = req.query
    const filename = "./cookies/" + phone + ".cookie"

    if(browsers.has(phone)){
        const {browser,page} = browsers.get(phone)
        browser.close()
        browsers.delete(phone)
    }
    const browser = await crawler.newBrowser(HEADLESS)
    console.log("new browser\n")
    fs.readFile(filename,async(err, data) => {
            if (err) {
                console.log(err)
                const page = await crawler.newPage(browser)
                await crawler.gotoLogin(page)
                await crawler.getSms(page,phone)
                browsers.set(phone,{
                    browser: browser,
                    page: page
                })
                console.log("sms sent, browser saved\n")
                // fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
                res.send("success")
            } else {
                const cookies = JSON.parse(data.toLocaleString())
                const page = await crawler.newPage(browser)
                await crawler.gotoHistory(page,cookies)
                if(await page.$("#fm-login-id") != null){
                    await crawler.gotoLogin(page)
                    await crawler.getSms(page,phone)
                    browsers.set(phone,{
                        browser: browser,
                        page: page
                    })
                    console.log("login expired, sms sent, browser saved\n")
                    // fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
                    res.send("success")
                }else {
                    browser.close()
                    browsers.delete(phone)
                    console.log("already login")
                    res.send("already login")
                }
            }
        }
    )
})
app.get('/login/sms',async function (req, res) {
    console.log("login with: ", req.query)
    const {phone,code} = req.query
    const filename = "./cookies/" + phone + ".cookie"
    const {browser,page} = browsers.get(phone)
    if (browser == null) {
        console.log(" null browser open, get sms first\n")
        res.send("error, get sms first")
        return
    }
    const cookies = await crawler.login(page,phone,code)

    console.log("login done for ",phone ," browser closed.\n")
    browser.close()
    browsers.delete(phone)
    fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
    console.log("sms send, cookies stored for ",phone ," return.\n")
    res.send("success")
})
// const login = async (browser,username, password) => {
//     console.log(chalk.green('开始登陆'))
//     const page = await crawler.newPage(browser)
//     await crawler.gotoLogin(page)
//     const cookies =  (await crawler.login(page, username, password))
//     return cookies
// }
app.get('/history/all', async function (req, res) {
    const {username} = req.query
    console.log("history of user " + username)
    const filename = "./cookies/" + username + ".cookie"
    const browser = await crawler.newBrowser(HEADLESS)
    fs.readFile(filename,async(err, data) => {
            if (err) {
                console.log(err)
                res.send("not login")
            } else {
                const cookies = JSON.parse(data.toLocaleString())
                const page = await crawler.newPage(browser)
                await crawler.gotoHistory(page, cookies)
                if(await page.$("#fm-login-id") != null){
                    res.send("login expired")
                    browser.close()
                }else {
                    const orders = await crawler.traverseHistory(page)
                    browser.close()
                    await res.json(orders)
                }
            }
        }
    )
})

app.get('/history/after', async function (req, res) {
    const {username,date} = req.query
    console.log(" history after date "+ date +" of user " + username)
    const filename = "./cookies/" + username + ".cookie"
    const browser = await crawler.newBrowser(HEADLESS)
    fs.readFile(filename,async(err, data) => {
            if (err) {
                console.log(err)
                res.send("not login")
            } else {
                const cookies = JSON.parse(data.toLocaleString())
                console.log("cookies load",cookies)
                const page = await crawler.newPage(browser)
                await crawler.gotoHistory(page, cookies)
                if(await page.$("#fm-login-id") != null){
                    res.send("login expired")
                    browser.close()
                    return
                }else {
                    const dateObject = new Date(date)
                    console.log(dateObject)
                    const orders = await crawler.traverseHistoryAfterDate(page,dateObject)
                    browser.close()
                    await res.json(orders)
                }
            }
        }
    )
})
const server = app.listen(8101,"0.0.0.0", function () {

    const host = server.address().address
    const port = server.address().port
    console.log("应用实例，访问地址为 http://%s:%s", host, port)
    const cookieDir = "./cookies"
    if(!fs.existsSync(cookieDir)){
        fs.mkdirSync(cookieDir)
    }

})