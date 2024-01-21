<template>
  <div  style="height: 94vh; display: flex; align-items: center; justify-content: center;">

    <div  class="home" style="display: flex; background-color: white; width: 70%; border-radius: 5px; overflow: hidden">
      <el-calendar>
        <template
            slot="dateCell"
            slot-scope="{date, data}">
          <div class="calendarStyle" style="min-height: 85px; word-break: break-all" @click="handleClick(data.day)">
            <div> {{ data.day.split('-').slice(1).join('-') }} </div>
            <div v-if="arr.find(v => v.date === data.day)">
              <span style="display:block; color: dodgerblue; cursor: pointer">More...</span>
            </div>
          </div>
        </template>
      </el-calendar>

      <!-- Form -->

      <el-dialog title="Event" :visible.sync="dialogFormEvent" style="width: 70%; margin-left: 200px">
        <el-table :data="showEvent" border style="width: 100%" @selection-change="handleSelectionChange" >
          <el-table-column prop="content" label="Content" style="width: 100%"></el-table-column>
          <el-table-column prop="date" label="Date" style="width: 100%"></el-table-column>
        </el-table>
<!--        <div v-for="(item, index) in showEvent" :key="index" style="line-height: 30px; text-align: left; padding: 0 80px">{{item.content}}</div>-->
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormEvent = false">Cancel</el-button>
          <el-button type="primary" @click="addNewEvent">Add a new event</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Event" :visible.sync="dialogFormVisible" style="width: 70%; margin-left: 200px">
        <el-form :model="calendar" :label-width="formLabelWidth">
          <el-form-item label="Please input content" >
            <el-input type="textarea" v-model="calendar.content" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="save">Confirm</el-button>
        </div>
      </el-dialog>


      <el-dialog title="Daily Event" :visible.sync="visible" style="width: 70%; margin-left: 200px">
        <div v-html = "content" style="line-height: 30px; text-align: left; padding: 0 80px"></div>
        <el-button  style="margin-top: 20px" type="primary" @click="saveContent">Got it</el-button>
      </el-dialog>

    </div>
  </div>


</template>

<script>

import request from "@/utils/request";

export default {
  name: "calender",
  data() {
    return {
      value: new Date(),
      arr: [],
      dialogFormVisible: false,
      dialogFormEvent: false,
      params: {
        cname: "",
      },
      calendar:{
        cname: ""
      },
      formLabelWidth: '200px',
      visible: true,
      content: '',
      showEvent: [],
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    };
  },

  created() {
    this.params.cname = this.user.name

    this.load();
  },
  methods: {
    saveContent(){
      this.visible = false;
    },
    load() {
      request.get("/event/search", {
        params: this.params,
      }).then(res => {
        if(res.code === '0'){
          this.arr = res.data

          const date = new Date().toJSON().split('T').join(' ').substr(0, 10)
          const data = this.arr.find(v => v.date === date)
          if(data) {
            this.content = data.content
            this.visible = true
          }
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    save() {
      request.post("/event", this.calendar).then(res => {
        if(res.code === '0'){
          this.$message({
            message: 'Operate Successfully',
            type: 'success'
          });
          this.load()
          this.dialogFormVisible = false
        } else {
          this.$message.error(res.msg);
          }
      })
    },
    addNewEvent() {
      this.dialogFormVisible = true;
      this.dialogFormEvent = false;
    },
    handleClick(date) {
      request.get("/event/date/" + date).then(res => {
        if (res){
          this.showEvent = res.data
          this.calendar = {date: date}
          this.calendar.cname = this.user.name
        }
        this.dialogFormEvent = true;
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  },
};
</script>


<style>.calendarStyle{
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 2;
}

</style>
