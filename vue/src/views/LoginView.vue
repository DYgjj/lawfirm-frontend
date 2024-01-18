<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #142a48">
    <div style="display: flex; background-color: white; width: 70%; border-radius: 5px; overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/login.jpg" alt="" style="width: 100%; display: block">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center; " >
        <el-form :model="user" style="width: 90%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 40px;">Welcome to G12 Law Firm Management System</div>
          <el-form-item prop="account">
            <el-input v-model="user.account" prefix-icon="el-icon-user" size="medium" placeholder="Please input your account" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  v-model="user.password" prefix-icon="el-icon-lock" size="medium" show-password placeholder="Please enter your password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.code"></el-input>
              <div style="flex: 1">
                <valid-code @update:value="getCode" style="height: 36px;"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="login()">Log in</el-button>
          </el-form-item>
          <div style="flex: 1; text-align: center">Don't have account? Please <span style="color: dodgerblue; cursor: pointer; " @click="register()">register</span></div>
          <div style="flex: 1; text-align: center; margin-top: 5px"><span style="color: dodgerblue; cursor: pointer" @click="handleForgetPass">Forgot Password?</span></div>
        </el-form>
      </div>
    </div>

    <el-dialog title="Forget password" :visible.sync="forgetPassDialogVis" width="30%">
      <el-form :model="forgetUserForm" label-width="120px" style="padding-right: 20px">
        <el-form-item label="Account">
          <el-input v-model="forgetUserForm.account" autocomplete="off" placeholder="Please input account"></el-input>
        </el-form-item>
        <el-form-item label="Phone number">
          <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="Please input phone number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="forgetPassDialogVis = false">Cancel</el-button>
        <el-button type="primary" @click="resetPassword">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import request from "@/utils/request";
import validCode from "@/components/ValidCode";

export default {
  name: 'Login',
  components:{
    validCode
  },
  data() {
    //验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input validate code'));
      } else if (value.toLowerCase() !== this.code){
        callback(new Error('Validate code error'))
      } else {
        callback();
      }
    };
    return {
      forgetUserForm: {},  //忘记密码的form
      forgetPassDialogVis: false,
      code: '',
      user: {
        code: '',
        account: '',
        password: '',
        role: '',
      },
      rules: {
        account: [
          { required: true, message: 'Please input account', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' },
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ]
      }
    };

  },
  //页面加载的时候，做一些事情在created里面
  created() {

  },
  //定义一些页面上空间触发事件调用的方法
  methods:{
    handleForgetPass() { //初始化表单
      this.forgetUserForm = {}
      this.forgetPassDialogVis = true
    },
    resetPassword() {
      request({
        url: "/user/password",
        headers: {
          isToken: false
        },
        method: "put",
        data: this.forgetUserForm
      }).then(res => {
        if (res.code === '0') {
          this.$message.success('Reset successfully')
          this.forgetPassDialogVis = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getCode(code) {
      this.code = code.toLowerCase();
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          request({
            url: "/user/login",
            headers: {
              isToken: false
            },
            method: "POST",
            data: this.user
          }).then(res => {
            if (res.code === '0') {
              this.$message({
                message: 'Login successfully',
                type: 'success'
              });
              localStorage.setItem("user", JSON.stringify(res.data));
              this.$router.push("/");
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          })
        }
      })
    },
    register() {
      this.$router.push('/register')
    }
  }
}
</script>
