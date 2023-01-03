<template>
  <!--  <el-container>-->
  <!--    <el-header>Header</el-header>-->
  <!--    <el-container>-->
  <!--      <el-aside width="200px">Aside</el-aside>-->
  <!--      <el-main>Main</el-main>-->
  <!--    </el-container>-->
  <!--  </el-container>-->

  <div class="common-layout">
    <el-container>
      <el-header>
        <!--      <span><el-icon><MostlyCloudy /></el-icon>云端文件管理系统</span>-->
        <el-row :gutter="20">
          <el-col :span="17" style="text-align: left; font-size: xx-large; ">
            <!--          <div class="grid-content ep-bg-purple"/>-->
            <el-icon size="xx-large" style="padding-top: 10px">
              <MostlyCloudy/>
            </el-icon>
            <span style="padding-top: 7px">云端文件管理系统</span>

          </el-col>
          <el-col :span="2"></el-col>
          <el-col :span="5" style="text-align: left; font-size: xx-large; align-items: end; height: 50px">
            <el-dropdown
                style="position: relative ; padding-left:5px; height: 50px; text-align: left; font-size: xx-large; align-items: center;">
              <span class="el-dropdown-link">
                <el-avatar style="position: relative ;top: 7px" :size="40" :src="circleUrl"/>
                <span style="position: relative ; padding-left:5px; height: 50px;">{{ currentUser }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu style="width: 200px">
                  <el-dropdown-item :icon="User">个人资料</el-dropdown-item>
                  <el-dropdown-item :icon="Tools">设置</el-dropdown-item>
                  <el-dropdown-item :icon="Shop" disabled>开通会员</el-dropdown-item>
                  <el-dropdown-item divided @click="currentUser=null">切换账户</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: #E8E8E8">
          <el-scrollbar style="background-color: #E8E8E8">
            <el-menu :default-openeds="['1', '6']" style="background-color: #E8E8E8">
              <el-sub-menu index="1">
                <template #title>
                  <el-icon>
                    <Document/>
                  </el-icon>
                  我的文件
                </template>
                <el-menu-item index="1-1" @click="this.TOBACK_get_all_file">全部文件</el-menu-item>
                <el-menu-item index="1-2" @click="this.TOBACK_get_file_by_type('picture')">图片</el-menu-item>
                <el-menu-item index="1-3" @click="this.TOBACK_get_file_by_type('video')">视频</el-menu-item>
                <el-menu-item index="1-4" @click="this.TOBACK_get_file_by_type('music')">音乐</el-menu-item>
                <el-menu-item index="1-5" @click="this.TOBACK_get_file_by_type('document')">文档</el-menu-item>
                <el-menu-item index="1-6" @click="this.TOBACK_get_file_by_type('other')">其他</el-menu-item>
              </el-sub-menu>
              <!--              <el-sub-menu index="2">-->
              <!--                <template #title>-->
              <!--                  <el-icon>-->
              <!--                    <Sort/>-->
              <!--                  </el-icon>-->
              <!--                  上传下载-->
              <!--                </template>-->
              <!--                <el-menu-item index="2-1">正在传输</el-menu-item>-->
              <!--                <el-menu-item index="2-2">下载完成</el-menu-item>-->
              <!--                <el-menu-item index="2-3">上传完成</el-menu-item>-->
              <!--              </el-sub-menu>-->
              <!--              <el-sub-menu index="3">-->
              <!--                <template #title>-->
              <!--                  <el-icon>-->
              <!--                    <Share/>-->
              <!--                  </el-icon>-->
              <!--                  文件分享-->
              <!--                </template>-->
              <!--                <el-menu-item index="3-1">我分享的</el-menu-item>-->
              <!--              </el-sub-menu>-->
            </el-menu>
          </el-scrollbar>
        </el-aside>

        <el-main>
          <!--弹出的登录界面-->
          <el-dialog v-model="loginDialogVisible" title="请先登录" style="align-items:center;" width="500px"
                     :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
            <el-row style="height: 40px; align-items:center; width: 100%;">
              <el-col :span="1" style="height: 40px"></el-col>
              <el-col :span="2" style="height: 40px; padding-top: 10px">账号</el-col>
              <el-col :span="21" style="height: 40px">
                <el-input
                    v-model="input_username"
                    class="m-2"
                    placeholder="输入用户名或手机号"
                    :suffix-icon="User"
                    style="width: 370px; height: 40px"
                />
              </el-col>
            </el-row>
            <el-row style="height: 40px; align-items:center; width: 100%;">
              <el-col :span="1" style="height: 40px"></el-col>
              <el-col :span="2" style="height: 40px; padding-top: 10px">密码</el-col>
              <el-col :span="21" style="height: 40px">
                <el-input
                    v-model="input_password"
                    class="m-2"
                    placeholder="请输入密码"
                    :suffix-icon="Lock"
                    style="width: 370px; height: 40px"
                    show-password
                />
              </el-col>
            </el-row>
            <el-row style="height: 40px; align-items:center; width: 100%;">
              <el-col :span="2" style="height: 40px"></el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="primary" :icon="Select" style="height: 35px; width: 100%" @click="login">登录</el-button>
              </el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="primary" :icon="Pointer" style="height: 35px; width: 100%" @click="registerDialogVisible=true">注册
                </el-button>
              </el-col>
              <el-col :span="2" style="height: 40px"></el-col>
            </el-row>
          </el-dialog>

          <!--          上传弹窗-->
          <el-dialog v-model="uploadDialogVisible" title="文件上传" style="align-items:center;" width="500px">
            <input type="file" class="file" ref="upload">
            <el-button type="primary" @click="submitForm('product_add')">确 定</el-button>
          </el-dialog>

          <!--          新建文件夹弹窗-->
          <el-dialog v-model="newFolderDialogVisible" title="新建文件夹" style="align-items:center;" width="500px">
            <el-input v-model="input_new_folder_name"
                      class="m-2"
                      placeholder="输入文件夹名称"
                      :suffix-icon="User"
                      style="width: 100%; height: 40px"/>
            <el-row style="height: 40px; align-items:center; width: 100%;">
              <el-col :span="2" style="height: 40px"></el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="success" :icon="Select" style="height: 35px; width: 100%" @click="create_folder" plain>
                  创建
                </el-button>
              </el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="danger" :icon="CloseBold" style="height: 35px; width: 100%"
                           @click="newFolderDialogVisible=false" plain>取消
                </el-button>
              </el-col>
              <el-col :span="2" style="height: 40px"></el-col>
            </el-row>
          </el-dialog>


          <!--          注册用户弹窗-->
          <el-dialog v-model="registerDialogVisible" title="注册新用户" style="align-items:center;" width="500px">
            <el-input v-model="input_register_username"
                      class="m-2"
                      placeholder="请输入用户名"
                      :suffix-icon="User"
                      style="width: 100%; height: 40px"/>
            <el-input v-model="input_register_password"
                      class="m-2"
                      placeholder="请输入密码"
                      :suffix-icon="Lock"
                      style="width: 100%; height: 40px"/>
            <el-input v-model="input_register_password_confirm"
                      class="m-2"
                      placeholder="确认密码名"
                      :suffix-icon="Lock"
                      style="width: 100%; height: 40px"/>
            <el-row style="height: 40px; align-items:center; width: 100%;">
              <el-col :span="2" style="height: 40px"></el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="success" :icon="Select" style="height: 35px; width: 100%" @click="register" plain>
                  注册
                </el-button>
              </el-col>
              <el-col :span="10" style="height: 40px; padding: 10px">
                <el-button type="danger" :icon="CloseBold" style="height: 35px; width: 100%"
                           @click="registerDialogVisible=false" plain>取消
                </el-button>
              </el-col>
              <el-col :span="2" style="height: 40px"></el-col>
            </el-row>
          </el-dialog>


          <el-scrollbar>
            <el-row style="background-color: white; height: 50px; align-items:center;">
              <el-col :span="2" style="height: 35px; padding-left: 10px">
                <!--                <el-button-group>-->
                <el-button type="primary" :icon="Upload" style="height: 35px" @click="uploadDialogVisible = true">上传文件
                </el-button>
                <!--/*                  <el-button type="primary" :icon="Download" style="height: 35px">下载文件</el-button>*/-->
                <!--                </el-button-group>-->
              </el-col>
              <el-col :span="2" style="height: 35px">
                <el-button type="primary" :icon="FolderAdd" style="height: 35px" @click="newFolderDialogVisible=true">
                  新建文件夹
                </el-button>
              </el-col>
              <el-col :span="15" style="height: 35px">
                <el-button-group>
                  <el-button type="primary" :icon="Download" style="height: 35px" @click="batch_download">批量下载
                  </el-button>
                  <el-button type="primary" :icon="Delete" style="height: 35px" @click="confirm_delete_files">批量删除
                  </el-button>
                  <el-button type="primary" :icon="Share" style="height: 35px" @click="batch_share">批量分享</el-button>
                </el-button-group>
              </el-col>
              <el-col :span="4" style="height: 35px">
                <el-input
                    v-model="input_search_file"
                    class="w-50 m-4"
                    placeholder="搜索文件"
                    :prefix-icon="Search"
                    style="height: 35px"
                    @keyup="search_file"
                />
              </el-col>
              <el-col :span="1" style="height: 35px; padding-left: 10px">
                <el-button type="primary" :icon="Search" style="height: 35px; width: 35px" circle @click="search_file"/>
              </el-col>
            </el-row>
            <el-row style="background-color: white; height: 30px; ">
              <el-col :span="1"></el-col>
              <el-col :span="1">
                <el-button size="small" id="path_back_button" @click="path_back" :icon="ArrowLeft"/>
              </el-col>
              <el-col :span="22">{{ currentPath }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-table
                    :data="tableData"
                    @selection-change="handleSelectionChange"
                    :default-sort="{ prop: 'filename', order: 'descending' }"
                    stripe
                    @cell-click="click_on_filename"
                    style="overflow: auto; height: 75vh">
                  <el-backtop :bottom="100">
                    <div style="
                          height: 100%;
                          width: 100%;
                          background-color: var(--el-bg-color-overlay);
                          box-shadow: var(--el-box-shadow-lighter);
                          text-align: center;
                          line-height: 40px;
                          color: #1989fa;">UP
                    </div>
                  </el-backtop>
                  <el-table-column type="selection" width="55"/>
                  <el-table-column prop="filename" label="文件名" width="600" sortable/>
                  <el-table-column prop="size" label="大小" width="120" sortable :formatter="sizeformatter"/>
                  <el-table-column prop="date" label="修改日期" sortable/>
                  <el-table-column label="">
                    <template #default="scope">
                      <el-button size="small" @click="single_file_download(scope.row.fileID, scope.row.filename)"
                                 :icon="Download"/>
                      <el-button size="small" @click="single_file_share(scope.row.fileID)" :icon="Share"/>
                      <el-button size="small" @click="single_file_delete(scope.row.fileID)" :icon="Delete"/>
                      <el-button size="small" @click="single_file_rename(scope.row.fileID)" :icon="Edit"/>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import axios from "axios";
// import { InfoFilled } from '@element-plus/icons-vue'

export default {
  props: {
    "num": {type: Number, default: -1}
  },
  components: {},
  mounted() {
  },
  data() {
    return {
      currentUser: null,//用户uid
      input_password: null,//输入的密码
      input_username: null,//输入的用户名
      input_register_password_confirm: null,//注册用户的确认密码
      input_register_password: null,//注册用户的密码
      input_register_username: null,//注册用户的用户名
      input_new_folder_name: null,//输入的文件夹名
      input_search_file: null,//输入的搜索文件关键词
      input_file_type: 'all-file',//输入的查看的文件类型
      loginDialogVisible: ref(false),//登录弹窗是否可见
      uploadDialogVisible: ref(false),//上传弹窗是否可见
      shareDialogVisible: ref(false),//分享弹窗是否可见
      newFolderDialogVisible: ref(false),//新建文件夹弹窗是否可见
      renameDialogVisible: ref(false),//重命名弹窗是否可见
      registerDialogVisible: ref(false),//注册弹窗是否可见
      currentPath: '/home',//当前目录
      formLabelWidth: '140px',
      form: reactive({
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      }),
      tableData: [
        {
          fileID: 113,
          filename: 'home',
          size: '989845444',
          date: '2016-05-03',
          type: 'folder'
        },
        {
          fileID: 114,
          filename: 'testDir',
          size: '2321323',
          date: '2016-05-03',
          type: 'folder'
        },
        {
          fileID: 115,
          filename: 'testFile.doc',
          size: '212132233',
          date: '2016-05-03',
          type: 'file'
        },
      ],
      multipleSelection: [],
      paths: ['home'],
    }
  },
  methods: { // 方法

    submitForm() {
      let myfile = this.$refs.upload
      let files = myfile.files
      let file = files[0]
      let formdata = new FormData()    //formdata格式
      formdata.append('belongs', this.currentUser) //其他参数
      formdata.append('path', this.currentPath)    //其他参数
      formdata.append("file", file)  //图片文件
      // 发送请求
      axios({
        method: 'POST',
        data: formdata,
        url: 'http://localhost:8888/files/upload',
        headers: {'Content-Type': 'multipart/form-data'}
      }).then((res) => {
        console.log(res)
        if (res.data == "2") {
          ElMessage({
            message: '上传失败',
            type: 'error',
          });
        } else {
          ElMessage({
            message: '上传成功：' + file,
            type: 'success',
          });
        }
        this.TOBACK_get_all_file();
        // this.initProduct()
        // if(res.data.code == 0){
        //   this.$message({
        //     message: '添加产品成功！',
        //     type: 'success'
        //   });
        // }else{
        //   this.$message.error('添加产品失败！');
        // }
      })
    },
    //要求后端删除files里的文件
    TOBACK_delete_file(fileID) {
      console.log('后端删除:' + fileID)
      axios.delete("http://localhost:8888/files/" + fileID).then((res) => {
        console.log(res);
      }).finally(() => {
        this.TOBACK_get_all_file();
      });
    },

    TOBACK_download_file(fileID, filename) {
      let formData = new FormData();
      formData.append('fileId', fileID); // 用于后端下载文件的路径
      axios
          .post('http://localhost:8888/files/download', formData, {
            responseType: 'blob'
          })
          .then(response => {
            console.log(response);
            if (response.status == 200) {
              console.log(response)
              let url = window.URL.createObjectURL(new Blob([response.data])) // 将获取的文件转化为blob格式
              let a = document.createElement('a'); // 此处向下是打开一个储存位置
              a.style.display = 'none';
              a.href = url;
              // 下面两行是自己项目需要的处理，总之就是得到下载的文件名（加后缀）即可
              a.setAttribute('download', filename);
              document.body.appendChild(a);
              a.click();//点击下载
              document.body.removeChild(a);// 下载完成移除元素
              window.URL.revokeObjectURL(url);// 释放掉blob对象
              ElMessage({
                message: '下载完成',
                type: 'success',
              });
            } else {
              ElMessage({
                message: '下载失败',
                type: 'error',
              });
            }
          })
    },

    TOBACK_share_file(fileID) {
      let formData = new FormData();
      formData.append('fileId', fileID); // 用于后端下载文件的路径
      axios
          .post('http://localhost:8888/files/download', formData, {
            responseType: 'blob'
          })
          .then(response => {
            console.log(response);
            if (response.status == 200) {
              console.log(response)
              let url = window.URL.createObjectURL(new Blob([response.data])) // 将获取的文件转化为blob格式
              ElNotification({
                title: '分享成功',
                message: '分享链接为：' + url,
                type: 'success',
                duration: 0,
              })
              //window.URL.revokeObjectURL(url);// 释放掉blob对象
            } else {
              ElMessage({
                message: '分析失败',
                type: 'error',
              });
            }
          })
    },

    TOBACK_rename_file(fileID, new_name) {
      console.log('后端重命名:' + fileID + " " + new_name);
      let params = new URLSearchParams();
      params.append("fileID", fileID);
      params.append("new_name", new_name);
      axios
          .put("http://localhost:8888/files/",
              params)
          .then((res) => {
            console.log(res);
          }).finally(() => {
        this.TOBACK_get_all_file();
      });
    },

    TOBACK_new_folder(folder_name) {
      console.log('后端创建文件夹:' + this.currentPath + " " + folder_name + "" + this.currentUser);
      let params = new URLSearchParams();
      params.append("folder_name", folder_name);
      params.append("path", this.currentPath);
      params.append("belongs", this.currentUser);
      axios
          .post("http://localhost:8888/files/newfolder",
              params)
          .then((res) => {
            console.log(res);
          }).finally(() => {
        this.TOBACK_get_all_file();
      });
    },

    TOBACK_search_file(keyword) {
      console.log('后端搜索文件:' + keyword);
      let params = new URLSearchParams();
      params.append("filename", keyword);
      params.append("userId", this.currentUser);
      axios
          .post("http://localhost:8888/files/search",
              params)
          .then((res) => {
            console.log(res);
            this.tableData = [];
            for (var i = 0; i < res.data.data.length; i++) {
              var li = {
                fileID: res.data.data[i].id,
                filename: res.data.data[i].name,
                size: res.data.data[i].size,
                date: res.data.data[i].lastmodify,
                type: res.data.data[i].type,
              }
              this.tableData.push(li);
            }
          });
    },

    TOBACK_get_all_file() {
      console.log('后端获取目录下全部文件:');
      // axios
      //     .post("http://localhost:8888/files/getpath",
      //         {
      //           path: 'home',
      //           belongs: 1,
      //         })
      //     .then((res)=>{
      //       console.log(res.data);
      //       this.tableData = [];
      //       for (var i = 0; i < res.data.length; i++){
      //         var li = {
      //           fileID: res.data[i].id,
      //           filename: res.data[i].name,
      //           size: res.data[i].size,
      //           date: res.data[i].lastmodify,
      //         }
      //         this.tableData.push(li);
      //       }
      //     })
      let params = new URLSearchParams();
      params.append("path", this.currentPath);
      params.append("belongs", this.currentUser);
      axios({
        method: 'post',//不写此参数默认为get
        url: 'http://localhost:8888/files/getpath',
        params
      }).then(res => {
        console.log(res.data.data);
        this.tableData = [];
        for (var i = 0; i < res.data.data.length; i++) {
          var li = {
            fileID: res.data.data[i].id,
            filename: res.data.data[i].name,
            size: res.data.data[i].size,
            date: res.data.data[i].lastmodify,
            type: res.data.data[i].type,
          }
          this.tableData.push(li);
        }
      })

    },

    TOBACK_get_file_by_type(type) {
      // type = video, music, document, other, all-file
      console.log('后端获取目录下全部文件:' + type);
      let params = new URLSearchParams();
      params.append("type", type);
      params.append("belongs", this.currentUser);
      axios({
        method: 'post',//不写此参数默认为get
        url: 'http://localhost:8888/files/gettype',
        params
      }).then(res => {
        console.log(res.data.data);
        this.tableData = [];
        for (var i = 0; i < res.data.data.length; i++) {
          var li = {
            fileID: res.data.data[i].id,
            filename: res.data.data[i].name,
            size: res.data.data[i].size,
            date: res.data.data[i].lastmodify,
            type: res.data.data[i].type,
          }
          this.tableData.push(li);
        }
      })
    },


    sizeformatter(row) {
      if (row.type === "folder") {
        row.size = -100; //确保排序时排在前面
        return "-"
      }
      if (row.size > 1024 * 1024) return (row.size / 1024.0 / 1024.0).toFixed(2) + "MB"
      else if (row.size > 1024) return (row.size / 1024.0).toFixed(2) + "KB"
      else return (row.size).toFixed(2) + "B"
    },
    batch_share() {//批量分享
      if (this.multipleSelection.length === 0) {
        ElMessage({
          type: 'warning',
          message: '未选择文件',
        })
        return;
      }
      this.multipleSelection.forEach(item => {
        if (item != null) {
          for (let i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].fileID === item) {
              clearTimeout(this.timer);  //清除延迟执行
              this.timer = setTimeout(() => {   //设置延迟执行
                this.single_file_share(this.tableData[i].fileID)
              }, 1000);
            }
          }
        }
      })
    },
    batch_download() {//批量下载
      if (this.multipleSelection.length === 0) {
        ElMessage({
          type: 'warning',
          message: '未选择文件',
        })
        return;
      }
      let download_list = '';
      this.multipleSelection.forEach(item => {
        if (item != null) {
          for (let i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].fileID === item) {
              download_list += this.tableData[i].filename + ";  \n"
              clearTimeout(this.timer);  //清除延迟执行
              this.timer = setTimeout(() => {   //设置延迟执行
                this.single_file_download(this.tableData[i].fileID, this.tableData[i].filename)
              }, 100);
              //this.single_file_download(this.tableData[i].fileID, this.tableData[i].filename)
            }
          }
        }
      })
      ElMessage({
        type: 'success',
        message: '下载成功，下载文件列表：' + download_list,
      })

    },
    confirm_delete_files() { // 确认删除
      ElMessageBox.confirm(
          '文件删除后将放入回收站，30天内可找回，是否确认删除？',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        this.multipleSelection.forEach(item => {
          this.TOBACK_delete_file(item);
        })
        ElMessage({
          type: 'success',
          message: '成功删除了' + this.multipleSelection.length + '文件',
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
    },
    login() {//登录
      this.loginDialogVisible = false;
      this.currentUser = this.input_username;
      let params = new URLSearchParams();
      params.append("username", this.input_username);
      params.append("password", this.input_password);
      axios
          .post("http://localhost:8888/login",
              params)
          .then((res) => {
            console.log(res.data);
            if (res.data == false) {
              ElMessage({
                message: '账号或密码错误',
                type: 'error',
              });
            }
          });
      this.TOBACK_get_all_file();
      //QUACK
      ElMessage({
        message: '登录成功：' + this.currentUser + '，欢迎回来',
        type: 'success',
      });
    },
    create_folder() {
      this.newFolderDialogVisible = false;
      if (this.input_new_folder_name == null) {
        ElMessage({
          message: '创建失败：文件夹名不可为空',
          type: 'error',
        });
      } else {
        this.TOBACK_new_folder(this.input_new_folder_name);
        ElMessage({
          message: '创建文件夹成功：' + this.input_new_folder_name,
          type: 'success',
        });
      }
    },
    search_file() {//搜索文件
      this.TOBACK_search_file(this.input_search_file);
    },
    handleSelectionChange(ls) {
      this.multipleSelection = [];
      ls.forEach(item => {
        this.multipleSelection.push(item.fileID)
      })
    },
    single_file_delete(fileID) {
      ElMessageBox.confirm(
          '文件删除后将放入回收站，30天内可找回，是否确认删除？',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        this.TOBACK_delete_file(fileID);
        ElMessage({
          type: 'success',
          message: '成功删除了文件',
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
    },
    single_file_rename(fileID) {
      ElMessageBox.prompt('请输入新的文件名称', 'Tip', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        inputPattern:
            /\S/,
        inputErrorMessage: '不可包含非法字符或为空',
      }).then(({value}) => {
        let new_name = `${value}`;
        this.TOBACK_rename_file(fileID, new_name);
        ElMessage({
          type: 'success',
          message: new_name + '重命名成功',
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消操作',
        })
      })
    },
    single_file_share(fileID) {//分享单个文件
      this.TOBACK_share_file(fileID)
    },
    single_file_download(fileID, filename) {
      this.TOBACK_download_file(fileID, filename);
    },
    // eslint-disable-next-line no-unused-vars
    click_on_filename(row, column, cell, event) {

      if (column.label === "文件名" && row.type === "folder") {
        // alert(column.label)
        this.paths.push(row.filename)
        this.update_currentPath()
        // console.log(this.paths)
        let params = new URLSearchParams();
        params.append("path", this.currentPath);
        params.append("belongs", this.currentUser);
        axios({
          method: 'post',//不写此参数默认为get
          url: 'http://localhost:8888/files/getpath',
          params
        }).then(res => {
          console.log(res.data.data);
          this.tableData = [];
          for (var i = 0; i < res.data.data.length; i++) {
            var li = {
              fileID: res.data.data[i].id,
              filename: res.data.data[i].name,
              size: res.data.data[i].size,
              date: res.data.data[i].lastmodify,
              type: res.data.data[i].type,
            }
            this.tableData.push(li);
          }
        })
      }
    },
    update_currentPath() {
      // console.log("changed!!")
      this.currentPath = "/" + this.paths[0];
      for (let i = 1; i < this.paths.length; i++) {
        this.currentPath += '/' + this.paths[i];
      }
      // if(this.currentPath==="/home") document.getElementById('path_back_button').disabled = true
      // else
    },
    path_back() {
      if (this.currentPath === "/home") {
        return
      }
      this.paths.pop()
      this.update_currentPath()
      let params = new URLSearchParams();
      params.append("path", this.currentPath);
      params.append("belongs", this.currentUser);
      axios({
        method: 'post',//不写此参数默认为get
        url: 'http://localhost:8888/files/getpath',
        params
      }).then(res => {
        console.log(res.data.data);
        this.tableData = [];
        for (var i = 0; i < res.data.data.length; i++) {
          var li = {
            fileID: res.data.data[i].id,
            filename: res.data.data[i].name,
            size: res.data.data[i].size,
            date: res.data.data[i].lastmodify,
            type: res.data.data[i].type,
          }
          this.tableData.push(li);
        }
      })
    },
    register() {
      // this.input_register_username
      // this.input_register_password
      // this.input_register_password_confirm

      // 注册成功后:
      // this.registerDialogVisible = false
      // this.input_username = this.input_register_username
      // this.input_password = this.input_register_password

    },
  },
  watch: { // 监听器
    // eslint-disable-next-line no-unused-vars
    currentUser: function (newData, oldData) {
      if (newData == null) this.loginDialogVisible = true;
    },
    // eslint-disable-next-line no-unused-vars
    input_file_type: function (newData, oldData) {
      this.TOBACK_get_file_by_type(newData)
    },
  },
  comments: {},
  created() {
    this.loginDialogVisible = true
  }
}
</script>

<script setup>
import {
  Search,
  Upload,
  Download,
  FolderAdd,
  Share,
  MostlyCloudy,
  // Sort,
  CloseBold,
  Document,
  Delete,
  Shop,
  Tools,
  User,
  Lock,
  Select,
  Pointer,
  // UploadFilled,
  Edit,
  ArrowLeft,
} from '@element-plus/icons-vue'

import {ElTable} from 'element-plus'

// import { TableColumnCtx } from 'element-plus'
</script>

<style>
/*.el-button--text {*/
/*  margin-right: 15px;*/
/*}*/
/*.el-select {*/
/*  width: 300px;*/
/*}*/
/*.el-input {*/
/*  width: 300px;*/
/*}*/
.dialog-footer button:first-child {
  margin-right: 10px;
}

.el-menu-item {
  background-color: #E8E8E8;
}

.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.el-header {
  position: relative;
  /*height: 50px;*/
  /*background-color: var(--el-color-primary-light-5);*/
  /*background-color: white;*/
  color: var(--el-text-color-primary);
  box-shadow: var(--el-box-shadow-lighter);
  border-bottom: grey;
}

.el-main {
  overflow: auto;
  height: 85%;
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  background-color: white;
  /*right: 20px;*/
}
</style>
