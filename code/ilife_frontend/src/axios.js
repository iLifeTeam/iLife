//axios拦截器
import axios from "axios";

axios.defaults.timeout = 10000;
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
    if (response.status === 200) {
      //console.log('请求成功');
      return response;
    }
    if (response.status === 401 || response.status === 403) {
      console.log("已过期重新登陆", response.data.code);
      window.location.href = "/login";
      return Promise.reject(response);
    } else {
      console.log("请求失败", response.data.code);
      alert(response.data.message);
      return Promise.reject(response);
    }
  },
  (error) => {
    //对响应数据错误做操作
    console.log("请求error", error.message);
    return Promise.reject(error);
  }
);

export default axios;
