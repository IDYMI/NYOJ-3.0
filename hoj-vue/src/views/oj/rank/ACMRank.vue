<template>
  <el-row type="flex" justify="space-around">
    <el-col :span="24">
    <el-card :padding="10">
      <div slot="header"><span class="panel-title">ACM Ranklist</span></div>
      <div class="echarts">
        <ECharts :options="options" ref="chart" :autoresize="true"></ECharts>
      </div>
    </el-card>
    <vxe-table :data="dataRank" :loading="loadingTable" align="center" highlight-hover-row auto-resize style="font-weight: 500;">
      <vxe-table-column type="seq" min-width="50"></vxe-table-column>
      <vxe-table-column field="username" title="User" min-width="150">
         <template v-slot="{ row }">
          <a @click="getInfoByUsername(row.uid,row.username)" style="color:rgb(87, 163, 243);">{{row.username}}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column field="nickname" title="Nickname" min-width="180"></vxe-table-column>
      <vxe-table-column field="signature" title="Mood" min-width="180"></vxe-table-column>
      <vxe-table-column field="solved" title="Solved" min-width="80"></vxe-table-column>
      <vxe-table-column title="AC" min-width="80">
        <template v-slot="{ row }">
          <a @click="goUserACStatus(row.username)" style="color:rgb(87, 163, 243);">{{row.ac}}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column field="total" title="Total" min-width="80"></vxe-table-column>
      <vxe-table-column title="Rating" min-width="80">
        <template v-slot="{row}">
          <span>{{getACRate(row.ac,row.total)}}</span>
        </template>
      </vxe-table-column>
    </vxe-table>
    
    <Pagination :total="total" :page-size.sync="limit" :current.sync="page"
                @on-change="getRankData" show-sizer
                @on-page-size-change="getRankData(1)"></Pagination>
    </el-col>
  </el-row>
</template>

<script>
  import api from '@/common/api'
  import Pagination from '@/components/oj/common/Pagination'
  import utils from '@/common/utils'
  import { RULE_TYPE } from '@/common/constants'

  export default {
    name: 'acm-rank',
    components: {
      Pagination
    },
    data () {
      return {
        page: 1,
        limit: 30,
        total: 0,
        loadingTable: false,
        dataRank: [],
        options: {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['AC', 'Total']
          },
          grid: {
            x: '3%',
            x2: '3%',
            left:'8%',
            right:'8%'
          },
          toolbox: {
            show: true,
            feature: {
              dataView: {show: true, readOnly: true},
              magicType: {show: true, type: ['line', 'bar', 'stack']},
              saveAsImage: {show: true}
            },
            right: '8%',
            top:'5%'
          },
          calculable: true,
          xAxis: [
            {
              type: 'category',
              data: ['root'],
              axisLabel: {
                interval: 0,
                showMinLabel: true,
                showMaxLabel: true,
                align: 'center',
                formatter: (value, index) => {
                  return utils.breakLongWords(value, 13)
                }
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              axisLabel: {
                rotate:50,
                textStyle:{
                  fontSize:'12em',
                },
              }
            }
          ],
          series: [
            {
              name: 'AC',
              type: 'bar',
              data: [0],
              itemStyle: {
                color:'#91c7ae'
              },
              markPoint: {
                data: [
                  {type: 'max', name: 'max'}
                ]
              }
            },
            {
              name: 'Total',
              type: 'bar',
              data: [0],
              itemStyle:{
                color:'#6ab0b8'
              },
              markPoint: {
                data: [
                  {type: 'max', name: 'max'}
                ]
              }
            }
          ]
        }
      }
    },
    mounted () {
      this.getRankData(1)
    },
    methods: {
      getRankData (page) {
        let bar = this.$refs.chart
        bar.showLoading({maskColor: 'rgba(250, 250, 250, 0.8)'})
        this.loadingTable = true
        api.getUserRank(page, this.limit, RULE_TYPE.ACM).then(res => {
          this.loadingTable = false
          if (page === 1) {
            this.changeCharts(res.data.data.records.slice(0, 10))
          }
          this.total = res.data.data.total
          this.dataRank = res.data.data.records
          bar.hideLoading()
        }).catch(() => {
          this.loadingTable = false
          bar.hideLoading()
        })
      },
      changeCharts (rankData) {
        let [usernames, acData, totalData] = [[], [], []]
        rankData.forEach(ele => {
          usernames.push(ele.username)
          acData.push(ele.ac)
          totalData.push(ele.total)
        })
        this.options.xAxis[0].data = usernames
        this.options.series[0].data = acData
        this.options.series[1].data = totalData
      },
      getInfoByUsername(uid,username){
        this.$router.push({
          path: '/user-home',
          query: {uid,username},
        });
      },
      goUserACStatus(username){
        this.$router.push({
          path: '/status',
          query: {username,status:0},
        });
      },
      getACRate(ac,total){
        return utils.getACRate(ac,total)
      }
    },
    computed:{

    }
  }
</script>

<style scoped>
  .echarts {
    margin: 0 auto;
    width: 100%;
    height: 400px;
  }
  @media screen and (max-width: 768px) {
    /deep/.el-card__body {
      padding: 0!important;
    }
  }
</style>