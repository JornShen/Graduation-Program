<%--
  Created by IntelliJ IDEA.
  User: atmlinux
  Date: 18-4-13
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datashow/model.js"></script>

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
                <h1 class="page-header">决策树预测</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">

            <div class="col-lg-9">
                <!--  大数据预测图表 -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-bar-chart-o fa-fw"></i> Decision Tree
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="col-md-16 col-md-offset-2">
                            <form class="form-horizontal" role="form" id="testModel">
                            <div class="form-group">
                                <label for="train_road" class="col-sm-3 control-label">训练集合地址</label>
                                <div class="col-sm-6">
                                    <%--<input type="text" class="form-control" id="train_road" placeholder="请输入训练集合地址">--%>
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                <span id="train_location">local</span>
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" id="train_loc_choose">
                                                <li>
                                                    <a>hdfs</a>
                                                </li>
                                                <li>
                                                    <a>local</a>
                                                </li>
                                            </ul>
                                        </div><!-- /btn-group -->
                                        <input type="text" class="form-control" id="train_road" name="train_road" placeholder="请输入训练集合地址">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="test_road" class="col-sm-3 control-label">测试集合地址</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                <span id="test_location">local</span>
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" id="test_loc_choose">
                                                <li>
                                                    <a>hdfs</a>
                                                </li>
                                                <li>
                                                    <a>local</a>
                                                </li>
                                            </ul>
                                        </div><!-- /btn-group -->
                                        <input type="text" class="form-control" id="test_road" name="test_road" placeholder="请输入测试集合地址">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="maxBin" class="col-sm-3 control-label">最大划分数</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="maxBin" name="maxBin" placeholder="请输入最大划分数">
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="maxDepth" class="col-sm-3 control-label">树最大深度</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="maxDepth" name="maxDepth" placeholder="请输入树最大深度">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="impurity" class="col-sm-3 control-label">特征选择</label>
                                <div class="col-sm-3">
                                    <select class="form-control" id="impurity">
                                        <option>gini</option>
                                        <option>entropy</option>
                                        <%-- 对应回归问题　<option>variance</option>--%>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-3 col-md-2">
                                    <button type="button" class="btn btn-info btn-block" id="commitbtn">提交</button>
                                </div>
                            </div>

                        </form>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
        </div>
        <div class="row">
             <div class="col-lg-12">
                 <div class="panel panel-default">
                     <div class="panel-heading">
                         <i class="fa fa-history fa-fw"></i> 历史记录
                     </div>
                     <!-- /.panel-heading -->
                     <div class="panel-body">
                         <div class="row">
                             <div class="col-lg-12">
                                 <div class="table-responsive">
                                     <table width="100%" class="table table-striped table-bordered table-hover">
                                         <thead>
                                         <tr>
                                             <th>训练地址</th>
                                             <th>测试地址</th>
                                             <th>最大划分数</th>
                                             <th>树最大深度</th>
                                             <th>特征选择</th>
                                             <th>F-Measure</th>
                                         </tr>
                                         </thead>
                                         <tbody id="hisTableBody"></tbody>
                                     </table>
                                 </div>
                                 <!-- /.table-responsive -->
                             </div>
                             <!-- /.col-lg-4 (nested) -->
                             <div class="col-lg-8">
                                 <div id="morris-bar-chart"></div>
                             </div>
                             <!-- /.col-lg-8 (nested) -->
                         </div>
                         <!-- /.row -->
                     </div>
                     <!-- /.panel-body -->
                 </div>
             </div>
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
</div>

<!-- 显示结果的模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    预测结果(小数表示精度,1为百分之百)
                </h4>
            </div>
            <div class="modal-body">
                <span id="result"></span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

</body>
</html>

