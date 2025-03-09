// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /user/add */
export async function addUser(body: API.UserCreateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/user/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /user/delete */
export async function deleteUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteUserParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean>('/user/delete', {
    method: 'DELETE',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /user/get */
export async function getUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseUserRespVO>('/user/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /user/list */
export async function getList(options?: { [key: string]: any }) {
  return request<API.BaseResponseListUserRespVO>('/user/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /user/login */
export async function userLogin(body: API.UserLoginReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseString>('/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /user/page */
export async function getPage(body: API.UserPageReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponsePageUserRespVO>('/user/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /user/register */
export async function userRegister(body: API.UserRegisterReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseString>('/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /user/update */
export async function updateUser(body: API.UserUpdateReqVO, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/user/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
