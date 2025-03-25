<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search-container">
      <!-- 选择音乐 -->
      <a-input
        v-model:value="username"
        placeholder="请输入用户名"
        class="search-item"
        style="width: 200px"
      />
      <!-- 选择声音 -->
      <a-select
        v-model:value="selectedRole"
        placeholder="请选择角色"
        class="search-item"
        style="width: 200px"
        :options="roleOptions"
      />
      <a-select
        v-model:value="selectedDisable"
        placeholder="请选择是否禁用"
        class="search-item"
        style="width: 200px"
        :options="disableOptions"
      />
      <!-- 选择时间范围 -->
      <a-range-picker class="search-item" v-model:value="selectedTimeRange" />
      <!-- 搜索按钮 -->
      <a-button type="primary" @click="handleSearch">搜索</a-button>
      <!-- 重置按钮 -->
      <a-button @click="handleReset">重置</a-button>
    </div>

    <!-- 分割线 -->
    <a-divider />

    <!-- 新增按钮 -->
    <div class="add-button-container">
      <a-button type="primary" @click="handleAdd">新增</a-button>
    </div>

    <a-modal v-model:open="open" :title="formTitle" @ok="handleOk">
      <a-form :model="userCreateReqVO" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="用户名">
          <a-input v-model:value="userCreateReqVO.username" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="userCreateReqVO.password" />
        </a-form-item>
        <a-form-item label="角色">
          <a-radio-group v-model:value="userCreateReqVO.role">
            <a-radio :value="0">用户</a-radio>
            <a-radio :value="1">管理员</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="头像">
          <a-upload
            v-model:file-list="fileList"
            name="file"
            list-type="picture-card"
            class="avatar-uploader"
            :headers="headers"
            :show-upload-list="false"
            :action="uploadUrl"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <img
              v-if="userCreateReqVO.avatarUrl"
              :src="urlApi + userCreateReqVO.avatarUrl"
              alt="avatar"
            />
            <div v-else>
              <loading-outlined v-if="loading"></loading-outlined>
              <plus-outlined v-else></plus-outlined>
              <div class="ant-upload-text">选择图片</div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item label="是否禁用">
          <a-radio-group v-model:value="userCreateReqVO.isDisable">
            <a-radio :value="0">否</a-radio>
            <a-radio :value="1">是</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
    <a-table
      :columns="columns"
      :data-source="data"
      :pagination="pagination"
      :loading="loading"
      align="center"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'avatarUrl'">
          <a-image
            :width="50"
            :src="urlApi + record.avatarUrl"
            fallback="data:image/png;base64,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"
          />
        </template>
        <template v-if="column.key === 'action'">
          <span>
            <a @click="handleEdit(record.id)">编辑</a>
            <a-divider type="vertical" />
            <a @click="handleDelete(record.id)">删除</a>
          </span>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { OpenAPI, UserControllerService } from '@/api'
import type { UserPageReqVO, AudioRespVO, UserRespVO, UserCreateReqVO } from '@/api'
import { computed, createVNode, onMounted } from 'vue'
import { message, Modal, type UploadChangeParam, type UploadFile } from 'ant-design-vue'
import { ref } from 'vue'
import dayjs from 'dayjs'

const columns = [
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    align: 'center',
  },
  {
    title: '角色',
    key: 'role',
    align: 'center',
    customRender: ({ record }: { record: UserRespVO }) =>
      roleOptions.filter((option) => option.value == record?.role).map((option) => option.label)[0],
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    key: 'avatarUrl',
    align: 'center',
  },
  {
    title: '是否禁用',
    key: 'isDisable',
    align: 'center',
    customRender: ({ record }: { record: UserRespVO }) =>
      disableOptions
        .filter((option) => option.value == record?.isDisable)
        .map((option) => option.label)[0],
  },
  {
    title: '创建时间',
    key: 'createTime',
    dataIndex: 'createTime',
    align: 'center',
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: '150px',
  },
]

const data = ref<Array<AudioRespVO>>([])
const loading = ref(true)
const total = ref<number | undefined>(0)
const current = ref<number | undefined>(1)
const pageSize = ref<number | undefined>(5)

const pagination = computed(() => ({
  total: total.value,
  showTotal: (total: number) => `共 ${total} 条`,
  current: current.value,
  pageSize: pageSize.value,
  showSizeChanger: true,
  pageSizeOptions: ['5', '10', '20', '30'],
  locale: {
    items_per_page: '条/页', // 自定义每页条数文案
  },
}))

let reqVO: UserPageReqVO = {
  pageNo: current.value,
  pageSize: pageSize.value,
}

const getReqData = () => {
  loading.value = true
  console.log(reqVO)
  // 清空旧数据
  data.value = []
  UserControllerService.getPage(reqVO).then(
    (res) => {
      loading.value = false
      if (res.code == 0 && res.data != null) {
        current.value = res.data.current
        total.value = res.data.total
        if (res.data.records != null) {
          data.value.push(...res.data.records)
        }
      } else {
        message.error(res.code)
      }
    },
    (err) => {
      loading.value = false
      message.error(err.message)
    },
  )
}
const handleTableChange = (
  pag: { pageSize: number; current: number },
  // filters: any,
  // sorter: any,
) => {
  if (pag.current != current.value || pag.pageSize != pageSize.value) {
    current.value = pag.current
    pageSize.value = pag.pageSize
    reqVO.pageNo = pag.current
    reqVO.pageSize = pag.pageSize
  }
  getReqData()
}

const username = ref<string | null>(null)
const selectedRole = ref<number | null>(null)
const selectedDisable = ref<number | null>(null)
const selectedTimeRange = ref<number[]>([])
const roleOptions = [
  {
    label: '用户',
    value: 0,
  },
  {
    label: '管理员',
    value: 1,
  },
]
const disableOptions = [
  {
    label: '否',
    value: 0,
  },
  {
    label: '是',
    value: 1,
  },
]
// 重置事件
const handleReset = () => {
  username.value = null
  selectedRole.value = null
  selectedDisable.value = null
  selectedTimeRange.value = []
  pagination.value.current = 1
  handleSearch()
}
// 搜索事件
const handleSearch = () => {
  reqVO = {}
  reqVO.pageNo = pagination.value.current
  reqVO.pageSize = pagination.value.pageSize
  reqVO.username = username.value ?? undefined
  reqVO.role = selectedRole.value ?? undefined
  reqVO.isDisable = selectedDisable.value ?? undefined
  reqVO.createTime = selectedTimeRange.value.map((item) =>
    dayjs(item).format(`YYYY-MM-DDTHH:mm:ss`),
  )
  getReqData()
}

// 初始化数据
onMounted(() => {
  getReqData()
})

import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
const open = ref(false)
const formTitle = ref<string>('新增')
const labelCol = { style: { width: '100px' } }
const wrapperCol = { span: 17 }
const userCreateReqVO = ref<UserCreateReqVO>({
  username: undefined,
  password: undefined,
  isDisable: undefined,
  role: undefined,
  avatarUrl: undefined,
})
const fileList = ref<Array<UploadFile>>([])
const urlApi = OpenAPI.BASE
const uploadUrl = urlApi + '/ai-file/upload'
const headers = OpenAPI.HEADERS
const beforeUpload = (file: File) => {
  fileList.value = []
  // 如果已经有文件，阻止上传
  // if (fileList.value.length >= 1) {
  //   message.warning('只能上传一个文件！')
  //   return false
  // }
  // 文件类型和大小校验
  // const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  // if (!isJpgOrPng) {
  //   message.error('只能上传 JPG/PNG 文件!');
  //   return false;
  // }
  const isLt2M = file.size / 1024 / 1024 <= 16
  if (!isLt2M) {
    message.error('文件大小不能超过 16MB!')
    return false
  }
  return true
}
// 处理上传状态变化
const handleChange = (info: UploadChangeParam) => {
  // 更新文件列表
  fileList.value = info.fileList
  if (info.file.status === 'done' && info.file.response.code === 0) {
    userCreateReqVO.value.avatarUrl = info.file.response.data
    message.success(`${info.file.name} 文件上传成功`)
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`)
  }
}
const handleOk = () => {
  // 提交后端
  if (formTitle.value == '新增') {
    UserControllerService.addUser(userCreateReqVO.value).then((res) => {
      if (res.code == 0) {
        message.success('新增成功')
        open.value = false
        getReqData()
      } else {
        open.value = false
        message.error(res.code)
      }
    })
  } else {
    UserControllerService.updateUser(userCreateReqVO.value).then((res) => {
      if (res.code == 0) {
        message.success('编辑成功')
        open.value = false
        getReqData()
      } else {
        open.value = false
        message.error(res.code)
      }
    })
  }
}
// 获取用户信息
const getUserInfo = (id: number) => {
  UserControllerService.getUser(id).then((res) => {
    if (res.code == 0 && res.data != null) {
      userCreateReqVO.value = res.data
    } else {
      message.error(res.code)
    }
  })
}
// 新增
const handleAdd = () => {
  formTitle.value = '新增'
  open.value = true
  userCreateReqVO.value = {}
  fileList.value = []
}
// 编辑
const handleEdit = (id: number) => {
  formTitle.value = '编辑'
  open.value = true
  getUserInfo(id)
  userCreateReqVO.value = {}
  fileList.value = []
}
// 删除
const handleDelete = (id: number) => {
  Modal.confirm({
    title: '您确定要删除这个条记录吗?',
    icon: createVNode(ExclamationCircleOutlined),
    // content: 'Some descriptions',
    okText: '是',
    okType: 'danger',
    cancelText: '否',
    onOk() {
      UserControllerService.deleteUser(id).then(
        (res) => {
          if (res.code == 0) {
            message.success('删除成功')
            getReqData()
          } else {
            message.error(res.message)
          }
        },
        (err) => {
          message.error(err.message)
        },
      )
      console.log('OK')
    },
    onCancel() {
      console.log('Cancel')
    },
  })
}
</script>

<style scoped>
/* 搜索栏容器 */
.search-container {
  display: flex;
  flex-wrap: wrap; /* 允许换行 */
  gap: 10px; /* 设置组件之间的间距 */
  margin-bottom: 16px;
}
/* 搜索组件 */
.search-item {
  margin-bottom: 10px; /* 确保换行时下方有 10px 的间隔 */
}
/* 新增按钮容器 */
.add-button-container {
  margin-bottom: 16px;
}

.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
