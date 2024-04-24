package com.rainsoul.oss.util;

import com.rainsoul.oss.entity.FileInfo;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * 创建一个MinIO存储桶（bucket）。
     * 如果指定的存储桶不存在，则会尝试创建该存储桶。
     *
     * @param bucketName 要创建的存储桶的名称。
     * @throws Exception 如果创建存储桶过程中遇到任何错误，将抛出异常。
     */
    public void createBucket(String bucketName) throws Exception {
        // 检查存储桶是否已经存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().build());
        if (!bucketExists) {
            // 如果存储桶不存在，则创建存储桶
            minioClient.makeBucket(MakeBucketArgs.builder().build());
        }
    }


    /**
     * 上传文件到MinIO服务器。
     *
     * @param inputStream 要上传的文件的输入流。
     * @param bucketName 存储桶的名称。
     * @param objectName 上传后文件在存储桶中的对象名称。
     */
    public void uploadFile(InputStream inputStream, String bucketName, String objectName) throws Exception {
        // 使用MinIO客户端将文件上传到指定的存储桶和对象名称
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                .stream(inputStream, -1, 10485760).build());
    }

    /**
     * 列出所有存储桶（Bucket）的名称。
     *
     * @return 返回一个包含所有存储桶名称的列表。
     * @throws Exception 如果在列出存储桶时发生错误，则抛出异常。
     */
    public List<String> getAllBucket() throws Exception {
        // 获取所有存储桶信息
        List<Bucket> buckets = minioClient.listBuckets();
        // 将存储桶名称转换为列表形式并返回
        return buckets.stream().map(Bucket::name).collect(Collectors.toList());
    }


    /**
     * 列出指定存储桶中的所有文件信息。
     *
     * @param bucket 指定的存储桶名称。
     * @return 返回一个包含存储桶中所有文件信息的列表。
     * @throws Exception 如果列出对象时发生错误，则抛出异常。
     */
    public List<FileInfo> getAllFile(String bucket) throws Exception {
        // 使用MinIO客户端列出指定存储桶中的所有对象
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).build());

        List<FileInfo> fileInfoList = new LinkedList<>();
        // 遍历列出的每个对象，将其信息封装到FileInfo对象中并添加到列表
        for (Result<Item> result : results) {
            FileInfo fileInfo = new FileInfo();
            Item item = result.get();

            // 设置文件名
            fileInfo.setFileName(item.objectName());
            // 设置是否为目录标志
            fileInfo.setDirectoryFlag(item.isDir());
            // 设置ETag
            fileInfo.setEtag(item.etag());

            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }


    /**
     * 下载OSS存储桶中的文件。
     *
     * @param bucket 存储桶的名称。
     * @param objectName 存储在存储桶中的对象（文件）的名称。
     * @return 返回一个InputStream，用于读取下载的文件内容。
     * @throws Exception 如果下载过程中发生错误，则抛出异常。
     */
    public InputStream downLoad(String bucket, String objectName) throws Exception {
        // 使用MinIO客户端从指定的存储桶中获取指定的对象（文件）
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }


    /**
     * 删除指定的存储桶（Bucket）。
     *
     * @param bucket 需要删除的存储桶的名称。
     * @throws Exception 如果删除操作遇到任何错误，则会抛出异常。
     */
    public void deleteBucket(String bucket) throws Exception {
        // 使用MinIO客户端删除指定的存储桶
        minioClient.removeBucket(
                RemoveBucketArgs.builder().bucket(bucket).build()
        );
    }


    /**
     * 删除指定存储桶中的文件。
     *
     * @param bucket 存储桶的名称。
     * @param objectName 需要删除的文件（对象）在存储桶中的名称。
     * @throws Exception 如果删除过程中遇到任何错误，则抛出异常。
     */
    public void deleteObject(String bucket, String objectName) throws Exception {
        // 使用MinIO客户端删除指定存储桶中的文件
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }


    /**
     * 获取文件的预览URL。
     * 该方法用于生成一个带有签名的URL，用于预览存储在MinIO服务器上的文件。
     *
     * @param bucketName 存储文件的桶（bucket）的名称。
     * @param objectName 存储在桶中的文件的名称。
     * @return 返回一个预览文件的URL，该URL带有访问权限签名，可用于在浏览器中直接访问文件。
     * @throws Exception 如果在获取预签名URL时发生错误，则抛出异常。
     */
    public String getPreviewFileUrl(String bucketName, String objectName) throws Exception{
        // 构建获取预签名URL的参数，指定方法为GET，指定桶名称和对象名称
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName).object(objectName).build();

        // 调用MinIO客户端，根据构建的参数获取预签名的文件URL，并返回
        return minioClient.getPresignedObjectUrl(args);
    }


}
