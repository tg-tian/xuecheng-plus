package com.tg.media.service;

import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.base.model.RestResponse;
import com.tg.media.model.dto.QueryMediaParamsDto;
import com.tg.media.model.dto.UploadFileParamsDto;
import com.tg.media.model.dto.UploadFileResultDto;
import com.tg.media.model.po.MediaFiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
  * @author Mr.M
  * @date 2022/9/10 8:57
 */
 PageResult<MediaFiles> queryMediaFiels(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


 UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);


 @Transactional
 MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

 RestResponse<Boolean> checkFile(String fileMd5);

 RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

 RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath);

 RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);

 boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucketFiles, String objectName);

 File downloadFileFromMinIO(String bucket, String objectName);


}
