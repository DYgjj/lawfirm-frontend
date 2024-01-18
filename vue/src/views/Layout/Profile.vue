<template>
  <div>
    <div style="display: flex; overflow: hidden">
      <el-card style="width: 55%; margin-left: auto; margin-right: auto;">
        <el-form :model="form" label-width="95px" style="padding-right: 20px;">
          <el-form-item label="Account" prop="account">
            <el-input v-model="form.account" placeholder= 'Account' disabled></el-input>
          </el-form-item>
          <el-form-item label="Name" prop="name">
            <el-input v-model="form.name" placeholder="Name"></el-input>
          </el-form-item>
          <el-form-item label="Phone" prop="phone">
            <el-input v-model="form.phone" placeholder="Phone"></el-input>
          </el-form-item>
          <el-form-item label="Email" prop="email">
            <el-input v-model="form.email" placeholder="Email"></el-input>
          </el-form-item>
          <el-form-item label="Description" prop="address">
            <el-input type="textarea" v-model="form.description" placeholder="Description"></el-input>
          </el-form-item>
          <div style="text-align: center; margin-bottom: 20px">
            <el-button type="primary" @click="submit">Confirm</el-button>
          </div>
        </el-form>
      </el-card>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Profile",
  data() {
    return {
      form: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {

  },
  methods: {
    submit() {
      request.post("/user",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          localStorage.setItem("user", JSON.stringify(this.form));

          // 触发父级的数据更新
          this.$emit('update:user', this.form)
        }else {
          this.$message.error(res.msg);
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
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
</style>
