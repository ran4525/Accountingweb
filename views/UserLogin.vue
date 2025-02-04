<template> 
  <div class="login-container">
    <div class="slider">
      <div :class="active === 1 ? 'form' : 'form hidden'">
        <div class="title">欢迎<b>回来</b></div>
        <div class="subtitle">登录你的账户</div>
        <div class="inputf">
          <input type="text" v-model="username" placeholder="用户名" />
          <span class="label">用户名</span>
        </div>
        <div class="inputf">
          <input type="password" v-model="password" placeholder="密码" />
          <span class="label">密码</span>
        </div>
        <button @click="login" :disabled="!username || !password">登录</button>
      </div>

      <div :class="active === 2 ? 'form' : 'form hidden'">
        <div class="title">开始</div>
        <div class="subtitle">创建你的账户</div>
        <div class="inputf">
          <input type="text" v-model="username" placeholder="用户名" />
          <span class="label">用户名</span>
        </div>
        <div class="inputf">
          <input type="password" v-model="password" placeholder="密码" />
          <span class="label">密码</span>
        </div>
        <button @click="register">注册</button>
      </div>

      <div :class="active === 1 ? 'card' : 'card active'">
        <div class="head">
          <div class="name"><span>记账</span>小帮手</div>
        </div>
        <div class="image-container">
          <img :src="require('@/assets/设计师工作.png')" width="300px" height="240px" />
        </div>
        <div class="btn">
          <button @click="toggleActive">
            {{ active === 1 ? '去注册' : '去登录' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      active: 1,
      username: '',
      password: ''
    };
  },
  methods: {
    // 切换 active 状态
    toggleActive() {
      this.active = this.active === 1 ? 2 : 1;
    },
    // 登录方法
    async login() {
      const jsonString = JSON.stringify({
        username: this.username,
        password: this.password
      });
      try {
        const response = await axios.post('http://localhost:8080/login', jsonString, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log(response.data); // 打印响应内容

        // 根据返回的 code 判断操作
        if (response.data.code === 0) {
          // 登录成功，跳转到 /page1
          this.$router.push({ path: '/page1', query: { username: this.username } });
        } else if (response.data.code === 2) {
          // 密码错误，弹出提示
          alert('密码错误！');
        } else {
          // 其他错误处理
          alert('登录失败！');
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert('网络错误，请稍后再试');
      }
    },
    // 注册方法
    async register() {
      const jsonString = JSON.stringify({
        username: this.username,
        password: this.password
      });
      try {
        const response = await axios.post('http://localhost:8080/regist', jsonString, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log(response.data); // 打印响应内容

        // 根据返回的 code 判断操作
        if (response.data.code === 0) {
          // 注册成功
          alert('注册成功');
        } else if (response.data.code === 1) {
          alert('用户名已存在');
        } else {
          // 其他错误处理
          alert('注册失败！');
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert('网络错误，请稍后再试');
      }
    }
  }
};
</script>
  
  
  <style>
  .login-container {
    width: 100%;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #C0C4CC;
    background-size: cover;
  }

  .image-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
  
  .slider {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .form {
    width: 400px;
    height: 500px;
    background: #606266;
    backdrop-filter: blur(16px) saturate(0);
    border-radius: 10px;
    border: 1px solid rgba(255, 255, 255, 0.15);
    padding: 40px 60px;
    box-shadow: #909399 50px 50px 100px -20px,
                #909399 30px 30px 60px -30px,
                rgba(212, 217, 222, 0.35) 2px -2px 6px 0px inset;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin: 0 0px;
    z-index: 2;
    transition: 0.5s ease-in-out;
  }
  
  .hidden {
    height: 395px;
    box-shadow: none;
    z-index: 1;
  }
  
  .title {
    font-size: 18px;
    color: rgb(246, 240, 255);
    letter-spacing: 1px;
    font-weight: 300;
  }
  
  .subtitle {
    font-size: 11px;
    color: rgb(246, 240, 255);
    letter-spacing: 1px;
    margin-bottom: 35px;
  }
  
  .inputf {
    width: 100%;
    position: relative;
    margin-bottom: 35px;
  }
  
  .inputf input {
    width: 100%;
    height: 35px;
    border: none;
    outline: 1.5px solid rgb(200, 200, 220);
    background: transparent;
    border-radius: 8px;
    font-size: 12px;
    padding: 0 15px;
    color: rgb(246, 240, 255);
  }
  
  .inputf input::placeholder {
    color: rgb(175, 180, 190);
  }
  
  .inputf .label {
    position: absolute;
    top: 0;
    left: 0;
    color: rgb(246, 240, 255);
    font-size: 11px;
    font-weight: bold;
    transition: 0.25s ease-out;
    opacity: 0;
  }
  
  button {
    width: 100%;
    height: 35px;
    background: rgb(36, 217, 127);
    color: #fff;
    border: none;
    outline: none;
    border-radius: 5px;
    font-size: 12px;
    cursor: pointer;
  }
  
  .card {
    position: absolute;
    right: 0;
    top: 290px;
    transform: translate(0, -50%);
    width: 440px;
    height: 400px;
    background:  white;
    background-size: contain;
    padding: 40px;
    border-radius: 0 10px 10px 0;
    transition: 0.5s ease-in-out;
    z-index: 2;
  }
  
  .card.active {
    right: calc(100% - 520px);
    border-radius: 10px 0 0 10px;
  }
  
  .head {
    font-size: 34px;
    margin-bottom: 35px;
  }
  
  .name {
    font-weight: 300;
  }
  
  .name span {
    color: rgb(36, 217, 127);
    font-weight: bold;
  }
  
  .desc {
    font-size: 14px;
    text-align: justify;
    margin-bottom: 35px;
  }
  
  .btn {
    font-size: 14px;
  }
  
  .btn button {
    background: rgb(36, 217, 127);
    font-size: 14px;
    padding: 5px 15px;
    border: none;
    outline: none;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 10px;
    margin-top: 15px;
  }
  </style>
  
