import Vue from 'vue';
import VueResource from 'vue-resource';
Vue.use(VueResource);
Vue.http.interceptors.push((request,next)=>{
  // request.credentials = true;
  request.url = '/api'+request.url;
  console.log(request.url);
  next((response) => {//在响应之后传给then之前对response进行修改和逻辑判断。对于token时候已过期的判断，就添加在此处，页面中任何一次http请求都会先调用此处方法
    console.log('2222');
    if(response.status===401){
      console.log('11111111111111111');
      console.log('response.body:',response.body);
      window.location.reload();
    }
    return response;


  });


})
