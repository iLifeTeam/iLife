const chalk = require("chalk");

const express = require('express');
const crawler = require('./index.js')
const fs = require("fs")

const app = express();

const HEADLESS = false ;
app.get('/login', function (req, res) {
    console.log("login with: ", req.query)
    const {username, password} = req.query
    const filename = "./cookies/" + username + ".cookie"
    fs.readFile(filename,async(err, data) => {
            if (err) {
                console.log(err)
                const cookies = await login(username,password)
                fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
                res.send("success")
            } else {
                const cookies = JSON.parse(data.toLocaleString())
                const browser = await crawler.newBrowser(HEADLESS)
                const page = await crawler.newPage(browser)
                await crawler.gotoHistory(page,cookies)
                browser.close()
                if(await page.$("#fm-login-id") != null){
                    const cookies = await login(username, password)
                    fs.writeFile(filename,JSON.stringify(cookies),err => {if (err) {console.log(err)}})
                }
                res.send("success")
            }
        }
    )
})
const login = async (username, password) => {
    console.log(chalk.green('开始登陆'))
    const browser = await crawler.newBrowser(HEADLESS)
    const page = await crawler.newPage(browser)
    await crawler.gotoLogin(page)
    const cookies =  (await crawler.login(page, username, password))

    await browser.close()
    return cookies
}
app.get('/history/all', async function (req, res) {
    const {username} = req.query
    console.log("history of user " + username)
    const filename = "./cookies/" + username + ".cookie"
    fs.readFile(filename,async(err, data) => {
            if (err) {
                console.log(err)
                res.send("not login")
            } else {
                const cookies = JSON.parse(data.toLocaleString())
                const browser = await crawler.newBrowser(HEADLESS)
                const page = await crawler.newPage(browser)
                await crawler.gotoHistory(page, cookies)
                if(await page.$("#fm-login-id") != null){
                    res.send("login expired")
                    browser.close()
                    return
                }else {
                    const orders = await crawler.traverseHistory(page)
                    browser.close()
                    await res.json(orders)
                }
            }
        }
    )
})
const server = app.listen(8081, function () {

    const host = server.address().address
    const port = server.address().port
    console.log("应用实例，访问地址为 http://%s:%s", host, port)
    const cookieDir = "./cookies"
    if(!fs.existsSync(cookieDir)){
        fs.mkdirSync(cookieDir)
    }

})