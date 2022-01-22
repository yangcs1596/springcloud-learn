/**
 * Copyright©2018 Xiamen Meiah Pico IT CO., Ltd.
 * All rights reserved.
 */

/**
 * 功能/模块：<br/>
 * 描述：<br/>
 * @author liwb2
 * @date 2020/12/18.
 */
public class UpsourceTest {
    public static void main(String[] args) {
        //循环次数
        int i = 50;
        for (int j = 0; j < i; j++) {
            System.out.println("test-"+j);
        }
        //开始测试整个单子review
        String s = "111";
        System.out.println("111".equals(s));
        //测试修复代码审核，管理jira
        int size = 20;
        for(int k=0;k<size;k++) {
            //再次提交测试jira单关联
            System.out.println("dkk");
            System.out.println("dk");
            //处理代码审核，提交注释reviewID
            System.out.println("dsll");
            //测试reviewId 写在中间位置
            System.out.println("dskk");
            //测试jira单写在中间位置
            System.out.println("okkk");
        }
        //提交代码，自动新增reviewId ，代码审核不通过，创建JIRA单
        //重新修改代码提交，注释写reviewId#JIRA单号，自动关联之前的review和jira单
        System.out.println("ok");
        System.out.println("commit");
        //结合使用jira的流程状态转换
        System.out.println("测试不通过");
        System.out.println("新提交review");
        //jira创建任务，完成任务提交，登记jira单号。解决任务，RESOLVED状态时，会如何。
        System.out.println("ss");
        //处理代码审核问题。ES-CR-144#CLDEYES-2563
        System.out.println("111");
        //单独提交jira单号，是否会自动关联review单.没有触发关联review单。是否因为review单子已经close
        System.out.println("ok");
        //再次提交测试.会自动关联jira单所关联的review单子。
        System.out.println("ok..");
        //再次测试，添加已经close的review单，是否还能关联.=》不会关联。
        System.out.println("ss");
        /**
         * 情况1：（1）.提交任务，没有填写jira单号。自动生成review1 ，审核不通过，创建jira1 =》jira1不会自动关联review1
         * （2） 代码修改，svn提交，只填写jira1，是否会关联review1还是生成review2？=》生成review2，此时如同情况2的开始。
         * 情况2：（1）提交任务，填写jira单号，jira1，自动生成review1，jira1关联review1. 审核不通过，创建jira2 =》jira2是否会自动关联review1？
         * =》不会自动关联review1.如同1的（1），只是单纯的创建jira单而已。
         * （2）代码修改，svn提交，只填写jira2，是否会关联review1还是生成review2？ 如果填写的jira2有关联review，则会关联填写的jira所关联的review
         * 如果填写的jira2没有关联review，则会生成新的review，同时会关联jira2，如同情况2（1）
         * 情况3：（1）提交任务，没有填写jira单号，自动生成review4 ，审核不通过，创建jira4。review4会显示jira4
         * （2）提交任务，填写review4，不会自动生成reivew5，会自动关联review4。jira4是否会关联review4？=》不会关联。
         * （3）提交任务，填写review4#jira4，不会自动生成reivew5，会自动关联review4，jira4会关联review4，同时reivew4会显示jira4
         * （理论上每次提交代码都会生成一个新的review id，但如果备注有填写review id则不会）
         *
         */
        //测试提交分支审核人，分不同分支审核  随机审核77773333  123  添加多个触发器 测试填写review id ，以review id开头，测试正则匹配
        //测试追加到已关闭的review单
    }
}
