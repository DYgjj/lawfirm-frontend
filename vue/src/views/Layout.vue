<template>
  <div>
    <el-container>
      <el-header style="background-color: #4c535a">
        <img src="@/assets/law-firm-logo.png" alt="" style="width: 80px; position: relative; top: 10px">
        <span style="font-size: 18px; margin-left: 10px; color: white">G12 Law Firm</span>
        <el-dropdown style="float: right;height: 60px; line-height: 60px">
          <span class="el-dropdown-link" style="color: white; front-size: 16px">{{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout()">Log out</el-dropdown-item>
            <el-dropdown-item @click.native="profile()">Profile</el-dropdown-item>
            <el-dropdown-item @click.native="password()">Change password</el-dropdown-item>
            <el-dropdown-item @click.native="home()">Main Page</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
    </el-container>

    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color: #545c64; width: 220px">
        <el-menu :default-active="$route.path" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/home">
            <i class="el-icon-menu"></i>
            <span slot="title">Dashboard</span>
          </el-menu-item>
          <el-menu-item index="/user" v-if="user.role === 'ROLE_ADMIN'">
            <i class="el-icon-user-solid"></i>
            <span slot="title">Account Information</span>
          </el-menu-item>
          <el-submenu index="2" v-if="user.role === 'ROLE_STAFF'">
            <template slot="title">
              <i class="el-icon-more"></i>
              <span>User Information</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/lawyer">Lawyer</el-menu-item>
              <el-menu-item index="/client">Client</el-menu-item>
            </el-menu-item-group>
          </el-submenu>



          <el-menu-item index="/lawCaseAcceptance" v-if="user.role === 'ROLE_STAFF'">
            <i class="el-icon-s-management"></i>
            <span slot="title">Law Case</span>
          </el-menu-item>

          <el-menu-item index="/myCase" v-if="user.role === 'ROLE_CLIENT'">
            <i class="el-icon-s-management"></i>
            <span slot="title">My Case</span>
          </el-menu-item>

          <el-menu-item index="/logs" v-if="user.role === 'ROLE_ADMIN'">
            <i class="el-icon-s-order"></i>
            <span slot="title">Logs</span>
          </el-menu-item>

        </el-menu>
      </el-aside>
      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 20px">
          <el-breadcrumb-item :to="{ path: '/' }">Dashboard</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{$route.meta.name}}</el-breadcrumb-item>
        </el-breadcrumb>

        <router-view @update:user="updateUser"/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Layout",

  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },

  methods: {
    updateUser(user) { //获取子组件传过来的数据更新当前页面数据
      this.user = JSON.parse((JSON.stringify(user)))//让父级对象和子集对象毫无关联
    },
    logout(){
      localStorage.removeItem("user");
      this.$router.push("/homepage_frontend");
    },
    profile() {
      this.$router.push("/profile");
    },
    password() {
      this.$router.push("/password");
    },
    home() {
      this.$router.push("/homepage_frontend");
    },
  }
}
</script>

<style>
.el-menu{
  border-right: none !important;
}
</style>
