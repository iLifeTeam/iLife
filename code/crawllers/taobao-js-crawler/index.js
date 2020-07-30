const chalk = require('chalk')
const path = require('path');
const config = require('config');
const puppeteer = require('puppeteer');

const {
  js1,
  // js2,
  js3,
  js4,
  js5
} = require('./exec')

const HISTORY_URI = 'https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm'
const LOGIN_URI = 'https://login.taobao.com/member/login.jhtml'

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
const getSms = async (page,phone) => {
  await setUp(page)
  // await page.click('#J_Quick2Static');
  await page.click('div.login-switch-tab a.sms-login-tab-item')
  await page.waitFor(800 +  Math.floor(Math.random() * 500));
  const opts = {
    delay: 2 + Math.floor(Math.random() * 2), //每个字母之间输入的间隔
  }
  await page.tap('input[name=fm-sms-login-id]');
  await page.type('input[name=fm-sms-login-id]', phone, opts);

  await page.click('div.send-btn a.send-btn-link')
  return
}
const login = async (page, phone,code) => {
  await page.waitFor(5000 +  Math.floor(Math.random() * 1000));
  const opts = {
    delay: 2 + Math.floor(Math.random() * 2), //每个字母之间输入的间隔
  }

  await page.tap('input[name=fm-smscode]');
  await page.type('input[name=fm-smscode]', code, opts);

  await page.screenshot({
    'path': path.join(__dirname, 'screenshots', 'login.png')
  })
  
  // const slider = await page.$eval('#nocaptcha-password', node => node.style);
  // if (slider && Object.keys(slider).length) {
  //   await page.screenshot({
  //     'path': path.join(__dirname, 'screenshots', 'login-slide.png')
  //   })
  //   await mouseSlide(page)
  // }

  await page.waitFor(1000 + Math.floor(Math.random() * 2000));
  // await page.tap('##nc_1_n1z');
  let loginBtn = await page.$('.fm-button')
  await loginBtn.click({
    delay: 20
  })
  try{
    await page.waitForSelector("#J_SiteNavMytaobao .site-nav-menu-bd-panel a:first-child",{
      timeout:5000
    })
  }catch(e){
    await page.screenshot({
      'path': path.join(__dirname, 'screenshots', 'timeout.png')
    })
    console.log(e)
  }
  // try {
  //   const error = await page.$eval('.error', node => node.textContent)
  //   if (error) {
  //     console.log('确保账户安全重新入输入');
  //     process.exit(1)
  //   }
  // }catch (err) {
  //   console.log(err)
  // }

  

  const cookies_list = await page.cookies()

  return cookies_list

}
const parseItem = async (itemHandle) => {
  try {
    const product = await itemHandle.$eval('td div div:nth-child(2) p a span:nth-child(2)', node => node.innerText)
    // console.log("product", product)
    const price = await itemHandle.$eval('td:nth-child(2) > div > p > span:nth-child(2)', node => node.innerText)
    // console.log("price",price)
    const number = await itemHandle.$eval('td:nth-child(3) div p', node => node.innerText)
    // console.log("number", number)
    const imgHandle = await itemHandle.$('td div div a img')
    const imgUrl = await (await imgHandle.getProperty("src")).jsonValue()
    return {
      product: product,
      price: price,
      number: number,
      imgUrl: imgUrl
    }
  }catch (e) {
     console.log("encounter an error item")
  }
}
const parseOrder = async (orderHandle) => {
  // await itemHandle.screenshot({
  //   'path': path.join(__dirname, 'screenshots', 'history_item.png')
  // })
  const order = {}
  order.items = []
  try {
    order.shop = await orderHandle.$eval('table tbody:nth-child(2) tr td:nth-child(2) span a', node => node.innerText)
  }catch (e) {
    console.log("the product has no shop")
    order.shop = ""
  }
  order.date = await orderHandle.$eval('table tbody:nth-child(2) tr td label span:nth-child(2)', node => node.innerText)
  // console.log("date", date)
  order.total = await orderHandle.$eval('table tbody:nth-child(3) tr td:nth-child(5) div div p strong span:nth-child(2)', node => node.innerText)
  // console.log("total", total)
  order.orderID = await orderHandle.$eval("table tbody:nth-child(2) tr td span span:nth-child(3)",node => node.innerText)

  const itemHandles = await orderHandle.$$('table tbody:nth-child(3) tr')
  for(const handle of itemHandles){
    const item = await parseItem(handle)
    if(item != undefined)
    order.items.push(item)
  }
  console.log(order)
  return order
}
const traverseHistory = async (page) => {
  // await page.screenshot({
  //   'path': path.join(__dirname, 'screenshots', 'history.png')
  // })
  let results = []
  let hasNext = true
  // let pageNum = await page.$eval("ul.pagination li:nth-last-child(2)", node => node.getAttribute("title"))
  let pageCount = 0
  do {
    pageCount ++
    console.log("正在处理第" + pageCount + "页")
    await page.waitForSelector("li.pagination-item.pagination-item-"+pageCount+".pagination-item-active")
    await page.waitFor(Math.floor(Math.random() * 500) * Math.floor(Math.random() * 10))
    console.log("确认时第" + pageCount + "页")
    results = results.concat(await traverseHistoryPage(page))
    const nextBtnHandle = await page.$("div[class*=simple-pagination] button:nth-child(2)")
    hasNext = ! await (await nextBtnHandle.getProperty("disabled")).jsonValue()
    console.log("hasNext",hasNext)
    if(hasNext) {
        nextBtnHandle.click({
          delay:20
        })
    }
  } while (hasNext);
  return results
}

const traverseHistoryAfterDate = async (page,date) => {
  let results = []
  let hasNext = true
  // let pageNum = await page.$eval("ul.pagination li:nth-last-child(2)", node => node.getAttribute("title"))
  let pageCount = 0
  do {
    pageCount ++
    console.log("正在处理第" + pageCount + "页")
    await page.waitForSelector("li.pagination-item.pagination-item-"+pageCount+".pagination-item-active")
    await page.waitFor(Math.floor(Math.random() * 500) * Math.floor(Math.random() * 10))
    console.log("确认时第" + pageCount + "页")
    const pageResults = await traverseHistoryPage(page)
    for (const order of pageResults){
      const orderDate = new Date(order.date)
      if (orderDate < date){
        hasNext = false
        break
      }else {
        results.push(order)
      }
    }
    try {
      const nextBtnHandle = await page.$("div[class*=simple-pagination] button:nth-child(2)")
    }catch (e) {
      await page.screenshot({
        'path': path.join(__dirname, 'screenshots', 'netBtnTimeout.png')
      })
    }
    hasNext &= !await (await nextBtnHandle.getProperty("disabled")).jsonValue()
    console.log("hasNext", hasNext)
    if(hasNext) {
      nextBtnHandle.click({
        delay:20
      })
    }
  } while (hasNext);
  return results
}
const traverseHistoryPage = async (page) => {
  const itemHandles = await page.$$(".js-order-container")
  const results = []
  for (const handle of itemHandles){
    results.push(await parseOrder(handle))
  }
  return results
}
const newBrowser = async (headless) => {
  const width = 1376;
  const height = 1376;
  const browser = await puppeteer.launch({
    headless: headless,
    args: [
      `--window-size=${ width },${ height }`,
      '--no-sandbox'
    ],
    // executablePath: pathToExtension
  });
  await browser.userAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299');
  return browser
}
const newPage = async (browser) =>{
  const page = await browser.newPage();
  await page.setViewport({
    width: 1376,
    height: 1376
  });
  return page
}
const gotoLogin = async (page) => {
  await page.goto(LOGIN_URI, {
    waitUntil: 'networkidle2',
    timeout:0
  });
}
const gotoHistory = async (page,cookies) => {
  if(cookies != null){
    for (const cookie of cookies){
      await page.setCookie(cookie)
    }
  }
  await page.goto(HISTORY_URI,{
    waitUntil: 'networkidle2',
    timeout:0
  })
  await page.screenshot({
      'path': path.join(__dirname, 'screenshots', 'history-page.png')
  })
  console.log("判断是否有滑块")
  const slider = await page.$('.slidetounlock');
  if (slider != null ) {
    await mouseSlide(page)
  }

}

/* test */
const startServer = async () => {
  try {
    const pathToExtension = path.join(__dirname, 'chrome-mac/Chromium.app/Contents/MacOS/Chromium');
    const width = 1376;
    const height = 1376;
    const browser = await puppeteer.launch({
      headless: false,
      args: [
        `--window-size=${ width },${ height }`,
        // '--no-sandbox'
      ],
      // executablePath: pathToExtension
    });
    await browser.userAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299');
    const {
      username,
      password
    } = config.taobao;
    console.log(config.cookies)
    const page = await browser.newPage();
    await page.setViewport({
      width: 1376,
      height: 1376
    });
    const saved_cookies = config.cookies

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
  } catch (error) {
    console.error('error', error)
  }

}

const mouseSlide = async (page) => {
  let bl = false
  while (!bl) {
    try {
      await page.hover('#nc_1_n1z')
      await page.mouse.down()
      await page.mouse.move(2000, 0, {
        'delay': 1000 + Math.floor(Math.random() * 1000)
      })
      await page.mouse.up()

      let slider_again = await page.$eval('.nc-lang-cnt', node => node.textContent)
      console.log('slider_again', slider_again)
      if (slider_again != '验证通过') {
        bl = false;
        await page.waitFor(1000 + Math.floor(Math.random() * 1000));
        break;
      }
      await page.screenshot({
        'path': path.join(__dirname, 'screenshots', 'result.png')
      })
      console.log('验证通过')
      return 1
    } catch (e) {
      console.log('error :slide login False', e)
      bl = false;
      break;
    }
  }
}

const loginWithPassword = async (page, phone, password) => {
  await setUp(page)
  // await page.click('#J_Quick2Static');
  await page.click('div.login-switch-tab a.sms-login-tab-item')
  await page.waitFor(1000 +  Math.floor(Math.random() * 2000));
  const opts = {
    delay: 2 + Math.floor(Math.random() * 2), //每个字母之间输入的间隔
  }
  await page.tap('input[name=fm-sms-login-id]');
  await page.type('#fm-login-id', username, opts);

  await page.waitFor(3000);

  await page.tap('#fm-login-password');
  await page.type('#fm-login-password', password, opts);

  await page.screenshot({
    'path': path.join(__dirname, 'screenshots', 'login.png')
  })

  const slider = await page.$eval('#nocaptcha-password', node => node.style);
  if (slider && Object.keys(slider).length) {
    await page.screenshot({
      'path': path.join(__dirname, 'screenshots', 'login-slide.png')
    })
    await mouseSlide(page)
  }

  await page.waitFor(1000 + Math.floor(Math.random() * 2000));
  // await page.tap('##nc_1_n1z');
  let loginBtn = await page.$('.fm-button')
  await loginBtn.click({
    delay: 20
  })
  try{
    await page.waitForSelector("#J_SiteNavMytaobao .site-nav-menu-bd-panel a:first-child",{
      timeout:5000
    })
  }catch(e){
    await page.screenshot({
      'path': path.join(__dirname, 'screenshots', 'timeout.png')
    })
    console.log(e)
  }
  // try {
  //   const error = await page.$eval('.error', node => node.textContent)
  //   if (error) {
  //     console.log('确保账户安全重新入输入');
  //     process.exit(1)
  //   }
  // }catch (err) {
  //   console.log(err)
  // }



  const cookies_list = await page.cookies()

  return cookies_list

}
// startServer()

module.exports = {
  traverseHistory: traverseHistory,
  traverseHistoryAfterDate: traverseHistoryAfterDate,
  login: login,
  getSms:getSms,
  gotoLogin: gotoLogin,
  gotoHistory: gotoHistory,
  newPage:newPage,
  newBrowser:newBrowser
}