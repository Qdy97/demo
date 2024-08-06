package com.example.demo.rest;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.data.Datas;
import com.example.demo.service.DataService;
import com.example.demo.utils.ApiResponseUtils;
import com.example.demo.utils.JarTool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Api(tags = "资料crud的相关接口")
@RestController
@RequestMapping(path = "/api/data",produces = "application/json;charset=utf-8")
@Slf4j
@Validated
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService){
        this.dataService = dataService;
    }

    @ApiOperation(value = "新增资料")
    @PostMapping("")
    public ApiResponseEntity add(
            @ApiParam(value = "资料实体类") @RequestBody Datas data
    ){
        dataService.add(data);
        return ApiResponseUtils.success();
    }

    @ApiOperation(value = "资料列表")
    @PostMapping("/list")
    public ApiResponseEntity<PageInfo<Datas>> list(
            @ApiParam(value = "资料实体类") @RequestBody(required = false) Datas datas,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo){
        return dataService.list(datas,pageNo,pageSize);
    }

    @ApiOperation(value = "删除资料")
    @DeleteMapping("/delete")
    public ApiResponseEntity<Boolean> delete(@RequestParam Integer id){
        dataService.delete(id);
        return ApiResponseUtils.success();
    }

    @ApiOperation(value = "资料上传")
    @PostMapping("/uploadFile")
    public ApiResponseEntity uploadFile(HttpServletRequest request,@RequestParam(value = "file", required = false)MultipartFile multipartFile){
        //获取jar包所在路径并添加斜线
        String path = JarTool.getJarDir();
        path = path.substring(0, path.lastIndexOf("\\"));
        path = path.substring(0, path.lastIndexOf("\\"));
        path = path +"\\";
        //如果路径以file/开头进行删减
        if (path.startsWith("file:\\")){
            path = path.substring(path.indexOf("\\",1)+1, path.length());
        }
        //获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //获取文件后缀
        String houzhui = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        UUID uuid = UUID.randomUUID();
        //使用UUID生成文件路径+名字
        String storePath = path+uuid+'.'+houzhui;
        try {
            if(multipartFile!=null){
                uploadFormFile(multipartFile.getInputStream(),storePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("附件上传出错");
            ApiResponseUtils.fail(e);
        }
        log.info("path:{}",path);
        return ApiResponseUtils.success(""+uuid+'.'+houzhui);
    }

    @ApiOperation(value = "资料下载")
    @GetMapping("/download")
    public HttpServletResponse download(@RequestParam String fileName, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String path = JarTool.getJarDir();
            path = path.substring(0, path.lastIndexOf("\\"));
            path = path.substring(0, path.lastIndexOf("\\"));
            path = path+"\\"+fileName;
            //如果路径以file/开头进行删减
            if (path.startsWith("file:\\")){
                path = path.substring(path.indexOf("\\",1)+1, path.length());
            }
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
            byte[] buffer = new byte[1024];
//            fis.read(buffer);
//            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
            int len = 0;
            while((len = fis.read(buffer)) != -1){
                toClient.write(buffer, 0, len);
            }
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String  uploadFormFile(InputStream is, String storePath)
            throws IOException {
        String returnStr = "fail";
        // 开始上传
        OutputStream os = null;
        try {
            os = new FileOutputStream(storePath);
            int bytes = 0;
            byte[] buffer = new byte[8192];
            while ((bytes = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytes);
            }
            os.flush();
            os.close();
            is.close();
            returnStr = "success";
        } catch (Exception e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                    os = null;
                } catch (Exception e1) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (Exception e1) {
                }
            }
        }
        return returnStr;
    }

    private int i = 1;

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public int test(@RequestParam Integer id){
        i=i+1;
        return i;
    }
}
