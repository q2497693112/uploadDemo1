package cn.cvs.controller;

import cn.cvs.pojo.*;
import cn.cvs.service.BackendUserService;
import cn.cvs.service.DevUserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private DevUserService devUserService;
    @Autowired
    private BackendUserService backendUserService;
    Map<String, Object> map = new HashMap<>();
    @RequestMapping("login")
    public String getBackendUserService(String userName, String password, Integer radio, HttpSession session) {
        Map<String, Object> maps = new HashMap<>();
        if (!"".equals(userName) && !"".equals(password)) {
            if (radio == 1) {
                DevUser devUser = devUserService.LoginDev(userName, password);
                if (devUser != null) {
                    session.setAttribute("dUser", devUser);
//                    map.put("uid", UUID.randomUUID().toString());
                    maps.put("result", "true");
                } else {
                    maps.put("result", "false");
                }
            } else {
                BackendUser backendUser = backendUserService.LoginUser(userName, password);
                if (backendUser != null) {
                    session.setAttribute("bUser", backendUser);
                    maps.put("result", "true");
                } else {
                    maps.put("result", "false");
                }
            }
        } else {
            maps.put("result", "isNull");
        }
        return JSON.toJSONString(maps);
    }
    @RequestMapping("list")
    public String list(String softwareName, String apkName, Integer status, Integer flatformId, Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpSession session) {
//        DevUser devUser =(DevUser) map.get("uid");
//        request.getHeader("token");
        DevUser devUser = (DevUser) session.getAttribute("dUser");
        BackendUser backendUser = (BackendUser) session.getAttribute("bUser");
        if (devUser == null) {
            if (backendUser != null) {
                Page page = devUserService.getPage(0, softwareName, apkName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, pageNo, pageSize);
                for (AppInfo appInfo : page.getAppInfoList()) {
                    String level1Name = appInfo.getCategoryLevel1Name() == null ? "" : appInfo.getCategoryLevel1Name();
                    String level2Name = appInfo.getCategoryLevel2Name() == null ? "" : "->" + appInfo.getCategoryLevel2Name();
                    String level3Name = appInfo.getCategoryLevel3Name() == null ? "" : "->" + appInfo.getCategoryLevel3Name();
                    appInfo.setCategoryName(level1Name+level2Name+level3Name);
                }
                return JSON.toJSONString(page);
            }
            return JSON.toJSONString(map.put("result", "error"));
        } else {
            if (backendUser != null) {
                Page page = devUserService.getPage(0, softwareName, apkName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, pageNo, pageSize);
                for (AppInfo appInfo : page.getAppInfoList()) {
                    String level1Name = appInfo.getCategoryLevel1Name() == null ? "" : appInfo.getCategoryLevel1Name();
                    String level2Name = appInfo.getCategoryLevel2Name() == null ? "" : "->" + appInfo.getCategoryLevel2Name();
                    String level3Name = appInfo.getCategoryLevel3Name() == null ? "" : "->" + appInfo.getCategoryLevel3Name();
                    appInfo.setCategoryName(level1Name+level2Name+level3Name);
                }
                return JSON.toJSONString(page);
            }
            Page page = devUserService.getPage(devUser.getId(), softwareName, apkName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, pageNo, pageSize);
            for (AppInfo appInfo : page.getAppInfoList()) {
                String level1Name = appInfo.getCategoryLevel1Name() == null ? "" : appInfo.getCategoryLevel1Name();
                String level2Name = appInfo.getCategoryLevel2Name() == null ? "" : "->" + appInfo.getCategoryLevel2Name();
                String level3Name = appInfo.getCategoryLevel3Name() == null ? "" : "->" + appInfo.getCategoryLevel3Name();
                appInfo.setCategoryName(level1Name+level2Name+level3Name);
            }
            return JSON.toJSONString(page);
        }
    }
    @RequestMapping("check")
    public String check(String apkName){
        return JSON.toJSONString(devUserService.checkApkName(apkName)>0);
    }
    @RequestMapping("add")
    public String add(AppInfo appInfo, MultipartFile logFile, HttpSession session) throws IOException {
        appInfo.setDevId(((DevUser) session.getAttribute("dUser")).getId());
        if (logFile != null) {
            String filename = FilenameUtils.getExtension(logFile.getOriginalFilename());
            if (filename.equalsIgnoreCase("png")||filename.equalsIgnoreCase("jpg")) {
                File path = new File(session.getServletContext().getRealPath(File.separator + "img"));
                if (!path.exists()) path.mkdirs();
                String newFileName = UUID.randomUUID().toString() + "." + filename;
                logFile.transferTo(new File(path, newFileName));
                appInfo.setLogoPicPath(File.separator + "img" + File.separator + newFileName);
            }else {
                return JSON.toJSONString(map.put("result", "error"));
            }
        }
        return JSON.toJSONString(devUserService.addInfo(appInfo));
    }
    @RequestMapping("update")
    public String update(AppInfo appInfo,MultipartFile logFile,HttpSession session) throws IOException {
        appInfo.setDevId(((DevUser) session.getAttribute("dUser")).getId());
        if (logFile != null) {
            String filename = FilenameUtils.getExtension(logFile.getOriginalFilename());
            if (filename.equalsIgnoreCase("png")||filename.equalsIgnoreCase("jpg")) {
                File path = new File(session.getServletContext().getRealPath(File.separator + "img"));
                if (!path.exists()) path.mkdirs();
                String newFileName = UUID.randomUUID().toString()+"." + filename;
                String logoPicPath = devUserService.appInfoById(appInfo.getId()).getLogoPicPath();
                if (logoPicPath != null && !"".equals(logoPicPath))
                    new File(session.getServletContext().getRealPath(logoPicPath)).delete();
                logFile.transferTo(new File(path, newFileName));
                appInfo.setLogoPicPath(File.separator + "img" + File.separator + newFileName);
            }else {
                return JSON.toJSONString(map.put("result", "error"));
            }
        }
        return JSON.toJSONString(devUserService.updateInfo(appInfo));
    }
    @RequestMapping("del")
    public String del(Integer id,HttpSession session){
        AppInfo appInfo = devUserService.appInfoById(id);
        List<AppVersion> versionList = devUserService.versionById(appInfo.getVersionId());
        if (versionList!=null){
            for (AppVersion version : versionList) {
                if (version.getDownloadLink() != null && !"".equals(version.getDownloadLink()))new File(session.getServletContext().getRealPath(version.getDownloadLink())).delete();
            }
        }
        if (appInfo.getLogoPicPath() != null && !"".equals(appInfo.getLogoPicPath()))new File(session.getServletContext().getRealPath(appInfo.getLogoPicPath())).delete();
        return JSON.toJSONString(devUserService.delAppInfo(id));
    }
    @RequestMapping("upStatus")
    public String upStatus(Integer status,Integer id){
        return JSON.toJSONString(devUserService.upOrDownStatus(status, id) > 0);
    }
    @RequestMapping("appInfoById")
    public String appInfoById(Integer id,HttpSession session){
        DevUser devUser = (DevUser) session.getAttribute("dUser");
        BackendUser backendUser = (BackendUser) session.getAttribute("bUser");
        if (devUser == null) {
            if (backendUser!=null) return JSON.toJSONString(devUserService.appInfoById(id));
            return JSON.toJSONString(map.put("result", "error"));
        }else {
            if (backendUser!=null) return JSON.toJSONString(devUserService.appInfoById(id));
            return JSON.toJSONString(devUserService.appInfoById(id));
        }
    }
    @RequestMapping("versionById/{id}")
    public String versionById(@PathVariable Integer id){
        return JSON.toJSONString(devUserService.versionById(id));
    }
    @RequestMapping("addVersion")
    public String addVersion(AppVersion version,MultipartFile apkFile,HttpSession session) throws IOException {
        version.setCreatedBy(((DevUser) session.getAttribute("dUser")).getId());
        if (apkFile != null) {
            String filename = FilenameUtils.getExtension(apkFile.getOriginalFilename());
            if (filename.equalsIgnoreCase("apk")) {
                File path = new File(session.getServletContext().getRealPath(File.separator + "img"));
                if (!path.exists()) path.mkdirs();
                String newFileName = UUID.randomUUID().toString()+"." + filename;
                List<AppVersion> versionList = devUserService.versionById(version.getAppId());
                if (versionList != null) {
                    for (AppVersion appVersion : versionList) {
                        if (appVersion.getDownloadLink() != null && !"".equals(appVersion.getDownloadLink())) new File(session.getServletContext().getRealPath(appVersion.getDownloadLink())).delete();
                    }
                }
                apkFile.transferTo(new File(path, newFileName));
                version.setDownloadLink(File.separator + "img" + File.separator + newFileName);
                version.setApkFileName(newFileName);
            }else {
                return JSON.toJSONString(map.put("result", "error"));
            }
        }
        return JSON.toJSONString(devUserService.addVersion(version));
    }
    @RequestMapping("appCategoryList")
    public String appCategoryList(Integer parentId){
        return JSON.toJSONString(devUserService.appCategoryList(parentId));
    }
    @RequestMapping("statusList")
    public String statusList(){
        return JSON.toJSONString(devUserService.statusList());
    }
    @RequestMapping("flatformList")
    public String flatformList(){
        return JSON.toJSONString(devUserService.flatformList());
    }
    @RequestMapping("exit")
    public String out(HttpSession session){
        session.invalidate();
        return JSON.toJSONString(map.put("result", "error"));
    }
}
