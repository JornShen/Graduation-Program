// 放置常用的函数

function getRootPath() {
    //取得当前URL
    var path = window.document.location.href;
    //取得主机地址后的目录
    var pathName = window.document.location.pathname;
    var post = path.indexOf(pathName);
    //取得主机地址
    var hostPath = path.substring(0, post);

    //取得项目名
    projectName = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);

    var rootPath = hostPath + projectName;
    return rootPath;
}
