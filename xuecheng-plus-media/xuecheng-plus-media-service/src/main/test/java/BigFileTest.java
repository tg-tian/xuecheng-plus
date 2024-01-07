import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BigFileTest {

    @Test
    public void testBigFile() throws IOException {
        File sourcefile = new File("E:/1.mp4");
        String chunkfileName = "E:/chunk/";

        int chunkSize = 1024 * 1024 * 30;
        byte[] bytes = new byte[1024];
        int times = (int)Math.ceil((sourcefile.length() * 1.0) / chunkSize);
        FileInputStream fileInputStream = new FileInputStream(sourcefile);
        for (int i = 0; i < times; i++) {

            int len = -1;
            File file = new File(chunkfileName + i);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while((len = fileInputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,len);
                if (file.length() >= chunkSize) {

                    break;
                }
            }
            fileOutputStream.close();

        }
        fileInputStream.close();

    }

    @Test
    public void testMergeBigFile() throws IOException {

        File sourcefile = new File("E:/1.mp4");
        File destfile = new File("E:/2.mp4");
        if (destfile.exists()) {
            destfile.delete();
        }
        destfile.createNewFile();
        String chunkfileName = "E:/chunk/";
        File chunkfile = new File(chunkfileName);
        File[] fileArray = chunkfile.listFiles();
        List<File> fileList = Arrays.asList(fileArray);
        // 从小到大排序
        Collections.sort(fileList, (o1, o2) -> Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName()));

        byte[] bytes = new byte[1024];

        FileOutputStream fileOutputStream = new FileOutputStream(destfile);

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            int len = -1;
            while((len = fileInputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,len);

            }
            fileInputStream.close();
        }
        fileOutputStream.close();
        String s = DigestUtils.md5Hex(new FileInputStream(sourcefile));
        String s1 = DigestUtils.md5Hex((new FileInputStream(destfile)));
        if(s.equals(s1)){
            System.out.println("合并成功");
        }
    }
}
