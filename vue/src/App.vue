<template>
  <div id="app">
    <el-container>
      <el-header style="background-color: #4c535a">
        <img src="@/assets/law-firm-logo.png" alt="" style="width: 80px; position: relative; top: 10px;">
        <span style="font-size: 18px; margin-left: 10px; color: white">G12 Law Firm</span>
      </el-header>
    </el-container>

    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color: #545c64; width: 220px">
        <el-menu :default-active="$route.path" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/">
            <i class="el-icon-menu"></i>
            <span slot="title">Dashboard</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>Information</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin">Admin</el-menu-item>
              <el-menu-item index="1-2">Client</el-menu-item>
              <el-menu-item index="1-3">Lawyer</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style>
.el-menu{
  border-right: none !important;
}
</style>
