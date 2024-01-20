<template>
  <div class="page1" style=" display: flex; background-color: antiquewhite">
    <el-container>
      <el-header style="background-color: #545c64; background: rgb(0,0,0);background: rgba(0,0,0,0.5)" >
        <div style="font-family: 'Times New Roman'">
          <img src="@/assets/logo.png" alt="" style="width: 100px; position: relative; top: 5px; right: 5px">
          <span style="font-size: xx-large;position: relative;bottom: 10px; font-weight: bold; color: antiquewhite; margin-left: 10px">Law Firm Management System</span>
          <div style="flex: 1; display: flex; justify-content: flex-end; align-items: center;">
            <el-menu
                :default-active="$route.path" router
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                text-color="antiquewhite"
                active-text-color="black"
                style=" background: rgb(204, 204, 204);background: rgba(204, 204, 204, 0.5);font-weight: bold; position: relative;bottom: 60px; left: 20px"
            >
              <el-menu-item index="/homepage_frontend">HomePage</el-menu-item>
              <el-menu-item index="/team_frontend">Expert Legal Team</el-menu-item>
              <el-menu-item index="/about_frontend">About Us</el-menu-item>
              <el-menu-item index="/calendar_frontend">Calendar</el-menu-item>
              <el-menu-item style="color: #c97f0a" index="/layout">Console</el-menu-item>
            </el-menu>
          </div>
        </div>
      </el-header>

      <container>
        <el-main style="margin: 0;padding: 0;border: 0;box-sizing: border-box;overflow: hidden;">
          <router-view/>
        </el-main>
      </container>

    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      default: 'homepage_frontend',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      gridData: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        name: '',
        lawyer:'',
        caseType: '',
        caseDetail: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    login() {
      this.$router.push("/login");

    },
    logout(){
      localStorage.removeItem("user");
      this.$router.push("/homepage_frontend");

    },
  }
}
</script>

<style>.el-header {
  /*width: 100%;*/
  /*line-height: 10px;*/
}
</style>

<style>.el-main {

}
</style>

<!--<style>.el-footer {-->
<!--  background-color: antiquewhite;-->
<!--  text-align: left;-->
<!--  line-height: 60px;-->
<!--  position: fixed;-->
<!--  left: 0;-->
<!--  bottom: 0;-->
<!--  width: 100%;-->
<!--}-->
<!--</style>-->

<style>
.page1
{
  background-image: url('../assets/BG1.jpg');
  /*opacity: 0.5;*/
  /*height: 1080px;*/
  /*width: 1920px;*/

  height: 100%;
  width: 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;

}
</style>

<style>.el-menu-demo{
  color: #777F8F;
}
</style>

<style>.el-menu-demo.is-active{
  color: #6681FA;
  background-color: #EAEEFF;
}
</style>
