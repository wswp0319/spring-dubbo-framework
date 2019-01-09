package net.menwei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.menwei.BaseController;
import net.menwei.ResultSet;
import net.menwei.service.DefaultApiService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Demo通用模块接口", description = "获取标签，帮助文档和开通业务的相关操作")
public class DemoConsumerController extends BaseController {
    private Logger logger = Logger.getLogger(DemoConsumerController.class);
    /**
     * 引入服务提供者
     */
    // com.alibaba.dubbo.config.annotation.Reference
    @Reference
    private DefaultApiService demoService;

    @ApiOperation(value = "根据name信息say hello", notes = "打招呼信息")
    @RequestMapping(value = "/sayHello", method = {RequestMethod.POST, RequestMethod.GET})
    @HystrixCommand(fallbackMethod = "failBackMethod")
    @ResponseBody
    public ResultSet sayHello(
            @ApiParam(name = "name", value = "店铺名车",example="光谷店", required = true) @RequestParam(value = "name") String name,
            @ApiParam(name = "address", value = "店铺地址",example="光谷软件园", required = true) @RequestParam(value = "address") String address,
            @ApiParam(name = "longitude", value = "店铺经度",example="114.40983", required = true) @RequestParam(value = "longitude") Double longitude,
            @ApiParam(name = "latitude", value = "店铺纬度",example="30.47919", required = true) @RequestParam(value = "latitude") Double latitude
    ) {
        logger.info("user " + name);
        return demoService.defaultMethod(name, address, longitude, latitude);
    }

    /**
     * 服务降级
     *
     * @param name
     * @return
     */
    @ResponseBody
    public ResultSet failBackMethod(String name, String address, Double longitude, Double latitude) {
        return ResultSet.createError("service request fail");
    }

}