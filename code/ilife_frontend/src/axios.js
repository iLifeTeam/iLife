//axios拦截器
import axios from "axios";
import { createBrowserHistory } from "history";
const history = createBrowserHistory();

//axios.defaults.timeout = 10000;
axios.defaults.withCredentials = true;

/*
axios.interceptors.request.use(config => {
    //发送请求操作，统一再请求里加上userId 
    config.headers['userId'] = window.sessionStorage.getItem("userId");
    return config;
}, error => {
    //发送请求错误操作
    console.log('请求失败')
    return Promise.reject(error);
})
*/
axios.interceptors.response.use(
  (response) => {
    //对响应数据做操作
    switch (response.status) {
      case 200:
        return response;
      case 302:
      case 403:
      case 405:
        console.log("已过期重新登陆", response.data.code);
        document.cookie =
          "username" +
          "=" +
          " " +
          ";path=/;domain=.;expires=" +
          new Date().toGMTString();
        history.push("/login");
        //window.location.reload();
        return Promise.reject(response);
      case 404:
        history.push("/404");
        window.location.reload();
        return Promise.reject(response);
      default:
        console.log("请求失败", response.data.code);
        alert(response.data.message);
        return Promise.reject(response);
    }
  },
  (error) => {
    if (
      error.message === "Request failed with status code 405" ||
      error.message === "Request failed with status code 403"
    ) {
      history.push("/login");
      let exp = new Date();
      exp.setTime(exp.getTime() - 1);
      document.cookie =
        "username" + "=" + " " + ";path=/;expires=" + exp.toGMTString();
      window.location.reload();
    }
    console.log("请求error", error.message);
    return Promise.reject(error);
  }
);

export default axios;
