<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      Hello {{ user.name }}, have a good day!
    </div>

    <div style="display: flex">
      <el-card style="width: 100%">
        <div style="text-align: center" slot="header" className="clearfix">
          <span style="font-size: 43px">Welcome to G12 law firm management system</span>
        </div>
        <div>
          <div style="margin-top: 20px">
            <div>
              <el-table :data="tableData" border style="width: 100%">
                <el-table-column prop="content" label="Content"></el-table-column>
                <el-table-column prop="date" label="Date"></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </el-card>

    </div>
  </div>

</template>

<script>

import request from "@/utils/request";

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
        Content:"",
        eventtitle:"",
        description:"",
        caseid:"",
        date:"",

      },
      tableData: [],
      eventObjs: [],
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

  created() {

    this.findBySearchEvent();

  },
  //定义一些页面上空间触发事件调用的方法
  methods: {
    findBySearchEvent() {
      request.get("/event/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list
          this.total = res.data.total
        } else {
          this.$message.error(res.msg);
        }
      })
    }
  }
}


</script>
