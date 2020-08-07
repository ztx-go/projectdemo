package com.example.projectdemo.controller;

import com.example.projectdemo.controller.model.ResponseModel;
import com.example.projectdemo.entity.OriginalTraditionalEntity;
import com.example.projectdemo.entity.UserEntity;
import com.example.projectdemo.service.OriginalTraditionalService;
import com.example.projectdemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(value = "原创作品_传统知识存证和保护controller")
@RestController
@RequestMapping("/v1/originalTraditional")
public class OriginalTraditionalController extends BaseController {

    @Autowired
    OriginalTraditionalService originalTraditionalService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseModel create(@ApiParam(value = "AuthTechEntity") @RequestBody OriginalTraditionalEntity originalTraditionalEntity, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            OriginalTraditionalEntity entity = originalTraditionalService.create(originalTraditionalEntity, userEntity);
            return this.buildHttpReslut(entity);
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseModel update(@ApiParam(value = "AuthTechEntity") @RequestBody OriginalTraditionalEntity originalTraditionalEntity, Principal logUser) {
        try {
            // 验证操作者是否登陆
            UserEntity userEntity = verifyLdapNodeLogin(logUser);
            Validate.notNull(userEntity, "操作者不能为空！");
            OriginalTraditionalEntity entity = originalTraditionalService.update(originalTraditionalEntity, userEntity);
            return this.buildHttpReslut(entity);
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
            OriginalTraditionalEntity entity = originalTraditionalService.findById(id);
            return this.buildHttpReslut(entity);
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }
}
