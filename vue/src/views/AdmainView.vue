<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.name" style="width: 200px" placeholder="Please input name"></el-input>
      <el-input v-model="params.email" style="width: 200px; margin-left: 5px" placeholder="Please input email"></el-input>
      <el-button type="warning" style="margin: 10px; width: 70px" @click="finBySearch()">search</el-button>
      <el-button type="warning" style="margin: 0px; width: 70px" @click="reset()">clean</el-button>
      <el-button type="primary" style="margin: 10px; width: 70px" @click="add()">add</el-button>
    </div>

    <div class="about">

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="account" label="Account"></el-table-column>
        <el-table-column prop="email" label="Emal"></el-table-column>
        <el-table-column label="operate">
          <template v-slot="scope">
            <el-button type="primary" style="width: 65px" @click="edit(scope.row)">Edit</el-button>
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

    <div>
      <el-dialog title="Please input information" :visible.sync="dialogFormVisible" width="38%">
        <el-form :model="form">
          <el-form-item label="Name" label-width="16%" >
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Account" label-width="16%" >
            <el-input v-model="form.account" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Email" label-width="16%" >
            <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submit()">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  data() {
    return {
      params: {
        name: "",
        email: "",
        pageNum: 1,
        pageSize: 5,
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {}
    }
  },
  //页面加载的时候，做一些事情在created里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上空间触发事件调用的方法
  methods:{
    findBySearch(){
      request.get("/admin/search", {
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
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    submit() {
      request.post("/admin",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch()
        }else {
          this.$message.error(res.msg);
        }
      })
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    del(id) {
      request.delete("/admin/" + id).then(res =>{
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
    }
  }
}
</script>
