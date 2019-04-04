package com.yhl.job;

import com.yhl.map.MapReduceOfMap;
import com.yhl.reduce.MapReduceOfReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 提交你写的jar包倒yarn 方便
 * 你自定义的jar包进行离线分析
 * */
public class JobSubmit {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration =new Configuration();
        configuration.set("fs.defaultFS","hdfs://192.168.2.55:9000");
        configuration.set("mapreduce.framework.name","yarn");
        configuration.set("yarn.resourcemanager.hostname","192.168.2.55");
        //如果要在windows上运行需要加跨平台提交参数
        configuration.set("mapreduce.app-submission.cross-platform","true");


        Job job =Job.getInstance(configuration);
        // 1.封装参数。jar包坐在未知
         //job.setJar("E:\\w.jar"); //根据地址来定位JAR
        job.setJarByClass(JobSubmit.class);//根据类的类型class路径
        //2封装本次job要调用的maper实现类，reduce视线来
        job.setMapperClass(MapReduceOfMap.class);
        job.setReducerClass(MapReduceOfReduce.class);
        //3封装本子job的mapreduce产生的结果数据的key value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //4.封装本次job要处理的输入数据集所在路径
        FileInputFormat.setInputPaths(job,new Path("/"));
        FileOutputFormat.setOutputPath(job,new Path("/kk"));//输出路径必须不存在

        //5.封装参数，想要启动的reduce task 的数量，//可以不用指定，map 不用指定，reduce可以指定
        job.setNumReduceTasks(2);
        //6.提交交job给yarm
         boolean result = job.waitForCompletion(true);
        if (result){
            System.out.println("执行成功");
        }else {
            System.out.println("执行失败");
        }

    }

}
