<template>
  <div>
      <h1 style="border-top: 10px; color: antiquewhite; text-align: center">OUR TEAM MEMBERS</h1>

    <div style="margin: 20px 0;display: flex; flex-wrap: wrap;justify-content: center;">
      <div style="width: 500px;height: 450px;margin:10px 50px 50px 5px;background-color: #211e1e;box-shadow: 0 0 10PX -2PX RGBA(0,0,0,5);border-radius: 15px;background: rgb(0,0,0);background: rgba(0,0,0,0.5);">
        <el-card class="box-card" style="background: rgb(204, 204, 204);background: rgba(204, 204, 204, 0.5); width: 100%; height: 100%;border-radius: 15px">
      <div slot="header" class="clearfix" style="color: antiquewhite">
        <span>Zeng Yukun (Matric No:155168)</span>
      </div>
      <div>
        <img src="@/assets/yukun.jpg" alt="" style="width: 180px;height: 200px">
        <div style="color: antiquewhite">
          <li>Implement backend logic for the forms on the website.</li>
          <li>Develop secure user authentication and login functionality.</li>
          <li>Set up alert functionalities to notify relevant parties of form submissions or other critical events.</li>
        </div>
      </div>
    </el-card>
      </div>
      <div style="width: 500px;height: 450px;margin:10px 50px 50px 5px;background-color: #211e1e;box-shadow: 0 0 10PX -2PX RGBA(0,0,0,5);border-radius: 15px;background: rgb(0,0,0);background: rgba(0,0,0,0.5);">
        <el-card class="box-card" style="background: rgb(204, 204, 204);background: rgba(204, 204, 204, 0.5); width: 100%; height: 100%;border-radius: 15px">
          <div slot="header" class="clearfix" style="color: antiquewhite">
            <span>Liu Yifan (Matric No:155054)</span>
          </div>
          <div>
            <img src="@/assets/yifan.jpg" alt=""  style="width: 160px;height: 200px">
            <div style="color: antiquewhite">
              <li>Design and implement the backend infrastructure, including database architecture.</li>
              <li>Develop secure user authentication and login functionality.</li>
              <li>Ensure data integrity and implement necessary backend logic for user accounts.</li>
            </div>
          </div>
        </el-card>

      </div>
    </div>
    <div style="margin: 20px 0;display: flex; flex-wrap: wrap;justify-content: center">
      <div style="width: 500px;height: 450px;margin:10px 50px 50px 5px;background-color: #211e1e;box-shadow: 0 0 10PX -2PX RGBA(0,0,0,5);border-radius: 15px;background: rgb(0,0,0);background: rgba(0,0,0,0.5);">
        <el-card class="box-card" style="background: rgb(204, 204, 204);background: rgba(204, 204, 204, 0.5); width: 100%; height: 100%;border-radius: 15px">
          <div slot="header" class="clearfix" style="color: antiquewhite">
            <span>Wei Xiaolong (Matric No:155114)</span>
          </div>
          <div>
            <img src="@/assets/xiaolong.jpg" alt=""  style="width: 180px;height: 200px">
            <div style="color: antiquewhite">
              <li>Design and develop the frontend components responsible for displaying content.</li>
              <li>Create a visually appealing and user-friendly interface for presenting team information.</li>
              <li>Implement a timeline feature to showcase the law firm's history, milestones, or notable achievements.</li>
            </div>
          </div>
        </el-card>
      </div>
      <div style="width: 500px;height: 450px;margin:10px 50px 50px 5px;background-color: #211e1e;box-shadow: 0 0 10PX -2PX RGBA(0,0,0,5);border-radius: 15px;background: rgb(0,0,0);background: rgba(0,0,0,0.5);">
        <el-card class="box-card" style="background: rgb(204, 204, 204);background: rgba(204, 204, 204, 0.5); width: 100%; height: 100%;border-radius: 15px">
          <div slot="header" class="clearfix" style="color: antiquewhite">
            <span>Hu Dewen (Matric No:155166)</span>
          </div>
          <div>
            <img src="@/assets/dewen.jpg" alt=""  style="width: 160px;height: 200px">
            <div style="color: antiquewhite">
              <li>Design the overall layout and visual elements of the the website.</li>
              <li>Implement the user interface for submitting forms, integrating with backend form functionalities.</li>
              <li>Implement frontend features for seamless user interaction and form submission.</li>
            </div>
          </div>
        </el-card>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'about_frontend',
}
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>
