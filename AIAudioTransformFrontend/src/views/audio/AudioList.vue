<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search-container">
      <!-- 选择音乐 -->
      <a-input
        v-model:value="audioName"
        placeholder="输入音频名称"
        class="search-item"
        style="width: 200px"
      />
      <!-- 选择声音 -->
      <a-select
        v-model:value="selectedType"
        placeholder="请选择类型"
        class="search-item"
        style="width: 200px"
        :options="typeOptions"
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
      <a-form :model="audioBaseVO" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="音频名称">
          <a-input v-model:value="audioBaseVO.audioName" />
        </a-form-item>
        <a-form-item label="上传音频">
          <a-upload
            v-model:file-list="fileList"
            name="file"
            :action="uploadUrl"
            :headers="headers"
            :before-upload="beforeUpload"
            @change="handleChange"
            :multiple="false"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              上传文件
            </a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="类型">
          <a-radio-group v-model:value="audioBaseVO.type">
            <a-radio :value="0">声音</a-radio>
            <a-radio :value="1">音乐</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="audioBaseVO.remark" />
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
        <template v-if="column.key === 'audioUrl'">
          <audio controls>
            <source :src="urlApi + record.audioUrl" type="audio/mpeg" />
          </audio>
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
import { AudioControllerService } from '@/api'
import type { AudioPageReqVO, AudioRespVO } from '@/api'
import { computed, createVNode, onMounted } from 'vue'
import { message, Modal, type UploadChangeParam, type UploadFile } from 'ant-design-vue'
import { ref } from 'vue'
import dayjs from 'dayjs'
import { ExclamationCircleOutlined, UploadOutlined } from '@ant-design/icons-vue'

const columns = [
  {
    title: '音频名称',
    dataIndex: 'audioName',
    key: 'audioName',
    align: 'center',
  },
  {
    title: '音频',
    dataIndex: 'audioUrl',
    key: 'audioUrl',
    align: 'center',
  },
  {
    title: '类型',
    key: 'type',
    align: 'center',
    customRender: ({ record }: { record: AudioRespVO }) =>
      typeOptions.filter((option) => option.value == record?.type).map((option) => option.label)[0],
  },
  {
    title: '创建时间',
    key: 'createTime',
    dataIndex: 'createTime',
    align: 'center',
  },
  {
    title: '备注',
    key: 'remark',
    dataIndex: 'remark',
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

let reqVO: AudioPageReqVO = {
  pageNo: current.value,
  pageSize: pageSize.value,
}

const getReqData = () => {
  loading.value = true
  // 清空旧数据
  data.value = []
  AudioControllerService.getPage1(reqVO).then(
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

const audioName = ref<string | null>(null)
const selectedType = ref<number | null>(null)
const selectedTimeRange = ref<number[]>([])
const typeOptions = [
  {
    label: '音乐',
    value: 0,
  },
  {
    label: '声音',
    value: 1,
  },
]
// 重置事件
const handleReset = () => {
  audioName.value = null
  selectedType.value = null
  selectedTimeRange.value = []
  pagination.value.current = 1
  handleSearch()
}
// 搜索事件
const handleSearch = () => {
  reqVO = {}
  reqVO.pageNo = pagination.value.current
  reqVO.pageSize = pagination.value.pageSize
  reqVO.audioName = audioName.value ?? undefined
  reqVO.type = selectedType.value ?? undefined
  reqVO.createTime = selectedTimeRange.value.map((item) =>
    dayjs(item).format(`YYYY-MM-DDTHH:mm:ss`),
  )
  getReqData()
}

// 初始化数据
onMounted(() => {
  getReqData()
})

import { OpenAPI } from '@/api'
const audioBaseVO = ref<AudioRespVO>({
  audioName: undefined,
  audioUrl: undefined,
  type: undefined,
  remark: undefined,
})
const urlApi = OpenAPI.BASE
const uploadUrl = urlApi + '/ai-file/upload'
const headers = OpenAPI.HEADERS
const fileList = ref<Array<UploadFile>>([])
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
    audioBaseVO.value.audioUrl = info.file.response.data
    message.success(`${info.file.name} 文件上传成功`)
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`)
  }
}

const formTitle = ref<string>('新增')
// 新增
const labelCol = { style: { width: '100px' } }
const wrapperCol = { span: 17 }
const open = ref(false)
const handleOk = () => {
  // 提交后端
  if (formTitle.value == '新增') {
    AudioControllerService.addAudio(audioBaseVO.value).then((res) => {
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
    AudioControllerService.updateAudio(audioBaseVO.value).then((res) => {
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
const getAudioInfo = (id: number) => {
  AudioControllerService.getAudio(id).then((res) => {
    if (res.code == 0 && res.data != null) {
      audioBaseVO.value = res.data
    } else {
      message.error(res.code)
    }
  })
}
const handleAdd = () => {
  formTitle.value = '新增'
  open.value = true
  audioBaseVO.value = {}
  fileList.value = []
}
// 编辑
const handleEdit = (id: number) => {
  formTitle.value = '编辑'
  open.value = true
  getAudioInfo(id)
  audioBaseVO.value = {}
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
      AudioControllerService.deleteAudio(id).then(
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
audio {
  width: 300px;
  height: 50px;
  border-radius: 20px;
  /* box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); */
  background-color: #fff;
  /* border: 1px solid #ddd; */
}

audio::-webkit-media-controls-panel {
  background-color: #fff;
  border-radius: 20px;
}

audio::-webkit-media-controls-play-button,
audio::-webkit-media-controls-mute-button {
  background-color: #007bff;
  border-radius: 50%;
}
</style>
