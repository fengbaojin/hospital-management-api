package com.example.demo.service.impl;

import com.example.demo.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {


    //    @Override
//    public void prepare() {
//        // 1、创建ProcessEngin
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        // 2、得到RepositoryService实例
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        // 3、使用RepositoryService进行部署
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("bpm/holiday.bpmn")
//                .addClasspathResource("processes/holiday.png")
//                .name("请假流程测试")
//                .deploy();
//
//        System.out.println(deployment.getId());//2501,activit生成的ID
//        System.out.println(deployment.getName());//请假流程
//        System.out.println(deployment.getKey());//null
//        System.out.println(deployment.getCategory());//null
//        System.out.println(deployment.getDeploymentTime());
//        System.out.println(deployment.getTenantId());//null
//
//    }


//    @Override
//    public void prepare() {
//        String bpmnName = "myProcess";
//
//        String bpmn = "bpm/" + bpmnName + "bpmn20.xml";
//
//        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
//                .name("请求流程")//添加部署的名称
//                .addClasspathResource(bpmn)
//                .deploy();
//        System.out.println("部署ID：" + deployment.getId());
//        System.out.println("部署名称：" + deployment.getName());
//    }

    /**
     * 部署流程定义
     */
    @Override
    public void prepare() {
        return;
    }
}
