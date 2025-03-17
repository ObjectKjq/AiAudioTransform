/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AudioCreateReqVO } from '../models/AudioCreateReqVO';
import type { AudioPageReqVO } from '../models/AudioPageReqVO';
import type { AudioUpdateReqVO } from '../models/AudioUpdateReqVO';
import type { BaseResponseAudioRespVO } from '../models/BaseResponseAudioRespVO';
import type { BaseResponseBoolean } from '../models/BaseResponseBoolean';
import type { BaseResponseListAudioRespVO } from '../models/BaseResponseListAudioRespVO';
import type { BaseResponsePageAudioRespVO } from '../models/BaseResponsePageAudioRespVO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AudioControllerService {
    /**
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static updateAudio({
        requestBody,
    }: {
        requestBody: AudioUpdateReqVO,
    }): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/audio/update',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns BaseResponsePageAudioRespVO OK
     * @throws ApiError
     */
    public static getPage1({
        requestBody,
    }: {
        requestBody: AudioPageReqVO,
    }): CancelablePromise<BaseResponsePageAudioRespVO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/audio/page',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static addAudio({
        requestBody,
    }: {
        requestBody: AudioCreateReqVO,
    }): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/audio/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns BaseResponseListAudioRespVO OK
     * @throws ApiError
     */
    public static getList1(): CancelablePromise<BaseResponseListAudioRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/audio/list',
        });
    }
    /**
     * @returns BaseResponseAudioRespVO OK
     * @throws ApiError
     */
    public static getAudio({
        id,
    }: {
        id: number,
    }): CancelablePromise<BaseResponseAudioRespVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/audio/get',
            query: {
                'id': id,
            },
        });
    }
    /**
     * @returns BaseResponseBoolean OK
     * @throws ApiError
     */
    public static deleteAudio({
        id,
    }: {
        id: number,
    }): CancelablePromise<BaseResponseBoolean> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/audio/delete',
            query: {
                'id': id,
            },
        });
    }
}
