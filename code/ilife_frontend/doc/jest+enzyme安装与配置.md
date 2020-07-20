# React测试-jest+enzyme安装与配置

第一回做前端测试，记录一下React开发中测试细节。

## 简介
首先，测试怎么做？用什么测试？有什么区别？

因为之前有junit和loadrunner经验，所以估计前端测试应该类似，React作为一个组件化的框架（阮一峰前辈的[博客](http://www.ruanyifeng.com/blog/2016/02/react-testing-tutorial.html)  中提到JSX语法对此也有影响），需要对组件进行操作，观察组件是否有正确的响应结果。

顺嘴提一句，react[官方文档](https://zh-hans.reactjs.org/docs/testing.html)中提到了两种测试工具/库，jest和React Testing Library（rtl），jest的适用范围和功能上应该比rtl更广一些。

## 安装过程

- **注意版本号！！！！！**
- 报版本号错误可以参考此[教程](https://blog.csdn.net/woyidingshijingcheng/article/details/89950405)

#### npm install

