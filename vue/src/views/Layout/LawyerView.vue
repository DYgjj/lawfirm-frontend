<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.name" style="width: 200px" placeholder="Please input name"></el-input>
      <el-select v-model="params.expertise" style="width: 200px; margin-left: 5px" placeholder="Please select expertise">
        <el-option label="Civil Case" value="Civil"></el-option>
        <el-option label="Criminal Case" value="Criminal"></el-option>
      </el-select>
      <el-button type="warning" style="margin: 10px; width: 70px" @click="findBySearch()">search</el-button>
      <el-button type="warning" style="margin: 0px; width: 70px" @click="reset()">clean</el-button>
    </div>
    <div>
      <el-button v-if="user.role === 'ROLE_ADMIN' || user.role === 'ROLE_STAFF'" type="primary" style="margin: 10px; width: 70px" @click="add()">add</el-button>
      <el-popconfirm v-if="user.role === 'ROLE_STAFF'" title="Confirm to delete?" @confirm="delBatch()">
        <el-button slot="reference" type="danger" style="width: 100px">Batch Delete</el-button>
      </el-popconfirm>
    </div>

    <div class="about">

      <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="gender" label="Gender"></el-table-column>
        <el-table-column prop="age" label="Age"></el-table-column>
        <el-table-column prop="phone" label="Phone"></el-table-column>
        <el-table-column prop="email" label="Email"></el-table-column>
        <el-table-column prop="expertise" label="Expertise"></el-table-column>

        <el-table-column prop="description" label="Description">
          <template slot-scope="scope" type="primary">
            <el-button type = "success" @click="viewEditor(scope.row.description)">
              Detail
            </el-button>
          </template>
        </el-table-column>

        <el-table-column prop="photo" label="Photo">
          <template v-slot="scope">
            <el-image
                style="width: 130px; height: 130px"
                :src="'http://localhost:8080/files/' + scope.row.photo"
                :preview-src-list="['http://localhost:8080/files/' + scope.row.photo]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="operate">
          <template v-slot="scope">
            <el-button type="primary" style="width: 65px" @click="edit(scope.row)" v-if="user.role === 'ROLE_STAFF'">Edit</el-button>
            <el-popconfirm title="Confirm to delete?" @confirm="del(scope.row.id)" v-if="user.role === 'ROLE_STAFF'">
              <el-button slot="reference" type="danger" style="width: 65px; margin-left: 10px" >Delete</el-button>
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
      <el-dialog title="Please input information" :visible.sync="dialogFormVisible" width="45%">
        <el-form :model="form">
          <el-form-item label="Name" label-width="16%" >
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Gender" label-width="16%" >
            <el-radio v-model="form.gender" label="male">Male</el-radio>
            <el-radio v-model="form.gender" label="female">Female</el-radio>
          </el-form-item>
          <el-form-item label="Age" label-width="16%" >
            <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Phone" label-width="16%" >
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Email" label-width="16%" >
            <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Expertise" label-width="16%">
            <el-select v-model="form.expertise" style="width: 50%" placeholder="Please select">
              <el-option label="Civil Case" value="Civil"></el-option>
              <el-option label="Criminal Case" value="Criminal"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="description" label-width="16%" >
            <el-input v-model="form.description" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="Photo" label-width="16%" >
            <el-upload action="http://localhost:8080/files/upload" :on-success="successUpload" ref="lawyerPhoto">
              <el-button size="small" type="primary">Upload</el-button>
              <div slot="tip" class="el-upload__tip">Please upload jpg/png files with a maximum size of 500kb</div>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submit()">Confirm</el-button>
        </div>
      </el-dialog>
      <el-dialog title = "case detail" :visible.sync = "editorVisible" width = "50%">
        <div v-html="this.form.description" class = "w-e-text"></div>
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
        phone: "",
        gender: "",
        age: "",
        pageNum: 1,
        pageSize: 5,
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {

      },
      radio: '1',
      multipleSelection: [],
      editorVisible :false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  //页面加载的时候，做一些事情在created里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上空间触发事件调用的方法
  methods:{
    viewEditor(data){
      this.form.description = data;
      this.editorVisible = true;
    },
    findBySearch(){
      request.get("/lawyer/search", {
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
        name: "",
        expertise: "",
        phone: "",
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
      request.post("/lawyer",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch()
          this.$refs.lawyerPhoto.clearFiles();
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
      request.delete("/lawyer/" + id).then(res =>{
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
    successUpload(res) {
      this.form.photo = res.data;
    },
    delBatch() {
      if (this.multipleSelection.length ===0){
        this.$message.warning("Please select the option you want to delete!")
        return
      }
      request.put("/lawyer/delBatch", this.multipleSelection).then(res => {
        if (res.code === '0'){
          this.$message.success("Operate Successfully");
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
