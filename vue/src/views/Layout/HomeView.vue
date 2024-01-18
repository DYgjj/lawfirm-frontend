<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      Hello {{user.name}}, have a good day!
    </div>

    <div style="display: flex">
      <el-card style="width: 100%">
        <div slot="header" class="clearfix">
          <span>Welcome to G12 law firm management system</span>
        </div>
        <div>
          <div style="margin-top: 20px">
            <div style="margin: 10px 0"><strong>主题色</strong></div>
            <el-button type="primary">按</el-button>
            <el-button type="success">按讯</el-button>
            <el-button type="warning">按讯</el-button>
            <el-button type="danger">按钮</el-button>
            <el-button type="info">按</el-button>
          </div>
        </div>
      </el-card>

<!--      <el-card style="width: 50%">-->
<!--        <div slot="header" class="clearfix">-->
<!--          <span>渲染用户数据</span>-->
<!--        </div>-->
<!--        <div>-->
<!--          <el-table :data = "users">-->
<!--            <el-table-column label="ID" prop="id"></el-table-column>-->
<!--            <el-table-column label="用户名" prop="name"></el-table-column>-->
<!--          </el-table>-->
<!--        </div>-->
<!--      </el-card>-->
    </div>
  </div>

</template>

<script>

import request from "@/utils/request";

export default {
  name: 'HomeView',
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  mounted() {
    // request.get('/user').then(res => {
    //   this.users = res.data;
    // });
  }

}
</script>
