// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /audio/add */
export async function addAudio(body: API.AudioCreateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/audio/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /audio/delete */
export async function deleteAudio(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteAudioParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean>('/audio/delete', {
    method: 'DELETE',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /audio/get */
export async function getAudio(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAudioParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseAudioRespVO>('/audio/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /audio/list */
export async function getList1(options?: { [key: string]: any }) {
  return request<API.BaseResponseListAudioRespVO>('/audio/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /audio/page */
export async function getPage1(body: API.AudioPageReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponsePageAudioRespVO>('/audio/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /audio/update */
export async function updateAudio(body: API.AudioUpdateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/audio/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
