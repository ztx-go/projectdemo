package com.example.projectdemo.controller;

import com.example.projectdemo.controller.model.ResponseModel;
import com.example.projectdemo.entity.AuthSourceAreaEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.service.AuthSourceAreaService;
import com.example.projectdemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(value = "授权作品_原产地存证controller")
@RestController
@RequestMapping("/v1/authSourceArea")
public class AuthSourceAreaController extends BaseController {

    @Autowired
    AuthSourceAreaService authSourceAreaService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseModel create(@ApiParam(value = "AuthTechEntity") @RequestBody AuthSourceAreaEntity authSourceAreaEntity, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            authSourceAreaService.create(authSourceAreaEntity, userEntity);
            return this.buildHttpReslut();
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseModel update(@ApiParam(value = "AuthTechEntity") @RequestBody AuthSourceAreaEntity authSourceAreaEntity, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            authSourceAreaService.update(authSourceAreaEntity, userEntity);
            return this.buildHttpReslut();
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseModel findById(@ApiParam(value = "id") String id, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            AuthSourceAreaEntity entity = authSourceAreaService.findById(id);
            return this.buildHttpReslut(entity, "createUser", "modifyUser");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseModel delete(@ApiParam(value = "id") @RequestParam String id, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            authSourceAreaService.delete(id);
            return this.buildHttpReslut();
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }
}
