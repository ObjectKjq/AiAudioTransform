declare namespace API {
  type AiFileCreateReqVO = {
    fileName?: string;
    fileUrl?: string;
    content?: string[];
    fileType?: string;
  };

  type AiFilePageReqVO = {
    pageNo?: number;
    pageSize?: number;
    fileName?: string;
    fileUrl?: string;
    content?: string[];
    fileType?: string;
    creator?: string;
    createTime?: string;
    updater?: string;
    updateTime?: string;
    deleted?: boolean;
  };

  type AiFileRespVO = {
    fileName?: string;
    fileUrl?: string;
    content?: string[];
    fileType?: string;
    id?: number;
  };

  type AiFileUpdateReqVO = {
    fileName?: string;
    fileUrl?: string;
    content?: string[];
    fileType?: string;
    id?: number;
  };

  type AudioCreateReqVO = {
    audioName?: string;
    audioUrl?: string;
    type?: number;
    songId?: number;
    voiceId?: number;
    userId?: number;
    remark?: string;
  };

  type AudioPageReqVO = {
    pageNo?: number;
    pageSize?: number;
    audioName?: string;
    audioUrl?: string;
    type?: number;
    songId?: number;
    voiceId?: number;
    userId?: number;
    remark?: string;
  };

  type AudioRespVO = {
    audioName?: string;
    audioUrl?: string;
    type?: number;
    songId?: number;
    voiceId?: number;
    userId?: number;
    remark?: string;
    id?: number;
  };

  type AudioUpdateReqVO = {
    audioName?: string;
    audioUrl?: string;
    type?: number;
    songId?: number;
    voiceId?: number;
    userId?: number;
    remark?: string;
    id?: number;
  };

  type BaseResponseAiFileRespVO = {
    code?: number;
    data?: AiFileRespVO;
    message?: string;
  };

  type BaseResponseAudioRespVO = {
    code?: number;
    data?: AudioRespVO;
    message?: string;
  };

  type BaseResponseBoolean = {
    code?: number;
    data?: boolean;
    message?: string;
  };

  type BaseResponseListAiFileRespVO = {
    code?: number;
    data?: AiFileRespVO[];
    message?: string;
  };

  type BaseResponseListAudioRespVO = {
    code?: number;
    data?: AudioRespVO[];
    message?: string;
  };

  type BaseResponseListUserRespVO = {
    code?: number;
    data?: UserRespVO[];
    message?: string;
  };

  type BaseResponsePageAiFileRespVO = {
    code?: number;
    data?: PageAiFileRespVO;
    message?: string;
  };

  type BaseResponsePageAudioRespVO = {
    code?: number;
    data?: PageAudioRespVO;
    message?: string;
  };

  type BaseResponsePageUserRespVO = {
    code?: number;
    data?: PageUserRespVO;
    message?: string;
  };

  type BaseResponseString = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponseUserRespVO = {
    code?: number;
    data?: UserRespVO;
    message?: string;
  };

  type deleteAiFileParams = {
    id: number;
  };

  type deleteAudioParams = {
    id: number;
  };

  type deleteUserParams = {
    id: number;
  };

  type getAiFileParams = {
    id: number;
  };

  type getAudioParams = {
    id: number;
  };

  type getUserParams = {
    id: number;
  };

  type OrderItem = {
    column?: string;
    asc?: boolean;
  };

  type PageAiFileRespVO = {
    records?: AiFileRespVO[];
    total?: number;
    size?: number;
    current?: number;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: number;
    pages?: number;
  };

  type PageAudioRespVO = {
    records?: AudioRespVO[];
    total?: number;
    size?: number;
    current?: number;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: number;
    pages?: number;
  };

  type PageUserRespVO = {
    records?: UserRespVO[];
    total?: number;
    size?: number;
    current?: number;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    countId?: string;
    maxLimit?: number;
    pages?: number;
  };

  type UserCreateReqVO = {
    username?: string;
    password?: string;
    role?: number;
    avatarUrl?: string;
    isDisable?: number;
  };

  type UserLoginReqVO = {
    username?: string;
    password?: string;
  };

  type UserPageReqVO = {
    pageNo?: number;
    pageSize?: number;
    username?: string;
    role?: number;
    isDisable?: number;
    createTime?: string;
    updateTime?: string;
  };

  type UserRegisterReqVO = {
    username?: string;
    password?: string;
    checkPassword?: string;
  };

  type UserRespVO = {
    username?: string;
    password?: string;
    role?: number;
    avatarUrl?: string;
    isDisable?: number;
    id?: number;
  };

  type UserUpdateReqVO = {
    username?: string;
    password?: string;
    role?: number;
    avatarUrl?: string;
    isDisable?: number;
    id?: number;
  };
}
