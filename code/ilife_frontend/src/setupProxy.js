const proxy = require('http-proxy-middleware');
module.exports = function (app) {
  app.use(
    '/zhihu',
    proxy.createProxyMiddleware({
      target: 'http://47.97.206.169:8090',
      changeOrigin: true,
    })
  );
};