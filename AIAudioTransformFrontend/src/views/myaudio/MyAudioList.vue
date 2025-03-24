<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search-container">
      <!-- 选择音乐 -->
      <a-select
        v-model:value="selectedSong"
        placeholder="请选择音乐"
        class="search-item"
        style="width: 200px"
        :options="songOptions"
      />
      <!-- 选择声音 -->
      <a-select
        v-model:value="selectedVoice"
        placeholder="请选择声音"
        class="search-item"
        style="width: 200px"
        :options="voiceOptions"
      />
      <!-- 选择时间范围 -->
      <a-range-picker class="search-item" v-model:value="selectedTimeRange" />
      <!-- 搜索按钮 -->
      <a-button type="primary" @click="handleSearch">搜索</a-button>
      <!-- 重置按钮 -->
      <a-button @click="handleReset">重置</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="data"
      :pagination="pagination"
      :loading="loading"
      align="center"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column }">
        <template v-if="column.key === 'action'">
          <span>
            <a @click="handleDownload">下载</a>
            <a-divider type="vertical" />
            <a @click="handleDelete">删除</a>
          </span>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { AudioControllerService } from '@/api'
import type { AudioPageReqVO, AudioRespVO } from '@/api'
import { computed, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { ref } from 'vue'
import dayjs from 'dayjs'

const data = ref<Array<AudioRespVO>>([])
const loading = ref(true)
const total = ref<number | undefined>(0)
const current = ref<number | undefined>(1)
const pageSize = ref<number | undefined>(5)

const columns = [
  {
    title: '歌曲名称',
    dataIndex: 'songName',
    key: 'songName',
    align: 'center',
  },
  {
    title: '声音名称',
    dataIndex: 'voiceName',
    key: 'voiceName',
    align: 'center',
  },
  {
    title: '音频',
    dataIndex: 'audioUrl',
    key: 'audioUrl',
    align: 'center',
  },
  {
    title: '生成时间',
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
  AudioControllerService.getMyPage(reqVO).then(
    (res) => {
      console.log(res)
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

const selectedSong = ref<number | null>(null)
const selectedVoice = ref<number | null>(null)
const selectedTimeRange = ref<number[]>([])
const songOptions = ref<Array<{ label: string | undefined; value: number | undefined }>>([])
const voiceOptions = ref<Array<{ label: string | undefined; value: number | undefined }>>([])
// 重置事件
const handleReset = () => {
  selectedSong.value = null
  selectedVoice.value = null
  selectedTimeRange.value = []
  pagination.value.current = 1
  handleSearch()
}
// 搜索事件
const handleSearch = () => {
  reqVO = {}
  reqVO.pageNo = pagination.value.current
  reqVO.pageSize = pagination.value.pageSize
  reqVO.songId = selectedSong.value ?? undefined
  reqVO.voiceId = selectedVoice.value ?? undefined
  reqVO.createTime = selectedTimeRange.value.map((item) =>
    dayjs(item).format(`YYYY-MM-DDTHH:mm:ss`),
  )
  getReqData()
}

// 获取音乐列表
const getSongList = () => {
  AudioControllerService.getSongList().then(
    (res) => {
      if (res.code == 0 && res.data != null) {
        songOptions.value = res.data.map((item) => ({
          label: item.audioName,
          value: item.id,
        }))
      } else {
        message.error(res.code)
      }
    },
    (err) => {
      message.error(err.message)
    },
  )
}
// 获取声音列表
const getVoiceList = () => {
  AudioControllerService.getVoiceList().then(
    (res) => {
      if (res.code == 0 && res.data != null) {
        voiceOptions.value = res.data.map((item) => ({
          label: item.audioName,
          value: item.id,
        }))
      } else {
        message.error(res.code)
      }
    },
    (err) => {
      message.error(err.message)
    },
  )
}
// 初始化数据
onMounted(() => {
  getReqData()
  getSongList()
  getVoiceList()
})

// 下载
const handleDownload = () => {}
// 删除
const handleDelete = () => {}
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
</style>
