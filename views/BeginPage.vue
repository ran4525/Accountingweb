<template>
<div class="container">
  <div class="left">
    <div>
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);border-radius: 15px;">
        <el-menu :default-openeds="['2']" style="border-radius: 15px">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i><el-link :underline="false">每日账单</el-link></template>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i><el-link :underline="false" @click="getTurn">账单明细</el-link></template>
            <el-menu-item-group>
              <el-menu-item index="2-1">年</el-menu-item>
              <el-menu-item index="2-2">月</el-menu-item>
              <el-menu-item index="2-3">日</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i>设置</template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>
    </div>
  </div>
  <div class="mid">
    <button @click="dialogFormVisible = true" style="background: #67C23A">+每日支出</button>
    
    <el-dialog title="提示" :visible.sync="dialogFormVisible" width="30%">
      <el-form ref="form1" :model="form" label-width="80px">
        <el-form-item label="金额">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.region" placeholder="请选择类型">
            <el-option label="饮食" value="1"></el-option>
            <el-option label="教育" value="2"></el-option>
            <el-option label="交通" value="3"></el-option>
            <el-option label="通讯" value="4"></el-option>
            <el-option label="娱乐" value="5"></el-option>
            <el-option label="其他" value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit1">立即创建</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table
    ref="filterTable"
    :data="tableData2"
    style="width: 100%">
    <el-table-column
      prop="number"
      label="金额"
      width="180">
    </el-table-column>
    <el-table-column
      prop="types"
      label="类型"
      width="100">
      <template slot-scope="scope">
        <el-tag
          :type="scope.row.types === '饮食' ? '通讯' : 'success'"
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
  <div class="right">
    <button @click="dialogVisible = true" style="background: #F56C6C">+每日收入</button>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form2" :model="form" label-width="80px">
        <el-form-item label="金额">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.region" placeholder="请选择类型">
            <el-option label="劳动" value="1"></el-option>
            <el-option label="投资" value="2"></el-option>
            <el-option label="其他" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-table
    ref="filterTable"
    :data="tableData1"
    style="width: 100%">
    <el-table-column
      prop="number"
      label="金额"
      width="180">
    </el-table-column>
    <el-table-column
      prop="types"
      label="类型"
      width="100">
      <template slot-scope="scope">
        <el-tag
          :type="scope.row.types === '投资' ? 'primary' : 'success'"
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
// 创建一个新的 Date 对象来获取当前日期
const today = new Date();

// 获取日期的各个部分
const year = today.getFullYear();
const month = today.getMonth() + 1; // 月份是从0开始的，所以加1
const day = today.getDate();

// 格式化日期
const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

  export default {
    data() {
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth() + 1;
  const day = today.getDate();
  const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

    return {
      dialogVisible: false,
      dialogFormVisible: false,
      form: {
        name: '',  // 金额
        region: '',  // 类型
        desc: '',  // 备注
        date: formattedDate  // 当前日期
      },
      tableData1: [],
      tableData2: []
    };
  },
  mounted() {
    // 页面加载时获取当天的账单数据
    this.getTodayBills();
  },
  methods: {
    async getTurn(){
      this.$router.push({ path: '/page2', query: { username: this.$route.query.username } });
    },
        // 获取当天账单数据
        async getTodayBills() {
          const requestData = {
            name: this.$route.query.username,
            date: formattedDate
          };
      try {
        const response = await axios.post('http://localhost:8080/search', requestData);
        if (response.data.code === 0) {
          this.tableData1 = response.data.data[0];
          this.tableData2 = response.data.data[1];
          console.log(response);
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
      },
    async onSubmit1() {
      const typeMap = {
        1: '饮食',
        2: '教育',
        3: '交通',
        4: '通讯',
        5: '娱乐',
        6: '其他',
      };
      const jsonString = JSON.stringify({
        name: this.$route.query.username,
        type: 'zhichu',  // 支出类型
        number: this.form.name,
        types: typeMap[this.form.region],
        date: formattedDate,
        cex: this.form.desc
      });
      console.log(jsonString);
      try {
        const response = await axios.post('http://localhost:8080/submitForm', jsonString, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log(response.data); // 打印响应内容

        // 根据返回的 code 判断操作
        if (response.data.code === 0) {
          alert('添加成功！');
          window.location.reload()
        } else {
          // 其他错误处理
          alert('添加失败！');
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert('网络错误，请稍后再试');
      }
      },
      async onSubmit() {
        const typeMap = {
        1: '劳动',
        2: '投资',
        3: '其他',
      };
      const jsonString = JSON.stringify({
        name: this.$route.query.username,
        type: 'shouru',
        number: this.form.name,
        types: typeMap[this.form.region],
        date: formattedDate,
        cex: this.form.desc
      });
      
      try {
        const response = await axios.post('http://localhost:8080/submitForm', jsonString, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log(response.data);

        if (response.data.code === 0) {
          alert('添加成功！');
          window.location.reload()
        } else {
          alert('添加失败！');
        }
      } catch (error) {
        console.error("Error during submission:", error);
        alert('网络错误，请稍后再试');
      }
    }
  }
};
  </script>
  
  <style>
  .container {
    width: 100%;
    display: flex;
  }
  .left {
    width: 200px;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    background: #C0C4CC;
    padding: 10px;
    border-radius: 15px;
  }
  .el-header {
      background-color: #B3C0D1;
      color: #333;
      line-height: 60px;
    }
    
    .el-aside {
      color: #333;
    }
  button {
    width: 100%;
    height: 35px;
    color: #fff;
    border: none;
    outline: none;
    border-radius: 5px;
    font-size: 12px;
    cursor: pointer;
  }
  .mid {
    width: 43%;
    min-height: 100vh;
    box-sizing: border-box;
    background-color: white;
    padding: 20px 20px 20px 20px;
    border: 10px solid #C0C4CC;
    border-radius: 15px;
    margin: 0 5px 0 5px;
  }
  .right {
    width: 43%;
    min-height: 100vh;
    box-sizing: border-box;
    background-color: white;
    padding: 20px 20px 20px 20px;
    border: 10px solid #C0C4CC;
    border-radius: 15px;
    margin: 0 5px 0 5px;
  }
  </style>
  