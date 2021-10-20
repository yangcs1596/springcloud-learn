package com.safedog.spark.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ycs
 * @description
 * @date 2021/10/15 15:07
 */
@Slf4j
public class WordCountTService {
    public static Text k = new Text();
    public static IntWritable v = new IntWritable();

    /**
     * map阶段实现类
     */
    public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        /**
         * input中的每一行会调用一次map方法
         * value是每一行的数据
         * context是MapReduce的上下文
         *
         * @param key
         * @param value
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            log.info("进入map阶段" + key);
//
//            super.map(key, value, context);
//            将input文件中的一行行的数据进行处理
            String line = value.toString();
            String[] words = line.split(" ");
            for (String word : words) {
                k.set(word);
                v.set(1);
//                将每一行的每一个单词放入上下文中，给reduce处理。
                context.write(k, v);
            }
        }
    }

    /**
     * 从map到reduce的过程中MapReduce会对数据进行一些处理，合并key，将key-value对中的value做成一个value迭代器集合
     */


    /**
     * reduce阶段的输入key、value类型必须要与map阶段的输出key、value类型一致
     */
    public static class MyReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        /**
         * @param key     是在map阶段处理完的数据key
         * @param values  在map阶段处理完的value
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            log.info("进入reduce阶段");
//            super.reduce(key, values, context);
            int counter = 0;
            for (IntWritable value : values) {
                counter += value.get();
            }

            log.info(" --------- key：" + key + "  count：" + counter);
            context.write(key, new IntWritable(counter));

        }
    }

    /**
     * 驱动类
     *
     * @param args args[0] 为输入文件的路径
     *             args[1] 为结果输出的路径
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        System.setProperty("hadoop.home.dir", "F:\\hadoop\\hadoop-3.0.0");
//        读取hdfs中的配置文件 : org.apache.hadoop.conf.Configuration
        org.apache.hadoop.conf.Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        //设置使用hdfs分布式文件系统 设置使用hdfs分布式文件系统，指定hdfs实现类，不然将会出现访问错误

//        获取配置 , 连接到hdfs
        configuration.set("fs.defaultFS", "hdfs://192.168.79.89:9000");
//        ========================================================
//          设置Job实例的各个参数
//        job名字
        Job job = Job.getInstance(configuration, "mywordcountT");
//      指定job类
        job.setJarByClass(WordCountTService.class);
//      指定map类
        job.setMapperClass(MyMapper.class);
//        指定map输出key、value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
//       指定数据输入路径
        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path("hbase://SmallFile/3411f1b163b60b693e01abd27e915d07"));
//        指定reduce类及输出key、value类型
        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
//        指定输出路径
        FileOutputFormat.setOutputPath(job, new Path("/data/c.txt"));

//        等待job完成
        int isok = job.waitForCompletion(true) ? 0 : 1;

        log.info("词频程序运行结束。");
        System.exit(isok);
    }

}
