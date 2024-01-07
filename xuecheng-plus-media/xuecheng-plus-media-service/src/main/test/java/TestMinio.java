import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestMinio {

    static MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials("minioadmin", "minioadmin").build();

    @Test
    public void upload() {
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".jpg");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        try {
            File file = new File("E:/chunk/");
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                UploadObjectArgs testbucket = UploadObjectArgs.builder().bucket("testbucket").object("chunk/" + i).filename(files[i].getAbsolutePath()).build();
                minioClient.uploadObject(testbucket);
            }

            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

    }

    @Test
    public void delete() {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket("testbucket").object("001/test001.jpg").build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    @Test
    public void getFile() {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("001/test001.jpg").build();
        try (FilterInputStream inputStream = minioClient.getObject(getObjectArgs); FileOutputStream outputStream = new FileOutputStream(new File("E:\\2.jpg"));) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void merge() {
        try {
            List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(4).map(i -> ComposeSource.builder().bucket("testbucket").object("chunk/".concat(Integer.toString(i))).build()).collect(Collectors.toList());
            ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder().bucket("testbucket").object("merge01.mp4").sources(sources).build();
            minioClient.composeObject(composeObjectArgs);
            System.out.println("合并成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("合并失败");
        }
    }

    @Test
    public void test_removeObjects(){
        //合并分块完成将分块文件清除
        List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                .limit(4)
                .map(i -> new DeleteObject("chunk/".concat(Integer.toString(i))))
                .collect(Collectors.toList());

        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket("testbucket").objects(deleteObjects).build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        results.forEach(r->{
            DeleteError deleteError = null;
            try {
                deleteError = r.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}