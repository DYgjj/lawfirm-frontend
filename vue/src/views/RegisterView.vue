<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #142a48">
    <div style="display: flex; background-color: white; width: 70%; border-radius: 5px; overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/register.jpg" alt="" style="width: 100%; display: block">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center; " >
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 10%">Register</div>
          <el-form-item prop="name">
            <el-input  v-model="user.name" prefix-icon="el-icon-user-solid" size="medium" placeholder="Please input your name"></el-input>
          </el-form-item>
          <el-form-item prop="account">
            <el-input v-model="user.account" prefix-icon="el-icon-user" size="medium" placeholder="Please input your account" ></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input  v-model="user.email" prefix-icon="el-icon-message" size="medium" placeholder="Please input your email"></el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input  v-model="user.phone" prefix-icon="el-icon-phone" size="medium" placeholder="Please input your phone number"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  v-model="user.password" prefix-icon="el-icon-lock" size="medium" show-password placeholder="Please input your password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input  v-model="user.confirmPass" prefix-icon="el-icon-lock" size="medium" show-password placeholder="Please confirm your password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="register()">Register</el-button>
          </el-form-item>
          <div style="flex: 1; text-align: center">Already have an account?  <span style="color: dodgerblue; cursor: pointer; " @click="$router.push('/login')">Login now </span></div>
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

export default {
  name: 'Register',
  data() {
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('Please confirm your password'));
      } else if (confirmPass !== this.user.password){
        callback(new Error('Password inconsistency'))
      } else {
        callback();
      }
    };
    return {
      forgetUserForm: {},
      forgetPassDialogVis: false,
      user: {
        name: '',
        account: '',
        password: '',
        email: '',
        phone: '',
        role: '',
        confirmPass: '',
      },
      rules: {
        name: [
          { required: true, message: 'Please input name', trigger: 'blur' },
        ],
        account: [
          { required: true, message: 'Please input account', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: 'Please input phone', trigger: 'blur' },
        ],
        email: [
          { required: true, message: 'Please input email', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' },
        ],
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
          this.$message.success('The new password is your account + 123, please change you password as soon as possible!')
          this.forgetPassDialogVis = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          request({
            url: "/user/register",
            headers: {
              isToken: false
            },
            method: "POST",
            data: this.user
          }).then(res => {
            if (res.code === '0'){
              this.$message({
                message: 'Register Successfully',
                type: 'success'
              });
              this.$router.push('/login')
            }else {
              this.$message.error(res.msg);
              this.$router.push('/register')
            }
          })
        }
      })
    },

  }
}
</script>
