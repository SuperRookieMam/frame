package com.yhl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;


public class hdfs {

    public static void main(String[] args) throws IOException {
        Configuration configuration =new Configuration();
        configuration.set("fs.defaultFS","hdfs://192.168.2.55:9000");
        configuration.set("fs.hdfs.impl",DistributedFileSystem.class.getName());
        FileSystem fileSystem =FileSystem.newInstance(configuration);
        fileSystem.copyFromLocalFile(new Path("C:\\Users\\Administrator\\Downloads\\1.rar"),new Path("/"));
        fileSystem.close();
    }
}
