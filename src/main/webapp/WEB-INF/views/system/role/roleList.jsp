]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
     <ul class="breadcrumb">
        <li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
    </ul>
    <div id="toolbar">
        <ul class="nav nav-pills">
            <li><a id="add" href="${ctx}/sys/role/form"><i class="icon-plus"></i>&nbsp;新增</a></li>
        </ul>
    </div>
    <sys:message content="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <tr>
            <th>角色名称</th>
            <th>所属单位</th>
            <th>数据权限</th>
            <shiro:hasPermission name="sys:role:edit">
                <th>操作</th>
            </shiro:hasPermission>
        </tr>
        <c:forEach items="${list}" var="role">
            <tr>
                <td><a href="form?id=${role.id}">${role.name}</a></td>
                <td>${role.office.name}</td>
                <td>${fns:getDictLabel(role.dataType, 'sys_data_scope', '无')}</td>
                <shiro:hasPermission name="sys:role:edit"><td>
                    <a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
                    <a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
                </td></shiro:hasPermission> 
            </tr>
        </c:forEach>
    </table>
</body>
</html>