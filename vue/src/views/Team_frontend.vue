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
        <el-button type="primary" round  @click="dialogFormVisible = true"  style="position: relative; bottom: 10px">Contact Us Now</el-button>
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
import request from "@/utils/request";

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
      tableData: [],
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
      dialogFormVisible: false,
    };
  },
  created() {
    this.findBySearch();
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
  }
}
</script>
