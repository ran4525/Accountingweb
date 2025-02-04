<template>
  <div class="container">
    <div class="left">
    <div>
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);border-radius: 15px;">
        <el-menu :default-openeds="['2']" style="border-radius: 15px">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i><el-link :underline="false" @click="getTurn">每日账单</el-link></template>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i><el-link :underline="false">账单明细</el-link></template>
          </el-submenu>
        </el-menu>
      </el-aside>
    </div>
  </div>
  <div class="right" style="width: 100%">
<el-input placeholder="请输入日期xxxx-xx-xx" v-model="input" style="width: 20%" class="input-with-select">
    <el-button slot="append" icon="el-icon-search" @click="getTodayBills"></el-button>
  </el-input>
  <el-table
    ref="filterTable"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="date"
      label="日期"
      sortable
      width="180"
      column-key="date"
    >
    </el-table-column>
    <el-table-column
      prop="number"
      label="金额"
      width="180">
    </el-table-column>
    <el-table-column
      prop="types"
      label="类型"
      width="100"
      :filters="[{ text: '教育', value: '教育' }, { text: '公司', value: '公司' }]"
      :filter-method="filterTag"
      filter-placement="bottom-end">
      <template slot-scope="scope">
        <el-tag
          :type="scope.row.types === '家' ? 'primary' : 'success'"
          disable-transitions>{{scope.row.types}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column
      prop="cex"
      label="备注"
      :formatter="formatter">
    </el-table-column>

  </el-table>
  </div>
  </div>

</template>

<script>
import axios from 'axios';
export default {
  data() {
  return {
    tableData: [],
    input: ''
  }
},

methods: {
  async getTurn(){
      this.$router.push({ path: '/page1', query: { username: this.$route.query.username } });
    },
  async getTodayBills() {
          const requestData = {
            name: this.$route.query.username,
            date: this.input
          };
      try {
        const response = await axios.post('http://localhost:8080/search', requestData);
        if (response.data.code === 0) {
          this.tableData = response.data.data[0].concat(response.data.data[1]);
        } else {
          alert('获取账单失败');
        }
      } catch (error) {
        console.error('Error fetching today bills:', error);
        alert('网络错误，请稍后再试');
      }
    },
  formatter(row, column) {
    const property = column['property'];
    if(row[property] === ""){
      return row.address;
    }
    return row.address;
  },
  filterTag(value, row) {
    return row.tag === value;
  },
  filterHandler(value, row, column) {
    const property = column['property'];
    return row[property] === value;
  }
}
};
</script>
  
  <style>
    .container {
    display: flex;
    width: 100%;
    height: 100%;
  }
    .el-header {
      background-color: #B3C0D1;
      color: #333;
      line-height: 60px;
    }
    .left {
    width: 200px;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    background: #C0C4CC;
    background-size: cover;
    padding: 10px;
    border-radius: 15px;
  }
    .right {
    width: 100%;
    min-height: 100vh;
    box-sizing: border-box;
    background-color: white;
    padding: 20px 20px 20px 20px;
    border: 10px solid #C0C4CC;
    border-radius: 15px;
    margin: 0 5px 0 5px;
  }
    .el-aside {
      color: #333;
    }
  </style>
