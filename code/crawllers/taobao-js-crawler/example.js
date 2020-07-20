const puppeteer = require('puppeteer');
// const chalk = require('chalk');

(async () => {
  const browser = await puppeteer.launch({
    // headless:false,
    // devtools:true
  });
  const page = await browser.newPage();
  await page.goto('https://taobao.com');
  await page.screenshot({path: 'taobao.png'});

  await browser.close();
})();