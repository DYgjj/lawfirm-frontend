<template>
  <div>
    <el-container>
<!--      <img src="@/assets/p2.jpg" alt=""  style="width: 100%; position:center;opacity: 0.5;">-->
      <el-header>
        <div style="flex: 2;display: flex;justify-content: center">
          <div style="margin-top: 20px;font-size: 90px;font-family:'Times New Roman';color: antiquewhite" >Professional Lawyer Team</div>
        </div>
        <el-divider></el-divider>
      </el-header>




      <el-main style="margin-top: 100px;justify-content: center">
        <div style="margin: 20px 0;display: flex; flex-wrap: wrap;justify-content: center;">
            <div v-for="(item, index) in tableData" :key="index" style="width: 400px;height: 1000px;margin:10px 50px 50px 5px;background-color: black; color: antiquewhite; background: rgb(0,0,0);background: rgba(0,0,0,0.5);box-shadow: 0 0 10PX -2PX RGBA(0,0,0,5)">
              <div style="width: 400px"  >
                <el-image
                    style="width: 380px; position:center;margin-left: 10px;margin-top: 10px"
                    :src="'http://localhost:8080/files/' + item.photo"
                    :preview-src-list="['http://localhost:8080/files/' + item.photo]">
                </el-image>
              </div>
              <el-divider></el-divider>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman';font-size: xx-large">Name:{{item.name}}</div>
              <el-divider></el-divider>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman';font-size: large"> Gender: {{item.gender}}</div>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman';font-size: large"> Age: {{item.age}}</div>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman';font-size: large"> Phone: {{item.phone}}</div>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman';font-size: large"> E-mail: {{item.email}}</div>
              <el-divider></el-divider>
              <div style="margin-top: 10px;margin-left: 10px;font-family:'Times New Roman'">
                Description: {{item.description}}

              </div>
              <div style="margin-top: 20px;margin-left: 10px;font-family:'Times New Roman'">
                Type of expertise: {{item.expertise}}
              </div>
            </div>
        </div>
      </el-main>
    </el-container>





    <container style=" background-color: transparent; text-align: left;line-height: 60px;position: fixed;left: 0;bottom: 0;width: 100%;height: 50px">
      <el-footer>
        <el-button type="primary" style="position: relative; bottom: 10px"  @click="add()"  >Contact Us Now</el-button>

        <div>
          <el-dialog title="Please input information" :visible.sync="dialogFormVisible1" width="43%">
            <el-form :model="form">
              <el-form-item label="Title" label-width="16%" >
                <el-input v-model="form.title" autocomplete="off" style="width: 100%"></el-input>
              </el-form-item>
              <el-form-item label="type" prop="status" label-width="16%" >
                <el-select  style="width: 30%" v-model="form.type">
                  <el-option v-for="item in ['criminal', 'civil']" :key="item" :value="item"></el-option>
                </el-select>
              </el-form-item >
              <el-form-item label="Lawyer Name" label-width="16%" >
                <el-select v-model="form.lname" placeholder="Select" style="width: 30%">
                  <el-option v-for="item in lawyerObjs" :key="item.id" :label="item.name" :value="item.id"></el-option>
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

        <el-dialog title="Fill Out Your" :visible.sync="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="Name" :label-width="formLabelWidth">
              <el-input
                  type="textarea"
                  autosize
                  autocomplete="off"
                  placeholder="Please enter your name..."
                  v-model="form.name">
              </el-input>
            </el-form-item>
            <el-form-item label="Case Type" :label-width="formLabelWidth">
              <el-select v-model="form.caseType" placeholder="Please choose case type">
                <el-option label="Criminal Matter" value="Criminal Matter"></el-option>
                <el-option label="Civil Matter" value="Civil Matter"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Lawyer" :label-width="formLabelWidth">
              <el-select v-model="form.lawyer" placeholder="Please choose your lawyer">
                <el-option label="Attorney Emily Davis" value="Criminal Matter"></el-option>
                <el-option label="Michael Chang" value="Civil Matter"></el-option>
                <el-option label="Jessica Patel " value="Civil Matter"></el-option>
                <el-option label="Attorney Brian Mitchell " value="Civil Matter"></el-option>
                <el-option label="Sophia Rodriguez" value="Civil Matter"></el-option>
                <el-option label="Attorney Emily Davis" value="Civil Matter"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Case Detail" :label-width="formLabelWidth">
              <el-input
                  type="textarea"
                  autocomplete="off"
                  :autosize="{ minRows: 4, maxRows: 10}"
                  placeholder="Please enter the case detail..."
                  v-model="form.caseDetail">

              </el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Cancel</el-button>
            <el-button type="primary" @click="dialogFormVisible = false">Submit</el-button>
          </div>
        </el-dialog>

      </el-footer>
    </container>

  </div>
</template>

<script>

import E from 'wangeditor'
import request from "@/utils/request";
import hljs from "highlight.js";


let editor
function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      editor = new E('#editor')
      editor.config.placeholder = '请输入内容'
      editor.config.uploadFileName = 'file'
      editor.config.uploadImgServer = '/files/wang/upload'
      editor.create()
    }
    editor.txt.html(content)
  }, 0)
}
export default {
  name: 'team_frontend',
  data() {
    return {
      items: [
        { name: 'John', age: 25 },
        { name: 'Alice', age: 30 },
        { name: 'Bob', age: 22 }
        // 其他数据...
      ],
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
      total: 0,
      dialogFormVisible: false,
      editorVisible :false,
      dialogFormFeedback: false,
      dialogFormStatus: false,


      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
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
      dialogTableVisible: false,
      dialogFormVisible1: false,
      form1 : {},
      eventObjs: {},
      EventD : false,
      EventFormVisible: false,
      EventForm: false,
      EventAddtable:false,
      lawyerObjs: [],
    };
  },
  created() {
    this.findBySearch();
    this.findLawyer();
  },


  methods: {
    findBySearch(){
      request.get("/lawyer/findAll").then(res => {
        if (res.code === '0') {
          this.tableData = res.data
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
    add() {
      initWangEditor("");
      this.form1 = {};
      this.dialogFormVisible1 = true;
    },
    submit() {
      this.form.content = editor.txt.html()
      this.form.status = "pending"
      this.form.cname = this.user.name
      request.post("/lawCase",this.form).then(res => {
        if (res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.dialogFormFeedback = false;
          this.dialogFormStatus = false;
          this.dialogFormVisible1 = false;
          this.editorVisible = false;
          this.findBySearch()
        }else {
          this.$message.error(res.msg);
        }
      })
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
