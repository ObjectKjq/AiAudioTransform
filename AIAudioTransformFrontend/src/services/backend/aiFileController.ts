// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /ai-file/add */
export async function addAiFile(body: API.AiFileCreateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/ai-file/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /ai-file/delete */
export async function deleteAiFile(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteAiFileParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean>('/ai-file/delete', {
    method: 'DELETE',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /ai-file/get */
export async function getAiFile(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAiFileParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseAiFileRespVO>('/ai-file/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /ai-file/list */
export async function getList2(options?: { [key: string]: any }) {
  return request<API.BaseResponseListAiFileRespVO>('/ai-file/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /ai-file/page */
export async function getPage2(body: API.AiFilePageReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponsePageAiFileRespVO>('/ai-file/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /ai-file/update */
export async function updateAiFile(body: API.AiFileUpdateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/ai-file/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
