package edu.xmut.examsys.web.student;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import edu.xmut.examsys.bean.UserFaceInfo;
import edu.xmut.examsys.bean.dto.FaceSearchResDto;
import edu.xmut.examsys.bean.dto.ProcessInfo;
import edu.xmut.examsys.bean.vo.FaceUserInfo;
import edu.xmut.examsys.exception.GlobalException;
import edu.xmut.examsys.service.FaceEngineService;
import edu.xmut.examsys.utils.R;
import edu.xmut.examsys.utils.UserUtils;
import fun.shuofeng.myspringmvc.annotaion.Autowired;
import fun.shuofeng.myspringmvc.annotaion.Controller;
import fun.shuofeng.myspringmvc.annotaion.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;


/**
 * @author 朔风
 * @date 2023-12-23 22:57
 */
@Controller
@RequestMapping("/face")
public class FaceController {
    public final static Logger logger = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    FaceEngineService faceEngineService;


    @RequestMapping(value = "/search", method = "post")
    public R faceSearch(HttpServletRequest request) throws Exception {
        Part file = request.getPart("file");
        System.out.println("原始文件名：" + file.getSubmittedFileName());
        System.out.println("表单字段名：" + file.getName());
        System.out.println("文件大小：" + file.getSize() + "字节");
        System.out.println("文件类型：" + file.getContentType());
        // 从文件中获得输入流
        InputStream inputStream = file.getInputStream();
        // 将输入流转化为字节数组
        byte[] decode = IoUtil.readBytes(inputStream);
        // 保存文件到磁盘
        FileUtil.writeBytes(decode, "D:/test.jpg");
        BufferedImage bufImage = ImageIO.read(new ByteArrayInputStream(decode));
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);

        // 人脸特征获取
        byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
        if (Objects.isNull(bytes)) {
            throw new GlobalException("人脸特征获取失败");
        }

        Long userId = UserUtils.getUserId(request);
        FaceSearchResDto faceSearchResDto = new FaceSearchResDto();

        // 人脸比对，获取比对结果
        List<FaceUserInfo> userFaceInfoList = faceEngineService.compareFaceFeature(bytes, userId);

        if (CollectionUtil.isNotEmpty(userFaceInfoList)) {
            FaceUserInfo faceUserInfo = userFaceInfoList.get(0);
            BeanUtil.copyProperties(faceUserInfo, faceSearchResDto);
            List<ProcessInfo> processInfoList = faceEngineService.process(imageInfo);

            if (CollectionUtil.isNotEmpty(processInfoList)) {
                // 人脸检测，得到人脸列表
                List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
                int left = faceInfoList.get(0).getRect().getLeft();
                int top = faceInfoList.get(0).getRect().getTop();
                int width = faceInfoList.get(0).getRect().getRight() - left;
                int height = faceInfoList.get(0).getRect().getBottom() - top;

                Graphics2D graphics2D = bufImage.createGraphics();
                graphics2D.setColor(Color.RED);// 红色
                BasicStroke stroke = new BasicStroke(5f);
                graphics2D.setStroke(stroke);
                graphics2D.drawRect(left, top, width, height);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufImage, "jpg", outputStream);
                byte[] bytes1 = outputStream.toByteArray();
                faceSearchResDto.setImage("data:image/jpeg;base64," + Base64Utils.encodeToString(bytes1));
                faceSearchResDto.setAge(processInfoList.get(0).getAge());
                faceSearchResDto.setGender(processInfoList.get(0).getGender().equals(1) ? "女" : "男");
            }

            return R.ok(faceSearchResDto);
        }


        return R.fail("人脸比对失败");
    }


    @RequestMapping(value = "/add", method = "post")
    public R addFace(HttpServletRequest request) throws ServletException, IOException, InterruptedException {
        Part file = request.getPart("file");
        InputStream inputStream = file.getInputStream();
        byte[] readBytes = IoUtil.readBytes(inputStream);
        String base64Image = "data:image/png;base64," + Base64Utils.encodeToString(readBytes);

        ImageInfo imageInfo = ImageFactory.getRGBData(readBytes);

        // 人脸特征获取
        byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
        if (bytes == null) {
            return R.fail();
        }

        UserFaceInfo userFaceInfo = new UserFaceInfo();
        userFaceInfo.setUserId(UserUtils.getUserId(request));
        userFaceInfo.setFaceId(RandomUtil.randomString(10));
        userFaceInfo.setFaceFeature(bytes);
        userFaceInfo.setFaceImage(base64Image);

        // 人脸特征插入到数据库
        faceEngineService.addFace(userFaceInfo);

        return R.ok("人脸添加成功");
    }

    @RequestMapping(value = "/get", method = "get")
    public R getFaceInfo(HttpServletRequest request) {
        String requestParameter = request.getParameter("userId");
        if (StringUtils.isEmpty(requestParameter)) {
            return R.fail("请求参数为空").code(401);
        }
        Long userId = Long.valueOf(requestParameter);

        List<UserFaceInfo> userFaceInfoList = faceEngineService.getFaceInfo(userId);
        userFaceInfoList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));

        return R.ok(userFaceInfoList.get(0));
    }
}
