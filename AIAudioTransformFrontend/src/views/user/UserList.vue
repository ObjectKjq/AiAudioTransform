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
            <a>编辑</a>
            <a-divider type="vertical" />
            <a>删除</a>
          </span>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { UserControllerService } from '@/api'
import type { UserPageReqVO, AudioRespVO, UserRespVO } from '@/api'
import { computed, onMounted } from 'vue'
import { message } from 'ant-design-vue'
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
</style>
