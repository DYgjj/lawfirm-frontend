<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.title" style="width: 200px" placeholder="Please input title"></el-input>
      <el-button type="warning" style="margin: 10px; width: 70px" @click="findBySearch()">search</el-button>
      <el-button type="warning" style="margin: 0px; width: 70px" @click="reset()">clean</el-button>
      <el-button type="primary" style="margin: 10px; width: 70px" @click="add()">add</el-button>
      <el-popconfirm title="Confirm to delete?" @confirm="delBatch()">
        <el-button slot="reference" type="danger" style="width: 100px">Batch Delete</el-button>
      </el-popconfirm>

      <el-button type="primary" style="margin: 10px; width: 100px" @click="findBySearchC()">Completion</el-button>
      <el-button type="primary" style="margin: 10px; width: 100px" @click="findBySearchR()">Refusaled</el-button>
      <el-button type="primary" style="margin: 10px; width: 100px" @click="findBySearch()">Aacceptanced</el-button>
      <el-button type="primary" style="margin: 10px; width: 100px" @click="findBySearchP()">Pending</el-button>

    </div>

    <div class="about">
      <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="title" label="Title"></el-table-column>
        <el-table-column prop="type" label="Type"></el-table-column>
        <el-table-column prop="cname" label="Client Name"></el-table-column>
        <el-table-column prop="lawyerName" label="Lawyer Name"></el-table-column>
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
          ///gaide
          <el-form-item label="Lawyer Name" label-width="16%" >
            <el-select v-model="form.lname" placeholder="Select" style="width: 30%">
              <el-option v-for="item in lawyerObjs" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>

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




    <div>
    <el-dialog title = "case detail" :visible.sync = "EventD" width = "50%">
      <div v-html="this.eventObjs.description" class = "w-e-text"></div>
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
        <div v-html="this.form.feedback" class = "w-e-text"></div>
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
    <!-- addevent -->
    <div>
      <el-dialog title="Please input information" :visible.sync="EventAddtable" width="43%">
        <el-form :model="eventObjs">
          <el-form-item label="Title" label-width="16%" >
            <el-input v-model="eventObjs.eventtitle" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item>
          <el-form-item label="Description" label-width="16%" >
            <el-input v-model="eventObjs.description" autocomplete="off" style="width: 100%" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="Dventdate" label-width="16%" >
            <el-input v-model="eventObjs.eventdate" autocomplete="off" style="width: 100%"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="EventAddtable = false">Cancel</el-button>
          <el-button type="primary" @click="submitevent()">Confirm</el-button>
        </div>
      </el-dialog>
      <el-dialog title = "case detail" :visible.sync = "editorVisible" width = "50%">
        <div v-html="this.form.content" class = "w-e-text"></div>
      </el-dialog>
    </div>

    <!-- event -->
    <div>
      <el-dialog title="Event" :visible.sync="EventForm" width="43%">
        <div class="about" >
          <el-table :data="eventObjs" border style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="eventtitle" label="Eventtitle"></el-table-column>
            <el-table-column prop="description" label="Description">
              <template v-slot="scope">
                <el-button type= "success" style="width: 65px" @click="viewEditor(scope.row.description)">
                  Detail
                </el-button>
              </template>
            </el-table-column>
            <el-table-column prop="eventdate" label="eventdate"></el-table-column>
            <el-table-column label="operate">
              <template v-slot="scope">
                <el-button type="primary" style="margin: 10px; width: 70px" @click="EventAdd()">addEvent</el-button>
                <el-popconfirm title="Confirm to delete?" @confirm="del(scope.row.id)">
                  <el-button slot="reference" type="danger" style="width: 65px; margin-left: 10px">Delete</el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
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
        content: "",
        title: "",
        pageNum: 1,
        pageSize: 5,
        type: "",
        status: "",
        feedback:"",
        eventtitle:"",
        description:"",
        caseid:"",
        eventdate:"",
      },
      tableData: [],
      eventObjs: {},
      total: 0,
      dialogFormVisible: false,
      editorVisible :false,
      EventD : false,
      dialogFormFeedback: false,
      dialogFormStatus: false,
      EventFormVisible: false,
      EventForm: false,
      EventAddtable:false,
      eventObjs1:[],
      form: {},
      multipleSelection: [],
      ///gaide
      lawyerObjs: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  //页面加载的时候，做一些事情在created里面
  created() {

    //gaide
    this.findBySearchEvent();
    this.findBySearchP();
    this.findLawyer();
    this.findevent();
    //this.cname();
  },
  //定义一些页面上空间触发事件调用的方法
  methods:{
    findBySearchC(){
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
    findBySearchP(){
      request.get("/lawCase/search", {
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
    findBySearchR(){
      request.get("/lawCase/searchRefusal", {
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
    findBySearch(){
      request.get("/lawCase/searchAcceptance", {
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
    findBySearchEvent(){
      request.get("/event/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.eventObjs = res.data.list
          this.total = res.data.total
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    findLawyer() {
      request.get("/lawyer/findAll").then(res => {
        if (res.code === '0') {
          this.lawyerObjs = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    findBySearch2(){
      request.get("/lawCase/search2", {
        params: this.params,


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
    viewEventD(data){
      this.form.description = data;
      this.EventD = true;
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
    cname(){
      this.params.cname = this.user.name;
      this.findBySearch2();
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
      initWangEditor("");
      this.form = {};
      this.dialogFormVisible = true;
    },
    EventAdd() {

      this.eventObjs = {};
      this.EventAddtable = true;
    },

    openevent() {

      this.form = {};
      this.EventForm = true;
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
          this.dialogFormStatus = false;
          this.dialogFormVisible = false,
              this.editorVisible = false,
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
          this.dialogFormVisible = false,
              this.editorVisible = false,
          this.findBySearch()
        }else {
          this.$message.error(res.msg);
        }
      })
    },

    submitevent() {
      request.post("/event",this.eventObjs).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormFeedback = false;
          this.dialogFormStatus = false;
          this.dialogFormVisible = false,
              this.editorVisible = false,
            this.EventAddtable = false,
              this.findBySearchEvent()
        }else {
          this.$message.error(res.msg);
        }
      })
    },
    findevent() {
      request.get("/event/findAll").then(res => {
        if (res.code === '0') {
          this.eventObjs1 = res.data;
        } else {
          this.$message.error(res.msg)
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
