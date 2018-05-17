<%--
  Created by IntelliJ IDEA.
  User: atmlinux
  Date: 18-4-18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/metisMenu.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sb-admin-2.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fonts/css/font-awesome.min.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/metisMenu.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/sb-admin-2.js"></script>

    <!--　引入 char.js 包 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.bundle.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.min.js"></script>

    <!-- 引入 char.js pie图表的插件使其可以显示百分比 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.PieceLabel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datashow/shoppingpricedis.js"></script>

</head>
<body>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/toshow">基于大数据平台的阿里广告预测</a>
        </div>

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/toshow"><i class="fa fa-calculator fa-fw"></i>模型预测</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>大数据图表示<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="${pageContext.request.contextPath}/toshow/toadclkcount">广告点击特征统计</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/toshow/toadrank">特征广告排名统计</a>
                            </li>
                            <li>
                                <a href="#">价格分布<span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/toshow/toclkpricedis">点击广告－价格分布</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/toshow/togenderpricedis">性别－价格分布</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/toshow/topvaluelevelpricedis">消费档次－价格分布</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/toshow/toshoppinglevelpricedis">购物深度－价格分布</a>
                                    </li>
                                </ul>
                                <!-- /.nav-third-level -->
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/toshow/togenderofshop">购物深度性别统计(hive)</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">购物深度-价格分布</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <!-- /.row -->
        <div class="row">
            <div class="col-lg-11">
                <!--  大数据预测图表 -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-bar-chart-o fa-fw"></i>购物深度价格分布
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="chart-container" style="position: relative">
                            <canvas id="shoppingpricedis"></canvas>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
        </div>
        <!-- /.row -->


    </div>
    <!-- /#page-wrapper -->
</div>


</body>
</html>

