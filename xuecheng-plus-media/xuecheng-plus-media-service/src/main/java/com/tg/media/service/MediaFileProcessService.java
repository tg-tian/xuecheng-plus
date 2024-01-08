package com.tg.media.service;

import com.tg.media.model.po.MediaProcess;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MediaFileProcessService {

    List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    boolean startTask(long id);

    @Transactional
    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);
}
