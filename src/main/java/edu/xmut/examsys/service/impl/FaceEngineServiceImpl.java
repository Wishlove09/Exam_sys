package edu.xmut.examsys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageInfo;
import com.google.common.collect.Lists;
import edu.xmut.examsys.bean.UserFaceInfo;
import edu.xmut.examsys.bean.dto.ProcessInfo;
import edu.xmut.examsys.bean.vo.FaceUserInfo;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.mapper.UserFaceInfoMapper;
import edu.xmut.examsys.service.FaceEngineService;
import edu.xmut.examsys.utils.FaceEngineFactory;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;


@Service
public class FaceEngineServiceImpl implements FaceEngineService {

    public final static Logger logger = LoggerFactory.getLogger(FaceEngineServiceImpl.class);
    private final UserFaceInfoMapper userFaceInfoMapper;

    public String sdkLibPath;

    public String appId;

    public String sdkKey;


    public Integer threadPoolSize;


    private Float passRate = 0.7F;

    private ExecutorService executorService;


    private GenericObjectPool<FaceEngine> faceEngineObjectPool;


    public FaceEngineServiceImpl() {
        // 初始化参数
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = FaceEngineServiceImpl.class.getResourceAsStream("/arcface.properties");
            properties.load(inputStream);
            appId = properties.getProperty("app-id");
            sdkKey = properties.getProperty("sdk-key");
            sdkLibPath = properties.getProperty("sdk-lib-path");
            threadPoolSize = Integer.parseInt(properties.getProperty("thread-pool-size"));
            passRate = Float.parseFloat(properties.getProperty("pass-rate"));

            inputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }


        executorService = Executors.newFixedThreadPool(threadPoolSize);
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(threadPoolSize);
        poolConfig.setMaxTotal(threadPoolSize);
        poolConfig.setMinIdle(threadPoolSize);
        poolConfig.setLifo(false);

        // 引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);
        // 功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        faceEngineObjectPool = new GenericObjectPool(new FaceEngineFactory(sdkLibPath, appId, sdkKey, engineConfiguration), poolConfig);// 底层库算法对象池

        // mapper注入
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(SqlSessionFactoryUtils.initSqlSessionFactory());
        userFaceInfoMapper = sessionTemplate.getMapper(UserFaceInfoMapper.class);

    }


    private int plusHundred(Float value) {
        BigDecimal target = new BigDecimal(value);
        BigDecimal hundred = new BigDecimal("100");
        return target.multiply(hundred).intValue();

    }

    /**
     * 检测人脸
     *
     * @param imageInfo
     * @return
     */
    @Override
    public List<FaceInfo> detectFaces(ImageInfo imageInfo) {
        FaceEngine faceEngine = null;
        try {
            // 获取引擎对象
            faceEngine = faceEngineObjectPool.borrowObject();

            // 人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<>();

            // 人脸检测
            faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
            return faceInfoList;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                // 释放引擎对象
                faceEngineObjectPool.returnObject(faceEngine);
            }
        }
        return null;
    }


    @Override
    public List<ProcessInfo> process(ImageInfo imageInfo) {
        FaceEngine faceEngine = null;
        try {
            // 获取引擎对象
            faceEngine = faceEngineObjectPool.borrowObject();
            // 人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            // 人脸检测
            faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
            int processResult = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, FunctionConfiguration.builder().supportAge(true).supportGender(true).build());
            List<ProcessInfo> processInfoList = Lists.newLinkedList();

            List<GenderInfo> genderInfoList = new ArrayList<>();
            // 性别提取
            int genderCode = faceEngine.getGender(genderInfoList);
            // 年龄提取
            List<AgeInfo> ageInfoList = new ArrayList<>();
            int ageCode = faceEngine.getAge(ageInfoList);
            for (int i = 0; i < genderInfoList.size(); i++) {
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setGender(genderInfoList.get(i).getGender());
                processInfo.setAge(ageInfoList.get(i).getAge());
                processInfoList.add(processInfo);
            }
            return processInfoList;

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                // 释放引擎对象
                faceEngineObjectPool.returnObject(faceEngine);
            }
        }

        return null;

    }

    /**
     * 人脸特征
     *
     * @param imageInfo
     * @return
     */
    @Override
    public byte[] extractFaceFeature(ImageInfo imageInfo) {

        FaceEngine faceEngine = null;
        try {
            // 获取引擎对象
            faceEngine = faceEngineObjectPool.borrowObject();

            // 人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<>();

            // 人脸检测
            int i = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

            if (CollectionUtil.isNotEmpty(faceInfoList)) {
                FaceFeature faceFeature = new FaceFeature();
                // 提取人脸特征
                faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);

                return faceFeature.getFeatureData();
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                // 释放引擎对象
                faceEngineObjectPool.returnObject(faceEngine);
            }

        }

        return null;
    }

    @Override
    public List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Long userId) throws InterruptedException, ExecutionException {
        List<FaceUserInfo> resultFaceInfoList = Lists.newLinkedList();// 识别到的人脸列表
        // 目标人脸特征信息
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature);
        // 从数据库中取出人脸库
        List<FaceUserInfo> faceInfoList = userFaceInfoMapper.getByUserId(userId);
        if (CollectionUtil.isEmpty(faceInfoList)) {
            throw new GlobalException("人脸信息不存在");
        }

        // 单线程比对人脸
        // resultFaceInfoList.addAll(singleThreadComparison(targetFaceFeature, faceInfoList));

        // 多线程比对人脸
        List<List<FaceUserInfo>> faceUserInfoPartList = Lists.partition(faceInfoList, 1000);// 分成1000一组，多线程处理
        CompletionService<List<FaceUserInfo>> completionService = new ExecutorCompletionService<>(executorService);

        for (List<FaceUserInfo> part : faceUserInfoPartList) {
            completionService.submit(new CompareFaceTask(part, targetFaceFeature));
        }
        for (int i = 0; i < faceUserInfoPartList.size(); i++) {
            List<FaceUserInfo> faceUserInfoList = completionService.take().get();
            if (CollectionUtil.isNotEmpty(faceInfoList)) {
                resultFaceInfoList.addAll(faceUserInfoList);
            }
        }

        resultFaceInfoList.sort((h1, h2) -> h2.getSimilarValue().compareTo(h1.getSimilarValue()));// 从大到小排序

        return resultFaceInfoList;
    }

    /**
     * 单线程人脸比对
     *
     * @param targetFaceFeature 目标人脸特征信息
     * @param faceInfoList      人脸库
     */
    private List<FaceUserInfo> singleThreadComparison(FaceFeature targetFaceFeature, List<FaceUserInfo> faceInfoList) {
        FaceEngine faceEngine = null;
        // 识别到的人脸列表
        List<FaceUserInfo> resultFaceInfoList = Lists.newLinkedList();
        try {
            // 从线程池中得到一个引擎对象
            faceEngine = faceEngineObjectPool.borrowObject();
            // 遍历人脸库
            for (FaceUserInfo faceUserInfo : faceInfoList) {
                // 人脸特征信息
                FaceFeature sourceFaceFeature = new FaceFeature();
                sourceFaceFeature.setFeatureData(faceUserInfo.getFaceFeature());
                // 相似度对象
                FaceSimilar faceSimilar = new FaceSimilar();
                // 比对人脸数据
                faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                // 获取相似值
                Float similarValue = faceSimilar.getScore();
                // 相似值大于配置预期，加入到识别到人脸的列表
                if (similarValue > passRate) {
                    FaceUserInfo info = new FaceUserInfo();
                    info.setFaceId(faceUserInfo.getFaceId());
                    info.setSimilarValue(similarValue);
                    resultFaceInfoList.add(info);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (faceEngine != null) {
                faceEngineObjectPool.returnObject(faceEngine);
            }
        }

        return resultFaceInfoList;
    }

    @Override
    public void addFace(UserFaceInfo userFaceInfo) {
        userFaceInfoMapper.insert(userFaceInfo);
    }

    @Override
    public List<UserFaceInfo> getFaceInfo(Long userId) {
        return userFaceInfoMapper.getAllByUserId(userId);
    }


    private class CompareFaceTask implements Callable<List<FaceUserInfo>> {

        /**
         * 人脸库
         */
        private List<FaceUserInfo> faceUserInfoList;
        /**
         * 目标人脸特征信息
         */
        private FaceFeature targetFaceFeature;


        public CompareFaceTask(List<FaceUserInfo> faceUserInfoList, FaceFeature targetFaceFeature) {
            this.faceUserInfoList = faceUserInfoList;
            this.targetFaceFeature = targetFaceFeature;
        }

        @Override
        public List<FaceUserInfo> call() {
            FaceEngine faceEngine = null;
            // 识别到的人脸列表
            List<FaceUserInfo> resultFaceInfoList = Lists.newLinkedList();
            try {
                // 从线程池中得到一个引擎对象
                faceEngine = faceEngineObjectPool.borrowObject();
                // 遍历人脸库
                for (FaceUserInfo faceUserInfo : faceUserInfoList) {
                    // 人脸特征信息
                    FaceFeature sourceFaceFeature = new FaceFeature();
                    sourceFaceFeature.setFeatureData(faceUserInfo.getFaceFeature());
                    // 相似度对象
                    FaceSimilar faceSimilar = new FaceSimilar();
                    // 比对人脸数据
                    faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                    // 获取相似值
                    Float similarValue = faceSimilar.getScore();
                    // 相似值大于配置预期，加入到识别到人脸的列表
                    if (similarValue > passRate) {
                        FaceUserInfo info = new FaceUserInfo();
                        info.setFaceId(faceUserInfo.getFaceId());
                        info.setSimilarValue(similarValue);
                        resultFaceInfoList.add(info);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (faceEngine != null) {
                    faceEngineObjectPool.returnObject(faceEngine);
                }
            }

            return resultFaceInfoList;
        }

    }
}
