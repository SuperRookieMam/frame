###用户管理：

    //此命令新建了一个用户gem，该用户的登录Shell是 /bin/bash，
     它属于group用户组，同时又属于adm和root用户组，其中group用户组是其主组。
      这里可能新建组：#groupadd group及groupadd adm
    useradd -m hadoop -s /bin/bash # 创建新用户hadoop 
    passwd hadoop  //修改用户登陆密码
    cd  /etc/sudoers     //etc 这个目录用来存放所有的系统管理所需要的配置文件和子目录。
    vi 文件名 文件进入编辑模式
    sudo 命令 简单的说,sudo 是一种权限管理机制,管理员可以授权于一些普通用户去执行一些 root 执行的操作,而不需要知道 root 的密码
    sudo tar -zxf ~/hadoop-2.6.5.tar.gz  -C /usr/local  # 解压到/usr/local中
    mv ./hadoop3.1.2 ./hadoop //把./hadoop3.1.2里面的东西移动到  ./hadoop下面，相当于改名
    yum install lrzsz -y    rz  sz指令可以实现linux和windows之间的文件传输，但要求在windows客户端要安装Xshell或SecureCRT远程连接工具。
    rz  文件上传 //前提时要装上面那个
    nmtui net-tools 下面的命令配置IP
