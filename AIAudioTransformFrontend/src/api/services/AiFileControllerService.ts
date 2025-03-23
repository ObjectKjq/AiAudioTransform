/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AiFileCreateReqVO } from '../models/AiFileCreateReqVO';
import type { AiFilePageReqVO } from '../models/AiFilePageReqVO';
import type { AiFileUpdateReqVO } from '../models/AiFileUpdateReqVO';
import type { BaseResponseAiFileRespVO } from '../models/BaseResponseAiFileRespVO';
import type { BaseResponseBoolean } from '../models/BaseResponseBoolean';
import type { BaseResponseListAiFileRespVO } from '../models/BaseResponseListAiFileRespVO';
import type { BaseResponsePageAiFileRespVO } from '../models/BaseResponsePageAiFileRespVO';
import type { BaseResponseString } from '../models/BaseResponseString';
import type { FileUploadReqVO } from '../models/FileUploadReqVO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AiFileControllerService {
    /**
     * @param requestBody
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static updateAiFile(
        requestBody: AiFileUpdateReqVO,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/ai-file/update',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param uploadReqVo
     * @returns BaseResponseString OK
     * @throws ApiError
     */
    public static uploadFile(
        uploadReqVo: FileUploadReqVO,
    ): CancelablePromise<BaseResponseString> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/ai-file/upload',
            query: {
                'uploadReqVO': uploadReqVo,
            },
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponsePageAiFileRespVO OK
     * @throws ApiError
     */
    public static getPage2(
        requestBody: AiFilePageReqVO,
    ): CancelablePromise<BaseResponsePageAiFileRespVO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/ai-file/page',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static addAiFile(
        requestBody: AiFileCreateReqVO,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/ai-file/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param aiFileId
     * @param fileName
     * @returns any OK
     * @throws ApiError
     */
    public static getAiFile(
        aiFileId: number,
        fileName: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/ai-file/{aiFileId}/download/{fileName}',
            path: {
                'aiFileId': aiFileId,
                'fileName': fileName,
            },
        });
    }
    /**
     * @returns BaseResponseListAiFileRespVO OK
     * @throws ApiError
     */
    public static getList2(): CancelablePromise<BaseResponseListAiFileRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/ai-file/list',
        });
    }
    /**
     * @param id
     * @returns BaseResponseAiFileRespVO OK
     * @throws ApiError
     */
    public static getAiFile1(
        id: number,
    ): CancelablePromise<BaseResponseAiFileRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/ai-file/get',
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
    public static deleteAiFile(
        id: number,
    ): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/ai-file/delete',
            query: {
                'id': id,
            },
        });
    }
}
