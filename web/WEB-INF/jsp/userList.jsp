<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面</span>
    </div>
    <div class="search">
        <form method="post" action="${pageContext.request.contextPath }/user/userList.html">
            <span>用户名：</span>
            <input name="queryName" class="input-text" type="text" value="${queryName }">

            <span>用户角色：</span>
            <select name="queryUserRole">
                <c:if test="${roleList != null }">
                    <option value="0">--请选择--</option>
                    <c:forEach var="role" items="${roleList}">
                        <option
                                <c:if test="${role.id == queryUserRole }">selected="selected"</c:if>
                                value="${role.id}">${role.roleName}</option>
                    </c:forEach>
                </c:if>
            </select>

            <input type="hidden" name="currentNo" value="1"/>
            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath}/user/useradd">添加用户</a>
        </form>
    </div>
    <!--用户-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">用户编码</th>
            <th width="20%">用户名称</th>
            <th width="10%">性别</th>
            <th width="10%">年龄</th>
            <th width="10%">电话</th>
            <th width="10%">用户角色</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
                </td>
                <td>
                    <span>${user.age}</span>
                </td>
                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
                    <span>${user.userRoleName}</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改"
                            title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img
                            src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>

    <%-- 查看用户信息的DIV --%>
    <div class="providerAdd">
        <div>
            <label for="userCode">用户编码：</label>
            <input name="userCode" type="text" id="userCode" value="" validatestatus="false">
        </div>
        <div>
            <label for="userName">用户名称：</label>
            <input name="userName" type="text" id="userName" value="" validatestatus="true">
        </div>
        <div>
            <label>用户性别：</label>
            <select name="gender" id="gender">
                <option value="1" selected="selected">男</option>
                <option value="2">女</option>
            </select>
        </div>
        <div>
            <label for="birthday">出生日期：</label>
            <input name="birthday" type="text" id="birthday" readonly="readonly" >
        </div>
        <div>
            <label for="phone">用户电话：</label>
            <input name="phone" type="text" id="phone" value="">
        </div>
        <div>
            <label for="address">用户地址：</label>
            <input name="address" id="address" value="">
        </div>
        <div>
            <label>用户角色：</label>
            <select name="userRole" id="userRole">
                <option value="1">系统管理员</option>
                <option value="2">经理</option>
                <option value="3" selected="selected">普通用户</option>
            </select>
        </div>
    </div>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/userlist.js"></script>
