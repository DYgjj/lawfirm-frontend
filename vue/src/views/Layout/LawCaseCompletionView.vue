CaseRejectedView.vue<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.title" style="width: 200px" placeholder="Please input title"></el-input>
      <el-button type="warning" style="margin: 10px; width: 70px" @click="findBySearch()">search</el-button>
      <el-button type="warning" style="margin: 0px; width: 70px" @click="reset()">clean</el-button>
      <el-button type="primary" style="margin: 10px; width: 70px" @click="add()">add</el-button>
      <el-popconfirm title="Confirm to delete?" @confirm="delBatch()">
        <el-button slot="reference" type="danger" style="width: 100px">Batch Delete</el-button>
      </el-popconfirm>
    </div>

    <div class="about">
      <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="title" label="Title"></el-table-column>
        <el-table-column prop="type" label="Type"></el-table-column>
        <el-table-column prop="cname" label="Client Name"></el-table-column>
        <el-table-column prop="lname" label="Lawyer Name"></el-table-column>
        <el-table-column prop="status" label="Status">
          <template v-slot="scope">
            <div v-if="isShow"> {{scope.row.status}}
              <span >
                <el-button type= "primary" style="width: 65px; float: right" @click="editStatus(scope.row)">
                  Edit
                </el-button>
              </span>
            </div>

          </template>
        </el-table-column>

        <el-table-column prop="feedback" label="Feedback">
          <template v-slot="scope">
            <el-button type= "primary" style="width: 65px; margin-left: 10px" @click="editFeedback(scope.row)">Edit</el-button>
            <el-button type= "success" style="width: 65px" @click="viewEditor(scope.row.feedback)">
              Detail
            </el-button>
          </template>
        </el-table-column>

        <el-table-column label="Content">
          <template slot-scope="scope" type="primary">
            <el-button type = "success" @click="viewEditor(scope.row.content)">
              Detail
            </el-button>
          </template>
        </el-table-column>
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

<!--    add-->
    <div>
      <el-dialog title="Please input information" :visible.sync="dialogFormVisible" width="43%">
        <el-form :model="form">
          <el-form-item label="Title" label-width="16%" >
            <el-input v-model="form.title" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item>
          <el-form-item label="type" prop="status" label-width="16%" >
            <el-select  style="width: 30%" v-model="form.type">
              <el-option v-for="item in ['criminal', 'civil']" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item >
          <el-form-item label="Client Name" label-width="16%" >
            <el-input v-model="form.cname" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item>
          <el-form-item label="Lawyer Name" label-width="16%" >
            <el-input v-model="form.lname" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item >
          <el-form-item label="status" prop="status" label-width="16%" >
            <el-select  style="width: 30%" v-model="form.status">
              <el-option v-for="item in ['pending', 'refusal','acceptance','completion']" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item >

          <el-form-item  prop="content" label="Content" label-width="16%" style="width: 100%">
            <div id="editor"></div>
          </el-form-item>

          <el-form-item label="Status feedback" label-width="16%" >
            <el-input v-model="form.feedback" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submit()">Confirm</el-button>
        </div>
      </el-dialog>
      <el-dialog title = "case detail" :visible.sync = "editorVisible" width = "50%">
        <div v-html="this.form.content" class = "w-e-text"></div>
      </el-dialog>
    </div>

<!--    修改feedback-->
    <div>
      <el-dialog title="Please input information" :visible.sync="dialogFormFeedback" width="43%">
        <el-form :model="form">

          <el-form-item label="Status feedback" label-width="16%" >
            <el-input v-model="form.feedback" autocomplete="off" style="width: 100%" type="textarea"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormFeedback = false">Cancel</el-button>
          <el-button type="primary" @click="submitNoText()">Confirm</el-button>
        </div>
      </el-dialog>
      <el-dialog title = "case detail" :visible.sync = "editorVisible" width = "50%">
        <div v-html="this.form.content" class = "w-e-text"></div>
      </el-dialog>
    </div>

<!-- 修改status -->
    <div>
      <el-dialog title="Please input information" :visible.sync="dialogFormStatus" width="43%">
        <el-form :model="form">

          <el-form-item label="status" prop="status" label-width="16%" >
            <el-select  style="width: 30%" v-model="form.status">
              <el-option v-for="item in ['pending', 'refusal','acceptance','completion']" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item >

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormStatus = false">Cancel</el-button>
          <el-button type="primary" @click="submitNoText()">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import E from 'wangeditor'
import request from "@/utils/request";
import hljs from "highlight.js";

let editor
function initWangEditor(content) {	setTimeout(() => {
  if (!editor) {
    editor = new E('#editor')
    editor.config.placeholder = '请输入内容'
    editor.config.uploadFileName = 'file'
    editor.config.uploadImgServer = 'http://localhost:8080/files/wang/upload'
    editor.create()
  }
  editor.txt.html(content)
}, 0)
}
export default {
  data() {
    return {
      isShow: true,
      params: {
        cname: "",
        lname: "",
        content: "",
        title: "",
        pageNum: 1,
        pageSize: 5,
        type: "",
        status: "",
        feedback:""
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      editorVisible :false,
      dialogFormFeedback: false,
      dialogFormStatus: false,
      form: {},
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
      request.get("/lawCase/searchCompletion", {
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
    viewEditor(data){
      this.form.content = data;
      this.editorVisible = true;
    },
    reset() {
      this.params = {
        cname: "",
        lname: "",
        content: "",
        title: "",
        pageNum: 1,
        pageSize: 5,
        type: "",
        status: "",
        feedback:""

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
      initWangEditor("")
      this.dialogFormVisible = true;
    },
    submit() {
      this.form.content = editor.txt.html()
      request.post("/lawCase",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormFeedback = false;
          this.findBySearch()
        }else {
          this.$message.error(res.msg);
        }
      })
    },
    submitNoText() {
      request.post("/lawCase",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormFeedback = false;
          this.dialogFormStatus = false;
          this.findBySearch()
        }else {
          this.$message.error(res.msg);
        }
      })
    },
    edit(obj) {
      this.form = obj;
      initWangEditor(obj.content ? this.form.content:"");
      this.dialogFormVisible = true;
    },

    editFeedback(obj) {
      this.form = obj;
      this.dialogFormFeedback = true;
    },

    editStatus(obj) {
      this.form = obj;
      this.dialogFormStatus = true;
    },

    del(id) {
      request.delete("/lawCase/" + id).then(res =>{
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

    delBatch() {
      if (this.multipleSelection.length ===0){
        this.$message.warning("Please select the option you want to delete!")
        return
      }
      request.put("/lawCase/delBatch", this.multipleSelection).then(res => {
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
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.config.uploadVideoServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadVideoName = 'file'
        this.editor.config.uploadVideoHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadVideoParams = {
          type: 'video',
        }
        this.editor.create()  // 创建
      })
    },
  }
}
</script>
