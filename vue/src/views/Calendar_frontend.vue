<template>
  <div  style="height: 94vh; display: flex; align-items: center; justify-content: center;">

    <div  class="home" style="display: flex; background-color: white; width: 70%; border-radius: 5px; overflow: hidden">
      <el-calendar>
        <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
        <template
            slot="dateCell"
            slot-scope="{date, data}">
          <div class="calendarStyle" style="min-height: 85px; word-break: break-all" @click="handleClick(data.day)">
            <div> {{ data.day.split('-').slice(1).join('-') }} </div>
            <div v-if="arr.find(v => v.date === data.day)"> {{ arr.find(v => v.date === data.day).content }} </div>
          </div>
        </template>
      </el-calendar>

      <!-- Form -->

      <el-dialog title="Event" :visible.sync="dialogFormVisible" style="width: 70%; margin-left: 200px">
        <el-form :model="calendar">
          <el-form-item label="Please input content" :label-width="formLabelWidth">
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
        <el-button @click="visible = false" style="margin-top: 20px" type="primary">Got it</el-button>
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
      calendar:{},
      formLabelWidth: '200px',
      visible: true,
      content: ''
    };
  },

  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/event").then(res => {
        if(res.code === '0'){
          this.arr = res.data
        } else {
          this.$message.error(res.msg);
        }
        const date = new Date().toJSON().split('T').join(' ').substr(0, 10)
        const data = this.arr.find(v => v.date === date)
        if(data) {
          this.visible = true
          this.content = data.content
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
          this.dialogFormVisible = false
          }
      })
    },
    handleClick(date) {
      request.get("/event/date/" + date).then(res => {
        if(res){
          this.calendar = res.data
          this.dialogFormVisible = true;
        } else {
          this.$message.error(res.msg);
          this.dialogFormVisible = true;
        }
      })
    }

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
