<template>
  <div class="app-container check-calendar">
    <cus-wraper full-height>
      <cus-title-card title="数据审核日历">
        <cus-filter-wraper>
          <el-date-picker v-model="month" type="month" :clearable="false" value-format="yyyy-MM" placeholder="请选择月份" />
        </cus-filter-wraper>
        <el-calendar v-model="month">
          <template
              slot="dateCell"
              slot-scope="{date, data}"
          >
            <div class="data-container" :class="calcCurrentData(data) ? (calcCurrentData(data).approveCheckStatus === 0 ? 'un-approve': 'is-approve') : '' ">
              <p :class="{'not-current-month': data.type !== 'current-month'}">{{ data.day | filterCalendar }}</p>
              <template v-if="calcCurrentData(data)">
                <span v-if="calcCurrentData(data).approveCheckStatus === 1" class="status">已审核</span>
                <span v-else class="status">未审核</span>
                <a @click="lookData(calcCurrentData(data))">查看数据</a>
              </template>
            </div>
          </template>
        </el-calendar>
      </cus-title-card>
    </cus-wraper>
  </div>
</template>

<script>
export default {
  data() {
    return {
      month: moment().format('YYYY-MM'),
      calendarData: []
    }
  },
  mounted() {
    this.getData()
    const queryDate = this.$route.query.date
    console.log(queryDate)
    if (queryDate) {
      this.listQuery.timeStart = moment(queryDate).format('YYYY-MM-DD HH:mm:ss')
      this.listQuery.timeEnd = moment(queryDate).format('YYYY-MM-DD HH:mm:ss')
    }
    this.getList()
  },
  methods: {
    lookData(data) {
      this.$router.push({ path: '/wam/data/data-approve/index', query: { date: data.dateData }})
    },
    getData(month) {
      approveCalendar({
        type: 1,
        date: month
      }).then(response => {
        this.calendarData = response.data
      })
    }
  }
}
</script>
