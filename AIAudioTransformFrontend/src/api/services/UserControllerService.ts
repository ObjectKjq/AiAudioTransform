/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseBoolean } from '../models/BaseResponseBoolean';
import type { BaseResponseListUserRespVO } from '../models/BaseResponseListUserRespVO';
import type { BaseResponsePageUserRespVO } from '../models/BaseResponsePageUserRespVO';
import type { BaseResponseString } from '../models/BaseResponseString';
import type { BaseResponseUserRespVO } from '../models/BaseResponseUserRespVO';
import type { UserCreateReqVO } from '../models/UserCreateReqVO';
import type { UserLoginReqVO } from '../models/UserLoginReqVO';
import type { UserPageReqVO } from '../models/UserPageReqVO';
import type { UserRegisterReqVO } from '../models/UserRegisterReqVO';
import type { UserUpdateReqVO } from '../models/UserUpdateReqVO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserControllerService {
    /**
     * @param requestBody
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static updateUser(
        requestBody: UserUpdateReqVO,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/user/update',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponseString OK
     * @throws ApiError
     */
    public static userRegister(
        requestBody: UserRegisterReqVO,
    ): CancelablePromise<BaseResponseString> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/register',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponsePageUserRespVO OK
     * @throws ApiError
     */
    public static getPage(
        requestBody: UserPageReqVO,
    ): CancelablePromise<BaseResponsePageUserRespVO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/page',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponseString OK
     * @throws ApiError
     */
    public static userLogin(
        requestBody: UserLoginReqVO,
    ): CancelablePromise<BaseResponseString> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/login',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static addUser(
        requestBody: UserCreateReqVO,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns BaseResponseListUserRespVO OK
     * @throws ApiError
     */
    public static getList(): CancelablePromise<BaseResponseListUserRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/user/list',
        });
    }
    /**
     * @returns BaseResponseUserRespVO OK
     * @throws ApiError
     */
    public static getUserInfo(): CancelablePromise<BaseResponseUserRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/user/info/get',
        });
    }
    /**
     * @param id
     * @returns BaseResponseUserRespVO OK
     * @throws ApiError
     */
    public static getUser(
        id: number,
    ): CancelablePromise<BaseResponseUserRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/user/get',
            query: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static deleteUser(
        id: number,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/user/delete',
            query: {
                'id': id,
            },
        });
    }
}
