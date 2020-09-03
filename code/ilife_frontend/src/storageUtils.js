/*
 * 进行local数据存储管理的工具模块
 * */
import store from "store";
const USER = "username";
export default {
  //保存
  saveUser(user) {
    // localStorage.setItem(USER,JSON.stringify(user))
    store.set(USER, user);
  },
  //读取
  getUser() {
    // return JSON.parse(localStorage.getItem(USER) || '{}')
    return store.get(USER) || {};
  },
  //删除
  deleteUser() {
    // localStorage.removeItem(USER)
    store.remove(USER);
  },
};
