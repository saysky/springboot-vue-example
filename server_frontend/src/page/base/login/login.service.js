import Vue from 'vue';
class loginService{
  constructor(){

  }
  login(url,params){
    return Vue.http.post(url,params);
  }
  logout(){
    return Vue.http.post(url);
  }
}
const loginService = new LoginService();

export {loginService};
