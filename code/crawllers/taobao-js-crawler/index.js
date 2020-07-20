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
const login = async (page, username, password) => {
  await page.goto(LOGIN_URI, {
    waitUntil: 'networkidle2'
  });

  await setUp(page)

  // await page.click('#J_Quick2Static');

  await page.waitFor(Math.floor(Math.random() * 500) * Math.floor(Math.random() * 10));
  const opts = {
    delay: 2 + Math.floor(Math.random() * 2), //每个字母之间输入的间隔
  }
  await page.tap('#fm-login-id');
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

  await page.waitFor("#J_SiteNavMytaobao .site-nav-menu-bd-panel a:first-child")
  try {
    const error = await page.$eval('.error', node => node.textContent)
    if (error) {
      console.log('确保账户安全重新入输入');
      process.exit(1)
    }
  }catch (err) {
    console.log(err)
  }

  const cookies_list = await page.cookies()

  return cookies_list

}

const parseItem = async (itemHandle) => {
  await itemHandle.screenshot({
    'path': path.join(__dirname, 'screenshots', 'history_item.png')
  })
  const order = {}
  const product = await itemHandle.$eval('table tbody:nth-child(3) tr td div div:nth-child(2) p a span:nth-child(2)', node => node.innerText)
  // console.log("product", product)
  const shop = await itemHandle.$eval('table tbody:nth-child(2) tr td:nth-child(2) span a', node => node.innerText)
  // console.log("shop", shop)
  const date = await itemHandle.$eval('table tbody:nth-child(2) tr td label span:nth-child(2)', node => node.innerText)
  // console.log("date", date)
  const price = await itemHandle.$eval('table tbody:nth-child(3) tr td:nth-child(2) div p:nth-child(2) span:nth-child(2)', node => node.innerText)
  // console.log("price",price)
  const number = await  itemHandle.$eval('table tbody:nth-child(3) tr td:nth-child(3) div p', node => node.innerText)
  // console.log("number", number)
  const payment = await itemHandle.$eval('table tbody:nth-child(3) tr td:nth-child(5) div div p strong span:nth-child(2)', node => node.innerText)
  // console.log("payment", payment)
  const imgHandle = await itemHandle.$('table tbody:nth-child(3) tr td div div a img')
  const imgUrl = await(await imgHandle.getProperty("src")).jsonValue()
  // console.log("imgUrl",imgUrl)
  order.product = product
  order.shop = shop
  order.date = date
  order.price = price
  order.number = number
  order.payment = payment
  order.imgUrl = imgUrl
  // console.log(order)
}
const TraverseHistory = async (page) => {
  await page.goto(HISTORY_URI,{
    waitUntil: 'networkidle2',
  })
  await page.screenshot({
    'path': path.join(__dirname, 'screenshots', 'history.png')
  })

  const itemHandles = await page.$$(".js-order-container")
  console.log(itemHandles.length)
  const results = []
  itemHandles.forEach(handle => parseItem(handle).then(orderItem => {
    console.log(orderItem)
    results.push(orderItem)
  }))
  console.log(results)

}
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

    if(saved_cookies == null) {
      console.log(chalk.green('开始登陆'));
      const cookies = await login(page, username, password)
      console.log(JSON.stringify(cookies))
      console.log(chalk.green('登陆成功'))
      // try{
      //   let bought_page = await page.$("#J_SiteNavMytaobao .site-nav-menu-bd-panel a:first-child")
      //   await bought_page.click({
      //     delay: 25
      //   })
      //   console.log("clicked")
      //   await page.waitFor(0)
      // }catch(err){
      //   console.log(chalk.red(err))
      // }
    }else {
      let cookie_list = JSON.parse(saved_cookies)
      for (const cookie of cookie_list){
        console.log(cookie)
        await page.setCookie(cookie)
      }
    }
    console.log(chalk.green('正在进入已买到的宝贝页面'))
    await TraverseHistory(page)


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


startServer()