<template>
  <div>
    <el-card style="width: 40%; margin-left: auto; margin-right: auto;">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="130px" style="padding-right: 20px;">
        <el-form-item label="Old password" prop="oldPassword">
          <el-input show-password v-model="form.oldPassword" placeholder= 'Old password'></el-input>
        </el-form-item>
        <el-form-item label="New password" prop="newPassword">
          <el-input show-password v-model="form.newPassword" placeholder= 'New password'></el-input>
        </el-form-item>
        <el-form-item label="New password" prop="confirmPassword">
          <el-input show-password v-model="form.confirmPassword" placeholder= 'Confirm password'></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="submit">Confirm</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please confirm the password'));
      } else if (value !== this.form.newPassword) {
        callback(new Error('Password inconsistency'))
      } else {
        callback();
      }
    };

    return {
      form: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

      rules: {
        oldPassword: [
          {required: true, message: 'Please input old password', trigger: 'blur'},
        ],
        newPassword: [
          {required: true, message: 'Please input new password', trigger: 'blur'},
        ],
        confirmPassword: [
          {validator: validatePassword, required: true, trigger: 'blur'},
        ],
      },
    }
  },
  created() {
  },
  methods: {
    submit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.password === this.form.oldPassword) {
            request.put("/user/changePassword",this.form).then(res => {
                if (res.code === '0'){
                  this.$message({
                    message: 'Operate Successfully',
                    type: 'success'
                  });
                  localStorage.setItem("user", JSON.stringify(this.form));
                  this.$router.push("/login");
                  localStorage.removeItem("user");

                  // 触发父级的数据更新
                  this.$emit('update:user', this.form)
                }else {
                  this.$message.error(res.msg);
                }
            })
          } else {
            this.$message.error("Old password is unconformity!");
          }
        }
      })
    },
  },
}

</script>


<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}

</style>
