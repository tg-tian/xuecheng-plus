package com.tg.media.service;

import com.tg.media.model.po.MediaProcess;

import java.util.List;

public interface MediaFileProcessService {

    List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);
}
