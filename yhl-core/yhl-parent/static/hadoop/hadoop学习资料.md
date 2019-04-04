#hadoop
#简介 
* Hadoop是使用Java编写，允许分布在集群，使用简单的编程模型的计算机大型数据集处理的Apache的开源框架。 Hadoop框架应用工程提供跨计算机集群的分布式存储和计算的环境。 Hadoop是专为从单一服务器到上千台机器扩展，每个机器都可以提供本地计算和存储。

在其核心，Hadoop主要有两个层次，即：
* 加工/计算层(MapReduce)，以及
* 存储层(Hadoop分布式文件系统)。

除了上面提到的两个核心组件，Hadoop的框架还包括以下两个模块：
* Hadoop通用：这是Java库和其他Hadoop组件所需的实用工具。
* Hadoop YARN ：这是作业调度和集群资源管理的框架。

###MapReduce
    MapReduce是一种并行编程模型，用于编写普通硬件的设计，
    谷歌对大量数据的高效处理(多TB数据集)的分布式应用在大型集群(数千个节点)以及可靠的容错方式。
    MapReduce程序可在Apache的开源框架Hadoop上运行。
    
Hadoop运行整个计算机集群代码。这个过程包括以下核心任务由 Hadoop 执行： 

		数据最初分为目录和文件。文件分为128M和64M（128M最好）统一大小块。 
		然后这些文件被分布在不同的群集节点，以便进一步处理。 
		HDFS，本地文件系统的顶端﹑监管处理。 
		块复制处理硬件故障。 
		检查代码已成功执行。 
		执行发生映射之间，减少阶段的排序 
		发送排序的数据到某一计算机。 
		为每个作业编写的调试日志。



##hadoop 安装
1. linux root 登陆
2. 创建用户：hadoop
    *  useradd -m hadoop -s /bin/bash # 创建新用户hadoop //此命令创建了一个用户hadoop，其中-d和-m选项用来为登录名sam产生一个主目录/bin/bash
    *  passwd hadoop  //修改hadoop shell 登陆密码
    *  创建无密码登陆
        * cd ~/.ssh/       # 若没有该目录，请先执行一次ssh localhost
        * ssh-keygen -t rsa     # 会有提示，都按回车就可以
        * cat id_rsa.pub >> authorized_keys  # 加入授权
        * chmod 600 ./authorized_keys    # 修改文件权限
        * $ scp ~/.ssh/id_rsa.pub hadoop@Slave1:/home/hadoop/ 
          //scp 是 secure copy 的简写，用于在 Linux 下进行远程拷贝文件，类似于 cp 命令，不过 cp 只能在本机中拷贝。
          执行 scp 时会要求输入 Slave1 上 hadoop 用户的密码(hadoop)，输入完成后会提示传输完毕：
    *  cd  /etc/sudoers 
    *  tar -zxf hadoop3.1.1 -C /usr/local  解压倒local下面取
    
    
