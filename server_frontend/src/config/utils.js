
const responseText = (val)=>{
  if(Object.prototype.toString.call(val) === '[object Array]'){
    return val;
  } else {
    return [];
  }
};

const formatDate = (time,type='-')=>{
  let data = new Date(time);
  let year = data.getFullYear();
  let mon = data.getMonth()+1;
  let day = data.getDate();
  return [year,mon,day].map((item)=>formatNumber(item)).join(type);
};

const formatTime = (time)=>{
  let data = new Date(time);
  let hh = data.getHours();
  let mm = data.getMinutes();
  let ss = data.getSeconds();
  return [hh,mm,ss].map((item)=>formatNumber(item)).join(':');
};

const formatDateTime = (time,type='-')=>{
  if(!time) return;
  return formatDate(time,type)+' '+formatTime(time);
}

const formatNumber = (number) =>{
  let n = number.toString();
  return n[1]? n : '0'+n;
}

const debounce = (fn,delay)=>{
  let timer;
  return function (...args) {
    if(timer) clearTimeout(timer);
    timer = setTimeout(()=>{
      fn.apply(this,args);
    },delay);
  }

};
export {
  formatDateTime,
  responseText,
  formatDate,
  formatTime,
  debounce,
}