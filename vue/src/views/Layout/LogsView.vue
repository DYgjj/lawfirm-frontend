<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.operation" style="width: 200px" placeholder="Please input operation content"></el-input>
      <el-input v-model="params.user" style="width: 200px; margin-left: 5px" placeholder="Please input operation user name"></el-input>
      <el-button type="warning" style="margin: 10px; width: 70px" @click="findBySearch()">search</el-button>
      <el-button type="warning" style="margin: 0px; width: 70px" @click="reset()">clean</el-button>
    </div>

    <div class="about">

      <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column prop="operation" label="Operation Content"></el-table-column>
        <el-table-column prop="type" label="Operation Type"></el-table-column>
        <el-table-column prop="user" label="Operation User Name"></el-table-column>
        <el-table-column prop="time" label="Operation Time"></el-table-column>
        <el-table-column prop="ip" label="IP"></el-table-column>
        <el-table-column label="operate">
          <template v-slot="scope">
            <el-popconfirm title="Confirm to delete?" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="width: 65px; margin-left: 10px">Delete</el-button>
            </el-popconfirm>
          </template>

        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 5px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: "HomeView",
  data() {
    return {
      params: {
        operation: "",
        user: "",
        pageNum: 1,
        pageSize: 5,
      },
      tableData: [],
      total: 0,
      form: {

      },
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  //页面加载的时候，做一些事情在created里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上空间触发事件调用的方法
  methods:{
    findBySearch(){
      request.get("/logs/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list
          this.total = res.data.total
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    reset() {
      this.params = {
        name: '',
        email: '',
        pageNum: 1,
        pageSize: 5,
      }
      this.findBySearch();
      },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    del(id) {
      request.delete("/logs/" + id).then(res =>{
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  }
}
</script>
